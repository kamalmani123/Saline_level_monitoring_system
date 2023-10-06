package com.prop.salinelevelmonitoring;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;



public class ViewSensorData extends Activity {

	String recvname="",recvname1="",recvname2="";
	String mobile="8778345283";
String sendername="";
	Connection conn;
	EditText edmessage;
	String complaint,area,landmark,description,date1,status,diet1,diet2,diag,dept,mobile1, mobile2, mobile3;
	Button sendmsg;
	ImageButton template;
	TextView t1,t2,t3,t4,t5,t6,t7,t8,t9;
	String s1,s2,s3,s4,s5,s6,s7,s8,username,facility;
	Button b1,b2,b3,b4 ;
	HashMap<String,String> usersList1 = null;
	ArrayList<HashMap<String,String>> usersList2 = new ArrayList<HashMap<String,String>>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.patients_report);
		
		
				
		Intent intent=getIntent();
		s1=intent.getStringExtra("name");
		
		s2=intent.getStringExtra("age");
		
		s3=intent.getStringExtra("gender");
		
		s4=intent.getStringExtra("mobileno");
		
		s5=intent.getStringExtra("address");
		
		s6=intent.getStringExtra("problem");
		
		s7=intent.getStringExtra("status");

		
		
		
		
		t1 = (TextView) findViewById(R.id.textView_collegename);
		t1.setText(s1);
		
		t2 = (TextView) findViewById(R.id.textView_addr);
		t2.setText(s2);

		t3 = (TextView) findViewById(R.id.textView_city);
		t3.setText(s3);

		t4 = (TextView) findViewById(R.id.textView_city1);
		t4.setText(s4);
		
		t5 = (TextView) findViewById(R.id.textView_addr);
		t5.setText(s5);
		
		t6 = (TextView) findViewById(R.id.textView_prob);
		t6.setText(s6);
		
		t7 = (TextView) findViewById(R.id.textView_status);
		t7.setText(s7);
		


		

		b2 = (Button)findViewById(R.id.viewbtn);
		 b2.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					try {


						String serverURL = "https://blynk.cloud/external/api/get?token=zZ9JnGXWbT4lJ-XR7JgKT6TCX0Cnec87&V1"; 
						
				        // Create Object and call AsyncTask execute Method
						new LongOperation().execute(serverURL);

					}
					catch(Exception e){
					//	Toast.makeText(applicationContext.getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
					}
				}
			});
	
		

		b4 = (Button)findViewById(R.id.backbtn);
		b4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				try {

					//String solved = "solved";
					//new StatusUpdate().execute(solved);
					startActivity(new Intent(ViewSensorData.this,ViewPatientsList.class));

				}
				catch(Exception e){
					//	Toast.makeText(applicationContext.getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
				}
			}
		});

	}

private class LongOperation  extends AsyncTask<String, Void, Boolean> {
    	
       
        private String Content;
        Exception error;
        private String Error = null;
        ProgressDialog Dialog ;
        
        protected void onPreExecute() {
        	// NOTE: You can call UI Element here.
        	
        	//UI Element
        	Dialog = new ProgressDialog(ViewSensorData.this);
			Dialog.setTitle("View Sensor Data");
            Dialog.setMessage("Downloading source..");
            Dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	        Dialog.setIndeterminate(false);
	        Dialog.setCancelable(false);
            Dialog.show();
        }

        // Call after onPreExecute method
        protected Boolean doInBackground(String... urls) {
            try {
            	
            	// Call long running operations here (perform background computation)
            	// NOTE: Don't call UI Element here.
            	
            	// Server url call by GET method
            	
            	URL url = new URL(urls[0]); // creating a url object  
                URLConnection urlConnection = url.openConnection(); // creating a urlconnection object  
            
                // wrapping the urlconnection in a bufferedreader  
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));  
                String line=bufferedReader.readLine();  
                
               // HttpGet httpget = new HttpGet(urls[0]);
               // ResponseHandler<String> responseHandler = new BasicResponseHandler();
                //Content = Client.execute(httpget, responseHandler);
                
                Content=line;
                
                  
                 			                               
                
            
            } catch (IOException e) {
                Error = e.getMessage();
                cancel(true);
            }
            
            return true;
        }
        
        protected void onPostExecute(Boolean result1) {
	    	Dialog.dismiss ( ) ;
	    	if(result1)
	    	{
                int saline=Integer.parseInt(Content);
			String status="Normal";
                if (saline>50)
                {
                	status="Critical";
                	
                	try {
			            SmsManager smsManager = SmsManager.getDefault();
			            smsManager.sendTextMessage(mobile, null, "The status is : "+status, null, null);
			            Toast.makeText(getApplicationContext(), "SMS Sent!",
			                    Toast.LENGTH_LONG).show();
			        } catch (Exception e) {
			            Toast.makeText(getApplicationContext(),
			                    "SMS failed, please try again later!",
			                    Toast.LENGTH_LONG).show();
			            e.printStackTrace();
			        }
                }
					
//					System.out.println("ELSE(JSON) LOOP EXE");
					try {// try3 open
						
						Intent intent=new Intent(ViewSensorData.this,SalineLevel.class);
						intent.putExtra("s1", Content);
						intent.putExtra("s2", status);
						
						
						startActivity(intent);			
						
					} catch (Exception e1) {
						Toast.makeText(getBaseContext(), e1.toString(),
								Toast.LENGTH_LONG).show();

					}					
				
            
	    	}else
	    	{
	    		if(error!=null)
	    		{
	    			Toast.makeText(getBaseContext(),error.getMessage().toString() ,Toast.LENGTH_LONG).show();
	    		}
	    		else
	    		{
	    			Toast.makeText(getBaseContext(),"Check your credentials!!!" ,Toast.LENGTH_LONG).show();
	    		}
	    	}
	    	super.onPostExecute(result1);
	    }
	}
	
	

	
}
