package semiramis.uyap.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import pelops.db.DBConnection;
import semiramis.operasyon.dao.BorcluBilgisiDAO;
import semiramis.uyap.model.Mernis;

public class MernisDAO extends DBConnection {

	private static MernisDAO dao;
	private BorcluBilgisiDAO borcluDAO = new BorcluBilgisiDAO();
	private Statement stm;
	private String sql = "";
	private ResultSet rs;
	private PreparedStatement pstm;

	public static MernisDAO getInstance() {
		if (dao == null) {
			dao = new MernisDAO();
		}
		return dao;
	}

	private boolean saveMernis(Mernis mernis) {
		int borcluID = 0;
		boolean rs = false;
		try {
			borcluID = borcluDAO.tcSorgulama(mernis.getTcNo());

		} catch (Exception e) {
			System.out.println("MernisDAO.borcluDAO.tcSorgulama : " + e.getMessage());
		}
		if (borcluID != 0) {
			rs = borcluDAO.saveMernisAdress(mernis.getSonuc(), mernis.getTcNo());
		}
		return rs;
	}

	public List saveListOfMernis(List mernises) {
		List returnList = new ArrayList<Mernis>();
		for (Object object : mernises) {
			Mernis mernis = (Mernis) object;
			boolean finished = saveMernis(mernis);
			if (!finished)
				returnList.add(mernis);
		}
		return returnList;
	}

}
