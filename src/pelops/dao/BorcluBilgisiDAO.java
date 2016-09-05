package pelops.dao;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.commons.httpclient.util.URIUtil;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONObject;

import pelops.controller.AktifBean;
import pelops.db.DBConnection;
import pelops.model.BorcluBilgisi;
import pelops.model.BorcluTipi;
import pelops.report.model.ReportUtils;

public class BorcluBilgisiDAO extends DBConnection {

	public ArrayList<BorcluTipi> borcluTipListe() throws Exception {

		newConnectDB();
		String SQL = "SELECT * FROM tbl_borc_tipi;";
		Statement stm;
		ResultSet rs;
		stm = conn.createStatement();
		rs = stm.executeQuery(SQL);
		BorcluTipi borcluTipi;
		ArrayList<BorcluTipi> tipListe = new ArrayList<BorcluTipi>();
		while (rs.next()) {

			borcluTipi = new BorcluTipi();
			borcluTipi.setId(rs.getInt("id"));
			borcluTipi.setAdi(rs.getString("adi"));
			tipListe.add(borcluTipi);

		}
		disconnectDB();

		return tipListe;
	}

	public BorcluBilgisi Liste(int id) throws Exception {
		DBConnection DB = new DBConnection();
		DB.newConnectDB();
		String SQL = "SELECT * FROM tbl_borclu where id= '" + id + "';";
		Statement stmt;
		ResultSet rs;
		stmt = DB.conn.createStatement();
		rs = stmt.executeQuery(SQL);
		BorcluBilgisi borclu = null;
		while (rs.next()) {
			borclu = new BorcluBilgisi();
			borclu.setAdres(rs.getString("adres"));
			borclu.setBorcluId(rs.getInt("id"));
			borclu.setTcNo(rs.getString("tc_no"));
			borclu.setIsYeriAdi(rs.getString("is_yeri_adi"));
			borclu.setIsYeriAdres(rs.getString("isyeri_adres"));
			borclu.setAdSoyad(rs.getString("ad_soyad"));
			borclu.setTelefonNo(rs.getInt("telefon_no"));
			borclu.setTelefon_no2(rs.getString("telefon_no2"));
			borclu.setTelefon_no3(rs.getString("telefon_no3"));
			borclu.setMusteriNo(rs.getString("musteri_no"));
			borclu.setUrunNo(rs.getString("urun_no"));
			borclu.setDogumTarihi(rs.getDate("dogum_tarihi"));

		}
		DB.disconnectDB();
		return borclu;
	}

