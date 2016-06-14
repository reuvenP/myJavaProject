package com.example.shmulik.control;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.shmulik.myjavaproject.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

import entities.Book;

/**
 * Created by reuvenp on 5/4/2016.
 */
public class BooksAddAdapter extends ArrayAdapter<Book> {
    public BooksAddAdapter(Context context, ArrayList<Book> book) {
        super(context, 0,  book);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_book_add, parent, false);
        }
        Book book = getItem(position);
        TextView book_id = (TextView) convertView.findViewById(R.id.book_id_row_add);
        TextView book_name = (TextView)convertView.findViewById(R.id.book_name_row_add);
        TextView book_category = (TextView) convertView.findViewById(R.id.book_category_row_add);
        TextView book_pages = (TextView) convertView.findViewById(R.id.book_pages_row_add);
        TextView book_year = (TextView) convertView.findViewById(R.id.book_year_row_add);
        TextView book_author = (TextView) convertView.findViewById(R.id.book_author_row_add);
        book_id.setText("Serial Number: " + Integer.toString(book.getBookID()));
        book_name.setText(book.getTitle());
        book_category.setText("Category: " + book.getCategory().toString());
        book_pages.setText("Pages: " + Integer.toString(book.getPages()));
        book_year.setText("Year: " + Integer.toString(book.getYear()));
        book_author.setText("Author: " + book.getAuthor());
        return convertView;
    }
}
