package com.care.nannycare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Parent_Info extends AppCompatActivity
{
    public String full_name,date,job_description,email,user_id,number,phone_Number;
    TextView parent_name,job_Description,parent_email,parent_number,date_confirmed;
    Button confirm;
    FirebaseAuth mauth;
    FirebaseFirestore fstore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_info);

        fstore = FirebaseFirestore.getInstance();
        mauth = FirebaseAuth.getInstance();

        parent_name = (TextView) findViewById(R.id.parent_name);
        job_Description = (TextView) findViewById(R.id.job_description);
        parent_email = (TextView) findViewById(R.id.parent_email);
        parent_number = (TextView)findViewById(R.id.parent_phone);
        date_confirmed = (TextView)findViewById(R.id.date);
        confirm = (Button)findViewById(R.id.confirm);

        user_id = getIntent().getStringExtra("user_Id");
        full_name = getIntent().getStringExtra("full_name");
        email = getIntent().getStringExtra("email");
        job_description = getIntent().getStringExtra("job_description");
        date = getIntent().getStringExtra("date");
        number = getIntent().getStringExtra("number");
        phone_Number = getIntent().getStringExtra("phone_Number");

        parent_name.setText(full_name);
        //parent_email.setText(email);
        //parent_number.setText(number);
        date_confirmed.setText(date);
        job_Description.setText(job_description);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Parent_Info.this,dashboard_activity.class);

                DocumentReference documentReference = fstore.collection("Booking Confirmed Nanny")
                        .document(mauth.getCurrentUser().getUid()).collection("Booked").document(user_id);
                Map<String, Object> nanny = new HashMap<>();
                nanny.put("user_id",user_id);
                nanny.put("nannyuser_id",mauth.getCurrentUser().getUid());

                DocumentReference df = fstore.collection("Booking Confirmed Parent")
                        .document(user_id).collection("Booked").document(mauth.getCurrentUser().getUid());
                Map<String, Object> user = new HashMap<>();
                user.put("user_id",user_id);
                user.put("nannyuser_id",mauth.getCurrentUser().getUid());

                fstore.collection("Nanny data").document(mauth.getCurrentUser().getUid())
                        .collection("Date").document(user_id).delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused)
                            {
                                Toast.makeText(Parent_Info.this, "Booking Confirmed", Toast.LENGTH_SHORT).show();

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e)
                            {
                                Log.d("Error:"," "+e);
                            }
                        });

                documentReference.set(nanny).addOnCompleteListener(task1 -> Log.d("TAG","on Success: "+ user_id));
                df.set(user).addOnCompleteListener(task1 -> Log.d("TAG","on Success: "+ user_id));
                startActivity(intent);
            }
        });

    }
}