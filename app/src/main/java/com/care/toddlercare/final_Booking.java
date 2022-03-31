package com.care.toddlercare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class final_Booking extends AppCompatActivity {


    String fname, user_Id;
    TextView confirm_name, confirm_date;
    CalendarView book_calendar;
    ProgressDialog progressDialog;
    FirebaseFirestore firestore;
    FirebaseAuth mauth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_booking);

        confirm_name = (TextView) findViewById(R.id.confirm_name);
        book_calendar = (CalendarView) findViewById(R.id.calendarView);

        mauth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        fname = getIntent().getStringExtra("fname");
        user_Id = getIntent().getStringExtra("user_Id");

        confirm_name.setText(fname);
        book_calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                String date = (i1+1) + "/" + i2 + "/" +i;


                Intent intent = new Intent(final_Booking.this,confirmed_booking.class);
                intent.putExtra("date",date);
                intent.putExtra("user_Id",user_Id);
                intent.putExtra("fname",fname);
                startActivity(intent);
            }
        });
    }
}