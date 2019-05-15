package com.example.yomna.bookstore.sideBarMenu;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.yomna.bookstore.HomePageActivity;
import com.example.yomna.bookstore.R;
import com.example.yomna.bookstore.user.authentication.CurrentUser;

import org.json.JSONException;

public class SideBarMenu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle nToggle;
    private Toolbar nToolbar;
    private Activity context;
    private HomePageActivity hpa;
    public SideBarMenu(final DrawerLayout dl, final ActionBarDrawerToggle ntoggle, final Toolbar toolbar, final Activity context, final HomePageActivity homePageActivity) {
        drawerLayout = dl;
        nToggle = ntoggle;
        nToolbar = toolbar;
        this.context = context;
        hpa = homePageActivity;
        ntoggle.getDrawerArrowDrawable().setColor(Color.parseColor("#FFFFFF"));
        drawerLayout.addDrawerListener(nToggle);
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View view, float v) {
            }

            @Override
            public void onDrawerOpened(@NonNull View view) {
            }

            @Override
            public void onDrawerClosed(@NonNull View view) {

            }

            @Override
            public void onDrawerStateChanged(int i) {

            }
        });
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        nToggle.syncState();
        NavigationView navigationView = (NavigationView) context.findViewById(R.id.navigation_view);
        Menu navMenu = navigationView.getMenu();
        MenuItem controlPanelNav = navMenu.findItem(R.id.nav_control_panel);
        try {
            if (!CurrentUser.getInstance().getIsManager()){
                controlPanelNav.setVisible(false);
            }
        } catch (JSONException e) {

        }
        navigationView.setNavigationItemSelectedListener(this);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                hpa.navigateToHome();
                break;
            case R.id.nav_account:
                hpa.navigateToMyAccount();
                break;
            case R.id.nav_control_panel:
                hpa.navigateToControlPanel();
                break;
            case R.id.nav_logout:
                hpa.logout();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onBackPressed(){
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }
}
