package com.example.yomna.bookstore.book.publisher;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yomna on 5/10/19.
 */

public class BookPublisherNameRequest extends StringRequest {
    private static final String url = "https://eldawybookstore.000webhostapp.com/bookstore/getPublisherName.php";
    private Map<String, String> params;
    @Override
    public Map<String, String> getParams() {
        return params;
    }

    public BookPublisherNameRequest(int publisherId, Response.Listener<String> listener) {
        super(Method.POST, url, listener, null);
        params = new HashMap<>();
        params.put("publisherId", publisherId + "");
    }
}
