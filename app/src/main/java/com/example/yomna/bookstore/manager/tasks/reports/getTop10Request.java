package com.example.yomna.bookstore.manager.tasks.reports;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

public class getTop10Request extends StringRequest {
    private static final String url = "https://eldawybookstore.000webhostapp.com/top10Books.php";


    public getTop10Request(Response.Listener<String> listener) {
        super(Method.POST, url, listener, null);

    }
}
