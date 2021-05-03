package com.siki.android.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by blastocode on 8/13/17.
 */

public class InfoDataUpdate implements Parcelable {

    public String tahun1;
    public String tahun2;
    public String bulan1;
    public String bulan2;

    public InfoDataUpdate() {

    }

    protected InfoDataUpdate(Parcel in) {

        tahun1 = in.readString();
        tahun2 = in.readString();
        bulan1 = in.readString();
        bulan2 = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(tahun1);
        dest.writeString(tahun2);
        dest.writeString(bulan1);
        dest.writeString(bulan2);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<InfoDataUpdate> CREATOR = new Creator<InfoDataUpdate>() {
        @Override
        public InfoDataUpdate createFromParcel(Parcel in) {
            return new InfoDataUpdate(in);
        }

        @Override
        public InfoDataUpdate[] newArray(int size) {
            return new InfoDataUpdate[size];
        }
    };
}
