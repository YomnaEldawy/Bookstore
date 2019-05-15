package com.example.yomna.bookstore.book;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class BookRequestByTitle extends StringRequest {
    private static final String url = "https://eldawybookstore.000webhostapp.com/getBookByTitle.php";

    @Override
    public Map<String, String> getParams() {
        return params;
    }

    private Map<String, String> params;

    public BookRequestByTitle(String searchValue , Response.Listener<String> listener) {
        super(Request.Method.POST, url, listener,null);
       params = new HashMap<>();
        params.put("value",searchValue);

    }
}
