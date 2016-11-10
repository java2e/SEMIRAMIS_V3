package semiramis.operasyon.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import pelops.controller.AktifBean;
import pelops.db.DBConnection;
import pelops.model.*;
import pelops.users.User;
import pelops.util.Util;
import semimis.utils.GenelArama;
import semiramis.operasyon.dao.HacizDAO;
import semiramis.operasyon.model.ComboItem;
import semiramis.operasyon.model.HacizBilgisi;
import semiramis.report.util.ReportPublish;

@ManagedBean(name = "hacizBilgisiBean")
@SessionScoped
public class HacizBilgisiBean {

	private HacizBilgisi hacizkayit = new HacizBilgisi();
	private String icraDosyaNo = AktifBean.icraDosyaNo;
	private String muvekkilAdi = AktifBean.muvekkilAdi;
	private String borcluAdi = AktifBean.borcluAdi;
	private int status = 0;
	private HacizDAO dao = new HacizDAO();
	private boolean panelRender;
	private boolean buttonDisabled;

	// haciz tipi controller
	private boolean cmbRender;
	private boolean lblRender;
	private String bilgiNotu;
	private boolean kaydetButtonRender;

	private boolean selected = false;

	private static String HACIZBILGISIJASPER = "haciz_bilgisi";

	private ArrayList<Tipi> hacizTipiList = new ArrayList<Tipi>();
	private ArrayList<String> list = new ArrayList<String>();

	private List<ComboItem> ListhacizStatusu;

	private String personelAdi;

	private ReportPublish publish = new ReportPublish();

	public ArrayList<Tipi> getHacizTipiList() throws Exception {
		list = dao.getHaczeEsasMalBilgisiFromBorcluID(AktifBean.borcluId);
		hacizTipiList = dao.getHacizTipiList(list);

		if (hacizTipiList.size() == 0) {
			LblPanelOpen();
			bilgiNotu = "İşlem yaptığınız borclunun sistemde mal kaydi bulunamamistir!";

		} else {
			LblPanelClose();
		}

		return hacizTipiList;
	}

	public void LblPanelOpen() {
		this.setCmbRender(false);
		this.setLblRender(true);
		this.setKaydetButtonRender(false);
	}

	public void LblPanelClose() {
		this.setCmbRender(true);
		this.setLblRender(false);
		this.setKaydetButtonRender(true);
	}

	ArrayList<HacizBilgisi> hacizList = new ArrayList<HacizBilgisi>();

	private void init() {
		User user = Util.getUser();
		personelAdi = user.getUsrAdSoyad();
		status = 0;
		icraDosyaNo = AktifBean.icraDosyaNo;
		muvekkilAdi = AktifBean.muvekkilAdi;
		borcluAdi = AktifBean.borcluAdi;

		ListhacizStatusu = new ArrayList<>();
		if (selected)
			hacizList = dao.getAllListFromIcraDosyaID(AktifBean.icraDosyaID);
		else
			hacizList = dao.getAllHacizList();

		if (buttonDisabled == false) {
			PanelClose();
			ButtonOpen();

		}

	}

	public HacizBilgisiBean() {
		init();
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
		borcluAdi = genelArama.getBorcluAdi();
		selected = true;
		init();
	}

	public void changeStatusu() {

		ListhacizStatusu = dao.getHacizStatusu(hacizkayit.getHacizTuruId());

	}

	public String getIcraDosyaNo() {

		list = dao.getHaczeEsasMalBilgisiFromBorcluID(AktifBean.borcluId);
		if (list.size() > 0) {

		}
		return AktifBean.icraDosyaNo;

	}

	public void YeniKayit() {

		status = 0;
		hacizkayit = new HacizBilgisi();
		PanelOpen();

	}

	public void PanelOpen() {
		list = dao.getHaczeEsasMalBilgisiFromBorcluID(AktifBean.borcluId);
		User user = Util.getUser();
		personelAdi = user.getUsrAdSoyad();
		if (list.size() > 0) {
			LblPanelClose();
		} else {
			LblPanelOpen();
		}
		this.setPanelRender(true);
		ButtonClose();

	}

