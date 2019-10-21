package com.attaartechs.ezypasswordmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.attaartechs.ezypasswordmanager.Utils.AppConstants;
import com.attaartechs.ezypasswordmanager.adapters.CategoryAdapter;
import com.attaartechs.ezypasswordmanager.models.Category;
import com.attaartechs.ezypasswordmanager.models.Password;
import com.balysv.materialripple.MaterialRippleLayout;
import com.fxn.cue.Cue;
import com.fxn.cue.enums.Type;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class AddPasswordActivity extends AppCompatActivity {

    private MaterialRippleLayout btnBack;

    private MaterialRippleLayout btnSave;

    private TextInputEditText editPassword;
    private TextInputEditText ediTitle;

    private List<Category> listCategories;
    private RecyclerView mRecyclerView;
    private Context myContext;
    private List<Password> listPasswords;

    private int previousSelectedCategory = -1;
    private CategoryAdapter categoryAdapter = null;
    private Category selectedCategory = null;
    private boolean isPasswordCategorized = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_password);

        myContext = this;


        listCategories = AppConstants.listCategory;
        listPasswords = Paper.book().read(AppConstants.KEY_PASSWORDS_LIST,new ArrayList<Password>());

        mRecyclerView = findViewById(R.id.recyclerCategory);

        categoryAdapter = new CategoryAdapter(myContext,listCategories);

        categoryAdapter.setListener(new CategoryAdapter.CategoryListener() {
            @Override
            public void onCategoryClicked(Category category,int position) {

                if(previousSelectedCategory == -1)
                {
                    previousSelectedCategory = position;
                }
                else
                {
                    listCategories.get(previousSelectedCategory).isChecked = false;
                    previousSelectedCategory = position;
                }
                isPasswordCategorized = !isPasswordCategorized;
                selectedCategory = category;
                listCategories.get(position).isChecked = true ;
                categoryAdapter.notifyDataSetChanged();


            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(myContext);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(categoryAdapter);
        btnBack = findViewById(R.id.btnBack);

        btnSave = findViewById(R.id.btnSave);

        ediTitle = findViewById(R.id.editPasswordName);
        editPassword = findViewById(R.id.editPassword);

        if(getIntent().getExtras()!=null)
        if(getIntent().getExtras().getString(AppConstants.KEY_GENERATED_PASSWORD,"").length() > 0)
        {
            editPassword.setText(getIntent().getExtras().getString(AppConstants.KEY_GENERATED_PASSWORD));
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ediTitle == null || editPassword == null)
                {
                    return;
                }
                if(ediTitle.getText().toString().trim().length() == 0)
                {
                    passwordValidationErrorMessage(myContext.getResources().getString(R.string.id_enter_password_titl));
                    return;
                }

                if(ediTitle.getText().toString().trim().length() == 0)
                {
                    passwordValidationErrorMessage(myContext.getResources().getString(R.string.id_enter_password));
                    return;
                }

                Password password = new Password(editPassword.getText().toString(),ediTitle.getText().toString(),listPasswords.size() + 1);


                if(isPasswordCategorized)
                {
                password.setCategoryId(selectedCategory.nId);
                password.setCategorized(true);

                }

                listPasswords.add(password);

                Paper.book().write(AppConstants.KEY_PASSWORDS_LIST, listPasswords);

                Cue.init()
                        .with(myContext)
                        .setMessage(myContext.getResources().getString(R.string.id_password_saved))
                        .setType(Type.SUCCESS).setPadding(40).setTextSize(17)
                        .show();

                finish();

            }
        });
    }


    /*********************************************************************************************/
    /*********************************************************************************************/
    private void passwordValidationErrorMessage(String csMessage)
    {
        Cue.init()
                .with(myContext)
                .setMessage(csMessage)
                .setType(Type.DARK).setPadding(40).setTextSize(17)
                .show();
    }
}
