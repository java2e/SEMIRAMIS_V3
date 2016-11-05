package semiramis.operasyon.controller;

import java.text.NumberFormat;
import java.time.Year;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import pelops.controller.AktifBean;
import pelops.dao.BaglantiDAO;
import pelops.dao.BasvuruHarciDAO;
import pelops.dao.HarcBilgisiDAO;
import pelops.dao.HesapDAO;
import pelops.dao.IcraDosyasiDAO;
import pelops.dao.VekaletHarciDAO;
import pelops.dao.VekaletSinirlariDAO;
import pelops.model.HarcBilgisi;
import pelops.model.Hesap;
import pelops.model.HesaplarList;
import pelops.model.IcraDosyasi;



@ManagedBean(name="hesapBean",eager=true)
@RequestScoped
public class HesapBean 
{
	
	private Hesap hesap=new Hesap();
	
	private Date hesapTarihi = new Date();

	private double basvuruHarciTL;

	private double vekaletHarciTL;

	private double pesinHarcTL;

	private double harcoranTL = 2.27;
	
	private IcraDosyasi icraDosyasi;
	
	private List<HesaplarList> hesaplarlistesi;
	
	
	
	public HesapBean()  {
		// TODO Auto-generated constructor stub
	
		
		try {
		

	hesap=new Hesap();	
	
	hesaplarlistesi=new ArrayList<>();
	
	icraDosyasi=new IcraDosyasi();
	
	int hesapID = new BaglantiDAO().Listele(AktifBean.icraDosyaID).getHesaplamaID();
	AktifBean.setHesapID(hesapID);
	
	icraDosyasi=new IcraDosyasiDAO().Listele(AktifBean.icraDosyaID);
	
	HesapDAO hesapdao = new HesapDAO();
	hesap = hesapdao.Liste(hesapID);
	
	hesapGetir();
	
	
		} catch (Exception e) {
			System.out.println("Hata HesapBean :"+e.getMessage());
		}
	
	}
	
