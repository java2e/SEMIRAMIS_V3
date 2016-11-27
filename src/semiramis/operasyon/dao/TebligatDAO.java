package semiramis.operasyon.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pelops.db.DBConnection;
import pelops.users.Util;
import semimis.utils.BarcodeBuilder;
import semimis.utils.IDAO;
import semiramis.operasyon.model.Tebligat;

public class TebligatDAO extends DBConnection implements IDAO<Tebligat> {

	@Override
	public boolean kaydet(Tebligat t) {
		boolean result = false;
		try {

			String sql = "INSERT INTO tbl_tebligat( "
					+ "icra_dosyasi_id, borclu_id, tebligat_turu_id, tebligat_statusu_id,  "
					+ " tebligat_sonucu_id, guncelleyen_kullanici_id, guncelleme_zamani) "
					+ " VALUES (?, ?, ?, ?, ?, ?,now()); ";

			newConnectDB();

			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setInt(1, t.getIcraDosyaId());
			stmt.setInt(2, t.getBorcluId());
			stmt.setInt(3, t.getTebligatTuruId());
			stmt.setInt(4, t.getTebligatStatusuId());
			stmt.setInt(5, t.getTebligatSonucuId());
			stmt.setInt(6, Util.getUser().getUsrId());

			int rs = stmt.executeUpdate();
			if (rs == 1)
				result = true;

			disconnectDB();

		} catch (Exception e) {

			System.out.println("Hata tebligatDAO KAYDET:" + e.getMessage());

		}

		return result;
	}

	@Override
	public boolean guncelleme(Tebligat t) {

		try {

			String sql = "UPDATE tbl_tebligat " + " SET tebligat_turu_id=?, tebligat_statusu_id=?,  "
					+ "    tebligat_sonucu_id=?, guncelleyen_kullanici_id=?, guncelleme_zamani=now() " + "  WHERE id="
					+ t.getId();

			newConnectDB();
			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setInt(1, t.getTebligatTuruId());
			stmt.setInt(2, t.getTebligatStatusuId());
			stmt.setInt(3, t.getTebligatSonucuId());
			stmt.setInt(4, Util.getUser().getUsrId());

			stmt.execute();

			disconnectDB();

		} catch (Exception e) {

			System.out.println("Hata tebligatDAO GUNCELLEME :" + e.getMessage());

		}

		return false;
	}

	@Override
	public boolean sil(int id) {

		try {

			String sql = "Delete from tbl_tebligat where id=" + id;

			newConnectDB();

			Statement stmt = conn.createStatement();

			stmt.execute(sql);

			disconnectDB();

		} catch (Exception e) {

			System.out.println("Hata tebligatDAO SİL :" + e.getMessage());
			// TODO: handle exception
		}

		return false;
	}

