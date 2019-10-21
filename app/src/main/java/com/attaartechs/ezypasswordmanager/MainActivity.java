package com.attaartechs.ezypasswordmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import com.attaartechs.ezypasswordmanager.Utils.AppConstants;
import com.attaartechs.ezypasswordmanager.Utils.PreferenceHelper;
import com.attaartechs.ezypasswordmanager.models.Password;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.fxn.cue.Cue;
import com.fxn.cue.enums.Type;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import io.paperdb.Paper;


public class MainActivity extends AppCompatActivity {

    private CardView btnPasswords;
    private CardView btnAddNewPassword;
    private CardView btnGenerateNewPassword;
    private CardView btnChangePin;
    private CardView btnBackup;
    private CardView btnRestore;



    private View layoutProgress;
    // Choose authentication providers
    List<AuthUI.IdpConfig> providers = Arrays.asList(
            new AuthUI.IdpConfig.EmailBuilder().build(),
            new AuthUI.IdpConfig.PhoneBuilder().build(),
            new AuthUI.IdpConfig.GoogleBuilder().build());


    private static final String ALLOWED_CHARACTERS ="0123456789qwertyuiopasdfghjklzxcvbnm";

    public static final int RC_SIGN_IN_Backup = 1000;
    public static final int RC_SIGN_IN_Restore = 1002;

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

        layoutProgress = findViewById(R.id.layoutProgress);
        layoutProgress.setVisibility(View.GONE);
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

        btnRestore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create and launch sign-in intent

                List<Password> listPasswords = Paper.book().read(AppConstants.KEY_PASSWORDS_LIST,new ArrayList<Password>());

                if(listPasswords.size() > 0)
                {
                    new LovelyStandardDialog(myContext, LovelyStandardDialog.ButtonLayout.HORIZONTAL)
                            .setTopColorRes(R.color.id_white)
                            .setIcon(R.drawable.restore)
                            .setButtonsColorRes(R.color.colorAccent)
                            .setTitle("Restore?")
                            .setMessage("current passwords may be lost or changed. Are you sure you want to continue?")
                            .setPositiveButton(myContext.getResources().getString(R.string.id_yes), new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivityForResult(
                                            AuthUI.getInstance()
                                                    .createSignInIntentBuilder()
                                                    .setAvailableProviders(providers)
                                                    .setIsSmartLockEnabled(false)
                                                    .build(),
                                            RC_SIGN_IN_Restore);

                                }
                            })
                            .setNegativeButton(myContext.getResources().getString(R.string.id_no), null)
                            .show();
                    return;
                }

                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setAvailableProviders(providers)
                                .setIsSmartLockEnabled(false)
                                .build(),
                        RC_SIGN_IN_Restore);
            }
        });

        btnBackup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create and launch sign-in intent

                List<Password> listPasswords = Paper.book().read(AppConstants.KEY_PASSWORDS_LIST,new ArrayList<Password>());

                if(listPasswords.size() == 0)
                {
                    Cue.init()
                            .with(myContext)
                            .setMessage(myContext.getResources().getString(R.string.id_empty_passwords))
                            .setType(Type.DANGER).setPadding(40).setTextSize(17)
                            .show();
                    return;
                }
                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setAvailableProviders(providers)
                                .setIsSmartLockEnabled(false)
                                .build(),
                        RC_SIGN_IN_Backup);
            }
        });


        layoutProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN_Backup) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                BackupData(user);
                // ...
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }

       else if (requestCode == RC_SIGN_IN_Restore) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                RestoreData(user);

                // ...
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }



    }

    /*********************************************************************************************/
    //Hassaan: Method to backup data on firebase
    /********************************************************************************************/
    private void BackupData(FirebaseUser user)
    {
        layoutProgress.setVisibility(View.VISIBLE);
        List<Password> listPasswords = Paper.book().read(AppConstants.KEY_PASSWORDS_LIST,new ArrayList<Password>());

        JSONArray passwordsArray = new JSONArray();

        for(int nIndex = 0; nIndex < listPasswords.size(); nIndex++)
        {
            JSONObject passwordObject = new JSONObject();
            Password password = listPasswords.get(nIndex);

            try {

                passwordObject.put(AppConstants.KEY_PASSWORD_NAME, password.csName);
                passwordObject.put(AppConstants.KEY_password_text, password.csPassword);
                passwordObject.put(AppConstants.KEY_CATEGORY_Id, password.categoryId);
                passwordObject.put(AppConstants.KEY_is_categorized, password.isCategorized());
                passwordObject.put(AppConstants.KEY_Id,password.nId);
                passwordsArray.put(passwordObject);

            }
            catch (Exception e)
            {

                e.printStackTrace();
            }


        }


        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference databaseReference = database.getReference("Users");

        databaseReference.child(user.getUid()).child(AppConstants.KEY_PASSWORDS_LIST).setValue(passwordsArray.toString());

        layoutProgress.setVisibility(View.GONE);

        Cue.init()
                .with(myContext)
                .setMessage(myContext.getResources().getString(R.string.id_passwords_successfully_backed_up))
                .setType(Type.SUCCESS).setPadding(40).setTextSize(17)
                .show();

    }


    /*********************************************************************************************/
    //Hassaan: Method to restore Data on Firebase
    /********************************************************************************************/
    private void RestoreData(FirebaseUser user)
    {

        layoutProgress.setVisibility(View.VISIBLE);

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference databaseReference = database.getReference("Users");



        databaseReference.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                if(dataSnapshot.exists()) {
                    String value = dataSnapshot.child(AppConstants.KEY_PASSWORDS_LIST).getValue(String.class);

                    Toast.makeText(myContext, value, Toast.LENGTH_SHORT).show();

                    try {
                        JSONArray passwordsJsonArray = new JSONArray(value);

                        List<Password> listPasswords = new ArrayList<>();
                        for (int nIndex = 0; nIndex < passwordsJsonArray.length(); nIndex++) {
                            JSONObject currentPassword = passwordsJsonArray.getJSONObject(nIndex);
                            Password password = new Password();
                            password.setCategorized(currentPassword.getBoolean(AppConstants.KEY_is_categorized));
                            password.csPassword = currentPassword.getString(AppConstants.KEY_password_text);
                            password.csName = currentPassword.getString(AppConstants.KEY_PASSWORD_NAME);
                            password.nId = currentPassword.getInt(AppConstants.KEY_Id);
                            password.categoryId = currentPassword.getInt(AppConstants.KEY_CATEGORY_Id);

                            listPasswords.add(password);

                        }

                        Paper.book().write(AppConstants.KEY_PASSWORDS_LIST, listPasswords);

                        Cue.init()
                                .with(myContext)
                                .setMessage(myContext.getResources().getString(R.string.id_passwords_restored))
                                .setType(Type.SUCCESS).setPadding(40).setTextSize(17)
                                .show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    Cue.init()
                            .with(myContext)
                            .setMessage(myContext.getResources().getString(R.string.id_no_backup_found))
                            .setType(Type.SUCCESS).setPadding(40).setTextSize(17)
                            .show();
                }

                layoutProgress.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

                layoutProgress.setVisibility(View.GONE);
            }
        });
    }


}
