package com.example.yomna.bookstore.manager.tasks;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yomna on 5/9/19.
 */

public class PlaceOrderRequest  extends StringRequest {
    private static final String PLACE_ORDER_REQUEST_URL = "https://eldawybookstore.000webhostapp.com/Place_order.php";
    private Map<String,String> params;
    public PlaceOrderRequest (String isbn, int quantity, Response.Listener<String> listener){
        super(Method.POST, PLACE_ORDER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("isbn", isbn);
        params.put("quantity", quantity+"");


    }

    public Map<String, String> getParams(){
        return params;
    }
}