package semiramis.operasyon.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import pelops.controller.AktifBean;
import pelops.model.MuameleIslemleri;
import pelops.model.TebligatListesi;
import pelops.model.TebligatZarfi;
import pelops.reports.controller.GenelYazdirBean;
import semiramis.operasyon.dao.HaczeEsasMalBilgisiDAO;
import semiramis.operasyon.model.HaczeEsasMalBilgisi;
import semiramis.operasyon.model.Muamele;

public class MuzekkereJasper 
{
	
	public final static int MUZEKKERE_MAAS = 2;
	public final static int MUZEKKERE_MAAS_TALEP = 2;

	public final static int MUZEKKERE_TAPU = 1;
	
	
	public  String genelPath =FacesContext.getCurrentInstance().getExternalContext()
			.getRealPath("/pdfler/");
			
	//FacesContext.getCurrentInstance().getExternalContext().getRealPath("\\pdfler\\")+"\\";
	
	public String zarfTipi="";
	
	public JasperPrint getMuzekkere(String path,Muamele muamele)
	{
		
		JasperPrint jasperPrint=null;
		
		try {
			
			
			ArrayList<Muamele> dataBeanList = new ArrayList<Muamele>();
			
			muamele.setBarkod(new GenelYazdirBean().createBarcode(muamele.getBarkodTxt()));
			
		
			dataBeanList.add(muamele);

			//HashMap<String, Object> parameters = new HashMap<String, Object>();
		

			String pathName = FacesContext.getCurrentInstance().getExternalContext()
					.getRealPath("/reports/talep_muzekkereler/" + path + ".jrxml");

			InputStream inputStream = new FileInputStream(pathName);

			File file = new File(
					genelPath+ path + ".pdf");
			file.delete();

			path = genelPath + path + ".pdf";

 			JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(dataBeanList);
			JasperDesign jasperDesign = new JasperDesign();
			jasperDesign = JRXmlLoader.load(inputStream);
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			jasperPrint = JasperFillManager.fillReport(jasperReport, null, beanColDataSource);
			// JasperExportManager.exportReportToPdfFile(jasperPrint, path);

		
			
		} catch (Exception e) {


			System.out.println("Hata muzekkereJasper getMuzekkere :"+e.getMessage());
			
		}
		
		return jasperPrint;
		
	}
	
	public JasperPrint getTalepler(String path,Muamele muamele)
	{
		
		JasperPrint jasperPrint=null;
		
		try {
			
			
			ArrayList<Muamele> dataBeanList = new ArrayList<Muamele>();
			
			muamele.setBarkod(new GenelYazdirBean().createBarcode(muamele.getBarkodTxt()));
			
		
			dataBeanList.add(muamele);

			HashMap<String, Object> parameters = new HashMap<String, Object>();
		

			String pathName = FacesContext.getCurrentInstance().getExternalContext()
					.getRealPath("/reports/talepler/" + path + ".jrxml");

			InputStream inputStream = new FileInputStream(pathName);

			File file = new File(
					genelPath+ path + ".pdf");
			file.delete();

			path = genelPath + path + ".pdf";

 			JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(dataBeanList);
			JasperDesign jasperDesign = new JasperDesign();
			jasperDesign = JRXmlLoader.load(inputStream);
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, beanColDataSource);
			// JasperExportManager.exportReportToPdfFile(jasperPrint, path);

		
			
		} catch (Exception e) {


			System.out.println("Hata muzekkereJasper getMuzekkere :"+e.getMessage());
			
		}
		
