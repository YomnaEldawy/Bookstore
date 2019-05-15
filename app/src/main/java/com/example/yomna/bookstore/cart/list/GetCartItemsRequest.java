package com.example.yomna.bookstore.cart.list;

import android.support.annotation.Nullable;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yomna on 5/7/19.
 */

public class GetCartItemsRequest extends StringRequest {
    private static final String URL = "https://eldawybookstore.000webhostapp.com/bookstore/GetCartItems.php";

    @Override
    public Map<String, String> getParams() {
        return params;
    }

    private Map<String,String> params;
    public GetCartItemsRequest(String username, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        params = new HashMap<>();
        params.put("username", username);
    }
}
