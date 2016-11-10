package semiramis.uyap.model;

import java.util.Date;

public class Mernis {

	private String tcNo;
	private String borcluAdi;
	private String icraDairesi;
	private String alacakli;
	private Date olumTarihi;
	private Date beyan;
	private String dosyaNo;
	private String sonuc;

	public String getTcNo() {
		return tcNo;
	}

	public void setTcNo(String tcNo) {
		this.tcNo = tcNo;
	}

	public String getBorcluAdi() {
		return borcluAdi;
	}

	public void setBorcluAdi(String borcluAdi) {
		this.borcluAdi = borcluAdi;
	}

	public String getIcraDairesi() {
		return icraDairesi;
	}

	public void setIcraDairesi(String icraDairesi) {
		this.icraDairesi = icraDairesi;
	}

	public String getAlacakli() {
		return alacakli;
	}

	public void setAlacakli(String alacakli) {
		this.alacakli = alacakli;
	}

	public Date getOlumTarihi() {
		return olumTarihi;
	}

	public void setOlumTarihi(Date olumTarihi) {
		this.olumTarihi = olumTarihi;
	}

	public Date getBeyan() {
		return beyan;
	}

	public void setBeyan(Date beyan) {
		this.beyan = beyan;
	}

	public String getDosyaNo() {
		return dosyaNo;
	}

	public void setDosyaNo(String dosyaNo) {
		this.dosyaNo = dosyaNo;
	}

	public String getSonuc() {
		return sonuc;
	}

	public void setSonuc(String sonuc) {
		this.sonuc = sonuc;
	}

}
