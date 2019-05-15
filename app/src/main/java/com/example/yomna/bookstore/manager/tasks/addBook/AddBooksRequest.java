package com.example.yomna.bookstore.manager.tasks.addBook;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddBooksRequest extends StringRequest {

    private static final String url = "https://eldawybookstore.000webhostapp.com/addNewBook.php";
    HashMap<String, String> params;

    public AddBooksRequest(String title, String ISBN, int year, double price, String publisher, String category, String pic_url,
                           int threshold, int copies, int count, String[] names, Response.Listener<String> listener) {
        super(Method.POST, url, listener, null);
        params = new HashMap<>();
        params.put("title", title);
        params.put("ISBN", ISBN);
        params.put("publication_year", year+"");
        params.put("no_of_copies", copies+"");
        params.put("threshold", threshold+"");
        params.put("pic_url", pic_url);
        params.put("publisher", publisher);
        params.put("category", category);
        params.put("price", price+"");
        params.put("count", count+"");
        for(int i=0;i<count;i++){
            params.put("a"+i+"",names[i]);
        }
        for(String key : params.values()){
            System.out.println("key "+ key);
        }

    }
    public Map<String, String> getParams(){
        return params;
    }
}
