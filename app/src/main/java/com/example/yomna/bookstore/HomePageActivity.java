package com.example.yomna.bookstore;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.yomna.bookstore.cart.list.CartFragment;
import com.example.yomna.bookstore.cart.list.EmptyCartRequest;
import com.example.yomna.bookstore.global.data.Activities;
import com.example.yomna.bookstore.manager.tasks.ControlPanelFragment;
import com.example.yomna.bookstore.sideBarMenu.SideBarMenu;
import com.example.yomna.bookstore.user.authentication.UserProfileFragment;
import com.example.yomna.bookstore.user.authentication.CurrentUser;
import com.example.yomna.bookstore.user.authentication.login.LoginActivity;

import org.json.JSONException;


public class HomePageActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle nToggle;
    private Toolbar nToolbar;
    private String username;

    @Override
    public void onBackPressed() {
        Fragment destination = FragmentHistory.getInstance().getFragment();
        if (destination != null)
        setFragment(destination);
        else
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        nToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        nToolbar = findViewById(R.id.toolbar);
        drawerLayout.addDrawerListener(nToggle);
        setSupportActionBar(nToolbar);
        SideBarMenu s = new SideBarMenu(drawerLayout, nToggle, nToolbar, this, this);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Activities activities = Activities.getInstance();
        activities.setHomePageActivity(this);
        setFragment( new HomeFragment());
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (nToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void navigateToHome() {
        setFragment(new HomeFragment());
    }

    public void navigateToMyAccount() {
        setFragment(new UserProfileFragment());
    }

    public void navigateToControlPanel() {
        setFragment(new ControlPanelFragment());
    }

    public void setFragment(Fragment f) {
        FragmentHistory fragmentsHistory = FragmentHistory.getInstance();
        fragmentsHistory.addFragment(f);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.userAreaContent, f);
        fragmentTransaction.commit();
    }

    public void logout(){

        AlertDialog.Builder builder = new AlertDialog.Builder(HomePageActivity.this);
        builder.setMessage("Do you want to remove all books from your cart?");
        try {
            username = CurrentUser.getInstance().getUsername();
        } catch (JSONException e) {
            username = "";
        }
        builder.setNegativeButton("No, keep items", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(HomePageActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        builder.setPositiveButton("Yes, empty my cart", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(HomePageActivity.this, LoginActivity.class);
                startActivity(intent);
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);

                    }

                };
                EmptyCartRequest emptyCartRequest = new EmptyCartRequest(username, responseListener);
                RequestQueue queue = Volley.newRequestQueue(HomePageActivity.this);
                queue.add(emptyCartRequest);
            }
        });
        builder.create().show();
        CurrentUser.getInstance().setJsonObject(null);
    }

    public void openCart(View view) {
        setFragment(new CartFragment());
    }

}

