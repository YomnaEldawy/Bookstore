package com.example.yomna.bookstore.user.authentication.login;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.yomna.bookstore.HomePageActivity;
import com.example.yomna.bookstore.R;
import com.example.yomna.bookstore.user.authentication.CurrentUser;
import com.example.yomna.bookstore.user.authentication.register.RegisterActivity;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void openRegister(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }


    public void go(View view) {
        Intent intent = new Intent(this, HomePageActivity.class);
        startActivity(intent);
    }

    public void login(View view){
        view.setBackgroundColor(Color.parseColor("#e8d18b"));
        EditText usernameEt = findViewById(R.id.etUsername);
        EditText passwordEt = findViewById(R.id.etPassword);
        String username = usernameEt.getText().toString();
        String password = passwordEt.getText().toString();
        System.out.println("Logging in");
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("Response is: " + response.toString());
                try{
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if(success){
                        CurrentUser currentUser = CurrentUser.getInstance();
                        currentUser.setJsonObject(jsonResponse);
                        System.out.println("Username is: " + CurrentUser.getInstance().getUsername());
                        Intent intent = new Intent(LoginActivity.this, HomePageActivity.class);
                        startActivity(intent);
                    }else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        builder.setMessage("Incorrect username or password").setNegativeButton("Retry", null).create().show();
                    }
                }catch (JSONException e){

                }
            }
        };

        LoginRequest loginRequest = new LoginRequest(username, password, responseListener);
        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
        queue.add(loginRequest);
    }
}
