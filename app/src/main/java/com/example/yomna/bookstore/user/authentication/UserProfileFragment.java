package com.example.yomna.bookstore.user.authentication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.yomna.bookstore.R;
import com.example.yomna.bookstore.user.authentication.CurrentUser;
import com.example.yomna.bookstore.user.authentication.EditUserDataRequest;
import org.json.JSONException;
import org.json.JSONObject;


public class UserProfileFragment extends Fragment implements View.OnClickListener {
    //TODO make respone
    EditText username;
    EditText password;
    EditText email;
    EditText country;
    EditText city;
    EditText phonenumber;
    EditText firstname;
    EditText lastname;
    EditText streetnumber;
    EditText streetname;
    Button saveBtn;
    CurrentUser currentUser;
    String old_username;
    String old_email;
    int ismanager;
    public UserProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_user_profile, container, false);
        currentUser = CurrentUser.getInstance();
        username = myView.findViewById(R.id.usernameET);
        password = myView.findViewById(R.id.passwordET);
        email = myView.findViewById(R.id.emailET);
        firstname = myView.findViewById(R.id.first_nameET);
        lastname = myView.findViewById(R.id.last_nameET);
        country = myView.findViewById(R.id.countryET);
        city = myView.findViewById(R.id.cityET);
        streetname = myView.findViewById(R.id.streetnameET);
        streetnumber = myView.findViewById(R.id.streetnumberET);
        phonenumber = myView.findViewById(R.id.PhoneNumberET);
        saveBtn = myView.findViewById(R.id.save_btn);
        try {
            old_username = currentUser.getUsername();
            old_email = currentUser.getEmail();
            username.setText(currentUser.getUsername());
            password.setText(currentUser.getPassword());
            email.setText(currentUser.getEmail());
            firstname.setText(currentUser.getFirstName());
            lastname.setText(currentUser.getLastName());
            country.setText(currentUser.getCountry());
            city.setText(currentUser.getCity());
            streetname.setText(currentUser.getStreetname());
            streetnumber.setText(currentUser.getStreetNumber()+"");
            phonenumber.setText(currentUser.getPhoneNumber());
            if(currentUser.getIsManager()) ismanager = 1;
            else ismanager = 0;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        saveBtn.setOnClickListener(this);
        return myView;
    }
    private void saveChanges(View view){
        saveBtn.setClickable(false);
        String usernameS = username.getText().toString();
        String passwordS = password.getText().toString();
        String emailS = email.getText().toString();
        String phone = phonenumber.getText().toString();
        String countryS = country.getText().toString();
        String cityS = city.getText().toString();
        String streetnameS = streetname.getText().toString();
        int streetnumberS = Integer.parseInt(streetnumber.getText().toString());
        String first = firstname.getText().toString();
        String last = lastname.getText().toString();
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    System.out.println(response);
                    JSONObject jsonResponse = new JSONObject(response);
                    currentUser.setJsonObject(jsonResponse);
                    boolean success = jsonResponse.getBoolean("success");
                    String msg = jsonResponse.getString("failureReason");
                    if (success) {
                        Toast toast = Toast.makeText(getActivity(),
                                "Successfully Changed",
                                Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                        toast.show();

                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setMessage(msg)
                                .setNegativeButton("Retry", null )
                                .create()
                                .show();
                    }
                    saveBtn.setClickable(true);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        EditUserDataRequest cdr = new EditUserDataRequest(old_username, old_email, ismanager,usernameS, passwordS, emailS, first,
                last, countryS, cityS, streetnameS, streetnumberS, phone,responseListener);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(cdr);
    }

    @Override
    public void onClick(View view) {
        saveChanges(view);
    }
}