package com.example.sharetaxi_driver;



import android.R.color;
import android.R.drawable;
import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class custom_list extends ArrayAdapter<String> { 
	//needs to extend ArrayAdapter

    private String[]cusname;         //for custom view name item
    private String[] rating;	     //for custom view photo items
    private Activity context;       //for to get current activity context

    public custom_list(Activity context, String[] cusname, String[] rating) {
        //constructor of this class to get the values from main_activity_class

        super(context, R.layout.customlist, cusname);
        this.context = context;
        this.cusname = cusname;
        this.rating = rating;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
                 //override getView() method

        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.customlist, null, true);
		//cust_list_view is xml file of layout created in step no.2

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textView1);
        RatingBar rb = (RatingBar) listViewItem.findViewById(R.id.ratingBar1);
      //  int c=android.R.color.holo_orange_dark;
     //  setRatingStarColor(drawable.getDrawable(2), ContextCompat.getColor(getContext(),android.R.color.holo_blue_light));
      //  rb.setProgressTintList(ColorStateList.valueOf(ContextCompat.getColor(context)));
        textViewName.setText(cusname[position]);
        rb.setRating(Float.parseFloat(rating[position]));
        rb.setIsIndicator(true);
        
        return  listViewItem;
    }
}
