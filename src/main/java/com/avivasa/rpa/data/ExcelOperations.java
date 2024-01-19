package com.avivasa.rpa.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.SkipException;

import com.avivasa.rpa.utiliy.EmailSend;
import com.avivasa.rpa.utiliy.log;

public class ExcelOperations {

	List<String[]> BESTahsilatList = new ArrayList<String[]>();
	List<String[]> BESTahsilatGelenDataList = new ArrayList<String[]>();

	static FileInputStream file;

	public boolean generateBESTahsilatContentReportExcel(String reportFileName)
			throws InterruptedException, IOException {

		try {
			File file = new File(GetBESTahsilatData.filePath + reportFileName);
			if (!file.exists())
				file.createNewFile();

			Workbook workbook = new XSSFWorkbook();
			Sheet sheetContentReport = workbook.createSheet("BESTahsilatSonuc");
			Sheet sheetGelenData = workbook.createSheet("AkbankEkstre");
			Sheet sheetKumulData = workbook.createSheet("BESTahsilatKumulData");

			Row rowsheetGelenData = sheetGelenData.createRow(0);

			Cell cell1 = rowsheetGelenData.createCell(0);
			Cell cell2 = rowsheetGelenData.createCell(1);
			Cell cell3 = rowsheetGelenData.createCell(2);
			Cell cell4 = rowsheetGelenData.createCell(3);
			Cell cell5 = rowsheetGelenData.createCell(4);
			Cell cell6 = rowsheetGelenData.createCell(5);
			Cell cell7 = rowsheetGelenData.createCell(6);

			cell1.setCellValue("Intikal Tarihi");
			cell2.setCellValue("Intikal Saati");
			cell3.setCellValue("Açıklama");
			cell4.setCellValue("PoliceNo");
			cell5.setCellValue("GelenPara");
			cell6.setCellValue("Banka");
			cell7.setCellValue("DekontNo");

			sheetGelenData.autoSizeColumn(0);
			sheetGelenData.autoSizeColumn(1);
			sheetGelenData.autoSizeColumn(2);
			sheetGelenData.autoSizeColumn(3);
			sheetGelenData.autoSizeColumn(4);
			sheetGelenData.autoSizeColumn(5);
			sheetGelenData.autoSizeColumn(6);

			Row rowContentReportData = sheetContentReport.createRow(0);

			Cell cell10 = rowContentReportData.createCell(0);
			Cell cell11 = rowContentReportData.createCell(1);
			Cell cell12 = rowContentReportData.createCell(2);
			Cell cell13 = rowContentReportData.createCell(3);
			Cell cell14 = rowContentReportData.createCell(4);
			Cell cell15 = rowContentReportData.createCell(5);
			Cell cell16 = rowContentReportData.createCell(6);
			Cell cell17 = rowContentReportData.createCell(7);
			Cell cell18 = rowContentReportData.createCell(8);
			Cell cell19 = rowContentReportData.createCell(9);

			cell10.setCellValue("Intikal Tarihi");
			cell11.setCellValue("Intikal Saati");
			cell12.setCellValue("Açıklama");
			cell13.setCellValue("PoliceNo");
			cell14.setCellValue("GelenPara");
			cell15.setCellValue("Banka");
			cell16.setCellValue("DekontNo");
			cell17.setCellValue("Ödeyen Bilgisi");
			cell18.setCellValue("Sonuç");
			cell19.setCellValue("Exception Sebebi");

			sheetContentReport.autoSizeColumn(0);
			sheetContentReport.autoSizeColumn(1);
			sheetContentReport.autoSizeColumn(2);
			sheetContentReport.autoSizeColumn(3);
			sheetContentReport.autoSizeColumn(4);
			sheetContentReport.autoSizeColumn(5);
			sheetContentReport.autoSizeColumn(6);
			sheetContentReport.autoSizeColumn(7);
			sheetContentReport.autoSizeColumn(8);
			sheetContentReport.autoSizeColumn(9);

			Row rowsheetKumulData = sheetKumulData.createRow(0);

			Cell cell20 = rowsheetKumulData.createCell(0);
			Cell cell21 = rowsheetKumulData.createCell(1);
			Cell cell22 = rowsheetKumulData.createCell(2);
			Cell cell23 = rowsheetKumulData.createCell(3);
			Cell cell24 = rowsheetKumulData.createCell(4);
			Cell cell25 = rowsheetKumulData.createCell(5);
			Cell cell26 = rowsheetKumulData.createCell(6);
			Cell cell27 = rowsheetKumulData.createCell(7);
			Cell cell28 = rowsheetKumulData.createCell(8);
			Cell cell29 = rowsheetKumulData.createCell(9);

			cell20.setCellValue("Intikal Tarihi");
			cell21.setCellValue("Intikal Saati");
			cell22.setCellValue("Açıklama");
			cell23.setCellValue("PoliceNo");
			cell24.setCellValue("GelenPara");
			cell25.setCellValue("Banka");
			cell26.setCellValue("DekontNo");
			cell27.setCellValue("Ödeyen Bilgisi");
			cell28.setCellValue("Sonuç");
			cell29.setCellValue("Exception Sebebi");

			sheetKumulData.autoSizeColumn(0);
			sheetKumulData.autoSizeColumn(1);
			sheetKumulData.autoSizeColumn(2);
			sheetKumulData.autoSizeColumn(3);
			sheetKumulData.autoSizeColumn(4);
			sheetKumulData.autoSizeColumn(5);
			sheetKumulData.autoSizeColumn(6);
			sheetKumulData.autoSizeColumn(7);
			sheetKumulData.autoSizeColumn(8);
			sheetKumulData.autoSizeColumn(9);

			// Write the output to a file
			FileOutputStream fileOut = new FileOutputStream(new File(GetBESTahsilatData.filePath + reportFileName));

			workbook.write(fileOut);
			fileOut.close();
			workbook.close();

		} catch (FileNotFoundException e) {
			log.error("Hata!! Rapor excel dosyasi olusturulamadi: " + e.toString());
			EmailSend.excelDosyasiOlusturmaHatasi();
			throw new SkipException("HATA: Rapor excel dosyasi olusturulamadi.");
		}
		return true;
	}

