package com.example.shmulik.control;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shmulik.myjavaproject.R;

import java.util.ArrayList;

import entities.Book;
import entities.BookSupplier;
import model.backend.Backend;
import model.backend.BackendFactory;

public class CustomerBookViewActivity extends AppCompatActivity {
    Backend backend;
    Book book;
    TextView bookName;
    TextView year;
    TextView pages;
    TextView author;
    TextView category;
    TextView id;

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

        bookName.setText(book.getTitle());
        year.setText("Year: " + Integer.toString(book.getYear()));
        pages.setText("Pages: " + Integer.toString(book.getPages()));
        author.setText("Author: " + book.getAuthor());
        category.setText("Category: " + book.getCategory().toString());
        id.setText("Serial Number: " + Integer.toString(book.getBookID()));

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
}
