package com.example.shmulik.control;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.shmulik.myjavaproject.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import entities.BooksForOrder;


/**
 * Created by shmuel on 19/06/2016.
 */
public class OrderViewAdapter extends ArrayAdapter<BooksForOrder> {
    public OrderViewAdapter(Context context, ArrayList<BooksForOrder> booksForOrder) {
        super(context, 0,  booksForOrder);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_book_for_order, parent, false);
        }
        BooksForOrder bookForOrder = getItem(position);
        TextView bookName = (TextView) convertView.findViewById(R.id.book_name_row);
        TextView supplierName = (TextView) convertView.findViewById(R.id.supplier_name_row);
        TextView bookPrice = (TextView) convertView.findViewById(R.id.book_price_row);
        TextView sumOfBooks = (TextView) convertView.findViewById(R.id.sum_of_books);
        bookName.setText("Book Name: " + bookForOrder.getBookSupplier().getBook().getTitle().toString());
        supplierName.setText("Supplier Name: " + bookForOrder.getBookSupplier().getSupplier().getName().toString());
        bookPrice.setText("Book Price: " + Float.toString(bookForOrder.getBookSupplier().getPrice()));
        sumOfBooks.setText("Sum Of Books: " + Float.toString(bookForOrder.getSumOfBooks()));

        if ((position % 2) == 0) { // set different color to each line.
            convertView.setBackgroundResource(R.color.colorEven2);
        }
        else {
            convertView.setBackgroundResource(R.color.colorOdd2);
        }
        return convertView;
    }
}
