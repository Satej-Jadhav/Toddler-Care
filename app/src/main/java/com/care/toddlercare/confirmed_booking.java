package com.care.toddlercare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class confirmed_booking extends AppCompatActivity
{
    String date, user_Id, fname;

    String user_name, email_add,phone_number;
    Button confirmed;
    EditText jd;
    TextView name,confirm_date;

    FirebaseFirestore firestore;
    FirebaseAuth mauth;
    DocumentReference df;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmed_booking);

        mauth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        jd = (EditText)findViewById(R.id.job_description);
        name = (TextView)findViewById(R.id.fname);
        confirmed = (Button)findViewById(R.id.confirm_booking);
        confirm_date = (TextView)findViewById(R.id.date);

        user_Id = getIntent().getStringExtra("user_Id");
        date = getIntent().getStringExtra("date");
        fname = getIntent().getStringExtra("fname");

        name.setText(fname);
        confirm_date.setText(date);

        confirmed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String job_desc = jd.getText().toString();
                progressDialog = new ProgressDialog(confirmed_booking.this);
                progressDialog.setCancelable(false);
                progressDialog.setMessage("Loading data");
                progressDialog.show();



                DocumentReference documentReferenc = firestore.collection("Nanny data").document(""+user_Id)
                        .collection("Date").document(mauth.getCurrentUser().getUid());

                Map<String, Object> user = new HashMap<>();
                user.put("date",date);
                user.put("user_id",mauth.getCurrentUser().getUid());
                user.put("job_description",job_desc);


                String userid = Objects.requireNonNull(mauth.getCurrentUser()).getUid();

                df = firestore.collection("User data").document(userid);

                df.get().addOnCompleteListener(task ->
                {
                    user_name = task.getResult().getString("full_name");
                    email_add = task.getResult().getString("email");
                    phone_number = task.getResult().getString("phone_number");
                    user.put("full_name",user_name);
                    user.put("email",email_add);
                    user.put("number",phone_number);

                    progressDialog = new ProgressDialog(confirmed_booking.this);
                    progressDialog.setCancelable(false);
                    progressDialog.setMessage("Loading data");
                    progressDialog.show();

                    documentReferenc.set(user, SetOptions.merge())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {

                                    progressDialog.dismiss();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(confirmed_booking.this, "Error: "+e, Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    });

                });


                documentReferenc.set(user, SetOptions.merge())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task)
                            {
                                startActivity(new Intent(confirmed_booking.this, dashboard_activity.class));
                                progressDialog.dismiss();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("Error: "+e);
                        progressDialog.dismiss();
                    }
                });
            }
        });

    }
}