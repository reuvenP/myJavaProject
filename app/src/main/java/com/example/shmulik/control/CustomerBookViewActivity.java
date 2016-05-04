package com.example.shmulik.control;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shmulik.myjavaproject.R;

import entities.Book;
import model.backend.Backend;
import model.backend.BackendFactory;

public class CustomerBookViewActivity extends AppCompatActivity {
    Backend backend;
    Book book;
    TextView bookName;

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

        bookName.setText(book.getTitle());
    }
}
