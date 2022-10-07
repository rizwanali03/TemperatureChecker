package com.example.temparaturechecker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

public class rating extends AppCompatActivity {
    Button submit;
    RatingBar Rb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int w=dm.widthPixels;
        int he=dm.heightPixels;
        getWindow().setLayout((int)(w*.8),(int)(w*.6));

        submit=findViewById(R.id.btnaddrating);
        Rb=findViewById(R.id.RAratingBar);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(rating.this,MainActivity.class);
                startActivity(i);
            }
        });
        Rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                int ratin=(int) rating;



            }
        });
    }
    }
