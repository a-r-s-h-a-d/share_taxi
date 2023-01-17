package com.example.sharetaxi_driver;

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
import android.widget.TextView;
import android.widget.Toast;

public class ACCEPT_REJECT_sharereq extends Activity implements JsonResponse
{
TextView t1,t2,t3;
Button b1,b2;

SharedPreferences sh;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_accept__reject_sharereq);

		t1=(TextView)findViewById(R.id.textView3);
		t2=(TextView)findViewById(R.id.textView4);
		t3=(TextView)findViewById(R.id.textView6);
		b1=(Button)findViewById(R.id.button1);
		b2=(Button)findViewById(R.id.button2);
		t1.setText(Sharedreq.from1);
		t2.setText(Sharedreq.to1);
		t3.setText(Sharedreq.passno);
		
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				action("accepted");
			}
		});
		b2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				action("rejected");
			}
		});
		
	}
public void action(String status)
{
	JsonReq jr= new JsonReq();
	jr.json_response=(JsonResponse)ACCEPT_REJECT_sharereq.this;
	String q="/actionshare?req_id=" + Sharedreq.rqid+"&status="+status+"&total="+Sharedreq.tot+"";
	jr.execute(q);
	
	
	
}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.accept__reject_sharereq, menu);
		return true;
	}
	@Override
	public void response(JSONObject jo) 
	{
		// TODO Auto-generated method stub
		try{
			String status=jo.getString("status");
			String method=jo.getString("method");
			if(method.equalsIgnoreCase("actionshare"))
			{
				if(status.equalsIgnoreCase("success"))
				{ 
		        	 Toast.makeText(getApplicationContext(), "request   "+ status+"  successfully ", Toast.LENGTH_LONG).show();
		        	 startActivity(new Intent(getApplicationContext(),HOME.class));
				}
				else
				{
					Toast.makeText(getApplicationContext(), "something went wrong please check your connection ", Toast.LENGTH_LONG).show();
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), "exp : "+e, Toast.LENGTH_LONG).show();
		}
	}

}
