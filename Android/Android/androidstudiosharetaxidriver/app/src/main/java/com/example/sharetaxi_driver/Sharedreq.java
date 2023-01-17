
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Sharedreq extends Activity implements OnItemClickListener,JsonResponse{
ListView l;
String req_id;

String method="";
public static Double fl,flo,tla,tlo,tot;

SharedPreferences sh;
public static String rqid,from1,to1,passno;
String[] reqid,cusname,no_of_pass,e_res,from,to,flat,flongi,tolat,tolongi,amount;

String soapaction="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sharedreq);
		
		l=(ListView)findViewById(R.id.listView1);
	   l.setOnItemClickListener(this);
	   
		JsonReq jr= new JsonReq();
		jr.json_response=(JsonResponse)Sharedreq.this;
		String q="/view_share_req?driver_id=" + Login.logid;
		jr.execute(q);
		
	 
	}
	 
	  public void response(JSONObject jo)
	  {
	  	// TODO Auto-generated method stub
	  	try{
	  		String status=jo.getString("status");
	  		String method=jo.getString("method");
	  		if(method.equalsIgnoreCase("view_share_req"))
	  		{
	  			if(status.equalsIgnoreCase("success"))
	  			{
	  				JSONArray ja=(JSONArray)jo.getJSONArray("data");
	  				reqid=new String[ja.length()];
		  		     cusname= new String[ja.length()];
		  		     no_of_pass= new String[ja.length()];   
		  		     from=new String[ja.length()];
		  		     flat=new String[ja.length()];
		  		     flongi=new String[ja.length()];
		  		     tolat=new String[ja.length()];
		  		     tolongi=new String[ja.length()];
		  		     amount=new String[ja.length()];
		  		     to=new String[ja.length()];
	  				e_res= new String[ja.length()];
	  				for(int i=0;i<ja.length();i++)
	  				{
	  					reqid[i]=ja.getJSONObject(i).getString("req_id");
	  					cusname[i]=ja.getJSONObject(i).getString("ride_fname")+" "+ja.getJSONObject(i).getString("rider_lname");
	  					no_of_pass[i]=ja.getJSONObject(i).getString("noofpassenger");
	  					from[i]=ja.getJSONObject(i).getString("booking_from");
	  					flat[i]=ja.getJSONObject(i).getString("flatitude");
	  					flongi[i]=ja.getJSONObject(i).getString("flongitude");
	  					tolat[i]=ja.getJSONObject(i).getString("tlatitude");
	  					tolongi[i]=ja.getJSONObject(i).getString("tlongitude");
	  					amount[i]=ja.getJSONObject(i).getString("amt");
	  					to[i]=ja.getJSONObject(i).getString("booking_to");
	  					
	                    e_res[i]= "Customer name           :"+cusname[i]+
	                     		   "\nTravel request  from    :"+from[i] +" to "+to[i];
	  				}
					 l.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.spin,e_res));
	  			}
	  			else
	  			{
	  				 Toast.makeText(getApplicationContext(), "no current ride here ", Toast.LENGTH_LONG).show();
	            	 startActivity(new Intent(getApplicationContext(),HOME.class));
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sharedreq, menu);
		return true;
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
	from1=from[arg2];
	rqid=reqid[arg2];
	to1=to[arg2];
    passno=no_of_pass[arg2];
    
        fl=Double.parseDouble(flat[arg2]);
		flo=Double.parseDouble(flongi[arg2]);
		tla=Double.parseDouble(tolat[arg2]);
		tlo=Double.parseDouble(tolongi[arg2]);
	    String dest = Double.toString(CalculationByDistance(fl,flo,tla,tlo)+3);
	    Toast.makeText(getApplicationContext(),"distance"+ dest, Toast.LENGTH_LONG).show();
	    tot= (Double.parseDouble(dest)*Double.parseDouble(amount[arg2]));
	    Toast.makeText(getApplicationContext(),"amount vehicle"+amount[arg2], Toast.LENGTH_LONG).show();
	    tot=(double) Math.round(tot);
	    
	   // Toast.makeText(getApplicationContext(), tot+" ",Toast.LENGTH_LONG).show();
	    
    startActivity(new Intent(getApplicationContext(),ACCEPT_REJECT_sharereq.class));
		
		
		
	}

}
