package semiramis.uyap.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import pelops.users.User;
import pelops.users.Util;
import semiramis.operasyon.controller.Utils;
import semiramis.operasyon.dao.HaczeEsasMalBilgisiDAO;
import semiramis.operasyon.model.ChronologyIdentifier;
import semiramis.operasyon.model.HaczeEsasMalBilgisi;

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

	public int getIcraDosyaID(int borcluID) {

		int id = 0;
		String sorgu = "SELECT  icra_dosyasi_id FROM tbl_baglanti where borclu_id =" + borcluID + ";";

		Statement stm;
		try {
			newConnectDB();

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

	public List<HaczeEsasMalBilgisi> saveEGMs(List egms) {
		List<HaczeEsasMalBilgisi> returnList = new ArrayList<>();
		for (Object o : egms) {
			HaczeEsasMalBilgisi haczeEsasMalBilgisi = (HaczeEsasMalBilgisi) o;
			haczeEsasMalBilgisi.setMalTipiId(3);
			boolean result = kaydet(haczeEsasMalBilgisi);
			if (!result)
				returnList.add(haczeEsasMalBilgisi);
		}
		return returnList;
	}

	public int getUserID() {
		HttpSession session = Util.getSession();
		User user = (User) session.getAttribute("user");
		return user.getUsrId();
	}

}
