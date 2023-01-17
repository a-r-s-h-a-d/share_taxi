package com.example.sharetaxi_driver;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.GINGERBREAD) public class RATINGS extends Activity implements JsonResponse {
	ListView lv;
	String namespace="http://tempuri.org/";
	String method="view_ratings";
	String url="";
	SharedPreferences sh;
	String soapaction=namespace+method;
	public static String[] cusname,rating;
   
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ratings);
		
		
        lv=(ListView)findViewById(R.id.listView1);
   
        JsonReq jr= new JsonReq();
		jr.json_response=(JsonResponse)RATINGS.this;
		String q="/view_ratings?logid=" + Login.logid;
		jr.execute(q);
		
     
    }
   
	
    public void response(JSONObject jo)
    {
    	// TODO Auto-generated method stub
    	try{
    		String status=jo.getString("status");
    		String method=jo.getString("method");
    		if(method.equalsIgnoreCase("view_ratings"))
    		{
    			if(status.equalsIgnoreCase("success"))
    			{
    				JSONArray ja=(JSONArray)jo.getJSONArray("data");
    				 cusname= new String[ja.length()];
    			      rating= new String[ja.length()]; 
    				for(int i=0;i<ja.length();i++)
    				{
    					cusname[i]=ja.getJSONObject(i).getString("ride_fname")+" "+ja.getJSONObject(i).getString("rider_lname");
    					rating[i]=ja.getJSONObject(i).getString("rating");
    					
    				}
    				 lv.setAdapter(new custom_list(this, cusname, rating));
    			}
    			else
    			{
    				Toast.makeText(getApplicationContext(), "No ratings", Toast.LENGTH_LONG).show();
    			}
    		}
    		
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    		Toast.makeText(getApplicationContext(), "exp : "+e, Toast.LENGTH_LONG).show();
    	}
    	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.rating, menu);
		return true;
	}
	

}
