package com.xoksis.firestorecrudsimple.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.xoksis.firestorecrudsimple.adapter_classes.UserAdapter;
import com.xoksis.firestorecrudsimple.databinding.ActivityMainBinding;
import com.xoksis.firestorecrudsimple.model_classes.UserData;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding mainBinding; // Binding.
    FirebaseFirestore firestore;
    UserData userData;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());

        firestore = FirebaseFirestore.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Uploading...");
        mainBinding.buttonSend.setOnClickListener(v -> {

            // radioButton gender.
            int myGender = mainBinding.radioGroupGender.getCheckedRadioButtonId();
            RadioButton radioButtonGender = findViewById(myGender);


            String name = mainBinding.textInputEditTextName.getText().toString();
            String rollNo = mainBinding.textInputEditTextRollNo.getText().toString();
            String gmail = mainBinding.textInputEditTextGmail.getText().toString();
            String gender = radioButtonGender.getText().toString();
            String userID = firestore.collection("students").document().getId();


            userData = new UserData(name, rollNo, gmail, gender, userID);

            progressDialog.show();
            firestore.collection("students").document(userID).set(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if (task.isSuccessful()) {

                        Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                        mainBinding.textInputEditTextName.setText("");
                        mainBinding.textInputEditTextRollNo.setText("");
                        mainBinding.textInputEditTextGmail.setText("");
                        progressDialog.dismiss();

                    }
                }
            });

        });

        mainBinding.buttonFetch.setOnClickListener(v -> {

            startActivity(new Intent(MainActivity.this, FetchActivity.class));

        });

    }

}