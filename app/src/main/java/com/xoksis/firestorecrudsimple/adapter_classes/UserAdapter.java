package com.xoksis.firestorecrudsimple.adapter_classes;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.xoksis.firestorecrudsimple.R;
import com.xoksis.firestorecrudsimple.activities.UpdateActivity;
import com.xoksis.firestorecrudsimple.databinding.ItemReceiveLayoutBinding;
import com.xoksis.firestorecrudsimple.model_classes.UserData;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    ArrayList<UserData> userDataList;
    Context context;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance(); // Firestore.

    public UserAdapter(ArrayList<UserData> userDataList, Context context) {
        this.userDataList = userDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_receive_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        UserData obj = userDataList.get(position);

        holder.layoutBinding.textViewName.setText(obj.getName());
        holder.layoutBinding.textViewRollNo.setText(obj.getRollNo());
        holder.layoutBinding.textViewGmail.setText(obj.getGmail());
        holder.layoutBinding.textViewGender.setText(obj.getGender());

        holder.layoutBinding.buttonUpdate.setOnClickListener(v -> {

            Intent intent = new Intent(context, UpdateActivity.class);
            intent.putExtra("userID",obj.getuserID());
            context.startActivity(intent);

        });

        holder.layoutBinding.buttonDelete.setOnClickListener(v -> {

            firestore.collection("students").document(obj.getuserID()).delete();

        });
    }

    @Override
    public int getItemCount() {
        return userDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ItemReceiveLayoutBinding layoutBinding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutBinding = ItemReceiveLayoutBinding.bind(itemView);
        }
    }
}