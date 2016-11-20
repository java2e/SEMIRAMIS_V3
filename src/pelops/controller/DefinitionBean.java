package pelops.controller;

import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import pelops.dao.DefinitionDAO;
import pelops.dao.TanimlarDAO;
import pelops.model.GenelTanimSablon;

@ManagedBean(name = "definitionBean")
@SessionScoped
public class DefinitionBean {

	private ArrayList<GenelTanimSablon> sablonListesiArrayList = new ArrayList<GenelTanimSablon>();
	private GenelTanimSablon secimSablon = new GenelTanimSablon();
	boolean panelRender;
	boolean buttonDisabled;
	private GenelTanimSablon genelTanimSablon;
	private String parentDBName = null;

	boolean cmbRender;
	boolean lblRender;
	boolean kaydetButtonRender;

	int status = 0;

	public DefinitionBean() {

		secimSablon = new GenelTanimSablon();
	}

	public ArrayList<GenelTanimSablon> getList(String dbAdi) throws Exception {
		sablonListesiArrayList = new ArrayList<>();
		return sablonListesiArrayList;
	}

	public void subjectSelectionChanged(final AjaxBehaviorEvent event) {
		Integer value = (Integer) ((UIOutput) event.getSource()).getValue();
		// sablonListesiArrayList =
		// DefinitionDAO.getInstance().getSelectedList("tbl_izleme_statusu",
		// "izleme_sonucu",
		// value);
	}

	public void Kayit(String dbAdi, String parentName) throws Exception {

		FacesContext context = FacesContext.getCurrentInstance();
		this.parentDBName = parentName;
		secimSablon.setParentDBName(parentName);

		if (status == 0) {

			boolean result = DefinitionDAO.getInstance().kaydet(secimSablon, dbAdi);

			if (result) {
				context.addMessage(null, new FacesMessage("Kaydedildi!"));

			} else {

				context.addMessage(null, new FacesMessage("Kaydet başarısız!"));

			}
		} else {
			boolean duzenlendi = DefinitionDAO.getInstance().duzenle(secimSablon, dbAdi);

			if (duzenlendi) {
				context.addMessage(null, new FacesMessage("Düzenlendi!"));
			} else {
				context.addMessage(null, new FacesMessage("Güncelleme işlemi başarısız!"));
			}
			status = 0;
		}

		sablonListesiArrayList = DefinitionDAO.getInstance().getList(dbAdi, parentDBName);
		PanelClose();
		ButtonOpen();

		sablonListesiArrayList = new ArrayList<GenelTanimSablon>();
		sablonListesiArrayList = DefinitionDAO.getInstance().getList(dbAdi, parentDBName);

		this.panelRender = false;
		ButtonOpen();
	}

	public void Sil(String dbAdi) throws Exception {
		TanimlarDAO tanimlarDAO = new TanimlarDAO();
		int id = Integer.parseInt(
				FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("sil").toString());
		tanimlarDAO.Sil(id, dbAdi);
		sablonListesiArrayList = new ArrayList<>();
		sablonListesiArrayList = tanimlarDAO.Liste(dbAdi);
	}

	public void Duzenle(String dbAdi, String parentDbName) throws Exception {
		status = 1;
		int parentId = 0;
		sablonListesiArrayList = new ArrayList<GenelTanimSablon>();
		ArrayList<GenelTanimSablon> list = DefinitionDAO.getInstance().getList(dbAdi, parentDBName);
		System.out.println(
				FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("parentId"));
		int id = Integer.parseInt(
				FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id").toString());
		if (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("parentId") != null)
			parentId = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
					.get("parentId").toString());
		for (GenelTanimSablon hem : list) {
			if (hem.getId() == id) {
				genelTanimSablon = hem;
			}
		}
		secimSablon = genelTanimSablon;
		if (parentId != 0)
			secimSablon.setParentId(parentId);
		System.out.println(parentId);
		PanelOpen();
		ButtonClose();

	}

	public void getSelectedList(String DBadi, String parentName) {
		if (secimSablon.getParentId() != 0)
			sablonListesiArrayList = DefinitionDAO.getInstance().getSelectedList(DBadi, parentName,
					secimSablon.getParentId());

	}

	public void checkSablonUpdateParent() {
		if (secimSablon.getParentId() != 0) {
			parentDBName = secimSablon.getParentDBName();
		}
	}

	public void ButtonClose() {

		this.setButtonDisabled(true);

	}

	public void Vazgec() {
		status = 0;
		PanelClose();
		ButtonOpen();
	}

	public void YeniKayit() throws Exception {

		status = 0;
		if (secimSablon.getParentId() == 0) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Lütfen Yukarıdaki çoklu seçim alanından birini seçiniz!"));
			return;
		}
		int parentId = secimSablon.getParentId();
		secimSablon = new GenelTanimSablon();
		secimSablon.setParentId(parentId);
		PanelOpen();
		ButtonClose();

	}

	public void PanelOpen() throws Exception {
		this.setPanelRender(true);
	}

	public void LblPanelOpen() {
		this.setCmbRender(false);
		this.setLblRender(true);
		this.setKaydetButtonRender(false);
	}

	public void PanelClose() {

		this.setPanelRender(false);

	}

	public void ButtonOpen() {

		this.setButtonDisabled(false);

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

	public boolean isCmbRender() {
		return cmbRender;
	}

	public void setCmbRender(boolean cmbRender) {
		this.cmbRender = cmbRender;
	}

	public boolean isLblRender() {
		return lblRender;
	}

	public void setLblRender(boolean lblRender) {
		this.lblRender = lblRender;
	}

	public boolean isKaydetButtonRender() {
		return kaydetButtonRender;
	}

	public void setKaydetButtonRender(boolean kaydetButtonRender) {
		this.kaydetButtonRender = kaydetButtonRender;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public GenelTanimSablon getSecimSablon() {
		return secimSablon;
	}

	public void setSecimSablon(GenelTanimSablon secimSablon) {
		this.secimSablon = secimSablon;
	}

	public GenelTanimSablon getGenelTanimSablon() {
		return genelTanimSablon;
	}

	public void setGenelTanimSablon(GenelTanimSablon genelTanimSablon) {
		this.genelTanimSablon = genelTanimSablon;
	}

	public String getParentDBName() {
		return parentDBName;
	}

	public void setParentDBName(String parentDBName) {
		this.parentDBName = parentDBName;
	}

	public ArrayList<GenelTanimSablon> getSablonListesiArrayList() {
		return sablonListesiArrayList;
	}

	public void setSablonListesiArrayList(ArrayList<GenelTanimSablon> sablonListesiArrayList) {
		this.sablonListesiArrayList = sablonListesiArrayList;
	}

}
