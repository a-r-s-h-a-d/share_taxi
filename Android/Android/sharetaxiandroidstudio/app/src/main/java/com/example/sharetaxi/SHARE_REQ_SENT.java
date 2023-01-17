package com.example.sharetaxi;

import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;



import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HeterogeneousExpandableList;
import android.widget.Toast;

public class SHARE_REQ_SENT extends Activity implements JsonResponse{
Button b1,b2;
EditText e1,e2;
String no,place;
String tolatti,tolongi;
int balseat;
SharedPreferences sh;
String soapaction="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_share__req__sent);
		b1=(Button)findViewById(R.id.Button01);
		b2=(Button)findViewById(R.id.button1);
		e2=(EditText)findViewById(R.id.EditText01);
		e1=(EditText)findViewById(R.id.editText2);
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String url = "http://www.google.com/maps?saddr="+View_shared.flati1+""+","+View_shared.flongi1+""+"&&daddr="+View_shared.dlati1+","+View_shared.dlongi1;
                Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(in);
			}
		});
		b2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			no=e1.getText().toString();
			place=e2.getText().toString();

			GeocodingLocation locationAddress = new GeocodingLocation();
            locationAddress.getAddressFromLocation(place,
                    getApplicationContext(), new GeocoderHandler());
			
			}
			
		});
		
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
	 
	 public void sendreq()
	 {
		 if(Integer.parseInt(no)>Integer.parseInt(View_shared.available_seats1))
			{
				
				e1.setError("unable to send request ..  !!only "+View_shared.available_seats1+ " seats are available");
				
			}
			else
			{
				JsonReq jr= new JsonReq();
				jr.json_response=(JsonResponse)SHARE_REQ_SENT.this;
				String q="/send_share_req?uid=" + Login.logid+"&noof="+no+"&flati="+LocationService.lati+"&flongi="+LocationService.logi+"&tlati="+tolatti+"&tlongi="+tolongi+"&travelto="+place+"&vehicleid="+View_shared.vid+"&driverid="+View_shared.driverid1+"&fromplace="+LocationService.place+"&balseat="+balseat+"";
				jr.execute(q);
				
			
			}
		 
		
		 
	 }
	 public void response(JSONObject jo)
		{
			// TODO Auto-generated method stub
			try{
				String status=jo.getString("status");
		
				if(status.equalsIgnoreCase("success"))
				{
					Toast.makeText(getApplicationContext(),"sending.. success",Toast.LENGTH_LONG).show();		
	                 startActivity(new Intent(getApplicationContext(),Share_home.class));
	        
				}
				else
				{
					 Toast.makeText(getApplicationContext(),"you already sent request ",Toast.LENGTH_LONG).show();
					    
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
		getMenuInflater().inflate(R.menu.share__req__sent, menu);
		return true;
	}

}
