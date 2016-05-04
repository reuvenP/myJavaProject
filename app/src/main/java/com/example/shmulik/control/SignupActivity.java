package com.example.shmulik.control;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.DatePicker;

import com.example.shmulik.myjavaproject.R;

import java.util.Date;

public class SignupActivity extends AppCompatActivity {
    DatePicker birthdayDP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        birthdayDP = (DatePicker) findViewById(R.id.birthday_signup);
        birthdayDP.setCalendarViewShown(false);
        Date now = new Date();
        birthdayDP.setMaxDate(now.getTime());
    }
}
