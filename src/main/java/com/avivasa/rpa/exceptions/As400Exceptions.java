package com.avivasa.rpa.exceptions;

import java.io.IOException;
import java.util.List;

import com.avivasa.rpa.data.ExcelOperations;
import com.avivasa.rpa.data.GetBESTahsilatData;
import com.avivasa.rpa.utiliy.UtilityMethods;

public class As400Exceptions extends Exception {

	UtilityMethods um = new UtilityMethods();
	ExcelOperations excelOperations = new ExcelOperations();

	public As400Exceptions(String paraGelisTarihi, String paraGelisSaati, String dekont, String policeNo,
			String gelenPara, String banka, String dekontNo, String odeyenBilgi, String sonuc, String exceptionSebebi,
			List<String[]> BESTahsilatList) throws InterruptedException, IOException {

		String[] temp = new String[10];

		temp[0] = paraGelisTarihi;
		temp[1] = paraGelisSaati;
		temp[2] = dekont;
		temp[3] = policeNo;
		temp[4] = gelenPara;
		temp[5] = banka;
		temp[6] = dekontNo;
		temp[7] = odeyenBilgi;
		temp[8] = sonuc;
		temp[9] = exceptionSebebi;

		BESTahsilatList.add(temp);

		excelOperations.insertListToExcel(BESTahsilatList, GetBESTahsilatData.reportFileName, "BESTahsilatSonuc", 10);
		excelOperations.insertListToExcel(BESTahsilatList, GetBESTahsilatData.reportFileName, "BESTahsilatKumulData",
				10);
	}
	
	

}
