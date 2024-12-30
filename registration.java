//package com.example.chattingapplication;
//
//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;
//import com.google.firebase.storage.UploadTask;
//
//import de.hdodenhof.circleimageview.CircleImageView;
//
//
//
//public class registration extends AppCompatActivity {
//    TextView loginbut;
//    EditText rg_username, rg_email, rg_password, rg_repassword;
//    Button rg_signup;
//    CircleImageView rg_profileImg;
//    FirebaseAuth auth;
//    Uri imageURI;
//    String imageuri;
//    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
//    FirebaseDatabase database;
//    FirebaseStorage storage;
//    ProgressDialog progressDialog;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_registration);
//        progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage("Establishing The Account");
//        progressDialog.setCancelable(false);
//        getSupportActionBar().hide();
//        database = FirebaseDatabase.getInstance();
//        storage = FirebaseStorage.getInstance();
//        auth = FirebaseAuth.getInstance();
//        loginbut = findViewById(R.id.loginbut);
//        rg_username = findViewById(R.id.rgusername);
//        rg_email = findViewById(R.id.rgemail);
//        rg_password = findViewById(R.id.rgpassword);
//        rg_repassword = findViewById(R.id.rgrepassword);
//        rg_profileImg = findViewById(R.id.profilerg0);
//        rg_signup = findViewById(R.id.signupbutton);
//
//
//        loginbut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(registration.this,login.class);
//                startActivity(intent);
//                finish();
//            }
//        });
//
//        rg_signup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String namee = rg_username.getText().toString();
//                String emaill = rg_email.getText().toString();
//                String Password = rg_password.getText().toString();
//                String cPassword = rg_repassword.getText().toString();
//                String status = "Hey I'm Using This Application";
//
//                if (TextUtils.isEmpty(namee) || TextUtils.isEmpty(emaill) ||
//                        TextUtils.isEmpty(Password) || TextUtils.isEmpty(cPassword)){
//                    progressDialog.dismiss();
//                    Toast.makeText(registration.this, "Please Enter Valid Information", Toast.LENGTH_SHORT).show();
//                }else  if (!emaill.matches(emailPattern)){
//                    progressDialog.dismiss();
//                    rg_email.setError("Type A Valid Email Here");
//                }else if (Password.length()<6){
//                    progressDialog.dismiss();
//                    rg_password.setError("Password Must Be 6 Characters Or More");
//                }else if (!Password.equals(cPassword)){
//                    progressDialog.dismiss();
//                    rg_password.setError("The Password Doesn't Match");
//                }else {
//                    auth.createUserWithEmailAndPassword(emaill,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            if (task.isSuccessful()){
//                                String id = task.getResult().getUser().getUid();
//
//                                DatabaseReference reference = database.getReference().child("user").child(id);
//                                StorageReference storageReference = storage.getReference().child("Upload").child(id);
//                                progressDialog.show();
//                                Intent intent = new Intent(registration.this,MainActivity.class);
//                                startActivity(intent);
//                                finish();
//                                Toast.makeText(registration.this, "Registration is completed successfully!", Toast.LENGTH_SHORT).show();
//                                startActivity(new Intent(registration.this, MainActivity.class));
//                                finish();
//                                if (imageURI!=null){
//                                    storageReference.putFile(imageURI).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
//                                            if (task.isSuccessful()){
//                                                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                                    @Override
//                                                    public void onSuccess(Uri uri) {
//                                                        imageuri = uri.toString();
//                                                        Users users = new Users(id,namee,emaill,Password,imageuri,status);
//                                                        reference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                            @Override
//                                                            public void onComplete(@NonNull Task<Void> task) {
//                                                                if (task.isSuccessful()){
//                                                                    progressDialog.show();
//                                                                    Toast.makeText(registration.this, "Registration is completed successfully!", Toast.LENGTH_SHORT).show();
//                                                                    startActivity(new Intent(registration.this, MainActivity.class));
//                                                                    finish();
//
//                                                                }else {
//                                                                    Toast.makeText(registration.this, "Error in creating the user", Toast.LENGTH_SHORT).show();
//                                                                }
//                                                            }
//                                                        });
//                                                    }
//                                                });
//                                            }
//                                        }
//                                    });
//                                }else {
//                                    String status = "Hey I'm Using This Application";
//                                    imageuri = "https://firebasestorage.googleapis.com/v0/b/av-messenger-dc8f3.appspot.com/o/man.png?alt=media&token=880f431d-9344-45e7-afe4-c2cafe8a5257";
//                                    Users users = new Users(id,namee,emaill,Password,imageuri,status);
//                                    reference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<Void> task) {
//                                            if (task.isSuccessful()){
//                                                progressDialog.show();
//                                                Intent intent = new Intent(registration.this,MainActivity.class);
//                                                startActivity(intent);
//                                                finish();
//                                            }else {
//                                                Toast.makeText(registration.this, "Error in creating the user", Toast.LENGTH_SHORT).show();
//                                            }
//                                        }
//                                    });
//                                }
//                            }else {
//                                Toast.makeText(registration.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//                }
//
//            }
//        });
//
//
//        rg_profileImg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(Intent.createChooser(intent,"Select Picture"),10);
//            }
//        });
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode==10){
//            if (data!=null){
//                imageURI = data.getData();
//                rg_profileImg.setImageURI(imageURI);
//            }
//        }
//    }
//}
//
//

package com.example.chattingapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;

import com.google.firebase.firestore.FirebaseFirestore;



public class registration extends AppCompatActivity {

    TextView loginbut;
    EditText rg_username, rg_email, rg_password, rg_repassword;
    Button rg_signup;
    CircleImageView rg_profileImg;
    FirebaseAuth auth;
    Uri imageURI;
    String imageuri;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    FirebaseDatabase realtimeDatabase;
    FirebaseFirestore firestore;
    FirebaseStorage storage;
    ProgressDialog progressDialog;
    boolean useFirestore = true; // Change this to switch between Firestore and Realtime Database

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Establishing The Account");

        getSupportActionBar().hide();

        // Initialize Firebase components
        realtimeDatabase = FirebaseDatabase.getInstance();
        firestore = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();

        // UI components
        loginbut = findViewById(R.id.loginbut);
        rg_username = findViewById(R.id.rgusername);
        rg_email = findViewById(R.id.rgemail);
        rg_password = findViewById(R.id.rgpassword);
        rg_repassword = findViewById(R.id.rgrepassword);
        rg_profileImg = findViewById(R.id.profilerg0);
        rg_signup = findViewById(R.id.signupbutton);

        // Login button click listener
        loginbut.setOnClickListener(v -> {
            Intent intent = new Intent(registration.this, login.class);
            startActivity(intent);
            finish();
        });

        // Signup button click listener
        rg_signup.setOnClickListener(v -> registerUser());

        // Profile image click listener
        rg_profileImg.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 10);
        });
    }

    private void registerUser() {
        String name = rg_username.getText().toString();
        String email = rg_email.getText().toString();
        String password = rg_password.getText().toString();
        String confirmPassword = rg_repassword.getText().toString();
        String status = "Hey, I'm using this application!";

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) ||
                TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!email.matches(emailPattern)) {
            rg_email.setError("Enter a valid email");
            return;
        }

        if (password.length() < 6) {
            rg_password.setError("Password must be at least 6 characters");
            return;
        }

        if (!password.equals(confirmPassword)) {
            rg_repassword.setError("Passwords do not match");
            return;
        }

        progressDialog.show();

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                String userId = task.getResult().getUser().getUid();

                if (imageURI != null) {
                    uploadImageAndSaveUser(userId, name, email, password, status);
                } else {
                    saveUserToDatabase(userId, name, email, password, "default_image_url", status);
                }
            } else {
                progressDialog.dismiss();
                Toast.makeText(this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadImageAndSaveUser(String userId, String name, String email, String password, String status) {
        StorageReference storageReference = storage.getReference().child("Upload").child(userId);

        storageReference.putFile(imageURI).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                    String imageUrl = uri.toString();
                    saveUserToDatabase(userId, name, email, password, imageUrl, status);
                });
            } else {
                progressDialog.dismiss();
                Toast.makeText(this, "Image upload failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveUserToDatabase(String userId, String name, String email, String password, String imageUrl, String status) {
        Users user = new Users(userId, name, email, password, imageUrl, status);

        if (useFirestore) {
            firestore.collection("users").document(userId).set(user).addOnCompleteListener(task -> {
                handleDatabaseResponse(task, "Firestore");
            });
        } else {
            DatabaseReference reference = realtimeDatabase.getReference("users").child(userId);
            reference.setValue(user).addOnCompleteListener(task -> {
                handleDatabaseResponse(task, "Realtime Database");
            });
        }
    }

    private void handleDatabaseResponse(Task<Void> task, String databaseType) {
        progressDialog.dismiss();
        if (task.isSuccessful()) {
            Toast.makeText(this, "User saved to " + databaseType + " successfully!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else {
            Toast.makeText(this, "Error saving user to " + databaseType, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && data != null) {
            imageURI = data.getData();
            rg_profileImg.setImageURI(imageURI);
        }
    }
}
