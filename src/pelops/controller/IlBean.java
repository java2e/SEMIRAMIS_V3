package pelops.controller;

import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import pelops.dao.IlDAO;
import pelops.model.HacizBilgisi;
import pelops.model.HarcBilgisi;
import pelops.model.Il;

@ManagedBean(name="ilBean")
@SessionScoped
public class IlBean {

	private ArrayList<Il> ilListesiArrayList= new ArrayList<Il>();
	private Il secimIl= new Il();
	boolean panelRender;
	boolean buttonDisabled;
	private Il il;
	boolean cmbRender;
	boolean lblRender;
	boolean kaydetButtonRender;
	
	int status=0;
	


	public Il getSecimIl() {
		return secimIl;
	}

	public void setSecimIl(Il secimIl) {
		this.secimIl = secimIl;
	}

	public IlBean(){
		
		secimIl= new Il();
	}
	
	public ArrayList<Il> Liste() throws Exception{
		ilListesiArrayList= new ArrayList<Il>();
		IlDAO ildao = new IlDAO();
		ilListesiArrayList = ildao.Liste();
		return ilListesiArrayList;		
	}
	
	public void Kayit() throws Exception{
		
		IlDAO ildao = new IlDAO();

		FacesContext context = FacesContext.getCurrentInstance();
		
		if (status == 0) {

			boolean result = 	ildao.Kayit(secimIl);
		

			if (result) {
				context.addMessage(null, new FacesMessage("Kaydedildi!"));

			} else {

				context.addMessage(null, new FacesMessage("Kaydet başarısız!"));

			}
		} else {
			boolean duzenlendi = ildao.Guncelle(secimIl);
		

			if (duzenlendi) {
				context.addMessage(null, new FacesMessage("Düzenlendi!"));
			} else {
				context.addMessage(null, new FacesMessage("Güncelleme işlemi başarısız!"));
			}
			status = 0;
		}

		ilListesiArrayList = ildao.Liste();

		PanelClose();
		ButtonOpen();
		
		
		
	
	
		ilListesiArrayList= new ArrayList<Il>();
		ilListesiArrayList = ildao.Liste();
		
		this.panelRender = false;
		ButtonOpen();
	}
	
	public void Sil() throws Exception{
		IlDAO ildao = new IlDAO();
		int id=Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("sil").toString());
		ildao.Sil(id);
		ilListesiArrayList= new ArrayList<Il>();
		ilListesiArrayList = ildao.Liste();
	}
	
	public void Duzenle() throws Exception{
			status = 1;

			IlDAO ildao = new IlDAO();
			ilListesiArrayList= new ArrayList<Il>();
			ArrayList<Il> list = ildao.Liste();

			int id = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
					.get("id").toString());
			

			for (Il hem : list) {
				if (hem.getId() == id) {
					il = hem;
				}
			}
			secimIl=il;
			PanelOpen();
			ButtonClose();
		
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
		il = new Il();
		secimIl = new Il();
		PanelOpen();

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

	public Il getIl() {
		return il;
	}

	public void setIl(Il il) {
		this.il = il;
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
	
	
	

}
