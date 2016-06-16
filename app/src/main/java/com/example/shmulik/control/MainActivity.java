package com.example.shmulik.control;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.shmulik.myjavaproject.R;

import entities.Permission;
import entities.User;
import model.backend.Backend;
import model.backend.BackendFactory;
import model.backend.userSingleton;


// class to manage start activity (main layout).
public class MainActivity extends AppCompatActivity {

    User currentUser;
    Backend backend;
    Button loginBT;
    Button signUpBT;
    SharedPreferences sharedPreferences; // key & value pair.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        backend = BackendFactory.getInstance(MainActivity.this); // get the current backend.
        currentUser = userSingleton.getInstance(); // get the current user if exist.

        /*new AsyncTask<Void, Void, Void>()
        {
            @Override
            protected Void doInBackground(Void... params) {
                Mail m = new Mail("javaproject2016sr@gmail.com", "shmulikreuven");
                String[] toArr = {"reuvenhn@gmail.com"}; // This is an array, you can add more emails, just separate them with a coma
                m.setTo(toArr); // load array to setTo function
                m.setFrom("javaproject2016sr@gmail.com"); // who is sending the email
                m.setSubject("New Order [#7689]");
                m.setBody("Hi Shmulik! You have a new Order");

                try {
                    //m.addAttachment("/sdcard/myPicture.jpg");  // path to file you want to attach
                    if(m.send()) {
                        // success
                        //Toast.makeText(MainActivity.this, "Email was sent successfully.", Toast.LENGTH_LONG).show();
                    } else {
                        // failure
                       // Toast.makeText(MainActivity.this, "Email was not sent.", Toast.LENGTH_LONG).show();
                    }
                } catch(Exception e) {
                    // some other problem
                    //Toast.makeText(MainActivity.this, "There was a problem sending the email.", Toast.LENGTH_LONG).show();
                }
                return null;
            }
        }.execute();*/
        try
        {
            /*
           //int id =  backend.addCustomer(new Customer(CustomerType.REGULAR,"avi", new Date(), Gender.MALE, "bialik 21",null));
            //backend.addUser(new User(Permission.CUSTOMER,"a@a.com","a",id));
            //id = backend.addSupplier(new Supplier(Rating.FIVE,"vivi",new Date(),Gender.FEMALE,"herzl 44",null));
            //backend.addUser(new User(Permission.SUPPLIER,"v@v.com","v",id));
            ArrayList<Customer> customerArrayList = backend.getCustomerList();
            ArrayList<Book> bookArrayList = backend.getBookList();
            ArrayList<Supplier> supplierArrayList = backend.getSupplierList();
            Book book = backend.getBookByBookID(3);
            Customer customer = backend.getCustomerByCustomerID(13);
            Supplier supplier = backend.getSupplierBySupplierID(14);
            User user = backend.getUserByID(13);
            ArrayList<User> userArrayList = backend.getUserList();
            //backend.updateBook(book, book.getBookID());
            //backend.addBookSupplier(new BookSupplier(supplierArrayList.get(0),bookArrayList.get(0),(float)98.5,10));
            //backend.deleteBook(bookArrayList.get(2).getBookID());
            //int id = backend.addBook(new Book("r",6,"t",7,Category.Anthology));
            */
           // ArrayList<BookSupplier> bookSupplierArrayList = backend.getBookSupplierBySupplierID(14);
           // BookSupplier bookSupplier = backend.getBookSupplierBySupplierIDAndByBookID(14,3);
           // Toast.makeText(MainActivity.this, bookSupplier.getBook().getTitle(),Toast.LENGTH_LONG).show();
        }
        catch (Exception e) {e.printStackTrace();}


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        loginBT = (Button) findViewById(R.id.login_mainBT);
        signUpBT = (Button) findViewById(R.id.signup_mainBT);

        signUpBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // on click go to sign up activity.
                Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
        loginBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // on click go to login activity.
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }



    @Override
    protected void onResume() {
        super.onResume();
        sharedPreferences = getSharedPreferences("userIDPre", Context.MODE_PRIVATE);
        int userID = sharedPreferences.getInt("userID", -1); // get user id if exist else return -1.
        if (userID != -1) // user id exist.
        {
            try {
                User user = backend.getUserByID(userID); // get the user.
                userSingleton.setInstance(user); // set the user singleton.
                currentUser = userSingleton.getInstance(); // get the user singleton.
                sharedPreferences.edit().putInt("userID", currentUser.getUserID()).apply(); // set the user id in the sharedPreferences.

                if (currentUser.getPermission() == Permission.CUSTOMER) // if the user is customer.
                {
                    Intent intent = new Intent(MainActivity.this, CustomerMainActivity.class); // load customer main activity.
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // clear current Activity stack and launch a new Activity.
                    startActivity(intent);
                }

                else if (currentUser.getPermission() == Permission.SUPPLIER) // if the user is supplier.
                {
                    Intent intent = new Intent(MainActivity.this, SupplierMainActivity.class); // load supplier main activity.
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // clear current Activity stack and launch a new Activity.
                    startActivity(intent);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Exit the store?")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            finish();
                        } catch (Exception e) {

                        }
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                })
                .show();
    }

}
