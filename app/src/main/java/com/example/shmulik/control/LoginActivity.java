package com.example.shmulik.control;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shmulik.myjavaproject.R;

import entities.Permission;
import entities.User;
import model.backend.Backend;
import model.backend.BackendFactory;
import model.backend.userSingleton;

// class to manage login layout.
public class LoginActivity extends AppCompatActivity {

    AutoCompleteTextView mailTextView;
    EditText passwordTextView;
    Button loginButton;
    User currentUser;
    Backend backend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        currentUser = userSingleton.getInstance(); // get the current user if exist.
        mailTextView = (AutoCompleteTextView) findViewById(R.id.email);
        mailTextView.requestFocus();
        passwordTextView = (EditText) findViewById(R.id.password);
        loginButton = (Button) findViewById(R.id.email_sign_in_button);
        backend = BackendFactory.getInstance(LoginActivity.this);  // get the current backend.
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // on click go to relevant activity.

                if (mailTextView == null || passwordTextView == null || // if does not fill all the must fields.
                        mailTextView.getText().toString().equals("") || passwordTextView.getText().toString().equals(""))
                {
                    Toast.makeText(LoginActivity.this, "all fields must not be empty", Toast.LENGTH_LONG).show();
                }

                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(mailTextView.getText().toString()).matches()) // check for valid mail address.
                {
                    Toast.makeText(LoginActivity.this, "enter a valid email address", Toast.LENGTH_LONG).show();
                    mailTextView.setText(""); // reset values.
                    passwordTextView.setText(""); // reset values.
                    mailTextView.requestFocus();
                    return;
                }

                else // if all fields are good...
                {
                    User user = backend.login(mailTextView.getText().toString(), passwordTextView.getText().toString()); // get the user from the DBA.(by mail and password.)
                    if (user == null) { // if the user does not exist.
                        mailTextView.setText(""); // reset values.
                        passwordTextView.setText(""); // reset values.
                        Toast.makeText(LoginActivity.this, "login failed", Toast.LENGTH_LONG).show();
                    }

                    else // if the user exist.
                    {
                        userSingleton.setInstance(user); // set the user singleton.
                        currentUser = userSingleton.getInstance(); // get the user singleton.
                        SharedPreferences sharedPreferences = getSharedPreferences("userIDPre", Context.MODE_PRIVATE); // key & value pair.
                        sharedPreferences.edit().putInt("userID", currentUser.getUserID()).apply(); // set the user id in the sharedPreferences.
                        Toast.makeText(LoginActivity.this, "login success", Toast.LENGTH_LONG).show();

                        if (currentUser.getPermission() == Permission.CUSTOMER) // if the user is customer.
                        {
                            Intent intent = new Intent(LoginActivity.this, CustomerMainActivity.class); // load customer main activity.
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // clear current Activity stack and launch a new Activity.
                            startActivity(intent);
                        }

                        else if (currentUser.getPermission() == Permission.SUPPLIER) // if the user is supplier.
                        {
                            Intent intent = new Intent(LoginActivity.this, SupplierMainActivity.class); // load supplier main activity.
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // clear current Activity stack and launch a new Activity.
                            startActivity(intent);
                        }
                    }
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (currentUser.getUserID() != -1) // if exist user.
        {
            if (currentUser.getPermission() == Permission.CUSTOMER) // if the user is customer.
            {
                Intent intent = new Intent(LoginActivity.this, CustomerMainActivity.class); // load customer main activity.
                startActivity(intent);
                finish();
            }

            else if (currentUser.getPermission() == Permission.SUPPLIER) // if the user is supplier.
            {
                Intent intent = new Intent(LoginActivity.this, SupplierMainActivity.class); // load supplier main activity.
                startActivity(intent);
                finish();
            }
        }
    }
}
