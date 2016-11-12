package semiramis.operasyon.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import pelops.controller.AktifBean;
import semimis.utils.GenelArama;
import semiramis.operasyon.dao.HaczeEsasMalBilgisiDAO;
import semiramis.operasyon.model.ComboItem;
import semiramis.operasyon.model.HaczeEsasMalBilgisi;
import semiramis.operasyon.model.HaczeEsasMalBilgisiView;

@ManagedBean(name = "haczeEsasMalBilgisiBean2", eager = true)
@SessionScoped
public class HaczeEsasMalBilgisiBean {

	public final static int MAL_EV = 1;
	public final static int MAL_ARAC = 3;
	public final static int MAL_SGK = 2;

	private String icraDosyaNO;
	private String borcluAdi;
	private String muvekkliAdi;

	private boolean panelArac = false;
	private boolean panelTapu = false;
	private boolean panelMaas = false;

	HaczeEsasMalBilgisiDAO hczEsasMalDAO;

	private boolean updatedVisible;

	private HaczeEsasMalBilgisi updatedHczEsasMal;

	private List<HaczeEsasMalBilgisiView> filteredHczEsasMallar;

	private List<HaczeEsasMalBilgisiView> hczEsasMallar;

	private List<ComboItem> ilList;

	private List<ComboItem> ilceList;

	private int ilId;

	public int ilceId = 0;

	private int islem;

	public HaczeEsasMalBilgisiBean() {

		init();

	}

	public void init() {
		ilceList = new ArrayList<ComboItem>();
		ilList = new ArrayList<ComboItem>();

		updatedVisible = false;

		updatedHczEsasMal = new HaczeEsasMalBilgisi();

		hczEsasMalDAO = new HaczeEsasMalBilgisiDAO();

		hczEsasMallar = hczEsasMalDAO.listeView(AktifBean.getBorcluId());

		ilList = hczEsasMalDAO.getIlList();

		eklePanelAc();
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

		icraDosyaNO = genelArama.getIcraDosyaNo();
		borcluAdi = genelArama.getBorcluAdi();

		init();
	}

	public void changeIlceList() {
		ilceList = new ArrayList<ComboItem>();

		ilceList = hczEsasMalDAO.getIlceList(ilId);

	}

	public void changePanel() {

		if (updatedHczEsasMal.getMalTipiId() == MAL_EV) {
			panelArac = false;
			panelMaas = false;
			panelTapu = true;
			updatedVisible = true;
		} else if (updatedHczEsasMal.getMalTipiId() == MAL_ARAC) {
			panelArac = true;
			panelMaas = false;
			panelTapu = false;

			updatedVisible = true;
		} else {
			panelArac = false;
			panelMaas = true;
			panelTapu = false;

			updatedVisible = true;
		}

	}

	public String getIslem() {

		String butonText = "";

		if (islem == 1) {

			butonText = "Güncelle";
		} else {

			butonText = "Ekle";
		}
		return butonText;
	}

	public void iptal() {

		updatedVisible = false;
	}

	public void guncellePanelAc(int id) { // Tablodaki guncelle
		// butonuna tiklandiginda
		// giris panelini acar ve
		// componentleri doldurur.

		islem = 1;
		updatedVisible = true;
		updatedHczEsasMal = hczEsasMalDAO.getT(id);
		
		ilId=updatedHczEsasMal.getTapuIlId();
		
		changeIlceList();

		changePanel();

	}

	/*
	 * Ekle butonuna tiklandiginda girisPaneli acar ve componentler bos gelir
	 */
	public void eklePanelAc() {

		islem = 0;
		updatedHczEsasMal = new HaczeEsasMalBilgisi();
		updatedVisible = true;

		changePanel();
	}

	// sayfanin her acilisinda tum panellerin kapatilmasini saglayan metod
	public void panelKapat() {
		filteredHczEsasMallar = hczEsasMallar;
		updatedVisible = false;
	}

