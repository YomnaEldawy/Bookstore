package com.example.yomna.bookstore.manager.tasks.reports;

import java.util.ArrayList;

public interface VolleyCallbackCustomer {
    void onSuccess(ArrayList<Customer> customers);

    void onFail(String msg);
}
