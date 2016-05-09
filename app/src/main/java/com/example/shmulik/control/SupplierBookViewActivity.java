package com.example.shmulik.control;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class SupplierBookViewActivity extends AppCompatActivity {
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
    Button update;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_book_view);
        backend = BackendFactory.getInstance(SupplierBookViewActivity.this);
        currentUser = UserSingltone.getInstance();
        Bundle extras = getIntent().getExtras();
        if (extras == null)
        {
            finish();
        }
        else
        {
            try {
                bookSupplier = backend.getBookSupplierBySupplierIDAndByBookID(currentUser.getUserID(),extras.getInt("bookID"));
            } catch (Exception e) {
                finish();
            }
        }
        title = (AutoCompleteTextView) findViewById(R.id.book_name_sup_bookview);
        author = (AutoCompleteTextView) findViewById(R.id.book_author_sup_bookview);
        year = (AutoCompleteTextView) findViewById(R.id.book_year_sup_bookview);
        pages = (AutoCompleteTextView) findViewById(R.id.book_pages_sup_bookview);
        category = (Spinner) findViewById(R.id.book_category_sup_bookview);
        price = (AutoCompleteTextView) findViewById(R.id.book_price_sup_bookview);
        amount = (NumberPicker) findViewById(R.id.book_amount_sup_bookview);
        update = (Button) findViewById(R.id.book_update_sup_bookview);
        amount.setMinValue(0);
        amount.setMaxValue(1000);

        title.setText(bookSupplier.getBook().getTitle().toString());
        author.setText(bookSupplier.getBook().getAuthor().toString());
        year.setText(Integer.toString(bookSupplier.getBook().getYear()));
        pages.setText(Integer.toString(bookSupplier.getBook().getPages()));
        price.setText(Float.toString(bookSupplier.getPrice()));
        amount.setValue(bookSupplier.getAmount());
        List<Category> categoryList = Arrays.asList(Category.values());
        final ArrayList<String> list = new ArrayList<>();
        for (int i=0; i<categoryList.size();i++)
        {
            list.add(categoryList.get(i).toString());
        }
        category.setAdapter(new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, list));
        category.setSelection(bookSupplier.getBook().getCategory().ordinal());

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookSupplier.getBook().setTitle(title.getText().toString());
                bookSupplier.getBook().setAuthor(author.getText().toString());
                bookSupplier.getBook().setPages(Integer.parseInt(pages.getText().toString()));
                bookSupplier.getBook().setYear(Integer.parseInt(year.getText().toString()));
                bookSupplier.getBook().setCategory(Category.valueOf(list.get(category.getSelectedItemPosition())));
                bookSupplier.setAmount(amount.getValue());
                bookSupplier.setPrice(Float.parseFloat(price.getText().toString()));
                try {
                    backend.updateBook(bookSupplier.getBook(),bookSupplier.getBook().getBookID());
                    backend.updateBookSupplier(bookSupplier);
                    Toast.makeText(SupplierBookViewActivity.this,"Update complete!",Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(SupplierBookViewActivity.this,"Error",Toast.LENGTH_LONG).show();
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
            Intent intent = new Intent(SupplierBookViewActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        layout = (LinearLayout) findViewById(R.id.layout_sup_bookview);
        layout.requestFocus();
    }
}
