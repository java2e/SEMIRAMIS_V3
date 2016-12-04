package semimis.utils;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import pelops.db.DBConnection;
import pelops.util.Util;
import semiramis.operasyon.controller.Utils;
import semiramis.operasyon.model.DosyaStatusu;

public class GenelAramaDAO extends DBConnection {

	

	@SuppressWarnings("rawtypes")
	public List<GenelArama> getListe(GenelArama gArama) {
		
		

		List<GenelArama> liste = null;

		try {

			String sql = " SELECT DISTINCT icr.id, icr.icra_dosyasi_no, alc.muvekkil_adi, " + "  icr.gelis_tarihi, "
					+ "  icr.hitam_tarihi, " + "  icr.takip_tarihi, izleme.id as izleme_id, "
					+ "  icmd.adi AS icra_mudurlugu,  icmd.id as icra_mudurluk_id,"
					+ "  usr.ad_soyad AS risk_yoneticisi, " + "  brc.ad_soyad AS borclu_adi,brc.id as borclu_id, " + "  brc.musteri_no, "
					+ "  brc.telefon_no, " + "  icr.avukat_sevis_no, " + "  icr.banka_servis_no, "
					+ "  ds.id AS dosya_statu_id, " + "  ds.adi AS dosya_statu_adi " + " FROM tbl_baglanti bg "
					+ "   LEFT JOIN tbl_icra_dosyasi icr ON icr.id = bg.icra_dosyasi_id "
					+ "   LEFT JOIN tbl_alacakli_bilgisi alc ON alc.id = bg.alacakli_id "
					+ "   LEFT JOIN tbl_borclu brc ON brc.id = bg.borclu_id "
					+ "   LEFT JOIN tbl_icra_mudurlugu icmd ON icmd.id = icr.icra_mudurlugu_id "
					+ "   LEFT JOIN tbl_kullanici usr ON usr.id = icr.risk_yoneticisi_id "
					+ "   LEFT JOIN tbl_dosya_statu ds ON ds.id = icr.dosya_statusu_id "
					+ "   LEFT JOIN tbl_izleme_bilgisi izleme ON izleme.icra_dosyasi_id = icr.id ";


			sql = sql + " where 1=1 ";
			
			
			if(Util.getUser().getUsrKullaniciTipi()!=1)
				sql=sql+" and icr.kullanici_id="+Util.getUser().getUsrId();
			
			
			if (gArama.getMuvekkilAdi() != "")
				sql = sql + " and alc.muvekkil_adi='" + gArama.getMuvekkilAdi()+"'";
			if (gArama.getBorcluAdi() != "")
				sql = sql + " and brc.ad_soyad=" + gArama.getBorcluAdi();
			if (gArama.getIcraDosyaNo() != "")
				sql = sql + " and icr.icra_dosyasi_no=" + gArama.getIcraDosyaNo() ;
			if (gArama.getIcraMudurlukId() != 0)
				sql = sql + " and  icmd.id =" + gArama.getIcraMudurlukId();
			if (gArama.getBankaServisNo() != "")
				sql = sql + " and icr.banka_servis_no='" + gArama.getBankaServisNo()+"'";
			if (gArama.getAvukatServisNo() != "")
				sql = sql + " and icr.avukat_sevis_no='" + gArama.getAvukatServisNo()+"'";
			if (gArama.getBankaMusteriNo() != 0)
				sql = sql + " and brc.musteri_no=" + gArama.getBankaMusteriNo();
			if (gArama.getGelisTarihi1() != null || gArama.getGelisTarihi2() != null)
				sql = sql + " and  icr.gelis_tarihi between '" + convertDateToString(gArama.getGelisTarihi1()) + "' and '" + convertDateToString(gArama.getGelisTarihi2()) + "'";
			if (gArama.getHitamTarihi1() != null || gArama.getHitamTarihi2() != null)
				sql = sql + " and  icr.hitam_tarihi between '" + convertDateToString(gArama.getHitamTarihi1()) + "' and '" + convertDateToString(gArama.getHitamTarihi2()) + "'";
			if (gArama.getTakipTarihi1() != null || gArama.getTakipTarihi2() != null)
				sql = sql + " and  icr.takip_tarihi between '" +convertDateToString(gArama.getTakipTarihi1())  + "' and '" + convertDateToString(gArama.getTakipTarihi2())  + "'";

			newConnectDB();

			Statement stmt = conn.createStatement();

			ResultSet set = stmt.executeQuery(sql);

			liste = new ArrayList<GenelArama>();
			
			HashMap<Integer, GenelArama> maplist=new HashMap<>();

			while (set.next()) {

				GenelArama genelArama = new GenelArama();

				genelArama.setIzleme_id(set.getInt("izleme_id"));
				genelArama.setId(set.getInt("id"));
				genelArama.setBorcluAdi(set.getString("borclu_adi"));
				genelArama.setBorcluId(set.getInt("borclu_id"));
				genelArama.setCepTel(set.getString("telefon_no"));
				genelArama.setGelisTarihi(set.getString("gelis_tarihi"));
				genelArama.setTakipTarihi(set.getString("takip_tarihi"));
				genelArama.setIcraDosyaNo(set.getString("icra_dosyasi_no"));
				genelArama.setIcraMudurlugu(set.getString("icra_mudurlugu"));
				genelArama.setMuvekkilAdi(set.getString("muvekkil_adi"));

				genelArama.setAvukatServisNo(set.getString("avukat_sevis_no"));

				genelArama.setBankaServisNo(set.getString("banka_servis_no"));

				genelArama.setDosyaStatuAdi(set.getString("dosya_statu_adi"));
				genelArama.setDosyaStatuId(set.getInt("dosya_statu_id"));

				if (genelArama.getDosyaStatuId() == DosyaStatusu.DERDEST_ID)
					genelArama.setRenk(DosyaStatusu.DERDEST_RENK);
				else if (genelArama.getDosyaStatuId() == DosyaStatusu.HITAM_ID)
					genelArama.setRenk(DosyaStatusu.HITAM_RENK);
				else if (genelArama.getDosyaStatuId() == DosyaStatusu.ITIRAZ_ID)
					genelArama.setRenk(DosyaStatusu.ITIRAZ_RENK);
				else if (genelArama.getDosyaStatuId() == DosyaStatusu.TEMLIK_ID)
					genelArama.setRenk(DosyaStatusu.TEMLIK_RENK);
				else if (genelArama.getDosyaStatuId() == DosyaStatusu.VEFAT_ID)
					genelArama.setRenk(DosyaStatusu.VEFAT_RENK);
				
				if(genelArama.getIzleme_id()>0)
					genelArama.setRenk(DosyaStatusu.IZLEME_RENK);
					

				maplist.put(genelArama.getId(), genelArama);
				

			}
			
			Iterator iter=maplist.entrySet().iterator();
			
			while (iter.hasNext()) {
				Map.Entry mEntry = (Map.Entry) iter.next();
				liste.add((GenelArama) mEntry.getValue());		
				}

		} catch (Exception e) {
			
			System.out.println("Hata GenelAramaDAO getList :"+e.getMessage());
			// TODO: handle exception
		}

		return liste;

	}

}
