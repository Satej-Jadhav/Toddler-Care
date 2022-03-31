package com.care.nannycare;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Parent_complete_info extends AppCompatActivity
{
    FirebaseAuth mAuth;
    FirebaseFirestore fstore;
    String userid;
    TextView firstname,lastname, phone, email;
    DocumentReference df;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_complete_info);

        mAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
    }
}