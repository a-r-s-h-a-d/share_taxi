package com.example.sharetaxi_driver;



import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.sax.StartElementListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class customdroplist extends ArrayAdapter<String> implements JsonResponse { 
	//needs to extend ArrayAdapter
	private String[] reqid;
    private String[]cusname;         //for custom view name item
    private String[] no_of_pass;	     //for custom view photo items
    private Activity context;       //for to get current activity context
    String method="";
    String namespace="http://tempuri.org/";
    SharedPreferences sh;
    String soapaction="";
    public customdroplist(Activity context, String[] reqid, String[] cusname, String[] no_of_pass) {
        //constructor of this class to get the values from main_activity_class

        super(context, R.layout.cusdroplist, cusname);
        this.context = context;
        this.cusname = cusname;
        this.no_of_pass = no_of_pass;
        this.reqid=reqid;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
                 //override getView() method

        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.cusdroplist, null, true);
		//cust_list_view is xml file of layout created in step no.2

        TextView t1 = (TextView) listViewItem.findViewById(R.id.textView2);
        TextView t2 = (TextView) listViewItem.findViewById(R.id.textView4);
       Button b = (Button) listViewItem.findViewById(R.id.button1);
       b.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			JsonReq jr= new JsonReq();
			jr.json_response=(JsonResponse)customdroplist.this;
			String q="/dropuser?req_id=" + reqid[position];
			jr.execute(q);
			
		}
	});

        t1.setText(cusname[position]);
        t2.setText(no_of_pass[position]);
        
       
        return  listViewItem;
    }

	@Override
	public void response(JSONObject jo) {
		// TODO Auto-generated method stub
		try{
			String status=jo.getString("status");
			String method=jo.getString("method");
			if(method.equalsIgnoreCase("dropuser"))
			{
				if(status.equalsIgnoreCase("success"))
				{
					Toast.makeText(getContext(), "drop successfully ", Toast.LENGTH_LONG).show();
	         		
		             context.startActivity(new Intent(getContext(),HOME.class));
		             
				}
				else
				{

         		   Toast.makeText(getContext(), "something went wrong please check your connection ", Toast.LENGTH_LONG).show();
      	          
         	
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			 Toast.makeText(getContext(), "Exception in changepass : " + e, Toast.LENGTH_LONG).show();
		}
	}
}
