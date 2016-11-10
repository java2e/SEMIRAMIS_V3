package semiramis.analiz.dao;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pelops.db.DBConnection;
import semiramis.analiz.model.VizitAnaliz;

public class VizitAnalizDAO extends DBConnection {

	public List<VizitAnaliz> getList() {

		List<VizitAnaliz> liste = null;

		try {

			String sql = "SELECT  borclu.ad_soyad,borclu.adres,icra.icra_dosyasi_no, iz.icra_dosyasi_id,iz.izleme_tarihi,iz.izleme_sonucu_id,isn.adi as izleme_sonucu,viz.* "
					+ " FROM tbl_izleme_bilgisi iz "
					+ " inner join tbl_izleme_sonucu isn on isn.id=iz.izleme_sonucu_id "
					+ " inner join tbl_baglanti bag on bag.icra_dosyasi_id=iz.icra_dosyasi_id "
					+ " inner join tbl_borclu borclu on borclu.id=bag.borclu_id "
					+ " inner join tbl_icra_dosyasi icra on icra.id=bag.icra_dosyasi_id "
					+ " left join tbl_vizit_bilgisi viz on iz.icra_dosyasi_id=viz.icra_dosyasi "
					+ " where iz.vizit_durumu=1";

			newConnectDB();

			Statement stmt = conn.createStatement();

			ResultSet set = stmt.executeQuery(sql);

			liste = new ArrayList<VizitAnaliz>();

			VizitAnaliz vizitAnaliz = null;

			while (set.next()) {

				vizitAnaliz = new VizitAnaliz();

				vizitAnaliz.setBorcluAdres(set.getString("adres"));
				vizitAnaliz.setBorcluAdSoyad(set.getString("ad_soyad"));
				vizitAnaliz.setIcraDosyaNo(set.getString("icra_dosyasi_no"));
				vizitAnaliz.setIzlemeSonucu(set.getString("izleme_sonucu"));
				vizitAnaliz.setIzlemeSonucuId(set.getInt("izleme_sonucu_id"));
				vizitAnaliz.setIzlemeTarihi(set.getString("izleme_tarihi"));
				vizitAnaliz.setOdemeSozuMiktari(set.getDouble("odeme_sozu_miktari"));
				vizitAnaliz.setOdemeSozuTarih(set.getString("odeme_sozu_tarihi"));
				vizitAnaliz.setVizitNotu(set.getString("vizit_notu"));
				vizitAnaliz.setVizitStatusu(set.getString("vizit_statusu"));
				vizitAnaliz.setVizitStatusuId(set.getInt("vizit_statusu_id"));
				vizitAnaliz.setVizitTarihi(set.getString("vizit_tarihi"));

				liste.add(vizitAnaliz);

			}

		} catch (Exception e) {

			System.out.println("Hata vizitAnalizDAO getList :" + e.getMessage());

		}

		return liste;

	}

	public ArrayList<VizitAnaliz> getWholeDataWithOptions(Date date1, Date date2, int icraMdId, int muvekkilId) {
		ArrayList<VizitAnaliz> liste = null;

		try {

			String sql = "SELECT  borclu.ad_soyad,borclu.adres,icra.icra_dosyasi_no, iz.icra_dosyasi_id,iz.izleme_tarihi,iz.izleme_sonucu_id,isn.adi as izleme_sonucu,viz.* "
					+ " FROM tbl_izleme_bilgisi iz "
					+ " inner join tbl_izleme_sonucu isn on isn.id=iz.izleme_sonucu_id "
					+ " inner join tbl_baglanti bag on bag.icra_dosyasi_id=iz.icra_dosyasi_id "
					+ " inner join tbl_borclu borclu on borclu.id=bag.borclu_id "
					+ " inner join tbl_icra_dosyasi icra on icra.id=bag.icra_dosyasi_id "
					+ " inner join tbl_alacakli_bilgisi ab on ab.id=bag.alacakli_id "
					+ " inner join tbl_icra_mudurlugu icm on icm.id= icra.icra_mudurlugu_id  "
					+ " left join tbl_vizit_bilgisi viz on iz.icra_dosyasi_id=viz.icra_dosyasi "
					+ " where iz.vizit_durumu=1 ";

			if (date1 != null && date2 != null && date1.before(date2)) {
				sql += " and iz.izleme_tarihi between '" + convertDateToString(date1) + "' and '"
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

			liste = new ArrayList<VizitAnaliz>();

			VizitAnaliz vizitAnaliz = null;

			while (set.next()) {

				vizitAnaliz = new VizitAnaliz();

				vizitAnaliz.setBorcluAdres(set.getString("adres"));
				vizitAnaliz.setBorcluAdSoyad(set.getString("ad_soyad"));
				vizitAnaliz.setIcraDosyaNo(set.getString("icra_dosyasi_no"));
				vizitAnaliz.setIzlemeSonucu(set.getString("izleme_sonucu"));
				vizitAnaliz.setIzlemeSonucuId(set.getInt("izleme_sonucu_id"));
				vizitAnaliz.setIzlemeTarihi(set.getString("izleme_tarihi"));
				vizitAnaliz.setOdemeSozuMiktari(set.getDouble("odeme_sozu_miktari"));
				vizitAnaliz.setOdemeSozuTarih(set.getString("odeme_sozu_tarihi"));
				vizitAnaliz.setVizitNotu(set.getString("vizit_notu"));
				vizitAnaliz.setVizitStatusu(set.getString("vizit_statusu"));
				vizitAnaliz.setVizitStatusuId(set.getInt("vizit_statusu_id"));
				vizitAnaliz.setVizitTarihi(set.getString("vizit_tarihi"));

				liste.add(vizitAnaliz);

			}

		} catch (Exception e) {

			System.out.println("Hata vizitAnalizDAO getWholeDataWithOptions :" + e.getMessage());

		}

		return liste;
	}

}
