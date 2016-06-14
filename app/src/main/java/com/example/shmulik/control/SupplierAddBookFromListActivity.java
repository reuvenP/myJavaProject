package com.example.shmulik.control;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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

public class SupplierAddBookFromListActivity extends AppCompatActivity {
    ListView listView;
    Backend backend;
    User currentUser;
    ArrayList<Book> bookArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_add_book_from_list);
        listView = (ListView) findViewById(R.id.add_book_from_list_LV);
        try {
            backend = BackendFactory.getInstance(SupplierAddBookFromListActivity.this);
            currentUser = UserSingltone.getInstance();
            bookArrayList = backend.getBookList();
            BooksAddAdapter adapter = new BooksAddAdapter(this, bookArrayList);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    final Dialog dialog = new Dialog(SupplierAddBookFromListActivity.this);
                    dialog.setContentView(R.layout.dialog_add_price_amount);
                    dialog.setTitle("Confirm Adding Book:");
                    Button submit = (Button) dialog.findViewById(R.id.submit_add_from_list);
                    submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Book book = bookArrayList.get(position);
                            try {
                                BookSupplier bookSupplier = backend.getBookSupplierBySupplierIDAndByBookID(currentUser.getUserID(),book.getBookID());
                                if (bookSupplier != null)
                                {
                                    Toast.makeText(SupplierAddBookFromListActivity.this, "You have this book already", Toast.LENGTH_LONG).show();
                                    dialog.dismiss();
                                }
                                else
                                {
                                    TextView price = (TextView)dialog.findViewById(R.id.price_add_from_list);
                                    TextView amount = (TextView)dialog.findViewById(R.id.amount_add_from_list);
                                    if (amount == null || price == null || amount.getText().equals("") || price.getText().equals(""))
                                    {
                                        Toast.makeText(SupplierAddBookFromListActivity.this, "Fill all the fields", Toast.LENGTH_LONG).show();
                                    }
                                    else
                                    {
                                        backend.addBookSupplier(new BookSupplier(backend.getSupplierBySupplierID(currentUser.getUserID()),book, Float.parseFloat(price.getText().toString()), Integer.parseInt(amount.getText().toString())));
                                        dialog.dismiss();
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                dialog.dismiss();
                            }
                        }
                    });
                    Button cancel = (Button)dialog.findViewById(R.id.cancel_add_from_list);
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
            });
        }
        catch (Exception e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
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
            Intent intent = new Intent(SupplierAddBookFromListActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
