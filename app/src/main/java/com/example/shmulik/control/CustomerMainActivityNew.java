package com.example.shmulik.control;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import com.example.shmulik.myjavaproject.R;

import entities.User;
import model.backend.userSingleton;


/**
 * Created by shmuel on 19/06/2016.
 */
public class CustomerMainActivityNew  extends AppCompatActivity {
    Button bookList;
    Button cart;
    Button orders;
    Button profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_main_new);
        bookList = (Button)findViewById(R.id.book_list_main_customer);
        cart = (Button)findViewById(R.id.cart_main_customer);
        orders = (Button)findViewById(R.id.orders_main_customer);
        profile = (Button)findViewById(R.id.edit_profile_main_customer);

        bookList.setOnClickListener(new View.OnClickListener() { // on click on "Book list" button.
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerMainActivityNew.this, CustomerMainActivity.class); // go to CustomerMainActivity.
                startActivity(intent);
            }
        }); // on click on "Book list" button.

        cart.setOnClickListener(new View.OnClickListener() { // on click on "cart" button.
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerMainActivityNew.this, CartActivity.class); // go to CartActivity.
                startActivity(intent);
            }
        }); // on click on "cart" button.

        orders.setOnClickListener(new View.OnClickListener() { // on click on "orders" button.
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerMainActivityNew.this, MyOrdersActivity.class); // go to MyOrdersActivity.
                startActivity(intent);
            }
        }); // on click on "orders" button.

        profile.setOnClickListener(new View.OnClickListener() { // on click on "submit" button.
            @Override
            public void onClick(View v) { // on click on "profile" button.
                Intent intent = new Intent(CustomerMainActivityNew.this, EditUserDetailsActivity.class); // go to EditUserDetailsActivity (to add to cart).
                startActivity(intent);
            }
        }); // on click on "profile" button.

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { // create the menu.
        getMenuInflater().inflate(R.menu.menu_logout, menu);
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
            Intent intent = new Intent(CustomerMainActivityNew.this, MainActivity.class); // go to main activity.
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // clear current Activity stack and launch a new Activity.
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(CustomerMainActivityNew.this)
                .setTitle("Exit the store?")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) { // choose yes...
                        try {
                            finish();
                        } catch (Exception e) {

                        }
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() { // choose no...
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                })
                .show();
    }
}
