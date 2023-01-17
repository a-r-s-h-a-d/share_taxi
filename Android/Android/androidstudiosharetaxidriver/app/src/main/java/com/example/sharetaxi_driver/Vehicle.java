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
import android.widget.EditText;
import android.widget.Toast;

public class Vehicle extends Activity implements JsonResponse{
EditText e1,e2,e3,e4;
String vname,seats,vno,amount;
String method="vehicle_reg";

Button b1,b2;
SharedPreferences sh;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vehicle);
		e1=(EditText)findViewById(R.id.editText1);
		e2=(EditText)findViewById(R.id.editText2);
		e3=(EditText)findViewById(R.id.editText3);
		e4=(EditText)findViewById(R.id.editText4);
		b1=(Button)findViewById(R.id.button1);
		b2=(Button)findViewById(R.id.button2);
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				vname=e1.getText().toString();
				seats=e2.getText().toString();
				vno=e3.getText().toString();
				amount=e4.getText().toString();
				if(vname.equalsIgnoreCase(""))
				{
					e1.setError("enter vehicle name");
					e1.setFocusable(true);
				}
				else if(seats.equalsIgnoreCase(""))
				{
					e2.setError("enter vehicle name");
					e2.setFocusable(true);
				}
				else if(vno.equalsIgnoreCase(""))
				{
					e3.setError("enter vehicle name");
					e3.setFocusable(true);
				}
				else if(amount.equalsIgnoreCase(""))
				{
					e4.setError("enter vehicle name");
					e4.setFocusable(true);
				}
				else
				{
					JsonReq jr= new JsonReq();
					jr.json_response=(JsonResponse)Vehicle.this;
					String q="/vehicle_reg?vname=" + vname+"&seats="+seats+"&vno="+vno+"&amount="+amount+"&driverid="+Login.logid;
					q.replace(" ", "%20");
					jr.execute(q);
					
				
			}
			}
		});
		b2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent next = new Intent(getApplicationContext(),VEHICLELIST.class);
                startActivity(next);
			}
		});
	}
	public void response(JSONObject jo)
	{
		// TODO Auto-generated method stub
		try{
			String status=jo.getString("status");
			String method=jo.getString("method");
			 if(method.equalsIgnoreCase("vehicle_reg"))
			{
				if(status.equalsIgnoreCase("success"))
				{
					Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                    Intent next = new Intent(getApplicationContext(),Vehicle.class);
                    startActivity(next);
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
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
		getMenuInflater().inflate(R.menu.vehicle, menu);
		return true;
	}

}
