package com.example.yomna.bookstore.manager.tasks.orderBooks;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.yomna.bookstore.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class showOrderedBooksFragment extends Fragment {
    ListView listView;
    ArrayList<ManagerOrder> managerOrders;
    OrdersAdapter adapter;
    ImageView delete;

    public showOrderedBooksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_show_ordered_books, container, false);
        managerOrders = new ArrayList<ManagerOrder>();
        listView = myView.findViewById(R.id.listViewBooks);

        getOrderedBooks(new VolleyCallbackOrder() {
            @Override
            public void onSuccess(ArrayList<ManagerOrder> resultManagerOrder) {
                managerOrders = resultManagerOrder;
                adapter = new OrdersAdapter(getActivity().getApplicationContext(), managerOrders);
                listView.setAdapter(adapter);
                System.out.println("size of manager orders " + managerOrders.size());
                for (int i = 0; i < managerOrders.size(); i++) {
                    System.out.println("Order " + managerOrders.get(i).getBookTitle() + " " + managerOrders.get(i).getQuantity());
                }
            }

            @Override
            public void onFail(String msg) {
                System.out.println("Failed");

            }
        });
        return myView;
    }

    private void getOrderedBooks(final VolleyCallbackOrder onCallback) {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    System.out.println("Response is :" + response);
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        managerOrders = toList(jsonResponse);
                        onCallback.onSuccess(toList(jsonResponse));
                    } else {
                        onCallback.onFail("No Books Found");

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        };
        ManagerOrderRequest r1 = new ManagerOrderRequest(responseListener);
        RequestQueue q1 = Volley.newRequestQueue(getActivity());
        q1.add(r1);

    }

    ArrayList<ManagerOrder> toList(JSONObject jsonResponse) {
        ArrayList<ManagerOrder> managerOrders = new ArrayList<ManagerOrder>();
        try {
            JSONArray titles = jsonResponse.getJSONArray("title");
            JSONArray quantities = jsonResponse.getJSONArray("quantity");
            JSONArray order_Ids = jsonResponse.getJSONArray("order_id");
            JSONArray bookISBNs = jsonResponse.getJSONArray("Book_ISBN");

            for (int i = 0; i < titles.length(); i++) {
                ManagerOrder m = new ManagerOrder();
                String title = (String) titles.get(i);
                int quantity = (Integer) quantities.get(i);
                int order_id = (Integer) order_Ids.get(i);
                String ISBN = (String) bookISBNs.get(i);
                m.setBookTitle(title);
                m.setQuantity(quantity);
                m.setOrderId(order_id);
                m.setISBN(ISBN);
                m.setConfirmed(0);
                m.setDeleted(0);
                managerOrders.add(m);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return managerOrders;
    }

}
