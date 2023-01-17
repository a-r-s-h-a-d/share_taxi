package com.example.sharetaxi;

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
import android.util.Patterns;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class Registration extends Activity implements JsonResponse{
	EditText e1,e2,e3,e4,e5,e6;
	Button b;
	RadioButton rmale,rfemale,rother;
	String fname,lname,hname,gender,place,pincode,phone,email,uname,pass;
	String method="user_register";
	SharedPreferences sh;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);
		
		
		e1=(EditText)findViewById(R.id.editText1);
		e2=(EditText)findViewById(R.id.editText2);
		e3=(EditText)findViewById(R.id.editText3);
		e4=(EditText)findViewById(R.id.editText4);
		e5=(EditText)findViewById(R.id.editText5);
		e6=(EditText)findViewById(R.id.editText6);
	
		rmale=(RadioButton)findViewById(R.id.radiomale);
		rfemale=(RadioButton)findViewById(R.id.radiofemale);
		rother=(RadioButton)findViewById(R.id.radioother);
		b=(Button)findViewById(R.id.button1);
		b.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				fname=e1.getText().toString();
				lname=e2.getText().toString();
				phone=e3.getText().toString();
				email=e4.getText().toString();
				uname=e5.getText().toString();
				pass=e6.getText().toString();
				
				if(rmale.isChecked())
				{
					gender="male";
				}
				else if(rfemale.isChecked())
				{
					gender="female";
				}
				else if(rother.isChecked())
				{
					gender="others";
				}
				if(fname.equalsIgnoreCase(""))
				{
					
					e1.setError("please fill this field");
					e1.setFocusable(true);
				}
				else if(lname.equalsIgnoreCase(""))
				{
					
					e2.setError("please fill this field");
					e2.setFocusable(true);
				}
				
				else if(phone.equalsIgnoreCase(""))
				{
					
					e3.setError("please fill this field");
					e3.setFocusable(true);
				}
				else if(phone.length()!=10)
				{
					
					e3.setError("enter valid phone number");
					e3.setFocusable(true);
				}
				else if(email.equalsIgnoreCase(""))
				{
					
					e4.setError("please fill this field");
					e4.setFocusable(true);
				}
				else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
				{
					
					e4.setError("enter valid email address");
					e4.setFocusable(true);
				}
				else if(uname.equalsIgnoreCase(""))
				{
					
					e5.setError("please fill this field");
					e5.setFocusable(true);
				}
				else if(pass.equalsIgnoreCase(""))
				{
					
					e6.setError("please fill this field");
					e6.setFocusable(true);
				}
				else
				{
					JsonReq jr= new JsonReq();
					jr.json_response=(JsonResponse)Registration.this;
					String q="/user_register?fname=" +fname+"&lname="+lname+"&gender="+gender+"&phone="+phone+"&email="+email+"&uname="+uname+"&pass="+pass;
					jr.execute(q);
					
				
				}
			}
		});
	}
	public void response(JSONObject jo) 
	{
		// TODO Auto-generated method stub
		try
		{
			String status=jo.getString("status");
			if(status.equalsIgnoreCase("success"))
			{
				 Toast.makeText(getApplicationContext(), "success ", Toast.LENGTH_LONG).show();
				 Intent next = new Intent(getApplicationContext(),Login.class);
                 startActivity(next);
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
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.registration, menu);
		return true;
	}

	public void onBackPressed()
	{
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent b=new Intent(getApplicationContext(),Login.class);
		startActivity(b);
	}

}
