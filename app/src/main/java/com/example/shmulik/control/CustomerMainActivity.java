package com.example.shmulik.control;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shmulik.myjavaproject.R;

import java.util.ArrayList;

import entities.Book;
import model.backend.Backend;
import model.backend.BackendFactory;

public class CustomerMainActivity extends AppCompatActivity {

    Backend backend;
    ListView customerLV;
    ArrayList<Book> bookArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_main);
        customerLV =// new ListView(this);
                (ListView) findViewById(R.id.customer_LV);
        try {

            backend = BackendFactory.getInstance();
            bookArrayList = backend.getBookList();
        }
        catch (Exception e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

            ArrayAdapter<Book> adapter = new ArrayAdapter<Book>(this, R.layout.row_book, bookArrayList)
            {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    if (convertView == null){
                        convertView = View.inflate(CustomerMainActivity.this, R.layout.row_book, null);
                    }
                    Book book = getItem(position);
                    TextView book_id = (TextView) findViewById(R.id.book_id_row);
                    TextView book_name = (TextView)findViewById(R.id.book_name_row);
                    TextView book_category = (TextView) findViewById(R.id.book_category_row);
                   // book_id.setText(bookArrayList.get(position).getBookID());
                   // book_name.setText(bookArrayList.get(position).getTitle());
                   // book_category.setText(book.getCategory().toString());
                    return convertView;
                }
            };
            customerLV.setAdapter(adapter);

    }
}
