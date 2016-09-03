package semiramis.operasyon.controller;

import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import pelops.controller.AktifBean;
import pelops.db.DBConnection;
import pelops.model.*;
import pelops.users.User;
import pelops.util.Util;
import semiramis.operasyon.dao.HacizDAO;
import semiramis.operasyon.model.HacizBilgisi;

@ManagedBean(name = "hacizBilgisiBean")
@SessionScoped
public class HacizBilgisiBean extends DBConnection {

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

	private ArrayList<Tipi> hacizTipiList = new ArrayList<Tipi>();
	private ArrayList<String> list = new ArrayList<String>();

	private String personelAdi;

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

	public HacizBilgisiBean() throws Exception {
		User user = Util.getUser();
		personelAdi = user.getUsrAdSoyad();
		status = 0;
		
		hacizList = dao.getAllListFromIcraDosyaID(AktifBean.icraDosyaID);
		
		if (buttonDisabled == false) {
			PanelClose();
			ButtonOpen();

		}

	}

	public HacizBilgisi getHacizkayit() {
		return hacizkayit;
	}

	public void setHacizkayit(HacizBilgisi hacizkayit) {
		this.hacizkayit = hacizkayit;
	}

	public String getIcraDosyaNo() throws Exception {

		list = dao.getHaczeEsasMalBilgisiFromBorcluID(AktifBean.borcluId);
		if (list.size() > 0) {

		}
		return AktifBean.icraDosyaNo;

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

		return dao.getAllListFromIcraDosyaID(AktifBean.icraDosyaID);
	}

	public void setHacizList(ArrayList<HacizBilgisi> hacizList) {
		this.hacizList = hacizList;
	}

	public void YeniKayit() throws Exception {

		status = 0;
		hacizkayit = new HacizBilgisi();
		PanelOpen();

	}

	public void PanelOpen() throws Exception {
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

	public void PanelClose() {

		this.setPanelRender(false);

	}

	public void ButtonOpen() {

		this.setButtonDisabled(false);

	}

	public void ButtonClose() {

		this.setButtonDisabled(true);

	}

	@SuppressWarnings("unused")
	public void Kaydet() throws Exception {
		HttpSession session = Util.getSession();
		User user = (User) session.getAttribute("user");
		HacizDAO dao = new HacizDAO();
		int hesapID = dao.getHesapID(AktifBean.icraDosyaID);
		FacesContext context = FacesContext.getCurrentInstance();
		hacizkayit.setPersonelId(user.getUsrId());

		if (status == 0) {
			if (hacizkayit.getAciklama().equals("") || hacizkayit.getTeslimYeriId() == 0) {
				context.addMessage(null, new FacesMessage("Eksik alan doldurdunuz!"));
				;
			} else {

				boolean result = dao.kaydet(hacizkayit);
				// dao.hesapDuzenle(hesapID, 1, hacizkayit.getHacizBedeli());

				// dao.hesapDuzenle(hesapID, 2, hacizkayit.getHacizBedeli());
				if (result) {

					context.addMessage(null, new FacesMessage("Kaydedildi!"));
					PanelClose();
					ButtonOpen();

				} else {

					context.addMessage(null, new FacesMessage("Kaydet işlemi başarısız!!!"));

				}

			}

		}
		if (status == 1) {
			hacizkayit.setPersonelId(user.getUsrId());
			hacizkayit.setIcra_dosyasi_id(AktifBean.icraDosyaID);
			boolean duzenlendi = dao.guncelle(hacizkayit);

			if (duzenlendi) {

				context.addMessage(null, new FacesMessage("Düzenlendi!"));
				PanelClose();
				ButtonOpen();
			} else {

				context.addMessage(null, new FacesMessage("Güncelleme işlemi Başarısız!"));
			}

			status = 0;
			
		}
		
		hacizList = dao.getAllListFromIcraDosyaID(AktifBean.icraDosyaID);

	}

	public void Duzenle() throws Exception {

		status = 1;

		ArrayList<HacizBilgisi> list = dao.getAllListFromIcraDosyaID(AktifBean.icraDosyaID);

		int id = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
				.get("buttonDuzenle").toString());

		if (id != 0) {
			for (HacizBilgisi hem : list) {
				if (hem.getId() == id) {
					hacizkayit = hem;
				}
			}
		}

		hacizList = dao.getAllListFromIcraDosyaID(AktifBean.icraDosyaID);

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
		PanelClose();
		ButtonOpen();

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

}
