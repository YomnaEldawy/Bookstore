package com.example.yomna.bookstore.user;

import android.support.annotation.Nullable;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class promoteRequest extends StringRequest {
    private static final String url = "https://eldawybookstore.000webhostapp.com/promoteUser.php";
    HashMap<String, String> params;

    public promoteRequest(String username, Response.Listener<String> listener) {
        super(Method.POST, url, listener, null);
        params = new HashMap<>();
        params.put("username", username);
    }
    public Map<String, String> getParams(){
        return params;
    }

}