	public boolean deleteRow(String sheetName, String excelPath) throws IOException {

		XSSFWorkbook workbook = null;
		XSSFSheet sheet = null;

		try {
			FileInputStream file = new FileInputStream(new File(excelPath));
			workbook = new XSSFWorkbook(file);
			sheet = workbook.getSheet(sheetName);

			if (sheet == null) {
				return false;
			}
			int lastRowNum = sheet.getLastRowNum();

			for (int i = lastRowNum; i > 0; i--) {
				
				XSSFRow removingRow = sheet.getRow(i);
				if (removingRow != null) {
					sheet.removeRow(removingRow);
				}
			}
//	        
//	        if (rowNo >= 0 && rowNo < lastRowNum) {
//	            sheet.shiftRows(rowNo + 1, lastRowNum, -1);
//	        }
//	        if (rowNo == lastRowNum) {
//	            XSSFRow removingRow=sheet.getRow(rowNo);
//	            if(removingRow != null) {
//	                sheet.removeRow(removingRow);
//	            }
//	        }
			file.close();
			FileOutputStream outFile = new FileOutputStream(new File(excelPath));
			workbook.write(outFile);
			outFile.close();

		}

		catch (Exception e) {
			throw e;
		} finally {
			if (workbook != null)
				workbook.close();
		}
		return false;
	}

	public Boolean controlFileAndSendMail(String filePath, String dataFileName) throws InterruptedException {

		boolean statusBrowser = false;

		if (!isFileExists(filePath, dataFileName)) {
			EmailSend.dosyaBulunamadi(filePath, dataFileName);
			log.info(filePath + dataFileName + " isimli dosya bulunamamistir.");
			statusBrowser = true;
		}

		return statusBrowser;
	}

