package com.siki.android.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by blastocode on 8/13/17.
 */

public class EWS implements Parcelable {
    public String judul;
    public String jumlahData;
    public String lonjakanTerbesar;
    public String lonjakan30;
    //public String lonjakan20;
    //public String lonjakan15;
    public String tahun1;
    public String tahun2;
    public String bulan1;
    public String bulan2;

    public EWS() {

    }

    protected EWS(Parcel in) {
        judul = in.readString();
        jumlahData = in.readString();
        lonjakanTerbesar = in.readString();
        lonjakan30 = in.readString();
        //lonjakan20 = in.readString();
        //lonjakan15 = in.readString();
        tahun1 = in.readString();
        tahun2 = in.readString();
        bulan1 = in.readString();
        bulan2 = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(judul);
        dest.writeString(jumlahData);
        dest.writeString(lonjakanTerbesar);
        dest.writeString(lonjakan30);
        //dest.writeString(lonjakan20);
        //dest.writeString(lonjakan15);
        dest.writeString(tahun1);
        dest.writeString(tahun2);
        dest.writeString(bulan1);
        dest.writeString(bulan2);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<EWS> CREATOR = new Creator<EWS>() {
        @Override
        public EWS createFromParcel(Parcel in) {
            return new EWS(in);
        }

        @Override
        public EWS[] newArray(int size) {
            return new EWS[size];
        }
    };
}
