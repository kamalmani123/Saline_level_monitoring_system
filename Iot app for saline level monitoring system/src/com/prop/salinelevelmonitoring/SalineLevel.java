package com.prop.salinelevelmonitoring;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.telephony.*;

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
import java.util.Timer;
import java.util.TimerTask;




public class SalineLevel extends Activity {

	String recvname="",recvname1="",recvname2="";
	String mobile="8778345283";
	private Timer timer;
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
		setContentView(R.layout.saline_level);
		
		t1 =(TextView) findViewById(R.id.textView_saline);
		t2 = (TextView) findViewById(R.id.textView_status);
	

		 Intent intent = new Intent(this, MyService.class);
		    startService(intent);
		
		b4 = (Button) findViewById(R.id.backbtn);
		b4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				try {

					//String solved = "solved";
					//new StatusUpdate().execute(solved);
					startActivity(new Intent(SalineLevel.this,AsyncronoustaskAndroidExample.class));

				}
				catch(Exception e){
					//	Toast.makeText(applicationContext.getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
				}
			}
		});
		
		timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    URL url = new URL("https://blynk.cloud/external/api/get?token=zZ9JnGXWbT4lJ-XR7JgKT6TCX0Cnec87&V0");
                    URLConnection conn = url.openConnection();
                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String inputLine;
                    
                    while ((inputLine = in.readLine()) != null) {
                        t1.setText(inputLine);
                       
                        s1=inputLine;
                    }
                    in.close();
                } 
                catch (Exception e) {
                    e.printStackTrace();
                }
                int saline=Integer.parseInt(s1);
                String status="normal";
                t2.setText(status);
                if((saline<150)&&(saline>0))
                {
                	status="critical";
               t2.setText(status);
               
                }
                else if(saline==0)
                {
                	status="Not Injected";
                	t2.setText(status);
                }
                
            }
        }, 0, 3000);
       
       
    }
	
	
	   @Override
	    protected void onDestroy() {
	        super.onDestroy();
	        timer.cancel();
	        Intent intent = new Intent(this, MyService.class);
		    stopService(intent);
	    }
		
		
	}



	


	

	
	



	    	


	

	

