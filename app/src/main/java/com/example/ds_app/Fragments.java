package com.example.ds_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

public class Fragments extends AppCompatActivity {
    /*Button homeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragments);
        homeBtn=findViewById(R.id.homeBtn);

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
    }


    public void btn1(View view) {
    }

    public void btn2(View view) {
    }*/

    FragmentTransaction fragmentTransaction;
    Button homeBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {       super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragments);

        homeBtn=findViewById(R.id.homeBtn);
        Switch sw = findViewById(R.id.switch1);

        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    Fragment1 firstFragment = new Fragment1();
                    fragmentTransaction.replace(R.id.fragment_container, firstFragment);
                    fragmentTransaction.commit();
                }
                else{
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    Fragment2 secondFragment = new Fragment2();
                    fragmentTransaction.replace(R.id.fragment_container,secondFragment);
                    fragmentTransaction.commit();
                }
            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });

    }
}