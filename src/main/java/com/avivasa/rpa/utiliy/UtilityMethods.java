package com.avivasa.rpa.utiliy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;

import com.avivasa.rpa.data.DBConnection;
import com.avivasa.rpa.data.GetBESTahsilatData;
import com.gargoylesoftware.htmlunit.javascript.host.geo.Position;

public class UtilityMethods {
	int waitSFTP = 10000;
	int maxColumn = 22;
	public static final String ENCODER = "Cp1254";
	String policeNo = "";
//	public static final String ENCODER= "ISO 8859-9";

	public static String getDate() {
		Date date = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		return ft.format(date);
	}

	public static String getDateDay() {
		Date date = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("dd");
		return ft.format(date);
	}

	public static String getDateMonth() {
		Date date = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("MM");
		return ft.format(date);

	}

	public static String getDateYear() {
		Date date = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("YY");
		return ft.format(date);

	}

	public static String getDateFormat() {
		Date date = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("dd.MM.yyyy");
		return ft.format(date);
	}

	public String getDate2() {
		Date date = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("dd.MM.yyyy");
		return ft.format(date);
	}

	public String getDate3() {
		Date date = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy");
		return ft.format(date);
	}

	public String getDate4() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
		String nowDate = sdf.format(date);
		System.out.println("formatted date in mm/dd/yy : " + nowDate);

