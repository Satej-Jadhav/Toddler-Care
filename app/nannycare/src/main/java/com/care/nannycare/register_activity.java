package com.care.nannycare;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class register_activity extends AppCompatActivity
{
    EditText email, pass, fname, lname, phone, about, years, aadhar_number;


    Button register;

    FirebaseAuth mAuth;
    String user_id;
    FirebaseFirestore firestore;
    FirebaseDatabase fbd;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_nanny);

        email = findViewById(R.id.user_email);
        pass = findViewById(R.id.user_pass);
        fname = findViewById(R.id.user_first_name);
        phone = findViewById(R.id.phone_user);
        lname = findViewById(R.id.user_last_name);
        about = findViewById(R.id.nanny_about);
        years = findViewById(R.id.no_years);
        aadhar_number = findViewById(R.id.aadhar_number);


        register = findViewById(R.id.register_nanny);
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        fbd = FirebaseDatabase.getInstance();

        register.setOnClickListener(view -> createUser());
    }
    private void createUser()
    {
        String emails = email.getText().toString();
        String password = pass.getText().toString();
        String first_name = fname.getText().toString();
        String last_name = lname.getText().toString();
        String phone_number =  phone.getText().toString();
        String nanny_about = about.getText().toString();
        String experience_years = years.getText().toString();
        String aadhar_card = aadhar_number.getText().toString();

        if(TextUtils.isEmpty(emails))
        {
            email.setError("Email Cannot be Empty");
            email.requestFocus();
        }else if (TextUtils.isEmpty(password))
        {
            pass.setError("Password Cannot be Empty");
            pass.requestFocus();
        }else
        {
            mAuth.createUserWithEmailAndPassword(emails,password).addOnCompleteListener(task -> {
                if (task.isSuccessful())
                {
                    Toast.makeText(register_activity.this,"User registered successfully",Toast.LENGTH_SHORT).show();


                    user_id = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                    DocumentReference documentReference = firestore.collection("Nanny data").document(user_id);
                    Map<String, Object> user = new HashMap<>();
                    user.put("first_Name",first_name);
                    user.put("last_Name",last_name);
                    user.put("email",emails);
                    user.put("phone_Number",phone_number);
                    user.put("about",nanny_about);
                    user.put("years_of_experience",experience_years);
                    user.put("aadhar_card_number",aadhar_card);
                    user.put("userid",user_id);

                    documentReference.set(user).addOnCompleteListener(task1 -> Log.d("TAG","on Success: "+ user_id));

                    startActivity(new Intent(getApplicationContext(), dashboard_activity.class));                }else
                {
                    Toast.makeText(register_activity.this,"User unsuccessfully: "+ Objects.requireNonNull(task.getException()).getMessage(),Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}