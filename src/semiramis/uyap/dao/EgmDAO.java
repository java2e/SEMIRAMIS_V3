package semiramis.uyap.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpSession;

import pelops.controller.AktifBean;
import pelops.model.HaczeEsasMalBilgisi;
import pelops.users.User;
import pelops.users.Util;
import semiramis.operasyon.controller.Utils;
import semiramis.operasyon.dao.HaczeEsasMalBilgisiDAO;
import semiramis.operasyon.model.ChronologyIdentifier;

public class EgmDAO extends HaczeEsasMalBilgisiDAO {
	private String SQL = null;
	private PreparedStatement pstm;

	public int getBorcluID(String tcno) {

		newConnectDB();
		int id = 0;
		String sorgu = "SELECT id FROM tbl_borclu where tc_no = '" + tcno + "';";

		Statement stm;
		try {
			stm = conn.createStatement();

			ResultSet rs = stm.executeQuery(sorgu);

			while (rs.next()) {

				id = rs.getInt("id");
			}
			disconnectDB();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("EgmDAO.getBorcluID :" + e.getMessage());
		}
		return id;
	}

	public int getIcraDosyaID(String tcno) {

		newConnectDB();
		int id = 0;
		String sorgu = "SELECT icra_dosyasi_id FROM tbl_borclu where tc_no = '" + tcno + "';";

		Statement stm;
		try {
			stm = conn.createStatement();

			ResultSet rs = stm.executeQuery(sorgu);

			while (rs.next()) {

				id = rs.getInt("icra_dosyasi_id");
			}
			disconnectDB();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("EgmDAO.getIcraDosyaID :" + e.getMessage());
		}
		return id;
	}

	public boolean saveEGMVehicle(HaczeEsasMalBilgisi haczeEsasMalBilgisi) {

		boolean kaydedildi = false;

		SQL = "INSERT INTO tbl_hacze_esas_mal_bilgisi" + "( borclu_id, arac_plaka_no, arac_aractipi,"
				+ " diger_bilgiler, icra_dosyasi_id," + " guncelleyen_kullanici_id)" + " VALUES (?, ?, ?, ?, ?, ?);";

		newConnectDB();
		System.out.println(haczeEsasMalBilgisi.getDigerBilgiler() + " " + Util.getUserId());

		try {
			pstm = conn.prepareStatement(SQL);

			pstm.setInt(1, haczeEsasMalBilgisi.getBorcluId());
			pstm.setString(2, haczeEsasMalBilgisi.getAracPlakaNo());
			pstm.setString(3, haczeEsasMalBilgisi.getAracAracTipi());
			pstm.setString(4, haczeEsasMalBilgisi.getDigerBilgiler());
			pstm.setInt(5, haczeEsasMalBilgisi.getIcraDosyaId());
			pstm.setInt(6, getUserID());

			int sonuc = pstm.executeUpdate();
			System.out.println(sonuc);
			disconnectDB();

			if (sonuc == 1) {
				kaydedildi = true;
				// utils.saveChronology(AktifBean.getIcraDosyaID(),
				// ChronologyIdentifier.ISLEM_HESAP,
				// utils.getBocluAdi() + " Borçluya hacze esas mal bilgisi
				// eklendi Mal Bilgisi: "
				// + haczeEsasMalBilgisi.getMenkulBilgisi());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("EgmDAO.saveEGMVehicle : " + e.getMessage());
		}
		return kaydedildi;
	}

	public int getUserID() {
		HttpSession session = Util.getSession();
		User user = (User) session.getAttribute("user");
		return user.getUsrId();
	}

}
