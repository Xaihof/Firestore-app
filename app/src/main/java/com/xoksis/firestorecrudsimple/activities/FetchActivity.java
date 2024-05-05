package com.xoksis.firestorecrudsimple.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.xoksis.firestorecrudsimple.adapter_classes.UserAdapter;
import com.xoksis.firestorecrudsimple.databinding.ActivityFetchBinding;
import com.xoksis.firestorecrudsimple.model_classes.UserData;

import java.util.ArrayList;
import java.util.List;

public class FetchActivity extends AppCompatActivity {

    ActivityFetchBinding fetchBinding;
    FirebaseFirestore firestore;
    ArrayList<UserData> userDataList;
    UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fetchBinding = ActivityFetchBinding.inflate(getLayoutInflater());
        setContentView(fetchBinding.getRoot());

        firestore = FirebaseFirestore.getInstance();

        userDataList = new ArrayList<>();

        firestore.collection("students").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {


                if (error == null) {

                    userDataList.clear();

                    List<UserData> data = value.toObjects(UserData.class);

                    userDataList.addAll(data);

                    userAdapter = new UserAdapter(userDataList, FetchActivity.this);

                    fetchBinding.recyclerView.setAdapter(userAdapter);

                } else {

                    Toast.makeText(FetchActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

}