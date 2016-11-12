package semiramis.uyap.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

import com.a.a.a.g.m.s;

import semiramis.uyap.dao.SgkDAO;
import semiramis.uyap.model.Sgk;

@ManagedBean(name = "sgkBean")
@SessionScoped
public class SgkBean {
	private static XSSFSheet xlsTable;

	public void sgkFileUpload(FileUploadEvent event) {

		UploadedFile uploadedFile = (UploadedFile) event.getFile();

		File dosya = new File("tmp.xlsx");
		try {
			FileUtils.copyInputStreamToFile(uploadedFile.getInputstream(), dosya);

			XSSFWorkbook workbook = new XSSFWorkbook(dosya);

			xlsTable = workbook.getSheetAt(0);

			workbook.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("SgkBean.fileupload :" + e.getMessage());
		}

	}

	public void saveSGKs() {
		List sgks = new ArrayList<Sgk>();
		List returnList = null;
		if (xlsTable == null)
			return;
		int rowNum = xlsTable.getLastRowNum() + 1;
		int cellNum = xlsTable.getRow(0).getLastCellNum();

		try {
			if (cellNum == 8 && xlsTable.getRow(0).getCell(7).getStringCellValue().trim().toLowerCase()
					.equals("işyeri sicil no")) {

				for (int i = 0; i < rowNum; i++) {
					Row row = xlsTable.getRow(i);
					String tcNo = "";
					String sonuc = "";
					String sicilNo = "";
					if (row.getCell(2) != null) {
						Cell cell = row.getCell(2);
						cell.setCellType(Cell.CELL_TYPE_STRING);
						tcNo = cell.getStringCellValue();
					}
					if (row.getCell(6) != null && row.getCell(6).getStringCellValue().length() > 0) {
						sonuc = String.valueOf(row.getCell(6).getStringCellValue());
					}
					if (row.getCell(7) != null && row.getCell(7).getStringCellValue().trim().length() > 0) {
						sicilNo = row.getCell(7).getStringCellValue();
					}
					Sgk sgk = parseExcellString(tcNo, sonuc, sicilNo);
					if (sgk != null)
						sgks.add(sgk);
				}
				returnList = SgkDAO.getInstance().saveSGKs(sgks);
				FacesContext context = FacesContext.getCurrentInstance();

				if (returnList.size() < 1) {
					context.addMessage(null,
							new FacesMessage("Sisteme Aktarım Mesajı", "İşlem Başarı ile Gerçekleştirilmiştir. "));
				} else {
					context.addMessage(null,
							new FacesMessage("Sisteme Aktarım Mesajı",
									"Toplam : " + sgks.size() + " adet kayıttan " + returnList.size()
											+ " Adet Dosya sisteme kayıtlı olmadığı için sisteme aktarılamadı! "));
				}

			} else {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage("Sisteme Aktarım Mesajı",
						"Seçilen dosya uygun formatta değildir. Lütfen uygun formatta dosya seçiniz! "));

			}
			xlsTable = null;

		} catch (Exception e) {
			System.out.println("SgkBean.saveSGKs: " + e.getMessage());
		}
	}

	private Sgk parseExcellString(String tcNo, String sonuc, String sicilNo) {
		String check = sonuc.toUpperCase().trim();
		Sgk sgk = new Sgk();
		if (check.contains("KAYDI YOK") || check.contains("AYRILDI"))
			return null;
		if (sicilNo.equals("") || sicilNo.equals(null) || sicilNo.length() < 1) {
			sicilNo = "-";

			if (check.contains("EMEKL")) {
				String[] arr = sonuc.split(";");
				if (arr.length > 0) {
					sgk.setBorcluSigortaStatusu(arr[0]);
					sgk.setIsYeriAdress("-");
					sgk.setTcNo(tcNo);
					sgk.setIsYeriSicilNo(sicilNo);
				}
			}
		}
		if (check.contains("ÇALIŞANI")) {
			if (check.contains("KAMU")) {
				String[] arr = check.split(":");
				if (arr.length > 0) {
					sgk.setBorcluSigortaStatusu(arr[0]);
					String[] arr2 = arr[1].split("-  İŞE BAŞLAMA");
					if (arr2.length > 0)
						sgk.setIsYeriAdress(arr2[0]);
				}
				String[] tmp = sgk.getIsYeriAdress().split("-");
				if (tmp.length > 1) {
					sgk.setIsYeri(tmp[0]);
					sgk.setIsYeriAdress(tmp[1]);
				}

				sgk.setTcNo(tcNo);
				sgk.setIsYeriSicilNo(sicilNo);

			} else if (check.contains("SSK")) {
				Matcher m = Pattern.compile("\\(([^)]+)\\)").matcher(check);
				while (m.find()) {
					sgk.setIsYeriAdress(m.group(1));
				}
				String[] tmp = sgk.getIsYeriAdress().split("-");
				if (tmp.length > 1) {
					sgk.setIsYeri(tmp[0]);
					sgk.setIsYeriAdress(tmp[1]);
				}

				String[] arr = check.split("-");
				if (arr.length > 0)
					sgk.setBorcluSigortaStatusu(arr[0]);
				sgk.setTcNo(tcNo);
				sgk.setIsYeriSicilNo(sicilNo);
			}
		}

		// System.out.println("tc no : " + sgk.getTcNo() + " ** " + " is yer : "
		// + sgk.getIsYeri() + " ** "
		// + " is adress : " + sgk.getIsYeriAdress() + " ** " + "sicil no : " +
		// sgk.getIsYeriSicilNo());

		return sgk;
	}
}
