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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.GINGERBREAD) public class VEHICLELIST extends Activity implements JsonResponse{
	String method="vehiclelist";
	SharedPreferences sh;
	public static String[] vname,vno,seats,amount,e_res;
 
	ListView lv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vehiclelist);
		
        lv=(ListView)findViewById(R.id.listView1);
        
        JsonReq jr= new JsonReq();
		jr.json_response=(JsonResponse)VEHICLELIST.this;
		String q="/vehiclelist?logid=" + Login.logid;
		jr.execute(q);
		
       
    }
    
public void response(JSONObject jo)
{
	// TODO Auto-generated method stub
	try{
		String status=jo.getString("status");
		String method=jo.getString("method");
		if(method.equalsIgnoreCase("vehiclelist"))
		{
			if(status.equalsIgnoreCase("success"))
			{
				JSONArray ja=(JSONArray)jo.getJSONArray("data");
				  vname= new String[ja.length()];
			      vno= new String[ja.length()];
			      amount= new String[ja.length()];
			      seats= new String[ja.length()];
			      e_res= new String[ja.length()];
				for(int i=0;i<ja.length();i++)
				{
					vname[i]=ja.getJSONObject(i).getString("vehicle_type");
					vno[i]=ja.getJSONObject(i).getString("vehicle_no");
					amount[i]=ja.getJSONObject(i).getString("amount");
					seats[i]=ja.getJSONObject(i).getString("no_of_seats");
					  e_res[i]= "VEHICLE NAME        :"+vname[i]+
                    		  "\nVEHICLE NUMBER      :"+vno[i]
                    		 +"\nAMOUNT              :"+amount[i]
                    		 +"\nSEATS               :"+seats[i];    
				}
				  
				 lv.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.spin,e_res));
			}
			else
			{
				Toast.makeText(getApplicationContext(),"nothing to show you",Toast.LENGTH_LONG).show();
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
		getMenuInflater().inflate(R.menu.vehiclelist, menu);
		return true;
	}

}
