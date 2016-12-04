package semiramis.tanimlar.dao;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import pelops.db.DBConnection;
import semiramis.operasyon.model.ComboItem;

public class IcraMudurlukleriDAO extends DBConnection
{
	
	public List<ComboItem> getList()
	{
		List<ComboItem> items=null;
		
		try {
			
			String sql=" select id,icra_adi,icra_no from tbl_icra_mudurlugu where  durum=1 order by icra_no asc ";
			
			newConnectDB();
			
			Statement stmt=conn.createStatement();
			
			ResultSet set=stmt.executeQuery(sql);
			
			items=new ArrayList<>();
			
			while(set.next())
			{
				ComboItem item=new ComboItem();
				
				item.setId(set.getInt("id"));
				item.setAdi(set.getString("icra_adi")+" "+set.getInt("icra_no")+". İcra Müdürlüğü");
				
				items.add(item);
				
			}
			
			disconnectDB();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return items;
		
	}

}
