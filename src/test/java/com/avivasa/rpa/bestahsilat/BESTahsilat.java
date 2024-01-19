package com.avivasa.rpa.bestahsilat;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.xml.rpc.ServiceException;

import org.testng.SkipException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.avivasa.rpa.actions.AS400Actions;
import com.avivasa.rpa.base.AbstractTest;
import com.avivasa.rpa.data.ExcelOperations;
import com.avivasa.rpa.data.GetBESTahsilatData;
import com.avivasa.rpa.exceptions.As400Exceptions;
import com.avivasa.rpa.utiliy.EmailSend;
import com.avivasa.rpa.utiliy.ExtentTestManager;
import com.avivasa.rpa.utiliy.UtilityMethods;
import com.avivasa.rpa.utiliy.log;

public class BESTahsilat extends AbstractTest {

	ExcelOperations excelOperations;
	AS400Actions as400Actions;
	UtilityMethods utilityMethods;

	List<String[]> BESTahsilatList = new ArrayList<String[]>();
	List<String[]> BESTahsilatGelenDataList = new ArrayList<String[]>();
	List<String[]> BESTahsilatKumulDataList = new ArrayList<String[]>();
	List<String[]> BESTahsilatBasariliDataList = new ArrayList<String[]>();
	List<String[]> kumulBESTahsilatDataList = new ArrayList<String[]>();
	List<String[]> kumulBESTahsilatEskiDataList = new ArrayList<String[]>();

	public static int kumulDataListSize;

	@BeforeSuite
	public void beforeSuite() throws ServiceException, InterruptedException, IOException, ParseException, SQLException {

		ExtentTestManager.startTest("RPA - BES Tahsilat İşlemleri");

		excelOperations = new ExcelOperations();
		utilityMethods = new UtilityMethods();

		if (!utilityMethods.getWorkingDaysFromDB(UtilityMethods.getDate())) {
			log.error("Hata!! Tatil gunu oldugu icin surec durdurulmustur.");
			throw new SkipException("Hata!! Tatil gunu oldugu icin surec durdurulmustur.");
		}
	
		
		// Control BESTahsilatDatalari.xlsx , send mail.
		if (excelOperations.controlFileAndSendMail(GetBESTahsilatData.ortakAlanFilaPath,
				GetBESTahsilatData.inputFileName)) {
			throw new SkipException("Hata!! " + GetBESTahsilatData.ortakAlanFilaPath + GetBESTahsilatData.inputFileName
				 	+ " isimli dosya bulunamamistir.");
		}

		// Is BESTahsilatDatalari.xlsx file open
		if (utilityMethods.fileOpenInFolder(GetBESTahsilatData.ortakAlanFilaPath, GetBESTahsilatData.inputFileName)) {
			log.error(GetBESTahsilatData.ortakAlanFilaPath + GetBESTahsilatData.inputFileName + " dosyasi acik");
			EmailSend.dosyaAcik(GetBESTahsilatData.ortakAlanFilaPath, GetBESTahsilatData.inputFileName);
			throw new SkipException("Hata!! " + GetBESTahsilatData.ortakAlanFilaPath + GetBESTahsilatData.inputFileName
					+ " dosyası acik");
		}
		
		if (!(excelOperations.isFileExists(GetBESTahsilatData.filePath, GetBESTahsilatData.reportFileName))) {
			excelOperations.generateBESTahsilatContentReportExcel(GetBESTahsilatData.reportFileName);
			log.info(GetBESTahsilatData.reportFileName + " Dosyası olusturuldu.");

		} else {
			kumulBESTahsilatEskiDataList = excelOperations.populateBESTahsilatList(0, GetBESTahsilatData.reportFileName,
					kumulBESTahsilatEskiDataList);
			kumulDataListSize = kumulBESTahsilatEskiDataList.size();
			log.info("Kumul Data Size:" + kumulDataListSize);
		}

		try {
			
			BESTahsilatList = excelOperations.populateBESTahsilatKayitList1(GetBESTahsilatData.ortakAlanFilaPath,
					GetBESTahsilatData.inputFileName);
			log.info("Populate BES Tahsilat Excel List");
		} catch (Exception e) {
			// EmailSend.dosyaOkunamadi(GetBESTahsilatData.ortakAlanFilaPath,
			// GetBESTahsilatData.inputFileName);
			log.info("Kontrol dosyasini okuyamadi.");
			throw new SkipException("Kontrol dosyasini okuyamadi.");
		}
	
		if (BESTahsilatList.size() == 0) {
			// EmailSend.BESTahsilatBulunamadiHatasi();
			log.error("BES Tahsilat islemleri icin police datasi bulunamamistir.");
			throw new SkipException("BES Tahsilat islemleri icin police datasi bulunamamistir.");
		}
		
		//BESTahsilat sheetindeki dataları temizlemek için kullanılıyor
		
		excelOperations.deleteRow("BESTahsilatSonuc",GetBESTahsilatData.filePath+GetBESTahsilatData.reportFileName);
		
		log.info("Total Odeme Police Data Provider: " + BESTahsilatList.size());

		int BESTahsilatListSize = BESTahsilatList.size();
		
		log.info("BESTahsilatList size: " + BESTahsilatListSize);

		int i;
		for (i = 0; i < BESTahsilatListSize; i++) {

			String paraGelisTarihi = BESTahsilatList.get(i)[0];

			String paraGelissaati = BESTahsilatList.get(i)[1];

			String dekont = BESTahsilatList.get(i)[2];

			String policeNo = BESTahsilatList.get(i)[3];

			String miktarYTL = BESTahsilatList.get(i)[4];

			String banka = BESTahsilatList.get(i)[5];

			String dekontNo = BESTahsilatList.get(i)[6];

			String[] temp = new String[7];
			temp[0] = paraGelisTarihi;
			temp[1] = paraGelissaati;
			temp[2] = dekont;
			temp[3] = policeNo;
			temp[4] = miktarYTL;
			temp[5] = banka;
			temp[6] = dekontNo;
			
			log.info("*******************************************************************");
			log.info("BESTahsilatList :" + "paraGelisTarihi :" + paraGelisTarihi + " paraGelissaati :" + paraGelissaati
					+ " dekont :" + dekont + "" + "policeNo :" + policeNo + "miktarYTL :" + miktarYTL + "banka :"
					+ banka + "dekontNo :" + dekontNo + "");
			log.info("BESTahsilatList listesine eklendi");
			log.info("*******************************************************************");	
			
			BESTahsilatGelenDataList.add(temp);	
		}
	
		excelOperations.insertListGelenData(BESTahsilatGelenDataList, GetBESTahsilatData.reportFileName, "AkbankEkstre", 7);
		log.info("*******************************************************************");
		log.info("BESTahsilatGelenDataList listesine eklendi");
		
		as400Actions = new AS400Actions();
	}

