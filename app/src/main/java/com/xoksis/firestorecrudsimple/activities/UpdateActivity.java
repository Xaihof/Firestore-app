package com.xoksis.firestorecrudsimple.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.xoksis.firestorecrudsimple.R;
import com.xoksis.firestorecrudsimple.databinding.ActivityUpdateBinding;
import com.xoksis.firestorecrudsimple.model_classes.UserData;

public class UpdateActivity extends AppCompatActivity {

    ActivityUpdateBinding updateBinding;
    FirebaseFirestore firestore;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updateBinding = ActivityUpdateBinding.inflate(getLayoutInflater());
        setContentView(updateBinding.getRoot());

        firestore = FirebaseFirestore.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Updating...");
        String userID = getIntent().getStringExtra("userID");

        firestore.collection("students").document(userID).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                UserData userData = value.toObject(UserData.class);

                updateBinding.textInputEditTextName.setText(userData.getName());
                updateBinding.textInputEditTextGmail.setText(userData.getGmail());
                updateBinding.textInputEditTextRollNo.setText(userData.getRollNo());


            }
        });

        updateBinding.buttonSend.setOnClickListener(v -> {


            progressDialog.show();
            // radioButton gender.
            int myGender = updateBinding.radioGroupGender.getCheckedRadioButtonId();
            RadioButton radioButtonGender = findViewById(myGender);


            String name = updateBinding.textInputEditTextName.getText().toString();
            String rollNo = updateBinding.textInputEditTextRollNo.getText().toString();
            String gmail = updateBinding.textInputEditTextGmail.getText().toString();
            String gender = radioButtonGender.getText().toString();


            firestore.collection("students").document(userID).update("name", name, "rollNo", rollNo, "gmail", gmail, "gender", gender).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if (task.isSuccessful()) {

                        Toast.makeText(UpdateActivity.this, "Data Updated Successfully", Toast.LENGTH_SHORT).show();
                        updateBinding.textInputEditTextName.setText("");
                        updateBinding.textInputEditTextRollNo.setText("");
                        updateBinding.textInputEditTextGmail.setText("");
                        progressDialog.dismiss();
                        finish();
                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(UpdateActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });


        });


    }
}