	public void hesapGetir()
	{
		

		try {
		

		VekaletSinirlariDAO vekaletsinirlar = new VekaletSinirlariDAO();
		VekaletHarciDAO vekaletharcidao = new VekaletHarciDAO();
		BasvuruHarciDAO basvuruharcidao = new BasvuruHarciDAO();
		int yil = Year.now().getValue();
		BaglantiDAO baglantidao = new BaglantiDAO();
		Date bugun = new Date();
		// hesaplistesi.setId(
		// baglantidao.Listele(pelops.controller.AktifBean.getIcraDosyaID()).getHesaplamaID());

		double asilalacak = hesap.getAsil_alacak();
		double gecikme = hesap.getGecikme_faizi();
		double temerrut = hesap.getTemerrut_faizi();
		double faizgider = hesap.getFaiz_gider_vergisi();
		double noter = hesap.getMasraf_tutari();

		double takipalacagi = asilalacak + gecikme + temerrut + faizgider + noter;

		hesap.setTakip_alacagi(takipalacagi);

		if (hesap.getTakip_alacagi() <= vekaletsinirlar.Liste(yil).getSinir_1()) {
			hesap
					.setVekalet_ucreti(hesap.getTakip_alacagi() * vekaletsinirlar.Liste(yil).getYuzde_1() / 100);

		} else {
			if (hesap.getTakip_alacagi() <= vekaletsinirlar.Liste(yil).getSinir_2()) {
				hesap.setVekalet_ucreti(
						(vekaletsinirlar.Liste(yil).getSinir_1() * vekaletsinirlar.Liste(yil).getYuzde_1() / 100)
								+ (hesap.getTakip_alacagi() - vekaletsinirlar.Liste(yil).getSinir_1())
										* vekaletsinirlar.Liste(yil).getYuzde_2() / 100);
			} else {
				if (hesap.getTakip_alacagi() <= vekaletsinirlar.Liste(yil).getSinir_3()) {
					hesap.setVekalet_ucreti(
							(vekaletsinirlar.Liste(yil).getSinir_2() * vekaletsinirlar.Liste(yil).getYuzde_2() / 100)
									+ (hesap.getTakip_alacagi() - vekaletsinirlar.Liste(yil).getSinir_2())
											* vekaletsinirlar.Liste(yil).getYuzde_3() / 100);
				} else {
					if (hesap.getTakip_alacagi() <= vekaletsinirlar.Liste(yil).getSinir_4()) {
						hesap.setVekalet_ucreti((vekaletsinirlar.Liste(yil).getSinir_3()
								* vekaletsinirlar.Liste(yil).getYuzde_3() / 100)
								+ (hesap.getTakip_alacagi() - vekaletsinirlar.Liste(yil).getSinir_3())
										* vekaletsinirlar.Liste(yil).getYuzde_4() / 100);

					} else {
						if (hesap.getTakip_alacagi() <= vekaletsinirlar.Liste(yil).getSinir_5()) {
							hesap.setVekalet_ucreti((vekaletsinirlar.Liste(yil).getSinir_4()
									* vekaletsinirlar.Liste(yil).getYuzde_4() / 100)
									+ (hesap.getTakip_alacagi() - vekaletsinirlar.Liste(yil).getSinir_4())
											* vekaletsinirlar.Liste(yil).getYuzde_5() / 100);
						} else {
							if (hesap.getTakip_alacagi() <= vekaletsinirlar.Liste(yil).getSinir_6()) {
								hesap.setVekalet_ucreti((vekaletsinirlar.Liste(yil).getSinir_5()
										* vekaletsinirlar.Liste(yil).getYuzde_5() / 100)
										+ (hesap.getTakip_alacagi() - vekaletsinirlar.Liste(yil).getSinir_5())
												* vekaletsinirlar.Liste(yil).getYuzde_6() / 100);
							} else {

								if (hesap.getTakip_alacagi() <= vekaletsinirlar.Liste(yil).getSinir_7()) {
									hesap.setVekalet_ucreti((vekaletsinirlar.Liste(yil).getSinir_6()
											* vekaletsinirlar.Liste(yil).getYuzde_6() / 100)
											+ (hesap.getTakip_alacagi()
													- vekaletsinirlar.Liste(yil).getSinir_6())
													* vekaletsinirlar.Liste(yil).getYuzde_7() / 100);
								} else {
									hesap.setVekalet_ucreti((vekaletsinirlar.Liste(yil).getSinir_7()
											* vekaletsinirlar.Liste(yil).getYuzde_7() / 100)
											+ (hesap.getTakip_alacagi()
													- vekaletsinirlar.Liste(yil).getSinir_7())
													* vekaletsinirlar.Liste(yil).getYuzde_8() / 100);

								}

							}

						}

					}

				}

			}

		}

		if (takipalacagi < vekaletsinirlar.Liste(yil).getVekalet_ucret_sinir()) {
			if (asilalacak < vekaletsinirlar.Liste(yil).getVekalet_ucret()) {
				hesap.setVekalet_ucreti(asilalacak);
			} else {
				hesap.setVekalet_ucreti(vekaletsinirlar.Liste(yil).getVekalet_ucret());

			}

		}

		long Fark;
		Date ilkTarih = icraDosyasi.getTakipTarihi();
		Date sonTarih = new Date();

		if (hesapTarihi.getDate() != bugun.getDate()) {
			sonTarih = hesapTarihi;
		}

		if (sonTarih == null || ilkTarih == null)
			Fark = 0;
		else
			Fark = (sonTarih.getTime() - ilkTarih.getTime());

		int gun = (int) (Fark / (1000 * 60 * 60 * 24));

		if (gun <= 7) {

			hesap.setVekalet_ucreti(hesap.getVekalet_ucreti() * 3 / 4);
		}

		double faiz = hesap.getTakip_alacagi() * gun / 360 * hesap.getTemerrut_faiz_orani() / 100;

		hesap.setTakip_faizi(faiz);

		hesap.setFaiz_gider_vergisi2(faiz * 5 / 100);

		double basvuruHarci = basvuruharcidao.liste(yil).getTutar();

		double vekaletHarci = vekaletharcidao.liste(yil).getTutar();

		double pesinHarc = hesap.getTakip_alacagi() * 0.5 / 100;

		double digerHarc = basvuruHarci + vekaletHarci + pesinHarc + toplamharc();

		double harcoran = this.getHarcoranTL();

		this.setPesinHarcTL(pesinHarc);
		this.setBasvuruHarciTL(basvuruHarci);
		this.setVekaletHarciTL(vekaletHarci);

		double tahsil_harci = (hesap.getTakip_alacagi() * harcoran / 100) - pesinHarc;

		hesap.setTahsil_harci(tahsil_harci);

		hesap.setDiger_harclar(digerHarc);

		hesap.setToplam_alacak(hesap.getTakip_alacagi() + hesap.getVekalet_ucreti()
				+ hesap.getTakip_faizi() + hesap.getFaiz_gider_vergisi2()
				+ hesap.getDiger_harclar() + hesap.getMasraf_tutari() + hesap.getTahsil_harci());

		hesap.setKalan_alacak(
				hesap.getToplam_alacak() - hesap.getTahsilat_tutari() - hesap.getIndirim_tutari());

		NumberFormat defaultFormat = NumberFormat.getCurrencyInstance();

		hesap.setAsil_alacak_tl(defaultFormat.format(hesap.getAsil_alacak()));
		hesap.setDiger_harclar_tl(defaultFormat.format(hesap.getDiger_harclar()));
		hesap.setFaiz_gider_vergisi_tl(defaultFormat.format(hesap.getFaiz_gider_vergisi()));
		hesap.setFaiz_gider_vergisi2_tl(defaultFormat.format(hesap.getFaiz_gider_vergisi2()));
		hesap.setGecikme_faizi_tl(defaultFormat.format(hesap.getGecikme_faizi()));
		hesap.setIndirim_faiz_orani_tl(defaultFormat.format(hesap.getIndirim_faiz_orani()));
		hesap.setIndirim_tutari_tl(defaultFormat.format(hesap.getIndirim_tutari()));
		hesap.setKalan_alacak_tl(defaultFormat.format(hesap.getKalan_alacak()));
		hesap.setMasraf_tutari_tl(defaultFormat.format(hesap.getMasraf_tutari()));
		hesap.setNoter_masrafi_tl(defaultFormat.format(hesap.getNoter_masrafi()));
		hesap.setTahsil_harci_tl(defaultFormat.format(hesap.getTahsil_harci()));
		hesap.setTahsilat_tutari_tl(defaultFormat.format(hesap.getTahsilat_tutari()));
		hesap.setTakip_alacagi_tl(defaultFormat.format(hesap.getTakip_alacagi()));

		hesap.setTakip_faiz_gider_vergi_tl(defaultFormat.format(hesap.getTakip_faiz_gider_vergi()));
		hesap.setTakip_faizi_tl(defaultFormat.format(hesap.getTakip_faizi()));
		hesap.setTemerrut_faizi_tl(defaultFormat.format(hesap.getTemerrut_faizi()));
		hesap.setToplam_alacak_tl(defaultFormat.format(hesap.getToplam_alacak()));
		hesap.setVekalet_ucreti_tl(defaultFormat.format(hesap.getVekalet_ucreti()));

		if (hesapTarihi.getDate() == bugun.getDate()) {
			HesapDAO hesapdao = new HesapDAO();

			hesapdao.Gucelle(hesap);
		}

		AktifBean.hesaplistesi = hesap;
		
		
		} catch (Exception e) {
			
			System.out.println("Hata HesapBean hesapla :"+e.getMessage());
			// TODO: handle exception
		}
	
		
	}
	
