package com.example.costumermanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Add extends AppCompatActivity {

    private EditText nameEditText;
    private EditText statusEditText;
    private EditText paymentMethodEditText;

    private NotificationHandler notificationHandler;


    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        nameEditText = findViewById(R.id.itemName);
        statusEditText = findViewById(R.id.itemStatus);
        paymentMethodEditText = findViewById(R.id.itemPaymentMethod);

        sharedPreferences = getSharedPreferences("add", MODE_PRIVATE);

        notificationHandler = new NotificationHandler(this);
    }

    public void add(View view) {
        Costumer costumer = new Costumer(
                nameEditText.getText().toString(),
                statusEditText.getText().toString(),
                paymentMethodEditText.getText().toString());

        new InsertAsync(costumer).execute();
        try {
            Thread.sleep(500);
        } catch (Exception e) {
            e.printStackTrace();
        }
        notificationHandler.send("User added");
        finish();
    }


    public void cancel(View view) {
        finish();
    }
}