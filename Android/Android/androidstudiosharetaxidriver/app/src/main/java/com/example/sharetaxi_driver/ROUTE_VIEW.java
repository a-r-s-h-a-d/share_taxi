package com.example.sharetaxi_driver;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ROUTE_VIEW extends Activity implements JsonResponse
{
TextView t1,t2;
String method="d_view_route";

SharedPreferences sh;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_route__view);
		t1=(TextView)findViewById(R.id.textView2);
		t2=(TextView)findViewById(R.id.textView4);
		
		JsonReq jr= new JsonReq();
		jr.json_response=(JsonResponse)ROUTE_VIEW.this;
		String q="/d_view_route?drid=" + Login.logid;
		jr.execute(q);
		
	
	}
	public void response(JSONObject jo)
	{
		// TODO Auto-generated method stub
		try{
			String status=jo.getString("status");
			String method=jo.getString("method");
			if(method.equalsIgnoreCase("d_view_route"))
			{
				if(status.equalsIgnoreCase("success"))
				{
					JSONArray ja=(JSONArray)jo.getJSONArray("data");
					
	                  
	                   final String flatti=ja.getJSONObject(0).getString("flatitude");
	                   final String	 flongi=ja.getJSONObject(0).getString("flongitude");
	                   final String	 tlatti=ja.getJSONObject(0).getString("tlatitude");
	                   final String	 tlongi=ja.getJSONObject(0).getString("tlongitude");
	                   
	                   final String	 statuss=ja.getJSONObject(0).getString("status");
				
					
					if(statuss.equalsIgnoreCase("picked"))
	                   {
	                	  
	                	   t1.setVisibility(View.GONE);
	                	   t2.setOnClickListener(new View.OnClickListener() {
	                			
	                			@Override
	                			public void onClick(View arg0) {
	                				String url = "http://www.google.com/maps?saddr="+flatti+""+","+flongi+""+"&&daddr="+tlatti+","+tlongi;
	                               Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
	                               startActivity(in);
	                			
	                			}
	                		});
	                   }
	                   else if(statuss.equalsIgnoreCase("approved"))
	                   {
	                	 
	            	 t1.setOnClickListener(new View.OnClickListener() {
	         			
	         			@Override
	         			public void onClick(View arg0) {
	         				
	         				 
	         				String url = "http://www.google.com/maps?saddr="+LocationService.lati+""+","+LocationService.logi+""+"&&daddr="+flatti+","+flongi;
	                        Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
	                        startActivity(in);
	         			}
	         		});
	                t2.setOnClickListener(new View.OnClickListener() {
	         			
	         			@Override
	         			public void onClick(View arg0) {
	         				String url = "http://www.google.com/maps?saddr="+flatti+""+","+flongi+""+"&&daddr="+tlatti+","+tlongi;
	                        Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
	                        startActivity(in);
	         			
	         			}
	         		});
	                
	               }
				}
				else
				{
					t1.setText("currenty no ride is found here..");
	            	t2.setVisibility(View.GONE);
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
		getMenuInflater().inflate(R.menu.route__view, menu);
		return true;
	}

}
