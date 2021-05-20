package com.example.costumermanagement;

import android.os.AsyncTask;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class DeleteAsync  extends AsyncTask<Void, Void, Void> {

    private Costumer costumerDelete;

    public DeleteAsync(Costumer costumerDelete) {
        this.costumerDelete = costumerDelete;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        FirebaseFirestore.getInstance().collection("Costumers").
                whereEqualTo("name", costumerDelete.getName()).
                whereEqualTo("status", costumerDelete.getStatus()).
                whereEqualTo("paymentMethod", costumerDelete.getPaymentMethod()).limit(1).get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot query : queryDocumentSnapshots){
                FirebaseFirestore.getInstance().collection("Costumers").document(query.getId()).delete();
                break;
            }
        });

        return null;
    }
}