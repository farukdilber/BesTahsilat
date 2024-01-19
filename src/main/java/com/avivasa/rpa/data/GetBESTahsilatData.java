package com.avivasa.rpa.data;

import com.avivasa.rpa.utiliy.UtilityMethods;

public class GetBESTahsilatData {

	static UtilityMethods utilityMethods = new UtilityMethods();

	// Canlı Ortam - AS400
	public static final String as400URL = "http://BTROB7:BTROB72@193.255.76.240:2060/webaccess/iWA5250?access=*PUBLIC&sessname=HAY400WEBS1";
	public static final String as400Username = "BTROB7";
	public static final String as400Password = "BTROB72";

//
////Test Ortam	
//
//	public static final String ortakAlanFilaPath = "C:\\RPA\\DataFile\\KPSSorgulamaDatalari\\";
//	public static final String reportFileName = "BESTahsilatRaporu_" + UtilityMethods.getDate() + ".xlsx";
//	public static final String inputFileName = "KPSSorgulamaDatalari.xlsx";
//	public static final String filePath = "C:\\RPA\\DataFile\\KPSSorgulamaDatalari\\";
//	
//	

//Canlı Ortam

	public static final String ortakAlanFilaPath = "\\\\10.10.15.240\\AvivaSA_DATA\\Operasyon ve Teknoloji Yonetimi\\Teknik Operasyon\\Kurumsal Müşteri Hizmetleri ve Tahsilat\\TAHSİLAT YÖNETİM\\Bes TechBot\\";
	public static final String reportFileName = "BESTahsilatRaporu_" + UtilityMethods.getDate() + ".xlsx";
	public static final String inputFileName = "BESTahsilatDatalari.xlsx";
	public static final String filePath = "C:\\RPA\\DataFile\\BESTahsilatSureci\\";

}
