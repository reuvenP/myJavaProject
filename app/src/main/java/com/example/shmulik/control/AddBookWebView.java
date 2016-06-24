package com.example.shmulik.control;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.example.shmulik.myjavaproject.R;

import java.util.zip.Inflater;

/**
 * Created by reuvenp on 6/20/2016.
 */
public class AddBookWebView extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_book, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        WebView webView = (WebView) getView().findViewById(R.id.add_book_webview);
        webView.loadUrl("http://plevinsk.vlab.jct.ac.il/form/addBook.html");
    }
}