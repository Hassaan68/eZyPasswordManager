package com.attaartechs.ezypasswordmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import com.attaartechs.ezypasswordmanager.Utils.AppConstants;
import com.attaartechs.ezypasswordmanager.Utils.PreferenceHelper;
import com.attaartechs.ezypasswordmanager.models.Password;
import com.fxn.cue.Cue;
import com.fxn.cue.enums.Type;

import java.util.ArrayList;
import java.util.Random;

import io.paperdb.Paper;


public class MainActivity extends AppCompatActivity {

    private CardView btnPasswords;
    private CardView btnAddNewPassword;
    private CardView btnGenerateNewPassword;
    private CardView btnChangePin;
    private CardView btnBackup;
    private CardView btnRestore;

    private static final String ALLOWED_CHARACTERS ="0123456789qwertyuiopasdfghjklzxcvbnm";


    private Context myContext;

    private PreferenceHelper preferenceHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myContext = this;

        Paper.init(myContext);

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



                if(Paper.book().read(AppConstants.KEY_PASSWORDS_LIST,new ArrayList<Password>()).size() > 0)
                {
                    Intent intent = new Intent(myContext,PasswordsActivity.class);
                   startActivity(intent);
                }
                else {

                    Cue.init()
                            .with(myContext)
                            .setMessage(myContext.getResources().getString(R.string.id_empty_passwords))
                            .setType(Type.DANGER).setPadding(40).setTextSize(17)
                            .show();
                }

            }
        });

        btnAddNewPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                                Intent intent = new Intent(myContext,AddPasswordActivity.class);
                startActivity(intent);
            }
        });

        /************************************************************************************************/

        btnGenerateNewPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(myContext,AddPasswordActivity.class);
                intent.putExtra(AppConstants.KEY_GENERATED_PASSWORD,getRandomString(10));
                startActivity(intent);
            }
        });

        btnChangePin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(myContext,InitialActivity.class);
                intent.putExtra(AppConstants.INTENT_KEY_FROM_CHANGE_PIN,true);
                startActivity(intent);
            }
        });
    }



    /************************************************************************************************/
    /************************************************************************************************/

    private static String getRandomString(final int sizeOfRandomString)
    {
        final Random random=new Random();
        final StringBuilder sb=new StringBuilder(sizeOfRandomString);
        for(int i=0;i<sizeOfRandomString;++i)
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
        return sb.toString();
    }
}
