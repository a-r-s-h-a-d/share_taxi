package com.example.sharetaxi_driver;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;



import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

public class Register extends Activity {
	EditText e1,e2,e3,e4,e5,e6,e7,e8,e9,e10,e11,e12;
	Button b,b1;
	Spinner sp;
	String data;
    String q;
    String fpth = "";
 
    String y = "";
	String path, pathname;
	int flag = 0;
	String [] exp={"less than 1 year","1 year","2 year","3 year","4 year","5 year","more than 5 year"};
	RadioButton r1,r2,r3;
	String fname,lname,hname,gender,city,pincode,phone,email,dob,license,doj,uname,pass;
	String method="driver_register";
	String namespace="http://tempuri.org/";
	SharedPreferences sh;
	File f;
	byte[] byteArray = null;

	private static final int CAMERA_CODE = 101, GALLERY_CODE = 201, CROPING_CODE = 301;
	private Uri mImageCaptureUri;
	private File outPutFile = null;
	private String imagename = "";
	String encodedImage;
	ImageView im1;

	String soapaction=namespace+method;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		e1=(EditText)findViewById(R.id.editText1);
		e2=(EditText)findViewById(R.id.editText2);
		e3=(EditText)findViewById(R.id.editText3);
		e4=(EditText)findViewById(R.id.editText4);
		e5=(EditText)findViewById(R.id.editText5);
		e6=(EditText)findViewById(R.id.editText6);
		e7=(EditText)findViewById(R.id.editText7);
		e8=(EditText)findViewById(R.id.editText8);
		e9=(EditText)findViewById(R.id.editText9);
		e10=(EditText)findViewById(R.id.editText10);
		e11=(EditText)findViewById(R.id.ed_uname);
		e12=(EditText)findViewById(R.id.ed_pass);
		sp=(Spinner)findViewById(R.id.spinner1);
		sp.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,exp));
		r1=(RadioButton)findViewById(R.id.radio0);
		r2=(RadioButton)findViewById(R.id.radio1);
		r3=(RadioButton)findViewById(R.id.radio2);
		b=(Button)findViewById(R.id.button1);
		b1=(Button)findViewById(R.id.bt_browse);
		im1=(ImageView)findViewById(R.id.imageView1);
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				flag = 1;
				selectImageOption();

			}
		});
		b.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				fname=e1.getText().toString();
				lname=e2.getText().toString();
				city=e4.getText().toString();
				hname=e3.getText().toString();
				pincode=e5.getText().toString();
				email=e6.getText().toString();
				dob=e7.getText().toString();
				phone=e8.getText().toString();
				license=e9.getText().toString();
				doj=e10.getText().toString();
				uname=e11.getText().toString();
				pass=e12.getText().toString();
				
				if(r1.isChecked())
				{
					gender="male";
				}
				else if(r2.isChecked())
				{
					gender="female";
				}
				else
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
				
				else if(hname.equalsIgnoreCase(""))
				{
					
					e3.setError("please fill this field");
					e3.setFocusable(true);
				}
				else if(city.equalsIgnoreCase(""))
				{
					
					e4.setError("please fill this field");
					e4.setFocusable(true);
				}
				else if(pincode.equalsIgnoreCase(""))
				{
					
					e5.setError("please fill this field");
					e5.setFocusable(true);
				}
				else if(pincode.length()!=6)
				{
					
					e5.setError("please enter a valid pincode");
					e5.setFocusable(true);
				}
				else if(email.equalsIgnoreCase(""))
				{
					
					e6.setError("please fill this field");
					e6.setFocusable(true);
				}else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
				{
					
					e6.setError("enter valid email address");
					e6.setFocusable(true);
				}
				
				else if(dob.equalsIgnoreCase(""))
				{
					
					e7.setError("please fill this field");
					e7.setFocusable(true);
				}
				else if(phone.equalsIgnoreCase(""))
				{
					
					e8.setError("please fill this field");
					e8.setFocusable(true);
				}
				else if(!Patterns.PHONE.matcher(phone).matches())
				{
					
					e8.setError("enter valid phone number");
					e8.setFocusable(true);
				}
				else if(phone.length()!=10)
				{
					
					e8.setError("enter valid phone number");
					e8.setFocusable(true);
				}
				else if(license.equalsIgnoreCase(""))
				{
					
					e9.setError("please fill this field");
					e9.setFocusable(true);
				}
				else if(doj.equalsIgnoreCase(""))
				{
					
					e10.setError("please fill this field");
					e10.setFocusable(true);
				}
				else if(uname.equalsIgnoreCase(""))
				{
					
					e11.setError("please fill this field");
					e11.setFocusable(true);
				}
				else if(pass.equalsIgnoreCase(""))
				{
					
					e12.setError("please fill this field");
					e12.setFocusable(true);
				}
				else
				{
					
					uploadData();
					
					
				}
			}
		});
		
	}
	 private void uploadData() {

	        try {
	        	q="http://"+IPSETTING.ipval+"/api/registration";
	            FileUpload client = new FileUpload(q);
	            client.connectForMultipart();

	            client.addFormPart("fname", fname);
	            client.addFormPart("lname", lname);
	            client.addFormPart("gender", gender);
	            client.addFormPart("hname", hname);
	            client.addFormPart("city", city);
	            client.addFormPart("pincode", pincode);
	            client.addFormPart("email", email);
	            client.addFormPart("dob", dob);
	            client.addFormPart("phone", phone);
	            client.addFormPart("license", license);
	            client.addFormPart("doj", doj);
	            client.addFormPart("exp", sp.getSelectedItem().toString());
	            client.addFormPart("uname", uname);
	            client.addFormPart("pass", pass);
	            
	            client.addFilePart("photo", "abc.jpg", byteArray);
	            
	       //     Toast.makeText(getApplicationContext(), "f"+fname+"l"+lname+"d"+dob+"e"+email+"p"+phone+"u"+uname+"p"+pa+"g"+gen+"de"+desc+"c"+city+"di"+dist+"s"+state+"c"+country+"img"+byteArray, Toast.LENGTH_LONG).show();
//	            client.addFilePart("image", "abc.jpg", bitmapdata);
	            client.finishMultipart();
	            data = client.getResponse();
	            Log.d("lllllllll", data);
	            JSONObject ob = new JSONObject(data);
//	            JSONArray ar=new JSONArray(data); 
	            if (ob.getString("status").equals("success")) {
	                Toast.makeText(getApplicationContext(), "Registered.!", Toast.LENGTH_LONG).show();
	                startActivity(new Intent(getApplicationContext(), Login.class));
	            }

	            Log.d("response=======", data);
	        } catch (Exception e) 
	        {
	//            Toast.makeText(getApplicationContext(), "Exception 123 : " + e, Toast.LENGTH_LONG).show();
	        	Toast.makeText(getApplicationContext(), "Please browse image...", Toast.LENGTH_LONG).show();

	            Log.d("jjj", e.toString());
	        }
	    }

	private void selectImageOption() {
		final CharSequence[] items = {"Capture Photo", "Choose from Gallery", "Cancel"};

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Add Photo!");
		builder.setItems(items, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int item) {

				if (items[item].equals("Capture Photo")) {
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					Date date = new Date();
					DateFormat df = new SimpleDateFormat("-mm-ss");
					imagename = df.format(date) + ".jpg";
					f = new File(android.os.Environment.getExternalStorageDirectory(), imagename);
					mImageCaptureUri = Uri.fromFile(f);
					intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
					startActivityForResult(intent, CAMERA_CODE);

				} else if (items[item].equals("Choose from Gallery")) {
					Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					startActivityForResult(i, GALLERY_CODE);

				} else if (items[item].equals("Cancel")) {
					dialog.dismiss();
				}
			}
		});
		builder.show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {


		if (flag == 1) {
			super.onActivityResult(requestCode, resultCode, data);
			if (requestCode == GALLERY_CODE && resultCode == RESULT_OK && null != data) {

				mImageCaptureUri = data.getData();
				System.out.println("Gallery Image URI: " + mImageCaptureUri);
				//   CropingIMG();

				try {

					Uri selectedImage = data.getData();
					String path = getRealPathFromURI(selectedImage);
					File file = new File(path);
					pathname = path.substring(path.lastIndexOf("/") + 1);


					// image = decodeFile(path);
					 byteArray = null;
					try {
						InputStream inputStream = new FileInputStream(file);
						ByteArrayOutputStream bos = new ByteArrayOutputStream();
						byte[] b = new byte[2048 * 8];
						int bytesRead = 0;

						while ((bytesRead = inputStream.read(b)) != -1) {
							bos.write(b, 0, bytesRead);
						}

						byteArray = bos.toByteArray();
						Bitmap bm = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
						im1.setImageBitmap(bm);
					} catch (IOException e) {

						Log.d("=err====", e.getMessage() + "");
						Toast.makeText(this, "String :" + e.getMessage().toString(), Toast.LENGTH_LONG).show();
					}

					String str = Base64.encodeToString(byteArray, Base64.DEFAULT);
					encodedImage = str;
				} catch (Exception e) {

				}
			} else if (requestCode == CAMERA_CODE && resultCode == Activity.RESULT_OK) {

				System.out.println("Camera Image URI : " + mImageCaptureUri);
				//  CropingIMG();

//	            String  path = f.getAbsolutePath();

//	            Bitmap image = decodeFile(path); //sha corrected
				try {
					Bundle extras = data.getExtras();
					Bitmap image = (Bitmap) extras.get("data");
					//Bitmap image = (Bitmap) data.getExtras().get("data");
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					image.compress(Bitmap.CompressFormat.JPEG, 100, baos);

					//im1.setImageBitmap(image);
				} catch (Exception e) {
					Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
				}

			}
		}
		
		}
	

	private String getRealPathFromURI(Uri contentURI) {
		String path;
		Cursor cursor = getContentResolver()
				.query(contentURI, null, null, null, null);
		if (cursor == null)
			path = contentURI.getPath();

		else {
			cursor.moveToFirst();
			int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
			path = cursor.getString(idx);

		}
		if (cursor != null)
			cursor.close();
		return path;
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}

}
