package semiramis.analiz.dao;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pelops.db.DBConnection;
import semiramis.analiz.model.TebligatAnaliz;
import semiramis.analiz.model.TebligatAnalizJSON;

public class TebligatAnalizDAO extends DBConnection {

	public List<TebligatAnalizJSON> getTebligatTuruJSON(int icraMdId, int muvekkilId, Date date1, Date date2) {
		List<TebligatAnalizJSON> liste = null;

		try {

			String sql = "select teb.tebligat_turu_id,tip.adi as tebligat_turu, count(teb.tebligat_turu_id) from tbl_tebligat teb "
					+ " right join tbl_tebligat_tipi tip on tip.id=teb.tebligat_turu_id inner join tbl_icra_dosyasi icra on icra.id=teb.icra_dosyasi_id "
					+ " inner join tbl_baglanti b on teb.icra_dosyasi_id = b.icra_dosyasi_id inner join tbl_alacakli_bilgisi ab on ab.id=b.alacakli_id "
					+ " inner join tbl_icra_mudurlugu icm on icm.id= icra.icra_mudurlugu_id  inner join tbl_borclu borclu on borclu.id=teb.borclu_id  left join tbl_tebligat_tipi ttip on ttip.id=teb.tebligat_turu_id  left join tbl_tebligat_statusu tstatu on tstatu.id=teb.tebligat_statusu_id "
					+ "  left join tbl_tebligat_sonucu tsonuc on tsonuc.id=teb.tebligat_sonucu_id " + " where 1=1 ";
			if (date1 != null && date2 != null && date1.before(date2)) {
				sql += " and teb.guncelleme_zamani between '" + convertDateToString(date1) + "' and '"
						+ convertDateToString(date2) + "' ";
			}
			if (icraMdId > 0) {
				sql += " and icm.id = " + icraMdId;
			}
			if (muvekkilId > 0) {
				sql += " and ab.id = " + muvekkilId;
			}
			sql += " group by teb.tebligat_turu_id,tip.adi ";

			newConnectDB();
			Statement stmt = conn.createStatement();

			ResultSet set = stmt.executeQuery(sql);

			liste = new ArrayList<TebligatAnalizJSON>();

			while (set.next()) {
				TebligatAnalizJSON json = new TebligatAnalizJSON();

				json.setAdi(set.getString("tebligat_turu"));
				json.setToplam(set.getInt("count"));
				json.setId(set.getInt("tebligat_turu_id"));
				liste.add(json);
			}

			disconnectDB();

		} catch (Exception e) {

			System.out.println("HATA tebligatANALİZDAO getTebligatTuruJSON :" + e.getMessage());

		}

		return liste;
	}

	public List<TebligatAnalizJSON> getTebligatStatusuJSON(int icraMdId, int muvekkilId, Date date1, Date date2) {
		List<TebligatAnalizJSON> liste = null;

		try {

			String sql = "select teb.tebligat_statusu_id,tip.adi as tebligat_statusu, count(teb.tebligat_statusu_id) from tbl_tebligat teb "
					+ " right join tbl_tebligat_statusu tip on tip.id=teb.tebligat_statusu_id inner join tbl_icra_dosyasi icra on icra.id=teb.icra_dosyasi_id "
					+ " inner join tbl_baglanti b on teb.icra_dosyasi_id = b.icra_dosyasi_id inner join tbl_alacakli_bilgisi ab on ab.id=b.alacakli_id "
					+ " inner join tbl_icra_mudurlugu icm on icm.id= icra.icra_mudurlugu_id   inner join tbl_borclu borclu on borclu.id=teb.borclu_id  left join tbl_tebligat_tipi ttip on ttip.id=teb.tebligat_turu_id  left join tbl_tebligat_statusu tstatu on tstatu.id=teb.tebligat_statusu_id "
					+ "  left join tbl_tebligat_sonucu tsonuc on tsonuc.id=teb.tebligat_sonucu_id " + " where 1=1 ";
			if (date1 != null && date2 != null && date1.before(date2)) {
				sql += " and teb.guncelleme_zamani between '" + convertDateToString(date1) + "' and '"
						+ convertDateToString(date2) + "' ";
			}
			if (icraMdId > 0) {
				sql += " and icm.id = " + icraMdId;
			}
			if (muvekkilId > 0) {
				sql += " and ab.id = " + muvekkilId;
			}
			sql += " group by teb.tebligat_statusu_id,tip.adi ";
			newConnectDB();
			Statement stmt = conn.createStatement();

			ResultSet set = stmt.executeQuery(sql);

			liste = new ArrayList<TebligatAnalizJSON>();

			while (set.next()) {
				TebligatAnalizJSON json = new TebligatAnalizJSON();

				json.setAdi(set.getString("tebligat_statusu"));
				json.setToplam(set.getInt("count"));
				json.setId(set.getInt("tebligat_statusu_id"));
				liste.add(json);
			}

			disconnectDB();

		} catch (Exception e) {

			System.out.println("HATA tebligatANALİZDAO getTebligatSonucuJSON :" + e.getMessage());

		}

		return liste;
	}

