package com.prop.salinelevelmonitoring;
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
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
    
public class AsyncronoustaskAndroidExample extends Activity {
	Connection conn;
	String r1,r2,r3,r4,a1,a2,a3,a4;
	TextView t1,t2,t3,t4,t5,t6,t7,t8,t9;
	EditText edt1;
	Button b1,b2;
	String regno, s1, s2, s3, s4, s5, s6,s7;
	String mobile="";
    @Override
    public void onCreate(Bundle savedInstanceState) {
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
		t5 = (TextView) findViewById(R.id.textView_address);
		t5.setText(s5);		
		t6 = (TextView) findViewById(R.id.textView_prob);
		t6.setText(s6);
		t7 = (TextView) findViewById(R.id.textView_status);
		t7.setText(s7);	
        b1=(Button)findViewById(R.id.viewbtn);
		if(s4.equals("4")) {
        b1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {			
				Intent intent = new Intent(AsyncronoustaskAndroidExample.this,SalineLevel.class);
				startActivity(intent);
			}
		});}
		else
		{
			 Toast.makeText(getApplicationContext(), "Saline bottle is not fixed to this patient",
	                    Toast.LENGTH_SHORT).show();
		}
		b2 = (Button)findViewById(R.id.backbtn);
		b2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				try {;
					startActivity(new Intent(AsyncronoustaskAndroidExample.this,MainActivity.class));
				}
				catch(Exception e){
				}
			}
		});
        
    }
    
    
    

	
	
        
    }
