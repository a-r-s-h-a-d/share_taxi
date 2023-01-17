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

@TargetApi(Build.VERSION_CODES.GINGERBREAD) public class View_notification extends Activity implements OnItemClickListener,JsonResponse{
	ListView lv;
	
	String method="notification";
	String url="";
	SharedPreferences sh;

	public static String[] reqid,flati,flongi,tolati,tolongi,fromplace,toplace,cusname,contactno,date,time,e_res,nos;
    public static String reqid1,flati1,flongi1,tolati1,tolongi1,nos1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_notification);
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		
        lv=(ListView)findViewById(R.id.listView1);
        lv.setOnItemClickListener(this);
        
    	JsonReq jr= new JsonReq();
		jr.json_response=(JsonResponse)View_notification.this;
		String q="/notification?logid=" + Login.logid+"&lati="+LocationService.lati+"&longi="+LocationService.logi;
		jr.execute(q);
		
    
    }
   
	
    public void response(JSONObject jo)
    {
    	// TODO Auto-generated method stub
    	try{
    		String status=jo.getString("status");
    		String method=jo.getString("method");
    		if(method.equalsIgnoreCase("notification"))
    		{
    			if(status.equalsIgnoreCase("success"))
    			{
    				JSONArray ja=(JSONArray)jo.getJSONArray("data");
    				   reqid= new String[ja.length()];
    				      cusname= new String[ja.length()];
    				      flati= new String[ja.length()];
    				      flongi= new String[ja.length()];
    				    tolati= new String[ja.length()];
    				     tolongi= new String[ja.length()];
    				      fromplace= new String[ja.length()];
    				       toplace= new String[ja.length()];
    				       contactno= new String[ja.length()]; 
    				       date= new String[ja.length()]; 
    				       time= new String[ja.length()];
    				       nos= new String[ja.length()]; 
    			      e_res= new String[ja.length()];
    				for(int i=0;i<ja.length();i++)
    				{
    					reqid[i]=ja.getJSONObject(i).getString("req_id");
    					cusname[i]=ja.getJSONObject(i).getString("ride_fname")+" "+ja.getJSONObject(i).getString("rider_lname");
    					flati[i]=ja.getJSONObject(i).getString("flatitude");
    					flongi[i]=ja.getJSONObject(i).getString("flongitude");
    					tolati[i]=ja.getJSONObject(i).getString("tlatitude");
    					tolongi[i]=ja.getJSONObject(i).getString("tlongitude");
    					fromplace[i]=ja.getJSONObject(i).getString("booking_from");
    					toplace[i]=ja.getJSONObject(i).getString("booking_to");
    					contactno[i]=ja.getJSONObject(i).getString("rider_phnumber");
    					date[i]=ja.getJSONObject(i).getString("date");
    					time[i]=ja.getJSONObject(i).getString("time");
    					nos[i]=ja.getJSONObject(i).getString("noofpassenger");
    					e_res[i]="RIDER NAME         :"+cusname[i]+"\nNO OF TRAVELLERS :"+nos[i]+"\nFROM          :"+fromplace[i]+"\nTO                :"+toplace[i]+"\nDATE             :"+date[i]+"\n TIME              :"+time[i];            
                    }
                
					 lv.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.spin,e_res));
                }
    			else
    			{
    				Toast.makeText(getApplicationContext(),"no request near to you",Toast.LENGTH_LONG).show();
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
		getMenuInflater().inflate(R.menu.view_notification, menu);
		return true;
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
		reqid1=reqid[arg2];
		flati1=flati[arg2];
		flongi1=flongi[arg2];
		tolati1=tolati[arg2];
		tolongi1=tolongi[arg2];
		nos1=nos[arg2];
		startActivity(new Intent(getApplicationContext(),Notif_details.class));
	}

}