	public List<TebligatAnalizJSON> getTebligatSonucuJSON(int icraMdId, int muvekkilId, Date date1, Date date2) {
		List<TebligatAnalizJSON> liste2 = null;

		try {

			String sql = "select teb.tebligat_sonucu_id,tip.adi as tebligat_sonucu, count(teb.tebligat_sonucu_id) from tbl_tebligat teb "
					+ " right join tbl_tebligat_sonucu tip on tip.id=teb.tebligat_sonucu_id inner join tbl_icra_dosyasi icra on icra.id=teb.icra_dosyasi_id "
					+ " inner join tbl_baglanti b on teb.icra_dosyasi_id = b.icra_dosyasi_id inner join tbl_alacakli_bilgisi ab on ab.id=b.alacakli_id "
					+ " inner join tbl_icra_mudurlugu icm on icm.id= icra.icra_mudurlugu_id   inner join tbl_borclu borclu on borclu.id=teb.borclu_id  left join tbl_tebligat_tipi ttip on ttip.id=teb.tebligat_turu_id  left join tbl_tebligat_statusu tstatu on tstatu.id=teb.tebligat_statusu_id "
					+ "  left join tbl_tebligat_sonucu tsonuc on tsonuc.id=teb.tebligat_sonucu_id  where 1=1  ";

			if (date1 != null && date2 != null && date1.before(date2)) {
				sql += " and teb.guncelleme_zamani between '" + convertDateToString(date1) + "' and '"
						+ convertDateToString(date2) + "' ";
			}
			if (icraMdId > 0) {
				sql += " and icm.id = " + icraMdId;
			}
			if (muvekkilId > 0) {
				sql += " and ab.id = " + muvekkilId;
			}
			sql += " group by teb.tebligat_sonucu_id,tip.adi ";

			newConnectDB();
			Statement stmt = conn.createStatement();

			ResultSet set = stmt.executeQuery(sql);

			liste2 = new ArrayList<TebligatAnalizJSON>();

			while (set.next()) {
				TebligatAnalizJSON json = new TebligatAnalizJSON();

				json.setAdi(set.getString("tebligat_sonucu"));
				json.setToplam(set.getInt("count"));
				json.setId(set.getInt("tebligat_sonucu_id"));

				liste2.add(json);
			}

			disconnectDB();

		} catch (Exception e) {

			System.out.println("HATA tebligatANALİZDAO getTebligatTuruJSON :" + e.getMessage());

		}

		return liste2;
	}

	public List<TebligatAnaliz> getList() {

		List<TebligatAnaliz> liste = null;

		try {

			String sql = "SELECT teb.*,icra.icra_dosyasi_no,borclu.ad_soyad,ttip.adi as tebligat_turu,tstatu.adi as tebligat_statusu,tsonuc.adi as tebligat_sonucu "
					+ " FROM tbl_tebligat teb " + " inner join tbl_icra_dosyasi icra on icra.id=teb.icra_dosyasi_id "
					+ " inner join tbl_borclu borclu on borclu.id=teb.borclu_id "
					+ " left join tbl_tebligat_tipi ttip on ttip.id=teb.tebligat_turu_id "
					+ " left join tbl_tebligat_statusu tstatu on tstatu.id=teb.tebligat_statusu_id "
					+ " left join tbl_tebligat_sonucu tsonuc on tsonuc.id=teb.tebligat_sonucu_id ";

			newConnectDB();

			Statement stmt = conn.createStatement();

			ResultSet set = stmt.executeQuery(sql);

			liste = new ArrayList<TebligatAnaliz>();

			while (set.next()) {
				TebligatAnaliz t = new TebligatAnaliz();
				t.setBorcluAdSoyad(set.getString("ad_soyad"));
				t.setBorcluId(set.getInt("borclu_id"));
				t.setIcraDosyaId(set.getInt("icra_dosyasi_id"));
				t.setIcraDosyaNo(set.getString("icra_dosyasi_no"));
				t.setId(set.getInt("id"));
				t.setTebligatSonuc(set.getString("tebligat_sonucu") == null ? "" : set.getString("tebligat_sonucu"));
				t.setTebligatSonucId(set.getInt("tebligat_sonucu_id"));
				t.setTebligatStatusu(
						set.getString("tebligat_statusu") == null ? "" : set.getString("tebligat_statusu"));
				t.setTebligatStatusuId(set.getInt("tebligat_statusu_id"));
				t.setTebligatTur(set.getString("tebligat_turu") == null ? "" : set.getString("tebligat_turu"));
				t.setTebligatTurId(set.getInt("tebligat_turu_id"));
				t.setGuncellemeZamani(set.getString("guncelleme_zamani"));

				liste.add(t);
			}

			disconnectDB();

		} catch (Exception e) {

			System.out.println("HATA tebligatAnaliziDAO list :" + e.getMessage());
			// TODO: handle exception
		}

		return liste;

	}

