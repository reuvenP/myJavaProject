package com.example.shmulik.control;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.example.shmulik.myjavaproject.R;

/**
 * Created by reuvenp on 6/24/2016.
 */
public class AddBookWebView extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_book_webview,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        WebView webView = (WebView) getView().findViewById(R.id.add_book_wv);
        if (webView != null)
            webView.loadUrl("http://plevinsk.vlab.jct.ac.il/form/addBook.html");
    }
}
