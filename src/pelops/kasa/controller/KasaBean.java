package pelops.kasa.controller;

import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.time.DateUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import pelops.controller.AktifBean;
import pelops.controller.GenelTanimBean;
import pelops.controller.IcraDosyaIslemleriBean;
import pelops.dao.GelismisAramaDAO;
import pelops.dao.LogErrorDAO;
import pelops.kasa.model.Reddiyat;
import pelops.kasa.model.ReddiyatView;
import pelops.kasa.model.Tahsilat;
import pelops.kasa.model.TahsilatView;
import pelops.kasa.model.TahsilatViewModel;
import pelops.kasa.model.ViewTahsilatListesi;
import pelops.model.DetayliArama;
import pelops.model.GenelTanimSablon;
import pelops.model.Hesap;
import pelops.model.Kasa;
import pelops.model.LogError;
import pelops.users.User;
import pelops.util.Util;
import semimis.utils.GenelArama;
import semiramis.kasa.dao.TahsilatDAO;
import semiramis.kasa.model.TahsilatRapor;
import semiramis.operasyon.controller.Utils;
import semiramis.operasyon.model.ChronologyIdentifier;


@ManagedBean(name = "kasaBean")
@SessionScoped
public class KasaBean {

	public static int tahsilatID;
	private String tali;
	LogErrorDAO log = new LogErrorDAO();
	Date nowDate = new Date();
	LogError newlog;
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	FacesContext context = FacesContext.getCurrentInstance();
	private Utils utils = new Utils();

	private boolean hitam = true, tahsilat = false, makbuz = true;

	private ArrayList<Kasa> kasaListesi = new ArrayList<Kasa>();
	private ArrayList<GenelTanimSablon> tahsilatStatuListesi = new ArrayList<GenelTanimSablon>();
	private ArrayList<GenelTanimSablon> odemeYeriListesi = new ArrayList<GenelTanimSablon>();
	private Kasa modelKasa = new Kasa();
	private ArrayList<DetayliArama> detayliAramaListesi = new ArrayList<DetayliArama>();
	private ArrayList<DetayliArama> filteredDetayliAramaListesi = new ArrayList<DetayliArama>();

	private ArrayList<DetayliArama> filterDetayliAramaListesi = new ArrayList<>();
	private Tahsilat bilgiTahsilat = new Tahsilat();
	private Date baslangicTarihi = new Date(), bitisTarihi = DateUtils.addMonths(new Date(), 1), olddatenew, enddatenew;
	private ArrayList<TahsilatViewModel> tahsilatYapilacakListe = new ArrayList<TahsilatViewModel>();

	private IcraDosyaIslemleriBean icdb;

	private ArrayList<TahsilatViewModel> tahsilatiGecmisListe = new ArrayList<TahsilatViewModel>();

	private ArrayList<TahsilatView> tahsilatYapilmisListe = new ArrayList<TahsilatView>();

	private ArrayList<TahsilatView> tahsilatviewyapilacakListe = new ArrayList<TahsilatView>();
	private ArrayList<ReddiyatView> reddiyatListesi = new ArrayList<>();

	private ArrayList<ReddiyatView> reddiyatYapilmisListe = new ArrayList<>();

	private KasaCtrl controller = new KasaCtrl();
	private Reddiyat reddiyatBilgisi = new Reddiyat();

	Date tarih_rapor = new Date();

	private double hsbc_aylik, akbank_aylik, garanti_aylik, ing_aylik;
	private double hsbc_gunluk, akbank_gunluk, garanti_gunluk, ing_gunluk;

	private String hsbc_aylik1, akbank_aylik1, garanti_aylik1, ing_aylik1;
	private String hsbc_gunluk1, akbank_gunluk1, garanti_gunluk1, ing_gunluk1;

	private String Ay, gun;
	
