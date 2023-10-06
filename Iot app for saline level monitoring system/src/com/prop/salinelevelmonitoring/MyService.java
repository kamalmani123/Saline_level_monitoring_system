package com.prop.salinelevelmonitoring;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.SmsManager;

public class MyService extends Service {
	  private Timer timer;
	   int threshold = 150;
	   String s1;
int saline;
	  @Override
	  public void onCreate() {
	    super.onCreate();
	    timer = new Timer();
	    timer.scheduleAtFixedRate(new TimerTask() {
	      @Override
	      public void run() {
	        try {
	        	URL url = new URL("https://blynk.cloud/external/api/get?token=zZ9JnGXWbT4lJ-XR7JgKT6TCX0Cnec87&V0");
                URLConnection conn = url.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                
                while ((inputLine = in.readLine()) != null) {
                   
                    s1=inputLine;
                     saline=Integer.parseInt(s1);
                }
                in.close();
            } catch (Exception e) {
	          e.printStackTrace();
	        }
	        if((saline<threshold)&&(saline>0))
	        {
	        	sendSms();
	        }
	      }
	    }, 0, 3000);
	  }
	  private void sendSms() {
	    SmsManager smsManager = SmsManager.getDefault();
	    String message = "Room no:4 Saline level is low, please come and refill";
	    String phoneNumber = "8778345283";
	    smsManager.sendTextMessage(phoneNumber, null, message, null, null);
	  }
	  @Override
	  public void onDestroy() {
	    super.onDestroy();
	    timer.cancel();   
	  }
	  @Override
	  public IBinder onBind(Intent intent) {
	    return null;
	  }
	}