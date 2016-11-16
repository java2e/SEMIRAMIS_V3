package semiramis.uyap.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.format.CellFormatType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import semiramis.operasyon.dao.BorcluBilgisiDAO;
import semiramis.operasyon.model.HaczeEsasMalBilgisi;
import semiramis.uyap.dao.EgmDAO;

@ManagedBean(name = "egmBean")
@ViewScoped
public class EGMBean {

	private static XSSFSheet xlsTable;
	private int SONUC = 6;
	private int ARAC_DETAY = 7;
	private int ARAC_HACIZ_BILGILERI = 8;

	private EgmDAO dao = new EgmDAO();

	public void egmFileUpload(FileUploadEvent event) {

		UploadedFile uploadedFile = (UploadedFile) event.getFile();

		File dosya = new File("tmp3.xlsx");
		try {
			FileUtils.copyInputStreamToFile(uploadedFile.getInputstream(), dosya);

			XSSFWorkbook workbook = new XSSFWorkbook(dosya);

			xlsTable = workbook.getSheetAt(0);

			workbook.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("EGMBean :" + e.getMessage());
		}

	}

	public void saveEGMs() {
		if (xlsTable == null)
			return;
		int rowNum = xlsTable.getLastRowNum();
		Row r = xlsTable.getRow(0);
		int cellNum = r.getLastCellNum();
		List<HaczeEsasMalBilgisi> list = new ArrayList<>();
		String message = "";
		if (cellNum == 9
				&& r.getCell(cellNum - 1).getStringCellValue().trim().toLowerCase().equals("araç haciz bilgileri")) {
			String tcNo = "";
			HaczeEsasMalBilgisi malBilgisi = null;
			for (int i = 0; i < rowNum; i++) {
				Row row = xlsTable.getRow(i);
				if (row.getCell(2) != null && row.getCell(2).toString().length() > 0) {
					Cell cell = row.getCell(2);
					cell.setCellType(Cell.CELL_TYPE_STRING);
					tcNo = cell.getStringCellValue();
				}

				int borcluId = dao.getBorcluID(tcNo);
				if (borcluId != 0) {
					System.out.println("borcluId : " + borcluId);
					if (row.getCell(SONUC) != null && row.getCell(SONUC).getStringCellValue().length() > 0) {
						String plakas = row.getCell(SONUC).getStringCellValue();
						plakas = plakas.replace("\n", "_");
						String[] arr = plakas.split(",");
						ArrayList<String> set = new ArrayList<>();
						HashMap<String, String> map = new HashMap<>();
						if (arr.length > 0) {
							for (String s : arr) {
								String array[] = s.split("-");
								if (array.length > 0)
									set.add(array[0]);
							}
						}

						if (row.getCell(ARAC_DETAY) != null
								&& row.getCell(ARAC_DETAY).getStringCellValue().length() > 0) {
							String detays = row.getCell(ARAC_DETAY).getStringCellValue();
							detays = detays.replace("\n", " ");
							for (String s : set) {
								detays = detays.replace(s.trim(), "#");
							}
							String detayArr[] = detays.split("#");
							List<String> l = new ArrayList<String>(Arrays.asList(detayArr));
							l.removeAll(Arrays.asList("", null));
							if (l.size() == set.size()) {
								for (int j = 0; j < l.size(); j++) {
									map.put(set.get(j), l.get(j).trim());
								}
							}

						}

						if (row.getCell(ARAC_HACIZ_BILGILERI) != null
								&& row.getCell(ARAC_HACIZ_BILGILERI).getStringCellValue().length() > 0) {
							String hacizInfo = row.getCell(ARAC_HACIZ_BILGILERI).getStringCellValue().trim();
							hacizInfo.replace("\n", " ");
							for (String s : set) {
								hacizInfo = hacizInfo.replace(s.trim(), "ozgen");
							}
							System.out.println(hacizInfo);
							String hacizArr[] = hacizInfo.split("ozgen");
							List<String> l = new ArrayList<String>(Arrays.asList(hacizArr));
							// for (String string : l) {
							// System.out.println(string + " ozgen");
							// }
							l.removeAll(Arrays.asList("", null));
							if (l.size() == set.size()) {

								for (int j = 0; j < l.size(); j++) {
									map.put(set.get(j), map.get(set.get(j)) + "\n" + l.get(j).trim());
								}
							}
						}
						for (String key : map.keySet()) {
							malBilgisi = new HaczeEsasMalBilgisi();
							malBilgisi.setBorcluId(borcluId);
							malBilgisi.setIcraDosyaId(dao.getIcraDosyaID(borcluId));
							malBilgisi.setAracPlakaNo(key);
							malBilgisi.setAracTipiId(3);// 3: ARAC
							malBilgisi.setDigerBilgiler(map.get(key));
							list.add(malBilgisi);
						}

					}

				}
			}

			for (HaczeEsasMalBilgisi h : list) {
				System.out.println("borcLU: " + h.getBorcluId() + "  icrad : " + h.getIcraDosyaId() + "   plaka : "
						+ h.getAracPlakaNo() + "   detay : " + h.getDigerBilgiler());
			}
			List returnList = dao.saveEGMs(list);
			FacesContext context = FacesContext.getCurrentInstance();
			if (returnList.size() < 1) {
				context.addMessage(null,
						new FacesMessage("Sisteme Aktarım Mesajı", "İşlem Başarı ile Gerçekleştirilmiştir. "));
			} else {
				context.addMessage(null,
						new FacesMessage("Sisteme Aktarım Mesajı",
								"Toplam : " + list.size() + " adet kayıttan " + returnList.size()
										+ " Adet Dosya sisteme kayıtlı olmadığı için sisteme aktarılamadı! "));
			}

		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Sisteme Aktarım Mesajı",
					"Seçilen dosya uygun formatta değildir. Lütfen uygun formatta dosya seçiniz! "));
		}

		xlsTable = null;
	}

}