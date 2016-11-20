package semiramis.uyap.dao;

import java.util.ArrayList;
import java.util.List;

import pelops.db.DBConnection;
import semiramis.operasyon.dao.BorcluBilgisiDAO;
import semiramis.operasyon.dao.HaczeEsasMalBilgisiDAO;
import semiramis.operasyon.model.HaczeEsasMalBilgisi;
import semiramis.uyap.model.Sgk;

public class SgkDAO extends DBConnection {

	private static SgkDAO dao;
	private BorcluBilgisiDAO borcluBilgisiDAO = new BorcluBilgisiDAO();

	public static SgkDAO getInstance() {
		if (dao == null)
			dao = new SgkDAO();
		return dao;
	}

	private boolean saveSGK(Sgk sgk) {
		int id = 0;
		boolean rs = false;
		HaczeEsasMalBilgisiDAO dao = new HaczeEsasMalBilgisiDAO();
		try {
			id = borcluBilgisiDAO.tcSorgulama(sgk.getTcNo());
		} catch (Exception e) {
			System.out.println("MernisDAO.borcluDAO.tcSorgulama : " + e.getMessage());
		}

		if (id != 0) {

			rs = dao.kaydet(convertSGKtoHB(sgk, id));
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

	public HaczeEsasMalBilgisi convertSGKtoHB(Sgk sgk, int borcluId) {
		EgmDAO egmDAO = new EgmDAO();
		HaczeEsasMalBilgisi haczeEsasMalBilgisi = new HaczeEsasMalBilgisi();
		haczeEsasMalBilgisi.setBorcluId(borcluId);
		haczeEsasMalBilgisi.setIcraDosyaId(egmDAO.getIcraDosyaID(borcluId));

		haczeEsasMalBilgisi.setMuhatapAdi(sgk.getIsYeri());
		haczeEsasMalBilgisi.setMuhattapSicilNo(sgk.getIsYeriSicilNo());
		haczeEsasMalBilgisi.setMalTipiId(2);// 2: sgk maaş
		haczeEsasMalBilgisi.setMuhatapAdresi(sgk.getIsYeriAdress());
		return haczeEsasMalBilgisi;
	}

}
