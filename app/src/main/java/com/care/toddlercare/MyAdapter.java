package com.care.toddlercare;

import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>
{

    HomeFragment context;
    ArrayList<User> userArrayList;

    public MyAdapter(HomeFragment context, ArrayList<User> userArrayList) {
        this.context = context;
        this.userArrayList = userArrayList;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {

        User user = userArrayList.get(position);

        holder.fname.setText(user.first_Name);
        holder.lname.setText(user.last_Name);

        holder.fname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                /*AppCompatActivity activity = (AppCompatActivity)view.getContext();
                activity.getFragmentManager().beginTransaction().replace(context.parent.getContext(),new Nanny_Info_fragment()).addToBackStack(null).commit();*/

                /*Bundle bundle = new Bundle();
                Nanny_Info_fragment fragment = new Nanny_Info_fragment();
                fragment.setArguments(bundle);*/

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
