package semiramis.uyap.controller;

import java.io.File;
import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import pelops.model.HaczeEsasMalBilgisi;
import semiramis.operasyon.dao.BorcluBilgisiDAO;
import semiramis.uyap.dao.EgmDAO;

@ManagedBean(name = "egmBean")
@ViewScoped
public class EGMBean {

	private static XSSFSheet xlsTable;

	private int ARAC_PLAKA = 30;
	private int ARAC_MARKA = 31;
	private int ARAC_MODEL = 29;
	private int ARAC_CINS = 28;

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
		String message = "";
		if (cellNum == 50
				&& r.getCell(cellNum - 1).getStringCellValue().trim().toLowerCase().equals("taşınma tarihi")) {
			if (r.getCell(31).getStringCellValue().toLowerCase().trim().equals("araç marka")
					&& r.getCell(30).getStringCellValue().trim().toLowerCase().equals("araç plaka no")
					&& r.getCell(29).getStringCellValue().trim().toLowerCase().equals("araç model")
					&& r.getCell(28).getStringCellValue().trim().toLowerCase().equals("araç cins")) {
				String tcNo = "";
				HaczeEsasMalBilgisi malBilgisi = null;
				for (int i = 1; i < rowNum; i++) {
					Row row = xlsTable.getRow(i);
					if (row.getCell(0) != null) {
						Cell cell = row.getCell(0);
						cell.setCellType(Cell.CELL_TYPE_STRING);
						tcNo = cell.getStringCellValue();
					}

					int borcluId = dao.getBorcluID(tcNo);
					if (borcluId != 0) {

						if (row.getCell(ARAC_PLAKA) != null && row.getCell(ARAC_CINS) != null
								&& row.getCell(ARAC_MARKA) != null && row.getCell(ARAC_MODEL) != null) {
							malBilgisi = new HaczeEsasMalBilgisi();
							malBilgisi.setBorcluId(borcluId);
							malBilgisi.setIcraDosyaId(dao.getIcraDosyaID(tcNo));
							Cell cell3 = row.getCell(ARAC_CINS);
							cell3.setCellType(Cell.CELL_TYPE_STRING);
							malBilgisi.setAracAracTipi(cell3.getStringCellValue().toUpperCase());
							Cell cell = row.getCell(ARAC_PLAKA);
							cell.setCellType(Cell.CELL_TYPE_STRING);
							malBilgisi.setAracPlakaNo(cell.getStringCellValue().toUpperCase());

							Cell cell4 = row.getCell(ARAC_MARKA);
							cell4.setCellType(Cell.CELL_TYPE_STRING);
							Cell cell2 = row.getCell(ARAC_MODEL);
							cell2.setCellType(Cell.CELL_TYPE_STRING);
							malBilgisi.setDigerBilgiler("MARKA : " + cell4.getStringCellValue().toUpperCase()
									+ " MODEL : " + cell2.getStringCellValue().toUpperCase());

							dao.saveEGMVehicle(malBilgisi);
						}

					} else {
						message += "TC No : " + tcNo + ", ";
					}

				}
				FacesContext context = FacesContext.getCurrentInstance();
				if (message.equals("")) {
					if (malBilgisi != null) {
						context.addMessage(null,
								new FacesMessage("Sisteme Aktarım Mesajı", "İşlem Başarı ile Gerçekleştirilmiştir. "));
					} else {
						context.addMessage(null, new FacesMessage("Sisteme Aktarım Mesajı", "Aktarım sırasında hata!"));
					}
				} else {
					message += " numaralı borclular sistemde mevcut olmadığı için kayıt yapılamamaktadır!";
					context.addMessage(null, new FacesMessage("Sisteme Aktarım Mesajı", message));
				}

			}

		}
		xlsTable = null;
	}

}