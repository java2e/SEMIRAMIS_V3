package semiramis.uyap.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import semiramis.uyap.dao.MernisDAO;
import semiramis.uyap.model.Mernis;

@ManagedBean(name = "mernisBean")
@SessionScoped
public class MernisBean {

	private static XSSFSheet xlsTable;

	public void mernisFileUpload(FileUploadEvent event) {

		UploadedFile uploadedFile = (UploadedFile) event.getFile();

		File dosya = new File("tmp2.xlsx");
		try {
			FileUtils.copyInputStreamToFile(uploadedFile.getInputstream(), dosya);

			XSSFWorkbook workbook = new XSSFWorkbook(dosya);

			xlsTable = workbook.getSheetAt(0);

			workbook.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("MernisBean.fileupload :" + e.getMessage());
		}

	}

	public void saveMernis() {
		List mernises = new ArrayList<Mernis>();
		List returnList = null;
		if (xlsTable == null)
			return;
		int rowNum = xlsTable.getLastRowNum() + 1;
		int cellNum = xlsTable.getRow(0).getLastCellNum();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
		String tcNo = "";
		try {

			if (cellNum == 9
					&& xlsTable.getRow(0).getCell(7).getStringCellValue().trim().toLowerCase().equals("beyan tarihi")) {
				for (int i = 1; i < rowNum; i++) {
					Row row = xlsTable.getRow(i);
					Mernis mernis = new Mernis();

					if (row.getCell(1) != null && row.getCell(1).getStringCellValue().length() > 0) {
						mernis.setBorcluAdi(row.getCell(1).getStringCellValue());
					}
					if (row.getCell(2) != null) {
						Cell cell = row.getCell(2);
						cell.setCellType(Cell.CELL_TYPE_STRING);
						tcNo = cell.getStringCellValue();
						mernis.setTcNo(tcNo);
					}
					if (row.getCell(6) != null && row.getCell(6).getStringCellValue().length() > 0) {
						mernis.setOlumTarihi(dateFormat.parse(row.getCell(6).getStringCellValue()));
					}
					if (row.getCell(7) != null && row.getCell(7).getStringCellValue().length() > 0) {

						dateFormat.parse(row.getCell(7).getStringCellValue());
						mernis.setBeyan(dateFormat.parse(row.getCell(7).getStringCellValue()));
					}
					if (row.getCell(8) != null && row.getCell(8).getStringCellValue().length() > 0) {
						mernis.setSonuc(row.getCell(8).getStringCellValue());
					}

					mernises.add(mernis);
				}

				returnList = MernisDAO.getInstance().saveListOfMernis(mernises);
				FacesContext context = FacesContext.getCurrentInstance();

				if (returnList.size() < 1) {
					context.addMessage(null,
							new FacesMessage("Sisteme Aktarım Mesajı", "İşlem Başarı ile Gerçekleştirilmiştir. "));
				} else {
					context.addMessage(null,
							new FacesMessage("Sisteme Aktarım Mesajı",
									"Toplam : " + mernises.size() + " adet kayıttan " + returnList.size()
											+ " Adet Dosya sisteme kayıtlı olmadığı için sisteme aktarılamadı! "));
				}
			} else {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage("Sisteme Aktarım Mesajı",
						"Seçilen dosya uygun formatta değildir. Lütfen uygun formatta dosya seçiniz! "));

			}
			xlsTable = null;
		} catch (Exception e) {
			System.out.println("MernisBean.saveMernis: " + e.getMessage());
		}

	}

}
