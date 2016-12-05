package semiramis.operasyon.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import pelops.db.DBConnection;
import pelops.model.Posta;
import pelops.report.model.ReportUtils;
import semimis.utils.BarcodeBuilder;
import semiramis.operasyon.model.Islem;
import semiramis.operasyon.model.Tebligat;

public class IslemDAO extends DBConnection {

	static IslemDAO dao = null;
	private Statement stm;
	private ResultSet rs;
	private String sql = "";
	private PreparedStatement pstm;

	public static IslemDAO getInstance() {
		if (dao == null)
			dao = new IslemDAO();
		return dao;
	}

	public void saveIslem(int icraDosyaId, int islemId, String barkod) {
		sql = "INSERT INTO tbl_islem( islem_id, icra_dosya_id, barkod , barkod_encoded, tarih) VALUES ( ?, ?, ?, ?, ?);";
		newConnectDB();
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, islemId);
			pstm.setInt(2, icraDosyaId);
			pstm.setString(3, barkod);
			BarcodeBuilder builder = new BarcodeBuilder(barkod);
			pstm.setString(4, builder.getFullCode());
			java.util.Date date = new java.util.Date();
			pstm.setDate(5, convertFromJAVADateToSQLDate(date));
			pstm.executeUpdate();
		} catch (SQLException e) {
			System.out.println("İslemDAO.saveIslem :" + e.getMessage());
			e.printStackTrace();
		} finally {
			disconnectDB();
		}
		// İşlem dao aslında ödeme emri için barkod kaydı yapmaktadır ancak bu
		// kayıt tebligat tablosunda
		// bulunmadığı için kayıt atıldı.
		TebligatDAO dao = new TebligatDAO();
		Tebligat t = new Tebligat();
		t.setIcraDosyaId(icraDosyaId);
		t.setTebligatTuruId(islemId);
		dao.kaydet(t);

	}

	public String checkBarkodforIslem(int icraDosyaId, int islemId) {
		sql = "SELECT barkod FROM tbl_islem" + " where islem_id =" + islemId + "  and icra_dosya_id =" + icraDosyaId
				+ " ;";
		String ret = null;
		newConnectDB();
		try {
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
			while (rs.next()) {
				ret = rs.getString("barkod");

			}
		} catch (SQLException e) {
			System.out.println("İslemDAO.checkBarkodforIslem :" + e.getMessage());

		} finally {
			disconnectDB();
		}
		return ret;
	}

	public String getFullBarkod(int icraDosyaId, int islemId) {
		sql = "SELECT barkod_encoded FROM tbl_islem" + " where islem_id =" + islemId + "  and icra_dosya_id ="
				+ icraDosyaId + " ;";
		String ret = null;
		newConnectDB();
		try {
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
			while (rs.next()) {
				ret = rs.getString("barkod_encoded");

			}
		} catch (SQLException e) {
			System.out.println("İslemDAO.getFullBarkod :" + e.getMessage());

		} finally {
			disconnectDB();
		}
		return ret;
	}

	private void updateData() {
		sql = "SELECT  icra_dosya_id, barkod FROM tbl_posta_barkod where durum = 1;";
		ArrayList<Posta> list = new ArrayList<>();
		newConnectDB();
		try {
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
			while (rs.next()) {
				Posta posta = new Posta();
				posta.setIcra_dosya_id(rs.getInt("icra_dosya_id"));
				posta.setBarkod(rs.getString("barkod"));
				list.add(posta);
			}
			disconnectDB();
			if (list.size() > 0) {
				for (Posta posta : list) {
					saveIslem(posta.getIcra_dosya_id(), ReportUtils.ODEME_EMRI_ISLEM_ID, posta.getBarkod());

				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ArrayList<Islem> getIslemByIcraDosyaId(int icraDosyaId) {

		ArrayList<Islem> list = new ArrayList<>();
		sql = "SELECT i.id, i.islem_id, i.icra_dosya_id, "
				+ " case when (COALESCE(mb.barkod,'')='') and (tb.id=5) then i.barkod else mb.barkod  end as barkod  ,"
				+ "  tb.adi as tebligat_adi, s.adi as sonuc, ts.adi as statusu, t.id as tebligat_id"
				+ " FROM tbl_tebligat t inner join tbl_islem i on  t.icra_dosyasi_id = i.icra_dosya_id "
				+ " inner join tbl_tebligat_tipi tb on i.islem_id = tb.id or t.tebligat_turu_id = tb.id "
				+ " left join tbl_muamele_bilgisi mb on i.icra_dosya_id = mb.icra_dosyasi_id "
				+ " left join tbl_tebligat_sonucu s on t.tebligat_sonucu_id = s.id "
				+ " left join tbl_tebligat_statusu ts on ts.id = t.tebligat_statusu_id  where i.icra_dosya_id ="
				+ icraDosyaId + ";";
		newConnectDB();
		try {

			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
			while (rs.next()) {
				Islem islem = new Islem();
				islem.setId(rs.getInt("id"));
				islem.setIslem_id(rs.getInt("islem_id"));
				islem.setBarkod(rs.getString("barkod") == null ? "" : rs.getString("barkod"));
				islem.setIslemAdi(rs.getString("tebligat_adi"));
				islem.setTebligatId(rs.getInt("tebligat_id"));
				islem.setSonuc(rs.getString("sonuc") == null ? "" : rs.getString("sonuc"));
				islem.setStatusu(rs.getString("statusu") == null ? "" : rs.getString("statusu"));
				list.add(islem);
			}
		} catch (SQLException e) {
			System.out.println("IslemDAO.getIslemByIcraDosyaId : " + e.getMessage());
		}
		return list;
	}

}
