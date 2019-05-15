package com.example.yomna.bookstore.book;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yomna.bookstore.R;
import com.example.yomna.bookstore.book.Book;

import java.io.InputStream;
import java.util.ArrayList;

public class BookListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Book> books;
    private Bitmap bitmap;
    public BookListAdapter(Context context, ArrayList<Book> books) {
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
        View v = View.inflate(context, R.layout.list_view_item, null);
        TextView titleTV = v.findViewById(R.id.book_title_tv);
        TextView priceTV = v.findViewById(R.id.price_value_tv);
        TextView authorTv = v.findViewById(R.id.book_author_tv);
        ImageView picture = v.findViewById(R.id.book_cover_iv);
        titleTV.setText(books.get(i).getTitle());
        priceTV.setText(books.get(i).getPrice()+" EGP");
        authorTv.setText(books.get(i).getAuthor_name().get(0));
        if(books.get(i).getPic_url() != null) {
            new GetImageFromUrl(picture).execute(books.get(i).getPic_url());
        }
        v.setTag(books.get(i).getBook_ISBN());
        return v;
    }
    public class GetImageFromUrl extends AsyncTask<String,Void,Bitmap>{
        public GetImageFromUrl(ImageView imageView) {
            this.imageView = imageView;
        }

        ImageView imageView;

        @Override
        protected Bitmap doInBackground(String... strings) {
            String urldisplay = strings[0];
            bitmap = null;
            try{
                InputStream is = new java.net.URL(urldisplay).openStream();
                bitmap = BitmapFactory.decodeStream(is);
            }catch (Exception ex){
                ex.printStackTrace();
            }
            return bitmap;
        }
        @Override
        protected void onPostExecute(Bitmap bitmap){

            super.onPostExecute(bitmap);
            imageView.setImageBitmap(bitmap);
        }
    }
}
