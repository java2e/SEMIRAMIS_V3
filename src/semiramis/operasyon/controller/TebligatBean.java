package semiramis.operasyon.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

import pelops.controller.AktifBean;
import semiramis.operasyon.dao.IslemDAO;
import semiramis.operasyon.dao.TebligatDAO;
import semiramis.operasyon.model.Islem;
import semiramis.operasyon.model.Tebligat;
import semiramis.tracking.classes.Checkpoint;
import semiramis.tracking.classes.Tracking;
import semiramis.tracking.main.TrackingUtil;

@ManagedBean(name = "tebligatBean", eager = true)
@RequestScoped
public class TebligatBean {

	private Tebligat tebligat;

	public TebligatDAO dao;

	public int kaydet = 1;

	private ArrayList<Islem> islems = new ArrayList<>();

	private List<Checkpoint> checkpoints = null;

	public TebligatBean() {
		// TODO Auto-generated constructor stub

		tebligat = new Tebligat();

		dao = new TebligatDAO();

		tebligat = dao.getT(AktifBean.icraDosyaID);

		if (tebligat != null)
			kaydet = 2;
		else
			tebligat = new Tebligat();

		tebligat.setIcraDosyaId(AktifBean.icraDosyaID);
		tebligat.setIcraDosyaNo(AktifBean.icraDosyaNo);
		tebligat.setBorcluId(AktifBean.borcluId);
		islems = IslemDAO.getInstance().getIslemByIcraDosyaId(AktifBean.icraDosyaID);
	}

	public void kaydet() {

		if (kaydet == 2)
			dao.guncelleme(tebligat);
		else
			dao.kaydet(tebligat);

	}

	public void search() {
		// :TODO afterShip API entegrasyonu yapıldıktan sonra nu alanın tekrar
		// kodlanması gerekicek...
		Tracking tracking = TrackingUtil.getTrackingWithBarcode("4270031279928");
		checkpoints = tracking.getCheckpoints();
	}

	public ArrayList<Islem> getIslems() {
		return islems;
	}

	public void setIslems(ArrayList<Islem> islems) {
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
