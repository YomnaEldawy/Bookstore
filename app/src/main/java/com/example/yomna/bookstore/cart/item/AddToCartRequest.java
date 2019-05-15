package com.example.yomna.bookstore.cart.item;

import android.support.annotation.Nullable;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yomna on 5/4/19.
 */

public class AddToCartRequest extends StringRequest {
    private static final String url = "https://eldawybookstore.000webhostapp.com/bookstore/addToCart.php";
    private Map<String, String> params;
    @Override
    public Map<String, String> getParams() {
        return params;
    }

    public AddToCartRequest(String username, String ISBN, Response.Listener<String> listener) {
        super(Method.POST, url, listener, null);
        params = new HashMap<>();
        params.put("ISBN", ISBN);
        params.put("username", username);
    }
}
