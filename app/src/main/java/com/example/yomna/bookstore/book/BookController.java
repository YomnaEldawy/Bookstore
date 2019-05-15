package com.example.yomna.bookstore.book;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.yomna.bookstore.HomePageActivity;
import com.example.yomna.bookstore.R;
import com.example.yomna.bookstore.cart.item.AddToCartRequest;
import com.example.yomna.bookstore.global.data.Activities;
import com.example.yomna.bookstore.user.authentication.CurrentUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by yomna on 5/4/19.
 */

public class BookController {
    Book book;
    Toast mToast;
    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    Bitmap bitmap;
    Activity activity;
    public BookController(Book book){
        this.book = book;
    }

    public View getBookView(View bookOverview){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(width/2, ViewGroup.LayoutParams.WRAP_CONTENT);
        bookOverview.setLayoutParams(layoutParams);
        TextView titleTv = bookOverview.findViewById(R.id.book_title_tv);
        TextView priceTv = bookOverview.findViewById(R.id.price_value_tv);
        TextView authorTv = bookOverview.findViewById(R.id.book_author_tv);
        ImageView coverIv = bookOverview.findViewById(R.id.book_cover_iv);
        ImageView addToCartIv = bookOverview.findViewById(R.id.add_to_cart_iv);
        titleTv.setText(book.getTitle());
        priceTv.setText(book.getPrice() + " EGP");
        String authorsStr = "";
        ArrayList<String> authors = book.getAuthor_name();
        for (int i = 0; i < authors.size(); i++){
            if (authorsStr.length() > 0)
                authorsStr = authorsStr + ", " + authors.get(i);
            else{
                authorsStr = authors.get(i);
            }
        }
        authorTv.setText(authorsStr);
        setOnAddToCartListener(addToCartIv);
        setOpenBookProfileListener(coverIv);
        return bookOverview;
    }
    public void setOnAddToCartListener(View view){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                try {
                    ((ImageView) view).setColorFilter(Color.parseColor("#edb1c2"));
                }catch (Exception e){
                    view.setBackgroundColor(Color.parseColor("#85b7c4"));
                }
                Response.Listener<String> responseListener = new Response.Listener<String >() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("response: " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getBoolean("success")){
                               displayOnToast(book.getTitle() + " is added to cart");
                            }else{
                                displayOnToast(book.getTitle() + " is already in your cart");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                try {
                    AddToCartRequest addToCartRequest = new AddToCartRequest(CurrentUser.getInstance().getUsername(), book.getBook_ISBN(), responseListener);
                    RequestQueue queue = Volley.newRequestQueue(activity);
                    queue.add(addToCartRequest);
                } catch (JSONException e) {

                }
            }
        });
    }

    public void setOpenBookProfileListener(View view){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Activities activities = Activities.getInstance();
                HomePageActivity homePageActivity = activities.getHomePageActivity();
                BookProfileFragment bookProfileFragment = new BookProfileFragment();
                bookProfileFragment.setBook(book);
                homePageActivity.setFragment(bookProfileFragment);
            }
        });
    }

    void displayOnToast(String message){
        if (mToast != null)
            mToast.cancel();
        mToast = Toast.makeText(activity.getApplicationContext(), message, Toast.LENGTH_SHORT);
        mToast.show();
    }


}
