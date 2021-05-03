package com.siki.android.sqlite.to;

import android.os.Parcel;
import android.os.Parcelable;

public class KomentarPerberita implements Parcelable {

	private int id;

	private String kd_hs;
	private String komentar;
	private String username;

	private String tenaga_kerja_asing;
	private String tenaga_kerja_dn;
	private int kd_badan_hukum;
	private String npwp;
	private String produksi_utama;
	private String sektor;

	//private Date dateOfBirth;
	//private double salary;

	public KomentarPerberita() {
		super();
	}

	private KomentarPerberita(Parcel in) {
		super();
		this.id = in.readInt();
		this.kd_hs = in.readString();
		this.komentar = in.readString();
		this.username = in.readString();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getKDHS() {
		return kd_hs;
	}
	public void setKDHS(String kd_hs) {
		this.kd_hs = kd_hs;
	}

	public String getKomentar() {
		return komentar;
	}
	public void setKomentar(String komentar) {
		this.komentar = komentar;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

/*	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}
*/
	@Override
	public String toString() {

		return "Komentar [id=" + id + ", kd_hs=" + kd_hs +", komentar=" + komentar + ", username="
				+ username + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KomentarPerberita other = (KomentarPerberita) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeInt(getId());
		parcel.writeString(getKDHS());
		parcel.writeString(getKomentar());
		parcel.writeString(getUsername());
	}

	public static final Creator<KomentarPerberita> CREATOR = new Creator<KomentarPerberita>() {
		public KomentarPerberita createFromParcel(Parcel in) {
			return new KomentarPerberita(in);
		}

		public KomentarPerberita[] newArray(int size) {
			return new KomentarPerberita[size];
		}
	};

}
