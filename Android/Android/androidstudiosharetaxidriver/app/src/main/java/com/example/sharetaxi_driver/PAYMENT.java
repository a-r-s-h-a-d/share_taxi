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
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.GINGERBREAD) public class PAYMENT extends Activity implements JsonResponse {
	ListView lv;
	
	String method="view_payments";
	String url="";
	SharedPreferences sh;
	
	public static String[] cusname,amount,date,from,to,e_res,balance;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_payment);
		try {
            if (Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }
        } catch (Exception e) {

        }
        lv=(ListView)findViewById(R.id.listView1);
        
        JsonReq jr= new JsonReq();
		jr.json_response=(JsonResponse)PAYMENT.this;
		String q="/view_payments?logid=" + Login.logid;
		jr.execute(q);
       
    }
   

public void response(JSONObject jo)
{
	// TODO Auto-generated method stub
	try{
		String status=jo.getString("status");
		String method=jo.getString("method");
		if(method.equalsIgnoreCase("view_payments"))
		{
			if(status.equalsIgnoreCase("success"))
			{
				JSONArray ja=(JSONArray)jo.getJSONArray("data");
				cusname= new String[ja.length()];
			     amount= new String[ja.length()];
			     balance= new String[ja.length()];
			      from= new String[ja.length()];
			    to= new String[ja.length()];
			     date= new String[ja.length()];
			     
				e_res= new String[ja.length()];
				for(int i=0;i<ja.length();i++)
				{
					
					cusname[i]=ja.getJSONObject(i).getString("ride_fname")+" "+ja.getJSONObject(i).getString("rider_lname");
					amount[i]=ja.getJSONObject(i).getString("amt");
					balance[i]=ja.getJSONObject(i).getString("balance");
					from[i]=ja.getJSONObject(i).getString("booking_from");
					to[i]=ja.getJSONObject(i).getString("booking_to");
					date[i]=ja.getJSONObject(i).getString("date");
					e_res[i]="\nRider name  :"+cusname[i]+"\nTotal amount     :"+amount[i]+"\nBalance     :"+balance[i]+"\nFrom      :"+from[i]+"\nTO          :"+to[i]+"\nDATE         :"+date[i];            
                }
				 lv.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,e_res));
            }
			else
			{
				Toast.makeText(getApplicationContext(), "No data", Toast.LENGTH_LONG).show();
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
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.payment, menu);
		return true;
	}

}
