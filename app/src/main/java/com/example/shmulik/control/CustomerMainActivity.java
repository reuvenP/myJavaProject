package com.example.shmulik.control;

import android.app.Fragment;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shmulik.myjavaproject.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import entities.Book;
import entities.Category;
import model.backend.Backend;
import model.backend.BackendFactory;

public class CustomerMainActivity extends AppCompatActivity {

    Backend backend;
    ListView customerLV;
    ArrayList<Book> bookArrayList;
    public Book bookToShow = null;
    Spinner categorySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_main);
        customerLV =// new ListView(this);
                (ListView) findViewById(R.id.customer_LV);
        categorySpinner = (Spinner) findViewById(R.id.category_spinner);
        try {

            backend = BackendFactory.getInstance();
            bookArrayList = backend.getBookList();
        }
        catch (Exception e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        BooksAdapter adapter = new BooksAdapter(this, bookArrayList);
        customerLV.setAdapter(adapter);
        customerLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                bookToShow = (Book) customerLV.getItemAtPosition(position);
                Intent intent = new Intent(CustomerMainActivity.this, CustomerBookViewActivity.class);
                intent.putExtra("bookID", bookToShow.getBookID());
                startActivity(intent);
            }
        });

        List<Category> categoryList = Arrays.asList(Category.values());
        ArrayList<String> list = new ArrayList<>();
        list.add("All");
        for (int i=0; i<categoryList.size();i++)
        {
            list.add(categoryList.get(i).toString());
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, list);
        categorySpinner.setAdapter(spinnerAdapter);
    }
}
