package semiramis.operasyon.controller;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.swing.plaf.TabbedPaneUI;

import org.primefaces.context.RequestContext;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import pelops.dao.MasrafDAO;
import pelops.dao.PostaDAO;
import pelops.model.MasrafBilgisi;
import pelops.model.Posta;
import pelops.reports.controller.GenelYazdirBean;
import semimis.utils.ConvertDate;
import semiramis.operasyon.dao.HaczeEsasMalBilgisiDAO;
import semiramis.operasyon.dao.MuameleDAO;
import semiramis.operasyon.dao.MuzekkereTopluDAO;
import semiramis.operasyon.model.ComboItem;
import semiramis.operasyon.model.HaczeEsasMalBilgisi;
import semiramis.operasyon.model.Muamele;
import semiramis.operasyon.model.SubReport;
import semiramis.operasyon.model.TapuBilgisi;

@ManagedBean(name = "muzekkereTopluBean")
@SessionScoped
public class MuzekkereTopluBean extends ConvertDate {
	public final static int MUZEKKERE_MAAS = 2;
	public final static int MUZEKKERE_MAAS_TALEP = 2;

	public final static int MUZEKKERE_TAPU = 1;

	public final static int TALEP_ARAC_SERH = 3;

	private int alacakliId;

	private int icraMudurluguId;

	private int muzekkereTipiId;

	private String icraDosyaNo;

	private Date basTarih;

	private Date bitTarih;

	private List<ComboItem> alacakliListe;

	private List<ComboItem> muzekkereTipListe;

	public MuzekkereTopluDAO dao;

	private List<Muamele> muameleList;

	private List<Muamele> selectedMuameleList;

	public MuameleDAO muameleDAO;

	private Integer[] selectedEvrak;

	public String muzekkereTalep = "";

