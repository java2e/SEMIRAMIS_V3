package semiramis.analiz.dao;

import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import pelops.db.DBConnection;
import semiramis.analiz.model.HacizAnaliz;

public class HacizAnalizDAO extends DBConnection {

	public List<HacizAnaliz> getList() {

		List<HacizAnaliz> liste = null;

		try {

			String sql = "  select icra.icra_dosyasi_no, hs.adi as haciz_statusu, hb.*,borclu.ad_soyad,hesap.toplam_alacak,tah.tahsilat from tbl_haciz_bilgisi hb "
					+ "  inner join tbl_baglanti bag on hb.borclu_id=bag.borclu_id "
					+ "  inner join tbl_hesap hesap on hesap.id=bag.hesap_id "
					+ "   inner join tbl_haciz_statusu hs on hs.id=hb.haciz_statusu_id "
					+ "  inner join tbl_borclu borclu on hb.borclu_id=borclu.id "
					+ "   inner join tbl_icra_dosyasi icra on icra.id=hb.icra_dosyasi_id "
					+ "  left join (SELECT icra_dosyasi_id, sum(tahsilat_miktari) as tahsilat " + "  FROM tbl_tahsilat "
					+ "  group by icra_dosyasi_id) as tah on tah.icra_dosyasi_id=hb.icra_dosyasi_id ";

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
