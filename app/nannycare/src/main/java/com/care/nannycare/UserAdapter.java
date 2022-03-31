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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder>
{
    HomeFragment context;
    ArrayList<User> userArrayList;
    ArrayList<Nanny> nannyArrayList;
    FirebaseFirestore fstore;
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;

    public UserAdapter(HomeFragment context, ArrayList<User> userArrayList) {
        this.context = context;
        this.userArrayList = userArrayList;
        this.fstore = FirebaseFirestore.getInstance();
        this.mAuth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position)
    {
        User user = userArrayList.get(position);
        holder.full_name.setText(user.getFull_name());
        holder.date.setText(user.getDate());

        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                DocumentReference df = fstore.collection("Nanny data").document(mAuth.getCurrentUser().getUid())
                        .collection("Date").document(user.getUser_id());
                Intent intent;
                intent = new Intent(view.getContext(), Parent_Info.class);


                intent.putExtra("user_Id", user.getUser_id());
                intent.putExtra("full_name",user.getFull_name());
                intent.putExtra("job_description",user.getJob_description());
                intent.putExtra("email",user.getEmail());
                intent.putExtra("date",user.getDate());
                intent.putExtra("number",user.getNumber());


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
        TextView full_name, date;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            full_name = itemView.findViewById(R.id.full_name);
            date = itemView.findViewById(R.id.date);

        }
    }
}
