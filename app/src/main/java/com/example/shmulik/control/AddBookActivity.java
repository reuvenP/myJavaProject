package com.example.shmulik.control;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.shmulik.myjavaproject.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import entities.Book;
import entities.BookSupplier;
import entities.Category;
import entities.User;
import model.backend.Backend;
import model.backend.BackendFactory;
import model.backend.UserSingltone;

public class AddBookActivity extends AppCompatActivity {

    Backend backend;
    BookSupplier bookSupplier;
    Spinner category;
    AutoCompleteTextView title;
    AutoCompleteTextView author;
    AutoCompleteTextView year;
    AutoCompleteTextView pages;
    AutoCompleteTextView price;
    NumberPicker amount;
    User currentUser;
    LinearLayout layout;
    Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        backend = BackendFactory.getInstance();
        currentUser = UserSingltone.getInstance();
        title = (AutoCompleteTextView) findViewById(R.id.book_name_sup_addbook);
        author = (AutoCompleteTextView) findViewById(R.id.book_author_sup_addbook);
        year = (AutoCompleteTextView) findViewById(R.id.book_year_sup_addbook);
        pages = (AutoCompleteTextView) findViewById(R.id.book_pages_sup_addbook);
        category = (Spinner) findViewById(R.id.book_category_sup_addbook);
        price = (AutoCompleteTextView) findViewById(R.id.book_price_sup_addbook);
        amount = (NumberPicker) findViewById(R.id.book_amount_sup_addbook);
        add = (Button) findViewById(R.id.book_update_sup_addbook);
        amount.setMinValue(0);
        amount.setMaxValue(1000);
        amount.setValue(0);

        List<Category> categoryList = Arrays.asList(Category.values());
        final ArrayList<String> list = new ArrayList<>();
        for (int i=0; i<categoryList.size();i++)
        {
            list.add(categoryList.get(i).toString());
        }
        category.setAdapter(new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, list));

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Book book = new Book(title.getText().toString(), Integer.parseInt(year.getText().toString()),
                           author.getText().toString(), Integer.parseInt(pages.getText().toString()),
                            Category.valueOf(list.get(category.getSelectedItemPosition())));
                    bookSupplier = new BookSupplier(backend.getSupplierBySupplierID(currentUser.getUserID()),
                            book, Float.valueOf(price.getText().toString()), amount.getValue());
                    backend.addBook(book);
                    backend.addBookSupplier(bookSupplier);
                    Toast.makeText(AddBookActivity.this,"Added",Toast.LENGTH_LONG).show();
                }
                catch (Exception e)
                {
                    Toast.makeText(AddBookActivity.this,"Error",Toast.LENGTH_LONG).show();

                }
            }
        });
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
            Intent intent = new Intent(AddBookActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        layout = (LinearLayout) findViewById(R.id.layout_sup_addbook);
        layout.requestFocus();
    }
}
