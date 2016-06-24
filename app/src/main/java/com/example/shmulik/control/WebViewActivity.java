package com.example.shmulik.control;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import com.example.shmulik.myjavaproject.R;

public class WebViewActivity extends AppCompatActivity {
    Fragment fragment;
    Button addBookBTN;
    Button addUserBTN;
    Button bookListBTN;
    static int state = 1;//state 1 - add book. state 2 - add user. state 3 - book list

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        addBookBTN = (Button) findViewById(R.id.add_book_webview_btn);
        addUserBTN = (Button) findViewById(R.id.add_user_webview_btn);
        bookListBTN = (Button) findViewById(R.id.book_list_webview_btn);
        addUserBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(state == 2)
                    return;
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                fragment = new AddUserWebView();
                transaction.replace(R.id.fragment_container, fragment);
                transaction.commit();
                state = 2;
            }
        });
        addBookBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (state == 1)
                    return;
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                fragment = new AddBookWebView();
                transaction.replace(R.id.fragment_container, fragment);
                transaction.commit();
                state = 1;
            }
        });
        bookListBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (state == 3)
                    return;
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                fragment = new BookListWebView();
                transaction.replace(R.id.fragment_container, fragment);
                transaction.commit();
                state = 3;
            }
        });
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        fragment = new AddBookWebView();
        transaction.add(R.id.fragment_container,fragment);
        transaction.commit();
    }
}
