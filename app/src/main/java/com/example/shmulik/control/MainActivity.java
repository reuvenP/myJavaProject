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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        backend = BackendFactory.getInstance();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, currentUser.getPassword(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });

        try {
            int ID = backend.addCustomer(new Customer(CustomerType.VIP, "Shmulik", new Date(), Gender.MALE, "Miron 16 Bnei Brak", new Account()));
            backend.addUser(new User(Permission.CUSTOMER, "shmulik@shmulikCorporatin.com","1234",ID));
            currentUser = backend.getUserByID(ID);
        }
        catch (Exception e)
        {
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
        if (currentUser != null){
            TextView mailTV = (TextView) findViewById(R.id.mailTV);
            mailTV.setText(currentUser.getMail());
            TextView permTV = (TextView) findViewById(R.id.permTV);
            permTV.setText(currentUser.getPermission().toString());
            TextView nameTV = (TextView) findViewById(R.id.nameTV);
            nameTV.setText("Shmulik");
            TextView passTV = (TextView) findViewById(R.id.passwordTV);
            passTV.setText(currentUser.getPassword());
        }
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
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
