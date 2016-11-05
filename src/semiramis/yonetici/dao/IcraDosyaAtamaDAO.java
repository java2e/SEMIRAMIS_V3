package semiramis.yonetici.dao;

import java.sql.Statement;

import pelops.db.DBConnection;
import semimis.utils.GenelArama;

public class IcraDosyaAtamaDAO extends DBConnection
{
	
	
	public void guncelleme(GenelArama icraDosyaNo,int userID)
	{
		
		
		try {
			
			String sql="UPDATE tbl_icra_dosyasi SET  guncelleme_tarihi=now(), kullanici_id="+userID+" WHERE icra_dosyasi_no='"+icraDosyaNo.getIcraDosyaNo()+"'";
			
			newConnectDB();
			
			Statement stmt=conn.createStatement();
			
			stmt.execute(sql);
			
			disconnectDB();
			
		} catch (Exception e) {

			System.out.println("Hata icraDosyaAtama Guncelleme :"+e.getMessage());
			// TODO: handle exception
		}
		
		
	}

}
