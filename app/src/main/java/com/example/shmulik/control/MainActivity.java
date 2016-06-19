package com.example.shmulik.control;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.shmulik.myjavaproject.R;

import java.util.ArrayList;

import entities.Order;
import entities.Permission;
import entities.User;
import model.backend.Backend;
import model.backend.BackendFactory;
import model.backend.userSingleton;


// class to manage start activity (main layout).
public class MainActivity extends AppCompatActivity {

    User currentUser;
    Backend backend;
    Button loginBT;
    Button signUpBT;
    SharedPreferences sharedPreferences; // key & value pair.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        backend = BackendFactory.getInstance(MainActivity.this); // get the current backend.
        currentUser = userSingleton.getInstance(); // get the current user if exist.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        loginBT = (Button) findViewById(R.id.login_mainBT);
        signUpBT = (Button) findViewById(R.id.signup_mainBT);

        signUpBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // on click go to sign up activity.
                Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
        loginBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // on click go to login activity.
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }



    @Override
    protected void onResume() {
        super.onResume();
        sharedPreferences = getSharedPreferences("userIDPre", Context.MODE_PRIVATE);
        int userID = sharedPreferences.getInt("userID", -1); // get user id if exist else return -1.
        if (userID != -1) // user id exist.
        {
            try {
                User user = backend.getUserByID(userID); // get the user.
                userSingleton.setInstance(user); // set the user singleton.
                currentUser = userSingleton.getInstance(); // get the user singleton.
                sharedPreferences.edit().putInt("userID", currentUser.getUserID()).apply(); // set the user id in the sharedPreferences.

                if (currentUser.getPermission() == Permission.CUSTOMER) // if the user is customer.
                {
                    Intent intent = new Intent(MainActivity.this, CustomerMainActivityNew.class); // load customer main activity.
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // clear current Activity stack and launch a new Activity.
                    startActivity(intent);
                }

                else if (currentUser.getPermission() == Permission.SUPPLIER) // if the user is supplier.
                {
                    Intent intent = new Intent(MainActivity.this, SupplierMainActivity.class); // load supplier main activity.
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // clear current Activity stack and launch a new Activity.
                    startActivity(intent);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Exit the store?")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            finish();
                        } catch (Exception e) {

                        }
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                })
                .show();
    }

}
