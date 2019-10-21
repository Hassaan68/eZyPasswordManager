package com.attaartechs.ezypasswordmanager.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Muhammad Hassaan on 10/19/2019.
 */
public class Password implements Parcelable {

    public String csName = "";
    public String csPassword = "";
    public int nId = 0;
    public int categoryId = 0;

    public int getCategoryId() {
        return categoryId;
    }

    public boolean isCategorized() {
        return isCategorized;
    }

    public void setCategorized(boolean categorized) {
        isCategorized = categorized;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    private boolean isCategorized = false;

    public Password() {
    }

    public Password(String csPassword,String csTitle,int nId) {
        this.csName = csTitle;
        this.csPassword = csPassword;
        this.nId = nId;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.csName);
        dest.writeString(this.csPassword);
        dest.writeInt(this.nId);
        dest.writeInt(this.categoryId);
        dest.writeByte(this.isCategorized ? (byte) 1 : (byte) 0);
    }

    protected Password(Parcel in) {
        this.csName = in.readString();
        this.csPassword = in.readString();
        this.nId = in.readInt();
        this.categoryId = in.readInt();
        this.isCategorized = in.readByte() != 0;
    }

    public static final Creator<Password> CREATOR = new Creator<Password>() {
        @Override
        public Password createFromParcel(Parcel source) {
            return new Password(source);
        }

        @Override
        public Password[] newArray(int size) {
            return new Password[size];
        }
    };
}