	public String genelPath = ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext())
			.getRealPath("/pdfler/");

	private String pdf;

	public MuzekkereTopluBean() {
		// TODO Auto-generated constructor stub

		dao = new MuzekkereTopluDAO();

		muameleDAO = new MuameleDAO();

		muameleList = new ArrayList<Muamele>();

		alacakliListe = new ArrayList<ComboItem>();

		alacakliListe = dao.getAlacakliList();

		muzekkereTipListe = new ArrayList<ComboItem>();

		muzekkereTipListe = muameleDAO.getMuzekkereTip();

	}

	public void muameleListYazdir() {

		for (int i = 0; i < selectedMuameleList.size(); i++) {

			selectedMuameleList.get(i).setBarkodTxt(barkodUret(selectedMuameleList.get(i)));

			if (muzekkereTipiId == MUZEKKERE_MAAS) {

				if(selectedMuameleList.size()>0)
				{
				List<HaczeEsasMalBilgisi> liste = new HaczeEsasMalBilgisiDAO().liste(selectedMuameleList.get(i).getBorcluId(), MUZEKKERE_MAAS);

				if(liste.size()>0)
				{
				selectedMuameleList.get(i).setHaczeEsasMalId(String.valueOf(liste.get(0).getId()));
				selectedMuameleList.get(i).setMuzekkereId(MUZEKKERE_MAAS);

				muameleDAO.kaydet(selectedMuameleList.get(i));

				MasrafBilgisi masraf = masrafEkle(selectedMuameleList.get(i));

				masraf.setMasrafMiktari(11.0);
				masraf.setMasrafAciklama("Maaş Müzekkere Tebligat Masrafı");

				new MasrafDAO().kaydet(masraf);
				}
				}
				else
				{
					FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Unuttunuz!",
							"Maaş Müzekkeresi için borçlu seçmelisiniz!.");

					RequestContext.getCurrentInstance().showMessageInDialog(message);
					
				}

			}

			else if (muzekkereTipiId == MUZEKKERE_TAPU) {

				TapuBilgisi bilgi = new TapuBilgisi();

				HashMap<String, List<TapuBilgisi>> map = new HashMap<String, List<TapuBilgisi>>();

				for (int i1 = 0; i1 < selectedMuameleList.get(i).getTapuBilgisiListesi().size(); i1++) {

					if (map.get(selectedMuameleList.get(i).getTapuBilgisiListesi().get(i1).getIlce()) != null) {

						bilgi = new TapuBilgisi();

						bilgi.setTapuKayitAciklama(
								selectedMuameleList.get(i).getTapuBilgisiListesi().get(i1).getIl().toUpperCase() + " "
										+ selectedMuameleList.get(i).getTapuBilgisiListesi().get(i1).getIlce()
												.toUpperCase()
										+ " ADA :" + selectedMuameleList.get(i).getTapuBilgisiListesi().get(i1).getAda()
										+ " PARSEL :"
										+ selectedMuameleList.get(i).getTapuBilgisiListesi().get(i1).getParsel() + " / "
										+ selectedMuameleList.get(i).getTapuBilgisiListesi().get(i1).getAciklama());
						bilgi.setId(selectedMuameleList.get(i).getTapuBilgisiListesi().get(i1).getId());

						map.get(selectedMuameleList.get(i).getTapuBilgisiListesi().get(i1).getIlce()).add(bilgi);

					} else {

						bilgi = new TapuBilgisi();

						List<TapuBilgisi> liste = new ArrayList<>();

						map.put(selectedMuameleList.get(i).getTapuBilgisiListesi().get(i1).getIlce(), liste);

						bilgi.setTapuKayitAciklama(
								selectedMuameleList.get(i).getTapuBilgisiListesi().get(i1).getIl().toUpperCase() + " "
										+ selectedMuameleList.get(i).getTapuBilgisiListesi().get(i1).getIlce()
												.toUpperCase()
										+ " ADA :" + selectedMuameleList.get(i).getTapuBilgisiListesi().get(i1).getAda()
										+ " PARSEL :"
										+ selectedMuameleList.get(i).getTapuBilgisiListesi().get(i1).getParsel() + " / "
										+ selectedMuameleList.get(i).getTapuBilgisiListesi().get(i1).getAciklama());
						bilgi.setId(selectedMuameleList.get(i).getTapuBilgisiListesi().get(i1).getId());

						map.get(selectedMuameleList.get(i).getTapuBilgisiListesi().get(i1).getIlce()).add(bilgi);

					}

				}

				for (Entry<String, List<TapuBilgisi>> entry : map.entrySet()) {
					String key = entry.getKey();
					List<TapuBilgisi> value = entry.getValue();

					selectedMuameleList.get(i).setTapuAciklama("");
					selectedMuameleList.get(i).setHaczeEsasMalId("");

					String id = "";
					String aciklama = "";
					for (int i1 = 0; i1 < value.size(); i1++) {

						aciklama = aciklama + value.get(i1).getTapuKayitAciklama();

						if (value.size() - 1 != i1)
							aciklama = aciklama + "<br>";

						id = id + value.get(i1).getId();

						if (value.size() - 1 != i1)

							id = id + ",";

					}

					selectedMuameleList.get(i).setTapuAciklama(aciklama);
					selectedMuameleList.get(i).setHaczeEsasMalId(id);

					muameleDAO.kaydet(selectedMuameleList.get(i));

				}

				MasrafBilgisi masraf = masrafEkle(selectedMuameleList.get(i));

				masraf.setMasrafMiktari(11.0);
				masraf.setMasrafAciklama("Tapu Müzekkere Tebligat Masrafı");
				masraf.setMasrafTipiId(4);

				new MasrafDAO().kaydet(masraf);

				MasrafBilgisi masraf2 = masrafEkle(selectedMuameleList.get(i));

				masraf2.setMasrafMiktari(5.10);
				masraf2.setMasrafAciklama("Tapu Posta Pulu Masrafı");
				masraf2.setMasrafTipiId(4);

				new MasrafDAO().kaydet(masraf2);

			} else if (muzekkereTipiId == TALEP_ARAC_SERH) {

				List<HaczeEsasMalBilgisi> liste = new HaczeEsasMalBilgisiDAO()
						.liste(selectedMuameleList.get(i).getBorcluId(), TALEP_ARAC_SERH);

				selectedMuameleList.get(i).setHaczeEsasMalId(String.valueOf(liste.get(0).getId()));
				selectedMuameleList.get(i).setMuzekkereId(TALEP_ARAC_SERH);

				muameleDAO.kaydet(selectedMuameleList.get(i));

			}

		}

		listePDF();

	}

	public void listePDF() {

		List<JasperPrint> listJasperPrint = new ArrayList<JasperPrint>();

		HashMap<String, List<JasperPrint>> mapList = new HashMap<String, List<JasperPrint>>();

		HashMap<String, List<Muamele>> mapTebligatListesi = new HashMap<String, List<Muamele>>();

		try {

			List<Muamele> tebligatListesi = new ArrayList<Muamele>();

			for (int i = 0; i < selectedMuameleList.size(); i++) {

				List<Muamele> muameleListe = new MuameleDAO()
						.getMuameleList(selectedMuameleList.get(i).getIcraDosyaID(), muzekkereTipiId);

				for (int j = 0; j < muameleListe.size(); j++) {

					Muamele muamele = muameleListe.get(j);

					if (muzekkereTipiId == MUZEKKERE_MAAS) {

						List<HaczeEsasMalBilgisi> liste = new HaczeEsasMalBilgisiDAO()
								.liste(selectedMuameleList.get(i).getBorcluId(), MUZEKKERE_MAAS);

						muamele.setBorcluIsyeriAdres(liste.get(0).getMuhatapAdresi());
						muamele.setBorcluIsyeriAdi(liste.get(0).getMuhatapAdi());

						muzekkereTalep = "maashacizmuzekkeresigenel";

						if (chooseEvrak(4)) {
							listJasperPrint.add(new MuzekkereJasper().getTalepler("maas_talep", muamele));

						}

					}

					else if (muzekkereTipiId == MUZEKKERE_TAPU) {
						muzekkereTalep = "tapuhacizmuzekkeresinokta";

						muamele.setTapuMudurlugu(muamele.getTapuAciklama().split(" ").length > 0
								? muamele.getTapuAciklama().split(" ")[1] + " "
										+ muamele.getTapuAciklama().split(" ")[0]
								: "");

						muamele.setTapuMudurluguIlce(muamele.getTapuAciklama().split(" ").length > 0
								? muamele.getTapuAciklama().split(" ")[1] : "");

						List<SubReport> liste = new ArrayList<SubReport>();

						for (int i1 = 0; i1 < muamele.getTapuAciklama().split("<br>").length; i1++) {
							SubReport report = new SubReport();
							report.setKayit(muamele.getTapuAciklama().split("<br>")[i1]);
							liste.add(report);

						}

						muamele.setSubReportList(liste);

					} else if (muzekkereTipiId == TALEP_ARAC_SERH) {
						muzekkereTalep = "tapuhacizmuzekkeresinokta";
						List<HaczeEsasMalBilgisi> liste = new HaczeEsasMalBilgisiDAO().liste(muamele.getBorcluId(),
								TALEP_ARAC_SERH);

						muamele.setHaczeEsasMalId(String.valueOf(liste.get(0).getId()));
						muamele.setPlaka(liste.get(0).getAracPlakaNo());
						muamele.setMuzekkereId(TALEP_ARAC_SERH);

					}

					muamele.setBarkod(new GenelYazdirBean().createBarcode(muamele.getBarkodTxt()));

					if (muzekkereTipiId > 10)
						listJasperPrint.add(new MuzekkereJasper().getMuzekkere(muzekkereTalep, muamele));

					else if (muzekkereTipiId == TALEP_ARAC_SERH)
						listJasperPrint.add(new MuzekkereJasper().getTalepler("aracserhitalebi", muamele));

					else {
						if (chooseEvrak(1)) {
							// listJasperPrint.add(new
							// MuzekkereJasper().getMuzekkere(muzekkereTalep,
							// muamele));
							// listJasperPrint.add(new
							// MuzekkereJasper().getMuzekkere(muzekkereTalep,
							// muamele));

							if (mapList.get(muamele.getIcraMudurlugu()) != null) {
								mapList.get(muamele.getIcraMudurlugu())
										.add(new MuzekkereJasper().getMuzekkere(muzekkereTalep, muamele));
								mapList.get(muamele.getIcraMudurlugu())
								.add(new MuzekkereJasper().getMuzekkere(muzekkereTalep, muamele));


							} else {
								List<JasperPrint> liste = new ArrayList<JasperPrint>();

								liste.add(new MuzekkereJasper().getMuzekkere(muzekkereTalep, muamele));
								liste.add(new MuzekkereJasper().getMuzekkere(muzekkereTalep, muamele));

								mapList.put(muamele.getIcraMudurlugu(), liste);

							}

						}
						if (chooseEvrak(2)) {  // Tebligat Zarfı yazdırmak için
							// listJasperPrint.add(new
							// MuzekkereJasper().tebligatZarfiJasper(muamele, muzekkereTalep));

							if (mapList.get(muamele.getIcraMudurlugu()) != null) {
								mapList.get(muamele.getIcraMudurlugu())
										.add(new MuzekkereJasper().tebligatZarfiJasper(muamele, muzekkereTalep));

							} else {
								List<JasperPrint> liste = new ArrayList<JasperPrint>();

								liste.add(new MuzekkereJasper().tebligatZarfiJasper(muamele, muzekkereTalep));

								mapList.put(muamele.getIcraMudurlugu(), liste);

							}
						}

						if (chooseEvrak(5)) { // Dörtlü talep
							// listJasperPrint.add(new MuzekkereJasper().getTalepler("dortlu_talep",muamele));

							if (mapList.get(muamele.getIcraMudurlugu()) != null) {
								mapList.get(muamele.getIcraMudurlugu())
										.add(new MuzekkereJasper().getTalepler("dortlu_talep",muamele));

							} else {
								List<JasperPrint> liste = new ArrayList<JasperPrint>();

								liste.add(new MuzekkereJasper().getTalepler("dortlu_talep",muamele));

								mapList.put(muamele.getIcraMudurlugu(), liste);

							}

						}

						// listJasperPrint.add(new
						// MuzekkereJasper().tebligatListesiJasper(muamele,
						// muzekkereTalep));
					}

					// tebligatListesi.add(muamele);

					if (mapTebligatListesi.get(muamele.getIcraMudurlugu()) != null) {
						mapTebligatListesi.get(muamele.getIcraMudurlugu()).add(muamele);

					} else {

						List<Muamele> listeTebligat = new ArrayList<Muamele>();

						listeTebligat.add(muamele);

						mapTebligatListesi.put(muamele.getIcraMudurlugu(), listeTebligat);

					}

				}

			}

			if (chooseEvrak(3)) {
				for (Map.Entry<String, List<Muamele>> entry : mapTebligatListesi.entrySet()) {

					mapList.get(entry.getKey())
							.add(new MuzekkereJasper().tebligatListesi(entry.getValue(), muzekkereTalep));

				}

				// listJasperPrint.add(new
				// MuzekkereJasper().tebligatListesi(tebligatListesi,
				// muzekkereTalep));

			}

			for (Map.Entry<String, List<JasperPrint>> entry : mapList.entrySet()) {

				listJasperPrint.addAll(entry.getValue());

			}

			String path = genelPath + muzekkereTalep + ".pdf";

			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setExporterInput(SimpleExporterInput.getInstance(listJasperPrint));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(path));
			SimplePdfExporterConfiguration config = new SimplePdfExporterConfiguration();
			config.setCreatingBatchModeBookmarks(true);
			exporter.exportReport();

			// Oluşturulan PDF'lerin Gösterimi Sağlanır

			pdf = path;

		} catch (Exception e) {

			System.out.println("Hata muzekkere toplu bean duzenle " + e.getMessage());

		}

	}

	public boolean chooseEvrak(int id) {
		boolean chooseEvrak = false;

		for (int i = 0; i < selectedEvrak.length; i++) {

			if (selectedEvrak[i] == id)
				chooseEvrak = true;
		}

		return chooseEvrak;
	}

	public MasrafBilgisi masrafEkle(Muamele muamele) {
		MasrafBilgisi masrafT = new MasrafBilgisi();

		masrafT.setBorcluId(muamele.getBorcluId());
		masrafT.setIcra_dosyasi_id(muamele.getIcraDosyaID());
		masrafT.setIcra_dosyasi_no(muamele.getIcraDosyaNo());
		masrafT.setMasrafTarihi(new Date());
		masrafT.setMuvekkilAdi(muamele.getMuvekkilAdi());

		return masrafT;
	}

	public String barkodUret(Muamele muamele) {
		String uretilenBarkod = "";
		try {

			String barkod = null;
			Posta posta = null;
			PostaDAO postaDAO = new PostaDAO();

			barkod = postaDAO.checkBarkod(muamele.getIcraDosyaID());
			posta = postaDAO.BarkodVer();

			if (barkod == null || muamele.getBarkodTxt() == "") {

				uretilenBarkod = posta.getBarkod();
				posta.setDurum(1);
				posta.setIcra_dosya_id(muamele.getIcraDosyaID());
				postaDAO.Duzenle(posta);

			} else {
				uretilenBarkod = barkod;
			}
		} catch (Exception e) {
			System.out.println("Hata muamelebean barkoduret :" + e.getMessage());
		}

		return uretilenBarkod;

	}

	public void listele() {
		try {

			if (basTarih != null || bitTarih != null) {

				List<Integer> icraDosyaListesi = dao.getIcraDosyaListesi(alacakliId, icraMudurluguId, muzekkereTipiId,
						icraDosyaNo, convertDateToString(basTarih), convertDateToString(bitTarih));

				selectedMuameleList = new ArrayList<Muamele>();

				muameleList = new ArrayList<Muamele>();

				for (int i = 0; i < icraDosyaListesi.size(); i++) {

					Muamele muamele = muameleDAO.getMuamele(icraDosyaListesi.get(i));

					muamele.setMumaleTarihi(new Date());

					muamele.setBorcMiktari(muamele.getToplamAlacak() - muamele.getTahsilatMiktari());

					muamele.setBorcMiktari(Double
							.valueOf(new DecimalFormat("0.00").format(muamele.getBorcMiktari()).replace(",", ".")));

					muamele.setPttAdi("PTT İSKİTLER MERKEZ MÜDÜRLÜĞÜ");
					muamele.setAvukatIBAN("TR3000 1230 0067 1038 9292 8100");

					muamele.setTapuBilgisiListesi(getTapuBilgisi(muamele));

					muamele.setMuzekkereId(muzekkereTipiId);

					muameleList.add(muamele);

				}
			} else {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Unuttunuz!",
						"Başlangıç ve Bitiş Tarihi girmelisiniz!.");

				RequestContext.getCurrentInstance().showMessageInDialog(message);
			}

		} catch (Exception e) {

			System.out.println("Muzekkere Toplu Listele :" + e.getMessage());
			// TODO: handle exception
		}

	}

	public List<TapuBilgisi> getTapuBilgisi(Muamele muamele) {
		List<TapuBilgisi> tapuList = new ArrayList<TapuBilgisi>();

		List<HaczeEsasMalBilgisi> liste = new HaczeEsasMalBilgisiDAO().liste(muamele.getBorcluId(), 1); // Gayrimenkul
																										// bilgisi
																										// hacze
																										// mal
																										// bilgisi
																										// kaydından
																										// alınıyor

		for (int i = 0; i < liste.size(); i++) {

			TapuBilgisi tapu = new TapuBilgisi();
			tapu.setId(liste.get(i).getId());
			tapu.setAciklama(liste.get(i).getTapuAciklama());
			tapu.setIl(liste.get(i).getIlAdi());
			tapu.setIlce(liste.get(i).getIlceAdi());
			tapu.setIlId(liste.get(i).getTapuIlId());
			tapu.setIlceId(liste.get(i).getTapuIlceId());
			tapu.setAda(liste.get(i).getTapuCiltNo());
			tapu.setParsel(liste.get(i).getTapuParsel());
			tapu.setTapuMudurlugu(liste.get(i).getTapuSicilMudurluk());

			tapuList.add(tapu);
		}

		return tapuList;
	}

	public String getPdf() {

		if (muameleList.isEmpty()) {
			pdf = "./pdfler/default.pdf?pfdrid_c=true";

		} else {

			pdf = "./pdfler/" + muzekkereTalep + ".pdf?pfdrid_c=true";

		}

		return pdf;
	}

	public void setPdf(String pdf) {
		this.pdf = pdf;
	}

	public Integer[] getSelectedEvrak() {
		return selectedEvrak;
	}

	public void setSelectedEvrak(Integer[] selectedEvrak) {
		this.selectedEvrak = selectedEvrak;
	}

	public List<Muamele> getMuameleList() {
		return muameleList;
	}

	public void setMuameleList(List<Muamele> muameleList) {
		this.muameleList = muameleList;
	}

	public List<ComboItem> getAlacakliListe() {
		return alacakliListe;
	}

	public void setAlacakliListe(List<ComboItem> alacakliListe) {
		this.alacakliListe = alacakliListe;
	}

	public int getAlacakliId() {
		return alacakliId;
	}

	public void setAlacakliId(int alacakliId) {
		this.alacakliId = alacakliId;
	}

	public int getIcraMudurluguId() {
		return icraMudurluguId;
	}

	public void setIcraMudurluguId(int icraMudurluguId) {
		this.icraMudurluguId = icraMudurluguId;
	}

	public int getMuzekkereTipiId() {
		return muzekkereTipiId;
	}

	public void setMuzekkereTipiId(int muzekkereTipiId) {
		this.muzekkereTipiId = muzekkereTipiId;
	}

	public String getIcraDosyaNo() {
		return icraDosyaNo;
	}

	public void setIcraDosyaNo(String icraDosyaNo) {
		this.icraDosyaNo = icraDosyaNo;
	}

	public Date getBasTarih() {
		return basTarih;
	}

	public void setBasTarih(Date basTarih) {
		this.basTarih = basTarih;
	}

	public Date getBitTarih() {
		return bitTarih;
	}

	public void setBitTarih(Date bitTarih) {
		this.bitTarih = bitTarih;
	}

	public List<Muamele> getSelectedMuameleList() {
		return selectedMuameleList;
	}

	public void setSelectedMuameleList(List<Muamele> selectedMuameleList) {
		this.selectedMuameleList = selectedMuameleList;
	}

	public List<ComboItem> getMuzekkereTipListe() {
		return muzekkereTipListe;
	}

	public void setMuzekkereTipListe(List<ComboItem> muzekkereTipListe) {
		this.muzekkereTipListe = muzekkereTipListe;
	}

}
