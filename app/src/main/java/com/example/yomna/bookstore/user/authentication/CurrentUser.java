package com.example.yomna.bookstore.user.authentication;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by yomna on 5/4/19.
 */

public class CurrentUser {
    private static CurrentUser instance = null;

    private String username, firstName, lastName, email, phoneNumber, country, city, streetname, password;
    private  int streetNumber, isManager;

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    private JSONObject jsonObject;
    private CurrentUser(){

    }
    public static CurrentUser getInstance(){
        if (instance == null)
            instance = new CurrentUser();
        return instance;
    }

    public String getUsername() throws JSONException {
        return jsonObject.getString("username");
    }
    public String getPassword() throws JSONException {
        return jsonObject.getString("password");
    }
    public String getFirstName() throws JSONException {
        return jsonObject.getString("firstname");
    }

    public String getLastName() throws JSONException {
        return jsonObject.getString("lastname");
    }

    public String getEmail() throws JSONException {
        return jsonObject.getString("email");
    }

    public String getPhoneNumber() throws JSONException {
        return jsonObject.getString("phonenumber");
    }

    public String getCountry() throws JSONException {
        return jsonObject.getString("country");
    }

    public String getCity() throws JSONException {
        return jsonObject.getString("city");
    }

    public String getStreetname() throws JSONException {
        return jsonObject.getString("streetname");
    }

    public int getStreetNumber() throws JSONException {
        return jsonObject.getInt("streetnumber");
    }

    public boolean getIsManager() throws JSONException {
        return jsonObject.getInt("ismanager") != 0;
    }
}