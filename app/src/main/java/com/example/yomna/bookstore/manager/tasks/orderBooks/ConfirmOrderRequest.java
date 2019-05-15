package com.example.yomna.bookstore.manager.tasks.orderBooks;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ConfirmOrderRequest extends StringRequest {
    private static final String url = "https://eldawybookstore.000webhostapp.com/confirmManagerOrder.php";
    HashMap<String, String> params;

    public ConfirmOrderRequest(String bookISBN, int order_id, Response.Listener<String> listener) {
        super(Method.POST, url, listener, null);
        params = new HashMap<>();
        params.put("Book_ISBN", bookISBN);
        params.put("order_id", order_id + "");
    }

    public Map<String, String> getParams() {
        return params;
    }
}
