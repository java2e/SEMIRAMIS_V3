package semiramis.operasyon.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.extensions.model.timeline.TimelineEvent;
import org.primefaces.extensions.model.timeline.TimelineModel;
import pelops.controller.AktifBean;
import pelops.report.model.ReportUtils;
import semimis.utils.GenelArama;
import semiramis.operasyon.model.Chronology;
import semiramis.report.util.ReportPublish;

@ManagedBean
@RequestScoped
public class BasicTimelineController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2799560844470830953L;

	private TimelineModel model;

	TimelineEvent event;

	public static String DOSYA_CRH_JASPER = "dosya_chronology";

	private String icraDosyaNo;
	private List<Chronology> chronologies = new ArrayList<>();

	private int icraDosyaID = 0;

	private ReportPublish publish = new ReportPublish();

	private Utils utils = new Utils();

	public BasicTimelineController() {
		initialize();
	}

	public void initialize() {
		model = new TimelineModel();

		try {
			if (icraDosyaID == 0 && AktifBean.getIcraDosyaID() != 0) {
				chronologies = utils.getChronologyList(AktifBean.getIcraDosyaID());
			} else {
				chronologies = utils.getChronologyList(icraDosyaID);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void chooseIcraDosyasi() {

		Map<String, Object> options = new HashMap<String, Object>();
		options.put("modal", true);
		options.put("contentWidth", 1800);
		RequestContext.getCurrentInstance().openDialog("dlg_genel_arama", options, null);

	}

	public void onIcraDosyasiChosen(SelectEvent event) {
		GenelArama genelArama = (GenelArama) event.getObject();
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Dosya Seçildi :",
				"Id:" + genelArama.getId());

		FacesContext.getCurrentInstance().addMessage(null, message);

		AktifBean.borcluId = genelArama.getBorcluId();
		AktifBean.icraDosyaID = genelArama.getId();
		AktifBean.borcluAdi = genelArama.getBorcluAdi();
		AktifBean.icraDosyaNo = genelArama.getIcraDosyaNo();
		AktifBean.muvekkilAdi = genelArama.getMuvekkilAdi();

		icraDosyaNo = genelArama.getIcraDosyaNo();

		initialize();
	}

	public void icraDosyaSec(int id) {
		RequestContext.getCurrentInstance().execute("PF('dlgdetayliarama').hide()");
		this.icraDosyaNo = AktifBean.getIcraDosyaNo();
		icraDosyaID = AktifBean.getIcraDosyaID();
		// initialize();
		chronologies = utils.getChronologyList(icraDosyaID);

	}

	public String getIcraDosyaNo() {
		return icraDosyaNo;
	}

	public void setIcraDosyaNo(String icraDosyaNo) {
		this.icraDosyaNo = icraDosyaNo;
	}

	public List<Chronology> getChronologies() {
		return chronologies;
	}

	public void setChronologies(List<Chronology> chronologies) {
		this.chronologies = chronologies;
	}

	public void printExcell() {
		if (AktifBean.icraDosyaID != 0) {
			if (chronologies.size() > 0) {
				publish.getReportXLS(chronologies, DOSYA_CRH_JASPER);
			}
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Lütfen yazdırmak istediğiniz icra dosyasını şeçiniz!"));
		}
	}

	public void printPDF() {
		if (AktifBean.icraDosyaID != 0) {
			if (chronologies.size() > 0) {
				publish.getReportPDF(chronologies, new HashMap<>(), DOSYA_CRH_JASPER);
			}
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Lütfen yazdırmak istediğiniz icra dosyasını şeçiniz!"));
		}
	}

}