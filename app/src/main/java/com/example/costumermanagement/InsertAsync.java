package com.example.costumermanagement;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;


import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.ref.WeakReference;


public class InsertAsync extends AsyncTask<Void, Void, Void> {

    private Costumer costumerAdd;

    public InsertAsync(Costumer costumer) {
        this.costumerAdd = costumer;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        FirebaseFirestore.getInstance().
                collection("Costumers").
                add(costumerAdd);
        return null;
    }
}
