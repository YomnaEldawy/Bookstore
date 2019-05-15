package com.example.yomna.bookstore.manager.tasks.reports;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.yomna.bookstore.R;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Top5CustomerFragment extends Fragment {
    ListView listView;
    ArrayList<Customer> customers;
    AdapterCustomer adapter;

    public Top5CustomerFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_top5_customer, container, false);
        customers = new ArrayList<Customer>();
        listView = myView.findViewById(R.id.listView_top5);
        getTop5Books(new VolleyCallbackCustomer() {
            @Override
            public void onSuccess(ArrayList<Customer> resultBookSales) {
                customers = resultBookSales;
       /* Collections.sort(bookSales, new Comparator<BookSales>(){
            public int compare(BookSales s1, BookSales s2) {
                return ((Integer)s1.getTotalSales()).compareTo(s2.getTotalSales());
            }
        });*/
                for(int i=0;i<customers.size();i++){
                    System.out.println("sale "+customers.get(i).getTopUsername()+" "+customers.get(i).getTotalPurchases());
                }
                adapter = new AdapterCustomer(getActivity().getApplicationContext(), customers);
                listView.setAdapter(adapter);
                getWholeListViewItemsToBitmap(listView);

            }

            @Override
            public void onFail(String msg) {

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

        storeImage(bigbitmap, "Top5.jpg");

        File sdIconStorageDir = new File(Environment.getExternalStorageDirectory()
                .getAbsolutePath() + "/Bookstore/");
        // create storage directories, if they don't exist
        sdIconStorageDir.mkdirs();
        String input = Environment.getExternalStorageDirectory()
                .getAbsolutePath() + "/myAppDir/Top5.jpg";
        String filePath = sdIconStorageDir.toString() + File.separator + "Top5Customers.pdf";
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
            g.add("Top 5 Customers");
            g.setSpacingAfter(50);
            Font font2 = new Font(Font.FontFamily.COURIER, 24, Font.BOLD, BaseColor.MAGENTA);

            Paragraph g1 = new Paragraph();
            g1.setFont(font2);
            Chunk left = new Chunk("username   ");
            g1.add(left);
            g1.setAlignment(left.ALIGN_LEFT);
            Chunk right = new Chunk("     Total Purchases   ");
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

    private void getTop5Books(final VolleyCallbackCustomer onCallback){

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    System.out.println("Response is :" + response);
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        customers = toList(jsonResponse);
                        onCallback.onSuccess(toList(jsonResponse));
                    } else {
                        onCallback.onFail("No Books Found");

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        };
        getTop5Request r1 = new getTop5Request(responseListener);
        RequestQueue q1 = Volley.newRequestQueue(getActivity());
        q1.add(r1);
    }

    ArrayList<Customer> toList(JSONObject jsonResponse) {
        ArrayList<Customer> result = new ArrayList<Customer>();
        try {
            JSONArray usernames = jsonResponse.getJSONArray("username");
            JSONArray sales = jsonResponse.getJSONArray("total_sales");
            for(int i = 0;i<usernames.length(); i++){
                Customer b = new Customer();
                String name = (String) usernames.get(i);
                double sale = Double.parseDouble(sales.get(i).toString());
                b.setTopUsername(name);
                b.setTotalPurchases(sale);
                result.add(b);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }


}
