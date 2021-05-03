package com.siki.android.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by blastocode on 8/13/17.
 */

public class HSLINKHs implements Parcelable{
    public String kodeHS;
    public String deskripsi;
    public String komoditas;
    public String lonjakan;
    public String berat;
    public String kelompok;

    public HSLINKHs() {

    }

    protected HSLINKHs(Parcel in) {
        kodeHS = in.readString();
        deskripsi = in.readString();
        komoditas = in.readString();
        lonjakan = in.readString();
        berat = in.readString();
        kelompok = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(kodeHS);
        dest.writeString(deskripsi);
        dest.writeString(komoditas);
        dest.writeString(lonjakan);
        dest.writeString(berat);
        dest.writeString(kelompok);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<HSLINKHs> CREATOR = new Creator<HSLINKHs>() {
        @Override
        public HSLINKHs createFromParcel(Parcel in) {
            return new HSLINKHs(in);
        }

        @Override
        public HSLINKHs[] newArray(int size) {
            return new HSLINKHs[size];
        }
    };
}