	public void kaydet() {
		
		int malTipi=0;

		if (islem != 1) {

			if (updatedHczEsasMal.getMalTipiId() == 0) {
				uyariMesaj("Mal Tipini seçiniz!");

			} else {
				if (updatedHczEsasMal.getMalTipiId() == MAL_SGK) {
					if ((updatedHczEsasMal.getMuhatapAdi() == "" || updatedHczEsasMal.getMuhatapAdresi() == ""))
						uyariMesaj("Muhattap Adresi ve (veya) Adini giriniz!");
					else {

						hczEsasMalDAO.kaydet(updatedHczEsasMal);
						
						malTipi=updatedHczEsasMal.getMalTipiId();

					}
				} 
				else if (updatedHczEsasMal.getMalTipiId() == MAL_ARAC) {

					if ((updatedHczEsasMal.getAracPlakaNo() == ""))
						uyariMesaj("Araç plakısını giriniz!");
					else {

						hczEsasMalDAO.kaydet(updatedHczEsasMal);

						malTipi=updatedHczEsasMal.getMalTipiId();

					}

				} else {

					if (ilId == 0 || updatedHczEsasMal.getTapuIlceId() == 0) {
						uyariMesaj("Tapu için il ve ilçe seçiniz!");

					} else {
						updatedHczEsasMal.setTapuIlId(ilId);

						hczEsasMalDAO.kaydet(updatedHczEsasMal);

						ilId = updatedHczEsasMal.getTapuIlId();

						ilceId = updatedHczEsasMal.getTapuIlceId();
						

						malTipi=updatedHczEsasMal.getMalTipiId();
						
					}

				}
			}

		} else {

			updatedHczEsasMal.setTapuIlId(ilId);

			hczEsasMalDAO.guncelleme(updatedHczEsasMal);

		}

		hczEsasMallar = new ArrayList<HaczeEsasMalBilgisiView>();

		hczEsasMallar = hczEsasMalDAO.listeView(AktifBean.getBorcluId());

		updatedHczEsasMal = new HaczeEsasMalBilgisi();
		
		updatedHczEsasMal.setMalTipiId(malTipi);

		updatedVisible = true;

	}

	public void uyariMesaj(String mesaj) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Unuttunuz!", mesaj);

		RequestContext.getCurrentInstance().showMessageInDialog(message);
	}

	public void sil(int id) {

		hczEsasMalDAO.sil(id);
		hczEsasMallar = hczEsasMalDAO.listeView(AktifBean.getBorcluId());

	}

	public String getIcraDosyaNO() {

		return AktifBean.getIcraDosyaNo();
	}

	public void setIcraDosyaNO(String icraDosyaNO) {
		this.icraDosyaNO = icraDosyaNO;
	}

	public String getBorcluAdi() {
		return AktifBean.getBorcluAdi();
	}

	public void setBorcluAdi(String borcluAdi) {
		this.borcluAdi = borcluAdi;
	}

	public String getMuvekkliAdi() {
		return AktifBean.getMuvekkilAdi();
	}

	public boolean isUpdatedVisible() {
		return updatedVisible;
	}

	public void setUpdatedVisible(boolean updatedVisible) {
		this.updatedVisible = updatedVisible;
	}

	public HaczeEsasMalBilgisi getUpdatedHczEsasMal() {
		return updatedHczEsasMal;
	}

	public void setUpdatedHczEsasMal(HaczeEsasMalBilgisi updatedHczEsasMal) {
		this.updatedHczEsasMal = updatedHczEsasMal;
	}

	public List<HaczeEsasMalBilgisiView> getFilteredHczEsasMallar() {
		return filteredHczEsasMallar;
	}

	public void setFilteredHczEsasMallar(List<HaczeEsasMalBilgisiView> filteredHczEsasMallar) {
		this.filteredHczEsasMallar = filteredHczEsasMallar;
	}

	public List<HaczeEsasMalBilgisiView> getHczEsasMallar() {
		return hczEsasMallar;
	}

	public void setHczEsasMallar(List<HaczeEsasMalBilgisiView> hczEsasMallar) {
		this.hczEsasMallar = hczEsasMallar;
	}

	public boolean isPanelArac() {
		return panelArac;
	}

	public void setPanelArac(boolean panelArac) {
		this.panelArac = panelArac;
	}

	public boolean isPanelTapu() {
		return panelTapu;
	}

	public void setPanelTapu(boolean panelTapu) {
		this.panelTapu = panelTapu;
	}

	public boolean isPanelMaas() {
		return panelMaas;
	}

	public void setPanelMaas(boolean panelMaas) {
		this.panelMaas = panelMaas;
	}

	public List<ComboItem> getIlList() {
		return ilList;
	}

	public void setIlList(List<ComboItem> ilList) {
		this.ilList = ilList;
	}

	public List<ComboItem> getIlceList() {
		return ilceList;
	}

	public void setIlceList(List<ComboItem> ilceList) {
		this.ilceList = ilceList;
	}

	public int getIlId() {
		return ilId;
	}

	public void setIlId(int ilId) {
		this.ilId = ilId;
	}

}
