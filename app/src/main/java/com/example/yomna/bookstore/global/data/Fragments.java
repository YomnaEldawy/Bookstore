package com.example.yomna.bookstore.global.data;

import com.example.yomna.bookstore.HomeFragment;
import com.example.yomna.bookstore.book.BookProfileFragment;
import com.example.yomna.bookstore.cart.list.CartFragment;

/**
 * Created by yomna on 5/8/19.
 */

public class Fragments {

    private static Fragments fragments = null;
    private Fragments(){

    }

    public static Fragments getInstance(){
        if (fragments == null)
            fragments = new Fragments();
        return fragments;
    }
    public CartFragment getCartFrament() {
        return cartFrament;
    }

    public void setCartFrament(CartFragment cartFrament) {
        this.cartFrament = cartFrament;
    }

    CartFragment cartFrament;

    public BookProfileFragment getBookProfileFragment() {
        return bookProfileFragment;
    }

    public void setBookProfileFragment(BookProfileFragment bookProfileFragment) {
        this.bookProfileFragment = bookProfileFragment;
    }

    BookProfileFragment bookProfileFragment;

}