	public List<String[]> populateBESTahsilatKayitList1(String filePath, String tahsilatfilename)
			throws InterruptedException, IOException {

		try {
			file = new FileInputStream(new File(filePath + tahsilatfilename));

			// Create Workbook instance holding reference to .xlsx file
			XSSFWorkbook workbook = new XSSFWorkbook(file);

			// Get first/desired sheet from the workbook
			XSSFSheet sheet = workbook.getSheetAt(0);
			DataFormatter fmt = new DataFormatter();

			// Iterate through each rows one by one
			Iterator<Row> rowIterator = sheet.iterator();

			for (int i = 0; i < 1; i++) {
				if (rowIterator.hasNext())
					rowIterator.next();
			}

			while (rowIterator.hasNext()) {
				String row[] = new String[11];
				Row rows = rowIterator.next();
//	            log.info("rowIndex:" + row.getRowNum());
				// For each row, iterate through all the columns
				Iterator<Cell> cellIterator = rows.cellIterator();

				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
//	                log.info("columnIndex:" + cell.getColumnIndex());
					if (cell.getColumnIndex() == 11) {
						break;
					}

					// Check the cell type and format accordingly
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_NUMERIC:
						row[cell.getColumnIndex()] = fmt.formatCellValue(cell);
						// log.info(cell.getNumericCellValue() + "\t");
						break;
					case Cell.CELL_TYPE_STRING:
						row[cell.getColumnIndex()] = fmt.formatCellValue(cell);
						// log.info(cell.getStringCellValue() + "\t");
						break;
					case Cell.CELL_TYPE_BLANK:
						row[cell.getColumnIndex()] = fmt.formatCellValue(cell);
						// row[cell.getColumnIndex()] = fmt.toString();
						// log.info(cell.getStringCellValue() + "\t");
						break;
					}
				}
				BESTahsilatList.add(row);
			}
			file.close();
		} catch (FileNotFoundException e) {

		}
		return BESTahsilatList;
	}

	public List<String[]> populateBESTahsilatKayitList(int sheetCount, String reportFileName,
			List<String[]> kumulBESTahsilatEskiDataList) throws InterruptedException, IOException {

		try {
			file = new FileInputStream(new File(GetBESTahsilatData.filePath + reportFileName));

			// Create Workbook instance holding reference to .xlsx file
			XSSFWorkbook workbook = new XSSFWorkbook(file);

			// Get first/desired sheet from the workbook
			XSSFSheet sheet = workbook.getSheetAt(sheetCount);
			DataFormatter fmt = new DataFormatter();

			// Iterate through each rows one by one
			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				String row[] = new String[9];
				Row rows = rowIterator.next();
				Iterator<Cell> cellIterator = rows.cellIterator();

				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();

					// Check the cell type and format accordingly
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_NUMERIC:
						row[cell.getColumnIndex()] = fmt.formatCellValue(cell);
						break;
					case Cell.CELL_TYPE_STRING:
						row[cell.getColumnIndex()] = fmt.formatCellValue(cell);
						break;
					}
				}
				kumulBESTahsilatEskiDataList.add(row);
			}
			file.close();

		} catch (FileNotFoundException e) {
			log.error("Hata!! Rapor listesi doldurulamadi: " + e.toString());
			EmailSend.raporListesiDoldurmaHatasi();
			throw new SkipException("HATA: Rapor listesi doldurulamadi.");
		}
		return kumulBESTahsilatEskiDataList;
	}

	public List<String[]> populateBESTahsilatGelenDataList1(int sheetCount, String reportFileName,
			List<String[]> kumulBESTahsilatEskiDataList) throws InterruptedException, IOException {

		try {
			file = new FileInputStream(new File(GetBESTahsilatData.filePath + reportFileName));

			// Create Workbook instance holding reference to .xlsx file
			XSSFWorkbook workbook = new XSSFWorkbook(file);

			// Get first/desired sheet from the workbook
			XSSFSheet sheet = workbook.getSheetAt(sheetCount);
			DataFormatter fmt = new DataFormatter();

			// Iterate through each rows one by one
			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				String row[] = new String[9];
				Row rows = rowIterator.next();
				Iterator<Cell> cellIterator = rows.cellIterator();

				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();

					// Check the cell type and format accordingly
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_NUMERIC:
						row[cell.getColumnIndex()] = fmt.formatCellValue(cell);
						break;
					case Cell.CELL_TYPE_STRING:
						row[cell.getColumnIndex()] = fmt.formatCellValue(cell);
						break;
					}
				}
				BESTahsilatGelenDataList.add(row);
			}
			file.close();

		} catch (FileNotFoundException e) {
			log.error("Hata!! Rapor listesi doldurulamadi: " + e.toString());
			EmailSend.raporListesiDoldurmaHatasi();
			throw new SkipException("HATA: Rapor listesi doldurulamadi.");
		}
		return kumulBESTahsilatEskiDataList;
	}

	public List<String[]> populateBESTahsilatGelenDataList() throws InterruptedException, IOException {
		// fileName = "HesapHareketleri_29.11.2018_0083320.xlsx";
		// fileName="AkbankCollection.xls";
		// int rowCountAkbankExcel = 0;

		try {
			file = new FileInputStream(new File(GetBESTahsilatData.filePath + GetBESTahsilatData.reportFileName));

			XSSFWorkbook workbook = new XSSFWorkbook(file);

			XSSFSheet sheet = workbook.getSheetAt(1);
			DataFormatter fmt = new DataFormatter();

			Iterator<Row> rowIterator;
			rowIterator = sheet.iterator();

			while (rowIterator.hasNext()) {
				Row rows = rowIterator.next();

				String row[] = new String[10];

				Iterator<Cell> cellIterator = rows.cellIterator();

				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();

					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_NUMERIC:
						row[cell.getColumnIndex()] = fmt.formatCellValue(cell);
						// log.info(cell.getNumericCellValue() + "\t");
						break;
					case Cell.CELL_TYPE_STRING:
						row[cell.getColumnIndex()] = fmt.formatCellValue(cell);
						// log.info(cell.getStringCellValue() + "\t");
						break;

					}
				}
				BESTahsilatGelenDataList.add(row);
			}
			file.close();
		} catch (FileNotFoundException e) {

		}

		// System.out.println("Size of list: " + akbankList.size());
		log.info("Size of Akbanklist: " + BESTahsilatGelenDataList.size());

		return BESTahsilatGelenDataList;
	}

	public void insertListToExcel(List<String[]> list, String fileName, String sheetName, int columnCount)
			throws InterruptedException, IOException {

		try {

			int listSize = list.size()-1;
			file = new FileInputStream(new File(GetBESTahsilatData.filePath + fileName));
			Workbook workbook = new XSSFWorkbook(file);
			Sheet sheet = workbook.getSheet(sheetName);
			int rowNumber = sheet.getLastRowNum()+1;
			
			// for (int j = 0; j < list.size(); j++) 
			
			Row row = sheet.createRow(rowNumber);
			for (int i = 0; i < columnCount; i++) {
				Cell cell = row.createCell(i);
				cell.setCellValue(list.get(listSize)[i]);
				sheet.autoSizeColumn(i);
			}
//
			// Write the output to a file
			FileOutputStream fileOut = new FileOutputStream(new File(GetBESTahsilatData.filePath + fileName));
			workbook.write(fileOut);
			fileOut.close();
			workbook.close();

		} catch (FileNotFoundException e) {
			log.error("Hata!! Rapor excel dosyasi doldurulamadi: " + e.toString());
			EmailSend.excelDosyasiDoldurmaHatasi();
			throw new SkipException("HATA: Rapor excel dosyasi doldurulamadi.");
		}
	}

	
	public void insertListToExcel1(List<String[]> list, String fileName, String sheetName, int columnCount)
			throws InterruptedException, IOException {

		try {

			int listSize = list.size();
			file = new FileInputStream(new File(GetBESTahsilatData.filePath + fileName));
			Workbook workbook = new XSSFWorkbook(file);
			Sheet sheet = workbook.getSheet(sheetName);
			int rowNumber = sheet.getLastRowNum()+1;
			
			 for (int j = 0; j < list.size(); j++) 
			{
				 
			Row row = sheet.createRow(rowNumber);
			for (int i = 0; i < columnCount; i++) {
				Cell cell = row.createCell(i);
				cell.setCellValue(list.get(listSize)[i]);
				sheet.autoSizeColumn(i);
			}
		
			// Write the output to a file
			FileOutputStream fileOut = new FileOutputStream(new File(GetBESTahsilatData.filePath + fileName));
			workbook.write(fileOut);
			fileOut.close();
			workbook.close();
			}
		} catch (FileNotFoundException e) {
			log.error("Hata!! Rapor excel dosyasi doldurulamadi: " + e.toString());
			EmailSend.excelDosyasiDoldurmaHatasi();
			throw new SkipException("HATA: Rapor excel dosyasi doldurulamadi.");
		}
	}
	public void insertListGelenData(List<String[]> list, String fileName, String sheetName, int columnCount)
			throws InterruptedException, IOException {
		try {

			file = new FileInputStream(new File(GetBESTahsilatData.filePath + fileName));
			Workbook workbook = new XSSFWorkbook(file);
			Sheet sheet = workbook.getSheet(sheetName);
			
			int rowNumber = sheet.getLastRowNum()+1;
			
			for (int j = 0; j < list.size(); j++) {

				Row row = sheet.createRow(j + rowNumber);

				for (int i = 0; i < columnCount; i++) {
					Cell cell = row.createCell(i);
					cell.setCellValue(list.get(j)[i]);
					sheet.autoSizeColumn(i);
				}
			}

			// Write the output to a file
			FileOutputStream fileOut = new FileOutputStream(new File(GetBESTahsilatData.filePath + fileName));
			workbook.write(fileOut);
			fileOut.close();
			workbook.close();

		} catch (FileNotFoundException e) {
			log.error("Hata!! Rapor excel dosyasi doldurulamadi: " + e.toString());
			EmailSend.excelDosyasiDoldurmaHatasi();
			throw new SkipException("HATA: Rapor excel dosyasi doldurulamadi.");
		}
	}

	public void insertListToExcelDifferent(List<String[]> list, String fileName, String sheetName, int columnCount,
			int rowCount) throws InterruptedException, IOException {

		try {
			file = new FileInputStream(new File(GetBESTahsilatData.filePath + fileName));

			Workbook workbook = new XSSFWorkbook(file);
			Sheet sheet = workbook.getSheet(sheetName);

			for (int j = 0; j < list.size(); j++) {
				Row row = sheet.createRow(j + 1 + rowCount);

				for (int i = 0; i < columnCount; i++) {
					Cell cell = row.createCell(i);
					cell.setCellValue(list.get(j)[i]);
					sheet.autoSizeColumn(i);
				}
			}

			// Write the output to a file
			FileOutputStream fileOut = new FileOutputStream(new File(GetBESTahsilatData.filePath + fileName));
			workbook.write(fileOut);
			fileOut.close();
			workbook.close();

		} catch (FileNotFoundException e) {
			log.error("Hata!! Rapor excel dosyasi doldurulamadi: " + e.toString());
			EmailSend.excelDosyasiKumulDoldurmaHatasi();
			throw new SkipException("HATA: Rapor excel dosyasi doldurulamadi.");
		}
	}

	public boolean isFileExists(String filePath, String fileName) throws InterruptedException {
		boolean flag = false;
		File dir = new File(filePath);
		for (int tryCount = 5; !flag && tryCount > 0; tryCount--) {
			Thread.sleep(1000);
			log.info(fileName + " isimli dosya kontrol ediliyor. Kontrol : " + tryCount);

			File[] dir_contents = dir.listFiles();
			for (int i = 0; i < dir_contents.length; i++) {
				if (dir_contents[i].getName().equals(fileName)) {
					log.info(fileName + " isimli dosya var.");
					return flag = true;
				}
			}
		}
		return flag;
	}

	public List<String[]> populateBESTahsilatList(String filePath, String BESTahsilatFileName)
			throws InterruptedException, IOException {

		try {
			file = new FileInputStream(new File(filePath + BESTahsilatFileName));

			// Create Workbook instance holding reference to .xlsx file
			XSSFWorkbook workbook = new XSSFWorkbook(file);

			// Get first/desired sheet from the workbook
			XSSFSheet sheet = workbook.getSheetAt(0);
			DataFormatter fmt = new DataFormatter();

			// Iterate through each rows one by one
			Iterator<Row> rowIterator = sheet.iterator();

			for (int i = 0; i < 1; i++) {
				if (rowIterator.hasNext())
					rowIterator.next();
			}

			while (rowIterator.hasNext()) {
				String row[] = new String[10];
				Row rows = rowIterator.next();
//	            log.info("rowIndex:" + row.getRowNum());
				// For each row, iterate through all the columns
				Iterator<Cell> cellIterator = rows.cellIterator();

				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
//	                log.info("columnIndex:" + cell.getColumnIndex());
					if (cell.getColumnIndex() == 10) {
						break;
					}

					// Check the cell type and format accordingly
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_NUMERIC:
						row[cell.getColumnIndex()] = fmt.formatCellValue(cell);
						// log.info(cell.getNumericCellValue() + "\t");
						break;
					case Cell.CELL_TYPE_STRING:
						row[cell.getColumnIndex()] = fmt.formatCellValue(cell);
						// log.info(cell.getStringCellValue() + "\t");
						break;
					case Cell.CELL_TYPE_BLANK:
						row[cell.getColumnIndex()] = fmt.formatCellValue(cell);
						// row[cell.getColumnIndex()] = fmt.toString();
						// log.info(cell.getStringCellValue() + "\t");
						break;
					}
				}
				BESTahsilatList.add(row);
			}
			file.close();
		} catch (FileNotFoundException e) {

		}
		return BESTahsilatList;
	}

	public List<String[]> populateBESTahsilatList(int sheetCount, String reportFileName,
			List<String[]> kumulBESTahsilatEskiDataList) throws InterruptedException, IOException {

		try {
			file = new FileInputStream(new File(GetBESTahsilatData.filePath + reportFileName));

			// Create Workbook instance holding reference to .xlsx file
			XSSFWorkbook workbook = new XSSFWorkbook(file);

			// Get first/desired sheet from the workbook
			XSSFSheet sheet = workbook.getSheetAt(sheetCount);
			DataFormatter fmt = new DataFormatter();

			// Iterate through each rows one by one
			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				String row[] = new String[10];
				Row rows = rowIterator.next();
				Iterator<Cell> cellIterator = rows.cellIterator();

				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();

					// Check the cell type and format accordingly
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_NUMERIC:
						row[cell.getColumnIndex()] = fmt.formatCellValue(cell);
						break;
					case Cell.CELL_TYPE_STRING:
						row[cell.getColumnIndex()] = fmt.formatCellValue(cell);
						break;
					}
				}
				kumulBESTahsilatEskiDataList.add(row);
			}
			file.close();

		} catch (FileNotFoundException e) {
			log.error("Hata!! Rapor listesi doldurulamadi: " + e.toString());
			EmailSend.raporListesiDoldurmaHatasi();
			throw new SkipException("HATA: Rapor listesi doldurulamadi.");
		}
		return kumulBESTahsilatEskiDataList;
	}

	public boolean deleteFile(String pathToFile) throws InterruptedException {
		Thread.sleep(2000);
		boolean flag = false;
		try {
			File file = new File(pathToFile);

			if (!file.exists())
				return false;

			flag = file.delete();

			if (flag) {
				Thread.sleep(1500);
				log.info(file.getName() + " dosyası silindi.");

			} else {
				log.error("Dosya silme islemi basarisiz.");
			}
		} catch (Exception e) {
			log.error("Error : Dosya silme islemi basarisiz.");
		}
		return flag;
	}
}