		return jasperPrint;
		
	}
	
	public JasperPrint tebligatZarfiJasper(Muamele muamele, String muzekkereTalep) throws Exception
	{

		JasperPrint jasperPrint=null;
		
		ArrayList<TebligatZarfi> dataBeanListForTebligat = new ArrayList<TebligatZarfi>();
		TebligatZarfi zarf = new TebligatZarfi();
		
		zarf.setMuameleTarihiTxt(muamele.getMuameleTarihiTxt());
		
		// Maaş Müzekkeresi Genel Durumunda 
		
		if(muamele.getMuzekkereId()==MUZEKKERE_MAAS)
		{
		
		zarf.setBorcluAdi(muamele.getBorcluIsyeriAdi().toUpperCase());
		zarf.setBorcluAdres(muamele.getBorcluIsyeriAdres().toUpperCase());
		zarf.setMuzekkereTalepAdi("Maaş Haciz Müzekkeresi (Genel) 1/4");
		zarfTipi="Maaş Haciz Müzekkeresi";

		
		}
		else if(muamele.getMuzekkereId()==MUZEKKERE_TAPU)
		{
			zarf.setBorcluAdi(muamele.getTapuAciklama().split(" ")[1]+" TAPU SİCİL MÜDÜRLÜĞÜ");
			zarf.setBorcluAdres(muamele.getTapuAciklama().split(" ")[1]+"/"+muamele.getTapuAciklama().split(" ")[0]);
			zarf.setMuzekkereTalepAdi("Tapu Haciz Müzekkeresi (Nokta)");
			zarfTipi="Tapu Haciz Müzekkeresi";
		}
		
		zarf.setIcraDosyaNo(muamele.getIcraDosyaNo());
		zarf.setIcraMudurluguAdi(muamele.getIcraMudurlugu());
		zarf.setAlacakliAdi(muamele.getMuvekkilAdi());
		zarf.setAvukatAdi(muamele.getAvukatAdi());
		zarf.setBarkod(muamele.getBarkod());
		

		dataBeanListForTebligat.add(zarf);

		HashMap<String, Object> parameters = new HashMap<String, Object>();

		String pathName = FacesContext.getCurrentInstance().getExternalContext()
				.getRealPath("/reports/talep_muzekkereler/tebligat_zarfi.jrxml");
		InputStream inputStream = new FileInputStream(pathName);
		JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(dataBeanListForTebligat);
		JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
		JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
		jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, beanColDataSource);

		return jasperPrint;

	}
	
	
	public JasperPrint tebligatListesiJasper(Muamele muamele, String muzekkereTalep) throws Exception {
		
		JasperPrint jasperPrint=null;
		
		HashMap<String, Object> parameters = new HashMap<String, Object>();

		ArrayList<TebligatListesi> dataBeanListForTebligat = new ArrayList<TebligatListesi>();
		TebligatListesi tebligatListesi = new TebligatListesi();
		
		// Maaş Müzekkeresi Genel Durumunda 
		
		if(muamele.getMuzekkereId()==MUZEKKERE_MAAS)
		{
		
		tebligatListesi.setBorcluAdi(muamele.getBorcluIsyeriAdi().toUpperCase());
		tebligatListesi.setIl(muamele.getIcraMudurlugu().split(" ")[0]);
		zarfTipi="Maaş Müzekkeresi";
		parameters.put("konu", zarfTipi);
		parameters.put("muvekkilAdi", muamele.getMuvekkilAdi());
		
		
		}
		else if(muamele.getMuzekkereId()==MUZEKKERE_TAPU)
		{
			tebligatListesi.setBorcluAdi(muamele.getBorcluAdSoyad());
			tebligatListesi.setIl(muamele.getTapuAciklama().split(" ")[1]);
			zarfTipi="Tapu Talep M.";
			parameters.put("konu", zarfTipi);
			parameters.put("muvekkilAdi", muamele.getMuvekkilAdi());
		
				
		}
		
		tebligatListesi.setIcraDosyaNo(muamele.getIcraDosyaNo());
		tebligatListesi.setIcraBilgi(muamele.getIcraMudurlugu());
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-YYYY");
		tebligatListesi.setTarih(sdf.format(new java.util.Date()));
		
		tebligatListesi.setBrcd(muamele.getBarkod());
		tebligatListesi.setKonu(zarfTipi);

		dataBeanListForTebligat.add(tebligatListesi);

		

		String pathName = FacesContext.getCurrentInstance().getExternalContext()
				.getRealPath("/reports/tebligat_listesi1.jrxml");
		InputStream inputStream = new FileInputStream(pathName);
		JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(dataBeanListForTebligat);
		JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
		JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
		jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, beanColDataSource);

		return jasperPrint;

	}
	
public JasperPrint tebligatListesi(List<Muamele> muameleList, String muzekkereTalep) throws Exception {
		
		JasperPrint jasperPrint=null;
		String listeTipi="tebligat_listesi1";
		
		
		HashMap<String, Object> parameters = new HashMap<String, Object>();

		ArrayList<TebligatListesi> dataBeanListForTebligat = new ArrayList<TebligatListesi>();
		
		
		for (int i = 0; i < muameleList.size(); i++) {
		
			Muamele muamele=muameleList.get(i);
			
			muamele.setBarkod(new GenelYazdirBean().createBarcode(muamele.getBarkodTxt()));

		
		TebligatListesi liste = new TebligatListesi();
		
		// Maaş Müzekkeresi Genel Durumunda 
		
		
		if(muamele.getMuzekkereId()==MUZEKKERE_MAAS)
		{
			
			List<HaczeEsasMalBilgisi> liste2 = new HaczeEsasMalBilgisiDAO().liste(muamele.getBorcluId(), MUZEKKERE_MAAS);
			
			muamele.setBorcluIsyeriAdres(liste2.get(0).getMuhatapAdresi());
			muamele.setBorcluIsyeriAdi(liste2.get(0).getMuhatapAdi());	
		
		liste.setBorcluAdi(muamele.getBorcluIsyeriAdi().toUpperCase());
		liste.setIl(muamele.getIcraMudurlugu().split(" ")[0]);
		zarfTipi="Maaş Müzekkeresi";
		parameters.put("konu", zarfTipi);
		parameters.put("muvekkilAdi", muamele.getMuvekkilAdi());
		
		
		}
		else if(muamele.getMuzekkereId()==MUZEKKERE_TAPU)
		{
			liste.setBorcluAdi(muamele.getTapuMudurlugu()+" TAPU SİCİL MÜD.");
			liste.setIl(muamele.getTapuAciklama().split(" ")[0]);
			zarfTipi="Tapu Talep M.";
			parameters.put("konu", zarfTipi);
			parameters.put("muvekkilAdi", muamele.getMuvekkilAdi());
			
			listeTipi="tebligat_listesi_tapu";
		
				
		}
		
		liste.setIcraDosyaNo(muamele.getIcraDosyaNo());
		liste.setIcraBilgi(muamele.getIcraMudurlugu());
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-YYYY");
		liste.setTarih(sdf.format(new java.util.Date()));
		
		liste.setBrcd(muamele.getBarkod());
		liste.setKonu(zarfTipi);

		dataBeanListForTebligat.add(liste);
		
		}

		

		String pathName = FacesContext.getCurrentInstance().getExternalContext()
				.getRealPath("/reports/"+listeTipi+".jrxml");
		InputStream inputStream = new FileInputStream(pathName);
		JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(dataBeanListForTebligat);
		JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
		JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
		jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, beanColDataSource);

		return jasperPrint;

	}

}
