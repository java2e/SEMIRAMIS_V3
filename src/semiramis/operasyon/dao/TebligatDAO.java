package semiramis.operasyon.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import pelops.db.DBConnection;
import pelops.users.Util;
import semimis.utils.IDAO;
import semiramis.operasyon.model.Tebligat;

public class TebligatDAO extends DBConnection implements IDAO<Tebligat> {

	@Override
	public boolean kaydet(Tebligat t) {

		try {

			String sql = "INSERT INTO tbl_tebligat( "
					+ "icra_dosyasi_id, borclu_id, tebligat_turu_id, tebligat_statusu_id,  "
					+ " tebligat_sonucu_id, guncelleyen_kullanici_id, guncelleme_zamani) " + " VALUES (?, ?, ?, ?, ?, ?,now()); ";

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

			String sql = "SELECT t.*,s.adi as tebligat_statusu "
					+" FROM tbl_tebligat t "
					+" left join tbl_tebligat_statusu s on t.tebligat_statusu_id = s.id "
					+" where icra_dosyasi_id=" + id;

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


		
		
		
		
		return null;
	}

	@Override
	public int getId(Tebligat t) {
		// TODO Auto-generated method stub
		return 0;
	}

}
