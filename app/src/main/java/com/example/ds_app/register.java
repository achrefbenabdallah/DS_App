package com.example.ds_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class register extends AppCompatActivity {
    EditText mFullname,mEmail,mPassword,mPhone;
    Button mRegisterBtn;
    TextView mLoginBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    FirebaseFirestore fStore;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFullname= findViewById(R.id.fullname);
        mEmail=findViewById(R.id.email);
        mPassword=findViewById(R.id.password);
        mPhone=findViewById(R.id.phone);
        mRegisterBtn=findViewById(R.id.registerbtn);
        mLoginBtn=findViewById(R.id.createtext);
        progressBar=findViewById(R.id.progressBar);

        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();

        if (fAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=mEmail.getText().toString().trim();
                String password=mPassword.getText().toString().trim();
                String fullname=mFullname.getText().toString().trim();
                String phone=mPhone.getText().toString().trim();


                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is empty !!");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Password is empty !!");
                    return;
                }
                if(password.length()<6){
                    mPassword.setError("Password must be more then 6 characters !!");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //register user in FireBase

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            Toast.makeText(register.this, "User Created", Toast.LENGTH_SHORT).show();

                            userId=fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference=fStore.collection("users").document(userId);
                            Map<String,Object> user=new HashMap<>();
                            user.put("FullName",fullname);
                            user.put("Email",email);
                            user.put("Phone",phone);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("TAG", "onSuccess: User profile is created with UID"+userId);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("TAG", "onFailure: "+e.toString() );
                                }
                            });
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else {
                            Toast.makeText(register.this, "Error !"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),login.class));
            }
        });
    }
}