	public ArrayList<TebligatAnaliz> getWholeDataWithOptions(Date date1, Date date2, int icraMdId, int muvekkilId) {
		ArrayList<TebligatAnaliz> liste = null;

		try {

			String sql = "SELECT teb.*,icra.icra_dosyasi_no,borclu.ad_soyad,ttip.adi as tebligat_turu,tstatu.adi as tebligat_statusu, "
					+ "ab.muvekkil_adi as muvekkil_adi, icm.adi as icra_md, "
					+ "tsonuc.adi as tebligat_sonucu  FROM tbl_tebligat teb  inner join tbl_icra_dosyasi icra on icra.id=teb.icra_dosyasi_id "
					+ " inner join tbl_baglanti b on teb.icra_dosyasi_id = b.icra_dosyasi_id inner join tbl_alacakli_bilgisi ab on ab.id=b.alacakli_id "
					+ " inner join tbl_icra_mudurlugu icm on icm.id= icra.icra_mudurlugu_id   inner join tbl_borclu borclu on borclu.id=teb.borclu_id  left join tbl_tebligat_tipi ttip on ttip.id=teb.tebligat_turu_id  left join tbl_tebligat_statusu tstatu on tstatu.id=teb.tebligat_statusu_id "
					+ "  left join tbl_tebligat_sonucu tsonuc on tsonuc.id=teb.tebligat_sonucu_id  where 1=1";

			if (date1 != null && date2 != null && date1.before(date2)) {
				sql += " and teb.guncelleme_zamani between '" + convertDateToString(date1) + "' and '"
						+ convertDateToString(date2) + "' ";
			}
			if (icraMdId > 0) {
				sql += " and icm.id = " + icraMdId;
			}
			if (muvekkilId > 0) {
				sql += " and ab.id = " + muvekkilId;
			}
			newConnectDB();

			Statement stmt = conn.createStatement();

			ResultSet set = stmt.executeQuery(sql);

			liste = new ArrayList<TebligatAnaliz>();

			while (set.next()) {
				TebligatAnaliz t = new TebligatAnaliz();
				t.setBorcluAdSoyad(set.getString("ad_soyad"));
				t.setBorcluId(set.getInt("borclu_id"));
				t.setIcraDosyaId(set.getInt("icra_dosyasi_id"));
				t.setIcraDosyaNo(set.getString("icra_dosyasi_no"));
				t.setId(set.getInt("id"));
				t.setTebligatSonuc(set.getString("tebligat_sonucu") == null ? "" : set.getString("tebligat_sonucu"));
				t.setTebligatSonucId(set.getInt("tebligat_sonucu_id"));
				t.setTebligatStatusu(
						set.getString("tebligat_statusu") == null ? "" : set.getString("tebligat_statusu"));
				t.setTebligatStatusuId(set.getInt("tebligat_statusu_id"));
				t.setTebligatTur(set.getString("tebligat_turu") == null ? "" : set.getString("tebligat_turu"));
				t.setTebligatTurId(set.getInt("tebligat_turu_id"));
				t.setGuncellemeZamani(set.getString("guncelleme_zamani"));
				t.setMuvekkil(set.getString("muvekkil_adi"));
				t.setIcraMd(set.getString("icra_md"));
				liste.add(t);
			}
			disconnectDB();
		} catch (Exception e) {
			System.out.println("TebligatAlanizDAO.getWholeListWithOptions :" + e.getMessage());
		} finally {
			disconnectDB();
		}

		return liste;

	}

}
