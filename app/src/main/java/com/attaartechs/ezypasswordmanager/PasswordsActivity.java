package com.attaartechs.ezypasswordmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.balysv.materialripple.MaterialRippleLayout;

public class PasswordsActivity extends AppCompatActivity implements View.OnClickListener {


    private MaterialRippleLayout btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passwords);

       btnBack =  findViewById(R.id.btnBack);

       btnBack.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               finish();
           }
       });
    }


    /**********************************************************************************************/
    /***********************************************************************************************/
    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.btnBack:
            {
                onBackPressed();
                break;

            }
        }
    }
}
