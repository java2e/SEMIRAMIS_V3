package semiramis.analiz.dao;

import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import pelops.db.DBConnection;
import semiramis.analiz.model.IzlemeAnaliz;

public class IzlemeAnalizDAO extends DBConnection{

	public List<IzlemeAnaliz> getList() {

		List<IzlemeAnaliz> liste = null;

		try {

			String sql = "SELECT ib.*,icra.icra_dosyasi_no,borclu.ad_soyad,tah.tahsilat_miktari,hesap.toplam_alacak, iso.adi as izleme_sonucu,kul.ad_soyad as kullanici "
					+ " FROM tbl_izleme_bilgisi ib "
					+ " inner join tbl_icra_dosyasi icra on icra.id=ib.icra_dosyasi_id "
					+ " inner join tbl_baglanti bag on bag.icra_dosyasi_id=ib.icra_dosyasi_id "
					+ " inner join tbl_borclu borclu on borclu.id=bag.borclu_id "
					+ " left join tbl_tahsilat tah on tah.icra_dosyasi_id=ib.icra_dosyasi_id "
					+ " inner join tbl_hesap hesap on bag.hesap_id=hesap.id "
					+ " inner join tbl_izleme_sonucu iso on iso.id=ib.izleme_sonucu_id "
					+ "  inner join tbl_kullanici kul on kul.id=ib.personel_id ";
			
			newConnectDB();
			
			Statement stmt=conn.createStatement();
			
			ResultSet set=stmt.executeQuery(sql);
			
			liste=new ArrayList<IzlemeAnaliz>();
			
			while(set.next())
			{
				
				IzlemeAnaliz iz=new IzlemeAnaliz();
				
				iz.setAciklama(set.getString("aciklama"));
				iz.setBorcluAdSoyad(set.getString("ad_soyad"));
				iz.setIcraDosyaId(set.getInt("icra_dosyasi_id"));
				iz.setIcraDosyaNo(set.getString("icra_dosyasi_no"));
				iz.setId(set.getInt("id"));
				iz.setIzlemeSonucuAdi(set.getString("izleme_sonucu"));
				iz.setIzlemeSonucuId(set.getInt("izleme_sonucu_id"));
				iz.setIzlemeTarihi(set.getString("izleme_tarihi"));
				iz.setTahsilatMiktari(Double.valueOf(new DecimalFormat("0.00").format(set.getDouble("tahsilat_miktari")).replace(",", ".")));
				iz.setToplamAlacak(Double.valueOf(new DecimalFormat("0.00").format(set.getDouble("toplam_alacak")).replace(",", ".")));
				iz.setVizitDurumu(set.getInt("vizit_durumu")==1 ? "EVET":"HAYIR");
				iz.setPersonelAdi(set.getString("kullanici"));
				
				liste.add(iz);
				
			}
			
			
			

		} catch (Exception e) {
			
			System.out.println("Hata izlemeanalizDAO getList :"+e.getMessage());
			// TODO: handle exception
		}
		
		return liste;

	}

}
