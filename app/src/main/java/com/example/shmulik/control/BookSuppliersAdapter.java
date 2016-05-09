package com.example.shmulik.control;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.shmulik.myjavaproject.R;

import java.util.ArrayList;

import entities.Book;
import entities.BookSupplier;

/**
 * Created by reuvenp on 5/4/2016.
 */
public class BookSuppliersAdapter extends ArrayAdapter<BookSupplier> {
    public BookSuppliersAdapter(Context context, ArrayList<BookSupplier> bookSuppliers) {
        super(context, 0,  bookSuppliers);
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_supplier_price, parent, false);
        }
        BookSupplier bookSupplier = getItem(position);
        TextView supplier_name = (TextView) convertView.findViewById(R.id.supplier_name);
        TextView supplier_address = (TextView)convertView.findViewById(R.id.supplier_address);
        TextView price = (TextView) convertView.findViewById(R.id.price);
        TextView amount = (TextView) convertView.findViewById(R.id.amount);
        Button addToCart = (Button) convertView.findViewById(R.id.add_to_cart_BTN);
        supplier_name.setText("Supplier: " + bookSupplier.getSupplier().getName());
        supplier_address.setText("Address: " + bookSupplier.getSupplier().getAddress());
        price.setText("Price: " + Float.toString(bookSupplier.getPrice()));
        amount.setText("Amount: " + Integer.toString(bookSupplier.getAmount()));
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return convertView;
    }
}
