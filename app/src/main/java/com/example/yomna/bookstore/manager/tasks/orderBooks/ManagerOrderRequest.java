package com.example.yomna.bookstore.manager.tasks.orderBooks;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

public class ManagerOrderRequest extends StringRequest {
    private static final String url = "https://eldawybookstore.000webhostapp.com/getManagerOrder.php";


    public ManagerOrderRequest( Response.Listener<String> listener) {
        super(Request.Method.POST, url, listener,null);


    }
}
