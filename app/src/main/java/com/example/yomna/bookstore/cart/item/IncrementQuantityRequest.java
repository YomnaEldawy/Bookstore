package com.example.yomna.bookstore.cart.item;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yomna on 5/9/19.
 */

public class IncrementQuantityRequest extends StringRequest {
    private static final String url = "https://eldawybookstore.000webhostapp.com/bookstore/incrementQuantity.php";

    @Override
    public Map<String, String> getParams() {
        return params;
    }

    private Map<String, String> params;

    public IncrementQuantityRequest(String bookISBN, String username, Response.Listener<String> listener) {
        super(Request.Method.POST, url, listener, null);
        params = new HashMap<>();
        params.put("ISBN", bookISBN);
        params.put("username", username);
    }
}
