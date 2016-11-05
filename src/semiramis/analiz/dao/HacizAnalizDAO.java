package semiramis.analiz.dao;

import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import pelops.db.DBConnection;
import semiramis.analiz.model.HacizAnaliz;
import semiramis.analiz.model.HacizAnalizJSON;

public class HacizAnalizDAO extends DBConnection {

	public List<HacizAnalizJSON> getListJSON() {

		List<HacizAnalizJSON> liste = null;

		try {

			String sql = "select hst.*,tablo2.toplam,haciztur.adi as haciz_turu_adi from tbl_haciz_statusu hst  "
					+ " left join ( "
					+ " select tablo.*,htur.id as haciz_turu_id,htur.adi as haciz_turu_adi from tbl_haciz_statusu has "
					+ " inner join tbl_haciz_turu htur on has.haciz_turu_id=htur.id " + " inner join ( "
					+ " SELECT hb.haciz_statusu_id,hs.adi,count(hs.adi) as toplam " + "   FROM tbl_haciz_bilgisi hb "
					+ "   inner join tbl_haciz_statusu hs on hs.id=hb.haciz_statusu_id "
					+ " group by hb.haciz_statusu_id,hs.adi ) tablo on has.id=tablo.haciz_statusu_id "
					+ " ) as tablo2 on tablo2.haciz_statusu_id=hst.id "
					+ " inner join tbl_haciz_turu haciztur on haciztur.id=hst.haciz_turu_id ";

			newConnectDB();

			Statement stmt = conn.createStatement();

			ResultSet set = stmt.executeQuery(sql);

			liste = new ArrayList<HacizAnalizJSON>();

			while (set.next()) {
				HacizAnalizJSON json = new HacizAnalizJSON();

				json.setHacizStatusuAdi(set.getString("adi"));
				json.setHacizStatusuId(set.getInt("id"));
				json.setHacizTuruAdi(set.getString("haciz_turu_adi"));
				json.setHacizTuruId(set.getInt("haciz_turu_id"));
				json.setToplam(set.getInt("toplam"));
				liste.add(json);
			}
			
			disconnectDB();

		} catch (Exception e) {

			System.out.println("Hata hacizAnalizDAO getListJSON :" + e.getMessage());

			// TODO: handle exception
		}

		return liste;
	}

	public List<HacizAnaliz> getList(int hacizTurId) {

		List<HacizAnaliz> liste = null;

		try {

			String sql = "   select icra.icra_dosyasi_no, hs.adi as haciz_statusu,htur.adi as haciz_turu,hsonuc.adi as haciz_sonuc, "
					+ "hb.*,borclu.ad_soyad,hesap.toplam_alacak,tah.tahsilat from tbl_haciz_bilgisi hb  "
					+ " inner join tbl_baglanti bag on hb.borclu_id=bag.borclu_id   inner join tbl_hesap hesap on hesap.id=bag.hesap_id   "
					+ "  inner join tbl_haciz_statusu hs on hs.id=hb.haciz_statusu_id   inner join tbl_borclu borclu on hb.borclu_id=borclu.id   "
					+ " inner join tbl_icra_dosyasi icra on icra.id=hb.icra_dosyasi_id "
					+ " left join tbl_haciz_turu htur on htur.id=hb.haciz_tur_id "
					+ " left join tbl_haciz_sonucu hsonuc on hsonuc.id=hb.haciz_sonucu_id "
					+ " left join (SELECT icra_dosyasi_id, sum(tahsilat_miktari) as tahsilat   "
					+ "  FROM tbl_tahsilat   group by icra_dosyasi_id) as tah on tah.icra_dosyasi_id=hb.icra_dosyasi_id  ";

			if (hacizTurId != 0)
				sql = sql + " where hb.haciz_tur_id=" + hacizTurId;

			newConnectDB();

			Statement stmt = conn.createStatement();

			ResultSet set = stmt.executeQuery(sql);

			liste = new ArrayList<HacizAnaliz>();

			HacizAnaliz ha = null;

			while (set.next()) {

				ha = new HacizAnaliz();

				ha.setBorcluAdSoyad(set.getString("ad_soyad"));
				ha.setBorcluId(set.getInt("borclu_id"));
				ha.setHacizStatusuAdi(set.getString("haciz_statusu"));
				ha.setHacizStatusuId(set.getInt("haciz_statusu_id"));
				ha.setHacizTarihi(set.getString("haciz_tarihi"));
				ha.setIcraDosyaId(set.getInt("icra_dosyasi_id"));
				ha.setIcraDosyaNo(set.getString("icra_dosyasi_no"));
				ha.setId(set.getInt("id"));
				ha.setTahsilatMiktari(
						Double.valueOf(new DecimalFormat("0.00").format(set.getDouble("tahsilat")).replace(",", ".")));
				ha.setToplamAlacak(Double
						.valueOf(new DecimalFormat("0.00").format(set.getDouble("toplam_alacak")).replace(",", ".")));
				ha.setKalanMiktar(ha.getToplamAlacak() - ha.getTahsilatMiktari());
				ha.setHacizSonuc(set.getString("haciz_sonuc"));
				ha.setHacizSonucId(set.getInt("haciz_sonucu_id"));
				ha.setHacizTuru(set.getString("haciz_turu"));
				ha.setHacizTuruId(set.getInt("haciz_tur_id"));

				liste.add(ha);

			}

			disconnectDB();

		} catch (Exception e) {

			System.out.println("Hata HacizAnalizDAO getListe :" + e.getMessage());

			// TODO: handle exception
		}

		return liste;

	}

}
