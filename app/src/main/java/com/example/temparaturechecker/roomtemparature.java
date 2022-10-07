package com.example.temparaturechecker;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.OffsetDateTime;
import java.util.Calendar;

public class roomtemparature extends AppCompatActivity implements SensorEventListener {
    Button progress;
    int p;
    ProgressBar cp;

    TextView time,temp;
    private SensorManager sensorManager;
    private Sensor pressure;
    private Sensor tempSensor;
    private Boolean isTemperatureSensorAvailable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roomtemparature);

        //progress=findViewById(R.id.progress);
        cp=findViewById(R.id.progressBar);
        time=findViewById(R.id.time);
        temp=findViewById(R.id.temp);
        Calendar c = Calendar.getInstance();
        String[]dayName={" ","Sunday","Monday","Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
        String month_name = month_date.format(c.getTime());
        String sDate = c.get(Calendar.HOUR_OF_DAY)
                + ":" + c.get(Calendar.MINUTE)+"\n\n"+month_name+" "+c.get(Calendar.DAY_OF_MONTH)+" "+dayName[c.get(Calendar.DAY_OF_WEEK)];


        SpannableString ss1=  new SpannableString(sDate);
        ss1.setSpan(new RelativeSizeSpan(2.5f), 0,5, 0); // set size
        ss1.setSpan(new ForegroundColorSpan(Color.rgb(244,81,30)), 0, 5, 0);// set color

        time.setText(ss1);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        if(sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) !=null) {
            //textView.setText("Temperature Sensor is available");
            tempSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
            isTemperatureSensorAvailable = true;
        }else{
            //textView.setText("Temperature Sensor is not available");
            isTemperatureSensorAvailable = false;
         }




    }
    private void updateprogressbar(){
        cp.setProgress(p);
        temp.setText("Current Temperature: "+Integer.toString(p));
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        p=(int)event.values[0];
        updateprogressbar();

          Log.d("Sensor Changed", "onSensor Change :" + event.values[0]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.d("Sensor Changed", "onSensor Change :");
    }

    protected void onResume(){
        super.onResume();
        if(isTemperatureSensorAvailable){
            sensorManager.registerListener(this,tempSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }


    @Override
    protected void onPause(){
        super.onPause();
        if(isTemperatureSensorAvailable){
            sensorManager.unregisterListener(this);
        }
    }


}