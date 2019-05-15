package com.example.yomna.bookstore.user;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.yomna.bookstore.R;

public class UserAdapter extends BaseAdapter {
    User user;
    Context context;
    private Button promoteBTN;

    public UserAdapter(Context context, User user) {
        this.user = user;
        this.context = context;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int i) {
        return user;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.listview_item, null);
        TextView name = v.findViewById(R.id.userNameTV);
        promoteBTN = v.findViewById(R.id.promoteBtn);
        promoteBTN.setBackgroundColor(Color.parseColor("#f7e0a3"));

        TextView email = v.findViewById(R.id.emailTV);
        name.setText(user.getUsername());
        email.setText(user.getEmail());
        promoteBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                promoteUser(view);
            }
        });
        v.setTag(user.getUsername());
        return v;
    }

    private void promoteUser(final View view) {
        promoteBTN.setClickable(false);
        promoteBTN.setBackgroundColor(Color.parseColor("#f09c67"));
        if (user.getIs_manager() == 0) {
            user.setIs_manager(1);
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    System.out.println("user response " + response);
                    Toast toast = Toast.makeText(context,
                            "Successfully promoted",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toast.show();
                    promoteBTN.setBackgroundColor(Color.parseColor("#" +
                            "f7e0a3"));
                    promoteBTN.setClickable(true);

                }
            };
            promoteRequest pr = new promoteRequest(user.getUsername(), responseListener);
            RequestQueue q = Volley.newRequestQueue(context);
            q.add(pr);
        } else {

            Toast toast = Toast.makeText(context,
                    "This user is already manager.",
                    Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.show();

        }
    }

}
