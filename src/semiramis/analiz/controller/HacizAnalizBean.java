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
import semiramis.analiz.model.HacizAnalizJSON;

@ManagedBean(name = "hacizAnalizBean", eager = true)
@SessionScoped
public class HacizAnalizBean {

	public static final int HACIZ_TAHSILAT_YAPILDI = 9;
	public static final int HACIZ_TAHSILAT_YAPILMADI = 10;

	private List<HacizAnaliz> liste;

	public HacizAnalizDAO dao;

	private String hacizStatusu;

	private String hacizGayrimenkulStatusu;

	private String hacizMaasStatusu;

	private String hacizAracStatusu;

	private String hacizTahsilat;

	private int id;

	public HacizAnalizBean() throws JSONException {
		// TODO Auto-generated constructor stub

		dao = new HacizAnalizDAO();

		liste = new ArrayList<HacizAnaliz>();

		liste = dao.getList(0);

		getJSON();

	}

	public void refresh() throws JSONException {
		HacizAnalizBean analiz = new HacizAnalizBean();
	}

	public void getJSON() throws JSONException {

		List<HacizAnalizJSON> liste = dao.getListJSON();

		JSONArray jsonArrayMenkul = new JSONArray();
		JSONArray jsonArrayGayrimenkul2 = new JSONArray();
		JSONArray jsonArrayMaas2 = new JSONArray();
		JSONArray jsonArrayArac2 = new JSONArray();

		for (int i = 0; i < liste.size(); i++) {

			if (liste.get(i).getHacizTuruId() == 4) // Menkul
			{
				JSONObject obje = new JSONObject();

				obje.put("id", liste.get(i).getHacizStatusuId());
				obje.put("durum", liste.get(i).getHacizStatusuAdi());
				obje.put("adet", liste.get(i).getToplam());
				obje.put("color", "#FF0F00");
				jsonArrayMenkul.put(obje);

			}

			else if (liste.get(i).getHacizTuruId() == 7) // Gayrimenkul
			{
				JSONObject obje = new JSONObject();

				obje.put("id", liste.get(i).getHacizStatusuId());
				obje.put("durum", liste.get(i).getHacizStatusuAdi());
				obje.put("adet", liste.get(i).getToplam());
				obje.put("color", "#FF0F00");
				jsonArrayGayrimenkul2.put(obje);

			}

			else if (liste.get(i).getHacizTuruId() == 5) // Maaş
			{
				JSONObject obje = new JSONObject();

				obje.put("id", liste.get(i).getHacizStatusuId());
				obje.put("durum", liste.get(i).getHacizStatusuAdi());
				obje.put("adet", liste.get(i).getToplam());
				obje.put("color", "#FF0F00");
				jsonArrayMaas2.put(obje);

			}

			else if (liste.get(i).getHacizTuruId() == 6) // Araç
			{

				JSONObject obje = new JSONObject();

				obje.put("id", liste.get(i).getHacizStatusuId());
				obje.put("durum", liste.get(i).getHacizStatusuAdi());
				obje.put("adet", liste.get(i).getToplam());
				obje.put("color", "#FF0F00");
				jsonArrayArac2.put(obje);

			}

		}

		hacizStatusu = jsonArrayMenkul.toString();

		hacizGayrimenkulStatusu = jsonArrayGayrimenkul2.toString();

		hacizMaasStatusu = jsonArrayMaas2.toString();

		hacizAracStatusu = jsonArrayArac2.toString();

	}

	public void getList() {
		liste = dao.getList(0);
		List<HacizAnaliz> liste2 = new ArrayList<HacizAnaliz>();
		for (int i = 0; i < liste.size(); i++) {
			if (id < 25) {
				if (liste.get(i).getHacizStatusuId() == id)
					liste2.add(liste.get(i));
			} else if (liste.get(i).getTahsilatMiktari() == 0 && id == 10)
				liste2.add(liste.get(i));
			else if (id == 9 && liste.get(i).getTahsilatMiktari() > 0)
				liste2.add(liste.get(i));

		}

		liste = liste2;

	}

	public String getHacizGayrimenkulStatusu() {
		return hacizGayrimenkulStatusu;
	}

	public void setHacizGayrimenkulStatusu(String hacizGayrimenkulStatusu) {
		this.hacizGayrimenkulStatusu = hacizGayrimenkulStatusu;
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

	public String getHacizMaasStatusu() {
		return hacizMaasStatusu;
	}

	public void setHacizMaasStatusu(String hacizMaasStatusu) {
		this.hacizMaasStatusu = hacizMaasStatusu;
	}

	public String getHacizAracStatusu() {
		return hacizAracStatusu;
	}

	public void setHacizAracStatusu(String hacizAracStatusu) {
		this.hacizAracStatusu = hacizAracStatusu;
	}

}
