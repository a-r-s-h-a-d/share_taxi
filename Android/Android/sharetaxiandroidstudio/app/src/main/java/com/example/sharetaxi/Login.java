package com.example.sharetaxi;

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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.GINGERBREAD) public class Login extends Activity implements JsonResponse {
	
	SharedPreferences sh;
	EditText e1,e2;
	Button b;
	String type;
	public static String logid;
	String uname,pass;
	TextView t,t1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		
		e1=(EditText)findViewById(R.id.editText1);
		e2=(EditText)findViewById(R.id.editText2);
	
		b=(Button)findViewById(R.id.button1);
		t=(TextView)findViewById(R.id.textView1);
		t1=(TextView)findViewById(R.id.tvforgot);
		t1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				startActivity(new Intent(getApplicationContext(),Forgot_password.class));
			}
		});
		t.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(),Registration.class));
				
			}
		});
		b.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				uname=e1.getText().toString();
				pass=e2.getText().toString();
				if(uname.equalsIgnoreCase(""))
				{
					
				 e1.setError("enter username");
				 e1.setFocusable(true);
					
				}
				else if(pass.equalsIgnoreCase(""))
				{
					
				 e2.setError("enter password");
				 e2.setFocusable(true);
					
				}
				else
				{
					JsonReq jr= new JsonReq();
					jr.json_response=(JsonResponse)Login.this;
					String q="/login?uname=" + uname+"&pass="+pass;
					jr.execute(q);
					
				}
			}
		});
	}
	  public void response(JSONObject jo) {
	        // TODO Auto-generated method stub
	        try {
	            String status = jo.getString("status");
	            if (status.equalsIgnoreCase("success"))
	            {
	                JSONArray ja = (JSONArray) jo.getJSONArray("data");
	                logid = ja.getJSONObject(0).getString("loginid");
	                type = ja.getJSONObject(0).getString("login_type");
	                SharedPreferences.Editor ed = sh.edit();
	                ed.putString("logid", logid);
	                ed.commit();

	                startActivity(new Intent(getApplicationContext(),Home.class));
	            	 startService(new Intent(getApplicationContext(),LocationService.class));
            
	              } 
	            else
	            {
	                Toast.makeText(getApplicationContext(), "Login failed.TRY AGAIN!!", Toast.LENGTH_LONG).show();
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
	        }
	    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	public void onBackPressed()
	{
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent b=new Intent(getApplicationContext(),IPSETTINGS.class);
		startActivity(b);
	}

}