	public int saveBorclu(BorcluBilgisi borcluBilgisi) throws Exception {

		int id = tcSorgulama(borcluBilgisi);

		if (id == 0) {
			newConnectDB();

			String SQL = "INSERT INTO tbl_borclu(tc_no, urun_no, ad_soyad, borclu_tip_id, ticaret_sicil_no,"
					+ " vergi_no, vergi_dairesi_id, ssk_isyeri_no, servis_no, "
					+ "musteri_no, talimat_icra_mud_id, email, web, \"not\", is_yeri_adi, departman,"
					+ " is_unvani, ssk_sicil_no, cinsiyet, baba_adi, ana_adi, dogum_yeri, "
					+ "dogum_tarihi, medeni_hali, il_adi, ilce_adi, mahalle, koy, cilt_no, "
					+ "sira_no, verildigi_yer, veris_nedeni,  vefat,"
					+ " resim, \"adressTuru\", ilce_id, il_id, adres, semt_adi, telefon_no,"
					+ " telefon_turu, verilis_tarihi, telefon_no2, telefon_no3, ad, soyad, uyap_rol, uyap_rol_id,isyeri_adres)"
					+ "  VALUES ( ?, ?, ?, ?, ?, " + " ?, ?, ?, ?, ?," + " ?, ?, ?, ?, ?, ?, " + " ?, ?, ?, ?, ?, ?, "
					+ " ?, ?, ?, ?, ?, ?, ?," + " ?, ?, ?, ?, ?, " + " ?, ?, ?, ?, ?, ?, ?, "
					+ " ?, ?, ?, ?, ?, ?, ?,?);";

			PreparedStatement pstm;

			pstm = conn.prepareStatement(SQL);

			pstm.setString(1, borcluBilgisi.getTcNo());
			pstm.setString(2, borcluBilgisi.getUrunNo());
			pstm.setString(3, borcluBilgisi.getAdSoyad());
			pstm.setInt(4, borcluBilgisi.getBorcluTipiId());
			pstm.setInt(5, borcluBilgisi.getTicaretSicilNo());
			pstm.setInt(6, borcluBilgisi.getVergiNo());
			pstm.setInt(7, borcluBilgisi.getVergiDairesiId());
			pstm.setInt(8, borcluBilgisi.getSskIsyeriNo());
			pstm.setInt(9, borcluBilgisi.getServisNo());
			pstm.setString(10, borcluBilgisi.getMusteriNo());
			pstm.setInt(11, borcluBilgisi.getTalimatIcraMudId());
			pstm.setString(12, borcluBilgisi.getEmail());
			pstm.setString(13, borcluBilgisi.getWebAdres());
			pstm.setString(14, borcluBilgisi.getNote());
			pstm.setString(15, borcluBilgisi.getIsYeriAdi());
			pstm.setString(16, borcluBilgisi.getDepartman());
			pstm.setString(17, borcluBilgisi.getIsUnvani());
			pstm.setString(18, borcluBilgisi.getSskSicilNo());
			pstm.setString(19, borcluBilgisi.getCinsiyet());
			pstm.setString(20, borcluBilgisi.getAnaAdi());
			pstm.setString(21, borcluBilgisi.getBabAdi());
			pstm.setString(22, borcluBilgisi.getDogumYeri());
			pstm.setDate(23, borcluBilgisi.getDogumTarihi());
			pstm.setString(24, borcluBilgisi.getMedeniHali());
			pstm.setString(25, borcluBilgisi.getIlAdi());
			pstm.setString(26, borcluBilgisi.getIlceAdi());
			pstm.setString(27, borcluBilgisi.getMahalle());
			pstm.setString(28, borcluBilgisi.getKoy());
			pstm.setInt(29, borcluBilgisi.getCiltNo());
			pstm.setInt(30, borcluBilgisi.getSiraNo());
			pstm.setString(31, borcluBilgisi.getVerilisYeri());
			pstm.setString(32, borcluBilgisi.getVerilisNedeni());
			pstm.setString(33, borcluBilgisi.getVefat());
			pstm.setString(34, borcluBilgisi.getResim());
			pstm.setInt(35, borcluBilgisi.getAdresTuruId());
			pstm.setInt(36, borcluBilgisi.getIlceId());
			pstm.setInt(37, borcluBilgisi.getIlId());
			pstm.setString(38, borcluBilgisi.getAdres());
			pstm.setString(39, borcluBilgisi.getSemtAdi());
			pstm.setInt(40, borcluBilgisi.getTelefonNo());
			pstm.setString(41, borcluBilgisi.getTelefon_no());
			pstm.setDate(42, borcluBilgisi.getVerilisTarihi());
			pstm.setString(43, borcluBilgisi.getTelefon_no1());
			pstm.setString(44, borcluBilgisi.getTelefon_no2());
			pstm.setString(45, borcluBilgisi.getAd());
			pstm.setString(46, borcluBilgisi.getSoyad());
			pstm.setString(47, borcluBilgisi.getUyap_rol());
			pstm.setString(48, borcluBilgisi.getUyap_rol_id());
			pstm.setString(49, borcluBilgisi.getIsYeriAdres());

			pstm.executeUpdate();
			disconnectDB();

			id = tcSorgulama(borcluBilgisi);

		}

		return id;

	}

	public void guncellemeMuhattapBilgisi(String isYeriAdi, String isYeriAdres, int borcluId) {

		try {

			String sql = "UPDATE tbl_borclu SET is_yeri_adi='" + isYeriAdi + "',isyeri_adres='" + isYeriAdres
					+ "' WHERE id=" + borcluId;

			newConnectDB();

			Statement stmt = conn.createStatement();

			stmt.execute(sql);

			disconnectDB();

		} catch (Exception e) {

			System.out.println("HATA borcluDAO guncellemeMuhattapBilgisi :" + e.getMessage());

		}

	}

