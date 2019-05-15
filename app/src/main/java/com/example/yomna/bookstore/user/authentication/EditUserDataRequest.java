package com.example.yomna.bookstore.user.authentication;

import android.support.annotation.Nullable;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class EditUserDataRequest extends StringRequest {

    private static final String url = "https://eldawybookstore.000webhostapp.com/editUserData.php";
    HashMap<String, String> params;

    public EditUserDataRequest (String old_username,String old_email, int ismanager,String username,String password, String email, String first_name, String last_name, String country, String city, String streetName,
                                int streetNumber,String phone_number, Response.Listener<String> listener) {
        super(Method.POST, url, listener, null);
        params = new HashMap<>();
        params.put("old_username",old_username);
        params.put("old_email",old_email);
        params.put("username", username);
        params.put("password", password);
        params.put("email", email);
        params.put("first_name", first_name);
        params.put("last_name", last_name);
        params.put("country", country);
        params.put("city", city);
        params.put("street_name", streetName);
        params.put("street_number", streetNumber+"");
        params.put("phone_number", phone_number);
        params.put("is_manager", ismanager+"");

    }
    public Map<String, String> getParams(){
        return params;
    }
}