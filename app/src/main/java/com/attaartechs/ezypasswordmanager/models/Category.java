package com.attaartechs.ezypasswordmanager.models;

/**
 * Created by Muhammad Hassaan on 10/19/2019.
 */
public class Category {

    public int nId = 0;
    public String csName = "";
    public int nDrawableId;
    public boolean isChecked = false;

    public Category(int nId,String csName,int nDrawableId)
    {
        this.nId = nId;
        this.csName = csName;
        this.nDrawableId = nDrawableId;
    }
}