	public BorcluBilgisi getBorcluBilgisi(String ad) throws Exception {
		String sql = "select * from tbl_borclu where ad_soyad = '" + ad + "';";

		BorcluBilgisi borclu = new BorcluBilgisi();
		newConnectDB();
		Statement stm = conn.createStatement();

		ResultSet rs = stm.executeQuery(sql);

		while (rs.next()) {

			borclu.setAdres(rs.getString("adres"));
			borclu.setTcNo(rs.getString("tc_no"));
			borclu.setAdSoyad(rs.getString("ad_soyad"));
			borclu.setTelefonNo(rs.getInt("telefon_no"));
			borclu.setTelefon_no(rs.getString("telefon_no2"));
			borclu.setTelefon_no1(rs.getString("telefon_no3"));
			borclu.setTelefon_no2(rs.getString("telefon_turu"));
			borclu.setMusteriNo(rs.getString("musteri_no"));
			borclu.setTcNo(rs.getString("tc_no"));
			borclu.setSskIsyeriNo(rs.getInt("ssk_isyeri_no"));
			borclu.setTalimatIcraMudId(rs.getInt("talimat_icra_mud_id"));
			borclu.setServisNo(rs.getInt("servis_no"));
			borclu.setVergiNo(rs.getInt("vergi_no"));
			borclu.setEmail(rs.getString("email"));
			borclu.setWebAdres(rs.getString("web"));
			borclu.setAdresTuruId(rs.getInt("adressTuru"));
			borclu.setIlAdi(rs.getString("il_adi"));
			borclu.setIlceAdi(rs.getString("ilce_adi"));
			borclu.setIsYeriAdi(rs.getString("is_yeri_adi"));
			borclu.setIsYeriAdres(rs.getString("isyeri_adres"));

		}
		disconnectDB();
		SehirlerDAO dao = new SehirlerDAO();
		String input = borclu.getIlAdi().toLowerCase();
		String output = input.substring(0, 1).toUpperCase() + input.substring(1);

		borclu.setIlId(dao.getIl_KoduFromName(output));

		return borclu;

	}

	public boolean updateBorclu(BorcluBilgisi borcluBilgisi) throws Exception {
		boolean guncelle = false;
		String SQL = "UPDATE tbl_borclu SET  urun_no=?, ad_soyad=?, borclu_tip_id=?,"
				+ " ticaret_sicil_no=?, vergi_no=?, vergi_dairesi_id=?, ssk_isyeri_no=?, servis_no=?, "
				+ " musteri_no=?, talimat_icra_mud_id=?, email=?, web=?, \"not\"=?, "
				+ " icra_dosyasi_id=?, \"adressTuru\"=?, ilce_id=?, il_id=?, adres=?, "
				+ "   semt_adi=?, telefon_no=?, telefon_turu=?, tc_no=?, telefon_no2=?, "
				+ "  telefon_no3=?,is_yeri_adi=?,isyeri_adres=?  WHERE id = ?;";

		PreparedStatement pstm;
		newConnectDB();

		pstm = conn.prepareStatement(SQL);

		pstm.setString(1, borcluBilgisi.getUrunNo());
		pstm.setString(2, borcluBilgisi.getAdSoyad());
		pstm.setInt(3, borcluBilgisi.getBorcluTipiId());
		pstm.setInt(4, borcluBilgisi.getTicaretSicilNo());
		pstm.setInt(5, borcluBilgisi.getVergiNo());
		pstm.setInt(6, borcluBilgisi.getVergiDairesiId());
		pstm.setInt(7, borcluBilgisi.getSskIsyeriNo());
		pstm.setInt(8, borcluBilgisi.getServisNo());
		pstm.setString(9, borcluBilgisi.getMusteriNo());
		pstm.setInt(10, borcluBilgisi.getTalimatIcraMudId());
		pstm.setString(11, borcluBilgisi.getEmail());
		pstm.setString(12, borcluBilgisi.getWebAdres());
		pstm.setString(13, borcluBilgisi.getNote());
		pstm.setInt(14, AktifBean.getIcraDosyaID());
		pstm.setInt(15, borcluBilgisi.getAdresTuruId());
		pstm.setInt(16, borcluBilgisi.getIlceId());
		pstm.setInt(17, borcluBilgisi.getIlId());
		pstm.setString(18, borcluBilgisi.getAdres());
		pstm.setString(19, borcluBilgisi.getSemtAdi());
		pstm.setInt(20, borcluBilgisi.getTelefonNo());
		pstm.setString(21, borcluBilgisi.getTelefon_no());
		pstm.setString(22, borcluBilgisi.getTcNo());
		pstm.setString(23, borcluBilgisi.getTelefon_no1());
		pstm.setString(24, borcluBilgisi.getTelefon_no2());
		pstm.setString(25, borcluBilgisi.getIsYeriAdi().toUpperCase());
		pstm.setString(26, borcluBilgisi.getIsYeriAdres().toUpperCase());
		pstm.setInt(27, AktifBean.getBorcluId());

		int result = pstm.executeUpdate();

		disconnectDB();
		if (result == 1) {
			guncelle = true;
		}

		return guncelle;

	}

