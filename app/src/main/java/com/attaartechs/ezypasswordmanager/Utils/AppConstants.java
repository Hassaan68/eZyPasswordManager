package com.attaartechs.ezypasswordmanager.Utils;

import com.attaartechs.ezypasswordmanager.R;
import com.attaartechs.ezypasswordmanager.models.Category;
import com.attaartechs.ezypasswordmanager.models.Password;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Muhammad Hassaan on 10/19/2019.
 */
public interface AppConstants {

    String PREF_NAME = "ezyPassManager";

    String KEY_REMOVE_ADS = "remove_ads";

    String KEY_PASSWORDS_LIST = "passwordsList";

    String KEY_GENERATED_PASSWORD = "generated_password";

    String PREF_KEY_PIN = "saved_pin";

    String INTENT_KEY_FROM_CHANGE_PIN = "from_change_pin";


    List<Category> listCategory = Arrays.asList(new Category(1,"Facebook",R.drawable.facebook),
    new Category(2,"Gmail",R.drawable.google), new Category(3,"Twitter",R.drawable.twitter)
    ,new Category(4,"Yahoo",R.drawable.yahoo),new Category(5,"Snap Chat",R.drawable.snapchat));
}
