package semiramis.kasa.model;

import java.util.Date;

public class Tahsilat 
{
	
	private int id;
	private int icraDosyaId;
	private String icraDosyaNo;
	private String muvekkilAdi;
	private String borcluAdSoyad;
	private int borcluId;
	private Date gelisTarihi;
	private String borcTipi;
	private int dosyaTipiId;
	private String dosyaTipi;
	private int icraMudurlukId;
	private String icraMudurlugu;
	private Date tahsilatTarihi;
	private String tahsilatTipi;
	private double tahsilatMiktari;
	private int tahsilatStatusuId;
	private String tahsilatStatusu;
	private int kasaPersonelID;
	private int onaylayanID;
	private int tahsilatDurum;
	private int durum;
	private int tahsilatTipiId;
	private String gelisYeri;
	private String tasilatiYapan;
	private String kasaIslemiYapan;
	private String tahsilatMiktariTL;
	private String baknaServisNo;
	private String musteriNo;
	private int izlemeId;
	private int vizitId;
	private int odemePlaniId;
	private int personelId;
	private int hitamDurum;
	
	
	
	public int getDosyaTipiId() {
		return dosyaTipiId;
	}
	public void setDosyaTipiId(int dosyaTipiId) {
		this.dosyaTipiId = dosyaTipiId;
	}
	public int getTahsilatStatusuId() {
		return tahsilatStatusuId;
	}
	public void setTahsilatStatusuId(int tahsilatStatusuId) {
		this.tahsilatStatusuId = tahsilatStatusuId;
	}
	public int getTahsilatTipiId() {
		return tahsilatTipiId;
	}
	public void setTahsilatTipiId(int tahsilatTipiId) {
		this.tahsilatTipiId = tahsilatTipiId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIcraDosyaId() {
		return icraDosyaId;
	}
	public void setIcraDosyaId(int icraDosyaId) {
		this.icraDosyaId = icraDosyaId;
	}
	public String getIcraDosyaNo() {
		return icraDosyaNo;
	}
	public void setIcraDosyaNo(String icraDosyaNo) {
		this.icraDosyaNo = icraDosyaNo;
	}
	public String getMuvekkilAdi() {
		return muvekkilAdi;
	}
	public void setMuvekkilAdi(String muvekkilAdi) {
		this.muvekkilAdi = muvekkilAdi;
	}
	public String getBorcluAdSoyad() {
		return borcluAdSoyad;
	}
	public void setBorcluAdSoyad(String borcluAdSoyad) {
		this.borcluAdSoyad = borcluAdSoyad;
	}
	public int getBorcluId() {
		return borcluId;
	}
	public void setBorcluId(int borcluId) {
		this.borcluId = borcluId;
	}
	public Date getGelisTarihi() {
		return gelisTarihi;
	}
	public void setGelisTarihi(Date gelisTarihi) {
		this.gelisTarihi = gelisTarihi;
	}
	public String getBorcTipi() {
		return borcTipi;
	}
	public void setBorcTipi(String borcTipi) {
		this.borcTipi = borcTipi;
	}
	public String getDosyaTipi() {
		return dosyaTipi;
	}
	public void setDosyaTipi(String dosyaTipi) {
		this.dosyaTipi = dosyaTipi;
	}
	public int getIcraMudurlukId() {
		return icraMudurlukId;
	}
	public void setIcraMudurlukId(int icraMudurlukId) {
		this.icraMudurlukId = icraMudurlukId;
	}
	public String getIcraMudurlugu() {
		return icraMudurlugu;
	}
	public void setIcraMudurlugu(String icraMudurlugu) {
		this.icraMudurlugu = icraMudurlugu;
	}
	public Date getTahsilatTarihi() {
		return tahsilatTarihi;
	}
	public void setTahsilatTarihi(Date tahsilatTarihi) {
		this.tahsilatTarihi = tahsilatTarihi;
	}
	public String getTahsilatTipi() {
		return tahsilatTipi;
	}
	public void setTahsilatTipi(String tahsilatTipi) {
		this.tahsilatTipi = tahsilatTipi;
	}
	public double getTahsilatMiktari() {
		return tahsilatMiktari;
	}
	public void setTahsilatMiktari(double tahsilatMiktari) {
		this.tahsilatMiktari = tahsilatMiktari;
	}
	public String getTahsilatStatusu() {
		return tahsilatStatusu;
	}
	public void setTahsilatStatusu(String tahsilatStatusu) {
		this.tahsilatStatusu = tahsilatStatusu;
	}
	public int getKasaPersonelID() {
		return kasaPersonelID;
	}
	public void setKasaPersonelID(int kasaPersonelID) {
		this.kasaPersonelID = kasaPersonelID;
	}
	public int getOnaylayanID() {
		return onaylayanID;
	}
	public void setOnaylayanID(int onaylayanID) {
		this.onaylayanID = onaylayanID;
	}
	public int getTahsilatDurum() {
		return tahsilatDurum;
	}
	public void setTahsilatDurum(int tahsilatDurum) {
		this.tahsilatDurum = tahsilatDurum;
	}
	public int getDurum() {
		return durum;
	}
	public void setDurum(int durum) {
		this.durum = durum;
	}
	public String getGelisYeri() {
		return gelisYeri;
	}
	public void setGelisYeri(String gelisYeri) {
		this.gelisYeri = gelisYeri;
	}
	public String getTasilatiYapan() {
		return tasilatiYapan;
	}
	public void setTasilatiYapan(String tasilatiYapan) {
		this.tasilatiYapan = tasilatiYapan;
	}
	public String getKasaIslemiYapan() {
		return kasaIslemiYapan;
	}
	public void setKasaIslemiYapan(String kasaIslemiYapan) {
		this.kasaIslemiYapan = kasaIslemiYapan;
	}
	public String getTahsilatMiktariTL() {
		return tahsilatMiktariTL;
	}
	public void setTahsilatMiktariTL(String tahsilatMiktariTL) {
		this.tahsilatMiktariTL = tahsilatMiktariTL;
	}
	public String getBaknaServisNo() {
		return baknaServisNo;
	}
	public void setBaknaServisNo(String baknaServisNo) {
		this.baknaServisNo = baknaServisNo;
	}
	public String getMusteriNo() {
		return musteriNo;
	}
	public void setMusteriNo(String musteriNo) {
		this.musteriNo = musteriNo;
	}
	public int getIzlemeId() {
		return izlemeId;
	}
	public void setIzlemeId(int izlemeId) {
		this.izlemeId = izlemeId;
	}
	public int getVizitId() {
		return vizitId;
	}
	public void setVizitId(int vizitId) {
		this.vizitId = vizitId;
	}
	public int getOdemePlaniId() {
		return odemePlaniId;
	}
	public void setOdemePlaniId(int odemePlaniId) {
		this.odemePlaniId = odemePlaniId;
	}
	public int getPersonelId() {
		return personelId;
	}
	public void setPersonelId(int personelId) {
		this.personelId = personelId;
	}
	public int getHitamDurum() {
		return hitamDurum;
	}
	public void setHitamDurum(int hitamDurum) {
		this.hitamDurum = hitamDurum;
	}

	
	
	

}