	// :TODO borclu listesini cağıran bir method yazılmalı ve buna setX ve setY
	// eklenmeli
	public boolean uyapKimlikBilgisiGuncelle(BorcluBilgisi borcluBilgisi) throws Exception {
		boolean guncelle = false;
		// String SQL = "UPDATE tbl_borclu SET baba_adi=?, ana_adi=?,
		// dogum_yeri=?, dogum_tarihi=?, medeni_hali=?, il_adi=?,"
		// +" ilce_adi=?, mahalle=?, koy=?, cilt_no=?, sira_no=?,
		// verildigi_yer=?,"
		// +" veris_nedeni=?, verilis_tarihi=?, adres=?, semt_adi=? WHERE
		// tc_no=?;";

		String SQL = "UPDATE tbl_borclu SET  baba_adi=?, ana_adi=?, dogum_yeri=?, adres=?, il_adi=?  WHERE tc_no=?;";

		PreparedStatement pstm;
		newConnectDB();

		pstm = conn.prepareStatement(SQL);

		pstm.setString(1, borcluBilgisi.getBabAdi());
		pstm.setString(2, borcluBilgisi.getAnaAdi());
		pstm.setString(3, borcluBilgisi.getDogumYeri());

		// pstm.setDate(4, borcluBilgisi.getDogumTarihi());
		// pstm.setString(5, borcluBilgisi.getMedeniHali());

		// pstm.setString(7, borcluBilgisi.getIlceAdi());
		// pstm.setString(8, borcluBilgisi.getMahalle());
		// pstm.setString(9, borcluBilgisi.getKoy());
		// pstm.setInt(10, borcluBilgisi.getCiltNo());
		// pstm.setInt(11, borcluBilgisi.getSiraNo());
		// pstm.setString(12, borcluBilgisi.getVerilisYeri());
		// pstm.setString(13, borcluBilgisi.getVerilisNedeni());
		// pstm.setDate(14, borcluBilgisi.getVerilisTarihi());
		pstm.setString(4, borcluBilgisi.getAdres());
		pstm.setString(5, borcluBilgisi.getIlAdi());
		// pstm.setString(16, borcluBilgisi.getSemtAdi());
		pstm.setString(6, borcluBilgisi.getTcNo());

		System.out.println(borcluBilgisi.getTcNo());
		int result = pstm.executeUpdate();
		disconnectDB();

		// SQL = "select \"icradosyasiID\" from tbl_baglanti where \"borcluID\"
		// =" + tcSorgulama(borcluBilgisi.getTcNo())
		// + ";";
		// Statement statement = conn.createStatement();
		// ResultSet resultSet = statement.executeQuery(SQL);
		// int icraDosyaId = 0;
		// while (resultSet.next()) {
		// icraDosyaId = resultSet.getInt("borcluID");
		// ChronologyUtil.getInstance().insertInstance(
		// new Instance(icraDosyaId, null, "Uyap Güncelleme", "Uyap Borclu
		// Bilgisi Güncellendi", 2));
		// }

		disconnectDB();

		if (result == 1) {
			guncelle = true;
			saveCoordinate(tcSorgulama(borcluBilgisi.getTcNo()));

		}

		return guncelle;

	}

	public int tcSorgulama(BorcluBilgisi borcluBilgisi) throws Exception {

		newConnectDB();

		int id = 0;

		String sorgu = "SELECT id FROM tbl_borclu where tc_no = '" + borcluBilgisi.getTcNo() + "';";

		Statement stm = conn.createStatement();

		ResultSet rs = stm.executeQuery(sorgu);

		while (rs.next()) {

			id = rs.getInt("id");
		}

		disconnectDB();

		return id;

	}

	public int tcSorgulama(String tcno) throws Exception {

		newConnectDB();

		int id = 0;

		String sorgu = "SELECT id FROM tbl_borclu where tc_no = '" + tcno + "';";

		Statement stm = conn.createStatement();

		ResultSet rs = stm.executeQuery(sorgu);

		while (rs.next()) {

			id = rs.getInt("id");
		}

		disconnectDB();

		return id;

	}

