package com.example.yomna.bookstore.manager.tasks.addBook;


import java.util.ArrayList;

public interface VolleyCallbackAuthor {
    void onSuccess(ArrayList<Author> name);

    void onFail(String msg);
}
