package semiramis.operasyon.dao;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pelops.db.DBConnection;
import semiramis.operasyon.model.ComboItem;

public class MuzekkereTopluDAO extends DBConnection {

	

	public List<Integer> getIcraDosyaListesi(int alacakliId, int icraMudurluguId, int muzekkereTipiId,
			String icraDosyaNo, String basTarih, String bitisTarih) {

		List<Integer> liste = null;

		try {

			String sql = "SELECT hmal.icra_dosyasi_id " + " FROM tbl_hacze_esas_mal_bilgisi hmal "
					+ " inner join tbl_baglanti bag on bag.borclu_id=hmal.borclu_id"
					+ " inner join tbl_icra_dosyasi icra on bag.icra_dosyasi_id=icra.id ";

			if (alacakliId != 0)
				sql = sql + " inner join tbl_alacakli_bilgisi alacakli on bag.alacakli_id=" + alacakliId;

			sql = sql + " where 1=1 ";

			if (icraMudurluguId != 0)
				sql = sql + " and icra.icra_mudurlugu_id=" + icraMudurluguId;

			if (muzekkereTipiId !=0)
				sql = sql + " and hmal.mal_tipi_id="+muzekkereTipiId;
			
			
			if (icraDosyaNo != "")
				sql = sql + " and icra.icra_dosyasi_no in('" + icraDosyaNo + "')";

			sql = sql + " and to_char(hmal.ekleme_tarihi,'YYYY-MM-DD')>='" + basTarih + "' and to_char(hmal.ekleme_tarihi,'YYYY-MM-DD')<'" + bitisTarih+"'";
			
			sql=sql+"  group by hmal.icra_dosyasi_id ";

			newConnectDB();

			Statement stmt = conn.createStatement();
			ResultSet set = stmt.executeQuery(sql);

			liste = new ArrayList<Integer>();

			while (set.next()) {
				liste.add(set.getInt("icra_dosyasi_id"));
			}

		} catch (Exception e) {

			System.out.println("Hata muzekkeretoplu hata :" + e.getMessage());
			// TODO: handle exception
		}

		return liste;

	}

	public List<ComboItem> getAlacakliList() {

		List<ComboItem> liste = null;

		try {

			String sql = "Select muvekkil_adi,id from tbl_alacakli_bilgisi";

			newConnectDB();

			Statement stmt = conn.createStatement();

			ResultSet set = stmt.executeQuery(sql);

			liste = new ArrayList<ComboItem>();

			while (set.next()) {
				ComboItem item = new ComboItem();

				item.setAdi(set.getString("muvekkil_adi"));
				item.setId(set.getInt("id"));

				liste.add(item);
			}

			disconnectDB();

		} catch (Exception e) {

			System.out.println("Hata :" + e.getMessage());
			// TODO: handle exception
		}

		return liste;
	}

}
