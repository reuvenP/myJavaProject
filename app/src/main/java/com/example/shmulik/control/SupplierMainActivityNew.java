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
import android.widget.Button;

import com.example.shmulik.myjavaproject.R;

import entities.User;
import model.backend.userSingleton;

/**
 * Created by shmuel on 19/06/2016.
 */
public class SupplierMainActivityNew extends AppCompatActivity {
    Button bookList;
    Button addNewBook;
    Button addNewBookFromList;
    Button profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_main_new);
        bookList = (Button)findViewById(R.id.book_list_main_supplier);
        addNewBook = (Button)findViewById(R.id.add_book_main_supplier);
        addNewBookFromList = (Button)findViewById(R.id.add_book_from_list_main_supplier);
        profile = (Button)findViewById(R.id.edit_profile_main_supplier);

        bookList.setOnClickListener(new View.OnClickListener() { // on click on "Book list" button.
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SupplierMainActivityNew.this, SupplierMainActivity.class); // go to SupplierMainActivity.
                startActivity(intent);
            }
        }); // on click on "Book list" button.

        addNewBook.setOnClickListener(new View.OnClickListener() { // on click on "addNewBook" button.
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SupplierMainActivityNew.this, AddBookActivity.class); // go to AddBookActivity.
                startActivity(intent);
            }
        }); // on click on "addNewBook" button.

        addNewBookFromList.setOnClickListener(new View.OnClickListener() { // on click on "addNewBookFromList" button.
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SupplierMainActivityNew.this, SupplierAddBookFromListActivity.class); // go to SupplierAddBookFromListActivity.
                startActivity(intent);
            }
        }); // on click on "addNewBookFromList" button.

        profile.setOnClickListener(new View.OnClickListener() { // on click on "submit" button.
            @Override
            public void onClick(View v) { // on click on "profile" button.
                Intent intent = new Intent(SupplierMainActivityNew.this, EditUserDetailsActivity.class); // go to EditUserDetailsActivity (to add to cart).
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
            Intent intent = new Intent(SupplierMainActivityNew.this, MainActivity.class); // go to main activity.
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // clear current Activity stack and launch a new Activity.
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(SupplierMainActivityNew.this)
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

