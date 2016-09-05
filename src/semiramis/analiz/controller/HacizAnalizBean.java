package semiramis.analiz.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;


import semiramis.analiz.dao.HacizAnalizDAO;
import semiramis.analiz.model.HacizAnaliz;

@ManagedBean(name="hacizAnalizBean",eager=true)
@SessionScoped
public class HacizAnalizBean
{
	
	public static final int HACIZ_TAHSILAT_YAPILDI=9;
	public static final int HACIZ_TAHSILAT_YAPILMADI=10;
	
	
	private List<HacizAnaliz> liste;
	
	public HacizAnalizDAO dao;
	
	private String hacizStatusu;
	
	private String hacizTahsilat;
	
	private int id;
	
	public HacizAnalizBean() throws JSONException {
		// TODO Auto-generated constructor stub
	
		dao=new HacizAnalizDAO();
		
		liste=new ArrayList<HacizAnaliz>();
		
		
		liste=dao.getList();
		
		getJSON();
	
	}
	
	
	public void refresh() throws JSONException
	{
		HacizAnalizBean analiz=new HacizAnalizBean();
	}
	
	
	public void getJSON() throws JSONException
	{
		
		int odemeSozu=0,adresdenTasinmis=0,haczeMalKabulYok=0,maasHacizMuvakafat=0,menkulHaciz=0,muhafazaHaciz=0,kefilAlindi=0,tahsilat=0;
		double alacak=0,tahsilatMiktari=0;
		
		for (int i = 0; i < liste.size(); i++) {
			
			if(liste.get(i).getHacizStatusuId()==1)
				odemeSozu++;
			else if(liste.get(i).getHacizStatusuId()==2)
				adresdenTasinmis++;
			else if(liste.get(i).getHacizStatusuId()==3)
				haczeMalKabulYok++;
			else if(liste.get(i).getHacizStatusuId()==8)
				maasHacizMuvakafat++;
			else if(liste.get(i).getHacizStatusuId()==4)
				menkulHaciz++;
			else if(liste.get(i).getHacizStatusuId()==5)
				muhafazaHaciz++;
			else if(liste.get(i).getHacizStatusuId()==6)
				kefilAlindi++;
			else if(liste.get(i).getHacizStatusuId()==7)
				tahsilat++;
			
			alacak=alacak+liste.get(i).getToplamAlacak();
			
			tahsilatMiktari=tahsilatMiktari+liste.get(i).getTahsilatMiktari();
			
		}


			JSONArray objeMain=new JSONArray();
			JSONObject obje=new JSONObject();
			
			obje.put("id", (int)1);
			obje.put("durum", "ÖDEME SÖZÜ".toString());
			obje.put("adet", (int)odemeSozu);
			obje.put("color", "#FF0F00");
 			objeMain.put(obje);
			
			
			obje=new JSONObject();
			
			obje.put("id", 2);
			obje.put("durum", "ADRESDEN TAŞINMIŞ");
			obje.put("adet", adresdenTasinmis);
			obje.put("color", "#FF6600");

			
			objeMain.put(obje);
			
			obje=new JSONObject();
			
			obje.put("id", 3);
			obje.put("durum", "HACZE KABİL MAL YOK");
			obje.put("adet", haczeMalKabulYok);
			obje.put("color", "#FF9E01");

			
			objeMain.put(obje);
			
			obje=new JSONObject();
			
			obje.put("id", 8);
			obje.put("durum", "MAAŞ HACİZ MUVAKAFAT");
			obje.put("adet", maasHacizMuvakafat);
			obje.put("color", "#FCD202");

			
			objeMain.put(obje);
			
			obje=new JSONObject();
			
			obje.put("id", 4);
			obje.put("durum", "HACİZ YEDİ EMİNLİ");
			obje.put("adet", menkulHaciz);
			obje.put("color", "#F8FF01");

			
			objeMain.put(obje);
			
			obje=new JSONObject();
			
			obje.put("id", 5);
			obje.put("durum", "MUHAZAFALI HACİZ");
			obje.put("adet", muhafazaHaciz);
			obje.put("color", "#B0DE09");

			
			objeMain.put(obje);
			
			obje=new JSONObject();
			
			obje.put("id", 6);
			obje.put("durum", "KEFİL ALINDI");
			obje.put("adet", kefilAlindi);
			obje.put("color", "#04D215");

			
			objeMain.put(obje);
			
			obje=new JSONObject();
			
			obje.put("id", 7);
			obje.put("durum", "TAHSİLAT");
			obje.put("adet", tahsilat);
			obje.put("color", "#0D8ECF");

			
			objeMain.put(obje);
			
			JSONArray objeMain2=new JSONArray();
			
			
			obje=new JSONObject();
			
			obje.put("id", HACIZ_TAHSILAT_YAPILDI);
			obje.put("durum", "YAPILDI");
			obje.put("adet", tahsilatMiktari);
			
			objeMain2.put(obje);
			
			obje=new JSONObject();
			
			obje.put("id", HACIZ_TAHSILAT_YAPILMADI);
			obje.put("durum", "YAPILMADI");
			obje.put("adet", alacak-tahsilatMiktari);
			
			objeMain2.put(obje);
			
			hacizStatusu=objeMain.toString();
			
			hacizTahsilat=objeMain2.toString();
			
		
	}
	
	
	public void getList()
	{
		liste=dao.getList();
		List<HacizAnaliz> liste2=new ArrayList<HacizAnaliz>();
			for(int i=0;i<liste.size();i++)
			{
				if(id<9)
				{
					if(liste.get(i).getHacizStatusuId()==id)
						liste2.add(liste.get(i));
				}
				else
					if(liste.get(i).getTahsilatMiktari()==0 && id==10)
						liste2.add(liste.get(i));
					else if(id==9 && liste.get(i).getTahsilatMiktari()>0)
						liste2.add(liste.get(i));
						
			}
			
			
			
			liste=liste2;
		
		
		
	}
	
	
	
	

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getHacizTahsilat() {
		return hacizTahsilat;
	}


	public void setHacizTahsilat(String hacizTahsilat) {
		this.hacizTahsilat = hacizTahsilat;
	}


	public List<HacizAnaliz> getListe() {
		return liste;
	}

	public void setListe(List<HacizAnaliz> liste) {
		this.liste = liste;
	}

	public String getHacizStatusu() {
		return hacizStatusu;
	}

	public void setHacizStatusu(String hacizStatusu) {
		this.hacizStatusu = hacizStatusu;
	}
	
	
	

}
