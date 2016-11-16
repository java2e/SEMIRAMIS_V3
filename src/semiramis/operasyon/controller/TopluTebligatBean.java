package semiramis.operasyon.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import semiramis.operasyon.dao.TopluTebligatDAO;
import semiramis.operasyon.model.Tebligat;
import semiramis.tracking.classes.Checkpoint;
import semiramis.tracking.classes.Tracking;
import semiramis.tracking.main.TrackingUtil;

@ManagedBean(name = "topluTebligatBean")
@SessionScoped
public class TopluTebligatBean {
	private int tebligatTuru = 0;
	private int icraMdId = 0;
	private Date date1;
	private Date date2;
	private TopluTebligatDAO dao = new TopluTebligatDAO();
	private ArrayList<Tebligat> tebligatList = new ArrayList<Tebligat>();
	private ArrayList<Tebligat> selectedTebligatList = new ArrayList<Tebligat>();
	private ArrayList<Tebligat> trackings = new ArrayList<>();

	private boolean renderTable = false;

	private boolean resultRender = false;

	public void search() {
		if (tebligatTuru == 0) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(null, "Lütfen Tebligat türünü seçiniz. "));

		} else {
			tebligatList = (ArrayList<Tebligat>) dao.getFilteredList(tebligatTuru, icraMdId, date1, date2);
			renderTable = true;
		}
	}

	public void cancel() {
		icraMdId = 0;
		tebligatTuru = 0;
		date1 = null;
		date2 = null;
		renderTable = false;
		resultRender = false;
	}

	public void searchDetailWithApi() {
		if (selectedTebligatList.size() > 0) {

			for (Tebligat tebligat : selectedTebligatList) {
				// :TODO afterShip API entegrasyonu yapıldıktan sonra nu alanın
				// tekrar
				// kodlanması gerekicek...
				Tracking tracking = TrackingUtil.getTrackingWithBarcode(tebligat.getBarkod());
				tebligat.setTracking(tracking);
				List<Checkpoint> checkpoints = tracking.getCheckpoints();
				if (checkpoints != null && checkpoints.size() > 0)
					tebligat.setLastCheckPoint(checkpoints.get(checkpoints.size() - 1));
				trackings.add(tebligat);
			}
			resultRender = true;
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(null, "Lütfen sorgulanacak Dosya/larıseçiniz.  "));
		}
	}

	public ArrayList<Tebligat> getTebligatList() {
		return tebligatList;
	}

	public void setTebligatList(ArrayList<Tebligat> tebligatList) {
		this.tebligatList = tebligatList;
	}

	public boolean isRenderTable() {
		return renderTable;
	}

	public void setRenderTable(boolean renderTable) {
		this.renderTable = renderTable;
	}

	public int getTebligatTuru() {
		return tebligatTuru;
	}

	public void setTebligatTuru(int tebligatTuru) {
		this.tebligatTuru = tebligatTuru;
	}

	public int getIcraMdId() {
		return icraMdId;
	}

	public void setIcraMdId(int icraMdId) {
		this.icraMdId = icraMdId;
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

	public ArrayList<Tebligat> getSelectedTebligatList() {
		return selectedTebligatList;
	}

	public void setSelectedTebligatList(ArrayList<Tebligat> selectedTebligatList) {
		this.selectedTebligatList = selectedTebligatList;
	}

	public ArrayList<Tebligat> getTrackings() {
		return trackings;
	}

	public void setTrackings(ArrayList<Tebligat> trackings) {
		this.trackings = trackings;
	}

	public boolean isResultRender() {
		return resultRender;
	}

	public void setResultRender(boolean resultRender) {
		this.resultRender = resultRender;
	}

}
