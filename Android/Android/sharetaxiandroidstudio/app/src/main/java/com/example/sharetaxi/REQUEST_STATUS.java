 package com.example.sharetaxi;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


import android.R.integer;
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

@TargetApi(Build.VERSION_CODES.GINGERBREAD) public class REQUEST_STATUS extends Activity implements OnItemClickListener,JsonResponse {
	ListView lv;
	SharedPreferences sh;
	TextView t;
	public static String[] bookid,value,vno,dlati,statuss,dlongi,driver,vehtype,seats,amount,e_res;
    public static String bid,dlati1,dlongi1,val,amount1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_request__status);
	
		 sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		t=(TextView)findViewById(R.id.textView2);
        lv=(ListView)findViewById(R.id.listView1);
        lv.setOnItemClickListener(this);
        
        
        JsonReq jr= new JsonReq();
		jr.json_response=(JsonResponse)REQUEST_STATUS.this;
		String q="/req_status?riderid=" + Login.logid;
		jr.execute(q);
        
    }
   
public void response(JSONObject jo)
{
	// TODO Auto-generated method stub
	try{
		String status=jo.getString("status");
		
		if(status.equalsIgnoreCase("success"))
		{
			JSONArray ja=(JSONArray)jo.getJSONArray("data");
			driver=new String[ja.length()];
			vehtype= new String[ja.length()];
			 seats= new String[ja.length()];
		      dlati= new String[ja.length()];
		      vno= new String[ja.length()];
		      value= new String[ja.length()];
		      statuss= new String[ja.length()];
		      dlongi= new String[ja.length()];
		      bookid= new String[ja.length()];
		      
		      amount= new String[ja.length()];
			e_res= new String[ja.length()];
			for(int i=0;i<ja.length();i++)
			{
				driver[i]=ja.getJSONObject(i).getString("driver_fname")+" "+ja.getJSONObject(i).getString("driver_lname");
				vehtype[i]=ja.getJSONObject(i).getString("vehicle_type");
				seats[i]=ja.getJSONObject(i).getString("no_of_seats");
				dlati[i]=ja.getJSONObject(i).getString("lattitude");
				vno[i]=ja.getJSONObject(i).getString("vehicle_no");
				statuss[i]=ja.getJSONObject(i).getString("status");
				dlongi[i]=ja.getJSONObject(i).getString("longitude");
				bookid[i]=ja.getJSONObject(i).getString("book_id");
				amount[i]=ja.getJSONObject(i).getString("amount");
				
				 if(statuss[i].equalsIgnoreCase("shared")||statuss[i].equalsIgnoreCase("accepted"))
                 {
                 	   
                 	  value[i] =(Double.parseDouble(amount[i]))-((Double.parseDouble(amount[i])*20)/100)+"";
                 	  e_res[i]="DRIVER NAME  :"+driver[i]+"\n VEHICLE :"+vehtype[i]+"\n VEHICLE NUMBER:"+vno[i]+"\n NO OF SEATS:"+seats[i]+"\n AMOUNT (20% off for you)    :"+value[i];            
                 }
                 
                 else
                 {
                 	 value[i] = amount[i];
                  e_res[i]="DRIVER NAME  :"+driver[i]+"\n VEHICLE :"+vehtype[i]+"\n VEHICLE NUMBER:"+vno[i]+"\n NO OF SEATS:"+seats[i]+"\n AMOUNT     :"+value[i];            
                 
                 }
             
				 lv.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.cust_list,e_res));
			}
		}
		else
		{
			Toast.makeText(getApplicationContext(), "No data", Toast.LENGTH_LONG).show();
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
		getMenuInflater().inflate(R.menu.request__statu, menu);
		return true;
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		bid=bookid[arg2];
		dlati1=dlati[arg2];
		dlongi1=dlongi[arg2];
		amount1=amount[arg2];
		val=value[arg2];
		startActivity(new Intent(getApplicationContext(),REQUESTDETAILS.class));
	}

	public void onBackPressed()
	{
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent b=new Intent(getApplicationContext(),Home.class);
		startActivity(b);
	}

}
