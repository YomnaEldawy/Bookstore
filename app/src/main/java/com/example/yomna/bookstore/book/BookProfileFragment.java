package com.example.yomna.bookstore.book;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.yomna.bookstore.R;
import com.example.yomna.bookstore.book.publisher.BookPublisherNameRequest;
import com.example.yomna.bookstore.book.publisher.PublisherNameCallback;
import com.example.yomna.bookstore.manager.tasks.PlaceOrderRequest;
import com.example.yomna.bookstore.user.authentication.CurrentUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookProfileFragment extends Fragment {

    public void setBook(Book book) {
        this.book = book;
    }

    Book book;
    View view;
    public BookProfileFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_book_profile, container, false);
        RelativeLayout updateBookCopiesRl = view.findViewById(R.id.update_books_copies_rl);
        CurrentUser currentUser = CurrentUser.getInstance();
        try {
            if (!currentUser.getIsManager()){
                updateBookCopiesRl.setVisibility(View.GONE);
            }
        } catch (JSONException e) {

        }
        Button order = view.findViewById(R.id.quantity_order_btn);
        Button modify = view.findViewById(R.id.update_books_copies_btn);
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderAction(v);
            }
        });
        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyAction(v);
            }
        });
        setViewAttributes();
        return view;
    }
    public void orderAction(View v) {
        EditText orderedQuantity = view.findViewById(R.id.quantity_ordered);
        final String quantity = orderedQuantity.getText().toString();
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("response is: " + response);
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    String failure = jsonResponse.getString("failureReason");
                    if(success){
                        Toast.makeText(getContext(),"Order Placed",Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getContext(),failure,Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setMessage("Order Failed").setNegativeButton("Retry",null).create().show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        PlaceOrderRequest orderRequest = new PlaceOrderRequest(book.getBook_ISBN(),
                Integer.parseInt(quantity),responseListener);

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(orderRequest);
    }
    public void modifyAction(View v) {
        EditText modifiedQuantity = view.findViewById(R.id.no_of_copies_et);
        final String quantity = modifiedQuantity.getText().toString();
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("response is: " + response);
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    //String failure = jsonResponse.getString("failureReason");
                    if(success){
                        Toast.makeText(getContext(),"Book Modified",Toast.LENGTH_LONG).show();
                        book.setNo_of_copies(Integer.parseInt(quantity));
                        TextView numOfCopiesTv = view.findViewById(R.id.no_of_copies_tv);
                        numOfCopiesTv.setText(book.getNo_of_copies() + " copies available");
                    }else{
                        Toast.makeText(getContext(),"An error occurred",Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setMessage("Modification Failed").setNegativeButton("Retry",null).create().show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(getContext(),"An error occurred",Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("Please enter a valid quantity").setNegativeButton("Retry",null).create().show();
                }
            }
        };

        ModifyRequest modifyRequest = new ModifyRequest(book.getBook_ISBN(),
                Integer.parseInt(quantity),responseListener);

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(modifyRequest);
    }
    public void Restart(Context ctx)
    {
        //getting activty from context
        Activity a = (Activity)ctx;
        //forcing activity to recreate
        a.recreate();
    }
    public void setViewAttributes(){
        TextView titleTv = view.findViewById(R.id.book_title_tv);
        final TextView publisherTv = view.findViewById(R.id.publisher_tv);
        TextView authorsTv = view.findViewById(R.id.book_author_tv);
        TextView priceTv = view.findViewById(R.id.price_tv);
        TextView numOfCopiesTv = view.findViewById(R.id.no_of_copies_tv);
        RelativeLayout addToCartRl = view.findViewById(R.id.add_to_cart_rl);

        BookController bookController = new BookController(book);
        bookController.setActivity(getActivity());
        bookController.setOnAddToCartListener(addToCartRl);

        titleTv.setText(book.getTitle());
        publisherTv.setText(book.getPublisher_id() +"");
        setPublisherName(new PublisherNameCallback() {
            @Override
            public void onSuccess(String publisherName) {
                publisherTv.setText(publisherName);
            }

            @Override
            public void onFail(String msg) {

            }
        });
        String authorsStr = "";
        ArrayList<String> authors = book.getAuthor_name();
        for (int i = 0; i < authors.size(); i++){
            if (authorsStr.length() > 0)
                authorsStr = authorsStr + ", " + authors.get(i);
            else{
                authorsStr = authors.get(i);
            }
        }
        authorsTv.setText(authorsStr);
        priceTv.setText(book.getPrice() + "");
        numOfCopiesTv.setText(book.getNo_of_copies() + " copies available");
    }


    void setPublisherName(final PublisherNameCallback publisherName){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    System.out.println("get Publisher: response is: " + response);
                    String name = jsonResponse.getString("name");
                    publisherName.onSuccess(name);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        BookPublisherNameRequest bookPublisherNameRequest = new BookPublisherNameRequest(book.getPublisher_id(), responseListener);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(bookPublisherNameRequest);
    }

}