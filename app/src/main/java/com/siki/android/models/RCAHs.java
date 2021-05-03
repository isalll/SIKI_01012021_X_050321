package com.siki.android.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by blastocode on 8/13/17.
 */

public class RCAHs implements Parcelable{
    public String kodeHS;
    public String uraian;
    public String rca;
    public String rsca;
    public String tbi;


    public RCAHs() {

    }

    protected RCAHs(Parcel in) {
        kodeHS = in.readString();
        uraian = in.readString();
        rca = in.readString();
        rsca = in.readString();
        tbi = in.readString();

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(kodeHS);
        dest.writeString(uraian);
        dest.writeString(rca);
        dest.writeString(rsca);
        dest.writeString(tbi);

    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RCAHs> CREATOR = new Creator<RCAHs>() {
        @Override
        public RCAHs createFromParcel(Parcel in) {
            return new RCAHs(in);
        }

        @Override
        public RCAHs[] newArray(int size) {
            return new RCAHs[size];
        }
    };
}
