package com.attaartechs.ezypasswordmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.attaartechs.ezypasswordmanager.Utils.AppConstants;
import com.attaartechs.ezypasswordmanager.Utils.PreferenceHelper;
import com.chaos.view.PinView;
import com.fxn.cue.Cue;
import com.fxn.cue.enums.Type;

public class InitialActivity extends AppCompatActivity {

    private TextView firstPinLabel,secondPinLabel;

    private PinView mainPinView,secondPinView;

    private PreferenceHelper preferenceHelper;

    private boolean isFromChangePin = false;

    private Context myContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);
        myContext = this;
        preferenceHelper = new PreferenceHelper(InitialActivity.this);

        if(getIntent().getExtras() != null)
        isFromChangePin = getIntent().getExtras().getBoolean(AppConstants.INTENT_KEY_FROM_CHANGE_PIN,false);


        firstPinLabel = findViewById(R.id.firstPinViewLabel);
        secondPinLabel = findViewById(R.id.secondPinViewLabel);

        mainPinView = findViewById(R.id.firstPinView);
        secondPinView = findViewById(R.id.secondPinView);

        if(preferenceHelper.getPin().length() > 0)
        {
            secondPinView.setVisibility(View.GONE);
            secondPinLabel.setVisibility(View.GONE);
        }

        if(isFromChangePin)
        {
            firstPinLabel.setText(myContext.getResources().getString(R.string.id_old_pin));
            secondPinLabel.setVisibility(View.VISIBLE);
            secondPinView.setVisibility(View.VISIBLE);
        }

        mainPinView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {



            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().length() == 4)
                {
                    if(preferenceHelper.getPin().length() > 0)
                    {

                        if(s.toString().equalsIgnoreCase(preferenceHelper.getPin()))
                        {
                            if(!isFromChangePin)
                            {
                                Intent intent = new Intent(myContext, MainActivity.class);
                                myContext.startActivity(intent);
                                finish();
                            }
                            else
                            {
                                mainPinView.setError(null);
                                mainPinView.setLineColor(myContext.getResources().getColor(R.color.colorPrimaryDark));
                            }

                        }

                        else
                        {
                            mainPinView.setLineColor(Color.RED);
                            mainPinView.setText("");
                            mainPinView.setError(myContext.getResources().getString(R.string.id_wrong_pin));
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        secondPinView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {



            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().length() == 4)
                {


                        if(s.toString().equalsIgnoreCase(mainPinView.getText().toString()))
                        {


                            if(!isFromChangePin) {
                                Intent intent = new Intent(myContext, MainActivity.class);
                                myContext.startActivity(intent);
                                preferenceHelper.setPin(mainPinView.getText().toString());
                                finish();
                            }
                            else
                            {
                                if(mainPinView.getText().toString().equalsIgnoreCase(preferenceHelper.getPin())) {

                                    Cue.init()
                                            .with(myContext)
                                            .setMessage(myContext.getResources().getString(R.string.id_pin_changed))
                                            .setType(Type.SUCCESS).setPadding(40).setTextSize(17)
                                            .show();
                                    finish();
                                }
                                else
                                {
                                    secondPinView.setLineColor(Color.RED);
                                    secondPinView.setError(myContext.getResources().getString(R.string.id_wrong_old_pin));
                                }
                            }
                        }

                        else
                        {

                            secondPinView.setLineColor(Color.RED);
                           // secondPinView.setText("");

                            secondPinView.setError(myContext.getResources().getString(R.string.id_mismatch));


                        }

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
