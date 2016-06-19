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
import entities.Order;
/**
 * Created by shmuel on 19/06/2016.
 */
public class OrdersAdapter extends ArrayAdapter<Order> {
    public OrdersAdapter(Context context, ArrayList<Order> orders) {
        super(context, 0,  orders);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_order, parent, false);
        }
        Order order = getItem(position);
        TextView order_id = (TextView) convertView.findViewById(R.id.order_id_row);
        TextView order_date = (TextView)convertView.findViewById(R.id.order_date_row);
        TextView order_total_price = (TextView) convertView.findViewById(R.id.order_total_price_row);
        order_id.setText("Serial Number: " + Integer.toString(order.getOrderID()));
        order_date.setText("Date of Order: " + new SimpleDateFormat("yyyy-MM-dd").format(order.getOrderDate()));
        order_total_price.setText("Total Price: " + Float.toString(order.getTotalPrice()));
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////should enter all the books_for_order///////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        if ((position % 2) == 0) { // set different color to each line.
            convertView.setBackgroundResource(R.color.colorEven2);
        }
        else {
            convertView.setBackgroundResource(R.color.colorOdd2);
        }
        return convertView;
    }

}
