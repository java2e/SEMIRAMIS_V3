package semiramis.operasyon.model;

public class Islem {
	private int id;
	private int icraDosyaId;
	private int islem_id;
	private String barkod;
	private String barcodEncoded;
	private String islemAdi;
	private int tebligatId;
	private String sonuc;
	private String statusu;

	public int getTebligatId() {
		return tebligatId;
	}

	public void setTebligatId(int tebligatId) {
		this.tebligatId = tebligatId;
	}

	public String getSonuc() {
		return sonuc;
	}

	public void setSonuc(String sonuc) {
		this.sonuc = sonuc;
	}

	public String getStatusu() {
		return statusu;
	}

	public void setStatusu(String statusu) {
		this.statusu = statusu;
	}

	public String getBarcodEncoded() {
		return barcodEncoded;
	}

	public void setBarcodEncoded(String barcodEncoded) {
		this.barcodEncoded = barcodEncoded;
	}

	public String getIslemAdi() {
		return islemAdi;
	}

	public void setIslemAdi(String islemAdi) {
		this.islemAdi = islemAdi;
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

	public int getIslem_id() {
		return islem_id;
	}

	public void setIslem_id(int islem_id) {
		this.islem_id = islem_id;
	}

	public String getBarkod() {
		return barkod;
	}

	public void setBarkod(String barkod) {
		this.barkod = barkod;
	}

}
