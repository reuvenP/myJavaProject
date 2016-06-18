package com.example.shmulik.control;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.shmulik.myjavaproject.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

import entities.BookSupplier;


public class CartAdapter extends ArrayAdapter<BookSupplier> {
    Context mContext;
    public CartAdapter(Context context, ArrayList<BookSupplier> bookSuppliers) {
        super(context, 0,  bookSuppliers);
        mContext = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_cart, parent, false);
        }
        final BookSupplier bookSupplier = getItem(position);
        TextView bookName = (TextView) convertView.findViewById(R.id.book_name_cart);
        TextView supplierName = (TextView) convertView.findViewById(R.id.supplier_name_cart);
        TextView price = (TextView) convertView.findViewById(R.id.price_cart);
        Button submit = (Button) convertView.findViewById(R.id.cart_submit_BTN);
        Button remove = (Button) convertView.findViewById(R.id.remove_from_cart_BTN);
        bookName.setText(bookSupplier.getBook().getTitle().toString());
        supplierName.setText(bookSupplier.getSupplier().getName().toString());
        price.setText(Float.toString(bookSupplier.getPrice()));
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // on click on "remove" button.
                if (mContext instanceof CartActivity)
                {
                    ((CartActivity)mContext).removeFromCard(position);
                }
            }
        });
        if ((position % 2) == 0) { // set different color to each line.
            convertView.setBackgroundResource(R.color.colorEven2);
        }
        else {
            convertView.setBackgroundResource(R.color.colorOdd2);
        }
        return convertView;
    }
}
