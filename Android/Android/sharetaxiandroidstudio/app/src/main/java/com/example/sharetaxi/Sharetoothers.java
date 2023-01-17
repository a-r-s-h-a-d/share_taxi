package com.example.sharetaxi;

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
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.GINGERBREAD) public class Sharetoothers extends Activity implements OnItemClickListener,JsonResponse {
ListView lv;

TextView tv;
SharedPreferences sh;
String soapaction="";
public static String[] reqid,flati,flongi,dlati,dlongi,from,to,available_seats,e_res;
public static String rqid,dlati1,dlongi1,amount1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sharetoothers);
		
        lv=(ListView)findViewById(R.id.listView1);
        tv=(TextView)findViewById(R.id.textView2);
        lv.setOnItemClickListener(this);
        
    	JsonReq jr= new JsonReq();
		jr.json_response=(JsonResponse)Sharetoothers.this;
		String q="/currentridetoshare?riderid=" + Login.logid;
		jr.execute(q);
		
      
    }
  

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sharetoothers, menu);
		return true;
	}
	public void response(JSONObject jo)
	{
		// TODO Auto-generated method stub
		try{
			String status=jo.getString("status");
			String method=jo.getString("method");
			if(method.equalsIgnoreCase("currentridetoshare"))
			{
				if(status.equalsIgnoreCase("success"))
				{
					JSONArray ja=(JSONArray)jo.getJSONArray("data");
					reqid=new String[ja.length()];
					from= new String[ja.length()];
					to= new String[ja.length()];
					available_seats= new String[ja.length()];
					e_res= new String[ja.length()];
					for(int i=0;i<ja.length();i++)
					{
						reqid[i]=ja.getJSONObject(i).getString("req_id");
						from[i]=ja.getJSONObject(i).getString("booking_from");
						to[i]=ja.getJSONObject(i).getString("booking_to");
						available_seats[i]=ja.getJSONObject(i).getString("availability");
						
						  e_res[i]="From                      :"+from[i]+
		                    		"\nTo                        :"+to[i]+
		                    	    "\nAvailable Seats to share  :"+available_seats[i];
		                    	           
					}
					 lv.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.cust_list,e_res));
				}
				else
				{
					tv.setText("no current ride found here");
				}
			}
			else if(method.equalsIgnoreCase("sharetoothers"))
			{
				if(status.equalsIgnoreCase("success"))
				{
					Toast.makeText(getApplicationContext(),"shared success",Toast.LENGTH_LONG).show();	
				}
				else
				{
					  Toast.makeText(getApplicationContext(),"something went wrong",Toast.LENGTH_LONG).show();
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
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		rqid=reqid[arg2];
		
		JsonReq jr= new JsonReq();
		jr.json_response=(JsonResponse)Sharetoothers.this;
		String q="/sharetoothers?riderid=" + Login.logid+"&reqid="+rqid;
		jr.execute(q);
		
	
	}

}
