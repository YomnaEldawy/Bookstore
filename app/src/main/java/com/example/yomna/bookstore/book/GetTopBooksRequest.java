package com.example.yomna.bookstore.book;

import android.support.annotation.Nullable;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yomna on 5/9/19.
 */

public class GetTopBooksRequest extends StringRequest {
    private static final String url = "https://eldawybookstore.000webhostapp.com/bookstore/getTopBooks.php";
    private Map<String, String> params;
    @Override
    public Map<String, String> getParams() {
        return params;
    }

    public GetTopBooksRequest(int booksToSkip, int booksCount, Response.Listener<String> listener) {
        super(Method.POST, url, listener, null);
        params = new HashMap<>();
        params.put("booksToSkip", booksToSkip + "");
        params.put("booksCount", booksCount + "");
    }
}
