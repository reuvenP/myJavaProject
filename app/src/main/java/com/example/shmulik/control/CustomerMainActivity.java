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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.shmulik.myjavaproject.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import entities.Book;
import entities.Category;
import entities.User;
import model.backend.Backend;
import model.backend.BackendFactory;
import model.backend.userSingleton;

// class to manage customer main activity.
public class CustomerMainActivity extends AppCompatActivity {
    Backend backend;
    ListView customerLV;
    ArrayList<Book> bookArrayList;
    public Book bookToShow = null;
    Spinner categorySpinner;
    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_main);
        customerLV = (ListView) findViewById(R.id.customer_LV);
        categorySpinner = (Spinner) findViewById(R.id.category_spinner);
        try {
            backend = BackendFactory.getInstance(CustomerMainActivity.this); // get the current backend.
            currentUser = userSingleton.getInstance(); // get the current user.
        }
        catch (Exception e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            finish();
        }

        setCustomerLV();
        customerLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) { // on click on book from the list.
                bookToShow = (Book) customerLV.getItemAtPosition(position);
                Intent intent = new Intent(CustomerMainActivity.this, CustomerBookViewActivity.class); // go to CustomerBookViewActivity (to add to cart).
                intent.putExtra("bookID", bookToShow.getBookID());
                startActivity(intent);
            }
        });

        List<Category> categoryList = Arrays.asList(Category.values());
        final ArrayList<String> list = new ArrayList<>();
        list.add("All");
        for (int i = 0; i < categoryList.size();i++) // create list of categories including "All".
        {
            list.add(categoryList.get(i).toString());
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, list);
        categorySpinner.setAdapter(spinnerAdapter);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // act by the category in the spinner.
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) // "All" category
                    setCustomerLV();
                else
                    setCustomerLV(Category.valueOf(list.get(position))); // specific category.
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    void setCustomerLV(){ // set the view of all categories.
        try {
            bookArrayList = backend.getBookList();
            BooksAdapter adapter = new BooksAdapter(this, bookArrayList);
            customerLV.setAdapter(adapter);
        } catch (Exception e) {
            finish();
        }
    }

    void setCustomerLV(Category category){ // set the view of specific categories.
        try {
            bookArrayList = backend.getBookListByCategory(category);
            BooksAdapter adapter = new BooksAdapter(this, bookArrayList);
            customerLV.setAdapter(adapter);
        } catch (Exception e) {
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { // create the menu.
        getMenuInflater().inflate(R.menu.menu_logout_cart, menu);
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
            Intent intent = new Intent(CustomerMainActivity.this, MainActivity.class); // go to main activity.
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // clear current Activity stack and launch a new Activity.
            startActivity(intent);
            return true;
        }

        else if (id == R.id.action_cart) // choose to go to cart.
        {
            Intent intent = new Intent(CustomerMainActivity.this, CartActivity.class); // go to cart activity.
            startActivity(intent);
        }

        else if (id == R.id.action_edit_details) // choose to edit profile
        {
            Intent intent = new Intent(CustomerMainActivity.this, EditUserDetailsActivity.class);
            startActivity(intent);
        }

        else if (id == R.id.action_my_orders) // choose to see old orders.
        {
            Intent intent = new Intent(CustomerMainActivity.this, MyOrdersActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (categorySpinner == null)
            setCustomerLV();
        else
        {
            if (categorySpinner.getSelectedItem().toString().equals("All"))
                setCustomerLV();
            else
            {
                setCustomerLV(Category.valueOf(categorySpinner.getSelectedItem().toString()));
            }
        }
    }

//    @Override
//    public void onBackPressed() {
//        new AlertDialog.Builder(CustomerMainActivity.this)
//                .setTitle("Exit the store?")
//                .setMessage("Are you sure you want to exit?")
//                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) { // choose yes...
//                        try {
//                            finish();
//                        } catch (Exception e) {
//
//                        }
//                    }
//                })
//                .setNegativeButton("NO", new DialogInterface.OnClickListener() { // choose no...
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        return;
//                    }
//                })
//                .show();
//    }
}
