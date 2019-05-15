package com.example.yomna.bookstore.manager.tasks.reports;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.yomna.bookstore.R;

import java.util.ArrayList;

public class AdapterCustomer extends BaseAdapter {
    private Context context;
    private ArrayList<Customer> customers;

    public AdapterCustomer(Context context, ArrayList<Customer> customers) {
        this.context = context;
        this.customers = customers;
    }

    @Override
    public int getCount() {
        return customers.size();
    }

    @Override
    public Object getItem(int i) {
        return customers.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.customer_item, null);
        TextView name = v.findViewById(R.id.nameR);
        TextView purchase = v.findViewById(R.id.purchase);
        name.setText(customers.get(i).getTopUsername());

        purchase.setText(String.format("%.2f",customers.get(i).getTotalPurchases())+" EGP");
        return v;
    }
}
