package com.care.toddlercare;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class register_activity extends AppCompatActivity
{
    EditText email;
    EditText pass;
    EditText fname;
    EditText phone;
    
    Button register;

    FirebaseAuth mAuth;
    public String user_id;
    FirebaseFirestore firestore;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = findViewById(R.id.user_email);
        pass = findViewById(R.id.user_pass);
        fname = findViewById(R.id.user_name);
        phone = findViewById(R.id.phone_user);
        
        register = findViewById(R.id.register_user);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        register.setOnClickListener(view -> createUser());
    }
    private void createUser()
    {
        String emails = email.getText().toString();
        String password = pass.getText().toString();
        String full_name = fname.getText().toString();
        String phone_number =  phone.getText().toString();


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
                    DocumentReference documentReference = firestore.collection("User data").document(user_id);

                    Map<String, Object> user = new HashMap<>();
                    user.put("full_name",full_name);
                    user.put("email",emails);
                    user.put("phone_number",phone_number);
                    user.put("user_id",user_id);
                    documentReference.set(user).addOnCompleteListener(task1 -> Log.d("TAG","on Success: "+ user_id));

                    startActivity(new Intent(getApplicationContext(), dashboard_activity.class));                }else
                {
                    Toast.makeText(register_activity.this,"User unsuccessfully: "+ Objects.requireNonNull(task.getException()).getMessage(),Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}