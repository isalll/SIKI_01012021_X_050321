package com.siki.android.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by blastocode on 8/13/17.
 */

public class PdHs implements Parcelable{
    public String kodeHS;
    public String deskripsi;
    public String komoditas;
    public String lonjakan;
    public String bec;
    public String kelompok;

    public PdHs() {

    }

    protected PdHs(Parcel in) {
        kodeHS = in.readString();
        deskripsi = in.readString();
        komoditas = in.readString();
        lonjakan = in.readString();
        bec = in.readString();
        kelompok = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(kodeHS);
        dest.writeString(deskripsi);
        dest.writeString(komoditas);
        dest.writeString(lonjakan);
        dest.writeString(bec);
        dest.writeString(kelompok);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PdHs> CREATOR = new Creator<PdHs>() {
        @Override
        public PdHs createFromParcel(Parcel in) {
            return new PdHs(in);
        }

        @Override
        public PdHs[] newArray(int size) {
            return new PdHs[size];
        }
    };
}
