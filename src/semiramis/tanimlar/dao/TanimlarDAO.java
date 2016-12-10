package semiramis.tanimlar.dao;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import pelops.db.DBConnection;
import semiramis.operasyon.model.ComboItem;

public class TanimlarDAO extends DBConnection
{
	
	

	public List<ComboItem> getTebligatTipi()
	{
		List<ComboItem> liste=null;

		
		try {
			
			String sql="Select * from tbl_tebligat_tipi ";
			
			newConnectDB();
			
			Statement stmt=conn.createStatement();
			
			ResultSet set=stmt.executeQuery(sql);
			
			 liste=new ArrayList<>();
			
			while(set.next())
			{
				ComboItem item=new ComboItem();
				
				item.setId(set.getInt("id"));
				item.setAdi(set.getString("adi"));
				liste.add(item);
			}
			
			disconnectDB();
			
			
		} catch (Exception e) {
			
			System.out.println("Hata tanimlarDAO tebligat tipi:"+e.getMessage());
			// TODO: handle exception
		}
		
		return liste;
		
	}

	
	
	public List<ComboItem> getTebligatStatusu()
	{
		List<ComboItem> liste=null;

		
		try {
			
			String sql="Select * from tbl_tebligat_statusu ";
			
			newConnectDB();
			
			Statement stmt=conn.createStatement();
			
			ResultSet set=stmt.executeQuery(sql);
			
			 liste=new ArrayList<>();
			
			while(set.next())
			{
				ComboItem item=new ComboItem();
				
				item.setId(set.getInt("id"));
				item.setAdi(set.getString("adi"));
				liste.add(item);
			}
			
			disconnectDB();
			
			
		} catch (Exception e) {
			
			System.out.println("Hata tanimlarDAO :"+e.getMessage());
			// TODO: handle exception
		}
		
		return liste;
		
	}

	
	public List<ComboItem> getTebligatSonucu()
	{
List<ComboItem> liste=null;

		
		try {
			
			String sql="Select * from tbl_tebligat_sonucu ";
			
			newConnectDB();
			
			Statement stmt=conn.createStatement();
			
			ResultSet set=stmt.executeQuery(sql);
			
			 liste=new ArrayList<>();
			
			while(set.next())
			{
				ComboItem item=new ComboItem();
				
				item.setId(set.getInt("id"));
				item.setAdi(set.getString("adi"));
				liste.add(item);
			}
			
			disconnectDB();
			
			
		} catch (Exception e) {
			
			System.out.println("Hata tanimlarDAO tebligat sonucu:"+e.getMessage());
			// TODO: handle exception
		}
		
		return liste;
		
		
	}

	
	
}
