package com.example.yomna.bookstore.book;

import org.json.JSONObject;

/**
 * Created by yomna on 5/8/19.
 */

public interface BookCallback {
    void onSuccess(JSONObject jsonObject);

    void onFail(String msg);
}
