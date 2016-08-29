package semiramis.operasyon.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.extensions.model.timeline.TimelineEvent;
import org.primefaces.extensions.model.timeline.TimelineModel;
import pelops.controller.AktifBean;
import pelops.report.model.ReportUtils;
import semiramis.operasyon.model.Chronology;
import semiramis.report.util.ReportPublish;

@ManagedBean
@ViewScoped
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
		// initialize();
	}

	// public void initialize() {
	// model = new TimelineModel();
	//
	// ArrayList<TimelineEvent> events = null;
	// try {
	// if (icraDosyaID == 0 && AktifBean.getIcraDosyaID() != 0) {
	// events = utils.getAllTimeLineEventsFromID(AktifBean.getIcraDosyaID());
	// chronologies = utils.getChronologyList(AktifBean.getIcraDosyaID());
	// } else {
	// events = utils.getAllTimeLineEventsFromID(icraDosyaID);
	// chronologies = utils.getChronologyList(icraDosyaID);
	//
	// }
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// if (events != null) {
	// if (events.size() > 0) {
	// for (TimelineEvent timelineEvent : events) {
	//
	// model.add(timelineEvent);
	// }
	// }
	//
	// }
	//
	// }

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
		if (icraDosyaID != 0) {
			if (chronologies.size() > 0) {
				publish.getReportXLS(chronologies, DOSYA_CRH_JASPER);
			}
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Lütfen yazdırmak istediğiniz icra dosyasını şeçiniz!"));
		}
	}

	public void printPDF() {
		if (icraDosyaID != 0) {
			if (chronologies.size() > 0) {
				publish.getReportPDF(chronologies, new HashMap<>(), DOSYA_CRH_JASPER);
			}
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Lütfen yazdırmak istediğiniz icra dosyasını şeçiniz!"));
		}
	}

}