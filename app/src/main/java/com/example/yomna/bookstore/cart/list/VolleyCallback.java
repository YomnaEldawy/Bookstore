package com.example.yomna.bookstore.cart.list;

import com.example.yomna.bookstore.book.Book;
import com.example.yomna.bookstore.cart.item.CartItem;

import java.util.ArrayList;

/**
 * Created by yomna on 5/7/19.
 */

public interface VolleyCallback {
    void onSuccess(ArrayList<CartItem> cartItems);

    void onFail(String msg);
}
