package semiramis.operasyon.model;

import java.util.Date;


public class IzlemeBilgisi {

	private int id;
	private Date izlemeTarihi;
	private Date izlemeSonucuTarihi;
	private Date odemeSozuTarihi;
	private Double odemeSozuMiktari;
	private String aciklama;
	private int PersonelId;
	private int icraDosyasiId;
	private int IzlemeSonucuId;
	
	private int izlemeStatusuId;
	
	private String izlemeStatusuAdi;
	
	private String izlemeSonucu;
	private boolean vizitDurumu;
	private int cagriAdet;
	
	private int borcluId;
	
	private String borcluAdSoyad;
	
	private String alacakliAdSoyad;
	
	private int icraDosyaId;
	
	private String icraDosyaNo;
	
	

	
	
	
	


	public int getBorcluId() {
		return borcluId;
	}

	public void setBorcluId(int borcluId) {
		this.borcluId = borcluId;
	}

	public String getBorcluAdSoyad() {
		return borcluAdSoyad;
	}

	public void setBorcluAdSoyad(String borcluAdSoyad) {
		this.borcluAdSoyad = borcluAdSoyad;
	}

	public String getAlacakliAdSoyad() {
		return alacakliAdSoyad;
	}

	public void setAlacakliAdSoyad(String alacakliAdSoyad) {
		this.alacakliAdSoyad = alacakliAdSoyad;
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

	public int getIzlemeStatusuId() {
		return izlemeStatusuId;
	}

	public void setIzlemeStatusuId(int izlemeStatusuId) {
		this.izlemeStatusuId = izlemeStatusuId;
	}

	public String getIzlemeStatusuAdi() {
		return izlemeStatusuAdi;
	}

	public void setIzlemeStatusuAdi(String izlemeStatusuAdi) {
		this.izlemeStatusuAdi = izlemeStatusuAdi;
	}

	public String getIzlemeSonucu() {
		return izlemeSonucu;
	}

	public void setIzlemeSonucu(String izlemeSonucu) {
		this.izlemeSonucu = izlemeSonucu;
	}

	public int getIzlemeSonucuId() {
		return IzlemeSonucuId;
	}

	public void setIzlemeSonucuId(int izlemeSonucuId) {
		IzlemeSonucuId = izlemeSonucuId;
	}

	public int getIcraDosyasiId() {
		return icraDosyasiId;
	}

	public void setIcraDosyasiId(int icraDosyasiId) {
		this.icraDosyasiId = icraDosyasiId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getIzlemeTarihi() {
		return izlemeTarihi;
	}

	public void setIzlemeTarihi(Date izlemeTarihi) {
		this.izlemeTarihi = izlemeTarihi;
	}

	public Date getIzlemeSonucuTarihi() {
		return izlemeSonucuTarihi;
	}

	public void setIzlemeSonucuTarihi(Date izlemeSonucuTarihi) {
		this.izlemeSonucuTarihi = izlemeSonucuTarihi;
	}

	public Date getOdemeSozuTarihi() {
		return odemeSozuTarihi;
	}

	public void setOdemeSozuTarihi(Date odemeSozuTarihi) {
		this.odemeSozuTarihi = odemeSozuTarihi;
	}

	public Double getOdemeSozuMiktari() {
		return odemeSozuMiktari;
	}

	public void setOdemeSozuMiktari(Double odemeSozuMiktari) {
		this.odemeSozuMiktari = odemeSozuMiktari;
	}

	public String getAciklama() {
		return aciklama;
	}

	public void setAciklama(String aciklama) {
		this.aciklama = aciklama;
	}

	public int getPersonelId() {
		return PersonelId;
	}

	public void setPersonelId(int personelId) {
		PersonelId = personelId;
	}

	public boolean isVizitDurumu() {
		return vizitDurumu;
	}

	public void setVizitDurumu(boolean vizitDurumu) {
		this.vizitDurumu = vizitDurumu;
	}

	public int getCagriAdet() {
		return cagriAdet;
	}

	public void setCagriAdet(int cagriAdet) {
		this.cagriAdet = cagriAdet;
	}


	
	
	

}
