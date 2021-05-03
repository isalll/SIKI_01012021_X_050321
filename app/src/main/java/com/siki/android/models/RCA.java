package com.siki.android.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by blastocode on 8/13/17.
 */

public class RCA implements Parcelable {
    public String group;
    public String uraian;
    public String jumlah;


    public RCA() {

    }

    protected RCA(Parcel in) {
        group = in.readString();
        uraian = in.readString();
        jumlah = in.readString();

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(group);
        dest.writeString(uraian);
        dest.writeString(jumlah);

    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RCA> CREATOR = new Creator<RCA>() {
        @Override
        public RCA createFromParcel(Parcel in) {
            return new RCA(in);
        }

        @Override
        public RCA[] newArray(int size) {
            return new RCA[size];
        }
    };
}
