package com.siki.android.sqlite.to;

import java.util.Date;
import android.os.Parcel;
import android.os.Parcelable;

public class Perusahaan implements Parcelable {

	private int id;
	private String nama;
	private String no_iui;
	private String tgl_iui;
	//private Date dateOfBirth;
	
	
	
	private String tenaga_kerja_asing;
	private String tenaga_kerja_dn;
	private int kd_badan_hukum;
	private String npwp;
	private String produksi_utama;
	private String sektor;
	
	//private Date dateOfBirth;
	//private double salary;

	public Perusahaan() {
		super();
	}

	private Perusahaan(Parcel in) {
		super();
		this.id = in.readInt();
		this.nama = in.readString();
		//this.dateOfBirth = new Date(in.readLong());
		this.no_iui = in.readString();
		//this.tgl_iui = new Date(in.readLong());
		this.tgl_iui = in.readString();

		
		//this.tgl_iui = new Date(in.readLong());
		
		
		
	
		this.tenaga_kerja_asing = in.readString();
		this.tenaga_kerja_dn = in.readString();
		this.kd_badan_hukum = in.readInt();
		this.npwp = in.readString();
		this.produksi_utama = in.readString();
		this.sektor = in.readString();
		
		
		//this.salary = in.readDouble();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	
	public String gettgl_iui() {
		return tgl_iui;
	}

	/*	
	public Date gettgl_iui() {
		return tgl_iui;
	}


	public void settgl_iui(Date tgl_iui) {
		this.tgl_iui = tgl_iui;
	}
*/
	public void settgl_iui(String tgl_iui) {
		this.tgl_iui = tgl_iui;
	}
	
	
	public String getno_iui() {
		return no_iui;
	}

	public void setno_iui(String no_iui) {
		this.no_iui = no_iui;
	}

	
	public String gettenaga_kerja_asing() {
		return tenaga_kerja_asing;
	}

	public void settenaga_kerja_asing(String tenaga_kerja_asing) {
		this.tenaga_kerja_asing = tenaga_kerja_asing;
	}
	public String gettenaga_kerja_dn() {
		return tenaga_kerja_dn;
	}

	public void settenaga_kerja_dn(String tenaga_kerja_dn) {
		this.tenaga_kerja_dn = tenaga_kerja_dn;
	}
	public int getkd_badan_hukum() {
		return id;
	}

	public void setkd_badan_hukum(int kd_badan_hukum) {
		this.kd_badan_hukum = kd_badan_hukum;
	}
	public String getnpwp() {
		return npwp;
	}

	public void setnpwp(String npwp) {
		this.npwp = npwp;
	}
	public String getproduksi_utama() {
		return produksi_utama;
	}

	public void setproduksi_utama(String produksi_utama) {
		this.produksi_utama = produksi_utama;
	}
	public String getsektor() {
		return sektor;
	}

	public void setsektor(String sektor) {
		this.sektor = sektor;
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
		
		return "Perusahaan [id=" + id + ", nama=" + nama+", no_iui=" + no_iui + ", tgl_iui="
				+ tgl_iui + ", tenaga_kerja_asing=" + tenaga_kerja_asing+ ", tenaga_kerja_dn=" + tenaga_kerja_dn 
				+ ",kd_badan_hukum=" + kd_badan_hukum + ",npwp=" + npwp + ",produksi_utama=" + produksi_utama 
				+ ",sektor=" + sektor + "]";
		
		//return "Employee [id=" + id + ", name=" + nama +  ", dob="
		//+ tgl_iui + "]";
		
		//return "Employee [id=" + id + ", name=" + nama + ", dob="
		//+ tgl_iui + "]";
		//return "Perusahaan [id=" + id + ", nama=" + nama + ", no_iui=" + no_iui+ ", tgl_iui=" + tgl_iui + "]";

		
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
		Perusahaan other = (Perusahaan) obj;
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
		parcel.writeString(getNama());
		parcel.writeString(getno_iui());
		parcel.writeString(gettgl_iui());
		//
		//parcel.writeLong(gettgl_iui().getTime());
		//parcel.writeLong(getDateOfBirth().getTime());
	
		
		
		parcel.writeString(gettenaga_kerja_asing());
		parcel.writeString(gettenaga_kerja_dn());
		parcel.writeInt(getkd_badan_hukum());
		parcel.writeString(getnpwp());
		parcel.writeString(getproduksi_utama());
		parcel.writeString(getsektor());
		
		//parcel.writeLong(getDateOfBirth().getTime());
		//parcel.writeDouble(getSalary());
	}

	public static final Parcelable.Creator<Perusahaan> CREATOR = new Parcelable.Creator<Perusahaan>() {
		public Perusahaan createFromParcel(Parcel in) {
			return new Perusahaan(in);
		}

		public Perusahaan[] newArray(int size) {
			return new Perusahaan[size];
		}
	};

}
