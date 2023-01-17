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
import android.widget.EditText;
import android.widget.Toast;

public class Feedback extends Activity implements JsonResponse{
EditText e1;
Button b;
SharedPreferences sh;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedback);
		e1=(EditText)findViewById(R.id.editText1);
		b=(Button)findViewById(R.id.button1);
		b.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String msg=e1.getText().toString();
				if(msg.equalsIgnoreCase(""))
				{
				 e1.setError("enter feedback");
				 e1.setFocusable(true);	
				}
				else
				{
					JsonReq jr= new JsonReq();
					jr.json_response=(JsonResponse)Feedback.this;
					String q="/feedback?uid=" + Login.logid+"&msg="+msg;
					jr.execute(q);
		
				}
				
			}
		});
	}
	public void response(JSONObject jo)
	{
		// TODO Auto-generated method stub
		try{
			String status=jo.getString("status");
	
			if(status.equalsIgnoreCase("success"))
			{
				  Toast.makeText(getApplicationContext(),"added success",Toast.LENGTH_LONG).show();
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
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.feedback, menu);
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
