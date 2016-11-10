package semiramis.operasyon.model;

import java.util.Date;

/**
 * @author Ozgen
 *
 */
public class HacizBilgisi {

	private int id;
	private int borcluId;
	private int personelId;
	private String personeName;
	private int hacizTipiId; // combo
	private String hacizStatusuAdi;
	private int hacizStatusuId;
	private String talimatIcraMd;
	private String dosyaNo;
	private String talimatTarihi;
	private String hacizTarihi;
	private int haczedilenTipiId; // combo
	private double hacizBedeli;
	private int teslimYeriId; // combo
	private String teslimYeri;
	private String sehir;
	private String aciklama;
	private int icra_dosyasi_id;
	private String hacizTipiAdi;
	private String haczedilenTipiAdi;
	private int talimatIcraMdID; // combo
	private String hacizTuru;
	private int hacizTuruId;
	private String borcluAdi;
	private String icraDosyaNo;

	private int hacizSonucuId;
	private String hacizSonucu;
	private String yazdirmaTarih;
	private String hacizBedeliTL;
	private Date hacizTarihiDate;
	private Date talimatTarihiDate;
	private String alacakli;

	public String getAlacakli() {
		return alacakli;
	}

	public void setAlacakli(String alacakli) {
		this.alacakli = alacakli;
	}

	public Date getHacizTarihiDate() {
		return hacizTarihiDate;
	}

	public void setHacizTarihiDate(Date hacizTarihiDate) {
		this.hacizTarihiDate = hacizTarihiDate;
	}

	public Date getTalimatTarihiDate() {
		return talimatTarihiDate;
	}

	public void setTalimatTarihiDate(Date talimatTarihiDate) {
		this.talimatTarihiDate = talimatTarihiDate;
	}

	public String getHacizBedeliTL() {
		return hacizBedeliTL;
	}

	public void setHacizBedeliTL(String hacizBedeliTL) {
		this.hacizBedeliTL = hacizBedeliTL;
	}

	public String getIcraDosyaNo() {
		return icraDosyaNo;
	}

	public void setIcraDosyaNo(String icraDosyaNo) {
		this.icraDosyaNo = icraDosyaNo;
	}

	public String getBorcluAdi() {
		return borcluAdi;
	}

	public void setBorcluAdi(String borcluAdi) {
		this.borcluAdi = borcluAdi;
	}

	public int getTalimatIcraMdID() {
		return talimatIcraMdID;
	}

	public void setTalimatIcraMdID(int talimatIcraMdID) {
		this.talimatIcraMdID = talimatIcraMdID;
	}

	public String getHacizTuru() {
		return hacizTuru;
	}

	public void setHacizTuru(String hacizTuru) {
		this.hacizTuru = hacizTuru;
	}

	public int getHacizTuruId() {
		return hacizTuruId;
	}

	public void setHacizTuruId(int hacizTuruId) {
		this.hacizTuruId = hacizTuruId;
	}

	public int getHacizSonucuId() {
		return hacizSonucuId;
	}

	public void setHacizSonucuId(int hacizSonucuId) {
		this.hacizSonucuId = hacizSonucuId;
	}

	public String getHacizSonucu() {
		return hacizSonucu;
	}

	public void setHacizSonucu(String hacizSonucu) {
		this.hacizSonucu = hacizSonucu;
	}

	public String getHacizStatusuAdi() {
		return hacizStatusuAdi;
	}

	public void setHacizStatusuAdi(String hacizStatusuAdi) {
		this.hacizStatusuAdi = hacizStatusuAdi;
	}

	public String getPersoneName() {
		return personeName;
	}

	public void setPersoneName(String personeName) {
		this.personeName = personeName;
	}

	public String getTeslimYeri() {
		return teslimYeri;
	}

	public void setTeslimYeri(String teslimYeri) {
		this.teslimYeri = teslimYeri;
	}

	public String getHacizTipiAdi() {
		return hacizTipiAdi;
	}

	public void setHacizTipiAdi(String hacizTipiAdi) {
		this.hacizTipiAdi = hacizTipiAdi;
	}

	public String getHaczedilenTipiAdi() {
		return haczedilenTipiAdi;
	}

	public void setHaczedilenTipiAdi(String haczedilenTipiAdi) {
		this.haczedilenTipiAdi = haczedilenTipiAdi;
	}

	public int getIcra_dosyasi_id() {
		return icra_dosyasi_id;
	}

	public void setIcra_dosyasi_id(int icra_dosyasi_id) {
		this.icra_dosyasi_id = icra_dosyasi_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPersonelId() {
		return personelId;
	}

	public void setPersonelId(int personelId) {
		this.personelId = personelId;
	}

	public int getBorcluId() {
		return borcluId;
	}

	public void setBorcluId(int borcluId) {
		this.borcluId = borcluId;
	}

	public String getTalimatIcraMd() {
		return talimatIcraMd;
	}

	public void setTalimatIcraMd(String talimatIcraMd) {
		this.talimatIcraMd = talimatIcraMd;
	}

	public String getDosyaNo() {
		return dosyaNo;
	}

	public void setDosyaNo(String dosyaNo) {
		this.dosyaNo = dosyaNo;
	}

	public String getTalimatTarihi() {
		return talimatTarihi;
	}

	public void setTalimatTarihi(String talimatTarihi) {
		this.talimatTarihi = talimatTarihi;
	}

	public String getHacizTarihi() {
		return hacizTarihi;
	}

	public void setHacizTarihi(String hacizTarihi) {
		this.hacizTarihi = hacizTarihi;
	}

	public String getYazdirmaTarih() {
		return yazdirmaTarih;
	}

	public void setYazdirmaTarih(String yazdirmaTarih) {
		this.yazdirmaTarih = yazdirmaTarih;
	}

	public double getHacizBedeli() {
		return hacizBedeli;
	}

	public void setHacizBedeli(double hacizBedeli) {
		this.hacizBedeli = hacizBedeli;
	}

	public String getSehir() {
		return sehir;
	}

	public void setSehir(String sehir) {
		this.sehir = sehir;
	}

	public String getAciklama() {
		return aciklama;
	}

	public void setAciklama(String aciklama) {
		this.aciklama = aciklama;
	}

	public int getHacizTipiId() {
		return hacizTipiId;
	}

	public void setHacizTipiId(int hacizTipiId) {
		this.hacizTipiId = hacizTipiId;
	}

	public int getHaczedilenTipiId() {
		return haczedilenTipiId;
	}

	public void setHaczedilenTipiId(int haczedilenTipiId) {
		this.haczedilenTipiId = haczedilenTipiId;
	}

	public int getTeslimYeriId() {
		return teslimYeriId;
	}

	public void setTeslimYeriId(int teslimYeriId) {
		this.teslimYeriId = teslimYeriId;
	}

	public int getHacizStatusuId() {
		return hacizStatusuId;
	}

	public void setHacizStatusuId(int hacizStatusuId) {
		this.hacizStatusuId = hacizStatusuId;
	}

}
