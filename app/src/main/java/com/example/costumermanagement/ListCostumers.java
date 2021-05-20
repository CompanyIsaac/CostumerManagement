package com.example.costumermanagement;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class ListCostumers extends AppCompatActivity {
    private static final String LOG_TAG = ListCostumers.class.getName();

    private FirebaseUser user;
    private FirebaseAuth mAuth;

    private RecyclerView mRecyclerView;
    private ArrayList<Costumer> costumerList;
    private CostumerAdapter mAdapter;

    private FirebaseFirestore firestore;
    private CollectionReference costumers;

    private boolean isRowView = true;

    private SharedPreferences sharedPref;

    private NotificationHandler notificationHandler;

    private TextView userName;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_costumers);

        mAuth = FirebaseAuth.getInstance();
        // mAuth.signOut();
        user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null) {
            Log.d(LOG_TAG, "Authenticated user!");
        } else {
            Log.d(LOG_TAG, "Unauthenticated user!");
            finish();
        }

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        costumerList = new ArrayList<>();

        mAdapter = new CostumerAdapter(this, costumerList);
        mRecyclerView.setAdapter(mAdapter);

        firestore = FirebaseFirestore.getInstance();
        costumers = firestore.collection("Costumers");

        sharedPref = getSharedPreferences("add", MODE_PRIVATE);

        notificationHandler = new NotificationHandler(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        queryData();
    }

    private void queryData() {

        costumerList.clear();

        costumers.orderBy("name").limit(10).get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot doc : queryDocumentSnapshots){
                costumerList.add(doc.toObject(Costumer.class));
            }
            mAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.log_out_button:
                FirebaseAuth.getInstance().signOut();
                Intent mIntent = new Intent(this, MainActivity.class);
                startActivity(mIntent);
                return true;
            case R.id.add_costumer:
                Intent uIntent = new Intent(this, Add.class);
                uIntent.putExtra("this", "add");
                startActivity(uIntent);
                return true;
            case R.id.refresh:
                queryData();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void updateRecord(String name, String status, String paymentMethod){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();

        editor.putString("name", name);
        editor.putString("status", status);
        editor.putString("paymentMethod", paymentMethod);

        editor.apply();

        Intent intent = new Intent(this, Update.class);
        intent.putExtra("this", "update");
        startActivity(intent);
    }

    public void delete(String name, String status, String paymentMethod) {
        Costumer costumer = new Costumer(name, status, paymentMethod);
        new DeleteAsync(costumer).execute();
        try {
            Thread.sleep(500);
        } catch (Exception e) {
            e.printStackTrace();
        }
        notificationHandler.send("User deleted");
        queryData();
    }

}