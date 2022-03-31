package com.care.nannycare;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class FavouriteFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<Nanny> nannyArrayList;
    NannyAdapter nannyAdapter;
    FirebaseFirestore fstore;
    ProgressDialog progressDialog;
    FirebaseAuth mauth;
    String user_id;


    public FavouriteFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favourite, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_fav);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        fstore = FirebaseFirestore.getInstance();
        nannyArrayList = new ArrayList<>();
        nannyAdapter = new NannyAdapter(FavouriteFragment.this,nannyArrayList);
        mauth =FirebaseAuth.getInstance();


        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading data");
        progressDialog.show();

        recyclerView.setAdapter(nannyAdapter);
        EventchangeListerner();


        return view;
    }

    @SuppressLint("NotifyDataSetChanged")
    private void EventchangeListerner()
    {
        fstore.collection("Booking Confirmed Nanny").document(mauth.getCurrentUser().getUid()).collection("Booked")
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
                                nannyArrayList.add(dc.getDocument().toObject(Nanny.class));
                                progressDialog.dismiss();
                            }
                            nannyAdapter.notifyDataSetChanged();
                        }

                    }
                });

    }
}