package com.example.yomna.bookstore.manager.tasks.reports;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

public class getTotalSalesRequest extends StringRequest {
    private static final String url = "https://eldawybookstore.000webhostapp.com/totalSales.php";


    public getTotalSalesRequest(Response.Listener<String> listener) {
        super(Method.POST, url, listener, null);

    }
}