	@SuppressWarnings({ "deprecation", "static-access", "unchecked" })
	public KasaBean() {
		try {
			
			sayfaGuncelle();
			getTahsilatRapor();

		} catch (Exception e) {

			System.out.println("KASABEAN :"+e.getMessage());
		}
	}
	
	
	public void chooseIcraDosyasi() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("modal", true);
		options.put("contentWidth", 1800);
		RequestContext.getCurrentInstance().openDialog("dlg_genel_arama", options, null);

	}

	public void onIcraDosyasiChosen(SelectEvent event) throws Exception {
		GenelArama genelArama = (GenelArama) event.getObject();
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Car Selected", "Id:" + genelArama.getId());

		FacesContext.getCurrentInstance().addMessage(null, message);

		icraDosyaSec(genelArama.getId());
	}
	
	public void icraDosyaSec(int id) throws Exception {

		Util usersbilgi = new Util();
		RequestContext.getCurrentInstance().execute("PF('dlgdetayliarama').hide()");

		icdb = new IcraDosyaIslemleriBean();
		icdb.GelismisListe(id);
		this.hesaplistesi = icdb.getHesaplistesi();

		bilgiTahsilat.setDosyaTipiId(AktifBean.DOSYA_STATUSU_ID);
		bilgiTahsilat.setBorcluId(AktifBean.borcluId);
		bilgiTahsilat.setBorclu_adi(AktifBean.getBorcluAdi());
		bilgiTahsilat.setIcra_dosya_no(AktifBean.getIcraDosyaNo());
		bilgiTahsilat.setMusteriNo(AktifBean.getMusteriNo());
		bilgiTahsilat.setIcra_dosyasi_id(AktifBean.getIcraDosyaID());
		bilgiTahsilat.setMuvekkil_adi(AktifBean.getMuvekkilAdi());
		bilgiTahsilat.setKasa_islemini_yapan(usersbilgi.getUser().getUsrAdSoyad());
		bilgiTahsilat.setTasilati_yapan(usersbilgi.getUser().getUsrAdSoyad());
		bilgiTahsilat.setIcra_mudurlugu(AktifBean.getIcraMudurlugu());
		bilgiTahsilat.setIcraMudurlukId(AktifBean.ICRA_MUDURLUK_ID);
		bilgiTahsilat.setTahsilat_tarihi(new Date());
		makbuz = true;
		tahsilat = false;

	}

	

	public void TahsilatAktar(String id) {

		if (id == null) {
			context.addMessage(null, new FacesMessage(
					"Müşteri ve Hesap Bilgileri Olmayan Kayıtları Seçemezsiniz Lütfen Sistem Yönetinize Başvurunuz..."));
		} else {

			Util usersbilgi = new Util();
			Tahsilat tahsilat = null;
			try {
				tahsilat = controller.secilenModeliGetir(id);
			} catch (Exception e) {
				
				System.out.println("HATA kasabean 146 satır :"+e.getMessage());
			}

			bilgiTahsilat.setBorclu_adi(AktifBean.getBorcluAdi());

			bilgiTahsilat.setMusteriNo(AktifBean.getMusteriNo());
			bilgiTahsilat.setIcra_dosya_no(AktifBean.getIcraDosyaNo());
			bilgiTahsilat.setIcra_dosyasi_id(AktifBean.getIcraDosyaID());
			bilgiTahsilat.setMuvekkil_adi(AktifBean.getMuvekkilAdi());
			// bilgiTahsilat.setTahsilat_miktari(this.getTahsilatYapilacakListe().get(returnID(id)).getOdemeMiktari());
			bilgiTahsilat.setTahsilat_miktari(tahsilat.getTahsilat_miktari());
			bilgiTahsilat.setIzleme_id(tahsilat.getIzleme_id());
			bilgiTahsilat.setOdemeplani_id(tahsilat.getOdemeplani_id());
			bilgiTahsilat.setVizit_id(tahsilat.getVizit_id());
			bilgiTahsilat.setTahsilat_tarihi(new Date());
			bilgiTahsilat.setKasa_islemini_yapan(usersbilgi.getUser().getUsrAdSoyad());
			bilgiTahsilat.setSoz_alan_personel_id(tahsilat.getSoz_alan_personel_id());
			bilgiTahsilat.setIcra_mudurlugu(tahsilat.getIcra_mudurlugu());

			bilgiTahsilat.setTasilati_yapan(tahsilat.getTasilati_yapan());
			RequestContext.getCurrentInstance().execute("PF('frmtahsilatyap').show();");

			this.setTahsilat(false);
			makbuz = true;
			try {
				icdb = new IcraDosyaIslemleriBean();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			icdb.GelismisListe(AktifBean.getIcraDosyaID());
			this.hesaplistesi = icdb.getHesaplistesi();

		}
	}

	public int returnID(int id) {
		int rID = 0;
		try {
			for (int i = 0; i < this.getTahsilatYapilacakListe().size(); i++) {
				if (this.getTahsilatYapilacakListe().get(i).getId() == id) {
					rID = i;
				}

			}
		} 
		catch (Exception e) {
			
		}

		return rID;

	}

	public int returnReddiyatID(int id) {
		int rtID = 0;
		for (int i = 0; i < reddiyatListesi.size(); i++) {
			if (reddiyatListesi.get(i).getId() == id) {
				rtID = i;
			}
		}
		return rtID;
	}



	public void ReddiyatAktar(int id) {
		reddiyatBilgisi = new Reddiyat();
		reddiyatListesi = returnReddiyatview(reddiyatListesi);
		reddiyatBilgisi = controller.createReddiyatFromReddiyatView(reddiyatListesi.get(returnReddiyatID(id)));

		RequestContext.getCurrentInstance().execute("PF('frmreddiyatyap').show();");

	}

	public ArrayList<ReddiyatView> returnReddiyatview(ArrayList<ReddiyatView> rw) {
		ArrayList<ReddiyatView> returnRW = new ArrayList<>();
		returnRW = rw;

		for (int i = 0; i < rw.size(); i++) {
			if (rw.get(i).getDevletDurum() == 0) {
				returnRW.get(i).setAktifTutarTL(rw.get(i).getDevletReddiyatTuttarTL());
				returnRW.get(i).setReddiyatTuru("Devlete Reddiyat");
			} else if (rw.get(i).getMuvekkilDurum() == 0) {
				returnRW.get(i).setAktifTutarTL(rw.get(i).getMuvekkilReddiyatTutarTL());
				returnRW.get(i).setReddiyatTuru("Bankaya Reddiyat");
			} else if (rw.get(i).getSasaDurum() == 0) {
				returnRW.get(i).setAktifTutarTL(rw.get(i).getSasaReddiyatTutarTL());
				returnRW.get(i).setReddiyatTuru("Sasaya Reddiyat");
			}

		}
		return returnRW;
	}

	public void reddiyatYap() {
		try {
			if (reddiyatBilgisi.getDevletDurum() == 0)
				reddiyatBilgisi.setDevletDurum(1);
			if (reddiyatBilgisi.getSasaDurum() == 0)
				reddiyatBilgisi.setSasaDurum(1);
			if (reddiyatBilgisi.getMuvekkilDurum() == 0)
				reddiyatBilgisi.setMuvekkilDurum(1);
			controller.guncelle(reddiyatBilgisi);
			sayfaGuncelle();
			utils.saveChronology(AktifBean.getIcraDosyaID(), ChronologyIdentifier.ISLEM_REDDIYAT,
					reddiyatBilgisi.getBorcluAdi() + "---" + reddiyatBilgisi.getToplamReddiyatTutari()
							+ " reddiyat yapılmıştır");
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Reddiyat İşlemi Başarı İle Gerçekleştirildi..."));
			RequestContext.getCurrentInstance().execute("PF('frmreddiyatyap').hide();");
		} 
		catch (Exception e) {}

	}

	private Hesap hesaplistesi = new Hesap();

	public Hesap getHesaplistesi() {
		return hesaplistesi;
	}

	public void setHesaplistesi(Hesap hesaplistesi) {
		this.hesaplistesi = hesaplistesi;
	}

	public void tahsilatYap() {

		try {

			Hesap hesap = AktifBean.hesaplistesi;
			ArrayList<Reddiyat> redList = new ArrayList<>();
			boolean hitam = false;
			if ((hesap.getKalan_alacak() - bilgiTahsilat.getTahsilat_miktari() <= 1)) {
				hitam = true;
				bilgiTahsilat.setHitam_durum(1);
				for (int i = 1; i < 4; i++) {
					Reddiyat reddiyat = controller.createReddiyatForStatus(i, hesap, bilgiTahsilat);
					redList.add(reddiyat);
				}
				utils.saveChronology(AktifBean.getIcraDosyaID(), ChronologyIdentifier.ISLEM_HITAM,
						bilgiTahsilat.getBorclu_adi() + "----" + bilgiTahsilat.getTahsilat_miktari()
								+ "TL Hitam yapılmıştır.");
			} else {
				hitam = false;
			}
			bilgiTahsilat.setDurum(1);

			controller.kaydet(bilgiTahsilat, hitam, redList);
			tahsilat = true;
			makbuz = false;
			utils.saveChronology(AktifBean.getIcraDosyaID(), ChronologyIdentifier.ISLEM_TAHSILAT,
					bilgiTahsilat.getBorclu_adi() + "----" +  bilgiTahsilat.getTahsilat_miktari()
							+ " TL Tahsilat yapılmıştır.");

			sayfaGuncelle();
			getTahsilatRapor();

			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Tahsilat İşlemi Başarı İle Gerçekleştirildi..."));

		} 
		catch (Exception e) {
			
			System.out.println("Hata KasaBean tahsilatYap :"+e.getMessage());
			
		}

	}

	@SuppressWarnings("unused")
	private static java.sql.Date convertUtilToSql(java.util.Date uDate) {

		java.sql.Date sDate = new java.sql.Date(uDate.getTime());

		return sDate;

	}

	public void temizle() {
		bilgiTahsilat = new Tahsilat();
		hesaplistesi = new Hesap();

	}

	
	public void sayfaGuncelle() {
		try {

			
			hsbc_aylik=0;
			hsbc_aylik1="";
			hsbc_gunluk=0;
			hsbc_gunluk1="";
			
			String oldDate = "01/01/2000";
			Date tarih = new Date(oldDate);
			GelismisAramaDAO dao = new GelismisAramaDAO();
			detayliAramaListesi = dao.Listele("", "", "", "", "", "", 0, 0, 0, tarih, tarih, tarih, tarih, tarih,
					tarih);
			filterDetayliAramaListesi = detayliAramaListesi;
			bilgiTahsilat = new Tahsilat();
			HttpSession session = Util.getSession();

			bilgiTahsilat.setKasa_islemini_yapan(((User) session.getAttribute("user")).getUsrAdSoyad());

			tahsilatYapilacakListe = controller.getListeFromViewsForTahsilatIslemi(baslangicTarihi, bitisTarihi);

			reddiyatListesi = (ArrayList<ReddiyatView>) controller.getListefromView(0, 3, 1, null, null);
			reddiyatListesi.addAll(controller.getListefromView(0, 3, 2, null, null));
			reddiyatListesi.addAll(controller.getListefromView(0, 3, 3, null, null));

			reddiyatListesi = returnReddiyatview(reddiyatListesi);

			ViewDAO viewdao = new ViewDAO();
			tahsilatYapilmisListe = (ArrayList<TahsilatView>) controller.getListefromView(1, 1, null, baslangicTarihi,
					bitisTarihi);

			reddiyatYapilmisListe = (ArrayList<ReddiyatView>) controller.getListefromView(1, 3, 1, baslangicTarihi,
					bitisTarihi);
			reddiyatYapilmisListe.addAll(controller.getListefromView(1, 3, 2, baslangicTarihi, bitisTarihi));
			reddiyatYapilmisListe.addAll(controller.getListefromView(1, 3, 3, baslangicTarihi, bitisTarihi));

			SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
			String dateInString = "01-01-2";
			Date date = sdf.parse(dateInString);

			olddatenew = date;
			enddatenew = baslangicTarihi;
			enddatenew.setHours(-24);

			tahsilatiGecmisListe = controller.getListeFromViewsForTahsilatIslemi(olddatenew, enddatenew);

			Ay = "2016 - HAZİRAN";
			gun = tarih_rapor.toString();

			if (AktifBean.getIcraDosyaID() > 0) {
				icdb = new IcraDosyaIslemleriBean();
				icdb.GelismisListe(AktifBean.getIcraDosyaID());
				this.hesaplistesi = icdb.getHesaplistesi();
			}

			
			
		

		} 
		catch (Exception e) {
			
			System.out.println("KasaBean sayfaGuncelle Line 425 :"+e.getMessage());
			
		}

	}
	
	
	
	
	
	public void getTahsilatRapor() // Günlük ve Aylık bazda raporların görüntülenmesi için
	{
		TahsilatDAO tahsilatDAO=new TahsilatDAO();
		
		List<TahsilatRapor> listeRaporAylik=tahsilatDAO.getAylik();
		List<TahsilatRapor> listeRaporGunluk=tahsilatDAO.getGunluk();
		
		for (int i = 0; i < listeRaporAylik.size(); i++) {

			if (listeRaporAylik.get(i).getMuvekkilAdi().equals("HSBC BANK A.Ş.") == true)
				hsbc_aylik = listeRaporAylik.get(i).getToplam();
			if (listeRaporAylik.get(i).getMuvekkilAdi().equals("AKBANK T.A.Ş.") == true)
				akbank_aylik = listeRaporAylik.get(i).getToplam();
			if (listeRaporAylik.get(i).getMuvekkilAdi().equals("T. GARANTİ BANKASI A.Ş.") == true)
				garanti_aylik = listeRaporAylik.get(i).getToplam();
			if (listeRaporAylik.get(i).getMuvekkilAdi().equals("İNG BANK A.Ş.") == true)
				ing_aylik = listeRaporAylik.get(i).getToplam();
		}
		
		
		
		
		for (int i = 0; i < listeRaporGunluk.size(); i++) {

			if (listeRaporGunluk.get(i).getMuvekkilAdi().equals("HSBC BANK A.Ş.") == true)
				hsbc_gunluk = listeRaporGunluk.get(i).getToplam();
			if (listeRaporGunluk.get(i).getMuvekkilAdi().equals("AKBANK T.A.Ş.") == true)
				akbank_gunluk += listeRaporGunluk.get(i).getToplam();
			if (listeRaporGunluk.get(i).getMuvekkilAdi().equals("T. GARANTİ BANKASI A.Ş.") == true)
				garanti_gunluk += listeRaporGunluk.get(i).getToplam();
			if (listeRaporGunluk.get(i).getMuvekkilAdi().equals("İNG BANK A.Ş.") == true)
				ing_gunluk += listeRaporGunluk.get(i).getToplam();

		}

		NumberFormat defaultFormat = NumberFormat.getCurrencyInstance();

		hsbc_aylik1 = defaultFormat.format(hsbc_aylik);
		akbank_aylik1 = defaultFormat.format(akbank_aylik);
		garanti_aylik1 = defaultFormat.format(garanti_aylik);
		ing_aylik1 = defaultFormat.format(ing_aylik);

		hsbc_gunluk1 = defaultFormat.format(hsbc_gunluk);
		akbank_gunluk1 = defaultFormat.format(akbank_gunluk);
		garanti_gunluk1 = defaultFormat.format(garanti_gunluk);
		ing_gunluk1 = defaultFormat.format(ing_gunluk);
	}
	
	

	public void print(Integer id) {
		try {

			KasaCtrl islem = new KasaCtrl();
			if (id == null) {

				islem.printTahsilatMakbuzu(tahsilatID);
				
			} else {
				islem.printTahsilatMakbuzu(id);
			}
		} catch (Exception e) {
			
		}

	}

	public void yazdir() throws Exception {

		ViewDAO dao = new ViewDAO();
		Map<String, Object> hashMap = new HashMap<String, Object>();
		ArrayList<ViewTahsilatListesi> liste = dao.getTahsilatListesiView(null, null, null);
		JasperPrint jasperPrint;

		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(liste);
		String reportPath = FacesContext.getCurrentInstance().getExternalContext()
				.getRealPath("/reports/JASPER/Tahsilat-Listesi.jasper");
		jasperPrint = JasperFillManager.fillReport(reportPath, hashMap, beanCollectionDataSource);

		HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance()
				.getExternalContext().getResponse();
		httpServletResponse.addHeader("Content-disposition", "attachment; filename=Tahsilat-Listesi.pdf");
		ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();

		JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
		
		

		servletOutputStream.flush();
		servletOutputStream.close();
		FacesContext.getCurrentInstance().responseComplete();
	}
	
	
	
	
	

	public ArrayList<DetayliArama> getFilteredDetayliAramaListesi() {
		return filteredDetayliAramaListesi;
	}

	public void setFilteredDetayliAramaListesi(ArrayList<DetayliArama> filteredDetayliAramaListesi) {
		this.filteredDetayliAramaListesi = filteredDetayliAramaListesi;
	}

	public boolean isHitam() {
		return hitam;
	}

	public void setHitam(boolean hitam) {
		this.hitam = hitam;
	}

	public boolean isTahsilat() {
		return tahsilat;
	}

	public void setTahsilat(boolean tahsilat) {
		this.tahsilat = tahsilat;
	}

	public boolean isMakbuz() {
		return makbuz;
	}

	public void setMakbuz(boolean makbuz) {
		this.makbuz = makbuz;
	}

	public IcraDosyaIslemleriBean getIcdb() {
		return icdb;
	}

	public void setIcdb(IcraDosyaIslemleriBean icdb) {
		this.icdb = icdb;
	}

	public String getTali() {
		return tali;
	}

	public void setTali(String tali) {
		this.tali = tali;
	}

	public String getHsbc_aylik1() {
		return hsbc_aylik1;
	}

	public void setHsbc_aylik1(String hsbc_aylik1) {
		this.hsbc_aylik1 = hsbc_aylik1;
	}

	public String getAkbank_aylik1() {
		return akbank_aylik1;
	}

	public void setAkbank_aylik1(String akbank_aylik1) {
		this.akbank_aylik1 = akbank_aylik1;
	}

	public String getGaranti_aylik1() {
		return garanti_aylik1;
	}

	public void setGaranti_aylik1(String garanti_aylik1) {
		this.garanti_aylik1 = garanti_aylik1;
	}

	public String getIng_aylik1() {
		return ing_aylik1;
	}

	public void setIng_aylik1(String ing_aylik1) {
		this.ing_aylik1 = ing_aylik1;
	}

	public String getHsbc_gunluk1() {
		return hsbc_gunluk1;
	}

	public void setHsbc_gunluk1(String hsbc_gunluk1) {
		this.hsbc_gunluk1 = hsbc_gunluk1;
	}

	public String getAkbank_gunluk1() {
		return akbank_gunluk1;
	}

	public void setAkbank_gunluk1(String akbank_gunluk1) {
		this.akbank_gunluk1 = akbank_gunluk1;
	}

	public String getGaranti_gunluk1() {
		return garanti_gunluk1;
	}

	public void setGaranti_gunluk1(String garanti_gunluk1) {
		this.garanti_gunluk1 = garanti_gunluk1;
	}

	public String getIng_gunluk1() {
		return ing_gunluk1;
	}

	public void setIng_gunluk1(String ing_gunluk1) {
		this.ing_gunluk1 = ing_gunluk1;
	}

	public String getAy() {
		return Ay;
	}

	public void setAy(String ay) {
		Ay = ay;
	}

	public String getGun() {
		return gun;
	}

	public void setGun(String gun) {
		this.gun = gun;
	}

	public Date getOlddatenew() {
		return olddatenew;
	}

	public void setOlddatenew(Date olddatenew) {
		this.olddatenew = olddatenew;
	}

	public Date getEnddatenew() {
		return enddatenew;
	}

	public void setEnddatenew(Date enddatenew) {
		this.enddatenew = enddatenew;
	}

	public Date getTarih() {
		return tarih_rapor;
	}

	public void setTarih(Date tarih) {
		this.tarih_rapor = tarih;
	}

	public Date getTarih_rapor() {
		return tarih_rapor;
	}

	public void setTarih_rapor(Date tarih_rapor) {
		this.tarih_rapor = tarih_rapor;
	}

	public double getHsbc_aylik() {
		return hsbc_aylik;
	}

	public void setHsbc_aylik(double hsbc_aylik) {
		this.hsbc_aylik = hsbc_aylik;
	}

	public double getAkbank_aylik() {
		return akbank_aylik;
	}

	public void setAkbank_aylik(double akbank_aylik) {
		this.akbank_aylik = akbank_aylik;
	}

	public double getGaranti_aylik() {
		return garanti_aylik;
	}

	public void setGaranti_aylik(double garanti_aylik) {
		this.garanti_aylik = garanti_aylik;
	}

	public double getIng_aylik() {
		return ing_aylik;
	}

	public void setIng_aylik(double ing_aylik) {
		this.ing_aylik = ing_aylik;
	}

	public double getHsbc_gunluk() {
		return hsbc_gunluk;
	}

	public void setHsbc_gunluk(double hsbc_gunluk) {
		this.hsbc_gunluk = hsbc_gunluk;
	}

	public double getAkbank_gunluk() {
		return akbank_gunluk;
	}

	public void setAkbank_gunluk(double akbank_gunluk) {
		this.akbank_gunluk = akbank_gunluk;
	}

	public double getGaranti_gunluk() {
		return garanti_gunluk;
	}

	public void setGaranti_gunluk(double garanti_gunluk) {
		this.garanti_gunluk = garanti_gunluk;
	}

	public double getIng_gunluk() {
		return ing_gunluk;
	}

	public void setIng_gunluk(double ing_gunluk) {
		this.ing_gunluk = ing_gunluk;
	}

	public ArrayList<TahsilatViewModel> getTahsilatiGecmisListe() throws Exception {

		return this.tahsilatiGecmisListe;
	}

	public void setTahsilatiGecmisListe(ArrayList<TahsilatViewModel> tahsilatiGecmisListe) {

		this.tahsilatiGecmisListe = tahsilatiGecmisListe;
	}

	public ArrayList<ReddiyatView> getReddiyatYapilmisListe() {
		return reddiyatYapilmisListe;
	}

	public void setReddiyatYapilmisListe(ArrayList<ReddiyatView> reddiyatYapilmisListe) {
		this.reddiyatYapilmisListe = reddiyatYapilmisListe;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<TahsilatView> getTahsilatYapilmisListe() throws Exception {
		return tahsilatYapilmisListe;
	}

	public void setTahsilatYapilmisListe(ArrayList<TahsilatView> tahsilatYapilmisListe) {

		this.tahsilatYapilmisListe = tahsilatYapilmisListe;
	}

	public ArrayList<TahsilatView> getTahsilatviewyapilacakListe() {
		return tahsilatviewyapilacakListe;
	}

	public void setTahsilatviewyapilacakListe(ArrayList<TahsilatView> tahsilatviewyapilacakListe) {
		this.tahsilatviewyapilacakListe = tahsilatviewyapilacakListe;
	}

	public Reddiyat getReddiyatBilgisi() {
		return reddiyatBilgisi;
	}

	public void setReddiyatBilgisi(Reddiyat reddiyatBilgisi) {
		this.reddiyatBilgisi = reddiyatBilgisi;
	}

	public ArrayList<ReddiyatView> getReddiyatListesi() {
		return reddiyatListesi;
	}

	public void setReddiyatListesi(ArrayList<ReddiyatView> reddiyatListesi) {
		this.reddiyatListesi = reddiyatListesi;
	}

	public ArrayList<TahsilatViewModel> getTahsilatYapilacakListe() throws Exception {

		return this.tahsilatYapilacakListe;
	}

	public void setTahsilatYapilacakListe(ArrayList<TahsilatViewModel> tahsilatYapilacakListe) {

		this.tahsilatYapilacakListe = tahsilatYapilacakListe;
	}

	public KasaCtrl getController() {
		return controller;
	}

	public void setController(KasaCtrl controller) {
		this.controller = controller;
	}

	public Date getBaslangicTarihi() {
		return baslangicTarihi;
	}

	public void setBaslangicTarihi(Date baslangicTarihi) {
		this.baslangicTarihi = baslangicTarihi;
	}

	public Date getBitisTarihi() {
		return bitisTarihi;
	}

	public void setBitisTarihi(Date bitisTarihi) {
		this.bitisTarihi = bitisTarihi;
	}

	public Tahsilat getBilgiTahsilat() {
		return bilgiTahsilat;
	}

	public void setBilgiTahsilat(Tahsilat bilgiTahsilat) {
		this.bilgiTahsilat = bilgiTahsilat;
	}

	public ArrayList<DetayliArama> getFilterDetayliAramaListesi() {
		return filterDetayliAramaListesi;
	}

	public void setFilterDetayliAramaListesi(ArrayList<DetayliArama> filterDetayliAramaListesi) {
		this.filterDetayliAramaListesi = filterDetayliAramaListesi;
	}

	public ArrayList<DetayliArama> getDetayliAramaListesi() {
		return detayliAramaListesi;
	}

	public void setDetayliAramaListesi(ArrayList<DetayliArama> detayliAramaListesi) {
		this.filterDetayliAramaListesi = detayliAramaListesi;
		this.detayliAramaListesi = detayliAramaListesi;
	}

	public ArrayList<GenelTanimSablon> getOdemeYeriListesi() throws Exception {
		GenelTanimBean gtlist = new GenelTanimBean();
		return gtlist.ListeGetir("tbl_tahsilat_tipi");
	}

	public void setOdemeYeriListesi(ArrayList<GenelTanimSablon> odemeYeriListesi) {
		this.odemeYeriListesi = odemeYeriListesi;
	}

	public ArrayList<GenelTanimSablon> getTahsilatStatuListesi() throws Exception {
		GenelTanimBean liste = new GenelTanimBean();
		return liste.ListeGetir("tbl_tahsilat_statusu");
	}

	public void setTahsilatStatuListesi(ArrayList<GenelTanimSablon> tahsilatStatuListesi) {
		this.tahsilatStatuListesi = tahsilatStatuListesi;
	}

	public ArrayList<Kasa> getKasaListesi() {
		return kasaListesi;
	}

	public void setKasaListesi(ArrayList<Kasa> kasaListesi) {
		this.kasaListesi = kasaListesi;
	}

	public Kasa getModelKasa() {
		return modelKasa;
	}

	public void setModelKasa(Kasa modelKasa) {
		this.modelKasa = modelKasa;
	}

}
