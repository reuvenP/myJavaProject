package com.example.shmulik.control;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.shmulik.myjavaproject.R;

import java.util.Calendar;
import java.util.Date;

import entities.Customer;
import entities.CustomerType;
import entities.Gender;
import entities.Permission;
import entities.Rating;
import entities.Supplier;
import entities.User;
import model.backend.Backend;
import model.backend.BackendFactory;
import model.backend.UserSingltone;

public class SignupActivity extends AppCompatActivity {
    DatePicker birthdayDP;
    AutoCompleteTextView mailTV;
    EditText passwordTV;
    EditText passwordReenterTV;
    AutoCompleteTextView nameTV;
    AutoCompleteTextView addressTV;
    Switch genderSW;
    RadioButton customrRB;
    RadioButton supplierRB;
    Button signupBTN;
    Backend backend;
    User currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        backend = BackendFactory.getInstance();
        currentUser = UserSingltone.getInstance();
        birthdayDP = (DatePicker) findViewById(R.id.birthday_signup);
        birthdayDP.setCalendarViewShown(false);
        Date now = new Date();
        birthdayDP.setMaxDate(now.getTime());
        mailTV = (AutoCompleteTextView) findViewById(R.id.email_signup);
        passwordTV = (EditText) findViewById(R.id.password_signup);
        passwordReenterTV = (EditText) findViewById(R.id.password_reenter_signup);
        nameTV = (AutoCompleteTextView) findViewById(R.id.name_signup);
        addressTV = (AutoCompleteTextView) findViewById(R.id.address_signup);
        genderSW = (Switch) findViewById(R.id.maleFemaleSwitch);
        customrRB = (RadioButton) findViewById(R.id.customerRB);
        supplierRB = (RadioButton) findViewById(R.id.supplierRB);
        signupBTN = (Button) findViewById(R.id.email_sign_up_button);
        signupBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }
    void register()
    {
        if (mailTV == null || passwordTV == null || passwordReenterTV == null ||
                nameTV == null || addressTV == null)
        {
            Toast.makeText(SignupActivity.this, "all fields must not be empty", Toast.LENGTH_LONG).show();
            return;
        }
        if (!passwordTV.getText().toString().equals(passwordReenterTV.getText().toString()))
        {
            Toast.makeText(SignupActivity.this, "passwords does not matching", Toast.LENGTH_LONG).show();
            passwordTV.setText("");
            passwordReenterTV.setText("");
            return;
        }
        String email = mailTV.getText().toString();
        String password = passwordTV.getText().toString();
        String name = nameTV.getText().toString();
        String address = addressTV.getText().toString();
        Gender gender;
        if (genderSW.isChecked())
            gender = Gender.MALE;
        else
            gender = Gender.FEMALE;
        Calendar calendar = Calendar.getInstance();
        calendar.set(birthdayDP.getYear() - 1900, birthdayDP.getMonth(), birthdayDP.getDayOfMonth());
        Date birthday = calendar.getTime();
        if (customrRB.isChecked())
        {
            try {
                int ID = backend.addCustomer(new Customer(CustomerType.REGULAR,name,birthday,gender,address,null));
                User user = new User(Permission.CUSTOMER,email, password, ID);
                backend.addUser(user);
                UserSingltone.setInstance(user);
                currentUser = UserSingltone.getInstance();
                SharedPreferences sharedPreferences = getSharedPreferences("userIDPre", Context.MODE_PRIVATE);
                sharedPreferences.edit().putInt("userID", currentUser.getUserID()).apply();
                Toast.makeText(SignupActivity.this, "registered successfully", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(SignupActivity.this, CustomerMainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(SignupActivity.this, "unsuccessful registered", Toast.LENGTH_LONG).show();
                return;
            }
        }
        else if(supplierRB.isChecked())
        {
            try
            {
                int ID = backend.addSupplier(new Supplier(Rating.THREE,name,birthday,gender,address,null));
                User user = new User(Permission.SUPPLIER, email, password, ID);
                backend.addUser(user);
                UserSingltone.setInstance(user);
                currentUser = UserSingltone.getInstance();
                SharedPreferences sharedPreferences = getSharedPreferences("userIDPre", Context.MODE_PRIVATE);
                sharedPreferences.edit().putInt("userID", currentUser.getUserID()).apply();
                Toast.makeText(SignupActivity.this, "registered successfully", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(SignupActivity.this, CustomerMainActivity.class);//TODO new class
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
            catch (Exception e) {
                Toast.makeText(SignupActivity.this, "unsuccessful registered", Toast.LENGTH_LONG).show();
                return;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (currentUser.getUserID() != -1)
        {
            if (currentUser.getPermission() == Permission.CUSTOMER)
            {
                Intent intent = new Intent(SignupActivity.this, CustomerMainActivity.class);
                startActivity(intent);
                finish();
            }
            else if (currentUser.getPermission() == Permission.SUPPLIER)
            {

            }
        }
    }
}
