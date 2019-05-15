package com.example.yomna.bookstore.user.authentication.register;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.yomna.bookstore.HomePageActivity;
import com.example.yomna.bookstore.R;
import com.example.yomna.bookstore.user.authentication.login.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    EditText firstNameEt, lastNameEt, userNameEt, passwordEt, emailEt, streetNameEt, streetNumberEt, countryEt, cityEt, phoneNumberEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firstNameEt = findViewById(R.id.etFirstName);
        lastNameEt = findViewById(R.id.etLastName);
        userNameEt = findViewById(R.id.etUsername);
        passwordEt = findViewById(R.id.etPassword);
        emailEt = findViewById(R.id.etEmail);
        streetNameEt = findViewById(R.id.etStreetName);
        streetNumberEt = findViewById(R.id.etStreetNumber);
        countryEt = findViewById(R.id.etCountry);
        cityEt = findViewById(R.id.etCity);
        phoneNumberEt = findViewById(R.id.etPhoneNumber);
    }


    public void register(View view) {
        view.setBackgroundColor(Color.parseColor("#e8d18b"));
        final String firstName = firstNameEt.getText().toString();
        final String lastName = lastNameEt.getText().toString();
        final String password = passwordEt.getText().toString();
        final String useranme = userNameEt.getText().toString();
        final String email = emailEt.getText().toString();
        final String country = countryEt.getText().toString();
        final String city = cityEt.getText().toString();
        final String streetName = streetNameEt.getText().toString();
        final String streetNumber = streetNumberEt.getText().toString();
        final String phoneNumber = phoneNumberEt.getText().toString();

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                        builder.setMessage("Register Failed").setNegativeButton("Retry", null).create().show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        RegisterRequest registerRequest = new RegisterRequest(firstName, lastName, useranme,
                password, email, phoneNumber, country, city, Integer.parseInt(streetNumber), streetName, 0, responseListener);

        RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
        queue.add(registerRequest);
    }

    public void openLogin(View view) {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        RegisterActivity.this.startActivity(intent);
    }
}