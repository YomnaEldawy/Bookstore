package com.example.yomna.bookstore;

import android.support.v4.app.Fragment;

import java.util.Stack;

public class FragmentHistory {
    private static FragmentHistory instance;
    private Stack<Fragment> history;
    private FragmentHistory(){
        history = new Stack<Fragment>();
    }
    public static FragmentHistory getInstance(){
        if (instance == null)
            instance = new FragmentHistory();
        return instance;
    }

    public void addFragment(Fragment fragment){
        history.push(fragment);
    }

    public Fragment getFragment(){
        if (!history.isEmpty()){
            history.pop();
        }
        if (!history.isEmpty())
            return history.pop();
        return null;
    }
}
