package com.example.yomna.bookstore.cart.item;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.yomna.bookstore.R;
import com.example.yomna.bookstore.cart.list.CartFragment;
import com.example.yomna.bookstore.global.data.Fragments;
import com.example.yomna.bookstore.user.authentication.CurrentUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by yomna on 5/7/19.
 */

public class CartItemController {
    View view;
    Activity activity;
    CartItem cartItem;

    public CartItemController(Activity activity, View v, CartItem cartItem) {
        view = v;
        this.activity = activity;
        this.cartItem = cartItem;
    }

    public View getView() {
        View cartItemView = view.inflate(activity, R.layout.cart_item, null);
        TextView bookTitle = cartItemView.findViewById(R.id.cart_product_name);
        TextView price = cartItemView.findViewById(R.id.price_tv);
        TextView quantity = cartItemView.findViewById(R.id.quantity_value);
        ImageView imageView = cartItemView.findViewById(R.id.cart_product_img);
        RelativeLayout deleteCartItem = cartItemView.findViewById(R.id.delete_cart_item);
        bookTitle.setText(cartItem.getBook().getTitle());
        price.setText(cartItem.getBook().getPrice() + "");
        quantity.setText(cartItem.getQuantity() + "");
        //TODO: set imageView

        setOnDeleteItemListener(deleteCartItem);
        setOnIncrementListener(cartItemView);
        setOnDecrementListener(cartItemView);
        return cartItemView;
    }

    private void setOnIncrementListener(final View cartItemView) {
        TextView incrementTv = cartItemView.findViewById(R.id.increment_btn);
        incrementTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final TextView quantityTv = cartItemView.findViewById(R.id.quantity_value);
                System.out.println("Quantity = " + cartItem.getQuantity());
                cartItem.setQuantity(cartItem.getQuantity() + 1);
                quantityTv.setText( Integer.parseInt(quantityTv.getText().toString()) + 1 + "");
                Fragments fragments = Fragments.getInstance();
                if (fragments.getCartFrament() != null)
                    fragments.getCartFrament().displayCartItems();
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("Response is: " + response.toString());
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            System.out.println("Item incremented successfully");
                        } catch (JSONException e) {
                            cartItem.setQuantity(cartItem.getQuantity() - 1);
                            Fragments fragments = Fragments.getInstance();
                            if (fragments.getCartFrament() != null)
                                fragments.getCartFrament().displayCartItems();
                            Context context = activity.getApplicationContext();
                            CharSequence text = "No more copies available at the bookstore";
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();

                        }
                    }
                };

                try {
                    IncrementQuantityRequest incrementQuantityRequest = new IncrementQuantityRequest(cartItem.getBook().getBook_ISBN(), CurrentUser.getInstance().getUsername(), responseListener);
                    RequestQueue queue = Volley.newRequestQueue(activity);
                    queue.add(incrementQuantityRequest);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void setOnDecrementListener(final View cartItemView) {
        TextView decrementTv = cartItemView.findViewById(R.id.decrement_btn);
        final TextView quantityTv = cartItemView.findViewById(R.id.quantity_value);
        decrementTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cartItem.getQuantity() > 1) {
                    cartItem.setQuantity(cartItem.getQuantity() - 1);
                    quantityTv.setText(cartItem.getQuantity() + "");
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            System.out.println("Response is: " + response.toString());
                            Fragments fragments = Fragments.getInstance();
                            if (fragments.getCartFrament() != null)
                                fragments.getCartFrament().displayCartItems();
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if (success) {
                                    System.out.println("Item decremented successfully");
                                } else {
                                    //TODO: Display error message
                                }
                            } catch (JSONException e) {

                            }
                        }
                    };

                    try {
                        DecrementQuantityRequest decrementQuantityRequest = new DecrementQuantityRequest(cartItem.getBook().getBook_ISBN(), CurrentUser.getInstance().getUsername(), responseListener);
                        RequestQueue queue = Volley.newRequestQueue(activity);
                        queue.add(decrementQuantityRequest);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    private void setOnDeleteItemListener(RelativeLayout deleteButton) {
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CartFragment cartFragment = Fragments.getInstance().getCartFrament();
                ArrayList<CartItem> cartItems = cartFragment.getCartItems();
                cartItems.remove(cartItem);
                cartFragment.displayCartItems();
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("Response is: " + response.toString());
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                System.out.println("Item removed successfully");
                            } else {
                                //TODO: Display error message
                            }
                        } catch (JSONException e) {

                        }
                    }
                };

                try {
                    RemoveFromCartRequest removeFromCartRequest = new RemoveFromCartRequest(cartItem.getBook().getBook_ISBN(), CurrentUser.getInstance().getUsername(), responseListener);
                    RequestQueue queue = Volley.newRequestQueue(activity);
                    queue.add(removeFromCartRequest);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
