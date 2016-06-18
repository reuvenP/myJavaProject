package com.example.shmulik.control;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.shmulik.myjavaproject.R;

import java.util.ArrayList;
import java.util.List;

import entities.Book;


public class BooksAdapter extends ArrayAdapter<Book> {
    public BooksAdapter(Context context, ArrayList<Book> book) {
        super(context, 0,  book);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_book, parent, false);
        }
        Book book = getItem(position);
        TextView book_id = (TextView) convertView.findViewById(R.id.book_id_row);
        TextView book_name = (TextView)convertView.findViewById(R.id.book_name_row);
        TextView book_category = (TextView) convertView.findViewById(R.id.book_category_row);
        book_id.setText("Serial Number: " + Integer.toString(book.getBookID()));
        book_name.setText(book.getTitle());
        book_category.setText("Category: " + book.getCategory().toString());
        if ((position % 2) == 0) {
            convertView.setBackgroundResource(R.color.colorEven);
        }
        else {
            convertView.setBackgroundResource(R.color.colorOdd);
        }
        return convertView;
    }
}