	@DataProvider(name = "BESPoliceTahsilat", parallel = false)
	public Iterator<Object[]> BESTahsilatDatalari() {

		Collection<Object[]> data = new ArrayList<Object[]>();
		BESTahsilatList.forEach(item -> data.add(new Object[] { item }));
		BESTahsilatList.clear();
		return data.iterator();
	}

	@Test(dataProvider = "BESPoliceTahsilat")
	public void BESTahsilatIslemleri(String[] dataFromList)
			throws As400Exceptions, InterruptedException, IOException, ParseException {

		String paraGelisTarihi = dataFromList[0]; //tarih
		String paraGelissaati = dataFromList[1]; //saat
		String dekont = dataFromList[2]; //açıklama
		String policeNo = dataFromList[3];//polno
		String miktarYTL = dataFromList[4];//tutar
		String banka = dataFromList[5];//banka
		String dekontNo = dataFromList[6];//dekont

		banka = utilityMethods.replaceBankaHesabi(banka);

		try {

			as400Actions.openUrl(GetBESTahsilatData.as400URL)
					.login(GetBESTahsilatData.as400Username, GetBESTahsilatData.as400Password)
	
					.BESSozlesmeKontrolu(policeNo, paraGelisTarihi, paraGelissaati, dekont, miktarYTL, dekontNo, banka,
							BESTahsilatList)
					.BESTahsilatIslemleri(policeNo, paraGelisTarihi, paraGelissaati, dekont, miktarYTL, dekontNo, banka, BESTahsilatList,BESTahsilatBasariliDataList)
			
					.stopAS400Session();
			
			log.info("Populate Avivasa Excel List");

		} catch (Exception e) {

			as400Actions.stopAS400Session();

			if (e.getMessage().contains("WebDriverException") || e.getMessage().contains("TimeoutException")) {
				log.info("exception identification:" + e.getMessage());

				throw new As400Exceptions(paraGelisTarihi, paraGelissaati, dekont, policeNo, miktarYTL, banka,dekontNo ,
						"", "Başarısız", "BES Tahsilat İşleminde Hata: Sistem Exception...", BESTahsilatList);

			}
		}
	}

	@AfterSuite
	public void afterSuite() throws InterruptedException, IOException {

		as400Actions.stopAS400Session();
		as400Actions.closeBrowser();

		int BESTahsilatBasariliDataListSize = BESTahsilatBasariliDataList.size();
		
		if (BESTahsilatBasariliDataListSize >=1) {			
			excelOperations.insertListGelenData(BESTahsilatBasariliDataList, GetBESTahsilatData.reportFileName, "BESTahsilatSonuc", 10);
			excelOperations.insertListGelenData(BESTahsilatBasariliDataList, GetBESTahsilatData.reportFileName, "BESTahsilatKumulData", 10);
			log.info("BESTahsilatBasariliDataList written to AvivaSA_BESTahsilatList_WorkQueue_Content_Report excel - BESTahsilatSonuc,BESTahsilatKumulData Data sheet");		
		}

//		if (kumulDataListSize > 1) {
//			excelOperations.insertListToExcelDifferent(BESTahsilatList, GetBESTahsilatData.reportFileName,
//					"BESTahsilat", 10, kumulDataListSize - 1);
//			log.info(
//					"BESTahsilatList written to AvivaSA_BESTahsilatList_WorkQueue_Content_Report.xlsx excel - Content Report sheet");
//		} else {
//			excelOperations.insertListToExcel(BESTahsilatList, GetBESTahsilatData.reportFileName, "BESTahsilat", 10);
//			log.info(
//					"BESTahsilatList written to AvivaSA_BESTahsilatList_WorkQueue_Content_Report.xlsx excel - Content Report sheet");
//		}

		EmailSend.BESTahsilatSonucRaporu(GetBESTahsilatData.reportFileName);
		utilityMethods.copyAndMoveFiles(GetBESTahsilatData.ortakAlanFilaPath, GetBESTahsilatData.inputFileName);
		//utilityMethods.copyAndMoveFiles(GetBESTahsilatData.filePath, GetBESTahsilatData.reportFileName);

	}

}
