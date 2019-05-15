package com.example.yomna.bookstore.manager.tasks.reports;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.yomna.bookstore.R;
import com.example.yomna.bookstore.manager.tasks.orderBooks.ManagerOrder;

import java.util.ArrayList;

public class Adapter extends BaseAdapter {
    private Context context;
    private ArrayList<BookSales> books;

    public Adapter(Context context, ArrayList<BookSales> books) {
        this.context = context;
        this.books = books;
    }

    @Override
    public int getCount() {
        return books.size();
    }

    @Override
    public Object getItem(int i) {
        return books.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.report_item, null);
        TextView isbn = v.findViewById(R.id.isbnR);
        TextView title = v.findViewById(R.id.titleR);
        TextView sales = v.findViewById(R.id.salesR);
        isbn.setText(books.get(i).getISBNBookSale());
        title.setText(books.get(i).getTitleBookSale());
        sales.setText(books.get(i).getTotalSales()+"");
        return v;
    }
}
