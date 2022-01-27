package com.care.toddlercare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class login_activity extends AppCompatActivity
{
    Button login;
    EditText login_email;
    EditText login_pass;
    TextView register_button;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        register_button = findViewById(R.id.register_page);
        login_email = findViewById(R.id.login_email);
        login_pass = findViewById(R.id.login_password);
        login = findViewById(R.id.login);
        mAuth = FirebaseAuth.getInstance();

        register_button.setOnClickListener(view -> login_activity.this.startActivity(new Intent(login_activity.this,
                register_activity.class)));

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                loginUser();
            }
        });
    }

    private void loginUser()
    {
        String emails = login_email.getText().toString();
        String password = login_pass.getText().toString();

        if(TextUtils.isEmpty(emails))
        {
            login_email.setError("Email Cannot be Empty");
            login_email.requestFocus();
        }else if (TextUtils.isEmpty(password))
        {
            login_pass.setError("Password Cannot be Empty");
            login_pass.requestFocus();
        }else
        {
            mAuth.signInWithEmailAndPassword(emails, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(login_activity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                    login_activity.this.startActivity(new Intent(login_activity.this, dashboard_activity.class));
                } else {
                    Toast.makeText(login_activity.this, "User unsuccessfully: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}