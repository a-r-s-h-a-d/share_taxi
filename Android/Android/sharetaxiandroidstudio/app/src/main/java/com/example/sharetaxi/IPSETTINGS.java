package com.example.sharetaxi;



import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

@TargetApi(Build.VERSION_CODES.GINGERBREAD) public class IPSETTINGS extends Activity {
	public static String ipval;
	 EditText e;
	 Button b;
	 SharedPreferences sh;
	 Editor ed;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipsettings);
        try {
            if (Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }
        } catch (Exception e) {
        }
        e=(EditText)findViewById(R.id.editText1);
        b=(Button)findViewById(R.id.button1);
        sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
       b.setOnClickListener(new View.OnClickListener()
       {
    	  
		@Override
		public void onClick(View arg0) 
		{
			// TODO Auto-generated method stub
			 ipval=e.getText().toString();
			 ed=sh.edit();
			 ed.putString("ipval", ipval);
			 ed.commit();

			
	if(ipval.equalsIgnoreCase(""))
	{
		e.setError("please enter ip address");
	}
	else
	{
			 startActivity(new Intent(getApplicationContext(),Login.class));
			
						 
		}
		}
	}); 
       
       e.setText(sh.getString("ipval", "")); 
       
    }
    


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.ipsetting, menu);
        return true;
    }


    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Exit  :")
                .setMessage("Are you sure you want to exit..?")
                .setPositiveButton("Yes",new DialogInterface.OnClickListener()
                {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1)
                    {
                        // TODO Auto-generated method stub
                        Intent i=new Intent(Intent.ACTION_MAIN);
                        i.addCategory(Intent.CATEGORY_HOME);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        finish();
                    }
                }).setNegativeButton("No",null).show();

    }
    
}
