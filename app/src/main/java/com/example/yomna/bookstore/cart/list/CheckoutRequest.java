package com.example.yomna.bookstore.cart.list;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yomna on 5/10/19.
 */

public class CheckoutRequest  extends StringRequest {
    private static final String URL = "https://eldawybookstore.000webhostapp.com/bookstore/checkout.php";

    @Override
    public Map<String, String> getParams() {
        return params;
    }

    private Map<String,String> params;
    public CheckoutRequest(String username, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        params = new HashMap<>();
        params.put("username", username);
    }
}
