package semiramis.operasyon.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import pelops.controller.AktifBean;
import pelops.dao.AlacakliDAO;
import pelops.users.UserDAO;
import semiramis.operasyon.dao.HedefDAO;
import semiramis.operasyon.model.Hedef;
import semiramis.report.util.ReportPublish;

@ManagedBean(name = "hedefbean")
@SessionScoped
public class HedefBean {

	private ArrayList<Hedef> hedefList = new ArrayList<>();
	private ArrayList<Hedef> filterhedefList = new ArrayList<>();
	HedefDAO dao = new HedefDAO();
	private Hedef hedef = new Hedef();
	private SelectItem[] YilListesi = new SelectItem[20];
	private SelectItem[] AyListesi = new SelectItem[] { new SelectItem("1", "OCAK"), new SelectItem("2", "ŞUBAT"),
			new SelectItem("3", "MART"), new SelectItem("4", "NİSAN"), new SelectItem("5", "MAYIS"),
			new SelectItem("6", "HAZİRAN"), new SelectItem("7", "TEMMUZ"), new SelectItem("8", "AĞUSTOS"),
			new SelectItem("9", "EYLÜL"), new SelectItem("10", "EKİM"), new SelectItem("11", "KASIM"),
			new SelectItem("12", "ARALIK") };

	private SimpleDateFormat yearsFormat = new SimpleDateFormat("yyyy");
	private SimpleDateFormat fullDateFormat = new SimpleDateFormat("dd-MM-yyyy");
	private Date nowDate = new Date();

	private boolean buttonDisabled = false;
	private ReportPublish publish = new ReportPublish();
	private static final String PERSONEL_HEDEF_JASPER = "personel_hedef";

	public HedefBean() {
		int yil = Integer.parseInt(yearsFormat.format(nowDate));

		for (int i = 0; i < 20; i++) {
			SelectItem TMP = new SelectItem((yil + i) + "", (yil + i) + "");
			YilListesi[i] = TMP;
		}
		hedef.setIlgili_yil(Integer.parseInt(yearsFormat.format(nowDate)));
		Listele();
	}

	public void Listele() {
		this.setHedefList(dao.Listele(hedef.getIlgili_yil(), hedef.getIlgili_ay()));
		this.setFilterhedefList(dao.Listele(hedef.getIlgili_yil(), hedef.getIlgili_ay()));
	}

	public void kaydet() {
		UserDAO usedao = new UserDAO();
		AlacakliDAO alacakli = new AlacakliDAO();

		hedef.setPersonel_adi(usedao.selectById(hedef.getPersonel_id()).getUsrAdSoyad());
		hedef.setMuvekkil_adi(alacakli.ListeGetir(hedef.getMuvekkil_id()).getMuvekkilAdi());

		dao.Kaydet(hedef);
		Listele();
		hedef = new Hedef();
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage("KAYIT İŞLEMİ !!!", new FacesMessage("Kayıt İşlemi Başarı ile Gerçekleşti."));

	}

	public void tarihListele() {
		this.setHedefList(dao.Listele(hedef.getIlgili_yil(), hedef.getIlgili_ay()));
		this.setFilterhedefList(dao.Listele(hedef.getIlgili_yil(), hedef.getIlgili_ay()));

	}

	public void duzenle(Hedef hedef) {
		this.hedef = hedef;
		buttonDisabled = true;
	}

	public void guncelle() {
		dao.Duzenle(hedef);
		Listele();
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Düzenleme İşlemi Başarı ile Gerçekleşti."));
		hedef = new Hedef();
		buttonDisabled = false;
	}

	public void sil(int id) {
		dao.Sil(id);
		Listele();
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Silme İşlemi Başarı ile Gerçekleşti."));
	}

	public void iptal() {
		hedef = new Hedef();
		buttonDisabled = false;
	}

	public void printExcell() {
		System.out.println(filterhedefList.get(0).getIlgili_yil_str()+ "burda");
		if (filterhedefList.size() > 0) {
			publish.getReportXLS(filterhedefList, PERSONEL_HEDEF_JASPER);
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Lütfen yazdırmak istediğiniz icra dosyasını şeçiniz!"));
		}
	}

	public void printPDF() {
		if (filterhedefList.size() > 0) {
			publish.getReportPDF(filterhedefList, new HashMap<>(), PERSONEL_HEDEF_JASPER);
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Lütfen yazdırmak istediğiniz icra dosyasını şeçiniz!"));
		}
	}

	public ArrayList<Hedef> getFilterhedefList() {
		return filterhedefList;
	}

	public void setFilterhedefList(ArrayList<Hedef> filterhedefList) {
		this.filterhedefList = filterhedefList;
	}

	public SelectItem[] getYilListesi() {
		return YilListesi;
	}

	public void setYilListesi(SelectItem[] yilListesi) {
		YilListesi = yilListesi;
	}

	public SelectItem[] getAyListesi() {
		return AyListesi;
	}

	public void setAyListesi(SelectItem[] ayListesi) {
		AyListesi = ayListesi;
	}

	public ArrayList<Hedef> getHedefList() {
		return hedefList;
	}

	public void setHedefList(ArrayList<Hedef> hedefList) {
		this.hedefList = hedefList;
		this.filterhedefList = hedefList;
	}

	public Hedef getHedef() {
		return hedef;
	}

	public void setHedef(Hedef hedef) {
		this.hedef = hedef;
	}

	public boolean isButtonDisabled() {
		return buttonDisabled;
	}

	public void setButtonDisabled(boolean buttonDisabled) {
		this.buttonDisabled = buttonDisabled;
	}

}
