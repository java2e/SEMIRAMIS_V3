package semimis.utils;

import java.util.Date;

public class GenelArama 
{
	public int id;
	public String muvekkilAdi, borcluAdi, icraDosyaNo, icraMudurlugu, cepTel, isTel;
	
	public int borcluId;
	
	public int borcluTelNo;
	
	public double borcMiktari;
	
	public int bankaMusteriNo;
	public String avukatServisNo;
	public String bankaServisNo;
	public Date gelisTarihi1, gelisTarihi2, hitamTarihi1, hitamTarihi2, takipTarihi1, takipTarihi2, sonucGelisTarihi, sonucHitamTarihi, sonucTakipTarihi;
	public String gelisTarihi, takipTarihi;
	
	
	private String renk;
	private String dosyaStatuAdi;
	private int dosyaStatuId;
	private int icraMudurlukId;
	
	private int izleme_id;
	
	
	
	
	
	public int getIzleme_id() {
		return izleme_id;
	}
	public void setIzleme_id(int izleme_id) {
		this.izleme_id = izleme_id;
	}
	public int getBorcluId() {
		return borcluId;
	}
	public void setBorcluId(int borcluId) {
		this.borcluId = borcluId;
	}
	public String getAvukatServisNo() {
		return avukatServisNo;
	}
	public void setAvukatServisNo(String avukatServisNo) {
		this.avukatServisNo = avukatServisNo;
	}
	public int getIcraMudurlukId() {
		return icraMudurlukId;
	}
	public void setIcraMudurlukId(int icraMudurlukId) {
		this.icraMudurlukId = icraMudurlukId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMuvekkilAdi() {
		return muvekkilAdi;
	}
	public void setMuvekkilAdi(String muvekkilAdi) {
		this.muvekkilAdi = muvekkilAdi;
	}
	public String getBorcluAdi() {
		return borcluAdi;
	}
	public void setBorcluAdi(String borcluAdi) {
		this.borcluAdi = borcluAdi;
	}
	public String getIcraDosyaNo() {
		return icraDosyaNo;
	}
	public void setIcraDosyaNo(String icraDosyaNo) {
		this.icraDosyaNo = icraDosyaNo;
	}
	public String getIcraMudurlugu() {
		return icraMudurlugu;
	}
	public void setIcraMudurlugu(String icraMudurlugu) {
		this.icraMudurlugu = icraMudurlugu;
	}
	public String getCepTel() {
		return cepTel;
	}
	public void setCepTel(String cepTel) {
		this.cepTel = cepTel;
	}
	public String getIsTel() {
		return isTel;
	}
	public void setIsTel(String isTel) {
		this.isTel = isTel;
	}

	public int getBankaMusteriNo() {
		return bankaMusteriNo;
	}
	public void setBankaMusteriNo(int bankaMusteriNo) {
		this.bankaMusteriNo = bankaMusteriNo;
	}
	public String getBankaServisNo() {
		return bankaServisNo;
	}
	public void setBankaServisNo(String bankaServisNo) {
		this.bankaServisNo = bankaServisNo;
	}
	
	
	

	public Date getGelisTarihi1() {
		return gelisTarihi1;
	}
	public void setGelisTarihi1(Date gelisTarihi1) {
		this.gelisTarihi1 = gelisTarihi1;
	}
	public Date getGelisTarihi2() {
		return gelisTarihi2;
	}
	public void setGelisTarihi2(Date gelisTarihi2) {
		this.gelisTarihi2 = gelisTarihi2;
	}
	public Date getHitamTarihi1() {
		return hitamTarihi1;
	}
	public void setHitamTarihi1(Date hitamTarihi1) {
		this.hitamTarihi1 = hitamTarihi1;
	}
	public Date getHitamTarihi2() {
		return hitamTarihi2;
	}
	public void setHitamTarihi2(Date hitamTarihi2) {
		this.hitamTarihi2 = hitamTarihi2;
	}
	public Date getTakipTarihi1() {
		return takipTarihi1;
	}
	public void setTakipTarihi1(Date takipTarihi1) {
		this.takipTarihi1 = takipTarihi1;
	}
	public Date getTakipTarihi2() {
		return takipTarihi2;
	}
	public void setTakipTarihi2(Date takipTarihi2) {
		this.takipTarihi2 = takipTarihi2;
	}
	public Date getSonucGelisTarihi() {
		return sonucGelisTarihi;
	}
	public void setSonucGelisTarihi(Date sonucGelisTarihi) {
		this.sonucGelisTarihi = sonucGelisTarihi;
	}
	public Date getSonucHitamTarihi() {
		return sonucHitamTarihi;
	}
	public void setSonucHitamTarihi(Date sonucHitamTarihi) {
		this.sonucHitamTarihi = sonucHitamTarihi;
	}
	public Date getSonucTakipTarihi() {
		return sonucTakipTarihi;
	}
	public void setSonucTakipTarihi(Date sonucTakipTarihi) {
		this.sonucTakipTarihi = sonucTakipTarihi;
	}
	public String getGelisTarihi() {
		return gelisTarihi;
	}
	public void setGelisTarihi(String gelisTarihi) {
		this.gelisTarihi = gelisTarihi;
	}
	public String getTakipTarihi() {
		return takipTarihi;
	}
	public void setTakipTarihi(String takipTarihi) {
		this.takipTarihi = takipTarihi;
	}
	public String getRenk() {
		return renk;
	}
	public void setRenk(String renk) {
		this.renk = renk;
	}
	public String getDosyaStatuAdi() {
		return dosyaStatuAdi;
	}
	public void setDosyaStatuAdi(String dosyaStatuAdi) {
		this.dosyaStatuAdi = dosyaStatuAdi;
	}
	public int getDosyaStatuId() {
		return dosyaStatuId;
	}
	public void setDosyaStatuId(int dosyaStatuId) {
		this.dosyaStatuId = dosyaStatuId;
	}
	
	
	

}
