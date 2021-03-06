package com.example.costumermanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {
    private static final String LOG_TAG = RegistrationActivity.class.getName();
    private static final String PREF_KEY = MainActivity.class.getPackage().toString();
    private static final int SECRET_KEY = 99;

    EditText userNameEditText;
    EditText userEmailEditText;
    EditText passwordEditText;
    EditText passwordConfirmEditText;

    private SharedPreferences preferences;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mAuth = FirebaseAuth.getInstance();

        userNameEditText = findViewById(R.id.userNameEditText);
        userEmailEditText = findViewById(R.id.userEmailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        passwordConfirmEditText = findViewById(R.id.passwordAgainEditText);

        preferences = getSharedPreferences(PREF_KEY, MODE_PRIVATE);

        String email = preferences.getString("email", "");
        String password = preferences.getString("password", "");

        userEmailEditText.setText(email);
        passwordEditText.setText(password);
        passwordConfirmEditText.setText(password);

        Log.i(LOG_TAG, "onCreate");

    }

    public void register(View view) {
    String userName = userNameEditText.getText().toString();
    String email = userEmailEditText.getText().toString();
    String password = passwordEditText.getText().toString();
    String passwordConfirm = passwordConfirmEditText.getText().toString();

        if (!password.equals(passwordConfirm)) {
        Log.e(LOG_TAG, "Nem egyenl?? a jelsz?? ??s a meger??s??t??se.");
        return;
    }

        Log.i(LOG_TAG, "Regisztr??lt: " + userName + ", e-mail: " + email);

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startListCostumers();
                }
                else {
                    Log.d(LOG_TAG, "User was't created successfully:", task.getException());
                    Toast.makeText(RegistrationActivity.this, "User was't created successfully:", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    public void cancel(View view) {
        finish();
    }

    private void startListCostumers(){
        Intent intent = new Intent(this, ListCostumers.class);
        intent.putExtra("SECRET_KEY", SECRET_KEY);
        startActivity(intent);
    }
}