package com.care.nannycare;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Objects;

public class NannyAdapter extends RecyclerView.Adapter<NannyAdapter.MyViewHolder>
{
    FavouriteFragment context;
    ArrayList<Nanny> nannyArrayList;
    FirebaseFirestore fstore;
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    DocumentReference df;
    public String fname;
    public String phone_number;
    public String user_id;
    public NannyAdapter(FavouriteFragment context, ArrayList<Nanny> nannyArrayList) {
        this.context = context;
        this.nannyArrayList = nannyArrayList;
        this.fstore = FirebaseFirestore.getInstance();
        this.mAuth = FirebaseAuth.getInstance();
    }

    public NannyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row, parent, false);
        return new NannyAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NannyAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position)
    {
        Nanny user = nannyArrayList.get(position);
        user_id = user.getUser_id();

        DocumentReference df = fstore.collection("User Data").document(user_id);
        df.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task)
            {
                fname = task.getResult().getString("first_Name");
                String lname = task.getResult().getString("last_Name");
                String email_add = task.getResult().getString("email");
                phone_number = task.getResult().getString("phone_Number");



            }
        });
        holder.full_name.setText(fname);
        holder.date.setText(phone_number);

        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent;
                intent = new Intent(view.getContext(), Parent_complete_info.class);

                Toast.makeText(view.getContext()," "+user_id,Toast.LENGTH_LONG).show();
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() { return nannyArrayList.size(); }
    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView full_name, date;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            full_name = itemView.findViewById(R.id.full_name);
            date = itemView.findViewById(R.id.date);

        }
    }
}
