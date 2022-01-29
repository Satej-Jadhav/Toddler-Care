package com.care.toddlercare;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.okhttp.internal.DiskLruCache;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment
{
    RecyclerView recyclerView;
    ArrayList<User> userArrayList;
    MyAdapter myAdapter;
    FirebaseFirestore fstore;
    ProgressDialog progressDialog;

    public HomeFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        fstore = FirebaseFirestore.getInstance();
        userArrayList = new ArrayList<>();
        myAdapter = new MyAdapter(HomeFragment.this,userArrayList);


        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading");
        progressDialog.show();

        recyclerView.setAdapter(myAdapter);
        EventchangeListerner();


        return view;
    }

    @SuppressLint("NotifyDataSetChanged")
    private void EventchangeListerner()
    {

        Query query = fstore
                .collection("Nanny date")
                .orderBy("years_of_experience", Query.Direction.DESCENDING);

        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error)
            {
                if (error != null) {
                    // Handle error
                    //...
                    return;
                }

                // Convert query snapshot to a list of chats
                //List<User> chats = snapshot.toObjects(User.class);

                // Update UI
                // ...

            }
        });
                /*.addSnapshotListener((value, error) -> {
                    if (error != null)
                    {
                        Log.e("Firestore error",error.getMessage());
                        return;
                    }

                    for(DocumentChange dc : value.getDocumentChanges())
                    {
                        if (dc.getType() == DocumentChange.Type.ADDED)
                        {
                            userArrayList.add(dc.getDocument().toObject(User.class));
                            progressDialog.dismiss();
                        }
                        myAdapter.notifyDataSetChanged();
                    }

                });*/
    }

}