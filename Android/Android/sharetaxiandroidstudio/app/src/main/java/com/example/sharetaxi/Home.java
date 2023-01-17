package com.example.sharetaxi;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

public class Home extends Activity implements JsonResponse {
Button b1,b2,b3,b4,b5,b6,b7;
String method="check_req";

SharedPreferences sh;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		b1=(Button)findViewById(R.id.button1);
		b2=(Button)findViewById(R.id.button2);
		b3=(Button)findViewById(R.id.button4);
		b4=(Button)findViewById(R.id.Button01);
		b5=(Button)findViewById(R.id.Button02);
		b6=(Button)findViewById(R.id.Button03);
		b7=(Button)findViewById(R.id.Button04);
		
         b6.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				startActivity(new Intent(getApplicationContext(),Add_rating.class));
			}
		});
         b7.setOnClickListener(new View.OnClickListener() {

 			@Override
 			public void onClick(View arg0) {
 				// TODO Auto-generated method stub

 				startActivity(new Intent(getApplicationContext(),Share_home.class));
 			}
 		});
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				checkreq();
				
			}
		});
	b2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(),REQUEST_STATUS.class));
			}
		});
	b3.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			startActivity(new Intent(getApplicationContext(),Login.class));
		}
	});
b4.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			startActivity(new Intent(getApplicationContext(),Complaint.class));
		}
	});
b5.setOnClickListener(new View.OnClickListener() {
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		startActivity(new Intent(getApplicationContext(),Feedback.class));
	}
});
	}
public void checkreq()
{
 	JsonReq jr= new JsonReq();
	jr.json_response=(JsonResponse)Home.this;
	String q="/check_req?uid=" + Login.logid;
	jr.execute(q);
	/*
	try {
		
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		
         SoapObject sop = new SoapObject(namespace, method);
         sop.addProperty("uid",Login.logid);      
         SoapSerializationEnvelope env = new SoapSerializationEnvelope(SoapEnvelope.VER11);
         env.setOutputSoapObject(sop);
         env.dotNet=true;
         HttpTransportSE hp = new HttpTransportSE(sh.getString("url",""));
         hp.call(soapaction, env);
         String result = env.getResponse().toString();
        // Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
         if (result.equalsIgnoreCase("failed")) {
        	 Toast.makeText(getApplicationContext(), "Sorry you already sent the request..... ", Toast.LENGTH_LONG).show();
         } else {
           
        	 startActivity(new Intent(getApplicationContext(),SEND_REQUEST.class));
         }
     } catch (Exception e) {
         Toast.makeText(getApplicationContext(), "Exception in changepass : " + e, Toast.LENGTH_LONG).show();
     }
	*/
	
	}
public void response(JSONObject jo)
{
	// TODO Auto-generated method stub
	try{
		String status=jo.getString("status");
		if(status.equalsIgnoreCase("success"))
		{
			 Toast.makeText(getApplicationContext(), "Sorry you already sent the request..... ", Toast.LENGTH_LONG).show();
		}
		else
		{
			 startActivity(new Intent(getApplicationContext(),SEND_REQUEST.class));
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
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

	public void onBackPressed()
	{
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent b=new Intent(getApplicationContext(),Home.class);
		startActivity(b);
	}

}
