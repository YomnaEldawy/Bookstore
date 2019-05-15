package com.example.yomna.bookstore.manager.tasks.reports;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.yomna.bookstore.R;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class Top10BooksFragment extends Fragment {
   private static ListView listView;
    ArrayList<BookSales> bookSales;
    Adapter adapter;
    RelativeLayout top10;
    public Top10BooksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View myView = inflater.inflate(R.layout.fragment_top10_books, container, false);
        bookSales = new ArrayList<BookSales>();

        listView = myView.findViewById(R.id.listView_top10);
        myView.findViewById(R.id.listView_top10).setDrawingCacheEnabled(true);
        myView.findViewById(R.id.listView_top10).measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        myView.findViewById(R.id.listView_top10).layout(0, 0, myView.getMeasuredWidth(), myView.getMeasuredHeight());
        myView.findViewById(R.id.listView_top10).buildDrawingCache(true);
        getTop10Books(new VolleyCallbackSale() {
    @Override
    public void onSuccess(ArrayList<BookSales> resultBookSales) {
        bookSales = resultBookSales;
        for(int i=0;i<bookSales.size();i++){
        System.out.println("sale "+bookSales.get(i).getISBNBookSale()+" "+bookSales.get(i).getTotalSales());
        }
        adapter = new Adapter(getActivity().getApplicationContext(), bookSales);
        listView.setAdapter(adapter);
        getWholeListViewItemsToBitmap(listView);
    }



    @Override
    public void onFail(String msg) {
    System.out.println("Failed");
    }
});

        return myView;
    }

    public void getWholeListViewItemsToBitmap(ListView p_ListView) {
        ListView listview = p_ListView;
        ListAdapter adapter = listview.getAdapter();
        int itemscount = adapter.getCount();
        int allitemsheight = 0;
        List<Bitmap> bmps = new ArrayList<Bitmap>();
        for (int i = 0; i < itemscount; i++) {
            View childView = adapter.getView(i, null, listview);
            childView.measure(
                    View.MeasureSpec.makeMeasureSpec(listview.getWidth(), View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            childView.layout(0, 0, childView.getMeasuredWidth(), childView.getMeasuredHeight());
            childView.setDrawingCacheEnabled(true);
            childView.buildDrawingCache();
            bmps.add(childView.getDrawingCache());
            allitemsheight += childView.getMeasuredHeight();
        }
        Bitmap bigbitmap = Bitmap.createBitmap(listview.getMeasuredWidth(), allitemsheight,
                Bitmap.Config.ARGB_8888);
        Canvas bigcanvas = new Canvas(bigbitmap);
        Paint paint = new Paint();
        int iHeight = 0;
        for (int i = 0; i < bmps.size(); i++) {
            Bitmap bmp = bmps.get(i);
            bigcanvas.drawBitmap(bmp, 0, iHeight, paint);
            iHeight += bmp.getHeight();
            bmp.recycle();
            bmp = null;
        }

        storeImage(bigbitmap, "Top10.jpg");

        File sdIconStorageDir = new File(Environment.getExternalStorageDirectory()
                .getAbsolutePath() + "/Bookstore/");
        // create storage directories, if they don't exist
        sdIconStorageDir.mkdirs();
        String input = Environment.getExternalStorageDirectory()
                .getAbsolutePath() + "/myAppDir/Top10.jpg";
        String filePath = sdIconStorageDir.toString() + File.separator + "Top10Books.pdf";
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Document document = new Document();
        try {

            PdfWriter writer = PdfWriter.getInstance(document, fos);
            writer.open();
            document.open();

            Font font1 = new Font(Font.FontFamily.COURIER, 32, Font.BOLD, BaseColor.BLUE);
            Paragraph g = new Paragraph();
            g.setAlignment(Paragraph.ALIGN_CENTER);
            g.setFont(font1);
            g.add("Top 10 Selling Books");
            g.setSpacingAfter(50);
            Font font2 = new Font(Font.FontFamily.COURIER, 22, Font.BOLD, BaseColor.MAGENTA);

            Paragraph g1 = new Paragraph();
            g1.setFont(font2);
            Chunk left = new Chunk("Book ISBN   ");
            g1.add(left);
            g1.setAlignment(left.ALIGN_LEFT);
            Chunk center = new Chunk("  Book Title  ");
            g1.add(center);
            g1.setAlignment(center.ALIGN_CENTER);
            Chunk right = new Chunk("  Total Sales   ");
            g1.add(right);
            g1.setAlignment(right.ALIGN_RIGHT);

            document.add(g);
            document.add(g1);
            Image img = Image.getInstance(input);
            img.scalePercent(70);
            document.add(img);
            document.close();
            writer.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }



    }

    public boolean storeImage(Bitmap imageData, String filename) {
        // get path to external storage (SD card)
        File sdIconStorageDir = new File(Environment.getExternalStorageDirectory()
                .getAbsolutePath() + "/myAppDir/");
        // create storage directories, if they don't exist
        sdIconStorageDir.mkdirs();
        try {
            String filePath = sdIconStorageDir.toString() + File.separator + filename;
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            BufferedOutputStream bos = new BufferedOutputStream(fileOutputStream);
            // choose another format if PNG doesn't suit you
            imageData.compress(Bitmap.CompressFormat.PNG, 100, bos);
            bos.flush();
            bos.close();
        } catch (FileNotFoundException e) {
            Log.w("TAG", "Error saving image file: " + e.getMessage());
            return false;
        } catch (IOException e) {
            Log.w("TAG", "Error saving image file: " + e.getMessage());
            return false;
        }
        return true;
    }

    private void getTop10Books(final VolleyCallbackSale onCallback){

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    System.out.println("Response is :" + response);
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        bookSales = toList(jsonResponse);
                        onCallback.onSuccess(toList(jsonResponse));
                    } else {
                        onCallback.onFail("No Books Found");

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        };
        getTop10Request r1 = new getTop10Request(responseListener);
        RequestQueue q1 = Volley.newRequestQueue(getActivity());
        q1.add(r1);
    }

    ArrayList<BookSales> toList(JSONObject jsonResponse) {
        ArrayList<BookSales> result = new ArrayList<BookSales>();
        try {
            JSONArray ISBNs = jsonResponse.getJSONArray("Book_ISBN");
            JSONArray titles = jsonResponse.getJSONArray("title");
            JSONArray sales = jsonResponse.getJSONArray("total_sales");
            for(int i = 0;i<ISBNs.length(); i++){
                BookSales b = new BookSales();
                String title = (String) titles.get(i);
                String ISBN = (String) ISBNs.get(i);
                int sale = Integer.parseInt(sales.get(i).toString());
                System.out.println("book "+title+" "+ISBN+" "+sale);
                b.setISBNBookSale(ISBN);
                b.setTitleBookSale(title);
                b.setTotalSales(sale);
                result.add(b);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

}
