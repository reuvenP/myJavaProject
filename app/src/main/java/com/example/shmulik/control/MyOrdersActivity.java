package com.example.shmulik.control;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.shmulik.myjavaproject.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import entities.Book;
import entities.Category;
import entities.Customer;
import entities.Order;
import entities.User;
import model.backend.Backend;
import model.backend.BackendFactory;
import model.backend.userSingleton;

/**
 * Created by shmuel on 19/06/2016.
 */
// class to manage customer old orders activity.
public class MyOrdersActivity extends AppCompatActivity {
        Backend backend;
        ListView ordersLV;
        ArrayList<Order> orderArrayList;
        public Order orderToShow = null;
        User currentUser;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_orders_list);
            ordersLV = (ListView) findViewById(R.id.orders_LV);

            try {
                backend = BackendFactory.getInstance(MyOrdersActivity.this); // get the current backend.
                currentUser = userSingleton.getInstance(); // get the current user.
            }
            catch (Exception e)
            {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                finish();
            }

            setOrdersLV();
            ordersLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) { // on click on order from the list.
                    orderToShow = (Order) ordersLV.getItemAtPosition(position);
                    Intent intent = new Intent(MyOrdersActivity.this, OrderViewActivity.class); // go to OrderViewActivity (to see more details).
                    intent.putExtra("orderID", orderToShow.getOrderID());
                    startActivity(intent);
                }
            });
        }

        void setOrdersLV(){ // set the view of all orders.
            try {
                orderArrayList = backend.getOrderListOfSpecificCustomerByCustomerID(currentUser.getUserID());
                OrdersAdapter adapter = new OrdersAdapter(this, orderArrayList);
                ordersLV.setAdapter(adapter);
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
                Intent intent = new Intent(MyOrdersActivity.this, MainActivity.class); // go to main activity.
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // clear current Activity stack and launch a new Activity.
                startActivity(intent);
                return true;
            }

            else if (id == R.id.action_cart) // choose to go to cart.
            {
                Intent intent = new Intent(MyOrdersActivity.this, CartActivity.class); // go to cart activity.
                startActivity(intent);
            }

            else if (id == R.id.action_edit_details) // choose to edit profile
            {
                Intent intent = new Intent(MyOrdersActivity.this, EditUserDetailsActivity.class);
                startActivity(intent);
            }

            else if (id == R.id.action_my_orders) // choose to see old orders.
            {
                Intent intent = new Intent(MyOrdersActivity.this, MyOrdersActivity.class);
                startActivity(intent);
            }
            return super.onOptionsItemSelected(item);
        }

        @Override
        protected void onResume() {
            super.onResume();
            setOrdersLV();
        }
}
