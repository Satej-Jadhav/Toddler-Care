package com.care.nannycare;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class HomeFragment extends Fragment
{
    RecyclerView recyclerView;
    ArrayList<User> userArrayList;
    UserAdapter userAdapter;
    FirebaseFirestore fstore;
    ProgressDialog progressDialog;
    FirebaseAuth mauth;


    public HomeFragment()
    {
        //yeahh ik
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_nanny, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        fstore = FirebaseFirestore.getInstance();
        userArrayList = new ArrayList<>();
        userAdapter = new UserAdapter(HomeFragment.this,userArrayList);
        mauth =FirebaseAuth.getInstance();


        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading data");
        progressDialog.show();

        recyclerView.setAdapter(userAdapter);
        EventchangeListerner();


        return view;
    }

    @SuppressLint("NotifyDataSetChanged")
    private void EventchangeListerner()
    {
        fstore.collection("Nanny data").document(mauth.getCurrentUser().getUid()).collection("Date")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error)
            {
                    if (error != null)
                    {
                        System.out.println("Firestore error" + error.getMessage());
                        progressDialog.dismiss();
                        return;
                    }

                    for(DocumentChange dc : value.getDocumentChanges())
                    {
                        if (dc.getType() == DocumentChange.Type.ADDED)
                        {
                            userArrayList.add(dc.getDocument().toObject(User.class));
                            progressDialog.dismiss();
                        }
                        userAdapter.notifyDataSetChanged();
                    }

                }
        });

    }
}