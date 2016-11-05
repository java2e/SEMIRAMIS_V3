package semiramis.kasa.model;

import java.util.Date;

public class Reddiyat 
{
	
	private int id;
	private int tahsilatID;
	private int kasaPersonelID;
	private int onaylayanID;
	private double sasaReddiyatTutari;
	private Integer sasaDurum;//
	private double devletReddiyatTutari;
	private Integer devletDurum;
	private double muvekkilReddiyatTutari;
	private Integer muvekkilDurum;// 0: kaydedildi fakat müvekkile aktarılmadı,
									// 1:
									// müvekkile ödendi,
	private String muvekkilAdi;

	private int borcluId;
	private String borcluAdi;

	private int icraDosyaId;
	private String icraDosyaNo;

	private double aktifTutar;

	private String reddiyatTuru;

	private Date reddiyatTarihi;

	private String sasaReddiyatTutariTL;

	private String muvekkilReddiyatTutariTL;

	private String aktifTutarTL;

	private String devletReddiyatTutariTL;

	private Date tarih;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTahsilatID() {
		return tahsilatID;
	}

	public void setTahsilatID(int tahsilatID) {
		this.tahsilatID = tahsilatID;
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

	public double getSasaReddiyatTutari() {
		return sasaReddiyatTutari;
	}

	public void setSasaReddiyatTutari(double sasaReddiyatTutari) {
		this.sasaReddiyatTutari = sasaReddiyatTutari;
	}

	public Integer getSasaDurum() {
		return sasaDurum;
	}

	public void setSasaDurum(Integer sasaDurum) {
		this.sasaDurum = sasaDurum;
	}

	public double getDevletReddiyatTutari() {
		return devletReddiyatTutari;
	}

	public void setDevletReddiyatTutari(double devletReddiyatTutari) {
		this.devletReddiyatTutari = devletReddiyatTutari;
	}

	public Integer getDevletDurum() {
		return devletDurum;
	}

	public void setDevletDurum(Integer devletDurum) {
		this.devletDurum = devletDurum;
	}

	public double getMuvekkilReddiyatTutari() {
		return muvekkilReddiyatTutari;
	}

	public void setMuvekkilReddiyatTutari(double muvekkilReddiyatTutari) {
		this.muvekkilReddiyatTutari = muvekkilReddiyatTutari;
	}

	public Integer getMuvekkilDurum() {
		return muvekkilDurum;
	}

	public void setMuvekkilDurum(Integer muvekkilDurum) {
		this.muvekkilDurum = muvekkilDurum;
	}

	public String getMuvekkilAdi() {
		return muvekkilAdi;
	}

	public void setMuvekkilAdi(String muvekkilAdi) {
		this.muvekkilAdi = muvekkilAdi;
	}

	public int getBorcluId() {
		return borcluId;
	}

	public void setBorcluId(int borcluId) {
		this.borcluId = borcluId;
	}

	public String getBorcluAdi() {
		return borcluAdi;
	}

	public void setBorcluAdi(String borcluAdi) {
		this.borcluAdi = borcluAdi;
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

	public double getAktifTutar() {
		return aktifTutar;
	}

	public void setAktifTutar(double aktifTutar) {
		this.aktifTutar = aktifTutar;
	}

	public String getReddiyatTuru() {
		return reddiyatTuru;
	}

	public void setReddiyatTuru(String reddiyatTuru) {
		this.reddiyatTuru = reddiyatTuru;
	}

	public Date getReddiyatTarihi() {
		return reddiyatTarihi;
	}

	public void setReddiyatTarihi(Date reddiyatTarihi) {
		this.reddiyatTarihi = reddiyatTarihi;
	}

	public String getSasaReddiyatTutariTL() {
		return sasaReddiyatTutariTL;
	}

	public void setSasaReddiyatTutariTL(String sasaReddiyatTutariTL) {
		this.sasaReddiyatTutariTL = sasaReddiyatTutariTL;
	}

	public String getMuvekkilReddiyatTutariTL() {
		return muvekkilReddiyatTutariTL;
	}

	public void setMuvekkilReddiyatTutariTL(String muvekkilReddiyatTutariTL) {
		this.muvekkilReddiyatTutariTL = muvekkilReddiyatTutariTL;
	}

	public String getAktifTutarTL() {
		return aktifTutarTL;
	}

	public void setAktifTutarTL(String aktifTutarTL) {
		this.aktifTutarTL = aktifTutarTL;
	}

	public String getDevletReddiyatTutariTL() {
		return devletReddiyatTutariTL;
	}

	public void setDevletReddiyatTutariTL(String devletReddiyatTutariTL) {
		this.devletReddiyatTutariTL = devletReddiyatTutariTL;
	}

	public Date getTarih() {
		return tarih;
	}

	public void setTarih(Date tarih) {
		this.tarih = tarih;
	}
	
	
	

}
