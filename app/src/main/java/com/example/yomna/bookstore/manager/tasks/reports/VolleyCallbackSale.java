package com.example.yomna.bookstore.manager.tasks.reports;


import java.util.ArrayList;

public interface VolleyCallbackSale {
    void onSuccess(ArrayList<BookSales> bookSales);

    void onFail(String msg);
}
