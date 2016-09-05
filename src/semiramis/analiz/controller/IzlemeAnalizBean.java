package semiramis.analiz.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;

import semiramis.analiz.dao.IzlemeAnalizDAO;
import semiramis.analiz.model.IzlemeAnaliz;

@ManagedBean(name="izlemeAnalizBean",eager=true)
@SessionScoped
public class IzlemeAnalizBean 
{
	
	public static final int  CEVAPSIZ=1;
	public static final int  ODEME_SOZU=2;
	public static final int  ULASILMADI=3;
	public static final int  PROTOKOL=4;
	public static final int  VEFAT=5;
	public static final int  KULLANIM_DISI=6;
	
	private List<IzlemeAnaliz> liste;
	
	private String izlemeSonucu;
	
	private int id;
	
	public IzlemeAnalizDAO dao;
	
	public IzlemeAnalizBean() throws JSONException {
		
		
		liste=new ArrayList<IzlemeAnaliz>();
		
		dao=new IzlemeAnalizDAO();
		
		liste=dao.getList();
		
		getJSON();
		
		// TODO Auto-generated constructor stub
	}
	
	public void refresh() throws JSONException
	{
		IzlemeAnalizBean analiz=new IzlemeAnalizBean();
	}
	
	
	
	public void getJSON() throws JSONException
	{
		
		
		int cevapsiz=0,odemeSozu=0,ulasilmadi=0,protokol=0,vefat=0,kullanimDisi=0;
		
		for (int i = 0; i < liste.size(); i++) {
			
			
			if(liste.get(i).getIzlemeSonucuId()==CEVAPSIZ)
				cevapsiz++;
			else if(liste.get(i).getIzlemeSonucuId()==ODEME_SOZU)
				odemeSozu++;
			else if(liste.get(i).getIzlemeSonucuId()==ULASILMADI)
				ulasilmadi++;
			else if(liste.get(i).getIzlemeSonucuId()==PROTOKOL)
				protokol++;
			else if(liste.get(i).getIzlemeSonucuId()==VEFAT)
				vefat++;
			else if(liste.get(i).getIzlemeSonucuId()==KULLANIM_DISI)
				kullanimDisi++;
		}
		
		
		JSONArray objeMain=new JSONArray();
		JSONObject obje=new JSONObject();
		
		obje.put("id", CEVAPSIZ);
		obje.put("durum", "CEVAPSIZ".toString());
		obje.put("adet", cevapsiz);
		obje.put("color", "#FF0F00");
		objeMain.put(obje);
		
		
		obje=new JSONObject();
		
		obje.put("id", ODEME_SOZU);
		obje.put("durum", "ÖDEME SÖZÜ");
		obje.put("adet", odemeSozu);
		obje.put("color", "#FF6600");

		
		objeMain.put(obje);
		
		obje=new JSONObject();
		
		obje.put("id", ULASILMADI);
		obje.put("durum", "ULAŞILMADI");
		obje.put("adet", ulasilmadi);
		obje.put("color", "#FF9E01");

		
		objeMain.put(obje);
		
		obje=new JSONObject();
		
		obje.put("id", PROTOKOL);
		obje.put("durum", "PROTOKOL");
		obje.put("adet", protokol);
		obje.put("color", "#FCD202");

		
		objeMain.put(obje);
		
		obje=new JSONObject();
		
		obje.put("id", VEFAT);
		obje.put("durum", "VEFAT");
		obje.put("adet", vefat);
		obje.put("color", "#F8FF01");

		
		objeMain.put(obje);
		
		obje=new JSONObject();
		
		obje.put("id", KULLANIM_DISI);
		obje.put("durum", "KULLANIM DIŞI");
		obje.put("adet", kullanimDisi);
		obje.put("color", "#B0DE09");

		objeMain.put(obje);
		
		
		izlemeSonucu=objeMain.toString();
		
		
	}

	
	public void getList()
	{
		
		liste=dao.getList();
		List<IzlemeAnaliz> liste2=new ArrayList<IzlemeAnaliz>();
		
		for (int i = 0; i < liste.size(); i++) {
			
			if(id==liste.get(i).getIzlemeSonucuId())
				liste2.add(liste.get(i));
			
		}
		
		liste=liste2;
		
	}

	public List<IzlemeAnaliz> getListe() {
		return liste;
	}


	public void setListe(List<IzlemeAnaliz> liste) {
		this.liste = liste;
	}


	public String getIzlemeSonucu() {
		return izlemeSonucu;
	}


	public void setIzlemeSonucu(String izlemeSonucu) {
		this.izlemeSonucu = izlemeSonucu;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}
	
	
	
	

}
