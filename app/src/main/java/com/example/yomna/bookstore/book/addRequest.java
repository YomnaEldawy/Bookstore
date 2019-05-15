package com.example.yomna.bookstore.book;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by omid on 5/8/2019.
 */

public class addRequest  extends StringRequest {
    private static final String url = "https://eldawybookstore.000webhostapp.com/addBook.php";

    @Override
    public Map<String, String> getParams() {
        return params;
    }

    private Map<String, String> params;
    public addRequest(String isbn, String title, int publicationYear, double price, int noOfCopies,
                      int threshold, String category, int publisherId, String picURL,  Response.Listener<String> listener) {
        super(Method.POST, url, listener,null);
        params = new HashMap<>();
        params.put("isbn",isbn);
        params.put("title",title);
        params.put("publicationYear",publicationYear+"");
        params.put("price",price+"");
        params.put("noOfCopies",noOfCopies+"");
        params.put("threshold",threshold+"");
        params.put("category",category);
        params.put("publisherId",publisherId+"");
        params.put("picURL",picURL);

    }
}