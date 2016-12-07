package semiramis.operasyon.model;

import java.util.Date;

import semiramis.tracking.classes.Checkpoint;
import semiramis.tracking.classes.Tracking;

public class Tebligat {

	private int id;

	private int icraDosyaId;

	private String icraDosyaNo;

	private int borcluId;

	private String borcluAdSoyad;

	private int tebligatTuruId;

	private String tebligatTuruAdi;

	private int tebligatStatusuId;

	private String tebligatStatusuAdi;

	private int tebligatSonucuId;

	private String tebligatSonucuAdi;

	private String guncellemeTarihi;

	private int kullaniciId;

	private String kullaniciAdSoyad;

	private String barkod;

	private String icraMd;

	private Tracking tracking;

	private Checkpoint lastCheckPoint;
	
	private Date tebligatTarihi;
	
	private String tebligatTarihiTxt;

	public Checkpoint getLastCheckPoint() {
		return lastCheckPoint;
	}

	public void setLastCheckPoint(Checkpoint lastCheckPoint) {
		this.lastCheckPoint = lastCheckPoint;
	}

	public Tracking getTracking() {
		return tracking;
	}

	public void setTracking(Tracking tracking) {
		this.tracking = tracking;
	}

	public String getBarkod() {
		return barkod;
	}

	public void setBarkod(String barkod) {
		this.barkod = barkod;
	}

	public String getIcraMd() {
		return icraMd;
	}

	public void setIcraMd(String icraMd) {
		this.icraMd = icraMd;
	}

	public int getBorcluId() {
		return borcluId;
	}

	public void setBorcluId(int borcluId) {
		this.borcluId = borcluId;
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

	public String getBorcluAdSoyad() {
		return borcluAdSoyad;
	}

	public void setBorcluAdSoyad(String borcluAdSoyad) {
		this.borcluAdSoyad = borcluAdSoyad;
	}

	public int getTebligatTuruId() {
		return tebligatTuruId;
	}

	public void setTebligatTuruId(int tebligatTuruId) {
		this.tebligatTuruId = tebligatTuruId;
	}

	public String getTebligatTuruAdi() {
		return tebligatTuruAdi;
	}

	public void setTebligatTuruAdi(String tebligatTuruAdi) {
		this.tebligatTuruAdi = tebligatTuruAdi;
	}

	public int getTebligatStatusuId() {
		return tebligatStatusuId;
	}

	public void setTebligatStatusuId(int tebligatStatusuId) {
		this.tebligatStatusuId = tebligatStatusuId;
	}

	public String getTebligatStatusuAdi() {
		return tebligatStatusuAdi;
	}

	public void setTebligatStatusuAdi(String tebligatStatusuAdi) {
		this.tebligatStatusuAdi = tebligatStatusuAdi;
	}

	public int getTebligatSonucuId() {
		return tebligatSonucuId;
	}

	public void setTebligatSonucuId(int tebligatSonucuId) {
		this.tebligatSonucuId = tebligatSonucuId;
	}

	public String getTebligatSonucuAdi() {
		return tebligatSonucuAdi;
	}

	public void setTebligatSonucuAdi(String tebligatSonucuAdi) {
		this.tebligatSonucuAdi = tebligatSonucuAdi;
	}

	public String getGuncellemeTarihi() {
		return guncellemeTarihi;
	}

	public void setGuncellemeTarihi(String guncellemeTarihi) {
		this.guncellemeTarihi = guncellemeTarihi;
	}

	public int getKullaniciId() {
		return kullaniciId;
	}

	public void setKullaniciId(int kullaniciId) {
		this.kullaniciId = kullaniciId;
	}

	public String getKullaniciAdSoyad() {
		return kullaniciAdSoyad;
	}

	public void setKullaniciAdSoyad(String kullaniciAdSoyad) {
		this.kullaniciAdSoyad = kullaniciAdSoyad;
	}

	public Date getTebligatTarihi() {
		return tebligatTarihi;
	}

	public void setTebligatTarihi(Date tebligatTarihi) {
		this.tebligatTarihi = tebligatTarihi;
	}

	public String getTebligatTarihiTxt() {
		return tebligatTarihiTxt;
	}

	public void setTebligatTarihiTxt(String tebligatTarihiTxt) {
		this.tebligatTarihiTxt = tebligatTarihiTxt;
	}
	
	

}