	@Override
	public Tebligat getT(int id) {

		Tebligat t = null;

		try {

			String sql = "SELECT id, icra_dosyasi_id, borclu_id, tebligat_turu_id, tebligat_statusu_id,"
					+ " tebligat_sonucu_id, guncelleyen_kullanici_id, guncelleme_zamani "
					+ " FROM tbl_tebligat where icra_dosyasi_id=" + id;

			newConnectDB();

			Statement stmt = conn.createStatement();

			ResultSet set = stmt.executeQuery(sql);

			while (set.next()) {

				t = new Tebligat();
				t.setId(set.getInt("id"));
				t.setBorcluId(set.getInt("borclu_id"));
				t.setIcraDosyaId(set.getInt("icra_dosyasi_id"));
				t.setKullaniciId(set.getInt("guncelleyen_kullanici_id"));
				t.setTebligatSonucuId(set.getInt("tebligat_sonucu_id"));
				t.setTebligatStatusuId(set.getInt("tebligat_statusu_id"));
				t.setTebligatTuruId(set.getInt("tebligat_turu_id"));

			}

			disconnectDB();

		} catch (Exception e) {

			System.out.println("Hata tebligatDAO SELECT :" + e.getMessage());

			// TODO: handle exception
		} finally {
			try {
				disconnectDB();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return t;
	}

	@Override
	public List<Tebligat> liste(int id, int id2) {
		List<Tebligat> list = new ArrayList<>();
		try {

			String sql = "SELECT t.id, t.icra_dosyasi_id, t.borclu_id, t.tebligat_turu_id, t.tebligat_statusu_id,"
					+ " ts.adi as tebligat_sonucu, tt.adi as tebligat_turu, t.tebligat_sonucu_id, "
					+ "b.ad_soyad as borclu_adi " + " FROM tbl_tebligat t "
					+ "inner join tbl_tebligat_statusu ts on ts.id=t.tebligat_statusu_id "
					+ "inner join tbl_borclu b on t.borclu_id = b.id "
					+ "inner join tbl_tebligat_tipi  tt on tt.id = t.tebligat_turu_id where t.icra_dosyasi_id =" + id
					+ " ;";
			newConnectDB();
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				Tebligat tebligat = new Tebligat();
				tebligat.setIcraDosyaId(rs.getInt("icra_dosyasi_id"));
				tebligat.setBorcluAdSoyad(rs.getString("borclu_adi"));
				tebligat.setBorcluId(rs.getInt("borclu_id"));
				tebligat.setTebligatTuruId(rs.getInt("tebligat_turu_id"));
				tebligat.setTebligatStatusuId(rs.getInt("tebligat_statusu_id"));
				tebligat.setTebligatTuruAdi(rs.getString("tebligat_turu"));
				tebligat.setTebligatSonucuId(rs.getInt("tebligat_sonucu_id"));
				tebligat.setTebligatSonucuAdi(rs.getString("tebligat_sonucu"));
				list.add(tebligat);
			}
			disconnectDB();
		} catch (Exception e) {
			System.out.println("TebligatDAO.liste : " + e.getMessage());
		}

		return list;
	}

	@Override
	public int getId(Tebligat t) {
		// TODO Auto-generated method stub
		return 0;
	}

	public List getFilteredListFromDB(int icraDosyaId) {
		String sql = "SELECT i.id, i.icra_dosyasi_no, i.icra_mudurlugu_id,  i.ekleme_tarihi,"
				+ " islem.barkod as barkod_odeme, mb.barkod as barkod_muamele" + ""
				+ ", md.adi as icra_md , t.id, t.icra_dosyasi_id, t.borclu_id, t.tebligat_turu_id, t.tebligat_statusu_id,"
				+ " ts.adi as tebligat_sonucu, tt.adi as tebligat_turu, t.tebligat_sonucu_id, "
				+ "b.ad_soyad as borclu_adi  FROM tbl_icra_dosyasi i "
				+ " inner join tbl_tebligat t on t.icra_dosyasi_id = i.id inner join tbl_tebligat_statusu ts on ts.id=t.tebligat_statusu_id "
				+ "inner join tbl_borclu b on t.borclu_id = b.id "
				+ "inner join tbl_tebligat_tipi  tt on tt.id = t.tebligat_turu_id"
				+ " left join tbl_islem islem on islem.icra_dosya_id = i.id "
				+ " left join tbl_muamele_bilgisi mb on i.id = mb.icra_dosyasi_id " + ""
				+ "inner join tbl_icra_mudurlugu md on i.icra_mudurlugu_id = md.id" + " where t.icra_dosyasi_id = "
				+ icraDosyaId;

		List<Tebligat> list = new ArrayList<>();
		try {
			newConnectDB();
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				Tebligat tebligat = new Tebligat();
				tebligat.setIcraDosyaId(rs.getInt("id"));
				tebligat.setIcraDosyaNo(rs.getString("icra_dosyasi_no"));
				if (rs.getString("barkod_odeme") != null && rs.getString("barkod_odeme").length() > 0) {
					BarcodeBuilder builder = new BarcodeBuilder(rs.getString("barkod_odeme"));
					tebligat.setBarkod(builder.getFullCode());
				}

				if (rs.getString("barkod_muamele") != null && rs.getString("barkod_muamele").length() > 0) {
					BarcodeBuilder builder2 = new BarcodeBuilder(rs.getString("barkod_muamele"));
					tebligat.setBarkod(builder2.getFullCode());
				}

				tebligat.setIcraMd(rs.getString("icra_md"));

				tebligat.setIcraDosyaId(rs.getInt("icra_dosyasi_id"));
				tebligat.setBorcluAdSoyad(rs.getString("borclu_adi"));
				tebligat.setBorcluId(rs.getInt("borclu_id"));
				tebligat.setTebligatTuruId(rs.getInt("tebligat_turu_id"));
				tebligat.setTebligatStatusuId(rs.getInt("tebligat_statusu_id"));
				tebligat.setTebligatTuruAdi(rs.getString("tebligat_turu"));
				tebligat.setTebligatSonucuId(rs.getInt("tebligat_sonucu_id"));
				tebligat.setTebligatStatusuAdi(rs.getString("tebligat_sonucu"));
				list.add(tebligat);
			}
			disconnectDB();

		} catch (Exception e) {
			System.out.println("TebligatDAO.getFilteredListFromMuamele :" + e.getMessage());
		}

		return list;
	}

}
