package semiramis.uyap.dao;

import java.util.ArrayList;
import java.util.List;

import semiramis.operasyon.dao.BorcluBilgisiDAO;
import semiramis.uyap.model.Sgk;

public class SgkDAO extends BorcluBilgisiDAO {

	private static SgkDAO dao;
	private BorcluBilgisiDAO borcluDAO = new BorcluBilgisiDAO();

	public static SgkDAO getInstance() {
		if (dao == null)
			dao = new SgkDAO();
		return dao;
	}

	private boolean saveSGK(Sgk sgk) {
		int id = 0;
		boolean rs = false;
		try {
			id = borcluDAO.tcSorgulama(sgk.getTcNo());
		} catch (Exception e) {
			System.out.println("MernisDAO.borcluDAO.tcSorgulama : " + e.getMessage());
		}

		if (id != 0) {
			rs = saveSGKInfo(sgk);
		}
		return rs;
	}

	public List saveSGKs(List sgks) {
		List list = new ArrayList<Sgk>();

		for (Object o : sgks) {
			Sgk sgk = (Sgk) o;
			boolean rs = saveSGK(sgk);
			if (!rs) {
				list.add(sgk);
			}
		}

		return list;
	}

}