		return nowDate;
	}

	public String getTimeFormat() {
		Date date = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("HH:mm:ss");
		return ft.format(date);
	}

	public Long getDayDifference(String startDate, String nowDay) throws ParseException {

		SimpleDateFormat myFormat = new SimpleDateFormat("dd.MM.yyyy");
		Long difDay = null;

		try {
			Date date1 = myFormat.parse(startDate);
			Date date2 = myFormat.parse(nowDay);
			long diff = date2.getTime() - date1.getTime();
			difDay = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
			// System.out.println ("Days: " + TimeUnit.DAYS.convert(diff,
			// TimeUnit.MILLISECONDS));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return difDay;
	}

	public static String changeDateFormatForCVBank(String strDate) throws ParseException {
		SimpleDateFormat myFormat = new SimpleDateFormat("dd.MM.yyyy");
		SimpleDateFormat yy = new SimpleDateFormat("MM/dd/yy");
		Date actualDate = null;

		if (strDate.contains("/") && (strDate != null && !strDate.isEmpty())) {
			actualDate = yy.parse(strDate);
			strDate = myFormat.format(actualDate);
		}

		return strDate;
	}

	public static String getDateForAkbankFormat() {
		Date date = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("dd.MM.yyyy");
		return ft.format(date);
	}

	public String getTime() {
		Date date = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("HH-mm-ss");
		return ft.format(date);
	}

	public String getTimeForAkbankFormat() {
		Date date = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("HH:mm:ss");
		return ft.format(date);
	}

	public String getTimeDifference(String time1, String time2) throws ParseException {
		SimpleDateFormat ft = new SimpleDateFormat("HH:mm:ss");
		Date date1 = ft.parse(time1);
		Date date2 = ft.parse(time2);
		long difference = Math.abs(date2.getTime() - date1.getTime());
		// System.out.println(difference/1000);

		String differenceAsString = Long.toString(difference / 1000);

		return differenceAsString;
	}

	public static String getLastDayOfTheMonth(String date) {
		String lastDayOfTheMonth = "";

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			java.util.Date dt = formatter.parse(date);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dt);

			calendar.add(Calendar.MONTH, 1);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			calendar.add(Calendar.DATE, -1);

			java.util.Date lastDay = calendar.getTime();

			lastDayOfTheMonth = formatter.format(lastDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return lastDayOfTheMonth;
	}

	public boolean getWorkingDaysFromDB(String currentDay) throws SQLException {

		String sql = "select gun_drm from sgo.takvim where Tarih = '" + currentDay + "'";
//		log.info("C veya T kontrolü : " + sql);
//		String organizeDays = "C";
		String organizeDays = DBConnection.selectTCRM(sql).get(0)[0];
		log.info("organizeDays:" + organizeDays);
		
		if (organizeDays.equals("T"))
			return false;
		else
			return true;
	}
	 
	public String convertToExcelName(String fileName) {
		String s[] = fileName.split("_");
		String excelFileName = s[0] + "_" + s[1] + "_" + s[2] + "_SONUC_" + s[3] + "_" + s[4];
		if (excelFileName.contains("txt"))
			excelFileName = excelFileName.replace("txt", "xlsx");
		else if (excelFileName.contains("TXT"))
			excelFileName = excelFileName.replace("TXT", "xlsx");
		return excelFileName;
	}

	public String convertToCustomExcelName(String fileName) {
		if (fileName.contains("txt"))
			fileName = fileName.replace("txt", "xlsx");
		else if (fileName.contains("TXT"))
			fileName = fileName.replace("TXT", "xlsx");
		return fileName;
	}

	public String convertToOutputTxtName(String fileName) {
		String s[] = fileName.split("_");
		String outputTxtFileName = s[0] + "_" + s[1] + "_" + s[2] + "_SONUC_" + s[3] + "_" + s[4].toUpperCase();
		return outputTxtFileName;
	}

	public String convertToCustomOutputTxtName(String fileName) {
//			String s[] = fileName.split(".");
		String outputTxtFileName = "SONUC_" + fileName;
		return outputTxtFileName;
	}

	public String convertToPreviousOutputTxtName(String fileName, String prev) {
		String s[] = fileName.split("_");
		String outputTxtFileName = s[0] + "_" + s[1] + "_" + s[2] + "_SONUC_" + prev + s[4].toUpperCase();
		log.info("previous output txt file name:" + outputTxtFileName);
		return outputTxtFileName;
	}

	public String convertToPreviousInputTxtName(String fileName, String prev) {
		String s[] = fileName.split("_");
		String outputTxtFileName = s[0] + "_" + s[1] + "_" + s[2] + "_" + prev + s[4];
		log.info("previous input txt file name:" + outputTxtFileName);
		return outputTxtFileName;
	}

	public String extractFileDate(String fileName) {
		String s[] = fileName.split("_");
		String fileDate = s[4].replace(".txt", "");
		return fileDate;
	}

	public String extractExcelFileDate(String fileName) {
		String s[] = fileName.split("_");
		String fileDate = s[5].replace(".xlsx", "");
		return fileDate;
	}

	public String reformatFileDate(String fileName) {
		String s[] = fileName.split("-");
		return s[2] + "." + s[1] + "." + s[0];
	}

	public void waitSFTP() throws InterruptedException {
		Thread.sleep(waitSFTP);
	}

	public int getMaxColumn() {
		return maxColumn;
	}

	public String removeNonSignificant(double x) {
		java.text.DecimalFormat format = new java.text.DecimalFormat("0.#");
		return format.format(x);
	}

	public String appendEmptyString(String s, int length) {
		for (int i = s.length(); i < length; i++)
			s += " ";
		return s;
	}

	public String addLeadingZeroString(String s, String value, int length) {
		for (int i = value.length(); i < length; i++)
			s += "0";
		return s;
	}

	public String addLeadingZero(String s, String value, int length) {
		// int digit = (int)(Math.log10(value)+1);

		for (int i = value.length(); i < length; i++)
			s += "0";
		return s;
	}

	public String calculatePreviousSchedule(String sTime) {
		if (sTime.equals("V2_"))
			return "V1_";
		else if (sTime.equals("V3_"))
			return "V2_";
		else
			return sTime;
	}

	public String splitKisDate(String strDate) {

		// 28.11.2018
		// 2018-11-29

		String[] result = strDate.split("\\.");
		String resultDate = result[2] + "-" + result[1] + "-" + result[0];

		return resultDate;
	}

	public String replaceTutar(String tutar) {

		// TODO: 59,15 TL 4,73 TL 188,00 TL 1.23 0.82 0.04
		tutar = tutar.trim();
		tutar = tutar.replace(" ", "");
		tutar = tutar.replace("TL", "");
		tutar = tutar.replace(".", "");
		tutar = tutar.replace(",", "");
		tutar = tutar.replace("$", "");
		tutar = tutar.substring(0, tutar.length() - 2) + "." + tutar.substring(tutar.length() - 2);

		return tutar;
	}

	public String replaceBankaHesabi(String banka) throws ParseException {

		banka = banka.trim();
		banka = banka.replace("TL-146597-AKB", "102011100000001500");
		banka = banka.replace("TL-146370-AKB", "102011200000036100");
		banka = banka.trim();
		
		return banka;
	}

	public String replaceAkbankTutar(String tutar) {

		// TODO: 59,15 TL 4,73 TL 188,00 TL 7.362,15 TL

		tutar = tutar.replace("TL", "");
		tutar = tutar.replace(".", "");
		tutar = tutar.replace(",", ".");
		tutar = tutar.trim();

		return tutar;
	}

	public String replaceAvivasaTutar(String tutar) {

		// TODO: 1.23 0.82 0.04 1968.00 530.00

		tutar = tutar.trim();

		return tutar;
	}

	public String changeTelFormat(String tel) {

		tel = tel.replace(" ", "");
		tel = tel.replace("(", "");
		tel = tel.replace(")", "");
		tel = tel.replace("+9", "");
		tel = tel.trim();

		int fark = Math.abs(tel.length() - 11);

		if (tel.length() < 11) {

			for (int i = 0; i < fark; i++) {
				tel = "0" + tel;
			}
		}

		return tel;
	}

	public String changeTelFormatForCVBank(String tel) {

		tel = tel.replace("(", "");
		tel = tel.replace(")", "");
		tel = tel.replace("+", "");
		tel = tel.trim();

		return tel;
	}

	public String currencyFormatting(String tutar) throws ParseException {

		// TODO: 59,15 TL 4,73 TL 188,00 TL 1.23 0.82 0.04

		double amount = 1000000000;
		NumberFormat formatter = new DecimalFormat("#,##0.00");
		Locale usa = Locale.US;// türk para birimi malesef bu şekilde bulunmadığı için getDefault()
								// özelliğinden yararlanıyoruz :)
		String para2 = NumberFormat.getCurrencyInstance(usa).format(amount);
		Locale turkishLocale = Locale.getDefault();// getDefault yapınca JVM(java virtual machine)varsayılan locale'leri
													// başlatma sırasında otomatik ayarlar
		String para1 = NumberFormat.getCurrencyInstance(turkishLocale).format(amount);// aslında oluşturduğumuz
																						// turkishLocale nesnesi paranın
																						// sonuna eklemek istediğimiz TL
																						// simgesidir..
		System.out.println("decimal değer:" + formatter.format(amount));
		System.out.println("decimal değer:" + para1);
		System.out.println("decimal değer:" + para2);

		NumberFormat format = NumberFormat.getInstance(Locale.getDefault());

		Number number = format.parse("4,73");
		System.out.println(number.doubleValue());
		System.out.println(number);

		DecimalFormat format1 = (DecimalFormat) NumberFormat.getInstance(Locale.getDefault());
		format1.setParseBigDecimal(true);
		BigDecimal number1 = (BigDecimal) format1.parse("4,73"); // 835,111.20000
		System.out.println("yeni değer:" + number1);

		String s = "4,73"; // 835,111.200
		Double d = Double.parseDouble(s.replaceAll(",", ""));
		BigDecimal value = new BigDecimal(s.replace(",", ""));
		System.out.println("yeni değer1:" + d);
		System.out.println("yeni değer2:" + value);

		return tutar;
	}

	public void copyAndMoveFiles(String sourceFilePath, String fileName1) {

		String filePath = GetBESTahsilatData.filePath + "Backup\\";
		String getDate = getDate();
		String getTime = getTime();
		String getDateTime = getDate + " " + getTime;
		String backupFolderDate = filePath + getDate;
		String backupFolderDateTime = filePath + getDate + "\\" + getTime;

		new File(backupFolderDate).mkdir();
		new File(backupFolderDateTime).mkdir();

		copyMoveDeleteToFile(GetBESTahsilatData.filePath, sourceFilePath, fileName1, getDateTime,
				backupFolderDateTime);

	}

	public void copyMoveDeleteToFile(String filePath, String sourceFilePath, String fileName, String nowDateTime,
			String backupFolder) {

		File file = new File(sourceFilePath + fileName);

		// renaming the file and moving it to a new location
		if (file.renameTo(new File(backupFolder + "\\" + fileName))) {
			// if file copied successfully then delete the original file
			file.delete();
			log.info("File moved successfully. " + filePath + fileName);
		} else {
			log.info("Failed to move the file. " + filePath + fileName);
		}
	}

	public void copyMoveDeleteToFile(String filePath, String fileName, String backupFolder) {

		File file = new File(filePath + fileName);

		// renaming the file and moving it to a new location
		if (file.renameTo(new File(backupFolder + "\\" + fileName))) {
			// if file copied successfully then delete the original file
			file.delete();
			log.info("File moved successfully. " + filePath + fileName);
		} else {
			log.info("Failed to move the file. " + filePath + fileName);
		}
	}

	public void copyMoveFile(String filePath, String fileName, String backupFolder) {

		File file = new File(filePath + fileName);

		// renaming the file and moving it to a new location
		if (file.renameTo(new File(backupFolder + "\\" + fileName))) {
			log.info("File moved successfully. " + filePath + fileName);
		} else {
			log.info("Failed to move the file. " + filePath + fileName);
		}
	}

	public void moveFile(String sourcePath, String targetPath) {

		InputStream inStream = null;
		OutputStream outStream = null;

		try {

			File afile = new File(sourcePath);
			File bfile = new File(targetPath);

			inStream = new FileInputStream(afile);
			outStream = new FileOutputStream(bfile);

			byte[] buffer = new byte[1024];

			int length;
			// copy the file content in bytes
			while ((length = inStream.read(buffer)) > 0) {

				outStream.write(buffer, 0, length);
			}

			inStream.close();
			outStream.close();

			// delete the original file
			// afile.delete();

			System.out.println("File is copied successful!");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getFileNameInFolder(String path) {

		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();
		String fileName = null;

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				fileName = listOfFiles[i].getName();
				if (fileName.contains("xls")) {
					break;
				}
				log.info("File " + listOfFiles[i].getName());
			}
//    	     else if (listOfFiles[i].isDirectory()) {
//    	       log.info("Directory " + listOfFiles[i].getName());
//    	     }
		}

		return fileName;
	}

	public int getFileSizeInFolder(String path) {

		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();
		int fileSize = listOfFiles.length;
		String fileName = null;

		return fileSize;
	}

	public String changePolicePaketFormat(String policepaket) {

		if ((policepaket != null && !policepaket.isEmpty() && policepaket.contains("-"))) {
			String[] data = policepaket.split("-");
			String paketkodu = data[0];
			policepaket = paketkodu;

		} else if ((policepaket != null && !policepaket.isEmpty() && policepaket.contains(""))) {
			String[] data = policepaket.split(" ");
			String paketkodu = data[0];
			policepaket = paketkodu;
		}
		return policepaket;
	}

	public String changePolicNoFormat(String policepaketNo) {

		policepaketNo = policepaketNo.trim();
		policepaketNo = policepaketNo.replace(" ", "");
		policepaketNo = policepaketNo.replace("-", "");
		policepaketNo = policepaketNo.replace("_", "");
		return policepaketNo;
	}

	public Boolean fileOPen(String path) {

		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();
		String fileName = null;
		boolean status = false;

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				fileName = listOfFiles[i].getName();
				if (fileName.contains("~$")) {
					status = true;
					break;
				}
				log.info("File " + listOfFiles[i].getName());
			}
//    	     else if (listOfFiles[i].isDirectory()) {
//    	       log.info("Directory " + listOfFiles[i].getName());
//    	     }
		}

		return status;
	}

	public Boolean isFileExist(String path) {

		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();
		int fileSize = listOfFiles.length;
		String fileName = null;

		for (int i = 0; i < listOfFiles.length; i++) {
			fileName = listOfFiles[i].getName();
			if (fileName.contains("Thumbs.db")) {
				fileSize = fileSize - 1;
				break;
			}
		}

		if (fileSize > 0) {
			return true;
		}

		return false;
	}
	
	public Boolean fileOpenInFolder(String path, String filenameInFolder) {

		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();
		String fileName = null;
		boolean status = false;

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				fileName = listOfFiles[i].getName();
				if (fileName.contains("~$") && fileName.contains(filenameInFolder)) {
					status = true;
					break;
				}
//				log.info("File " + listOfFiles[i].getName());
			}
// 	     else if (listOfFiles[i].isDirectory()) {
// 	       log.info("Directory " + listOfFiles[i].getName());
// 	     }
		}

		return status;
	}
}
