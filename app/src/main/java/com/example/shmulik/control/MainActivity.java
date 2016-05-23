package com.example.shmulik.control;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shmulik.myjavaproject.R;

import java.util.ArrayList;
import java.util.Date;

import entities.Account;
import entities.Book;
import entities.Category;
import entities.Customer;
import entities.CustomerType;
import entities.Gender;
import entities.Permission;
import entities.Supplier;
import entities.User;
import model.backend.Backend;
import model.backend.BackendFactory;
import model.backend.UserSingltone;

public class MainActivity extends AppCompatActivity {

    User currentUser;
    Backend backend;
    Button loginBT;
    Button signupBT;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        backend = BackendFactory.getInstance(MainActivity.this);
        currentUser = UserSingltone.getInstance();
        try
        {
            ArrayList<Customer> customerArrayList = backend.getCustomerList();
            Toast.makeText(MainActivity.this, customerArrayList.get(0).getName(),Toast.LENGTH_LONG).show();
        }
        catch (Exception e){e.printStackTrace();}
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        loginBT = (Button) findViewById(R.id.login_mainBT);
        signupBT = (Button) findViewById(R.id.signup_mainBT);


        signupBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
        loginBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }



    @Override
    protected void onResume() {
        super.onResume();
        sharedPreferences = getSharedPreferences("userIDPre", Context.MODE_PRIVATE);
        int userID = sharedPreferences.getInt("userID", -1);
        if (userID != -1)
        {
            try {
                User user = backend.getUserByID(userID);
                UserSingltone.setInstance(user);
                currentUser = UserSingltone.getInstance();
                sharedPreferences.edit().putInt("userID", currentUser.getUserID()).apply();
                if (currentUser.getPermission() == Permission.CUSTOMER)
                {
                    Intent intent = new Intent(MainActivity.this, CustomerMainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                else if (currentUser.getPermission() == Permission.SUPPLIER)
                {
                    Intent intent = new Intent(MainActivity.this, SupplierMainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
