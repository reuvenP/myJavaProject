package com.example.shmulik.control;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shmulik.myjavaproject.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;


import entities.BooksForOrder;
import entities.Order;
import entities.User;
import model.backend.Backend;
import model.backend.BackendFactory;
import model.backend.userSingleton;

/**
 * Created by shmuel on 19/06/2016.
 */

// class to manage customer old order view activity.
public class OrderViewActivity extends AppCompatActivity {
    Backend backend;
    TextView orderID;
    TextView orderDate;
    TextView totalPrice;
    TextView listHead ;
    ListView booksForOrderLV;
    Order order;
    ArrayList<BooksForOrder> booksForOrderList;
    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_view);
        booksForOrderLV = (ListView) findViewById(R.id.books_for_order_LV);
        Bundle extras = getIntent().getExtras(); // fetches data which was added using putExtra().
        if (extras == null)
        {
            finish();
        }

        try {
            backend = BackendFactory.getInstance(OrderViewActivity.this); // get the current backend.
            currentUser = userSingleton.getInstance(); // get the current user.
            order = backend.getOrderByOrderID(extras.getInt("orderID"));
        }
        catch (Exception e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            finish();
        }
        orderID = (TextView) findViewById(R.id.order_id_orderview);
        orderDate = (TextView) findViewById(R.id.order_date_orderview);
        totalPrice = (TextView) findViewById(R.id.total_price_orderview);
        listHead = (TextView) findViewById(R.id.list_head_orderview);
        orderID.setText("Serial Number: " + Integer.toString(order.getOrderID()));
        orderDate.setText("Date of Order: " + new SimpleDateFormat("yyyy-MM-dd").format(order.getOrderDate()));
        totalPrice.setText("Total Price: " + Float.toString(order.getTotalPrice()));
        setBooksForOrderLV();
    }

    void setBooksForOrderLV(){ // set the view of all books for order.
        try {
            booksForOrderList = order.getBooksForOrders();
            OrderViewAdapter adapter = new OrderViewAdapter(this, booksForOrderList);
            booksForOrderLV.setAdapter(adapter);
        }
        catch (Exception e) {
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { // create the menu.
        getMenuInflater().inflate(R.menu.menu_logout_cart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_logout) { // choose to logout.
            User user = new User(); // reset the user...
            userSingleton.setInstance(user);
            SharedPreferences sharedPreferences = getSharedPreferences("userIDPre", Context.MODE_PRIVATE);
            sharedPreferences.edit().putInt("userID", -1).apply();
            Intent intent = new Intent(OrderViewActivity.this, MainActivity.class); // go to main activity.
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // clear current Activity stack and launch a new Activity.
            startActivity(intent);
            return true;
        }

        else if (id == R.id.action_cart) // choose to go to cart.
        {
            Intent intent = new Intent(OrderViewActivity.this, CartActivity.class); // go to cart activity.
            startActivity(intent);
        }

        else if (id == R.id.action_edit_details) // choose to edit profile
        {
            Intent intent = new Intent(OrderViewActivity.this, EditUserDetailsActivity.class);
            startActivity(intent);
        }

        else if (id == R.id.action_my_orders) // choose to see old orders.
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setBooksForOrderLV();
    }
}
