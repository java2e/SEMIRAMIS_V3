package semiramis.operasyon.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import pelops.controller.AktifBean;
import semimis.utils.GenelArama;
import semiramis.operasyon.dao.TebligatDAO;
import semiramis.operasyon.model.ChronologyIdentifier;
import semiramis.operasyon.model.Tebligat;
import semiramis.tracking.classes.Checkpoint;
import semiramis.tracking.classes.Tracking;
import semiramis.tracking.main.TrackingUtil;

@ManagedBean(name = "tebligatBean")
@SessionScoped
public class TebligatBean {

	private Tebligat tebligat;

	public TebligatDAO dao;

	public int kaydet;

	private ArrayList<Tebligat> islems = new ArrayList<>();

	private List<Checkpoint> checkpoints = null;

	public void init() {
		// TODO Auto-generated constructor stub

		tebligat = new Tebligat();

		dao = new TebligatDAO();

		tebligat = dao.getT(AktifBean.icraDosyaID);

		// if (tebligat != null)
		// kaydet = 2;
		// else
		tebligat = new Tebligat();

		tebligat.setIcraDosyaId(AktifBean.icraDosyaID);
		tebligat.setIcraDosyaNo(AktifBean.icraDosyaNo);
		tebligat.setBorcluId(AktifBean.borcluId);
		islems = (ArrayList<Tebligat>) dao.liste(AktifBean.icraDosyaID, 0);
	}

	public TebligatBean() {
		init();
	}

	public void select() {
		int id = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
				.get("duzenle").toString());
		if (id > 0) {
			for (Tebligat t : islems) {
				if (t.getId() == id) {
					tebligat = t;
					kaydet = 2;
					tebligat.setIcraDosyaId(AktifBean.icraDosyaID);
					tebligat.setIcraDosyaNo(AktifBean.icraDosyaNo);
					tebligat.setBorcluId(AktifBean.borcluId);
				}
			}
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

		tebligat.setIcraDosyaId(AktifBean.icraDosyaID);
		tebligat.setIcraDosyaNo(AktifBean.icraDosyaNo);
		tebligat.setBorcluId(AktifBean.borcluId);
		init();
	}

	public void kaydet() {
		if (kaydet == 2)
			dao.guncelleme(tebligat);
		else
			dao.kaydet(tebligat);

		islems.clear();

		islems = (ArrayList<Tebligat>) dao.liste(AktifBean.icraDosyaID, 0);

		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "İşlem Sonucu!",
				"Tebligat Kaydı Başarıyla Yapılmıştır.");

		RequestContext.getCurrentInstance().showMessageInDialog(message);

		Utils utils = new Utils();
		String tebligatTuru = tebligat.getTebligatTuruAdi() != null ? tebligat.getTebligatTuruAdi()
				: dao.getTebligatStatusu(tebligat.getTebligatTuruId()).toUpperCase();
		String tebligatSonucu = tebligat.getTebligatStatusuId() == 1 ? " TEBLİĞ EDİLDİ " : "BİLA OLDU";
		utils.saveChronology(tebligat.getIcraDosyaId(), ChronologyIdentifier.ISLEM_TEBLIGAT,
				tebligatTuru + "  " + tebligatSonucu);

	}

	public void search() {
		// :TODO afterShip API entegrasyonu yapıldıktan sonra nu alanın tekrar
		// kodlanması gerekicek...
		Tracking tracking = TrackingUtil.getTrackingWithBarcode("4270031279928");
		checkpoints = tracking.getCheckpoints();
	}

	public ArrayList<Tebligat> getIslems() {
		return islems;
	}

	public void setIslems(ArrayList<Tebligat> islems) {
		this.islems = islems;
	}

	public Tebligat getTebligat() {
		return tebligat;
	}

	public void setTebligat(Tebligat tebligat) {
		this.tebligat = tebligat;
	}

	public List<Checkpoint> getCheckpoints() {
		return checkpoints;
	}

	public void setCheckpoints(List<Checkpoint> checkpoints) {
		this.checkpoints = checkpoints;
	}

}
