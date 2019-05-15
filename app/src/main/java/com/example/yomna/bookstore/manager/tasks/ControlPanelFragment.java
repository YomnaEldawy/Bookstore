package com.example.yomna.bookstore.manager.tasks;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.yomna.bookstore.FragmentHistory;
import com.example.yomna.bookstore.R;
import com.example.yomna.bookstore.manager.tasks.orderBooks.showOrderedBooksFragment;
import com.example.yomna.bookstore.manager.tasks.addBook.AddBooksFragment;

import com.example.yomna.bookstore.manager.tasks.reports.Top10BooksFragment;
import com.example.yomna.bookstore.manager.tasks.reports.Top5CustomerFragment;
import com.example.yomna.bookstore.manager.tasks.reports.TotalSalesFragment;
import com.example.yomna.bookstore.user.searchUserFragment;


public class ControlPanelFragment extends Fragment implements View.OnClickListener {
    Button searchUserBtn;
    Button showOrderedBooks;
    Button addBooks;
    Button top10;
    Button top5;
    Button total;


    public ControlPanelFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_control_panel, container, false);
        searchUserBtn = myView.findViewById(R.id.btnSearchUser);
        showOrderedBooks = myView.findViewById(R.id.showordered_btn);
        addBooks = myView.findViewById(R.id.addBooks_btn);
        top10 = myView.findViewById(R.id.top10_btn);
        top5 = myView.findViewById(R.id.top5_btn);
        total = myView.findViewById(R.id.total_btn);
        showOrderedBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new showOrderedBooksFragment());

            }
        });
        addBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new AddBooksFragment());
            }
        });
        top10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new Top10BooksFragment());
            }
        });
        top5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new Top5CustomerFragment());
            }
        });
        total.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new TotalSalesFragment());
            }
        });

        searchUserBtn.setOnClickListener(this);

        return myView;
    }


    @Override
    public void onClick(View view) {
        setFragment(new searchUserFragment());

    }

    public void setFragment(Fragment f) {
        FragmentHistory fragmentsHistory = FragmentHistory.getInstance();
        fragmentsHistory.addFragment(f);
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.userAreaContent, f);
        fragmentTransaction.commit();
    }
}