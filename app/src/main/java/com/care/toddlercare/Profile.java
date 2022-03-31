package com.care.toddlercare;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class Profile extends Fragment
{

    Button logout;
    FirebaseAuth mAuth;
    FirebaseFirestore fstore;
    String userid;
    TextView fullname, phone, email;
    DocumentReference df;
    ProgressDialog progressDialog;

    public Profile() {}
        // Required empty public constructor
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading");
        progressDialog.show();

        // Inflate the layout for this fragment
        logout = view.findViewById(R.id.logout_butt);
        mAuth = FirebaseAuth.getInstance();
        logout.setOnClickListener(view1 -> {
            mAuth.signOut();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading");
        progressDialog.show();

        // Inflate the layout for th
            Intent intent = new Intent(getActivity(),MainActivity.class);
            startActivity(intent);
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fullname = requireActivity().findViewById(R.id.customer_name);
        phone = getActivity().findViewById(R.id.customer_phone);
        email = getActivity().findViewById(R.id.customer_email);
    }

   @Override
    public void onStart()
   {
       super.onStart();

       fstore = FirebaseFirestore.getInstance();
       userid = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

       df = fstore.collection("User data").document(userid);

       df.get().addOnCompleteListener(task -> {
           progressDialog.dismiss();
           String name = task.getResult().getString("full_name");
           String email_add = task.getResult().getString("email");
           String phone_number = task.getResult().getString("phone_number");


           fullname.setText(name);
           phone.setText(phone_number);
           email.setText(email_add);
       });

   }
}