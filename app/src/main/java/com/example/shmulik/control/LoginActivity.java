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
import model.backend.UserSingltone;

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
        currentUser = UserSingltone.getInstance();
        mailTextView = (AutoCompleteTextView) findViewById(R.id.email);
        passwordTextView = (EditText) findViewById(R.id.password);
        loginButton = (Button) findViewById(R.id.email_sign_in_button);
        backend = BackendFactory.getInstance(LoginActivity.this);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mailTextView == null || passwordTextView == null ||
                        mailTextView.getText().toString().equals("") || passwordTextView.getText().toString().equals(""))
                {
                    Toast.makeText(LoginActivity.this, "all fields must not be empty", Toast.LENGTH_LONG).show();
                }
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(mailTextView.getText().toString()).matches())
                {
                    Toast.makeText(LoginActivity.this, "enter a valid email address", Toast.LENGTH_LONG).show();
                    mailTextView.setText("");
                    passwordTextView.setText("");
                    mailTextView.requestFocus();
                    return;
                }
                else
                {
                    User user = backend.login(mailTextView.getText().toString(), passwordTextView.getText().toString());
                    if (user == null) {
                        mailTextView.setText("");
                        passwordTextView.setText("");
                        Toast.makeText(LoginActivity.this, "login failed", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        UserSingltone.setInstance(user);
                        currentUser = UserSingltone.getInstance();
                        SharedPreferences sharedPreferences = getSharedPreferences("userIDPre", Context.MODE_PRIVATE);
                        sharedPreferences.edit().putInt("userID", currentUser.getUserID()).apply();
                        Toast.makeText(LoginActivity.this, "login success", Toast.LENGTH_LONG).show();
                        if (currentUser.getPermission() == Permission.CUSTOMER)
                        {
                            Intent intent = new Intent(LoginActivity.this, CustomerMainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                        else if (currentUser.getPermission() == Permission.SUPPLIER)
                        {
                            Intent intent = new Intent(LoginActivity.this, SupplierMainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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
        if (currentUser.getUserID() != -1)
        {
            if (currentUser.getPermission() == Permission.CUSTOMER)
            {
                Intent intent = new Intent(LoginActivity.this, CustomerMainActivity.class);
                startActivity(intent);
                finish();
            }
            else if (currentUser.getPermission() == Permission.SUPPLIER)
            {

            }
        }
    }
}
