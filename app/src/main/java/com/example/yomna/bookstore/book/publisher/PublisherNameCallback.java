package com.example.yomna.bookstore.book.publisher;

import java.util.ArrayList;

/**
 * Created by yomna on 5/10/19.
 */

public interface PublisherNameCallback {
    void onSuccess(String publisherName);

    void onFail(String msg);
}
