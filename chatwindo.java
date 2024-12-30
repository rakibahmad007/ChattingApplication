package com.example.chattingapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

import javax.annotation.Nullable;

import de.hdodenhof.circleimageview.CircleImageView;

public class chatwindo extends AppCompatActivity {
    String reciverimg, reciverUid, reciverName, SenderUID;
    CircleImageView profile;
    TextView reciverNName;
    FirebaseFirestore firestore;
    FirebaseAuth firebaseAuth;
    public static String senderImg;
    public static String reciverIImg;
    CardView sendbtn;
    EditText textmsg;

    String senderRoom, reciverRoom;
    RecyclerView messageAdpter;
    ArrayList<msgModelclass> messagesArrayList;
    messagesAdpter mmessagesAdpter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatwindow);
        getSupportActionBar().hide();

        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        reciverName = getIntent().getStringExtra("nameeee");
        reciverimg = getIntent().getStringExtra("reciverImg");
        reciverUid = getIntent().getStringExtra("uid");

        messagesArrayList = new ArrayList<>();

        sendbtn = findViewById(R.id.sendbtnn);
        textmsg = findViewById(R.id.textmsg);
        reciverNName = findViewById(R.id.recivername);
        profile = findViewById(R.id.profileimgg);
        messageAdpter = findViewById(R.id.msgadpter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        messageAdpter.setLayoutManager(linearLayoutManager);

        mmessagesAdpter = new messagesAdpter(chatwindo.this, messagesArrayList);
        messageAdpter.setAdapter(mmessagesAdpter);

        Picasso.get().load(reciverimg).into(profile);
        reciverNName.setText(reciverName);

        SenderUID = firebaseAuth.getUid();

        senderRoom = SenderUID + reciverUid;
        reciverRoom = reciverUid + SenderUID;

        // Fetch sender's profile picture
        firestore.collection("users")
                .document(SenderUID)
                .addSnapshotListener((snapshot, error) -> {
                    if (snapshot != null && snapshot.exists()) {
                        senderImg = snapshot.getString("profilepic");
                        reciverIImg = reciverimg;
                    }
                });

        // Fetch messages in real-time
        firestore.collection("chats")
                .document(senderRoom)
                .collection("messages")
                .orderBy("timestamp", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Toast.makeText(chatwindo.this, "Error loading messages", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (value != null) {
                            for (DocumentChange doc : value.getDocumentChanges()) {
                                if (doc.getType() == DocumentChange.Type.ADDED) {
                                    msgModelclass message = doc.getDocument().toObject(msgModelclass.class);
                                    messagesArrayList.add(message);
                                    mmessagesAdpter.notifyDataSetChanged();
                                }
                            }
                        }
                    }
                });

        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = textmsg.getText().toString();
                if (message.isEmpty()) {
                    Toast.makeText(chatwindo.this, "Enter the message first", Toast.LENGTH_SHORT).show();
                    return;
                }
                textmsg.setText("");

                Date date = new Date();
                msgModelclass messagess = new msgModelclass(message, SenderUID, reciverUid, "" , "" , "" , "" , date.getTime());

                // Send message to Firestore
                firestore.collection("chats")
                        .document(senderRoom)
                        .collection("messages")
                        .add(messagess)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                firestore.collection("chats")
                                        .document(reciverRoom)
                                        .collection("messages")
                                        .add(messagess);
                            }
                        });
            }
        });
    }
}
