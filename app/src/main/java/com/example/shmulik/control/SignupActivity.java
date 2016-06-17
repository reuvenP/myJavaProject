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
import model.backend.userSingleton;

// class to manage sign up activity.
public class SignupActivity extends AppCompatActivity {
    DatePicker birthdayDP;
    AutoCompleteTextView mailTV;
    EditText passwordTV;
    EditText passwordReenterTV;
    AutoCompleteTextView nameTV;
    AutoCompleteTextView addressTV;
    Switch genderSW;
    RadioButton customerRB;
    RadioButton supplierRB;
    Button signUpBTN;
    Backend backend;
    User currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        backend = BackendFactory.getInstance(SignupActivity.this);
        currentUser = userSingleton.getInstance(); // get the current user if exist.
        birthdayDP = (DatePicker) findViewById(R.id.birthday_signup);
        birthdayDP.setCalendarViewShown(false);
        Date now = new Date();
        birthdayDP.setMaxDate(now.getTime());
        birthdayDP.updateDate(1990, 0, 1);
        mailTV = (AutoCompleteTextView) findViewById(R.id.email_signup);
        mailTV.requestFocus();
        passwordTV = (EditText) findViewById(R.id.password_signup);
        passwordReenterTV = (EditText) findViewById(R.id.password_reenter_signup);
        nameTV = (AutoCompleteTextView) findViewById(R.id.name_signup);
        addressTV = (AutoCompleteTextView) findViewById(R.id.address_signup);
        genderSW = (Switch) findViewById(R.id.maleFemaleSwitch);
        customerRB = (RadioButton) findViewById(R.id.customerRB);
        supplierRB = (RadioButton) findViewById(R.id.supplierRB);
        signUpBTN = (Button) findViewById(R.id.email_sign_up_button);
        signUpBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }


    void register() // on click on register button.
    {
        if (mailTV == null || passwordTV == null || passwordReenterTV == null || // if does not fill all the must fields.
                nameTV == null || addressTV == null ||
                mailTV.getText().toString().equals("") || passwordTV.getText().toString().equals("") ||
                passwordReenterTV.getText().toString().equals("") ||
                nameTV.getText().toString().equals("") || addressTV.getText().toString().equals(""))
        {
            Toast.makeText(SignupActivity.this, "all fields must not be empty", Toast.LENGTH_LONG).show();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(mailTV.getText().toString()).matches()) // check for valid mail address.
        {
            Toast.makeText(SignupActivity.this, "enter a valid email address", Toast.LENGTH_LONG).show();
            mailTV.setText("");
            mailTV.requestFocus();
            return;
        }

        if (!passwordTV.getText().toString().equals(passwordReenterTV.getText().toString()))  // check for same password in 2 different TV.
        {
            Toast.makeText(SignupActivity.this, "passwords does not matching", Toast.LENGTH_LONG).show();
            passwordTV.setText("");
            passwordReenterTV.setText("");
            return;
        }

        // if all fields are good... get the values.
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

        if (customerRB.isChecked()) // need to create new customer.
        {
            try {
                int ID = backend.addCustomer(new Customer(CustomerType.REGULAR,name,birthday,gender,address,null));
                User user = new User(Permission.CUSTOMER,email, password, ID);
                backend.addUser(user);
                userSingleton.setInstance(user); // set the user singleton.
                currentUser = userSingleton.getInstance(); // get the user singleton.
                SharedPreferences sharedPreferences = getSharedPreferences("userIDPre", Context.MODE_PRIVATE); // key & value pair.
                sharedPreferences.edit().putInt("userID", currentUser.getUserID()).apply(); // set the user id in the sharedPreferences.
                Toast.makeText(SignupActivity.this, "registered successfully", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(SignupActivity.this, CustomerMainActivity.class); // load customer main activity.
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // clear current Activity stack and launch a new Activity.
                startActivity(intent);
            }
            catch (Exception e) {
                Toast.makeText(SignupActivity.this, "unsuccessful registered", Toast.LENGTH_LONG).show();
                return;
            }
        }

        else if(supplierRB.isChecked()) // need to create new supplier.
        {
            try
            {
                int ID = backend.addSupplier(new Supplier(Rating.THREE,name,birthday,gender,address,null));
                User user = new User(Permission.SUPPLIER, email, password, ID);
                backend.addUser(user);
                userSingleton.setInstance(user); // set the user singleton.
                currentUser = userSingleton.getInstance(); // get the user singleton.
                SharedPreferences sharedPreferences = getSharedPreferences("userIDPre", Context.MODE_PRIVATE); // key & value pair.
                sharedPreferences.edit().putInt("userID", currentUser.getUserID()).apply(); // set the user id in the sharedPreferences.
                Toast.makeText(SignupActivity.this, "registered successfully", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(SignupActivity.this, SupplierMainActivity.class); // load supplier main activity.
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // clear current Activity stack and launch a new Activity.
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
            if (currentUser.getPermission() == Permission.CUSTOMER) // if the user is customer.
            {
                Intent intent = new Intent(SignupActivity.this, CustomerMainActivity.class); // load customer main activity.
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // clear current Activity stack and launch a new Activity.
                startActivity(intent);
            }

            else if (currentUser.getPermission() == Permission.SUPPLIER) // if the user is supplier.
            {
                Intent intent = new Intent(SignupActivity.this, SupplierMainActivity.class); // load supplier main activity.
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // clear current Activity stack and launch a new Activity.
                startActivity(intent);
            }
        }
    }
}
