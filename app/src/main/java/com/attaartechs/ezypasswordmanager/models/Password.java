package com.attaartechs.ezypasswordmanager.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Muhammad Hassaan on 10/19/2019.
 */
public class Password implements Parcelable {

    private String csName = "";
    private String csPassword = "";
    private int nId = 0;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.csName);
        dest.writeString(this.csPassword);
        dest.writeInt(this.nId);
    }

    public Password() {
    }

    protected Password(Parcel in) {
        this.csName = in.readString();
        this.csPassword = in.readString();
        this.nId = in.readInt();
    }

    public static final Parcelable.Creator<Password> CREATOR = new Parcelable.Creator<Password>() {
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