	public ArrayList<Integer> getBorcluIDList() throws Exception {

		ArrayList<Integer> list = new ArrayList<Integer>();

		String sql = "select id from tbl_borclu;";

		newConnectDB();

		Statement stm = conn.createStatement();
		ResultSet rs = stm.executeQuery(sql);
		while (rs.next()) {

			int a = rs.getInt("id");

			list.add(a);
		}
		disconnectDB();
		return list;
	}

	public ArrayList<BorcluBilgisi> getBorcluList() throws Exception {

		ArrayList<BorcluBilgisi> list = new ArrayList<BorcluBilgisi>();

		String sql = "select * from tbl_borclu;";

		newConnectDB();

		Statement stm = conn.createStatement();
		ResultSet rs = stm.executeQuery(sql);
		while (rs.next()) {
			BorcluBilgisi borcluBilgisi = new BorcluBilgisi();
			borcluBilgisi.setId((rs.getInt("id")));
			borcluBilgisi.setAdSoyad(rs.getString("ad_soyad"));
			borcluBilgisi.setTelefon_no2(rs.getString("telefon_no2"));
			borcluBilgisi.setTelefon_no3(rs.getString("telefon_no3"));
			list.add(borcluBilgisi);

		}
		disconnectDB();
		return list;
	}

	public String getBocluAdress(int borcluId) {
		String adress = "";
		String sql = "SELECT  il_adi, adres FROM tbl_borclu where id = " + borcluId + ";";
		newConnectDB();
		try {
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				String il = rs.getString("il_adi") != null ? rs.getString("il_adi").toLowerCase().trim() : "";
				String defAdress = rs.getString("adres") != null ? rs.getString("adres").toLowerCase().trim() : "";
				if (defAdress.contains(il)) {
					if (defAdress.contains("dış")) {
						String a = defAdress;
						String b = defAdress;
						defAdress = a.substring(0, a.indexOf("dış")) + " " + b.substring(b.indexOf(il));
					}
					adress = defAdress;
				} else {
					adress = defAdress + " " + il;
					String a = adress;
					String b = adress;
					if (adress.contains("dış")) {
						adress = a.substring(0, a.indexOf("dış")) + " " + b.substring(b.indexOf(il));
					}
				}

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return adress;
	}

	private String getBorcluIlAdi(int borcluId) {
		String ilAdi = "";
		String sql = "SELECT  il_adi from tbl_borclu where id = " + borcluId + ";";
		newConnectDB();
		Statement statement;
		try {
			statement = conn.createStatement();

			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				String il = rs.getString("il_adi") != null ? rs.getString("il_adi").toLowerCase().trim() : "";
				if (il.equals("bulunamadı.") || il.equals("")) {
					ilAdi = "ankara";
				} else {
					String a = ReportUtils.extractNumber(il);
					if (a.length() > 0) {
						ilAdi = "ankara";
					} else {
						ilAdi = il;

					}
				}
			}
			disconnectDB();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ilAdi;
	}

	private JSONObject getGoogleApiResults(String adres) {
		URL url;
		JSONObject jsonObj = null;
		try {
			url = new URL("http://maps.googleapis.com/maps/api/geocode/json?address=" + URIUtil.encodeQuery(adres)
					+ "&sensor=true");

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output = "", full = "";
			while ((output = br.readLine()) != null) {
				full += output;
			}
			jsonObj = new JSONObject(full);
			if (jsonObj.get("status").equals("OK")) {

			} else {
				jsonObj = null;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObj;
	}

	public void saveCoordinate(int borcluId) {
		JSONObject jsonObj = getGoogleApiResults(getBocluAdress(borcluId));
		if (jsonObj == null) {
			jsonObj = getGoogleApiResults(getBorcluIlAdi(borcluId));
		}
		try {
			if (jsonObj == null) {
				return;
			}
			JSONArray array = jsonObj.getJSONArray("results");
			JSONObject jsonObject = array.getJSONObject(0);
			JSONObject location = jsonObject.getJSONObject("geometry").getJSONObject("location");
			String sql = "update tbl_borclu SET x=?, y=? where tc_no =?";
			newConnectDB();
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, location.getString("lng"));
			pstm.setString(2, location.getString("lat"));
			pstm.setInt(3, borcluId);
			pstm.executeUpdate();
			disconnectDB();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				disconnectDB();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
