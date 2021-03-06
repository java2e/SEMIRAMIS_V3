package pelops.controller;

import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import pelops.dao.MasrafDAO;
import pelops.dao.UtilDAO;
import pelops.db.DBConnection;
import pelops.model.GenelTanimSablon;
import pelops.model.MasrafBilgisi;
import pelops.users.User;
import pelops.util.Util;
import semiramis.operasyon.controller.Utils;

/**
 * @author Ozgen
 *
 */
@ManagedBean(name = "masrafBilgisiBean")
@SessionScoped
public class MasrafBilgisiBean {

	private MasrafBilgisi masrafBilgisi = new MasrafBilgisi();

	@SuppressWarnings("unused")
	private ArrayList<MasrafBilgisi> masrafBilListesi = new ArrayList<MasrafBilgisi>();

	private MasrafDAO dao = new MasrafDAO();

	@SuppressWarnings("unused")
	private String icradosyaNo;

	@SuppressWarnings("unused")
	private String borcluAdi;

	private String muvekkilAdi;

	private String icraMd;

	private boolean panelRender;

	private boolean buttonDisabled;

	private int status = 0;

	private String personelAdi;

	public MasrafBilgisi getMasrafBilgisi() {

		masrafBilgisi.setMuvekkilAdi(AktifBean.getMuvekkilAdi());

		masrafBilgisi.setIcra_dosyasi_no(AktifBean.getIcraDosyaNo());
		return masrafBilgisi;
	}

	public void setMasrafBilgisi(MasrafBilgisi masrafBilgisi) {
		this.masrafBilgisi = masrafBilgisi;
	}

	public MasrafBilgisiBean() {

		try {
			masrafBilListesi = dao.getAllListFromIcraDosyaID(AktifBean.icraDosyaID);
			HttpSession session = Util.getSession();
			User user = (User) session.getAttribute("user");
			personelAdi = user.getUsrAdSoyad();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		PanelClose();
		ButtonOpen();
	}

	@SuppressWarnings("unused")
	public void Kaydet() throws Exception {
		HttpSession session = Util.getSession();
		User user = (User) session.getAttribute("user");
		FacesContext context = FacesContext.getCurrentInstance();

		if ((masrafBilgisi.getMasrafMiktari() == null) && masrafBilgisi.getMasrafTarihi() == null) {

			context.addMessage(null, new FacesMessage("Masraf miktarı ve masraf tarihi boş geçilemez!"));

		} else {

			if (masrafBilgisi.getMasrafAciklama() == "" && masrafBilgisi.getPersonelName() == null
					&& masrafBilgisi.getMasrafUygulamaAsamasiId() == 0 && masrafBilgisi.getMasrafTipiId() == 0) {
				context.addMessage(null, new FacesMessage("En az bir alanı doldurunuz"));
			} else {

				if (status == 0) {
					boolean result = false;
					masrafBilgisi.setMasrafPersonel_adi_id(user.getUsrId());
					if (masrafBilgisi.getMasrafMiktari() != 0) {
						result = dao.kaydet(masrafBilgisi);
					} else {
						context.addMessage(null, new FacesMessage("Lütfen masraf miktarını doldurunuz!"));
					}

					GenelTanimBean bean = new GenelTanimBean();
					ArrayList<GenelTanimSablon> a = bean.ListeGetir("tbl_uygulama_asamasi");

					if (result) {
						// Pop up a��lmas�n� sa�lar
						context.addMessage(null, new FacesMessage("Kaydedildi!"));
						masrafBilListesi = dao.getAllListFromIcraDosyaID(AktifBean.getIcraDosyaID());

					} else {

						context.addMessage(null, new FacesMessage("Kaydet i�lemi ba�ar�s�z!"));

					}
				} else {
					masrafBilgisi.setIcra_dosyasi_id(AktifBean.icraDosyaID);
					masrafBilgisi.setMasrafPersonel_adi_id(user.getUsrId());
					boolean duzenlendi = false;
					if (masrafBilgisi.getMasrafMiktari() != 0) {

						duzenlendi = dao.guncelle(masrafBilgisi);
					} else {
						context.addMessage(null, new FacesMessage("Lütfen masraf miktarını doldurunuz!"));
					}
					if (duzenlendi) {
						context.addMessage(null, new FacesMessage("Düzenlendi!"));
					} else {
						context.addMessage(null, new FacesMessage("Güncelleme işlemi başarısız!"));
					}
					status = 0;
					masrafBilListesi = dao.getAllListFromIcraDosyaID(AktifBean.icraDosyaID);
				}

				PanelClose();
				ButtonOpen();

			}

		}
	}

	public void Duzenle() throws Exception {

		status = 1;

		ArrayList<MasrafBilgisi> list = dao.getAllListFromIcraDosyaID(AktifBean.icraDosyaID);

		int id = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
				.get("duzenle").toString());

		for (MasrafBilgisi oe : list) {
			if (oe.getId() == id) {
				masrafBilgisi = oe;
			}
		}
		masrafBilListesi = dao.getAllListFromIcraDosyaID(AktifBean.icraDosyaID);
		PanelOpen();
		ButtonClose();

	}

