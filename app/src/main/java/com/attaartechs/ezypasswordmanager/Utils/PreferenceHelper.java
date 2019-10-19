package com.attaartechs.ezypasswordmanager.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Muhammad Hassaan on 10/19/2019.
 */
public class PreferenceHelper {

    private Context myContext = null;

    private SharedPreferences.Editor prefEditor;
    private SharedPreferences sharedPreferences;


    /*************************************************************************************************/
    /*************************************************************************************************/
    public PreferenceHelper(Context myContext)
    {
        this.myContext = myContext;

        sharedPreferences =  myContext.getSharedPreferences(AppConstants.PREF_NAME,Context.MODE_PRIVATE);

        prefEditor = sharedPreferences.edit();
    }

    /*************************************************************************************************/
    /*************************************************************************************************/
    public void setRemoveAds(boolean bStatus)
    {
        prefEditor.putBoolean(AppConstants.KEY_REMOVE_ADS,bStatus).commit();
    }

    /*************************************************************************************************/
    /*************************************************************************************************/
    public boolean getRemoveAds()
    {
        return sharedPreferences.getBoolean(AppConstants.KEY_REMOVE_ADS,false);
    }

    /*************************************************************************************************/
    /*************************************************************************************************/

}
