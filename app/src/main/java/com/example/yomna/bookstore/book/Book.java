package com.example.yomna.bookstore.book;

import android.app.Activity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.yomna.bookstore.HomeFragment;
import com.example.yomna.bookstore.cart.list.CartFragment;
import com.example.yomna.bookstore.global.data.Fragments;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Book {
    @SerializedName("Book_ISBN") private String Book_ISBN;
    @SerializedName("title") private String title;
    @SerializedName("publication_year") private int publication_year;
    @SerializedName("price") private double price;
    @SerializedName("no_of_copies") private int no_of_copies;
    @SerializedName("threshold") private int threshold;
    @SerializedName("Category_category_id") private int category_id;
    @SerializedName("Publisher_publisher_id") private int publisher_id;
    @SerializedName("pic_url") private String pic_url;

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    Activity activity;
    public ArrayList<Integer> getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(ArrayList<Integer> author_id) {
        this.author_id = author_id;
    }

    public ArrayList<String> getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(ArrayList<String> author_name) {
        this.author_name = author_name;
    }

    private ArrayList<Integer> author_id;
    private ArrayList<String> author_name = new ArrayList<>();

    public void setBook_ISBN(String book_ISBN) {
        Book_ISBN = book_ISBN;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPublication_year(int publication_year) {
        this.publication_year = publication_year;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setNo_of_copies(int no_of_copies) {
        this.no_of_copies = no_of_copies;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public void setPublisher_id(int publisher_id) {
        this.publisher_id = publisher_id;
    }

    public String getBook_ISBN() {
        return Book_ISBN;
    }

    public String getTitle() {
        return title;
    }

    public int getPublication_year() {
        return publication_year;
    }

    public double getPrice() {
        return price;
    }

    public int getNo_of_copies() {
        return no_of_copies;
    }

    public int getThreshold() {
        return threshold;
    }

    public int getCategory_id() {
        return category_id;
    }

    public int getPublisher_id() {
        return publisher_id;
    }
    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public void getBook(final BookCallback bookCallback){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    System.out.println("getBook: response is: " + response);
                    bookCallback.onSuccess(jsonResponse);
                    Fragments fragments = Fragments.getInstance();
                    CartFragment cartFragment = fragments.getCartFrament();
                    if (cartFragment != null)
                        cartFragment.displayCartItems();
                    if (fragments.getBookProfileFragment() != null)
                        fragments.getBookProfileFragment().setViewAttributes();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        BookRequestByISBN bookRequestByISBN = new BookRequestByISBN(getBook_ISBN(), responseListener);
        RequestQueue queue = Volley.newRequestQueue(activity);
        queue.add(bookRequestByISBN);
    }

    public Book(){

    }

    public Book(String ISBN, Activity activity){
        this.Book_ISBN = ISBN;
        this.activity = activity;
        getBook(new BookCallback() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                System.out.println("on success: " + jsonObject.toString());
                try {
                    JSONArray prices = jsonObject.getJSONArray("price");
                    JSONArray titles = jsonObject.getJSONArray("title");
                    JSONArray authors = jsonObject.getJSONArray("author_name");
                    JSONArray numberOfCopies = jsonObject.getJSONArray("no_of_copies");
                    setPrice(prices.getDouble(0));
                    setTitle(titles.getString(0));
                    for (int i = 0; i < authors.length(); i++){
                        author_name.add((String) authors.get(i));
                    }
                    setNo_of_copies(numberOfCopies.getInt(0));
                    System.out.println("Book retrieved");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });

    }

}
