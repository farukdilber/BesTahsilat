package com.avivasa.rpa.data;

import java.io.File;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.avivasa.rpa.util.DataFinder;

@com.mongodb.annotations.ThreadSafe
@javax.annotation.concurrent.ThreadSafe
@net.jcip.annotations.ThreadSafe
public class GetData {

	public static String FILESYSDATE;
	public static final int DEFAULT_WAIT = 120;
	public static final int DEFAULT_WAIT_LOADERBOX = 90;
	public static final String TEMP_FILE_PATH = File.separator + File.separator + "10.10.15.240" + File.separator
			+ "Bilgi_Teknolojileri" + File.separator + "BT Ortak" + File.separator + "TestOtomasyonRaporlari"
			+ File.separator;
	public static final String RESULT_FILE_PATH = "C:" + File.separator + "TestOtomasyonRaporlari" + File.separator;

	// URL("http://192.168.149.121:4444/wd/hub")//192.168.238.61 10.2.17.113
	public static final String REMOTE_MACHINE_IP = "http://192.168.238.61:4444/wd/hub";

	public enum Url {
		KIS_OPERASYON_URL, KIS_KURUMIK_URL, MINIBIS_URL, AS400_URL, ROP_URL, N11_URL;
	}

	public enum Data {
		GRUP_NO, GRUP_ADI_NO, SOZLEME_GRUP_NO, GRUP_ADI_NO2,

		UNVAN, VERGI_DAIRESI, SATAN_AD_SOYAD, SATAN_ACENTE, MBB_NO, GRUP_FON_TERCIHI_MBB_NO, GRUP_ODEME_GUNU,
		GIRIS_TARIHI, YETKILI_KISI_AD, YETKILI_KISI_SOYAD, YETKILI_KISI_TEL,

		YETKILI_KISI_TEL_GUNCELLEME, YETKILIKISIEMAIL, YETKILIKISIEMAILDOMAIN, ADRES, ADRES2, ADRES3, ILCE, POSTA_KODU,
		IL, TEL_NO, FAX, EMAIL_ADRESS, EMAIL_DOMAIN, CALISAN_SAYISI, KATKI_TUTARI;

		public String getValue() {
			return DataFinder.getData(this);
		}
	}

	public enum LoginInfo {
		USERNAME, USERNAME_ONAY, PASSWORD,

		KURUMIK_USERNAME, KURUMIK_PASSWORD, KURUMIK_VKN,

		AS400_USERID, AS400_SIFRE, AS400_USERID_EKRAN, AS400_SIFRE_EKRAN;

		public String getValue() {
			return DataFinder.getLoginInfo(this);
		}
	}

	public enum ParolamıUnuttum {
		PU_EMAIL, PU_VKN, PU_CEPTEL;

		public String getValue() {
			return DataFinder.getValue(this.name());
		}
	}

	public enum RopOrganizasyonelBilgileri {
		ROP_USERNAME1, ROP_USERNAME2, ROP_USERNAME3;

		public String getValue() {
			return DataFinder.getValue(this.name());
		}
	}

	// Yüklenen dosyaların yolu
	public static final String DOWNLOAD_FILE_PATH = TEMP_FILE_PATH + "Test Data" + File.separator + "Download";
	public static final String TAHSILAT_FILE_PATH = TEMP_FILE_PATH + "Test Data" + File.separator
			+ "TESTOTOMASYON_tahsilat.xls";
	public static final String TAHSILAT_TEMP_FILE_PATH = TEMP_FILE_PATH + "Test Data" + File.separator
			+ "TESTOTOMASYON_tahsilatTemp.xls";
	public static final String MUSTERI_BILGILERI_FILE_PATH = TEMP_FILE_PATH + "Test Data" + File.separator
			+ "Musteri_Bilgileri_Guncelleme.xls";
	public static final String BASVURU_TOPLU_AKTARIM_PATH = TEMP_FILE_PATH + "Test Data" + File.separator
			+ "TESTOTOMASYON_Basvuru_Toplu_Aktarim.xls";
	public static final String SCREENSHOTS_PATH = RESULT_FILE_PATH + "TestOtomasyonRaporlari" + File.separator
			+ "Test Error ScreenShots" + File.separator;

	public static String ikiOncekiIsGunu;
	
	public static List<String[]> OdemeDegisiklikSelectPoliceCream() throws SQLException {

		// Test ortamında açılacak kod
//		String sql = "select POLICE_NO from CREAM.D_BASVURU where POLICE_NO='1682303'";
		// + "and POLICE_NO='1682301'";
		// + "and URUN_ID in ('219','221') and ILK_TAHSILAT_TARIHI is not null and
		// ONAY_TARIH >='04/08/2016' and URUN_GRUP_ID='1'";
//		return DBConnection.selectTCRM(sql);

		// Canlı Ortamda açılacak kod
		ikiOncekiIsGunu = DBConnection.selectPCRMCanli(
				"select * from sgo.takvim where tarih <= '" + getDate() + "' AND GUN_DRM = 'C' ORDER BY TARIH DESC ")
				.get(2)[0];

		String sql = "select POLICE_NO from CREAM.D_BASVURU where  POLICE_PAKET_KOD='E' and URUN_ID in ('219','221') and ONAY_TARIH>='01.01.2019' and ILK_TAHSILAT_TARIHI='"
				+ ikiOncekiIsGunu + "' and URUN_GRUP_ID='1' ";

//		String sql = "select POLICE_NO from CREAM.D_BASVURU where  POLICE_PAKET_KOD='E' and URUN_ID in ('219','221') and ONAY_TARIH>='01.01.2019' and "
//				+ "ILK_TAHSILAT_TARIHI= '21.08.2019' and URUN_GRUP_ID='1' ";
		
		return DBConnection.selectPCRMCanli(sql);
	}

	public static List<String[]> OdemeDegisiklikSelectPoliceAS400(String policeNo) throws SQLException {

		String sql = "select haypno from  emklib.emk100 where hayzey=(select max(hayzey) from emklib.emk100 where haypno='"
				+ policeNo + "' and haypak='E') and haypno='" + policeNo
				+ "' and hayhrk not in ('94','96') and haypak='E'";

		// Canlı Ortam
		return DBConnection.selectAs400(sql);

		// Test Ortamı
//		 return DBConnection.selectAs400(sql);
	}

	public static Date yesterday() {
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		return cal.getTime();
	}

	public static String getYesterdayDateString() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		return dateFormat.format(yesterday());
	}

	public static String getDate() {
		SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		return ft.format(date);
	}
}