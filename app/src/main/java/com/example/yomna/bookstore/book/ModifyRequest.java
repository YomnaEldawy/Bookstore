package com.example.yomna.bookstore.book;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by omid on 5/10/2019.
 */

public class ModifyRequest  extends StringRequest {
    private static final String Modify_REQUEST_URL = "https://eldawybookstore.000webhostapp.com/bookstore/modify_book.php";

    @Override
    public Map<String, String> getParams() {
        return params;
    }

    private Map<String,String> params;
    public ModifyRequest (String isbn, int quantity, Response.Listener<String> listener){
        super(Method.POST, Modify_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("isbn", isbn);
        params.put("quantity", quantity+"");

    }
}
