package semiramis.operasyon.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pelops.db.DBConnection;
import semimis.utils.BarcodeBuilder;
import semiramis.operasyon.model.Tebligat;

public class TopluTebligatDAO extends DBConnection {
	private static TopluTebligatDAO dao;

	public static TopluTebligatDAO getInstance() {
		if (dao == null)
			dao = new TopluTebligatDAO();
		return dao;
	}

	public List getFilteredList(int tebligatTuru, int icraMdId, Date date1, Date date2) {

		return getFilteredListFromDB(tebligatTuru, icraMdId, date1, date2);

	}

	private List getFilteredListFromDB(int tebligatTuru, int icraMdId, Date date1, Date date2) {
		String sql = "SELECT i.id, i.icra_dosyasi_no, i.icra_mudurlugu_id,  i.ekleme_tarihi,"
				+ " islem.barkod as barkod_odeme, mb.barkod as barkod_muamele" + ""
				+ ", md.adi as icra_md  FROM tbl_icra_dosyasi i"
				+ " inner join tbl_islem islem on islem.icra_dosya_id = i.id "
				+ " inner join tbl_muamele_bilgisi mb on i.id = mb.icra_dosyasi_id " + ""
				+ "inner join tbl_icra_mudurlugu md on i.icra_mudurlugu_id = md.id" + " where 1=1 ";
		if (icraMdId > 0) {
			sql += " and i.icra_mudurlugu_id=" + icraMdId;
		}
		if (date1 != null && date2 != null && date1.before(date2)) {
			sql += " and i.ekleme_tarihi between '" + convertDateToString(date1) + "' and '"
					+ convertDateToString(date2) + "' ";
		}
		List<Tebligat> list = new ArrayList<>();
		try {
			newConnectDB();
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				Tebligat tebligat = new Tebligat();
				tebligat.setIcraDosyaId(rs.getInt("id"));
				tebligat.setIcraDosyaNo(rs.getString("icra_dosyasi_no"));
				if (tebligatTuru == 5) { // 5 : Odeme Emri
					BarcodeBuilder builder = new BarcodeBuilder(rs.getString("barkod_odeme"));
					tebligat.setBarkod(builder.getFullCode());
				} else {
					BarcodeBuilder builder = new BarcodeBuilder(rs.getString("barkod_muamele"));
					tebligat.setBarkod(builder.getFullCode());
				}
				tebligat.setIcraMd(rs.getString("icra_md"));
				list.add(tebligat);
			}
			disconnectDB();

		} catch (Exception e) {
			System.out.println("TopluTebligatDAO.getFilteredListFromMuamele :" + e.getMessage());
		}

		return list;
	}

}
