package com.example.yomna.bookstore.manager.tasks.orderBooks;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.yomna.bookstore.R;

import java.util.ArrayList;

public class OrdersAdapter extends BaseAdapter {
    Button confirm;
    ImageView delete;
    private Context context;
    private ArrayList<ManagerOrder> managerOrders;

    public OrdersAdapter(Context context, ArrayList<ManagerOrder> managerOrders) {
        this.context = context;
        this.managerOrders = managerOrders;
    }

    @Override
    public int getCount() {
        return managerOrders.size();
    }

    @Override
    public Object getItem(int i) {
        return managerOrders.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.listview_book_item, null);
        TextView title = v.findViewById(R.id.bookTitleTV);
        TextView quantity = v.findViewById(R.id.quantityTV);
        title.setText(managerOrders.get(i).getBookTitle());
        quantity.setText(managerOrders.get(i).getQuantity() + " books");
        confirm = v.findViewById(R.id.confirmBtn);
        delete = v.findViewById(R.id.deleteImg);
        confirm.setBackgroundColor(Color.parseColor("#f7e0a3"));
        confirm.setTag(i);
        delete.setTag(i);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirm = view.findViewById(R.id.confirmBtn);
                confirm.setClickable(false);
                confirm.setBackgroundColor(Color.parseColor("#f09c67"));
                int position = (Integer) view.getTag();
                if (managerOrders.get(position).getConfirmed() == 0) {
                    managerOrders.get(position).setConfirmed(1);
                    managerOrders.get(position).setDeleted(1);
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            System.out.println(" response " + response);
                            Toast toast = Toast.makeText(context,
                                    "Successfully confirmed",
                                    Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                            toast.show();
                            confirm.setBackgroundColor(Color.parseColor("#" +
                                    "f7e0a3"));
                            confirm.setClickable(true);

                        }
                    };
                    System.out.println("position " + position);
                    ConfirmOrderRequest pr = new ConfirmOrderRequest(managerOrders.get(position).getISBN(), managerOrders.get(position).getOrderId(), responseListener);
                    RequestQueue q = Volley.newRequestQueue(context);
                    q.add(pr);
                } else {
                    Toast toast = Toast.makeText(context,
                            "This order is already confirmed.",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toast.show();
                }
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                delete = view.findViewById(R.id.deleteImg);
                int position = (Integer) view.getTag();
                if (managerOrders.get(position).getDeleted() == 0) {
                    managerOrders.get(position).setDeleted(1);
                    managerOrders.get(position).setConfirmed(1);
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            System.out.println(" response " + response);
                            Toast toast = Toast.makeText(context,
                                    "Successfully deleted",
                                    Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                            toast.show();
                        }
                    };
                    System.out.println("position " + position);
                    DeleteOrderRequest pr = new DeleteOrderRequest(managerOrders.get(position).getISBN(), managerOrders.get(position).getOrderId(), responseListener);
                    RequestQueue q = Volley.newRequestQueue(context);
                    q.add(pr);
                } else {
                    Toast toast = Toast.makeText(context,
                            "This order is already deleted.",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toast.show();
                }
            }
        });
        return v;
    }

}
