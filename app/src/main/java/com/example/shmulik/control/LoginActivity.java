package com.example.shmulik.control;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shmulik.myjavaproject.R;

import entities.User;
import model.backend.Backend;
import model.backend.BackendFactory;

public class LoginActivity extends AppCompatActivity {

    AutoCompleteTextView mailTextView;
    EditText passwordTextView;
    Button loginButton;
    Button signupButton;
    User currentUser;
    Backend backend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mailTextView = (AutoCompleteTextView) findViewById(R.id.email);
        passwordTextView = (EditText) findViewById(R.id.password);
        loginButton = (Button) findViewById(R.id.email_sign_in_button);
        signupButton = (Button) findViewById(R.id.email_sign_up_button);
        backend = BackendFactory.getInstance();
        try {
            currentUser = backend.getUserList().get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mailTextView.setText(currentUser.getMail());
                passwordTextView.setText(currentUser.getPassword());
            }
        });
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentUser.setPassword(passwordTextView.getText().toString());
                Toast.makeText(LoginActivity.this,currentUser.getPassword(),Toast.LENGTH_LONG).show();
            }
        });
    }
}