	public void YeniKayit() {
		masrafBilgisi = new MasrafBilgisi();
		PanelOpen();

	}

	public void PanelOpen() {
		HttpSession session = Util.getSession();
		User user = (User) session.getAttribute("user");
		personelAdi = user.getUsrAdSoyad();
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

	public boolean isButtonDisabled() {
		return buttonDisabled;
	}

	public void setButtonDisabled(boolean buttonDisabled) {
		this.buttonDisabled = buttonDisabled;
	}

	public void Sil() throws Exception {

		FacesContext context = FacesContext.getCurrentInstance();

		int id = Integer.parseInt(
				FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("sil").toString());

		boolean result = dao.Sil(id);
		if (result) {

			context.addMessage(null, new FacesMessage("Silindi!"));
		} else {
			context.addMessage(null, new FacesMessage("Silme başarısız!"));
		}
		masrafBilListesi = dao.getAllListFromIcraDosyaID(AktifBean.icraDosyaID);

	}

	public String getIcradosyaNo() {
		return AktifBean.icraDosyaNo;
	}

	public void setIcradosyaNo(String icradosyaNo) {
		this.icradosyaNo = icradosyaNo;
	}

	public String getBorcluAdi() {
		return AktifBean.borcluAdi;
	}

	public void setBorcluAdi(String borcluAdi) {
		this.borcluAdi = borcluAdi;
	}

	public String getMuvekkilAdi() {
		return AktifBean.muvekkilAdi;
	}

	public void setMuvekkilAdi(String muvekkilAdi) {
		this.muvekkilAdi = muvekkilAdi;
	}

	public void dlgKaydet() throws Exception {
		Kaydet();
		RequestContext.getCurrentInstance().execute("PF('dialogWidget').show()");
	}

	public void dlgVazgec() {
		Vazgec();
		RequestContext.getCurrentInstance().execute("PF('dialogWidget').show()");
	}

	public void dlgPanelOpen() {
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

	public void dlgYeniKayit() {
		YeniKayit();
		RequestContext.getCurrentInstance().execute("PF('dialogWidget').show()");
	}

	public String getIcraMd() {
		return UtilDAO.getInstance().getIcraMdwithID(AktifBean.icraDosyaID);
	}

	public void setIcraMd(String icraMd) {
		this.icraMd = icraMd;
	}

	public boolean isPanelRender() {
		return panelRender;
	}

	public void setPanelRender(boolean panelRender) {
		this.panelRender = panelRender;
	}

	public void Vazgec() {
		status = 0;
		PanelClose();
		ButtonOpen();

	}

	public ArrayList<MasrafBilgisi> getMasrafBilListesi() {
		return masrafBilListesi;
	}

	public void setMasrafBilListesi(ArrayList<MasrafBilgisi> masrafBilListesi) {
		this.masrafBilListesi = masrafBilListesi;
	}

	public String getPersonelAdi() {
		return personelAdi;
	}

	public void setPersonelAdi(String personelAdi) {
		this.personelAdi = personelAdi;
	}

}
