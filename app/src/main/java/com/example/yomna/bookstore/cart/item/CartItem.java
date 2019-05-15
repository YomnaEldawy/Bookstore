package com.example.yomna.bookstore.cart.item;

import android.app.Activity;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.yomna.bookstore.book.Book;
import com.example.yomna.bookstore.book.BookController;
import com.example.yomna.bookstore.book.BookRequestByISBN;
import com.example.yomna.bookstore.cart.list.CartFragment;
import com.example.yomna.bookstore.global.data.Fragments;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by yomna on 5/7/19.
 */

public class CartItem {

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    Activity activity;
    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    Book book;
    int quantity;
    public CartItem(Activity activity, String ISBN, int quantity){
        this.activity = activity;
        book = new Book(ISBN, activity);
        this.quantity = quantity;
        Fragments fragments = Fragments.getInstance();
        CartFragment cartFragment = fragments.getCartFrament();
        cartFragment.displayCartItems();
    }


}
