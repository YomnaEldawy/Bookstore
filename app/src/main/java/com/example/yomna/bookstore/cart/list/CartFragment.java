package com.example.yomna.bookstore.cart.list;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.yomna.bookstore.R;
import com.example.yomna.bookstore.cart.item.*;
import com.example.yomna.bookstore.cart.item.CartItemController;
import com.example.yomna.bookstore.global.data.Fragments;
import com.example.yomna.bookstore.user.authentication.CurrentUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment {


    View view;
    public ArrayList<CartItem> getCartItems() {
        return cartItems;
    }

    ArrayList<CartItem> cartItems;

    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_cart, container, false);
        cartItems = new ArrayList<CartItem>();
        RelativeLayout checkoutRl = view.findViewById(R.id.checkout_container);
        Button submitPaymentDetails = view.findViewById(R.id.submit_btn);
        setOnCheckoutListener(checkoutRl);
        setOnSubmitPaymentDetailsListener(submitPaymentDetails);
        Fragments fragments = Fragments.getInstance();
        setCartItems();
        displayCartItems();
        fragments.setCartFrament(this);
        return view;
    }

    private void setOnSubmitPaymentDetailsListener(Button button){
        button.setOnClickListener(new View.OnClickListener() {
            public boolean isValidDate(String date){
                try {
                    String monthStr = date.substring(0, date.indexOf('/'));
                    String yearStr = date.substring(date.indexOf('/') + 1);
                    int month = Integer.parseInt(monthStr);
                    int year = Integer.parseInt(yearStr);
                    int currentYear = Calendar.getInstance().get(Calendar.YEAR);
                    int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
                    if (month > 12)
                        return false;
                    if (year > currentYear || year == currentYear && month > currentMonth)
                        return true;
                }catch (Exception e){
                    e.printStackTrace();
                }
                return false;
            }

            @Override
            public void onClick(View v) {
                EditText cardNumberEt = view.findViewById(R.id.card_number_et);
                EditText expiryDateEt = view.findViewById(R.id.expiry_date_et);
                if (cardNumberEt.getText().toString().length() == 16 && isValidDate(expiryDateEt.getText().toString())) {
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            System.out.println("Response is: " + response.toString());
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                System.out.println("success = " + success);
                                if (success) {
                                    cartItems = new ArrayList<>();
                                    displayCartItems();
                                    RelativeLayout paymentDetailsRl = view.findViewById(R.id.payment_details_rl);
                                    paymentDetailsRl.setVisibility(View.GONE);
                                    Context context = getActivity().getApplicationContext();
                                    CharSequence text = "Successfully ordered";
                                    int duration = Toast.LENGTH_SHORT;
                                    Toast toast = Toast.makeText(context, text, duration);
                                    toast.show();
                                } else {
                                    Context context = getActivity().getApplicationContext();
                                    CharSequence text = "Book is no longer available";
                                    int duration = Toast.LENGTH_SHORT;
                                    Toast toast = Toast.makeText(context, text, duration);
                                    toast.show();
                                }
                            } catch (JSONException e) {

                            }
                        }

                    };

                    try {
                        CheckoutRequest checkoutRequest = new CheckoutRequest(CurrentUser.getInstance().getUsername(), responseListener);
                        RequestQueue queue = Volley.newRequestQueue(getActivity());
                        queue.add(checkoutRequest);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Invalid payment details", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }
    private void setOnCheckoutListener(RelativeLayout checkoutRl) {
        checkoutRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RelativeLayout paymentDetailsRl = view.findViewById(R.id.payment_details_rl);
                paymentDetailsRl.setVisibility(View.VISIBLE);
            }
        });
    }

    public void displayCartItems(){
        LinearLayout root = view.findViewById(R.id.cart_item_container);
        TextView totalPriceTv = view.findViewById(R.id.total_value);
        double totalPrice = 0;
        root.removeAllViews();
        for (int i = 0; i < cartItems.size(); i++) {
            CartItemController cartItemController = new CartItemController(getActivity(), getView(), cartItems.get(i));
            View cartItemView = cartItemController.getView();
            root.addView(cartItemView);
            totalPrice +=( cartItems.get(i).getBook().getPrice() * cartItems.get(i).getQuantity());
        }
        totalPriceTv.setText(Math.floor(totalPrice*100)/100 + "");
        System.out.println("Items displayed");
    }
    void setCartItems() {
        getCartItems(new VolleyCallback() {
            @Override
            public void onSuccess(ArrayList<CartItem> volleyCartItems) {
                cartItems = volleyCartItems;
            }

            @Override
            public void onFail(String msg) {
                Context context = getActivity().getApplicationContext();
                CharSequence text = msg;
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });
    }

    void getCartItems(final VolleyCallback onCallback) {

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("Response is: " + response.toString());
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        cartItems = toObject(jsonResponse);
                        onCallback.onSuccess(cartItems);
                    } else {
                        onCallback.onFail("No items in cart");
                    }
                } catch (JSONException e) {

                }
            }

        };

        try {
            GetCartItemsRequest getCartItemsRequest = new GetCartItemsRequest(CurrentUser.getInstance().getUsername(), responseListener);
            RequestQueue queue = Volley.newRequestQueue(getActivity());
            queue.add(getCartItemsRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<CartItem> toObject(JSONObject jsonResponse) {
        ArrayList<CartItem> cartItems = new ArrayList<>();
        try {
            JSONArray ISBNs = jsonResponse.getJSONArray("book_ISBN");
            JSONArray quantities = jsonResponse.getJSONArray("quantity");
            for (int i = 0; i < ISBNs.length(); i++) {
                CartItem cartItem = new CartItem(getActivity(), (String) ISBNs.get(i), (int) quantities.get(i));
                cartItems.add(cartItem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return cartItems;
    }



}
