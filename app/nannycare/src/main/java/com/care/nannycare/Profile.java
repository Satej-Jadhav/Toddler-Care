package com.care.nannycare;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class Profile extends Fragment
{

    Button logout;
    FirebaseAuth mAuth;
    FirebaseFirestore fstore;
    String userid;
    TextView firstname,lastname, phone, email;
    DocumentReference df;

    public Profile() {}
        // Required empty public constructor
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_nanny, container, false);
        // Inflate the layout for this fragment
        logout = view.findViewById(R.id.logout_butt);
        mAuth = FirebaseAuth.getInstance();
        logout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                mAuth.signOut();
                Intent intent = new Intent(getActivity(),MainActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        firstname = getActivity().findViewById(R.id.first_name);
        lastname = getActivity().findViewById(R.id.last_name);
        phone = getActivity().findViewById(R.id.customer_phone);
        email = getActivity().findViewById(R.id.customer_email);
    }

   @Override
    public void onStart()
   {
       super.onStart();

       fstore = FirebaseFirestore.getInstance();
       userid = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

       df = fstore.collection("Nanny data").document(userid);

       df.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>()
       {
           @Override
           public void onComplete(@NonNull Task<DocumentSnapshot> task)
           {
               String fname = task.getResult().getString("first_Name");
               String lname = task.getResult().getString("last_Name");
               String email_add = task.getResult().getString("email");
               String phone_number = task.getResult().getString("phone_Number");


               firstname.setText(fname);
               lastname.setText(lname);
               phone.setText(phone_number);
               email.setText(email_add);
           }
       });

   }
}