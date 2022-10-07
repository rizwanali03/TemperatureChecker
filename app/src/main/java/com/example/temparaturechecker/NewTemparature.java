package com.example.temparaturechecker;
import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
public class NewTemparature extends AppCompatActivity {
    String CITY1;
    TextView city,tempara;
    EditText CITY;
    Button loc;
    ImageButton room1;
    private static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    String latitude, longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_temparature);
        ActivityCompat.requestPermissions( this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        city = findViewById(R.id.txtcity);
        tempara = findViewById(R.id.txttemp);

        room1=findViewById(R.id.btnroom);
        new weatherTask().execute();

        room1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(NewTemparature.this,roomtemparature.class);
                startActivity(i);
            }
        });
    }







    class weatherTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();



        }

        protected String doInBackground(String args[]) {
            String response = HttpRequest.excuteGet("https://api.openweathermap.org/data/2.5/weather?q="+"Attock"+"&appid=ac92fff10f3de1174690e09f4f9dd249");
            //System.out.println("Rizvi: "+response);
            return response;

        }



        @Override
        protected void onPostExecute(String result) {

            //if(result!=null){


            try {
                //Toast.makeText(NewTemparature.this,"reached",Toast.LENGTH_LONG).show();
                JSONObject jsonObj = new JSONObject(result);
                JSONObject main = jsonObj.getJSONObject("main");
                JSONObject sys = jsonObj.getJSONObject("sys");
                JSONObject wind = jsonObj.getJSONObject("wind");
                JSONObject weather = jsonObj.getJSONArray("weather").getJSONObject(0);

                Long updatedAt = jsonObj.getLong("dt");
                String updatedAtText = "Updated at: " + new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(new Date(updatedAt * 1000));
                String temp = main.getString("temp") ;
                double t=Double.parseDouble(temp);
                System.out.println(t);
                t-=273.15;
                temp=new DecimalFormat("##.#").format(t)+ "°C";


                String tempMin = main.getString("temp_min") ;
                t=Double.parseDouble(tempMin);
                t-=273.15;
                tempMin="Min Temp: " + new DecimalFormat("##.#").format(t)+ "°C";

                String address = jsonObj.getString("name") + ", " + sys.getString("country");

                city.setText(address);

                tempara.setText(temp);


            } catch (JSONException e) {

            }//}
            //else Toast.makeText(NewTemparature.this,"Error",Toast.LENGTH_LONG).show();

        }
    }
}

