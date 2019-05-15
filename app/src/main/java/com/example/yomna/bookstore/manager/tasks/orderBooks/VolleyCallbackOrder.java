package com.example.yomna.bookstore.manager.tasks.orderBooks;

import java.util.ArrayList;

public interface VolleyCallbackOrder {
    void onSuccess(ArrayList<ManagerOrder> managerOrder);

    void onFail(String msg);
}
