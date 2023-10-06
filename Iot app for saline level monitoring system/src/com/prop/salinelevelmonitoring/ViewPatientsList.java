package com.prop.salinelevelmonitoring;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class ViewPatientsList extends Activity {
	ListView listView;
	Connection conn;
	Double lat,lon;
	String username;
	String lati,longi,loginname,areaname1;
	String sendername;
	String collegecode;
	HashMap<String,String> usersList1 = null;
	ArrayList<HashMap<String,String>> usersList2 = new ArrayList<HashMap<String,String>>();
;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.patientsview_list);
		 listView = (ListView) findViewById(R.id.listView1);	
		try{
			new QuerySQL().execute();
		}
		catch (Exception e){
			System.out.println("NumberFormatException: " + e.getMessage());
		}
	}
	public class QuerySQL extends AsyncTask<Object, Void, Boolean> {

		ProgressDialog pDialog ;
		Exception error;
		ResultSet rs;
		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pDialog = new ProgressDialog(ViewPatientsList.this);
			pDialog.setTitle("View Patients List");
			pDialog.setMessage("Getting List...");
			pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}
		@Override
		protected Boolean doInBackground(Object... args) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://192.168.119.252:3306/saline?characterEncoding=utf8","root","root");
			} catch (SQLException se) {
				Log.e("ERRO1",se.getMessage());
			} catch (ClassNotFoundException e) {
				Log.e("ERRO22",e.getMessage());
			} catch (Exception e) {
				Log.e("ERRO3",e.getMessage());
			}
			try {
				String COMANDOSQL="select * from patientdetails";
				Statement statement = conn.createStatement();
				rs = statement.executeQuery(COMANDOSQL);
				while(rs.next()){
					usersList1 = new HashMap<String, String>();
					usersList1.put("name",rs.getString(1));
					usersList1.put("age",rs.getString(2));
					usersList1.put("gender",rs.getString(3));
					usersList1.put("mobileno",rs.getString(4));
					usersList1.put("address",rs.getString(5));
					usersList1.put("problem",rs.getString(6));
					usersList1.put("status",rs.getString(7));
					Log.d("Friend List Map :",usersList1.toString());
					usersList2.add(usersList1);
				}
				return true;
			} catch (Exception e) {
				error = e;
				return false;
			}
		}

		@SuppressLint("NewApi")
		@Override
		protected void onPostExecute(Boolean result1) {
			pDialog.dismiss ( ) ;
			if(result1)
			{
				try {

					listView.setAdapter(new ViewPatientsAdapter(ViewPatientsList.this, usersList2));
					listView.setOnItemClickListener(new OnItemClickListener() {

						public void onItemClick(AdapterView<?> parent, View v,
												int position, long id) {
							Intent intent = new Intent(
									ViewPatientsList.this,
									AsyncronoustaskAndroidExample.class);
							intent.putExtra("name", usersList2.get(position)
									.get("name"));
							
							intent.putExtra("age", usersList2.get(position)
									.get("age"));
							
							intent.putExtra("gender", usersList2.get(position)
									.get("gender"));
							
							intent.putExtra("mobileno", usersList2.get(position)
									.get("mobileno"));
							
							intent.putExtra("address", usersList2.get(position)
									.get("address"));
							
							intent.putExtra("problem", usersList2.get(position)
									.get("problem"));
							
							intent.putExtra("status", usersList2.get(position)
									.get("status"));				
							startActivity(intent);
						}
					});

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
			}
			super.onPostExecute(result1);
		}
	}
}
