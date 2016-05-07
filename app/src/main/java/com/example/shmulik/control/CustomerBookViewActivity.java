package com.example.shmulik.control;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shmulik.myjavaproject.R;

import java.util.ArrayList;

import entities.Book;
import entities.BookSupplier;
import entities.User;
import model.backend.Backend;
import model.backend.BackendFactory;
import model.backend.UserSingltone;

public class CustomerBookViewActivity extends AppCompatActivity {
    Backend backend;
    Book book;
    TextView bookName;
    TextView year;
    TextView pages;
    TextView author;
    TextView category;
    TextView id;
    TextView amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_book_view);
        backend = BackendFactory.getInstance();
        Bundle extras = getIntent().getExtras();
        if (extras == null)
        {
            finish();
        }
        else
        {
            try {
                book = backend.getBookByBookID(extras.getInt("bookID"));
            } catch (Exception e) {
                finish();
            }
        }
        bookName = (TextView) findViewById(R.id.book_name_bookview);
        year = (TextView) findViewById(R.id.year_bookview);
        pages = (TextView) findViewById(R.id.pages_bookview);
        author = (TextView) findViewById(R.id.author_bookview);
        category = (TextView) findViewById(R.id.category_bookview);
        id = (TextView) findViewById(R.id.id_bookview);
        amount = (TextView) findViewById(R.id.amount_bookview);

        bookName.setText(book.getTitle());
        year.setText("Year: " + Integer.toString(book.getYear()));
        pages.setText("Pages: " + Integer.toString(book.getPages()));
        author.setText("Author: " + book.getAuthor());
        category.setText("Category: " + book.getCategory().toString());
        id.setText("Serial Number: " + Integer.toString(book.getBookID()));
        amount.setText("Total amount in store: " + backend.getBookAmountByBookID(book.getBookID()));

        ArrayList<BookSupplier> bookSupplierArrayList;
        BookSuppliersAdapter adapter;

        try {
            bookSupplierArrayList = backend.getBookSupplierByBookID(book.getBookID());
            adapter = new BookSuppliersAdapter(this,bookSupplierArrayList);
            ListView LV = (ListView) findViewById(R.id.bookSupplierLV);
            LV.setAdapter(adapter);
        } catch (Exception e) {
            finish();
        }
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
            Intent intent = new Intent(CustomerBookViewActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
