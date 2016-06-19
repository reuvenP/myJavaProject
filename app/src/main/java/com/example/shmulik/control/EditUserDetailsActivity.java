package com.example.shmulik.control;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shmulik.myjavaproject.R;

import entities.Customer;
import entities.Permission;
import entities.Supplier;
import entities.User;
import model.backend.Backend;
import model.backend.BackendFactory;
import model.backend.userSingleton;

// class to manage customer or supplier edit profile activity.
public class EditUserDetailsActivity extends AppCompatActivity {
    Backend backend;
    User currentUser;
    TextView email;
    TextView password;
    TextView rePassword;
    TextView name;
    TextView address;
    Button submit;
    Supplier supplier;
    Customer customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_details);
        backend = BackendFactory.getInstance(EditUserDetailsActivity.this); // get the current backend.
        currentUser = userSingleton.getInstance(); // get the current user.
        if (currentUser == null)
        {
            finish();
        }
        email = (TextView) findViewById(R.id.email_signup_edit);
        password = (TextView) findViewById(R.id.password_signup_edit);
        rePassword = (TextView) findViewById(R.id.password_reenter_signup_edit);
        name = (TextView) findViewById(R.id.name_signup_edit);
        address = (TextView) findViewById(R.id.address_signup_edit);
        submit = (Button) findViewById(R.id.email_sign_up_button_edit);

        if (currentUser.getPermission() == Permission.SUPPLIER) // if the user is supplier...
        {
            try {
                supplier = backend.getSupplierBySupplierID(currentUser.getUserID());
                customer = null;
                email.setText(currentUser.getMail());
                password.setText(currentUser.getPassword());
                rePassword.setText(currentUser.getPassword());
                name.setText(supplier.getName());
                address.setText(supplier.getAddress());
            }
            catch (Exception e) {
                Toast.makeText(EditUserDetailsActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                finish();
            }
        }
        else    // the user is customer...
        {
            try {
                customer = backend.getCustomerByCustomerID(currentUser.getUserID());
                supplier = null;
                email.setText(currentUser.getMail());
                password.setText(currentUser.getPassword());
                rePassword.setText(currentUser.getPassword());
                name.setText(customer.getName());
                address.setText(customer.getAddress());
            }
            catch (Exception e) {
                Toast.makeText(EditUserDetailsActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                finish();
            }
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // on click on "Update" button.
                if (!password.getText().toString().equals(rePassword.getText().toString())) { // if enter tow different passwords
                    Toast.makeText(EditUserDetailsActivity.this, "Password not match", Toast.LENGTH_LONG).show();
                    password.setText(currentUser.getPassword()); // reset the password.
                    rePassword.setText(currentUser.getPassword());
                    return;
                }

                if (!currentUser.getPassword().equals(password.getText())) // if the password was changed.
                {
                    try
                    {
                        currentUser.setPassword(password.getText().toString());
                        backend.updateUser(currentUser);
                    }
                    catch (Exception e) {
                        Toast.makeText(EditUserDetailsActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        return;
                    }
                }

                if (supplier != null) // the user is supplier.
                {
                    if (!supplier.getName().equals(name.getText()) || !supplier.getAddress().equals(address.getText())) // if the name or the address was changed.
                    {
                        try
                        {
                            supplier.setName(name.getText().toString());
                            supplier.setAddress(address.getText().toString());
                            backend.updateSupplier(supplier, supplier.getSupplierID());
                        }
                        catch (Exception e) {
                            Toast.makeText(EditUserDetailsActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                }

                else if (customer != null) // the user is customer.
                {
                    if (!customer.getName().equals(name.getText()) || !customer.getAddress().equals(address.getText())) // if the name or the address was changed.
                    {
                        try
                        {
                            customer.setName(name.getText().toString());
                            customer.setAddress(address.getText().toString());
                            backend.updateCustomer(customer, customer.getCustomerID());
                        }
                        catch (Exception e) {
                            Toast.makeText(EditUserDetailsActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                }
                Toast.makeText(EditUserDetailsActivity.this, "Updates Successfully", Toast.LENGTH_LONG).show();
                finish();
            }
        });
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
            Intent intent = new Intent(EditUserDetailsActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
