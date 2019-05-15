package com.example.yomna.bookstore.cart.item;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yomna on 5/9/19.
 */

public class RemoveFromCartRequest extends StringRequest {
    private static final String url = "https://eldawybookstore.000webhostapp.com/bookstore/removeCartItem.php";

    @Override
    public Map<String, String> getParams() {
        return params;
    }

    private Map<String, String> params;

    public RemoveFromCartRequest(String bookISBN, String username, Response.Listener<String> listener) {
        super(Method.POST, url, listener, null);
        params = new HashMap<>();
        params.put("ISBN", bookISBN);
        params.put("username", username);
    }
}