package com.example.shmulik.control;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.example.shmulik.myjavaproject.R;

public class WebViewActivity extends AppCompatActivity {
    WebView addBookView;
    WebView bookListView;
    WebView addUserView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        //addBookView = (WebView) findViewById(R.id.add_book_webview);
        //bookListView = (WebView) findViewById(R.id.book_list_webview);
        //addUserView = (WebView) findViewById(R.id.add_user_webview);
       // addBookView.loadUrl("http://plevinsk.vlab.jct.ac.il/form/addBook.html");
        //bookListView.loadUrl("http://plevinsk.vlab.jct.ac.il/form/BookList.php");
        //addUserView.loadUrl("http://plevinsk.vlab.jct.ac.il/form/addUser.html");
        Fragment fragment = new AddUserWebView();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.web_view_fragment, fragment);
        fragmentTransaction.commit();

    }
}
