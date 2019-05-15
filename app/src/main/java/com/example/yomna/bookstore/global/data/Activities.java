package com.example.yomna.bookstore.global.data;

import android.app.Activity;

import com.example.yomna.bookstore.HomePageActivity;
import com.example.yomna.bookstore.cart.list.CartFragment;

/**
 * Created by yomna on 5/9/19.
 */

public class Activities {

    private static Activities activities = null;

    public HomePageActivity getHomePageActivity() {
        return homePageActivity;
    }

    public void setHomePageActivity(HomePageActivity homePageActivity) {
        this.homePageActivity = homePageActivity;
    }

    HomePageActivity homePageActivity;

    private Activities(){

    }

    public static Activities getInstance(){
        if (activities == null)
            activities = new Activities();
        return activities;
    }

}
