package com.example.yomna.bookstore.manager.tasks.addBook;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.yomna.bookstore.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import com.example.yomna.bookstore.manager.tasks.addBook.AddBooksRequest;



public class AddBooksFragment extends Fragment implements View.OnClickListener {
   EditText title;
   EditText ISBN;
   EditText publication_year;
   EditText price;
   EditText publisher;
   EditText category;
   EditText pic_url;
   EditText threshold;
   EditText copies;
   Button add;
   EditText authors;
  /* ArrayList<Author> authors_names;
    ArrayAdapter<String> aa;
    ListView chl*/;

    public AddBooksFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_add_book, container, false);
        title = myView.findViewById(R.id.etTitle);
        ISBN = myView.findViewById(R.id.etISBN);
        publication_year = myView.findViewById(R.id.etPublicationYear);
        price = myView.findViewById(R.id.etPrice);
        publisher = myView.findViewById(R.id.etPublisher);
        category = myView.findViewById(R.id.etCategory);
        pic_url = myView.findViewById(R.id.etUrl);
        threshold = myView.findViewById(R.id.etThreshold);
        copies = myView.findViewById(R.id.etNoOfCopies);
        add = myView.findViewById(R.id.btnAdd);
        authors = myView.findViewById(R.id.etAuthor);
       /* authors_names = new ArrayList<Author>();
         chl=(ListView) myView.findViewById(R.id.checkable_list);
        chl.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        getAuthors(new VolleyCallbackAuthor() {
            @Override
            public void onSuccess(ArrayList<Author> result) {
                authors_names = result;
                String[] items = new String[authors_names.size()];

                for(int i = 0;i<authors_names.size();i++){
                    items[i] = authors_names.get(i).getName();
                    aa=new ArrayAdapter<String>(getActivity(), R.layout.rowlayout, R.id.txt_lan,items);
                    chl.setAdapter(aa);
                    System.out.println(items[i]);
                }

            }

            @Override
            public void onFail(String msg) {
                System.out.println("Failed");

            }
        });
        //set multiple selection mode
        chl.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println (((TextView) view).getText().toString());
            }

        });
*/
        add.setOnClickListener(this);

        return  myView;
    }
    /*
    void getAuthors (final VolleyCallbackAuthor onCallback){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    System.out.println("Response is :" + response);
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        authors_names = toList(jsonResponse);
                        onCallback.onSuccess(toList(jsonResponse));
                    } else {
                        onCallback.onFail("No Authors Found");

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        };
        AuthorRequest r1 = new AuthorRequest(responseListener);
        RequestQueue q1 = Volley.newRequestQueue(getActivity());
        q1.add(r1);


    }
    ArrayList<Author> toList (JSONObject jsonResponse){
        ArrayList<Author> names = new ArrayList<Author>();
        try {
            JSONArray authors = jsonResponse.getJSONArray("author_name");
            JSONArray ids = jsonResponse.getJSONArray("id");

            for (int i =0;i<authors.length();i++){
                Author a = new Author();
                a.setId((Integer)ids.get(i));
                a.setName((String) authors.get(i));
                 names.add(a);
             }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return names;
    }
*/
    @Override
    public void onClick(View view) {
        addBooks(view);
    }

    private void addBooks(View view) {
        add.setClickable(false);

        String titleS = title.getText().toString();
        String ISBNS = ISBN.getText().toString();
        int year = Integer.parseInt(publication_year.getText().toString());
        double priceS = Double.parseDouble(price.getText().toString());
        String publisherS = publisher.getText().toString();
        String categoryS = category.getText().toString();
        String pic_urlS = pic_url.getText().toString();
        if(pic_urlS == " " || pic_urlS.isEmpty()) pic_urlS = "NULL";
        int thresholdS = Integer.parseInt(threshold.getText().toString());
        int copiesS = Integer.parseInt(copies.getText().toString());
        String[] names;
        names = authors.getText().toString().split("\n");

        int count = authors.getLineCount();
        System.out.println("count "+count);
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    System.out.println(response);
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    String msg = jsonResponse.getString("failureReason");
                    if (success) {
                        Toast toast = Toast.makeText(getActivity(),
                                "Successfully Added",
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
                    add.setClickable(true);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        AddBooksRequest cdr = new AddBooksRequest(titleS, ISBNS, year, priceS, publisherS, categoryS, pic_urlS,
                thresholdS, copiesS, count, names,responseListener);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(cdr);

    }


}
