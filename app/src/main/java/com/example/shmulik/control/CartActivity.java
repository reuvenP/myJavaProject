package com.example.shmulik.control;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
    Backend backend;
    User currentUser;
    ListView listView;
    Button clear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        backend = BackendFactory.getInstance();
        currentUser = UserSingltone.getInstance();
        listView = (ListView) findViewById(R.id.cart_LV);
        clear = (Button) findViewById(R.id.cart_clear_BTN);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emptyCart();
            }
        });
        CartAdapter adapter = new CartAdapter(this, currentUser.getOrder());
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
    public void removeFromCard(int position)
    {
        try {
            BookSupplier bookSupplier = currentUser.getOrder().get(position);
            bookSupplier.setAmount(bookSupplier.getAmount()+1);
            currentUser.getOrder().remove(position);
            backend.updateBookSupplier(bookSupplier);
            backend.updateUser(currentUser);
            refreshView();
        } catch (Exception e) {
            Toast.makeText(CartActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }
    }
    void refreshView()
    {
        CartAdapter adapter = new CartAdapter(this, currentUser.getOrder());
        listView.setAdapter(adapter);
    }

    void emptyCart()
    {
        try {
            for (BookSupplier bookSupplier : currentUser.getOrder()) {
                bookSupplier.setAmount(bookSupplier.getAmount()+1);
                backend.updateBookSupplier(bookSupplier);
            }
            currentUser.getOrder().clear();
            backend.updateUser(currentUser);
            refreshView();
        }catch (Exception e) {
            Toast.makeText(CartActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }
    }
}
