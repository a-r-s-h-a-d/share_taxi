package com.example.sharetaxi;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.R.integer;
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

public class Advancepay extends Activity implements  JsonResponse {
EditText e,ecard,ecvv,edate;
Button b;
String amt,card,cvv,dt;
SharedPreferences sh;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_advancepay);
		e=(EditText)findViewById(R.id.editText1);
		ecard=(EditText)findViewById(R.id.editText2);
		ecvv=(EditText)findViewById(R.id.editText3);
		edate=(EditText)findViewById(R.id.editText4);
		Double amount=(Double.parseDouble(REQUEST_STATUS.val)*10)/100;
		e.setText(amount+"");
		e.setEnabled(false);
		b=(Button)findViewById(R.id.button1);
		b.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				amt=e.getText().toString();
				card=ecard.getText().toString();
				cvv=ecvv.getText().toString();
				dt=edate.getText().toString();
				
				if(card.equalsIgnoreCase(""))
				{
					
				 ecard.setError("enter card number");
				 ecard.setFocusable(true);
					
				}
				else if(card.length()!=16)
				{
					
					 ecard.setError("enter valid card number(16 digit)");
					 ecard.setFocusable(true);
						
					}
				else if(cvv.equalsIgnoreCase(""))
				{
					
					 ecvv.setError("enter your cvv");
					 ecvv.setFocusable(true);
						
					}
				else if(cvv.length()!=3)
				{
					
					 ecvv.setError("enter valid cvv (3 digit)");
					 ecvv.setFocusable(true);
						
					}
				else if(dt.equalsIgnoreCase(""))
				{
					
					 edate.setError("enter valid date");
					 edate.setFocusable(true);
						
					}
				
				else 
				{
					JsonReq jr= new JsonReq();
					jr.json_response=(JsonResponse)Advancepay.this;
					String q="/advance?amt=" + amt+"&bookid="+REQUEST_STATUS.bid+"&total="+REQUEST_STATUS.val;
					jr.execute(q);
					
		
				}
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.advancepay, menu);
		return true;
	}

	@Override
	public void response(JSONObject jo) {
		// TODO Auto-generated method stub
		try{
			String status=jo.getString("status");
		
			if(status.equalsIgnoreCase("success"))
			{
				Toast.makeText(getApplicationContext(), "payment success ", Toast.LENGTH_LONG).show();
           	 	startActivity(new Intent(getApplicationContext(),Home.class));
           	 
			}
			else
			{
				e.setError("please enter an amount less than your balance ");
            	e.setFocusable(true);
			}
		}
		
		catch(Exception e){
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), "exp : "+e, Toast.LENGTH_LONG).show();
		}
	}

}
