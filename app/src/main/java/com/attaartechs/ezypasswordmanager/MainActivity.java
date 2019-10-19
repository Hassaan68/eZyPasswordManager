package com.attaartechs.ezypasswordmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.balysv.materialripple.MaterialRippleLayout;


public class MainActivity extends AppCompatActivity {

    private CardView btnPasswords;
    private CardView btnAddNewPassword;
    private CardView btnGenerateNewPassword;
    private CardView btnChangePin;
    private CardView btnBackup;
    private CardView btnRestore;

    private Context myContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myContext = this;
        btnAddNewPassword = findViewById(R.id.btnAddNewPassword);
        btnPasswords = findViewById(R.id.btnPasswords);
        btnGenerateNewPassword = findViewById(R.id.btnGenerateNewPassword);
        btnChangePin = findViewById(R.id.btnChangePin);
        btnBackup = findViewById(R.id.btnBackupPasswords);
        btnRestore = findViewById(R.id.btnRestorePasswords);

       // MaterialRippleLayout.on(btnPasswords).rippleColor(Color.GRAY).create();
        btnPasswords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
//                Intent intent = new Intent(myContext,PasswordsActivity.class);
//                startActivity(intent);


            }
        });
    }
}
