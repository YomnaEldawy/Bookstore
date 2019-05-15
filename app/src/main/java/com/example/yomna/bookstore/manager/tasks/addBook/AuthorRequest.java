package com.example.yomna.bookstore.manager.tasks.addBook;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;

public class AuthorRequest extends StringRequest {
    private static final String url = "https://yomnaeldawy.000webhostapp.com/Authors.php";

    public AuthorRequest(Response.Listener<String> listener) {
        super(Method.POST, url, listener, null);

    }
}