	@SuppressWarnings("unused")
	public void Kaydet() throws Exception {
		HttpSession session = Util.getSession();
		User user = (User) session.getAttribute("user");
		HacizDAO dao = new HacizDAO();
		int hesapID = dao.getHesapID(AktifBean.icraDosyaID);
		hacizkayit.setPersonelId(user.getUsrId());

		if (status == 0) {
			if (hacizkayit.getAciklama().equals("") || hacizkayit.getTeslimYeriId() == 0 || icraDosyaNo == null
					|| personelAdi == null || hacizkayit.getHacizBedeli() == 0) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Eksik alan doldurdunuz!"));

			} else {

				boolean result = dao.kaydet(hacizkayit);
				// dao.hesapDuzenle(hesapID, 1, hacizkayit.getHacizBedeli());

				// dao.hesapDuzenle(hesapID, 2, hacizkayit.getHacizBedeli());
				if (result) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Kaydedildi!"));
					PanelClose();
					ButtonOpen();

				} else {

					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Kaydet işlemi başarısız!!!"));

				}

			}

		}
		if (status == 1) {
			hacizkayit.setPersonelId(user.getUsrId());
			hacizkayit.setIcra_dosyasi_id(AktifBean.icraDosyaID);
			boolean duzenlendi = dao.guncelle(hacizkayit);
			if (duzenlendi) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Düzenlendi!"));
				PanelClose();
				ButtonOpen();
			} else {

				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Güncelleme işlemi Başarısız!"));
			}

			status = 0;

		}

		hacizList = dao.getAllHacizList();

	}

	public void Duzenle() {

		status = 1;

		ArrayList<HacizBilgisi> list = dao.getAllHacizList();

		int id = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
				.get("buttonDuzenle").toString());

		if (id != 0) {
			for (HacizBilgisi hem : list) {
				if (hem.getId() == id) {
					hacizkayit = hem;
					changeStatusu();
				}
			}
		}
		if (hacizkayit != null) {
			AktifBean.borcluId = hacizkayit.getBorcluId();
			AktifBean.icraDosyaID = hacizkayit.getIcra_dosyasi_id();
			AktifBean.borcluAdi = hacizkayit.getBorcluAdi();
			AktifBean.icraDosyaNo = hacizkayit.getIcraDosyaNo();
			AktifBean.muvekkilAdi = hacizkayit.getAlacakli();
		}
		hacizList = dao.getAllListFromIcraDosyaID(AktifBean.getIcraDosyaID());

		PanelOpen();
		ButtonClose();

	}

	public void Sil() throws Exception {
		@SuppressWarnings("unused")
		int hesapID = dao.getHesapID(AktifBean.icraDosyaID);
		ArrayList<HacizBilgisi> list = dao.getAllListFromIcraDosyaID(AktifBean.icraDosyaID);

		int id = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
				.get("buttonSil").toString());

		if (id != 0) {
			for (HacizBilgisi hem : list) {
				if (hem.getId() == id) {
					hacizkayit = hem;
				}
			}
		}

		if (id != 0) {
			// dao.hesapDuzenle(hesapID, 3, hacizkayit.getHacizBedeli());
			dao.Sil(id);

		}
		status = 0;

	}

	public void Vazgec() {

		status = 0;
		hacizList = dao.getAllHacizList();
		PanelClose();
		ButtonOpen();

	}

	public boolean isKaydetButtonRender() {
		return kaydetButtonRender;
	}

	public void setKaydetButtonRender(boolean kaydetButtonRender) {
		this.kaydetButtonRender = kaydetButtonRender;
	}

	public boolean isLblRender() {
		return lblRender;
	}

	public void setLblRender(boolean lblRender) {
		this.lblRender = lblRender;
	}

	public void setHacizTipiList(ArrayList<Tipi> hacizTipiList) {
		this.hacizTipiList = hacizTipiList;
	}

	public String getBilgiNotu() {
		return "İşlem yaptığınız borclunun sistemde mal kaydi bulunamamistir!";
	}

	public void setBilgiNotu(String bilgiNotu) {
		this.bilgiNotu = bilgiNotu;
	}

	public boolean isCmbRender() {
		return cmbRender;
	}

	public void setCmbRender(boolean cmbRender) {
		this.cmbRender = cmbRender;
	}

	public HacizBilgisi getHacizkayit() {
		return hacizkayit;
	}

	public void setHacizkayit(HacizBilgisi hacizkayit) {
		this.hacizkayit = hacizkayit;
	}

	public void setIcraDosyaNo(String icraDosyaNo) {
		this.icraDosyaNo = icraDosyaNo;
	}

	public String getMuvekkilAdi() {
		return AktifBean.muvekkilAdi;
	}

	public void setMuvekkilAdi(String muvekkilAdi) {
		this.muvekkilAdi = muvekkilAdi;
	}

	public String getBorcluAdi() {
		return AktifBean.borcluAdi;
	}

	public void setBorcluAdi(String borcluAdi) {
		this.borcluAdi = borcluAdi;
	}

	public boolean isPanelRender() {
		return panelRender;
	}

	public void setPanelRender(boolean panelRender) {
		this.panelRender = panelRender;
	}

	public boolean isButtonDisabled() {
		return buttonDisabled;
	}

	public void setButtonDisabled(boolean buttonDisabled) {
		this.buttonDisabled = buttonDisabled;
	}

	public ArrayList<HacizBilgisi> getHacizList() throws Exception {

		return this.hacizList;
	}

	public void setHacizList(ArrayList<HacizBilgisi> hacizList) {
		this.hacizList = hacizList;
	}

	public void PanelClose() {

		this.setPanelRender(false);

	}

	public void ButtonOpen() {

		this.setButtonDisabled(false);

	}

	public void ButtonClose() {

		this.setButtonDisabled(true);

	}

	public void printExcell() {
		if (AktifBean.icraDosyaID != 0) {

			if (hacizList.size() > 0) {
				publish.getReportXLS(hacizList, HACIZBILGISIJASPER);
			}
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Lütfen yazdırmak istediğiniz icra dosyasını şeçiniz!"));
		}
	}

	public void printPDF() {
		if (AktifBean.icraDosyaID != 0) {
			if (hacizList.size() > 0) {
				publish.getReportPDF(hacizList, new HashMap<>(), HACIZBILGISIJASPER);
			}
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Lütfen yazdırmak istediğiniz icra dosyasını şeçiniz!"));
		}
	}

	public void dlgKaydet() throws Exception {
		Kaydet();
		RequestContext.getCurrentInstance().execute("PF('dialogWidget').show()");
	}

	public void dlgVazgec() {
		Vazgec();
		RequestContext.getCurrentInstance().execute("PF('dialogWidget').show()");
	}

	public void dlgPanelOpen() throws Exception {
		PanelOpen();
		RequestContext.getCurrentInstance().execute("PF('dialogWidget').show()");

	}

	public void dlgDuzenle() throws Exception {
		Duzenle();
		RequestContext.getCurrentInstance().execute("PF('dialogWidget').show()");
	}

	public void dlgSil() throws Exception {
		Sil();
		RequestContext.getCurrentInstance().execute("PF('dialogWidget').show()");
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getPersonelAdi() {
		return personelAdi;
	}

	public void setPersonelAdi(String personelAdi) {
		this.personelAdi = personelAdi;
	}

	public List<ComboItem> getListhacizStatusu() {
		return ListhacizStatusu;
	}

	public void setListhacizStatusu(List<ComboItem> listhacizStatusu) {
		ListhacizStatusu = listhacizStatusu;
	}

}
