package com.example.costumermanagement;

import android.os.AsyncTask;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.ref.WeakReference;


public class UpdateAsync extends AsyncTask<Void, Void, Void> {

    private Costumer costumerUpdate;
    private Costumer costumerActual;

    public UpdateAsync(Costumer costumerUpdate, Costumer costumerActual) {
        this.costumerUpdate = costumerUpdate;
        this.costumerActual = costumerActual;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        FirebaseFirestore.getInstance().collection("Costumers").
                whereEqualTo("name", costumerActual.getName()).
                whereEqualTo("status", costumerActual.getStatus()).
                whereEqualTo("paymentMethod", costumerActual.getPaymentMethod()).limit(1).get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot query : queryDocumentSnapshots){
                FirebaseFirestore.getInstance().collection("Costumers").document(query.getId()).update(
                        "name", costumerUpdate.getName(),
                        "status", costumerUpdate.getStatus(),
                        "paymentMethod", costumerUpdate.getPaymentMethod()                );
                break;
            }
        });

        return null;
    }
}
