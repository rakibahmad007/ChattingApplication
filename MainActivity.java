package com.example.chattingapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    RecyclerView mainUserRecyclerView;
    UserAdpter adapter;
    FirebaseFirestore firestore;
    ArrayList<Users> usersArrayList;
    ImageView imglogout, cumbut, setbut;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        // Initialize Firebase and UI elements
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        cumbut = findViewById(R.id.camBut);
        setbut = findViewById(R.id.settingBut);
        imglogout = findViewById(R.id.logoutimg);

        usersArrayList = new ArrayList<>();
        mainUserRecyclerView = findViewById(R.id.mainUserRecyclerView);
        mainUserRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UserAdpter(MainActivity.this, usersArrayList);
        mainUserRecyclerView.setAdapter(adapter);

        CollectionReference usersCollection = firestore.collection("users");

        usersCollection.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot value, FirebaseFirestoreException error) {
                if (error != null) {
                    Toast.makeText(MainActivity.this, "Failed to load data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }
                usersArrayList.clear();
                for (QueryDocumentSnapshot document : value) {
                    Users user = document.toObject(Users.class);
                    usersArrayList.add(user);
                }
                adapter.notifyDataSetChanged();
            }
        });

        // Logout functionality
        imglogout.setOnClickListener(v -> {
            Dialog dialog = new Dialog(MainActivity.this, R.style.dialoge);
            dialog.setContentView(R.layout.dialog_layout);
            Button no = dialog.findViewById(R.id.nobnt);
            Button yes = dialog.findViewById(R.id.yesbnt);

            yes.setOnClickListener(v1 -> {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this, login.class);
                startActivity(intent);
                finish();
            });

            no.setOnClickListener(v1 -> dialog.dismiss());
            dialog.show();
        });

        // Settings button
        setbut.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, setting.class);
            startActivity(intent);
        });

        // Camera functionality
        cumbut.setOnClickListener(v -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 10);
        });

        // Redirect to login if user is not authenticated
        if (auth.getCurrentUser() == null) {
            Intent intent = new Intent(MainActivity.this, login.class);
            startActivity(intent);
            finish();
        }
    }
}
