package com.example.shmulik.control;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
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
public class SupplierMainActivity extends AppCompatActivity /*implements SwipeRefreshLayout.OnRefreshListener */{
    Backend backend;
    ListView supplierLV;
    SwipeRefreshLayout pullToRefresh;
    ArrayList<Book> bookArrayList;
    public Book bookToShow = null;
    User currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_main);
        supplierLV = (ListView) findViewById(R.id.supplier_LV);
        pullToRefresh = (SwipeRefreshLayout) findViewById(R.id.pullToRefresh_supplier_main);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                // TODO Auto-generated method stub
                pullToRefresh.setRefreshing(true);
                refreshContent();
            }
        });

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
                                } catch (Exception e) {

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
                                            public void onClick(DialogInterface dialog, int which) { // choose yes...
                                                try {
                                                    backend.deleteBookSupplier(((Book) supplierLV.getItemAtPosition(position)).getBookID(), currentUser.getUserID()); // delete the book from the supplier.
                                                    refreshListView(); // refresh LV with the new changes.
                                                } catch (Exception e) {

                                                }
                                            }
                                        })
                                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) { // choose no...
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
    public boolean onCreateOptionsMenu(Menu menu) { // create the menu
        getMenuInflater().inflate(R.menu.menu_logout_add_book, menu);
        return true;
    }

    private void refreshContent(){
        pullToRefresh.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(SupplierMainActivity.this, "start to refresh", Toast.LENGTH_SHORT).show();
                refreshListView();
                pullToRefresh.setRefreshing(false);
                Toast.makeText(SupplierMainActivity.this, "finished to refresh", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_logout) { // choose to logout.
            User user = new User(); // reset the user...
            userSingleton.setInstance(user);
            SharedPreferences sharedPreferences = getSharedPreferences("userIDPre", Context.MODE_PRIVATE);
            sharedPreferences.edit().putInt("userID", -1).apply();
            Intent intent = new Intent(SupplierMainActivity.this, MainActivity.class); // go to main activity.
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // clear current Activity stack and launch a new Activity.
            startActivity(intent);
            return true;
        }

        else if (id == R.id.action_add_book) // choose to add a new book to sell.
        {
            Intent intent = new Intent(SupplierMainActivity.this, AddBookActivity.class); // go to add book activity.
            startActivity(intent);
        }

        else if (id == R.id.action_add_exist_book) // choose to add an a exist book to sell.
        {
            Intent intent = new Intent(SupplierMainActivity.this, SupplierAddBookFromListActivity.class); // go to add book from list activity.
            startActivity(intent);
        }

        else if (id == R.id.action_edit_details) //choose to edit profile
        {
            Intent intent = new Intent(SupplierMainActivity.this, EditUserDetailsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    void refreshListView() // refresh the view.
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
}
