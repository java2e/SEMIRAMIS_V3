package pelops.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import pelops.db.DBConnection;
import pelops.model.Definition;
import pelops.model.GenelTanimSablon;

public class DefinitionDAO extends DBConnection {

	private static DefinitionDAO dao;

	private TanimlarDAO tanimlarDAO = new TanimlarDAO();

	public static DefinitionDAO getInstance() {
		if (dao == null)
			dao = new DefinitionDAO();
		return dao;
	}

	public boolean kaydet(GenelTanimSablon definition, String dbAdi) {
		boolean st = false;
		if (definition.getParentId() == 0) {
			try {
				st = tanimlarDAO.Kayit(definition, dbAdi);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("DefinitionDAO.tanimlarDAO.kaydet : " + e.getMessage());
			}
		} else {
			String SQL = "INSERT INTO " + dbAdi + "(\"adi\", " + definition.getParentDBName() + "_id) VALUES ('"
					+ definition.getAdi() + "', " + definition.getParentId() + ")";

			newConnectDB();
			Statement stmt;
			try {
				stmt = conn.createStatement();
				st = stmt.execute(SQL);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			disconnectDB();

		}
		return st;
	}

	public ArrayList<GenelTanimSablon> getList(String DBAdi, String parentDbName) {
		ArrayList<GenelTanimSablon> SablonListesi = new ArrayList<GenelTanimSablon>();

		if (parentDbName == null) {
			try {
				SablonListesi = tanimlarDAO.Liste(DBAdi);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			String SQL = "SELECT * FROM " + DBAdi;
			Statement stmt;
			ResultSet rs;
			try {
				newConnectDB();
				stmt = conn.createStatement();
				rs = stmt.executeQuery(SQL);
				GenelTanimSablon Sablon;

				while (rs.next()) {
					Sablon = new GenelTanimSablon();
					Sablon.setAdi(rs.getString("adi"));
					Sablon.setId(rs.getInt("id"));
					Sablon.setParentId(rs.getInt(parentDbName + "_id"));
					Sablon.setParentDBName(parentDbName);
					SablonListesi.add(Sablon);
				}
				disconnectDB();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return SablonListesi;
	}

	public ArrayList<GenelTanimSablon> getSelectedList(String DBAdi, String parentName, int id) {
		ArrayList<GenelTanimSablon> SablonListesi = new ArrayList<GenelTanimSablon>();
		if (id == 0)
			id = 1;
		String SQL = "SELECT * FROM " + DBAdi + " where " + parentName + "_id = " + id;
		Statement stmt;
		ResultSet rs;
		try {
			newConnectDB();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(SQL);
			GenelTanimSablon Sablon;

			while (rs.next()) {
				Sablon = new GenelTanimSablon();
				Sablon.setAdi(rs.getString("adi"));
				Sablon.setId(rs.getInt("id"));
				Sablon.setParentId(rs.getInt(parentName + "_id"));
				Sablon.setParentDBName(parentName);
				SablonListesi.add(Sablon);
			}
			disconnectDB();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SablonListesi;
	}

	public boolean sil(int id, String DBAdi) {
		try {
			return tanimlarDAO.Sil(id, DBAdi);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public boolean duzenle(GenelTanimSablon Sablon, String DBAdi) {
		boolean rs = false;
		if (Sablon.getParentId() == 0) {
			try {
				rs = tanimlarDAO.Duzenle(Sablon, DBAdi);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			String SQL = "UPDATE " + DBAdi + " SET \"adi\"='" + Sablon.getAdi() + "', " + Sablon.getParentDBName()
					+ "_id = " + Sablon.getParentId() + "  WHERE \"id\"='" + Sablon.getId() + "'";
			newConnectDB();
			Statement stmt;
			try {
				stmt = conn.createStatement();
				rs = stmt.execute(SQL);
				disconnectDB();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return rs;
	}

}
