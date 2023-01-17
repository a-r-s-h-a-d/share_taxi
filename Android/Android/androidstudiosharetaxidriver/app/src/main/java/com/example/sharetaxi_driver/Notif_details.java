package com.example.sharetaxi_driver;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


import android.net.Uri;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.GINGERBREAD) public class Notif_details extends Activity  implements OnItemSelectedListener,JsonResponse{
Spinner sp;
String [] vehid,vehtype,seats,vehno,amount,e_res;
String namespace="";
String method="";
String url="";
TextView t2,t3,t4;
double tot;
int available_seats;
String amt;
SharedPreferences sh;
String seats1,veh1;
double flati,flongi,tolati,tolongi;
Button b1,b2;
String soapaction="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notif_details);
		try {
            if (Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }
        } catch (Exception e) {

        }
	
		
	    
        sp=(Spinner)findViewById(R.id.spinner1);
        sp.setOnItemSelectedListener(this);
      
	   
        t2=(TextView)findViewById(R.id.textView2);
        t3=(TextView)findViewById(R.id.textView3);
        
        t4=(TextView)findViewById(R.id.textView4);
        b1=(Button)findViewById(R.id.button1);
        b2=(Button)findViewById(R.id.button2);
        b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			    
				requestresponse("approved");
				
			}
		});
        b2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			    
				requestresponse("rejected");
			}
		});
        sp.setOnItemSelectedListener(this);
        t4.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String url = "http://www.google.com/maps?saddr="+View_notification.flati1+""+","+View_notification.flongi1+""+"&&daddr="+View_notification.tolati1+","+View_notification.tolongi1;
                Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(in);
			}
		});
        
        JsonReq jr= new JsonReq();
		jr.json_response=(JsonResponse)Notif_details.this;
		String q="/view_vehicle?driverid=" + Login.logid;
		jr.execute(q);
		
    

    }
   
    public void requestresponse(String status)
    {
    	JsonReq jr= new JsonReq();
 		jr.json_response=(JsonResponse)Notif_details.this;
 		String q="/req_response?req_id=" +  View_notification.reqid1+"&status="+status+"&amount="+tot+"&driver_id="+Login.logid+"&vehid="+veh1+"&available_seats="+available_seats+"";
 		jr.execute(q);
    	
    }
		
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.notif_details, menu);
		return true;
	}
	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		veh1=vehid[arg2];
		
		seats1=seats[arg2];
	    amt=amount[arg2];
		flati=Double.parseDouble(View_notification.flati1);
		flongi=Double.parseDouble(View_notification.flongi1);
		tolati=Double.parseDouble(View_notification.tolati1);
		tolongi=Double.parseDouble(View_notification.tolongi1);
	    String dest = Double.toString(CalculationByDistance(flati,flongi,tolati,tolongi)+3);
	 //   Toast.makeText(getApplicationContext(), amt, Toast.LENGTH_LONG).show();
	    tot=Double.parseDouble(dest)*Double.parseDouble(amt);
	    tot=Math.round(tot);
	   
		if(Integer.parseInt(View_notification.nos1)>Integer.parseInt(seats1))
		{
			b1.setVisibility(View.GONE);
			b2.setVisibility(View.GONE);
			t2.setVisibility(View.GONE);
			t3.setText("unable to proceed the request using this vehicle !! \n please check the no of seats");
		}
		else
		{
			b1.setVisibility(View.VISIBLE);
			b2.setVisibility(View.VISIBLE);
			t2.setVisibility(View.VISIBLE);
			t3.setText("Rs:"+tot+" ");
			available_seats=Integer.parseInt(seats1)-Integer.parseInt(View_notification.nos1);
		}
		
	}
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

public void response(JSONObject jo)
{
	// TODO Auto-generated method stub
	try{
		String status=jo.getString("status");
		String method=jo.getString("method");
		if(method.equalsIgnoreCase("view_vehicle"))
		{
			if(status.equalsIgnoreCase("success"))
			{
				JSONArray ja=(JSONArray)jo.getJSONArray("data");
				  vehid= new String[ja.length()];
			      vehtype= new String[ja.length()];
			      seats= new String[ja.length()];
			      vehno= new String[ja.length()];
			    amount= new String[ja.length()];
				e_res= new String[ja.length()];
				for(int i=0;i<ja.length();i++)
				{
					vehid[i]=ja.getJSONObject(i).getString("vehicle_id");
					vehtype[i]=ja.getJSONObject(i).getString("vehicle_type");
					seats[i]=ja.getJSONObject(i).getString("no_of_seats");
					vehno[i]=ja.getJSONObject(i).getString("vehicle_no");
					amount[i]=ja.getJSONObject(i).getString("amount");
					

                    e_res[i]="Vehicle:"+vehtype[i]+" Seats:"+seats[i];       
                   }
					 sp.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,e_res));
               }
			else
			{
				//Toast.makeText(getApplicationContext(), "No Students", Toast.LENGTH_LONG).show();
			}
		}
		else if(method.equalsIgnoreCase("req_response"))
		{
			if(status.equalsIgnoreCase("success"))
			{
				 Toast.makeText(getApplicationContext(), "request "+status+" successfully", Toast.LENGTH_LONG).show();
	                Intent next = new Intent(getApplicationContext(), HOME.class);
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

public double CalculationByDistance(double initialLat, double initialLong,
                                    double finalLat, double finalLong){
    int R = 6371; // km (Earth radius)
    double dLat = toRadians(finalLat-initialLat);
    double dLon = toRadians(finalLong-initialLong);
    initialLat = toRadians(initialLat);
    finalLat = toRadians(finalLat);

    double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
            Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(initialLat) * Math.cos(finalLat);
    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
    return R * c;
}

public double toRadians(double deg) {
    return deg * (Math.PI/180);
}
}
