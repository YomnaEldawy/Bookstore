package com.example.yomna.bookstore.user.authentication.register;

import android.support.annotation.Nullable;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yomna on 4/27/19.
 */

public class RegisterRequest extends StringRequest {
    private static final String url = "https://eldawybookstore.000webhostapp.com/bookstore/bookstoreRegister.php";

    @Override
    public Map<String, String> getParams() {
        return params;
    }

    private Map<String, String> params;
    public RegisterRequest(String firstname, String lastname, String username, String password, String email,
                           String phonenumber, String country, String city, int streetnumber, String streetname, int ismanager,  Response.Listener<String> listener) {
        super(Method.POST, url, listener,null);
        params = new HashMap<>();
        params.put("firstname",firstname);
        params.put("lastname",lastname);
        params.put("email",email);
        params.put("password",password);
        params.put("username",username);
        params.put("country",country);
        params.put("streetname",streetname);
        params.put("city",city);
        params.put("streetnumber",streetnumber+"");
        params.put("phonenumber",phonenumber);
        params.put("ismanager",ismanager+"");
    }
}
