package semiramis.analiz.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONObject;

import semiramis.analiz.dao.TebligatAnalizDAO;
import semiramis.analiz.model.TebligatAnaliz;
import semiramis.analiz.model.TebligatAnalizJSON;
import semiramis.report.util.ReportPublish;

@ManagedBean(name = "tebligatAnalizBean", eager = true)
@SessionScoped
public class TebligatAnalizBean {

	private List<TebligatAnaliz> liste;

	private String tebligatTuru;

	private String tebligatSonucu;

	private String tebligatStatusu;

	private int id;

	public TebligatAnalizDAO dao;
	private int icraMdId;
	private int muvekkilId;
	private Date date1;
	private Date date2;

	private ReportPublish publish = new ReportPublish();
	private static String TEBLIGAT_JASPER = "tebligat_analizi";

	public ArrayList<TebligatAnaliz> wholeList;

	public TebligatAnalizBean() {

		dao = new TebligatAnalizDAO();

		wholeList = dao.getWholeDataWithOptions(date1, date2, icraMdId, muvekkilId);

		try {
			getJSON();
		} catch (Exception e) {
			System.out.println("JSON Veri çekerken HATA getJSON :" + e.getMessage());
		}

	}

	public void getJSON() {

		List<TebligatAnalizJSON> listeTur = dao.getTebligatTuruJSON(icraMdId, muvekkilId, date1, date2);

		JSONArray jsonArrayTur = new JSONArray();
		JSONArray jsonArraySonuc = new JSONArray();
		JSONArray jsonArrayStatusu = new JSONArray();

		try {

			for (int i = 0; i < listeTur.size(); i++) {

				JSONObject obje = new JSONObject();

				obje.put("id", listeTur.get(i).getId());
				obje.put("durum", listeTur.get(i).getAdi());
				obje.put("adet", listeTur.get(i).getToplam());
				obje.put("color", "#FF0F00");
				jsonArrayTur.put(obje);

			}

			List<TebligatAnalizJSON> listeSonuc = dao.getTebligatSonucuJSON(icraMdId, muvekkilId, date1, date2);

			for (int i = 0; i < listeSonuc.size(); i++) {

				JSONObject obje = new JSONObject();

				obje.put("id", listeSonuc.get(i).getId());
				obje.put("durum", listeSonuc.get(i).getAdi());
				obje.put("adet", listeSonuc.get(i).getToplam());
				obje.put("color", "#FF0F00");
				jsonArraySonuc.put(obje);

			}

			List<TebligatAnalizJSON> listeStatusu = dao.getTebligatStatusuJSON(icraMdId, muvekkilId, date1, date2);

			for (int i = 0; i < listeStatusu.size(); i++) {

				JSONObject obje = new JSONObject();

				obje.put("id", listeStatusu.get(i).getId());
				obje.put("durum", listeStatusu.get(i).getAdi());
				obje.put("adet", listeStatusu.get(i).getToplam());
				obje.put("color", "#FF0F00");
				jsonArrayStatusu.put(obje);

			}
		} catch (Exception e) {
			System.out.println("TebligatAnalizBean.getJSON :" + e.getMessage());
		}

		tebligatSonucu = jsonArraySonuc.toString();
		tebligatStatusu = jsonArrayStatusu.toString();
		tebligatTuru = jsonArrayTur.toString();

	}

	public void search() {
		System.out.println(date1 + " " + date2 + " " + muvekkilId + " " + icraMdId);
		wholeList = dao.getWholeDataWithOptions(date1, date2, icraMdId, muvekkilId);

		try {
			getJSON();
		} catch (Exception e) {
			System.out.println("JSON Veri çekerken HATA getJSON :" + e.getMessage());
		}
	}

	public void getList() {

		wholeList = dao.getWholeDataWithOptions(date1, date2, icraMdId, muvekkilId);
		List<TebligatAnaliz> liste2 = new ArrayList<TebligatAnaliz>();

		for (int i = 0; i < wholeList.size(); i++) {
			if (id == wholeList.get(i).getTebligatStatusuId())
				liste2.add(wholeList.get(i));

		}

		wholeList = (ArrayList<TebligatAnaliz>) liste2;

	}

	public void getListSonuc() {

		wholeList = dao.getWholeDataWithOptions(date1, date2, icraMdId, muvekkilId);
		List<TebligatAnaliz> liste2 = new ArrayList<TebligatAnaliz>();

		for (int i = 0; i < wholeList.size(); i++) {
			if (id == wholeList.get(i).getTebligatSonucId())
				liste2.add(wholeList.get(i));

		}

		wholeList = (ArrayList<TebligatAnaliz>) liste2;

	}

	public void getListTur() {

		wholeList = dao.getWholeDataWithOptions(date1, date2, icraMdId, muvekkilId);
		List<TebligatAnaliz> liste2 = new ArrayList<TebligatAnaliz>();

		for (int i = 0; i < wholeList.size(); i++) {
			if (id == wholeList.get(i).getTebligatTurId())
				liste2.add(wholeList.get(i));

		}

		wholeList = (ArrayList<TebligatAnaliz>) liste2;

	}

	public void cancel() {
		date1 = null;
		date2 = null;
		muvekkilId = 0;
		icraMdId = 0;

	}

	public void printExcell() {

		if (wholeList.size() > 0) {
			publish.getReportXLS(wholeList, TEBLIGAT_JASPER);
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Liste Boş!"));
		}
	}

	public void printPDF() {
		if (wholeList.size() > 0) {
			publish.getReportPDF(wholeList, new HashMap<>(), TEBLIGAT_JASPER);
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Liste Boş!"));
		}
	}

	public ArrayList<TebligatAnaliz> getWholeList() {
		return wholeList;
	}

	public void setWholeList(ArrayList<TebligatAnaliz> wholeList) {
		this.wholeList = wholeList;
	}

	public List<TebligatAnaliz> getListe() {
		return liste;
	}

	public void setListe(List<TebligatAnaliz> liste) {
		this.liste = liste;
	}

	public String getTebligatTuru() {
		return tebligatTuru;
	}

	public void setTebligatTuru(String tebligatTuru) {
		this.tebligatTuru = tebligatTuru;
	}

	public String getTebligatSonucu() {
		return tebligatSonucu;
	}

	public void setTebligatSonucu(String tebligatSonucu) {
		this.tebligatSonucu = tebligatSonucu;
	}

	public String getTebligatStatusu() {
		return tebligatStatusu;
	}

	public void setTebligatStatusu(String tebligatStatusu) {
		this.tebligatStatusu = tebligatStatusu;
	}

	public int getIcraMdId() {
		return icraMdId;
	}

	public void setIcraMdId(int icraMdId) {
		this.icraMdId = icraMdId;
	}

	public int getMuvekkilId() {
		return muvekkilId;
	}

	public void setMuvekkilId(int muvekkilId) {
		this.muvekkilId = muvekkilId;
	}

	public Date getDate1() {
		return date1;
	}

	public void setDate1(Date date1) {
		this.date1 = date1;
	}

	public Date getDate2() {
		return date2;
	}

	public void setDate2(Date date2) {
		this.date2 = date2;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