	public double toplamharc() {

		HarcBilgisiDAO dao = new HarcBilgisiDAO();
		double returndouble = 0;

		try {
			for (HarcBilgisi harc : dao.getAllListFromIcraDosyaID(AktifBean.getIcraDosyaID())) {

				returndouble += harc.getHarc_miktari();
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returndouble;

	}



	
	
	public Hesap getHesap() {
		return hesap;
	}

	public void setHesap(Hesap hesap) {
		this.hesap = hesap;
	}

	public Date getHesapTarihi() {
		return hesapTarihi;
	}

	public void setHesapTarihi(Date hesapTarihi) {
		this.hesapTarihi = hesapTarihi;
	}

	public double getBasvuruHarciTL() {
		return basvuruHarciTL;
	}

	public void setBasvuruHarciTL(double basvuruHarciTL) {
		this.basvuruHarciTL = basvuruHarciTL;
	}

	public double getVekaletHarciTL() {
		return vekaletHarciTL;
	}

	public void setVekaletHarciTL(double vekaletHarciTL) {
		this.vekaletHarciTL = vekaletHarciTL;
	}

	public double getPesinHarcTL() {
		return pesinHarcTL;
	}

	public void setPesinHarcTL(double pesinHarcTL) {
		this.pesinHarcTL = pesinHarcTL;
	}

	public double getHarcoranTL() {
		return harcoranTL;
	}

	public void setHarcoranTL(double harcoranTL) {
		this.harcoranTL = harcoranTL;
	}

	

	public IcraDosyasi getIcraDosyasi() {
		return icraDosyasi;
	}

	public void setIcraDosyasi(IcraDosyasi icraDosyasi) {
		this.icraDosyasi = icraDosyasi;
	}

	public List<HesaplarList> getHesaplarlistesi() {
		return hesaplarlistesi;
	}

	public void setHesaplarlistesi(List<HesaplarList> hesaplarlistesi) {
		this.hesaplarlistesi = hesaplarlistesi;
	}
	
	
	

}
