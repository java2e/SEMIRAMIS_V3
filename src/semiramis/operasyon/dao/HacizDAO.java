package semiramis.operasyon.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import pelops.controller.AktifBean;
import pelops.db.DBConnection;
import pelops.model.Tipi;
import semiramis.operasyon.model.ComboItem;
import semiramis.operasyon.model.HacizBilgisi;

public class HacizDAO extends DBConnection {

	String SQL;
	PreparedStatement pstmt;
	Statement stmt;
	ResultSet rs;

	public List<ComboItem> getHacizStatusu(int hacizTurId) {

		List<ComboItem> liste = null;

		try {

			String sql = "SELECT * from tbl_haciz_statusu where haciz_turu_id=" + hacizTurId;

			newConnectDB();

			Statement stmt = conn.createStatement();

			ResultSet set = stmt.executeQuery(sql);

			liste = new ArrayList<ComboItem>();

			while (set.next()) {
				ComboItem item = new ComboItem();

				item.setAdi(set.getString("adi"));
				item.setId(set.getInt("id"));
				liste.add(item);
			}

		} catch (Exception e) {

			System.out.println("HATA hacizDAO getHacizStatusu :" + e.getMessage());
			// TODO: handle exception
		}

		return liste;
	}

	public boolean kaydet(HacizBilgisi haciz) {
		SimpleDateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
		java.sql.Date dateTalimat = null;
		java.sql.Date dateHaciz = null;
		try {
			dateTalimat = convertFromJAVADateToSQLDate(haciz.getTalimatTarihiDate());
			dateHaciz = convertFromJAVADateToSQLDate(haciz.getHacizTarihiDate());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("HacizDao.kaydet parse date: " + e.getMessage());
		}

		boolean kaydedildi = false;

		String SQL = "INSERT INTO tbl_haciz_bilgisi(icra_dosyasi_id, haciz_tipi_id, talimat_icra_mudurlugu, talimat_dosya_no,"
				+ "talimat_tarihi, haciz_tarihi, haciz_bedeli, teslim_yeri_id, sehir,"
				+ "aciklama, haczedilen_tipi_id, personel_adi_id, borclu_id, haciz_statusu_id,haciz_tur_id,haciz_sonucu_id, talimat_icra_md_id)"
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?, ?);";

		try {

			newConnectDB();

			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, AktifBean.getIcraDosyaID());
			pstmt.setInt(2, haciz.getHacizTipiId());
			pstmt.setString(3, haciz.getTalimatIcraMd());
			pstmt.setString(4, haciz.getDosyaNo());
			pstmt.setDate(5, dateTalimat);
			pstmt.setDate(6, dateHaciz);
			pstmt.setDouble(7, haciz.getHacizBedeli());
			pstmt.setInt(8, haciz.getTeslimYeriId());
			pstmt.setString(9, haciz.getSehir());
			pstmt.setString(10, haciz.getAciklama());
			pstmt.setInt(11, haciz.getHacizTipiId());
			pstmt.setInt(12, haciz.getPersonelId());
			pstmt.setInt(13, AktifBean.getBorcluId());
			pstmt.setInt(14, haciz.getHacizStatusuId());
			pstmt.setInt(15, haciz.getHacizTuruId());
			pstmt.setInt(16, haciz.getHacizSonucuId());
			pstmt.setInt(17, haciz.getTalimatIcraMdID());

			int result = pstmt.executeUpdate();
			if (result == 1) {

				return kaydedildi = true;
			}

