package com.example.yomna.bookstore.user;


public interface VolleyCallbackUser {
    void onSuccess(User user);

    void onFail(String msg);
}
