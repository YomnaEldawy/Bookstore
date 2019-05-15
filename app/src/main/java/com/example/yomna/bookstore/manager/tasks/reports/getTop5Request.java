package com.example.yomna.bookstore.manager.tasks.reports;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

public class getTop5Request extends StringRequest {
    private static final String url = "https://eldawybookstore.000webhostapp.com/top5Customers.php";


    public getTop5Request(Response.Listener<String> listener) {
        super(Method.POST, url, listener, null);

    }
}
