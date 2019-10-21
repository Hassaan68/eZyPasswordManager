package com.attaartechs.ezypasswordmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.attaartechs.ezypasswordmanager.Utils.AppConstants;
import com.attaartechs.ezypasswordmanager.adapters.PasswordsAdapter;
import com.attaartechs.ezypasswordmanager.models.Category;
import com.attaartechs.ezypasswordmanager.models.Password;
import com.balysv.materialripple.MaterialRippleLayout;
import com.fxn.cue.Cue;
import com.fxn.cue.enums.Type;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;


import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class PasswordsActivity extends AppCompatActivity implements View.OnClickListener {


    private MaterialRippleLayout btnBack;

    private RecyclerView recyclerPasswords;

    private PasswordsAdapter passwordsAdapter = null;

    private Context myContext;

    private List<Password> listPasswords = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passwords);

        myContext = this;
        listPasswords = Paper.book().read(AppConstants.KEY_PASSWORDS_LIST,new ArrayList<Password>());
       btnBack =  findViewById(R.id.btnBack);
       passwordsAdapter = new PasswordsAdapter(myContext,listPasswords);

       passwordsAdapter.setListener(new PasswordsAdapter.PasswordListener() {
           @Override
           public void onPasswordClicked(Category category, int nposition) {

           }

           @Override
           public void onCopyClicked(String csTextToCopy) {

               ClipboardManager clipboard = (ClipboardManager) myContext.getSystemService(CLIPBOARD_SERVICE);

               clipboard.setText(csTextToCopy);

               Cue.init()
                       .with(myContext)
                       .setMessage(myContext.getResources().getString(R.string.id_password_copied))
                       .setType(Type.SUCCESS).setPadding(40).setTextSize(17)
                       .show();

           }

           @Override
           public void onLongClicked(final int nPosition) {

               new LovelyStandardDialog(myContext, LovelyStandardDialog.ButtonLayout.HORIZONTAL)
                       .setTopColorRes(R.color.colorPrimaryDark)
                       .setIcon(R.drawable.clear)
                       .setButtonsColorRes(R.color.colorAccent)
                       .setTitle("Delete?")
                       .setMessage("Are you sure you want to delete?")
                       .setPositiveButton(myContext.getResources().getString(R.string.id_yes), new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {

                               listPasswords.remove(nPosition);
                               Paper.book().write(AppConstants.KEY_PASSWORDS_LIST, listPasswords);

                               passwordsAdapter.notifyDataSetChanged();
                               if(listPasswords.size() == 0)
                               {
                                   finish();
                               }
                           }
                       })
                       .setNegativeButton(myContext.getResources().getString(R.string.id_no), null)
                       .show();

           }
       });
       recyclerPasswords = findViewById(R.id.recyclerPasswords);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(myContext);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerPasswords.setLayoutManager(linearLayoutManager);
        recyclerPasswords.setAdapter(passwordsAdapter);


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
