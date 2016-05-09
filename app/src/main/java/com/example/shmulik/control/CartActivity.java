package com.example.shmulik.control;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.shmulik.myjavaproject.R;

import java.util.ArrayList;

import entities.BookSupplier;
import entities.BooksForOrder;
import entities.Order;
import entities.User;
import model.backend.Backend;
import model.backend.BackendFactory;
import model.backend.UserSingltone;

public class CartActivity extends AppCompatActivity {
    Order order;
    Backend backend;
    User currentUser;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        backend = BackendFactory.getInstance();
        currentUser = UserSingltone.getInstance();
        if (order == null)
        {
            SharedPreferences orderSharedPreferences;
            orderSharedPreferences = getSharedPreferences("orderIDPre", Context.MODE_PRIVATE);
            int orderID = orderSharedPreferences.getInt("orderID", -1);
            if (orderID == -1)
            {
                order = null;
                Toast.makeText(CartActivity.this, "Cart is empty", Toast.LENGTH_LONG).show();
                finish();
                return;
            }
            else
            {
                try
                {
                    order = backend.getOrderByOrderID(orderID);
                }
                catch (Exception e)
                {
                    order = null;
                }
            }
        }
        listView = (ListView) findViewById(R.id.cart_LV);
        ArrayList<BookSupplier> bookSupplierArrayList = new ArrayList<>();
        for (BooksForOrder booksForOrder: order.getBooksForOrders())
        {
            bookSupplierArrayList.add(booksForOrder.getBookSupplier());
        }
        CartAdapter adapter = new CartAdapter(this, bookSupplierArrayList);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_logout) {
            User user = new User();
            UserSingltone.setInstance(user);
            SharedPreferences sharedPreferences = getSharedPreferences("userIDPre", Context.MODE_PRIVATE);
            sharedPreferences.edit().putInt("userID", -1).apply();
            Intent intent = new Intent(CartActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences orderSharedPreferences;
        orderSharedPreferences = getSharedPreferences("orderIDPre", Context.MODE_PRIVATE);
        int orderID = orderSharedPreferences.getInt("orderID", -1);
        if (orderID == -1)
        {
            order = null;
            Toast.makeText(CartActivity.this, "Cart is empty", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        else
        {
            try
            {
                order = backend.getOrderByOrderID(orderID);
            }
            catch (Exception e)
            {
                order = null;
            }
        }
    }
}
