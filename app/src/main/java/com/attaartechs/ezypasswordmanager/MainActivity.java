package com.attaartechs.ezypasswordmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;

import android.os.Bundle;
import android.view.View;


import com.attaartechs.ezypasswordmanager.Utils.PreferenceHelper;
import com.fxn.cue.Cue;
import com.fxn.cue.enums.Type;


public class MainActivity extends AppCompatActivity {

    private CardView btnPasswords;
    private CardView btnAddNewPassword;
    private CardView btnGenerateNewPassword;
    private CardView btnChangePin;
    private CardView btnBackup;
    private CardView btnRestore;

    private Context myContext;

    private PreferenceHelper preferenceHelper;

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

        preferenceHelper = new PreferenceHelper(this);

        btnPasswords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent intent = new Intent(myContext,PasswordsActivity.class);
//                startActivity(intent);


                Cue.init()
                        .with(myContext)
                        .setMessage(myContext.getResources().getString(R.string.id_empty_passwords))
                        .setType(Type.DANGER).setPadding(40).setTextSize(17)
                        .show();

            }
        });
    }
}
