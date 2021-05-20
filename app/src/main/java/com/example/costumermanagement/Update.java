package com.example.costumermanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class Update extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    private NotificationHandler notificationHandler;

    private EditText nameEditText;
    private EditText statusEditText;
    private EditText paymentMethodEditText;

    private Costumer costumerRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        sharedPreferences = getSharedPreferences("add", MODE_PRIVATE);

        nameEditText = findViewById(R.id.itemName);
        statusEditText = findViewById(R.id.itemStatus);
        paymentMethodEditText = findViewById(R.id.itemPaymentMethod);

        nameEditText.setText(sharedPreferences.getString("name", ""));
        statusEditText.setText(sharedPreferences.getString("status", ""));
        paymentMethodEditText.setText(sharedPreferences.getString("paymentMethod", ""));

        costumerRef = new Costumer(
                sharedPreferences.getString("name", ""),
                sharedPreferences.getString("status", ""),
                sharedPreferences.getString("paymentMethod", ""));

        notificationHandler = new NotificationHandler(this);
    }

    public void update(View view) {
        Costumer costumer = new Costumer(
                nameEditText.getText().toString(),
                statusEditText.getText().toString(),
                paymentMethodEditText.getText().toString());

        new UpdateAsync(costumer, costumerRef).execute();
        try {
            Thread.sleep(500);
        } catch (Exception e) {
            e.printStackTrace();
        }
        notificationHandler.send("User updated");
        finish();
    }

    public void cancel(View view) {
        finish();
    }
}