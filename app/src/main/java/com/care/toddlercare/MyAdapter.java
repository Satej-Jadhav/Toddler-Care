package com.care.toddlercare;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>
{
    HomeFragment context;
    ArrayList<User> userArrayList;
    FirebaseFirestore fstore;
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;

    public MyAdapter(HomeFragment context, ArrayList<User> userArrayList) {
        this.context = context;
        this.userArrayList = userArrayList;
        this.fstore = FirebaseFirestore.getInstance();
        this.mAuth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position)
    {
        User user = userArrayList.get(position);
        holder.fname.setText(user.first_Name);
        holder.lname.setText(user.last_Name);

        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                DocumentReference df = fstore.collection("Nanny data").document(user.getUserid());
                Intent intent;
                intent = new Intent(view.getContext(), Nanny_Info.class);


                intent.putExtra("user_Id", user.getUserid());
                intent.putExtra("first_name",user.getFirst_Name());
                intent.putExtra("about",user.getAbout());
                intent.putExtra("email",user.getEmail());
                intent.putExtra("year_experience",user.getYears_of_experience());
                intent.putExtra("phone_number",user.getPhone_Number());

                //Toast.makeText(view.getContext()," "+user.getUserid(),Toast.LENGTH_LONG).show();
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView fname,lname;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            fname = itemView.findViewById(R.id.fname);
            lname = itemView.findViewById(R.id.lname);

        }
    }
}
