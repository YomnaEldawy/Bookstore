package com.example.yomna.bookstore.book;

import com.example.yomna.bookstore.book.Book;

import java.util.ArrayList;

public interface VolleyCallback {
    void onSuccess(ArrayList<Book> books);

    void onFail(String msg);
}
