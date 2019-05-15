package com.example.yomna.bookstore;

import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.yomna.bookstore.book.Book;
import com.example.yomna.bookstore.book.BookController;
import com.example.yomna.bookstore.book.BookListAdapter;
import com.example.yomna.bookstore.book.BookRequestByAuthor;
import com.example.yomna.bookstore.book.BookRequestByCategory;
import com.example.yomna.bookstore.book.BookRequestByISBN;
import com.example.yomna.bookstore.book.BookRequestByPublisher;
import com.example.yomna.bookstore.book.BookRequestByTitle;
import com.example.yomna.bookstore.book.GetTopBooksRequest;
import com.example.yomna.bookstore.book.VolleyCallback;
import com.example.yomna.bookstore.global.data.Fragments;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class HomeFragment extends Fragment implements View.OnClickListener {
    private String[] items = {"Title", "Author", "ISBN", "Category", "Publisher"};
    private ArrayAdapter<String> adapter;
    private EditText searchText;
    private String searchBy = "";
    private String searchValue = "";
    private ArrayList<Book> books;
    private GridView gridView;
    private BookListAdapter bookListAdapter;
    private ProgressBar progressBar;
    private ImageView searchBtn;
    View myView;
    private ArrayList<Book> topBooks = new ArrayList<>();
    Toast mToast;
    private int booksToSkip = 0;
    LinearLayout root;

    private Boolean allBooksLoaded = false;
    private Boolean loading = false;
    private Boolean search = false;
    public HomeFragment() {
        // Required empty public constructor

    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_home, container, false);
        Spinner sp = myView.findViewById(R.id.spinner);
        searchText = myView.findViewById(R.id.etSearch);
        books = new ArrayList<Book>();
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
        // listView = findViewById(R.id.listView);
        gridView = myView.findViewById(R.id.gridView);
        progressBar = myView.findViewById(R.id.progress);
        progressBar.setVisibility(View.GONE);
        searchBtn = myView.findViewById(R.id.search_img);

        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        searchBy = "title";
                        break;
                    case 1:
                        searchBy = "author";
                        break;
                    case 2:
                        searchBy = "ISBN";
                        break;
                    case 3:
                        searchBy = "category";
                        break;
                    case 4:
                        searchBy = "publisher";
                        break;
                }
                System.out.println("Search BY :" + searchBy);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                searchBy = "title";
            }
        });
        searchBtn.setOnClickListener(this);
        ScrollView scrollView = myView.findViewById(R.id.home_scroll_view);
        setScrollViewListener(scrollView);
        root = myView.findViewById(R.id.books_ll);
        initializeBooks(topBooks);
        System.out.println("Number of books to display = " + topBooks.size());
        return myView;
    }

    public void search(View view) {
        search = true;
        progressBar.setVisibility(View.VISIBLE);
        searchValue = searchText.getText().toString();
        System.out.println("Search value :" + searchValue);
        getBooks(new VolleyCallback() {

            @Override
            public void onSuccess(ArrayList<Book> resultBooks) {
                progressBar.setVisibility(View.GONE);
                topBooks = resultBooks;
                displayBooks();
            }

            @Override
            public void onFail(String msg) {
                progressBar.setVisibility(View.GONE);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage(msg).setNegativeButton("search for another book", null).create().show();
                System.out.println("Failed");
                search = false;

            }
        });
    }

    private void getBooks(final VolleyCallback onCallback) {
        System.out.println("hereeeeeeeeeeeeeeeeeeeeeee");
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    System.out.println("Response is :" + response);
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        books = toList(jsonResponse);
                        onCallback.onSuccess(toList(jsonResponse));
                    } else {
                        onCallback.onFail("No Books Found");

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        };
        switch (searchBy) {
            case "title":
                BookRequestByTitle r1 = new BookRequestByTitle(searchValue, responseListener);
                RequestQueue q1 = Volley.newRequestQueue(getActivity());
                q1.add(r1);
                break;
            case "author":
                BookRequestByAuthor r2 = new BookRequestByAuthor(searchValue, responseListener);
                RequestQueue q2 = Volley.newRequestQueue(getActivity());
                q2.add(r2);
                break;
            case "ISBN":
                BookRequestByISBN r3 = new BookRequestByISBN(searchValue, responseListener);
                RequestQueue q3 = Volley.newRequestQueue(getActivity());
                q3.add(r3);
                break;
            case "category":
                BookRequestByCategory r4 = new BookRequestByCategory(searchValue, responseListener);
                RequestQueue q4 = Volley.newRequestQueue(getActivity());
                q4.add(r4);
                break;
            case "publisher":
                BookRequestByPublisher r5 = new BookRequestByPublisher(searchValue, responseListener);
                RequestQueue q5 = Volley.newRequestQueue(getActivity());
                q5.add(r5);
                break;

        }
    }

    private ArrayList<Book> toList(JSONObject jsonResponse) {
        ArrayList<Book> booksResult = new ArrayList<Book>();
        try {
            JSONArray titles = jsonResponse.getJSONArray("title");
            JSONArray ISBNs = jsonResponse.getJSONArray("Book_ISBN");
            JSONArray publication_years = jsonResponse.getJSONArray("publication_year");
            JSONArray prices = jsonResponse.getJSONArray("price");
            JSONArray no_of_copiess = jsonResponse.getJSONArray("no_of_copies");
            JSONArray thresholds = jsonResponse.getJSONArray("threshold");
            JSONArray category_ids = jsonResponse.getJSONArray("Category_category_id");
            JSONArray publisher_ids = jsonResponse.getJSONArray("Publisher_publisher_id");
            JSONArray pic_urls = jsonResponse.getJSONArray("pic_url");
            JSONArray author_ids = jsonResponse.getJSONArray("author_id");
            JSONArray author_names = jsonResponse.getJSONArray("author_name");

            int size = titles.length();
            for (int i = 0; i < size; i++) {

                String ISBN = (String) ISBNs.get(i);
                String title = (String) titles.get(i);
                int publication_year = (int) publication_years.get(i);
                double price = (double) prices.get(i);
                int no_of_copies = (int) no_of_copiess.get(i);
                int threshold = (int) thresholds.get(i);
                int category_id = (int) category_ids.get(i);
                int publisher_id = (int) publisher_ids.get(i);
                int author_id = (int) author_ids.get(i);
                String author_name = (String) author_names.get(i);
                if (booksResult.isEmpty()) {
                    Book b = new Book();
                    ArrayList<Integer> newId = new ArrayList<Integer>();
                    ArrayList<String> newName = new ArrayList<String>();
                    b.setBook_ISBN(ISBN);
                    b.setTitle(title);
                    b.setPublication_year(publication_year);
                    b.setPrice(price);
                    b.setNo_of_copies(no_of_copies);
                    b.setThreshold(threshold);
                    b.setCategory_id(category_id);
                    b.setPublisher_id(publisher_id);
                    newId.add(author_id);
                    b.setAuthor_id(newId);
                    newName.add(author_name);
                    b.setAuthor_name(newName);
                    if (!pic_urls.isNull(i)) {
                        String pic_url = (String) pic_urls.get(i);
                        b.setPic_url(pic_url);
                    }
                    booksResult.add(b);
                } else {
                    if (!booksResult.get(booksResult.size() - 1).getBook_ISBN().equals(ISBN)) {
                        Book b = new Book();
                        ArrayList<Integer> newId = new ArrayList<Integer>();
                        ArrayList<String> newName = new ArrayList<String>();
                        b.setBook_ISBN(ISBN);
                        b.setTitle(title);
                        b.setPublication_year(publication_year);
                        b.setPrice(price);
                        b.setNo_of_copies(no_of_copies);
                        b.setThreshold(threshold);
                        b.setCategory_id(category_id);
                        b.setPublisher_id(publisher_id);
                        newId.add(author_id);
                        b.setAuthor_id(newId);
                        newName.add(author_name);
                        b.setAuthor_name(newName);
                        if (!pic_urls.isNull(i)) {
                            String pic_url = (String) pic_urls.get(i);
                            b.setPic_url(pic_url);
                        }
                        booksResult.add(b);
                    } else {
                        ArrayList<Integer> oldId = booksResult.get(booksResult.size() - 1).getAuthor_id();
                        ArrayList<String> oldName = booksResult.get(booksResult.size() - 1).getAuthor_name();
                        oldId.add(author_id);
                        oldName.add(author_name);
                        booksResult.get(booksResult.size() - 1).setAuthor_id(oldId);
                        booksResult.get(booksResult.size() - 1).setAuthor_name(oldName);
                    }
                }

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return booksResult;

    }


    public void displayBooks(){
        root.removeAllViews();
        int index = 0;
        for (int i = 0; i < topBooks.size()/2 + 1; i++){
            LinearLayout rowLayout = new LinearLayout(getContext());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            rowLayout.setLayoutParams(layoutParams);
            rowLayout.setOrientation(LinearLayout.HORIZONTAL);
            rowLayout.setGravity(Gravity.CENTER);
            for (int j = 0; j < 2; j++){
                if (index >= topBooks.size())
                    break;
                BookController bookController = new BookController(topBooks.get(index));
                bookController.setActivity(getActivity());
                View view = bookController.getBookView(getView().inflate(getContext(), R.layout.book_overview, null));
                rowLayout.addView(view);
                System.out.println("Book title is: " + topBooks.get(index).getTitle());
                index++;
            }
            root.addView(rowLayout);
        }
    }

    //TODO: retrieve books from database
    void initializeBooks(ArrayList<Book> books){
        loading = true;
        retrieveTopBooks(booksToSkip, 8, new VolleyCallback() {
            @Override
            public void onSuccess(ArrayList<Book> books){
                if (books.size() == 0){
                    allBooksLoaded = true;
                }
                mToast.cancel();
                loading = false;
                topBooks.addAll(books);
                System.out.println("Top books count = " + topBooks.size());
                displayBooks();
            }

            @Override
            public void onFail(String msg) {

            }
        });
        booksToSkip += 8;
    }

    void retrieveTopBooks(int booksToSkip, int booksCount, final VolleyCallback volleyCallback){
        ArrayList<Book> books = new ArrayList<>();
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    System.out.println("retrieve books response: " + response);
                    JSONObject jsonResponse = new JSONObject(response);
                    volleyCallback.onSuccess(toArrayList(jsonResponse));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        GetTopBooksRequest getTopBooksRequest = new GetTopBooksRequest(booksToSkip, booksCount , responseListener);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(getTopBooksRequest);
    }
    @Override
    public void onClick(View view) {
        search(view);
    }

    ArrayList<Book> toArrayList(JSONObject jsonObject){
        ArrayList<Book> books = new ArrayList<>();
        try {
            JSONArray ISBNs = jsonObject.getJSONArray("BookISBN");
            JSONArray titles = jsonObject.getJSONArray("title");
            JSONArray prices = jsonObject.getJSONArray("price");
            JSONArray authors = jsonObject.getJSONArray("author");
            JSONArray noOfCopies = jsonObject.getJSONArray("noOfCopies");
            JSONArray publisherIds = jsonObject.getJSONArray("publisherId");
            for (int i = 0; i < ISBNs.length(); i++) {
                Book book = new Book();
                book.setBook_ISBN(ISBNs.getString(i));
                book.setTitle(titles.getString(i));
                book.setPrice(prices.getDouble(i));
                JSONArray authorsNames = authors.getJSONArray(i);
                for (int j = 0; j < authorsNames.length(); j++){
                    book.getAuthor_name().add((String) authorsNames.get(j));
                    }
                books.add(book);
                book.setNo_of_copies(noOfCopies.getInt(i));
                book.setPublisher_id(publisherIds.getInt(0));
            }
        } catch (JSONException e) {
            allBooksLoaded = true;
        }
        return books;
    }


    private void setScrollViewListener(final ScrollView scrollView){
        scrollView.getViewTreeObserver()
                .addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                    @Override
                    public void onScrollChanged() {
                        if (scrollView.getChildAt(0).getBottom()
                                <= (scrollView.getHeight() + scrollView.getScrollY())) {
                            if (mToast != null){
                                mToast.cancel();
                            }
                            if (loading){
                                mToast = Toast.makeText(getActivity().getApplicationContext(), "Loading books", Toast.LENGTH_SHORT);
                                mToast.show();
                            }
                            if (!allBooksLoaded && !loading && !search) {
                                initializeBooks(topBooks);
                            } else if (allBooksLoaded){
                                mToast = Toast.makeText(getActivity().getApplicationContext(), "No more books to show", Toast.LENGTH_SHORT);
                                mToast.show();
                            }
                        }
                    }
                });
    }
}
