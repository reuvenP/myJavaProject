package com.example.shmulik.control;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shmulik.myjavaproject.R;

import java.util.ArrayList;
import java.util.Date;

import entities.BookSupplier;
import entities.BooksForOrder;
import entities.Order;
import entities.User;
import model.backend.Backend;
import model.backend.BackendFactory;
import model.backend.userSingleton;

// class to manage cart activity.
public class CartActivity extends AppCompatActivity {
    Backend backend;
    User currentUser;
    ListView listView;
    Button clear;
    TextView total;
    TextView sumOfItems;
    Button submit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        backend = BackendFactory.getInstance(CartActivity.this); // get the current backend.
        currentUser = userSingleton.getInstance(); // get the current user.
        listView = (ListView) findViewById(R.id.cart_LV);
        total = (TextView) findViewById(R.id.cart_total);
        sumOfItems = (TextView) findViewById(R.id.cart_sum_of_item);
        clear = (Button) findViewById(R.id.cart_clear_BTN);

        clear.setOnClickListener(new View.OnClickListener() { // on click on "clear" button.
            @Override
            public void onClick(View v) {
                emptyCart();
            }
        }); // on click on "clear" button.
        CartAdapter adapter = new CartAdapter(this, currentUser.getOrder());
        listView.setAdapter(adapter);
        total.setText("Total: " + Float.toString(totalForOrder()));
        sumOfItems.setText("    Sum of items: " + currentUser.getOrder().size());
        submit = (Button) findViewById(R.id.cart_submit_BTN);
        submit.setOnClickListener(new View.OnClickListener() { // on click on "submit" button.
            @Override
            public void onClick(View v) {
                submit();
            }
        }); // on click on "submit" button.
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { // create the menu.
        getMenuInflater().inflate(R.menu.menu_logout, menu);
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
            Intent intent = new Intent(CartActivity.this, MainActivity.class); // go to main activity.
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // clear current Activity stack and launch a new Activity.
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void removeFromCard(int position) // on click on "remove" button. (called from adapter).
    {
        try
        {
            BookSupplier bookSupplier = currentUser.getOrder().get(position); // get the bookSupplier to remove from cart.
            bookSupplier.setAmount(bookSupplier.getAmount()+1); // return the amount back.

            for (BookSupplier bookSupplier1 : currentUser.getOrder()) // return the amount back to each same bookSupplier in cart.
            {
                if (bookSupplier.getBook().getBookID() == bookSupplier1.getBook().getBookID() &&
                        bookSupplier.getSupplier().getSupplierID() == bookSupplier1.getSupplier().getSupplierID())
                    bookSupplier1.setAmount(bookSupplier.getAmount());
            }
            currentUser.getOrder().remove(position); // remove bookSupplier from cart.
            backend.updateBookSupplier(bookSupplier);
            backend.updateUser(currentUser);
            refreshView();
        }
        catch (Exception e) {
            Toast.makeText(CartActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }
    }

    void refreshView()
    {
        CartAdapter adapter = new CartAdapter(this, currentUser.getOrder());
        listView.setAdapter(adapter);
        total.setText("Total: " + Float.toString(totalForOrder()));
        sumOfItems.setText("    Sum of items: " + currentUser.getOrder().size());
    }

    void emptyCart() // on click on "clear" button.
    {
        try
        {
            for (BookSupplier bookSupplier : currentUser.getOrder())
            {
                bookSupplier.setAmount(bookSupplier.getAmount()+1); // return the amount back.
                for (BookSupplier bookSupplier1 : currentUser.getOrder()) // return the amount back to each same bookSupplier in cart.
                {
                    if (bookSupplier.getBook().getBookID() == bookSupplier1.getBook().getBookID() &&
                            bookSupplier.getSupplier().getSupplierID() == bookSupplier1.getSupplier().getSupplierID())
                        bookSupplier1.setAmount(bookSupplier.getAmount());
                }
                backend.updateBookSupplier(bookSupplier);
            }
            currentUser.getOrder().clear(); // remove all bookSupplier from cart.
            backend.updateUser(currentUser);
            refreshView();
        }
        catch (Exception e) {
            Toast.makeText(CartActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }
    }

    float totalForOrder()
    {
        if (currentUser == null || currentUser.getOrder() == null)
            return 0;
        float sum = 0;
        for (BookSupplier bookSupplier : currentUser.getOrder())
        {
            sum += bookSupplier.getPrice();
        }
        return sum;
    }

    void submit() // on click on "submit" button. (called from listener)
    {
        try
        {
            if (currentUser.getOrder().size() == 0) // make sure that there is some order in cart
                return;
            ArrayList<BooksForOrder> booksForOrders = new ArrayList<>();
            /*for (BookSupplier bookSupplier : currentUser.getOrder())
            {
                booksForOrders.add(new BooksForOrder(bookSupplier,1,true));
            }*/
            boolean found;
            for (BookSupplier bookSupplier : currentUser.getOrder()) // check if ordered this book from same supplier.
            {
                found = false;
                for (BooksForOrder booksForOrder : booksForOrders)
                {
                    if (bookSupplier.getBook().getBookID() == booksForOrder.getBookSupplier().getBook().getBookID() &&
                            bookSupplier.getSupplier().getSupplierID() == booksForOrder.getBookSupplier().getSupplier().getSupplierID()) {
                        booksForOrder.setSumOfBooks(booksForOrder.getSumOfBooks() + 1);
                        found = true;
                        break;
                    }
                }
                if (!found)
                    booksForOrders.add(new BooksForOrder(bookSupplier,1,true));
            }
            Date now = new Date();
            Order order = new Order(backend.getCustomerByCustomerID(currentUser.getUserID()),booksForOrders,now,totalForOrder(),true);
            int id = backend.addOrder(order);
            order.setOrderID(id);
            Toast.makeText(CartActivity.this, "submitted",Toast.LENGTH_LONG).show();
            currentUser.getOrder().clear(); // clear the cart.
            backend.updateUser(currentUser);
            //backend.updateOrder(order,order.getOrderID());
            backend.submit(order); // send the mail to the seller.
            refreshView();

        }
        catch (Exception e) {
            Toast.makeText(CartActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }
    }


}
