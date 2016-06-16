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
import android.widget.ListView;
import android.widget.Toast;

import com.example.shmulik.myjavaproject.R;

import java.util.ArrayList;

import entities.Book;
import entities.User;
import model.backend.Backend;
import model.backend.BackendFactory;
import model.backend.userSingleton;


// class to manage supplier main activity.
public class SupplierMainActivity extends AppCompatActivity {
    Backend backend;
    ListView supplierLV;
    ArrayList<Book> bookArrayList;
    public Book bookToShow = null;
    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_main);
        supplierLV = (ListView) findViewById(R.id.supplier_LV);
        try {
            backend = BackendFactory.getInstance(SupplierMainActivity.this); // get the current backend.
            currentUser = userSingleton.getInstance(); // get the current user.
            bookArrayList = backend.getBookListBySupplier(currentUser.getUserID()); // get the supplier book list. (by supplier ID)
            BooksAdapter adapter = new BooksAdapter(this, bookArrayList);
            supplierLV.setAdapter(adapter);
        }
        catch (Exception e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            finish();
        }

        supplierLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) { // on click on book from the list.
                bookToShow = (Book) supplierLV.getItemAtPosition(position);
                Intent intent = new Intent(SupplierMainActivity.this, SupplierBookViewActivity.class); // go to SupplierBookViewActivity (to edit option).
                intent.putExtra("bookID", bookToShow.getBookID());
                startActivity(intent);
            }
        });

        supplierLV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) { // on long click on book from the list. -> delete or edit options.
                new AlertDialog.Builder(SupplierMainActivity.this)
                        .setTitle("Delete or Edit?")
                        .setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) { // choose edit...
                                try {
                                    bookToShow = (Book) supplierLV.getItemAtPosition(position);
                                    Intent intent = new Intent(SupplierMainActivity.this, SupplierBookViewActivity.class); // go to SupplierBookViewActivity (to edit option).
                                    intent.putExtra("bookID", bookToShow.getBookID());
                                    startActivity(intent);
                                    refreshListView(); // refresh LV with the new changes.
                                }
                                catch (Exception e) {

                                }
                            }
                        })
                        .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) { // choose delete...
                                new AlertDialog.Builder(SupplierMainActivity.this)
                                        .setTitle("Are you sure?")
                                        .setMessage("Are you sure you want to delete?")
                                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                try {
                                                    backend.deleteBookSupplier(((Book) supplierLV.getItemAtPosition(position)).getBookID(), currentUser.getUserID()); // delete the book from the supplier.
                                                    refreshListView(); // refresh LV with the new changes.
                                                }
                                                catch (Exception e) {

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
                        })
                        .show();
                return true;
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_logout_add_book, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_logout) {
            User user = new User();
            userSingleton.setInstance(user);
            SharedPreferences sharedPreferences = getSharedPreferences("userIDPre", Context.MODE_PRIVATE);
            sharedPreferences.edit().putInt("userID", -1).apply();
            Intent intent = new Intent(SupplierMainActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.action_add_book)
        {
            Intent intent = new Intent(SupplierMainActivity.this, AddBookActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.action_add_exist_book)
        {
            Intent intent = new Intent(SupplierMainActivity.this, SupplierAddBookFromListActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    void refreshListView()
    {
        try
        {
            bookArrayList = backend.getBookListBySupplier(currentUser.getUserID());
            BooksAdapter adapter = new BooksAdapter(this, bookArrayList);
            supplierLV.setAdapter(adapter);
        }
        catch (Exception e)
        {
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshListView();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(SupplierMainActivity.this)
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
