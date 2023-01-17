package com.example.sharetaxi;

import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SEND_REQUEST extends Activity  implements JsonResponse{

EditText e1,e2,e3,e4;
SharedPreferences sh;
Button b;
String travelto,no_of_pass,date,time,tolongi,tolatti;
String [] vehtype,noofseat,vehno;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_send__request);
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		
		e1=(EditText)findViewById(R.id.editText1);
		e2=(EditText)findViewById(R.id.editText2);
		b=(Button)findViewById(R.id.button1);
	
		b.setOnClickListener(new View.OnClickListener() 
		{
			
			@Override
			public void onClick(View arg0) 
			{
				// TODO Auto-generated method stub
				travelto=e1.getText().toString();
				no_of_pass=e2.getText().toString();
			
				if(travelto.equalsIgnoreCase(""))
				{
					
				 e1.setError("enter place");
				 e1.setFocusable(true);
				}
				else if(no_of_pass.equalsIgnoreCase(""))
				{
				 e2.setError("enter no of passengers");
				 e2.setFocusable(true);
				}
				else
				{
				GeocodingLocation locationAddress = new GeocodingLocation();
                locationAddress.getAddressFromLocation(travelto,
                        getApplicationContext(), new GeocoderHandler());
				

				
				}
			}
		});
	}
	public void sendreq()
	{
		JsonReq jr= new JsonReq();
		jr.json_response=(JsonResponse)SEND_REQUEST.this;
		String q="/sentrequest?travelto="+ travelto+"&fromplace="+LocationService.place+"&noof="+no_of_pass+"&flati="+LocationService.lati+"&flongi="+LocationService.logi+"&tlati="+tolatti+"&tlongi="+tolongi+"&uid="+Login.logid;
		q.replace(" ", "%20");
		jr.execute(q);
		
		
	}
	  private class GeocoderHandler extends Handler {
	        @Override
	        public void handleMessage(Message message) {
	        	try
	        	{
	            String locationAddress;
	            switch (message.what) {
	                case 1:
	                    Bundle bundle = message.getData();
	                    locationAddress = bundle.getString("address");
	                    break;
	                default:
	                    locationAddress = null;
	            }
	           String[] tmp=locationAddress.split("\\,");
	            Toast.makeText(getApplicationContext(), "check spelling"+tmp, Toast.LENGTH_LONG).show();
	           tolatti=tmp[0];
	           tolongi=tmp[1];
	           		sendreq(); 
	        	}
	        	catch(Exception e)
	        	{
	        		Toast.makeText(getApplicationContext(), "check spelling"+e.toString(), Toast.LENGTH_LONG).show();
	        	}
	        }
	    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.send__request, menu);
		return true;
	}
	
	@Override
	public void response(JSONObject jo) {
		// TODO Auto-generated method stub
		try{
			String status=jo.getString("status");
			Toast.makeText(getApplicationContext(), status, Toast.LENGTH_LONG).show();

			if(status.equalsIgnoreCase("success"))
			{
				 Toast.makeText(getApplicationContext(), "sending request....", Toast.LENGTH_LONG).show();
            	 Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_LONG).show();
            	 startActivity(new Intent(getApplicationContext(),Home.class));
			}
			else
			{
				Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), "exp : "+e, Toast.LENGTH_LONG).show();
		}
	}

}
