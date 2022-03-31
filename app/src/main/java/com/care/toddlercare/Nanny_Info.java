package com.care.toddlercare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class Nanny_Info extends AppCompatActivity
{
    public String userid, fname,about,email,year_experience,phone_number;
    TextView nanny_name,nanny_about,nanny_email,nanny_number;
    Button book;
    FirebaseAuth mauth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nanny_info);

        mauth = FirebaseAuth.getInstance();

        nanny_name = (TextView) findViewById(R.id.nanny_name);
        nanny_about = (TextView) findViewById(R.id.nanny_about);
        nanny_email = (TextView) findViewById(R.id.nanny_email);
        nanny_number = (TextView)findViewById(R.id.nanny_phone);
        book = (Button)findViewById(R.id.book);

        userid = getIntent().getStringExtra("user_Id");
        fname = getIntent().getStringExtra("first_name");
        email = getIntent().getStringExtra("email");
        about = getIntent().getStringExtra("about");
        year_experience = getIntent().getStringExtra("year_experience");
        phone_number = getIntent().getStringExtra("phone_number");

        nanny_name.setText(fname);
        nanny_email.setText(email);
        nanny_about.setText(about);
        nanny_number.setText(year_experience);


        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Nanny_Info.this,final_Booking.class);
                intent.putExtra("fname",fname);
                intent.putExtra("user_Id",userid);

                //Toast.makeText(Nanny_Info.this,""+Objects.requireNonNull(mauth.getCurrentUser()).getUid(),Toast.LENGTH_LONG).show();

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });
    }
}