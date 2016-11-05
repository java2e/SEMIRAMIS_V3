package semiramis.operasyon.model;

public class HaczeEsasMalBilgisiView
{
	
	private int id;
	private int borcluId;
	private int icraDosyaId;
	
	private int maltipiId;
	private String malTipi;
	private float borcMiktari;
	private int durum;
	
	private String borcluAdSoyad;
	private String icraDosyaNo;
	
	
	
	
	
	
	
	
	public String getBorcluAdSoyad() {
		return borcluAdSoyad;
	}
	public void setBorcluAdSoyad(String borcluAdSoyad) {
		this.borcluAdSoyad = borcluAdSoyad;
	}
	public String getIcraDosyaNo() {
		return icraDosyaNo;
	}
	public void setIcraDosyaNo(String icraDosyaNo) {
		this.icraDosyaNo = icraDosyaNo;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getBorcluId() {
		return borcluId;
	}
	public void setBorcluId(int borcluId) {
		this.borcluId = borcluId;
	}
	public int getIcraDosyaId() {
		return icraDosyaId;
	}
	public void setIcraDosyaId(int icraDosyaId) {
		this.icraDosyaId = icraDosyaId;
	}
	public int getMaltipiId() {
		return maltipiId;
	}
	public void setMaltipiId(int maltipiId) {
		this.maltipiId = maltipiId;
	}
	public String getMalTipi() {
		return malTipi;
	}
	public void setMalTipi(String malTipi) {
		this.malTipi = malTipi;
	}
	public float getBorcMiktari() {
		return borcMiktari;
	}
	public void setBorcMiktari(float borcMiktari) {
		this.borcMiktari = borcMiktari;
	}
	public int getDurum() {
		return durum;
	}
	public void setDurum(int durum) {
		this.durum = durum;
	}
	
	

}