			disconnectDB();

		} catch (Exception ex) {

			System.out.println("HacizDao.kaydet " + ex.getMessage());
		}

		return kaydedildi;

	}

	public ArrayList<HacizBilgisi> getAllListFromIcraDosyaID(int id) {

		ArrayList<HacizBilgisi> list = new ArrayList<HacizBilgisi>();

		SQL = "SELECT h.id, h.borclu_id, h.haciz_tipi_id, "
				+ " h.talimat_icra_mudurlugu, h.talimat_dosya_no, h.talimat_tarihi, "
				+ "h.haciz_tarihi, h.haciz_bedeli, h.sehir, h.aciklama, ht.adi as haciz_turu_adi, "
				+ " h.icra_dosyasi_id, h.haczedilen_tipi_id, h.personel_adi_id, h.haciz_tur_id,"
				+ " u.ad_soyad as uadi, h.teslim_yeri_id, ty.adi as tyadi,hs.adi as haciz_statusu_adi, "
				+ "hs.id as haciz_statusu_id , im.adi as icra_md, b.ad_soyad , ab.muvekkil_adi as alacakli, "
				+ " icra.icra_dosyasi_no FROM tbl_haciz_bilgisi h "
				+ " inner join tbl_kullanici u on h.personel_adi_id=u.id "
				+ " inner join tbl_haciz_statusu hs on h.haciz_statusu_id=hs.id"
				+ " inner join tbl_teslim_yeri ty on h.teslim_yeri_id=ty.id"
				+ " inner join  tbl_haciz_turu  ht on h.haciz_tur_id = ht.id"
				+ " inner join tbl_icra_mudurlugu im on im.adi = h.talimat_icra_mudurlugu"
				+ " inner join tbl_borclu b on b.id=h.borclu_id "
				+ " inner join tbl_baglanti bag on bag.icra_dosyasi_id = h.icra_dosyasi_id "
				+ " inner join tbl_alacakli_bilgisi ab on ab.id = bag.alacakli_id "
				+ " inner join tbl_icra_dosyasi icra on icra.id= h.icra_dosyasi_id " + " where h.icra_dosyasi_id=" + id
				+ ";";

		newConnectDB();
		try {

			stmt = conn.createStatement();
			rs = stmt.executeQuery(SQL);
			while (rs.next()) {

				HacizBilgisi hacizBilgisi = new HacizBilgisi();

				hacizBilgisi.setId(rs.getInt("id"));
				hacizBilgisi.setBorcluId(rs.getInt("borclu_id"));
				hacizBilgisi.setHacizTipiId(rs.getInt("haciz_tipi_id"));
				hacizBilgisi.setTalimatIcraMd(rs.getString("talimat_icra_mudurlugu"));
				hacizBilgisi.setDosyaNo(rs.getString("talimat_dosya_no"));
				hacizBilgisi.setTalimatTarihi(rs.getString("talimat_tarihi"));
				hacizBilgisi.setHacizTarihi(rs.getString("haciz_tarihi"));
				hacizBilgisi.setTalimatTarihiDate(rs.getDate("talimat_tarihi"));
				hacizBilgisi.setHacizTarihiDate(rs.getDate("haciz_tarihi"));
				hacizBilgisi.setHacizBedeli(rs.getDouble("haciz_bedeli"));
				hacizBilgisi.setSehir(rs.getString("sehir"));
				hacizBilgisi.setAciklama(rs.getString("aciklama"));
				hacizBilgisi.setIcra_dosyasi_id(rs.getInt("icra_dosyasi_id"));
				hacizBilgisi.setHaczedilenTipiId(rs.getInt("haczedilen_tipi_id"));
				hacizBilgisi.setPersonelId(rs.getInt("personel_adi_id"));
				hacizBilgisi.setPersoneName(rs.getString("uadi"));
				hacizBilgisi.setTeslimYeriId(rs.getInt("teslim_yeri_id"));
				hacizBilgisi.setTeslimYeri(rs.getString("tyadi"));
				hacizBilgisi.setIcra_dosyasi_id(rs.getInt("icra_dosyasi_id"));
				hacizBilgisi.setHacizStatusuId(rs.getInt("haciz_statusu_id"));
				hacizBilgisi.setHacizStatusuAdi(rs.getString("haciz_statusu_adi"));
				hacizBilgisi.setHacizTuruId(rs.getInt("haciz_tur_id"));
				hacizBilgisi.setHacizTuru(rs.getString("haciz_turu_adi"));
				hacizBilgisi.setTalimatIcraMd(rs.getString("icra_md"));
				hacizBilgisi.setYazdirmaTarih(new SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date()));
				hacizBilgisi.setIcraDosyaNo(AktifBean.getIcraDosyaNo());
				hacizBilgisi.setHacizBedeliTL(String.valueOf(rs.getDouble("haciz_bedeli") + " TL"));
				hacizBilgisi.setBorcluAdi(rs.getString("ad_soyad"));
				hacizBilgisi.setAlacakli(rs.getString("alacakli"));
				hacizBilgisi.setIcraDosyaNo(rs.getString("icra_dosyasi_no"));

				list.add(hacizBilgisi);
			}

			disconnectDB();

			for (int i = 0; i < list.size(); i++) {
				String name = null;
				name = getHaczedilenTipi(list.get(i).getHaczedilenTipiId());
				list.get(i).setHaczedilenTipiAdi(name);
			}
		} catch (Exception e) {
			System.out.println("HacizDao.getAllListFromIcraDosyaID " + e.getMessage());
		}

		return list;

	}

	public ArrayList<HacizBilgisi> getAllHacizList() {

		ArrayList<HacizBilgisi> list = new ArrayList<HacizBilgisi>();

		SQL = "SELECT h.id, h.borclu_id, h.haciz_tipi_id, "
				+ " h.talimat_icra_mudurlugu, h.talimat_dosya_no, h.talimat_tarihi, "
				+ "h.haciz_tarihi, h.haciz_bedeli, h.sehir, h.aciklama, ht.adi as haciz_turu_adi, "
				+ " h.icra_dosyasi_id, h.haczedilen_tipi_id, h.personel_adi_id, h.haciz_tur_id,"
				+ " u.ad_soyad as uadi, h.teslim_yeri_id, ty.adi as tyadi,hs.adi as haciz_statusu_adi, "
				+ "hs.id as haciz_statusu_id , im.adi as icra_md, b.ad_soyad , ab.muvekkil_adi as alacakli, "
				+ " icra.icra_dosyasi_no FROM tbl_haciz_bilgisi h "
				+ " inner join tbl_kullanici u on h.personel_adi_id=u.id "
				+ " inner join tbl_haciz_statusu hs on h.haciz_statusu_id=hs.id"
				+ " inner join tbl_teslim_yeri ty on h.teslim_yeri_id=ty.id"
				+ " inner join  tbl_haciz_turu  ht on h.haciz_tur_id = ht.id"
				+ " inner join tbl_icra_mudurlugu im on im.adi = h.talimat_icra_mudurlugu"
				+ " inner join tbl_borclu b on b.id=h.borclu_id "
				+ " inner join tbl_baglanti bag on bag.icra_dosyasi_id = h.icra_dosyasi_id "
				+ " inner join tbl_alacakli_bilgisi ab on ab.id = bag.alacakli_id "
				+ " inner join tbl_icra_dosyasi icra on icra.id= h.icra_dosyasi_id " + ";";

		newConnectDB();
		try {

			stmt = conn.createStatement();
			rs = stmt.executeQuery(SQL);
			while (rs.next()) {

				HacizBilgisi hacizBilgisi = new HacizBilgisi();

				hacizBilgisi.setId(rs.getInt("id"));
				hacizBilgisi.setBorcluId(rs.getInt("borclu_id"));
				hacizBilgisi.setHacizTipiId(rs.getInt("haciz_tipi_id"));
				hacizBilgisi.setTalimatIcraMd(rs.getString("talimat_icra_mudurlugu"));
				hacizBilgisi.setDosyaNo(rs.getString("talimat_dosya_no"));
				hacizBilgisi.setTalimatTarihi(rs.getString("talimat_tarihi"));
				hacizBilgisi.setHacizTarihi(rs.getString("haciz_tarihi"));
				hacizBilgisi.setTalimatTarihiDate(rs.getDate("talimat_tarihi"));
				hacizBilgisi.setHacizTarihiDate(rs.getDate("haciz_tarihi"));
				hacizBilgisi.setHacizBedeli(rs.getDouble("haciz_bedeli"));
				hacizBilgisi.setSehir(rs.getString("sehir"));
				hacizBilgisi.setAciklama(rs.getString("aciklama"));
				hacizBilgisi.setIcra_dosyasi_id(rs.getInt("icra_dosyasi_id"));
				hacizBilgisi.setHaczedilenTipiId(rs.getInt("haczedilen_tipi_id"));
				hacizBilgisi.setPersonelId(rs.getInt("personel_adi_id"));
				hacizBilgisi.setPersoneName(rs.getString("uadi"));
				hacizBilgisi.setTeslimYeriId(rs.getInt("teslim_yeri_id"));
				hacizBilgisi.setTeslimYeri(rs.getString("tyadi"));
				hacizBilgisi.setIcra_dosyasi_id(rs.getInt("icra_dosyasi_id"));
				hacizBilgisi.setHacizStatusuId(rs.getInt("haciz_statusu_id"));
				hacizBilgisi.setHacizStatusuAdi(rs.getString("haciz_statusu_adi"));
				hacizBilgisi.setHacizTuruId(rs.getInt("haciz_tur_id"));
				hacizBilgisi.setHacizTuru(rs.getString("haciz_turu_adi"));
				hacizBilgisi.setTalimatIcraMd(rs.getString("icra_md"));
				hacizBilgisi.setYazdirmaTarih(new SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date()));
				hacizBilgisi.setIcraDosyaNo(AktifBean.getIcraDosyaNo());
				hacizBilgisi.setHacizBedeliTL(String.valueOf(rs.getDouble("haciz_bedeli") + " TL"));
				hacizBilgisi.setBorcluAdi(rs.getString("ad_soyad"));
				hacizBilgisi.setAlacakli(rs.getString("alacakli"));
				hacizBilgisi.setIcraDosyaNo(rs.getString("icra_dosyasi_no"));

				list.add(hacizBilgisi);
			}

			disconnectDB();

			for (int i = 0; i < list.size(); i++) {
				String name = null;
				name = getHaczedilenTipi(list.get(i).getHaczedilenTipiId());
				list.get(i).setHaczedilenTipiAdi(name);
			}
		} catch (Exception e) {
			System.out.println("HacizDao.getAllHacizList " + e.getMessage());
		}

		return list;

	}

	public boolean guncelle(HacizBilgisi haciz) {
		boolean duzenlendi = false;
		SQL = "UPDATE tbl_haciz_bilgisi SET " + " borclu_id=?, haciz_tipi_id=?, talimat_icra_mudurlugu=?, "
				+ "talimat_dosya_no=?, talimat_tarihi=?, haciz_tarihi=?, haciz_bedeli=?, "
				+ " sehir=?, aciklama=?, icra_dosyasi_id=?, haczedilen_tipi_id=?, "
				+ " personel_adi_id=?, teslim_yeri_id=? , haciz_tur_id=?, haciz_sonucu_id=?, "
				+ "talimat_icra_md_id=? , haciz_statusu_id=?" + " WHERE id=" + haciz.getId() + ";";

		newConnectDB();

		try {

			pstmt = conn.prepareStatement(SQL);

			pstmt.setInt(1, AktifBean.getBorcluId());
			pstmt.setInt(2, haciz.getHacizTipiId());
			pstmt.setString(3, haciz.getTalimatIcraMd());
			pstmt.setString(4, haciz.getDosyaNo());
			java.sql.Date date = convertFromJAVADateToSQLDate(new java.util.Date(haciz.getTalimatTarihi()));
			pstmt.setDate(5, date);
			date = convertFromJAVADateToSQLDate(new java.util.Date(haciz.getHacizTarihi()));
			pstmt.setDate(6, date);
			pstmt.setDouble(7, haciz.getHacizBedeli());
			pstmt.setString(8, haciz.getSehir());
			pstmt.setString(9, haciz.getAciklama());
			pstmt.setInt(10, AktifBean.getIcraDosyaID());
			pstmt.setInt(11, haciz.getHaczedilenTipiId());
			pstmt.setInt(12, haciz.getPersonelId());
			pstmt.setInt(13, haciz.getTeslimYeriId());
			pstmt.setInt(14, haciz.getHacizTuruId());
			pstmt.setInt(15, haciz.getHacizSonucuId());
			pstmt.setInt(16, haciz.getTalimatIcraMdID());
			pstmt.setInt(17, haciz.getHacizStatusuId());
			int sonuc = pstmt.executeUpdate();
			disconnectDB();
			if (sonuc == 1) {
				duzenlendi = true;
			}
		} catch (Exception e) {
			System.out.println("HacizDao.guncelle " + e.getMessage());
		} finally {
			if (this.conn != null) {
				disconnectDB();
			}
		}
		return duzenlendi;
	}

	public String getHaczedilenTipi(int id) {
		String sql = "select * from tbl_haczedilen_tipi where id = " + id + ";";
		newConnectDB();
		String hacz = null;
		try {

			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery(sql);

			while (rs.next()) {
				hacz = rs.getString("adi");
			}
			disconnectDB();
		} catch (Exception e) {
			System.out.println("HacizDao.getHaczedilenTipi " + e.getMessage());
		}
		return hacz;
	}

	public boolean Sil(int id) {

		String SQL = "DELETE FROM tbl_haciz_bilgisi WHERE id = ?;";

		boolean silindi = false;

		try {
			newConnectDB();
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, id);

			// execute delete SQL stetement
			int result = pstmt.executeUpdate();

			disconnectDB();

			if (result == 1) {

				silindi = true;
			}

		} catch (Exception e) {
			System.out.println("HacizDao.Sil" + e.getMessage());
		} finally {
			if (this.conn != null) {
				disconnectDB();
			}
		}

		return silindi;
	}

	public int getHesapID(int id) {
		SQL = "SELECT id, icra_dosyasi_id, alacakli_id, borclu_id, hesap_id, ekleme_tarihi, "
				+ " guncelleme_tarihi FROM tbl_baglanti where icra_dosyasi_id  = " + id + ";";
		newConnectDB();
		int hesapid = 0;
		try {

			stmt = conn.createStatement();
			rs = stmt.executeQuery(SQL);
			while (rs.next()) {
				hesapid = rs.getInt("hesap_id");
			}
			disconnectDB();
		} catch (Exception e) {
			System.out.println("HacizDao.getHesapID " + e.getMessage());
		} finally {
			if (this.conn != null) {
				disconnectDB();
			}
		}
		return hesapid;

	}

	public void hesapDuzenle(int hesapID, int ekleCikar, double tutar) {
		SQL = "select * from tbl_hesap where id= " + hesapID + ";";
		newConnectDB();

		try {

			stmt = conn.createStatement();
			double tahsilatTutari = 0;
			rs = stmt.executeQuery(SQL);
			while (rs.next()) {
				tahsilatTutari = rs.getDouble("tahsilat_tutari");
			}
			disconnectDB();
			switch (ekleCikar) {
			case 1:
				tahsilatTutari = tahsilatTutari + tutar;
				break;
			case 2:
				tahsilatTutari = tutar;
				break;
			case 3:
				tahsilatTutari = tahsilatTutari - tutar;
				break;

			default:
				break;
			}

			String update = "UPDATE tbl_hesap SET  tahsilat_tutari=?  WHERE id = " + hesapID + ";";
			newConnectDB();
			pstmt = conn.prepareStatement(update);
			pstmt.setDouble(1, tahsilatTutari);
			pstmt.executeUpdate();
			disconnectDB();

		} catch (Exception e) {
			System.out.println("HacizDao.hesapDuzenle " + e.getMessage());
		} finally {
			if (this.conn != null) {
				disconnectDB();
			}
		}

	}

	public ArrayList<String> getHaczeEsasMalBilgisiFromBorcluID(int id) {
		ArrayList<String> malList = new ArrayList<String>();
		SQL = "SELECT mal.adi as mal_tipi FROM tbl_hacze_esas_mal_bilgisi hcz "
				+ " INNER JOIN tbl_mal_tipi mal on hcz.mal_tipi_id=mal.id" + " where hcz.borclu_id =" + id + ";";

		newConnectDB();
		try {

			stmt = conn.createStatement();
			rs = stmt.executeQuery(SQL);
			while (rs.next()) {
				String mal = null;
				mal = rs.getString("mal_tipi");
				malList.add(mal);
			}
			disconnectDB();
		} catch (Exception e) {
			System.out.println("HacizDao.getHaczeEsasMalBilgisiFromBorcluID " + e.getMessage());
		} finally {
			if (this.conn != null) {
				disconnectDB();
			}
		}
		return malList;
	}

	public ArrayList<Tipi> getHacizTipiList(ArrayList<String> malTipiList) {

		ArrayList<Tipi> hacizTipiList = new ArrayList<Tipi>();
		SQL = "SELECT id, adi FROM tbl_haciz_tipi;";
		newConnectDB();
		ArrayList<Tipi> borcluMalTipiList = new ArrayList<Tipi>();
		try {

			stmt = conn.createStatement();
			rs = stmt.executeQuery(SQL);
			while (rs.next()) {
				Tipi tipi = new Tipi();
				tipi.setId(rs.getInt("id"));
				tipi.setAdi(rs.getString("adi"));
				hacizTipiList.add(tipi);
			}
			disconnectDB();

			if (malTipiList.size() > 0) {
				for (int j = 0; j < malTipiList.size(); j++) {
					for (int i = 0; i < hacizTipiList.size(); i++) {

						if (malTipiList.get(j).equals(hacizTipiList.get(i).getAdi())) {

							borcluMalTipiList.add(hacizTipiList.get(i));

						}
					}
				}

			}
		} catch (Exception e) {
			System.out.println("HacizDao.getHacizTipiList " + e.getMessage());
		} finally {
			if (this.conn != null) {
				disconnectDB();
			}
		}
		return borcluMalTipiList;
	}

	public List<ComboItem> getHacizTuru() {
		List<ComboItem> liste = null;

		try {

			String sql = "Select * from tbl_haciz_turu ";

			newConnectDB();

			Statement stmt = conn.createStatement();

			ResultSet set = stmt.executeQuery(sql);

			liste = new ArrayList<>();

			while (set.next()) {
				ComboItem item = new ComboItem();

				item.setId(set.getInt("id"));
				item.setAdi(set.getString("adi"));
				liste.add(item);
			}

			disconnectDB();

		} catch (Exception e) {

			System.out.println("Hata tanimlarDAO tbl_haciz_turu :" + e.getMessage());
			// TODO: handle exception
		}

		return liste;

	}

	public List<ComboItem> getHacizSonucu() {
		List<ComboItem> liste = null;

		try {

			String sql = "Select * from tbl_haciz_sonucu ";

			newConnectDB();

			Statement stmt = conn.createStatement();

			ResultSet set = stmt.executeQuery(sql);

			liste = new ArrayList<>();

			while (set.next()) {
				ComboItem item = new ComboItem();

				item.setId(set.getInt("id"));
				item.setAdi(set.getString("adi"));
				liste.add(item);
			}

			disconnectDB();

		} catch (Exception e) {

			System.out.println("Hata tanimlarDAO tbl_haciz_sonucu :" + e.getMessage());
			// TODO: handle exception
		}

		return liste;

	}

	public List<ComboItem> getTeslimYeri() {
		List<ComboItem> liste = null;

		try {

			String sql = "Select * from tbl_teslim_yeri ";

			newConnectDB();

			Statement stmt = conn.createStatement();

			ResultSet set = stmt.executeQuery(sql);

			liste = new ArrayList<>();

			while (set.next()) {
				ComboItem item = new ComboItem();

				item.setId(set.getInt("id"));
				item.setAdi(set.getString("adi"));
				liste.add(item);
			}

			disconnectDB();

		} catch (Exception e) {

			System.out.println("Hata tanimlarDAO tbl_teslim_yeri :" + e.getMessage());
			// TODO: handle exception
		}

		return liste;

	}

}
