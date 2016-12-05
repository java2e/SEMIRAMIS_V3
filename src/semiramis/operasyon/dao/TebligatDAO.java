package semiramis.operasyon.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import pelops.db.DBConnection;
import pelops.users.Util;
import semimis.utils.BarcodeBuilder;
import semimis.utils.IDAO;
import semiramis.operasyon.model.Tebligat;

public class TebligatDAO extends DBConnection implements IDAO<Tebligat> {

	@Override
	public boolean kaydet(Tebligat t) {

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

			stmt.execute();

			disconnectDB();

		} catch (Exception e) {

			System.out.println("Hata tebligatDAO KAYDET:" + e.getMessage());

		}

		return false;
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

			String sql = "SELECT t.*,s.adi as tebligat_statusu " + " FROM tbl_tebligat t "
					+ " left join tbl_tebligat_statusu s on t.tebligat_statusu_id = s.id " + " where icra_dosyasi_id="
					+ id;

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
				t.setGuncellemeTarihi(set.getString("guncelleme_zamani"));
				t.setTebligatStatusuAdi(set.getString("tebligat_statusu"));

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
		Tebligat t = null;
		List list = new ArrayList<Tebligat>();
		Set<String> map = new HashSet<>();
		try {

			String sql = "SELECT t.*,s.adi as tebligat_statusu , tb.adi as tebligat_turu ,"
					+ "islem.barkod as barkod_odeme, mb.barkod as barkod_muamele " + " FROM tbl_tebligat t "
					+ " left join tbl_islem islem on islem.icra_dosya_id = t.icra_dosyasi_id "
					+ " left join tbl_muamele_bilgisi mb on t.icra_dosyasi_id = mb.icra_dosyasi_id "
					+ " inner join tbl_tebligat_statusu s on t.tebligat_statusu_id = s.id"
					+ " left join tbl_tebligat_tipi tb on t.tebligat_turu_id = tb.id " + " where t.icra_dosyasi_id="
					+ id;

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
				t.setGuncellemeTarihi(set.getString("guncelleme_zamani"));
				t.setTebligatStatusuAdi(set.getString("tebligat_statusu"));
				t.setTebligatTuruAdi(set.getString("tebligat_turu"));
				t.setGuncellemeTarihi(set.getString("guncelleme_zamani"));
				if (t.getTebligatTuruId() == 5 && set.getString("barkod_odeme") != null) { // 5
																							// :
																							// Odeme
																							// Emri
					BarcodeBuilder builder = new BarcodeBuilder(set.getString("barkod_odeme"));
					t.setBarkod(builder.getFullCode());
				} else if (set.getString("barkod_muamele") != null) {
					BarcodeBuilder builder = new BarcodeBuilder(set.getString("barkod_muamele"));
					t.setBarkod(builder.getFullCode());
				}
				if (map.add(t.getBarkod() + t.getTebligatTuruId()))
					list.add(t);

			}

			disconnectDB();

		} catch (Exception e) {

			System.out.println("Hata tebligatDAOListe SELECT :" + e.getMessage());

			// TODO: handle exception
		} finally {
			try {
				disconnectDB();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return list;
	}

	@Override
	public int getId(Tebligat t) {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getTebligatStatusu(int id) {
		String status = "";
		try {
			String sql = "select adi from tbl_tebligat_tipi where id =" + id;
			newConnectDB();
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				status = rs.getString("adi");
			}
			disconnectDB();
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			disconnectDB();
		}
		return status;
	}

}
