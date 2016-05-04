package com.example.shmulik.control;

import android.content.Intent;
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

import java.util.Date;

import entities.Account;
import entities.Customer;
import entities.CustomerType;
import entities.Gender;
import entities.Permission;
import entities.User;
import model.backend.Backend;
import model.backend.BackendFactory;

public class MainActivity extends AppCompatActivity {

    User currentUser;
    Backend backend;
    Button loginBT;
    Button signupBT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        backend = BackendFactory.getInstance();
        try {
            backend.createLists();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        loginBT = (Button) findViewById(R.id.login_mainBT);
        signupBT = (Button) findViewById(R.id.signup_mainBT);


        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, currentUser.getPassword(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });*/

        try {
            int ID = backend.addCustomer(new Customer(CustomerType.VIP, "Shmulik", new Date(), Gender.MALE, "Miron 16 Bnei Brak", new Account()));
            backend.addUser(new User(Permission.CUSTOMER, "shmulik@shmulikCorporatin.com","1234",ID));
            currentUser = backend.getUserByID(ID);
        }
        catch (Exception e)
        {
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
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
                Intent intent = new Intent(MainActivity.this, CustomerMainActivity.class);//LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
