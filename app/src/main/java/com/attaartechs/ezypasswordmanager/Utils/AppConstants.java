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


    String KEY_JSON_ARRAY = "passwords_lis";

    String KEY_PASSWORD_NAME = "password_name";
    String KEY_password_text = "password_text";
    String KEY_CATEGORY_Id = "category_id";
    String KEY_Id = "id";
    String KEY_is_categorized = "is_categorized";
    List<Category> listCategory = Arrays.asList(new Category(1,"Facebook",R.drawable.facebook),
    new Category(2,"Gmail",R.drawable.google), new Category(3,"Twitter",R.drawable.twitter)
    ,new Category(4,"Yahoo",R.drawable.yahoo),new Category(5,"Snap Chat",R.drawable.snapchat)
    ,new Category(6,"Youtube",R.drawable.youtube),new Category(7,"GooglePlus",R.drawable.googleplus)
    ,new Category(8,"Linkedin",R.drawable.linkedin),new Category(9,"Skype",R.drawable.skype)
    ,new Category(10,"Telegram",R.drawable.telegram),new Category(11,"Reddit",R.drawable.reddit)
    ,new Category(4,"Tumblr",R.drawable.tumblr));
}
