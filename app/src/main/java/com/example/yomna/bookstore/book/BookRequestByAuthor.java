package com.example.yomna.bookstore.book;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class BookRequestByAuthor extends StringRequest {
    private static final String url = "https://eldawybookstore.000webhostapp.com/getBookByAuthor.php";

    @Override
    public Map<String, String> getParams() {
        return params;
    }

    private Map<String, String> params;

    public BookRequestByAuthor(String searchValue , Response.Listener<String> listener) {
        super(Request.Method.POST, url, listener,null);
        params = new HashMap<>();
        params.put("value",searchValue);

    }
}