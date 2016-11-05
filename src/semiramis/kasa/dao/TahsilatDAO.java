package semiramis.kasa.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import pelops.db.DBConnection;
import semimis.utils.ConvertDate;
import semimis.utils.IDAO;
import semiramis.kasa.model.Tahsilat;
import semiramis.kasa.model.TahsilatRapor;

public class TahsilatDAO extends DBConnection implements IDAO<Tahsilat> {
	
	
	public ConvertDate convertDate;
	
	public List<TahsilatRapor> getAylik()
	{
		
		convertDate=new ConvertDate();
		
		List<TahsilatRapor> liste=null;
		
		try {
			
			
			String basTarih="01-"+convertDate.getAy()+"-"+convertDate.getYil();
			String bitTarih="31-"+convertDate.getAy()+"-"+convertDate.getYil();
			
			String sql="  SELECT  muvekkil_adi, sum(tahsilat_miktari) as toplam  FROM tbl_tahsilat "
					+" where tahsilat_tarihi between '"+basTarih+"' and '"+bitTarih+"' group by muvekkil_adi ";
			
			newConnectDB();
			
			Statement stmt=conn.createStatement();
			
			ResultSet set=stmt.executeQuery(sql);
			
			liste=new ArrayList<TahsilatRapor>();
			
			while(set.next())
			{
				
				TahsilatRapor tr=new TahsilatRapor();
				
				tr.setMuvekkilAdi(set.getString("muvekkil_adi"));
				tr.setToplam(set.getDouble("toplam"));
				liste.add(tr);
			}
			
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		return liste;
	}
	
	
	public List<TahsilatRapor> getGunluk()
	{
		
		convertDate=new ConvertDate();
		
		List<TahsilatRapor> liste=null;
		
		try {
			
			
			String basTarih=convertDate.getNowDate();
			
			String sql="  SELECT  muvekkil_adi, sum(tahsilat_miktari) as toplam  FROM tbl_tahsilat "
					+" where tahsilat_tarihi='"+basTarih+"' group by muvekkil_adi ";
			
			newConnectDB();
			
			Statement stmt=conn.createStatement();
			
			ResultSet set=stmt.executeQuery(sql);
			
			liste=new ArrayList<TahsilatRapor>();
			
			while(set.next())
			{
				
				TahsilatRapor tr=new TahsilatRapor();
				
				tr.setMuvekkilAdi(set.getString("muvekkil_adi"));
				tr.setToplam(set.getDouble("toplam"));
				liste.add(tr);
			}
			
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		return liste;
	}

	@Override
	public boolean kaydet(Tahsilat t) {

		try {

			String sql = "INSERT INTO tbl_tahsilat( icra_dosyasi_id, muvekkil_adi,  "
					+ "  gelis_tarihi, borc_tipi,tahsilat_tarihi, tahsilat_miktari, durum,  "
					+ "  onaylayan_id, kasa_personel_id, izleme_id, vizit_id, odemeplani_id,  "
					+ "  soz_alan_personel_id, hitam_durum, borclu_id, icra_mudurluk_id,  "
					+ "  dosya_tipi_id, tahsilat_statusu_id, tahsilat_tipi_id) "
					+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,  ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
			
			newConnectDB();
			
			PreparedStatement stmt=conn.prepareStatement(sql);
			
			stmt.setInt(1, t.getIcraDosyaId());
			stmt.setString(2, t.getMuvekkilAdi());
			stmt.setDate(3, (Date) t.getGelisTarihi());
			stmt.setString(4, t.getBorcTipi());
			stmt.setDate(5, (Date) t.getTahsilatTarihi());
			stmt.setDouble(5, t.getTahsilatMiktari());
			stmt.setInt(6, t.getDurum());
			stmt.setInt(7, t.getOnaylayanID());
			stmt.setInt(8, t.getKasaPersonelID());
			stmt.setInt(9, t.getIzlemeId());
			stmt.setInt(10, t.getVizitId());
			stmt.setInt(11, t.getOdemePlaniId());
			stmt.setInt(12, t.getPersonelId());
			stmt.setInt(13, t.getHitamDurum());
			stmt.setInt(14, t.getBorcluId());
			stmt.setInt(15, t.getIcraMudurlukId());
			stmt.setInt(16, t.getDosyaTipiId());
			stmt.setInt(17, t.getTahsilatStatusuId());
			stmt.setInt(18, t.getTahsilatTipiId());
			
			
			stmt.executeUpdate();
			
			

		} catch (Exception e) 
		{
			
			System.out.println("Hata tahsilatDAO kaydet :"+e.getMessage());
			// TODO: handle exception
		}

		return false;
	}

	@Override
	public boolean guncelleme(Tahsilat t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean sil(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Tahsilat getT(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Tahsilat> liste(int id, int id2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getId(Tahsilat t) {
		// TODO Auto-generated method stub
		return 0;
	}

}
