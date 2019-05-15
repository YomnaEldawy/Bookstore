package com.example.yomna.bookstore.user;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.yomna.bookstore.R;

import org.json.JSONException;
import org.json.JSONObject;

public class searchUserFragment extends Fragment implements View.OnClickListener {
    private ProgressBar progressBar;
    private String searchValue = "";
    private EditText searchText;
    private ImageView searchImg;
    private User user;
    private UserAdapter userAdapter;
    private ListView listView;

    public searchUserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        user = new User();
        View myView = inflater.inflate(R.layout.fragment_search_user, container, false);
        progressBar = myView.findViewById(R.id.progress);
        progressBar.setVisibility(View.GONE);
        searchText = myView.findViewById(R.id.etSearchUser);
        searchImg = myView.findViewById(R.id.searchUser_img);
        listView = myView.findViewById(R.id.listView);
        searchImg.setOnClickListener(this);

        return myView;
    }


    @Override
    public void onClick(View view) {
        searchUser(view);

    }

    private void searchUser(View view) {
        progressBar.setVisibility(View.VISIBLE);
        searchValue = searchText.getText().toString();
        getUser(new VolleyCallbackUser() {
            @Override
            public void onSuccess(User resultUser) {
                progressBar.setVisibility(View.GONE);
                user = resultUser;
                userAdapter = new UserAdapter(getActivity().getApplicationContext(), user);
                listView.setAdapter(userAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Toast.makeText(getActivity().getApplicationContext(), "Clicked user " + view.getTag(), Toast.LENGTH_SHORT).show();
                    }
                });
                System.out.println("user " + user.getUsername() + " " + user.getEmail() + " " + user.getIs_manager() + " ");

            }

            @Override
            public void onFail(String msg) {
                progressBar.setVisibility(View.GONE);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage(msg).setNegativeButton("Type a correct username", null).create().show();
            }
        });

    }

    private void getUser(final VolleyCallbackUser onCallback) {
        System.out.println("userrrrrrrrrrrrrrrrrrrrrrrrrr");
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    System.out.println("Response is :" + response);
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        user = toObject(jsonResponse);
                        onCallback.onSuccess(toObject(jsonResponse));
                    } else {
                        onCallback.onFail("Can't find user with this name");

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        };
        UserRequest ur = new UserRequest(searchValue, responseListener);
        RequestQueue q = Volley.newRequestQueue(getActivity());
        q.add(ur);


    }

    private User toObject(JSONObject jsonResponse) {
        User s = new User();
        try {
            String username = (String) jsonResponse.get("username");
            String email = (String) jsonResponse.get("email");
            int is_manager = (Integer) jsonResponse.get("is_manager");
            s.setUsername(username);
            s.setEmail(email);
            s.setIs_manager(is_manager);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return s;

    }

}
