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
import android.widget.ListView;
import android.widget.Toast;

public class Complaint extends Activity implements JsonResponse {
	ListView lv;
	EditText e1;
	Button b;
	
	SharedPreferences sh;
	public static String[] message,reply,e_res;
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_complaint);
	 
    lv=(ListView)findViewById(R.id.listView1);
   e1=(EditText)findViewById(R.id.editText1);
   b=(Button)findViewById(R.id.button1);
   
	JsonReq jr= new JsonReq();
	jr.json_response=(JsonResponse)Complaint.this;
	String q="/viewcomplaint?riderid=" + Login.logid;
	jr.execute(q);
	
  
   b.setOnClickListener(new View.OnClickListener() {
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		String txt=e1.getText().toString();
		
		if(txt.equalsIgnoreCase(""))
		{
			
		 e1.setError("Enter Complaints");
		 e1.setFocusable(true);
			
		}
		else
		{
			JsonReq jr= new JsonReq();
			jr.json_response=(JsonResponse)Complaint.this;
			String q="/addcomplaint?riderid=" + Login.logid+"&msg="+txt;
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
		String method=jo.getString("method");
		if(method.equalsIgnoreCase("viewcomplaint"))
		{
			if(status.equalsIgnoreCase("success"))
			{
				JSONArray ja=(JSONArray)jo.getJSONArray("data");
				message=new String[ja.length()];
				reply= new String[ja.length()];
				e_res= new String[ja.length()];
				for(int i=0;i<ja.length();i++)
				{
					message[i]=ja.getJSONObject(i).getString("complaint");
					reply[i]=ja.getJSONObject(i).getString("reply");
					
					e_res[i]="MESSAGE  :"+message[i]+"\nREPLY :"+reply[i];   
				}
				 lv.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.cust_list,e_res));
			}
			else
			{
				//Toast.makeText(getApplicationContext(), "No Students", Toast.LENGTH_LONG).show();
			}
		}
		else if(method.equalsIgnoreCase("addcomplaint"))
		{
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
		getMenuInflater().inflate(R.menu.complaint, menu);
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
