package semiramis.operasyon.dao;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import pelops.db.DBConnection;
import semiramis.operasyon.model.HaczeEsasMalBilgisi;

public class LevhaDAO extends DBConnection {

	public List<HaczeEsasMalBilgisi> getAracList(int borcluId) {

		List<HaczeEsasMalBilgisi> liste = null;

		try {

			String sql = "SELECT id, borclu_id , arac_plaka_no, arac_aractipi,  muhatap_adi, muhatap_adresi, "
					+ " diger_bilgiler, mal_tutari, icra_dosyasi_id,   ekleme_tarihi "
					+ " FROM tbl_hacze_esas_mal_bilgisi where mal_tipi_id=3 and  borclu_id=" + borcluId;

			newConnectDB();

			Statement stmt = conn.createStatement();

			ResultSet set = stmt.executeQuery(sql);

			liste = new ArrayList<HaczeEsasMalBilgisi>();

			while (set.next()) {
				HaczeEsasMalBilgisi hm = new HaczeEsasMalBilgisi();

				hm.setBorcluId(set.getInt("borclu_id"));
				hm.setAracPlakaNo(set.getString("arac_plaka_no"));
				hm.setAracAracTipi(set.getString("arac_aractipi"));
				hm.setEklemeTarihi(set.getDate("ekleme_tarihi"));

				liste.add(hm);

			}
			
			disconnectDB();

		} catch (Exception e) {

			System.out.println("Hata LevhaDao :" + e.getMessage());
			// TODO: handle exception
		}

		return liste;

	}
	
	
	
	public List<HaczeEsasMalBilgisi> getTapuListe(int borcluId)
	{
		
		List<HaczeEsasMalBilgisi> liste=null;
		try {
			
			String sql="SELECT mb.id, mb.borclu_id, mb.menkul_bilgisi, mb.tapu_mahalle_adi, mb.tapu_parsel, "
     +"  mb.tapu_sayfa_no, mb.tapu_cilt_no, mb.ekleme_tarihi, il.il_adi,  "
      +" ilce.ilce_adi as ilce_adi, mulk_tipi_id, mb.mal_tipi_id, mb.guncelleyen_kullanici_id,  "
      +" mb.haciz_durum, mb.tapu_aciklama, mb.tapu_sicil_mudurluk, mb.tapu_ada "
+"  FROM tbl_hacze_esas_mal_bilgisi mb "
+"  inner join tbl_il il on il.id=mb.tapu_il_id "
+"  inner join tbl_ilce ilce on ilce.id=mb.tapu_ilce_id "
+"  where mal_tipi_id=1 and borclu_id="+borcluId;
			
			
			newConnectDB();
			
			Statement stmt=conn.createStatement();
			
			ResultSet set=stmt.executeQuery(sql);
			
			liste=new ArrayList<HaczeEsasMalBilgisi>();
			
			while(set.next())
			{
				HaczeEsasMalBilgisi hb=new HaczeEsasMalBilgisi();
				
				hb.setTapuAciklama(set.getString("tapu_aciklama"));
				hb.setTapuAda(set.getString("tapu_ada"));
				hb.setTapuCiltNo(set.getString("tapu_cilt_no"));
				hb.setTapuMahalleAdi(set.getString("tapu_mahalle_adi"));
				hb.setTapuParsel(set.getString("tapu_parsel"));
				hb.setTapuIl(set.getString("il_adi"));
				hb.setTapuIlce(set.getString("ilce_adi"));
				
				hb.setEklemeTarihi(set.getDate("ekleme_tarihi"));
				
				liste.add(hb);
				
			
			}
			
			disconnectDB();
			
			
		} catch (Exception e) 
		{
			
			
			System.out.println("Hata levhaDAO :"+e.getMessage());
			
			// TODO: handle exception
		}
		
		
		return liste;
		
	}
	
	
	

}
