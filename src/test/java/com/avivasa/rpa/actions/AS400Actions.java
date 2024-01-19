package com.avivasa.rpa.actions;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.SkipException;

import com.avivasa.rpa.data.ExcelOperations;
import com.avivasa.rpa.data.GetBESTahsilatData;
import com.avivasa.rpa.exceptions.As400Exceptions;
import com.avivasa.rpa.library.AS400Library;
import com.avivasa.rpa.pageobject.ObjectRepository.AS400ObjectRepostiory;
import com.avivasa.rpa.utiliy.UtilityMethods;
import com.avivasa.rpa.utiliy.log;
import com.ibm.as400.access.AS400Exception;
import com.ibm.db2.jcc.am.ne;

import okhttp3.OkHttpClient;
import okhttp3.Response;

public class AS400Actions extends AS400Library {

	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	UtilityMethods utilityMethods = new UtilityMethods();
	ExcelOperations excelOperations;

	public OkHttpClient.Builder client = client();
	public Response response = null;
	public String sessionId = null;
	public String token = null;
	public String requestBody = null;
	public String url = null;
	public String refererUrl = null;
	public String xApiKey = null;
	public List<String> hesapBilgisi = null;
	public boolean urlHatasi = false;
	public boolean loginHatasi = false;
	public boolean webServisHatasi = false;
	private String yurulukTarihiGun;
	private String yurulukTarihiAy;
	private String yurulukTarihiYil;

	public OkHttpClient.Builder client() {

		OkHttpClient.Builder client = new OkHttpClient.Builder();
		client.connectTimeout(30, TimeUnit.SECONDS); // connect timeout
		client.readTimeout(45, TimeUnit.SECONDS); // socket timeout
		client.writeTimeout(30, TimeUnit.SECONDS);

		client.hostnameVerifier(new HostnameVerifier() {
			@Override
			public boolean verify(String hostname, SSLSession session) {

				return true;
			}
		});
		return client;
	}

	public AS400Actions() throws MalformedURLException {

		super();
	}

	public AS400Actions(String browserName) throws MalformedURLException {

		super(browserName);
	}

	public AS400Actions openUrl(String url) {

		try {

			navigateTo(url);
			log.info("openUrl transaction passed");

		} catch (Exception e) {
			log.error("Hata!! As400 url baslatilamadi: " + e);
			urlHatasi = true;
			throw new SkipException("Hata!! As400 url baslatilamadi.");
		}

		return this;
	}

	public AS400Actions login(String username, String password) throws InterruptedException {

		try {
			sendKeys(AS400ObjectRepostiory.txtUser, username, false);
			sendKeys(AS400ObjectRepostiory.txtPassword, password, false);
			as400PressEnter();

			if (isElementExist(AS400ObjectRepostiory.btnExitSign, 1)) {
				as400PressEnter();
				as400PressEnter();
			}
			as400PressEnter();
			log.info("As400 Login passed.");

		} catch (Exception e) {
			log.info("Hata!! As400 login basarisiz.");
			loginHatasi = true;
			throw new SkipException("Hata!! As400 login basarisiz.");
		}

		return this;
	}

	public AS400Actions BESSozlesmeKontrolu(String policeNumarasi, String paraGelisTarihi, String paraGelissaati,
			String dekont, String gelenPara, String dekontNo, String banka, List<String[]> BESTahsilatList)
			throws As400Exceptions, InterruptedException, IOException, ParseException {
//      //Test Ortamı

//		clickMenu(1);
//		clickMenu(145);
//		clickMenu(4);

//		//Canlı Ortam
		// sendKeys(Keys.F3);
		clickMenu(1);
		clickMenu(1);
		sendKeysWithLabel("Pol/Söz. no.....:", "E", 1);
		sendKeysWithLabel("Pol/Söz. no.....:", policeNumarasi, 2);
		as400PressEnter();
		as400SendKeysEnter(By.id("inpR6C3L1.id"), "X");
		// kontrolHataMesajıPoliceGirisi("E",policeNumarasi,paraGelisTarihi,paraGelissaati,dekont,Double.parseDouble(gelenPara),dekontNo,BESTahsilatList);
		as400SendKeysEnter(By.id("inpR6C2L1.id"), "X");
		sendKeysWithLabel("Pol/Söz.Bilgilerini Görüntüleme ..:", "X");
		as400PressEnter();
		String sigortaliAdi = getTextOfElement(By.xpath("(//body//div//td[@colspan='30'])[3]")).trim();

		String SigortaliSoyadi = getTextOfElement(By.xpath("(//body//div//td[@colspan='30'])[4]")).trim();

		String odeyenBilgi = sigortaliAdi + " " + SigortaliSoyadi;
		BESTarihKontrolu(policeNumarasi, paraGelisTarihi, paraGelissaati, dekont, gelenPara, dekontNo, banka,
				odeyenBilgi, BESTahsilatList);

		return this;
	}

	// TODO Auto-generated method stub

	public AS400Actions BESTahsilatIslemleri(String policeNo, String paraGelisTarihi, String paraGelissaati,
			String dekont, String miktarYTL, String dekontNo, String banka, List<String[]> BESTahsilatList,
			List<String[]> BESTahsilatBasariliDataList)
			throws As400Exceptions, InterruptedException, IOException, ParseException {

		String sigortaliAdi = getTextOfElement(By.xpath("(//body//div//td[@colspan='30'])[3]")).trim();

		String SigortaliSoyadi = getTextOfElement(By.xpath("(//body//div//td[@colspan='30'])[4]")).trim();

		String odeyenBilgi = sigortaliAdi + " " + SigortaliSoyadi;

		String yurulukTarihiGun = findElement(By.xpath("(//*[@id='screenarea']/tbody/tr[6])//td[@colspan='1'][2]"))
				.getText();
		String yurulukTarihiAy = findElement(By.xpath("(//*[@id='screenarea']/tbody/tr[6])//td[@colspan='1'][3]"))
				.getText();
		String yurulukTarihiYil = findElement(By.xpath("(//*[@id='screenarea']/tbody/tr[6])//td[@colspan='4'][1]"))
				.getText();

		String plan = findElement(By.xpath("(//*[@id='screenarea']/tbody//td//b//a)[1]")).getText();
		sendKeys(Keys.F3);
		sendKeys(Keys.F3);
		sendKeys(Keys.F3);
		// sendKeys(Keys.F3);
		// sendKeys(Keys.F3);
		plan = plan.trim();

		miktarYTL = miktarYTL.trim();
		miktarYTL = miktarYTL.replace(",", "");
		double gelenPara = Double.parseDouble(miktarYTL);

		if (yurulukTarihiGun.equals(" ") && yurulukTarihiAy.equals(" ") && yurulukTarihiYil.equals("    ")
				&& plan.equals("PLAN 0159")) {

			BESKatkiPayiBaslangicKapitaliTahsilat(policeNo, miktarYTL, paraGelisTarihi, paraGelissaati, banka, dekont,
					dekontNo, odeyenBilgi, BESTahsilatList, BESTahsilatBasariliDataList);
		}

		else {

			BESDekontKontrolu(policeNo, paraGelisTarihi, paraGelissaati, dekont, banka, miktarYTL, dekontNo,
					odeyenBilgi, BESTahsilatList);

			BESTahsilatBaslangicKapitali(policeNo, paraGelisTarihi, paraGelissaati, dekont, gelenPara, dekontNo,
					miktarYTL, banka, odeyenBilgi, BESTahsilatList, BESTahsilatBasariliDataList);
		}

		return this;

	}

	public AS400Actions BESTarihKontrolu(String policeNumarasi, String paraGelisTarihi, String paraGelisSaati,
			String dekont, String gelenPara, String banka, String dekontNo, String odeyenBilgi,
			List<String[]> BESTahsilatList) throws As400Exceptions, InterruptedException, IOException, ParseException {

		Date intikalTarihi;
		Date teklifTarihi;
		Date yurulukTarihi = null;
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

		try {

			// intikal Tarihi

			String paragelisgunu = paraGelisTarihi.substring(0, 2);
			String paragelisayi = paraGelisTarihi.substring(3, 5);
			String paragelisyili = paraGelisTarihi.substring(6, 8);
			String date = paragelisgunu + "/" + paragelisayi + "/" + 20 + paragelisyili;

			intikalTarihi = formatter.parse(date);

		} catch (Exception e) {
			log.error(
					"Hata:Exceldeki EFF_DATE (Paranın geliş tarihi) tarih formatında hata alındı.Lütfen kontrol ediniz...");

			throw new As400Exceptions(paraGelisTarihi, paraGelisSaati, dekont, policeNumarasi, gelenPara, banka,
					dekontNo, odeyenBilgi, "Başarısız",
					"Hata:Exceldeki EFF_DATE (Paranın geliş tarihi) tarih formatında hata alındı.Lütfen kontrol ediniz...",
					BESTahsilatList);

		}

		// Teklif Tarihi

		String teklifTarihiGun = findElement(By.xpath("(//*[@id='screenarea']/tbody/tr[5])//td[@colspan='2'][2]"))
				.getText();
		String teklifTarihiAy = findElement(By.xpath("(//*[@id='screenarea']/tbody/tr[5])//td[@colspan='2'][3]"))
				.getText();
		String teklifTarihiYil = findElement(By.xpath("(//*[@id='screenarea']/tbody/tr[5])//td[@colspan='4'][1]"))
				.getText();

		String date1 = teklifTarihiGun + "/" + teklifTarihiAy + "/" + teklifTarihiYil;
		date1 = date1.trim();
		teklifTarihi = formatter.parse(date1);

		if (intikalTarihi.compareTo(teklifTarihi) == -1) {
			log.error("HATASözleşmenin teklif tarihi ile intikal tarihi uyumsuz...");

			throw new As400Exceptions(paraGelisTarihi, paraGelisSaati, dekont, policeNumarasi, gelenPara, banka,
					dekontNo, odeyenBilgi, "Başarısız", "HATA:Sözleşmenin teklif tarihi ile intikal tarihi uyumsuz...",
					BESTahsilatList);

		}

		// Yuruluk Tarihi

		String yurulukTarihiGun = findElement(By.xpath("(//*[@id='screenarea']/tbody/tr[6])//td[@colspan='1'][2]"))
				.getText();
		String yurulukTarihiAy = findElement(By.xpath("(//*[@id='screenarea']/tbody/tr[6])//td[@colspan='1'][3]"))
				.getText();
		String yurulukTarihiYil = findElement(By.xpath("(//*[@id='screenarea']/tbody/tr[6])//td[@colspan='4'][1]"))
				.getText();

		if (!yurulukTarihiGun.equals(" ") && !yurulukTarihiAy.equals(" ") && !yurulukTarihiYil.equals("    ")) {

			String date2 = yurulukTarihiYil + "-" + yurulukTarihiAy + "-" + yurulukTarihiGun;
			yurulukTarihi = formatter.parse(date2);

			if (intikalTarihi.after(yurulukTarihi)) {
				log.error("HATA:Vade tarihi sistem tarihinden ileri...");
				throw new As400Exceptions(paraGelisTarihi, paraGelisSaati, dekont, policeNumarasi, gelenPara, banka,
						dekontNo, odeyenBilgi, "Başarısız",
						"HATA:Sözleşmenin yruluk tarihi ile intikal tarihi uyumsuz....", BESTahsilatList);

			}
		}
		return this;

	}

	public AS400Actions BESDekontKontrolu(String policeNo, String paraGelisTarihi, String paragelissaati, String dekont,
			String banka, String gelenPara, String dekontNo, String odeyenBilgi, List<String[]> BESTahsilatList)

			throws As400Exceptions, InterruptedException, IOException, ParseException {

		// Dekont açıklaması kontrolu

		dekont = dekont.toLowerCase();

		if (!dekont.contains("toplu odeme") && !dekont.contains("toplu ödeme") && !dekont.contains("ek katki")
				&& !dekont.contains("ek katkı") && !dekont.contains("ara odeme") && !dekont.contains("ara ödeme")
				&& !dekont.contains("ek odeme") && !dekont.contains("ek ödeme") && !dekont.contains("ekp")
				&& !dekont.contains("ilave ödeme") && !dekont.contains("ılave odeme") && !dekont.contains("ilave odeme")
				&& !dekont.contains("ılave ödeme") && !dekont.contains("toplu para") && !dekont.contains("TOPLU PARS"))

		{

			log.error("HATA:Toplu ödeme için aranan dekont açıklaması bulunamadı. Manuel bakılması gerekmektedir.");
			throw new As400Exceptions(paraGelisTarihi, paragelissaati, dekont, policeNo, gelenPara, banka, dekontNo,
					odeyenBilgi, "Başarısız",
					"HATA:Toplu ödeme için aranan dekont açıklaması bulunamadı. Manuel bakılması gerekmektedir.",
					BESTahsilatList);
		}

//		
//		if (!dekont.contains("Toplu Ödeme") && !dekont.contains("Ek Katkı") && !dekont.contains("Ara Ödeme")
//				&& !dekont.contains("ARA ÖDEME") && !dekont.contains("EK ÖDEME") && !dekont.contains("EKP")
//				&& !dekont.contains("İlave ödeme") && !dekont.contains("TOPLU ODEME") && !dekont.contains("TOPLU ÖDEME")
//				&& !dekont.contains("toplu odeme") && !dekont.contains("toplu ödeme") && !dekont.contains("ek katkı")
//				&& !dekont.contains("EK KATKI") && !dekont.contains("ARA ODEME") && !dekont.contains("ara odeme")
//				&& !dekont.contains("ara ödeme") && !dekont.contains("ekp") && !dekont.contains("İLAVE ÖDEME")
//				&& !dekont.contains("ilave ödeme")) {
//			log.error("HATA:Toplu ödeme için aranan dekont açıklaması bulunamadı. Manuel bakılması gerekmektedir.");
//			throw new As400Exceptions("E", policeNo, paraGelisTarihi, paragelissaati, "Başarısız",
//					"HATA:Toplu ödeme için aranan dekont açıklaması bulunamadı. Manuel bakılması gerekmektedir.",
//					dekont, gelenPara, dekontNo, BESTahsilatList);
//
//		}

		return this;
	}

	public AS400Actions BESKatkiPayiBaslangicKapitaliTahsilat(String policeNumarasi, String miktarYTL,
			String paraGelisTarihi, String paraGelissaati, String banka, String dekont, String dekontNo,
			String odeyenBilgi, List<String[]> BESTahsilatList, List<String[]> BESTahsilatBasariliDataList)
			throws As400Exceptions, InterruptedException, IOException, ParseException {

		miktarYTL = miktarYTL.trim();
		miktarYTL = miktarYTL.replace(",", "");
		double gelenPara = Double.parseDouble(miktarYTL);

		if (gelenPara <= 30000.0) {
			log.error(
					"HATA: Gelen para teminat tutarınından  30.000 TL'den az veya eşit..." + "Gelen Para:" + miktarYTL);

			throw new As400Exceptions(paraGelisTarihi, paraGelissaati, dekont, policeNumarasi, "" + gelenPara + "",
					banka, dekontNo, odeyenBilgi, "Başarısız",
					"HATA: Gelen para teminat tutarindan 30.000 TL'den az veya eşit...", BESTahsilatList);

		}

		BESTahsilatPoliceBilgileri(policeNumarasi, paraGelisTarihi, paraGelissaati, dekont, "" + gelenPara + "",
				dekontNo, banka, odeyenBilgi, BESTahsilatList);

		String teminatTutari = findElement(By.xpath("//*[@id='screenarea']/tbody/tr[8]//td[16]")).getText();
		teminatTutari = teminatTutari.trim();
		teminatTutari = teminatTutari.replace(",", "");
		double teminat = Double.parseDouble(teminatTutari);

		double kalanPara = gelenPara - teminat;

		if (kalanPara >= 30000.0) {

			findElement(By.xpath("//*[@id='screenarea']/tbody/tr//td//input[@class='iwa-editField' and @tabindex='5']"))
					.clear();
			findElement(By.xpath("//*[@id='screenarea']/tbody/tr//td//input[@class='iwa-editField' and @tabindex='5']"))
					.sendKeys("" + teminat + "");

			// Hesap numarası bilgisinin girildiği yer

			findElement(By.xpath("//*[@id='screenarea']/tbody/tr//td//input[@class='iwa-editField' and @tabindex='1']"))
					.sendKeys(banka);
			as400PressEnter();

			kontrolHataMesajı(policeNumarasi, paraGelisTarihi, paraGelissaati, dekont, "" + gelenPara + "", dekontNo,
					banka, odeyenBilgi, BESTahsilatList);
			kalanPara = gelenPara - teminat;
			log.info("Kalan para:" + kalanPara);
			sendKeys(Keys.F4);
			findElement(By.xpath("//tbody//tr[8]"));
			addBasariliToList(paraGelisTarihi, paraGelissaati, dekont, policeNumarasi, "" + teminat + "", banka,
					dekontNo, odeyenBilgi, "Başarılı", "", BESTahsilatBasariliDataList);
			sendKeys(Keys.F3);
			sendKeys(Keys.F3);
			BESTahsilatBaslangicKapitali(policeNumarasi, paraGelisTarihi, paraGelissaati, dekont, kalanPara, dekontNo,
					miktarYTL, banka, odeyenBilgi, BESTahsilatList, BESTahsilatBasariliDataList);
			// kontrolBasariliMesajı(policeNumarasi, paraGelisTarihi, paraGelissaati,
			// dekont, kalanPara, dekontNo, banka,
			// odeyenBilgi, BESTahsilatList);

		} else {

			log.error("HATA:Gelen paradan açık borç ödendiğinde kalan para" + kalanPara
					+ "TL olduğu için tahsilat işlemi yapılamadı...");

			throw new As400Exceptions(paraGelisTarihi, paraGelissaati, dekont, policeNumarasi, "" + gelenPara + "",
					banka, dekontNo, odeyenBilgi, "Başarısız",
					"HATA:Gelen paradan açık borç ödendiğinde kalan para" + kalanPara
							+ "TL olduğu için tahsilat işlemi yapılamadı..." + "Eksik Tutar:" + kalanPara,
					BESTahsilatList);

		}
		return this;
	}

	public AS400Actions BESTahsilatBaslangicKapitali(String policeNumarasi, String paraGelisTarihi,
			String paraGelissaati, String dekont, double kalanPara, String dekontNo, String miktarYTL, String banka,
			String odeyenBilgi, List<String[]> BESTahsilatList, List<String[]> BESTahsilatBasariliDataList)

			throws As400Exceptions, InterruptedException, IOException, ParseException {
		// Test
//		clickMenu(1);
//		clickMenu(210);
//		clickMenu(1);
//		clickMenu(29);

		// Canlı
		clickMenu(3);
		sendKeysWithLabel("ÇALIŞMA KİTAPLIĞI İÇİN YIL GİRİNİZ:", getSysYear());
		as400PressEnter();
		clickMenu(2);
		BESTahsilatPoliceBilgileriKapital(policeNumarasi, paraGelisTarihi, paraGelissaati, dekont, kalanPara, dekontNo,
				miktarYTL, banka, odeyenBilgi, BESTahsilatList, BESTahsilatBasariliDataList);
		log.info("Başarılı:BESTahsilatBaslangicKapitali başarılı.");
		return this;

	}

	public AS400Actions BESTahsilatPoliceBilgileriKapital(String policeNumarasi, String paraGelisTarihi,
			String paraGelissaati, String dekont, double kalanPara, String dekontNo, String miktarYTL, String banka,
			String odeyenBilgi, List<String[]> BESTahsilatList, List<String[]> BESTahsilatBasariliDataList)
			throws As400Exceptions, InterruptedException, IOException, ParseException {

		sendKeysWithLabel("Pol/Söz.Pak. :", "E");
		sendKeysWithLabel("Pol/Söz. NO  :", policeNumarasi);
		sendKeysWithLabel("Fiş türü     :", "C");
		sendKeysWithLabel("Tahsil şekli :", "7");

		// Paranın geliş tarihine bakılacak;
		ParaninGelisTarihi(policeNumarasi, paraGelisTarihi, paraGelissaati, dekont, "" + kalanPara + "", dekontNo,
				banka, odeyenBilgi, BESTahsilatList);

		sendKeysWithLabel("Yatırılan tut:", Double.toString(kalanPara));
		as400PressEnter();
		kontrolHataMesajıPoliceGirisi(policeNumarasi, paraGelisTarihi, paraGelissaati, dekont, kalanPara, banka,
				dekontNo, odeyenBilgi, BESTahsilatList);
		findElement(By.id("inpR7C107L18.id")).sendKeys("102011100000001500");
		as400PressEnter();
		sendKeys(Keys.F4);
		findElement(By.xpath("//tbody//tr[8]"));
		addBasariliToList(paraGelisTarihi, paraGelissaati, dekont, policeNumarasi, Double.toString(kalanPara), banka,
				dekontNo, odeyenBilgi, "Basarili", "", BESTahsilatBasariliDataList);

		log.info("Başarılı:Police bilgileri girildi.");

		return this;
	}

	public AS400Actions BESTahsilatKaydiSilme(String policeNumarasi, String paraGelisTarihi, String ParaGelissaati,
			String dekont, String gelenPara, String dekontNo, String banka, String odeyenBilgi,
			List<String[]> BESTahsilatList) throws As400Exceptions, InterruptedException, IOException {

//		// Test ortamında kullanılan menü
//		clickMenu("TÜM MENÜLER");
//		clickMenu(160);
//		clickMenu(36);

		// Canlı ortamında kullanılan menu
		clickMenu(2);
		clickMenu(1);
		sendKeysWithLabel("POLİÇE NUMARASINI GİRİNİZ...:", "E");
		sendKeysWithLabel("POLİÇE NUMARASINI GİRİNİZ...:", policeNumarasi, 2);
		as400PressEnter();
		kontrolHataMesajıPoliceKontrol(policeNumarasi, paraGelisTarihi, ParaGelissaati, dekont, gelenPara, dekontNo,
				banka, odeyenBilgi, BESTahsilatList);
		kontrolHataMesajıTahsilatSilme(policeNumarasi, policeNumarasi, paraGelisTarihi, ParaGelissaati, dekont,
				gelenPara, dekontNo, banka, odeyenBilgi, BESTahsilatList);

		sendKeys(Keys.F5);

		if (isElementExist(By.xpath("//*[@id='screenarea']/tbody/tr[11]"), 0) == true) {
			log.info("SİLİNECEK KAYIT BULUNAMAMIŞTIR..." + "E" + policeNumarasi);
			as400PressEnter();
			sendKeys(Keys.F3);
		}
		// Düzeltilecek
		else {

			as400PressEnter();
			sendKeys(Keys.F3);
			log.info("Başarılı:Tahsilata giden kaydı silindi...");
		}
		return this;
	}

	public AS400Actions BESTahsilatPoliceBilgileri(String policeNumarasi, String paraGelisTarihi, String paraGelissaati,
			String dekont, String gelenPara, String dekontNo, String banka, String odeyenBilgi,
			List<String[]> BESTahsilatList) throws As400Exceptions, InterruptedException, IOException, ParseException {

		// sendKeys(Keys.F3);
		// sendKeys(Keys.F3);
////		//Test ortamında kullanılan menü
//		clickMenu("TÜM MENÜLER");
//		clickMenu(211);
//		clickMenu(2);

		// Canlı ortamında kullanılan menü
		clickMenu(3);
		sendKeysWithLabel("ÇALIŞMA KİTAPLIĞI İÇİN YIL GİRİNİZ:", getSysYear());
		as400PressEnter();
		clickMenu(1);

		sendKeysWithLabel("Poliçe paket :", "E");
		sendKeysWithLabel("Poliçe no    :", policeNumarasi);
		sendKeysWithLabel("Fiş türü     :", "C");
		sendKeysWithLabel("Tahsil şekli :", "7");

		// Paranın geliş tarihine bakılacak

		ParaninGelisTarihi(policeNumarasi, paraGelisTarihi, paraGelissaati, dekont, gelenPara, dekontNo, banka,
				odeyenBilgi, BESTahsilatList);
		as400PressEnter();
		kontrolHataMesajı(policeNumarasi, paraGelisTarihi, paraGelissaati, dekont, gelenPara, dekontNo, banka,
				odeyenBilgi, BESTahsilatList);
		log.info("Başarılı:Police bilgileri girildi.");

		return this;
	}

	public void ParaninGelisTarihi(String policeNumarasi, String paraGelisTarihi, String paraGelisSaati, String dekont,
			String gelenPara, String dekontNo, String banka, String odeyenBilgi, List<String[]> BESTahsilatList)
			throws As400Exceptions, InterruptedException, IOException {

		// Paranın geliş tarihine bakılacak
		try {

			String paragelisayi = paraGelisTarihi.substring(0, 2);
			String paragelisgunu = paraGelisTarihi.substring(3, 5);
			String paragelisyili = paraGelisTarihi.substring(6, 8);

			sendKeysWithLabel("P.gel.tarih     :", paragelisayi, 1);
			sendKeysWithLabel("P.gel.tarih     :", paragelisgunu, 2);
			sendKeysWithLabel("P.gel.tarih     :", 20 + paragelisyili, 3);

		} catch (Exception e) {

			log.error(
					"Hata:Exceldeki EFF_DATE (Paranın geliş tarihi) tarih formatında hata alındı.Lütfen kontrol ediniz...");

			throw new As400Exceptions(paraGelisTarihi, paraGelisSaati, dekont, policeNumarasi, gelenPara, banka,
					dekontNo, odeyenBilgi, "Başarısız",
					"Hata:Exceldeki EFF_DATE (Paranın geliş tarihi) tarih formatında hata alındı.Lütfen kontrol ediniz...",
					BESTahsilatList);
		}

	}

	public AS400Actions stopAS400Session() {

		try {

			String stopSession = "//input[@name='emdisconnect']";

			if (isElementExist(By.xpath(stopSession), 1)) {
				findElement(By.xpath(stopSession)).click();
				log.info("AS400 session kapatildi....");
			}
		} catch (Exception e) {

			log.info("HATA:AS400 session kapatırkan sorun oluştu....");
		}
		return this;

	}

	public AS400Actions BESTahsilatGirisi(String policeNumarasi, double gelenPara, String banka, String paraGelisTarihi,
			String paraGelissaati, String dekont, String dekontNo, String odeyenBilgi, List<String[]> BESTahsilatList)
			throws As400Exceptions, InterruptedException, IOException, ParseException {

		String teminatTutari = findElement(By.xpath("//*[@id='screenarea']/tbody/tr[8]//td[16]")).getText();
		teminatTutari = teminatTutari.trim();

		int hesapnoindex = 1;
		double kalanPara = 0;
		double teminat = Double.parseDouble(teminatTutari);

		if (teminat > gelenPara + 8) {

			kalanPara = teminat - gelenPara;
			log.error("HATA: Gelen para teminat tutarınından " + Double.toString(kalanPara) + "TL  az...");

			throw new As400Exceptions(paraGelisTarihi, paraGelissaati, dekont, policeNumarasi, "" + gelenPara + "",
					banka, dekontNo, odeyenBilgi, "Başarısız",
					"HATA: Gelen para teminat tutarınından " + Double.toString(kalanPara) + " TL az...",
					BESTahsilatList);

		}

		for (int index = 5; isElementExist(
				By.xpath("//*[@id='screenarea']/tbody/tr//td//input[@class='iwa-editField' and @tabindex=" + index
						+ "]")); index = index + 5) {

			findElement(By.xpath(
					"//*[@id='screenarea']/tbody/tr//td//input[@class='iwa-editField' and @tabindex=" + index + "]"))
							.clear();
			findElement(By.xpath(
					"//*[@id='screenarea']/tbody/tr//td//input[@class='iwa-editField' and @tabindex=" + index + "]"))
							.sendKeys("" + teminat + "");

			// Hesap numarası bilgisinin girildiği yer

			findElement(By.xpath("//*[@id='screenarea']/tbody/tr//td//input[@class='iwa-editField' and @tabindex="
					+ hesapnoindex + "]")).sendKeys(banka);
			as400PressEnter();
			kontrolHataMesajı(policeNumarasi, paraGelisTarihi, paraGelissaati, dekontNo, "" + gelenPara + "", dekont,
					banka, odeyenBilgi, BESTahsilatList);
			hesapnoindex = hesapnoindex + 5;
			gelenPara = gelenPara - teminat;
			log.info("Kalan para:" + gelenPara);

		}

		sendKeys(Keys.F4);
		kontrolBasariliMesajı(policeNumarasi, paraGelisTarihi, paraGelissaati, dekont, gelenPara, dekontNo, banka,
				odeyenBilgi, BESTahsilatList);

		return this;
	}

	public void kontrolBasariliMesajı(String policeNumarasi, String paraGelisTarihi, String paraGelissaati,
			String dekont, double gelenPara, String dekontNo, String banka, String odeyenBilgi,
			List<String[]> BESTahsilatList) throws As400Exceptions, InterruptedException, IOException {

		throw new As400Exceptions(paraGelisTarihi, paraGelissaati, dekont, policeNumarasi, "" + gelenPara + "", banka,
				dekontNo, odeyenBilgi, "Başarılı", "", BESTahsilatList);
	}

	public void addBasariliToList(String paraGelisTarihi, String paraGelisSaati, String dekont, String policeNo,
			String gelenPara, String banka, String dekontNo, String odeyenBilgi, String sonuc, String exceptionSebebi,
			List<String[]> BESTahsilatBasariliDataList) throws InterruptedException, IOException {

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
		BESTahsilatBasariliDataList.add(temp);
	}

	public void kontrolHataMesajı(String policeNumarasi, String paraGelisTarihi, String paraGelissaati, String dekont,
			String gelenPara, String dekontNo, String banka, String odeyenBilgi, List<String[]> BESTahsilatList)
			throws As400Exceptions, InterruptedException, IOException {

		if (isElementExist(By.xpath("//*[@id='screenarea']/tbody/tr[27]/td[2]"), 1)) {
			if (!(getTextOfElement(findElement(By.xpath("//*[@id='screenarea']/tbody/tr[27]/td[2]"))).trim()
					.equals(""))) {
				log.info("Hata!! "
						+ getTextOfElement(findElement(By.xpath("//*[@id='screenarea']/tbody/tr[27]/td[2]"))));

				throw new As400Exceptions(paraGelisTarihi, paraGelissaati, dekont, policeNumarasi, "" + gelenPara + "",
						banka, dekontNo, odeyenBilgi, "Başarısız",
						getTextOfElement(By.xpath("//*[@id='screenarea']/tbody/tr[27]/td[2]")), BESTahsilatList);
			}

		}

	}

	public void kontrolHataMesajıTahsilatSilme(String policePaket, String policeNumarasi, String paraGelisTarihi,
			String paraGelissaati, String dekont, String gelenPara, String dekontNo, String banka, String odeyenBilgi,
			List<String[]> BESTahsilatList) throws As400Exceptions, InterruptedException, IOException {

		if (isElementExist(By.xpath("//*[@id='screenarea']/tbody/tr//td[@colspan='77']"), 1)) {
			if (!(getTextOfElement(findElement(By.xpath("//*[@id='screenarea']/tbody/tr//td[@colspan='77']"))).trim()
					.equals(""))) {
				log.info("Hata!! "
						+ getTextOfElement(findElement(By.xpath("/*[@id='screenarea']/tbody/tr//td[@colspan='77']"))));
				throw new As400Exceptions(paraGelisTarihi, paraGelissaati, dekont, policeNumarasi, "" + gelenPara + "",
						banka, dekontNo, odeyenBilgi, "Başarısız",
						getTextOfElement(By.xpath("/*[@id='screenarea']/tbody/tr//td[@colspan='77']")),
						BESTahsilatList);

			}

		}

	}

	public void kontrolHataMesajıPoliceKontrol(String policeNumarasi, String paraGelisTarihi, String paraGelissaati,
			String dekont, String gelenPara, String dekontNo, String banka, String odeyenBilgi,
			List<String[]> BESTahsilatList) throws As400Exceptions, InterruptedException, IOException {

		if (isElementExist(By.xpath("(/*//tr//td[@colspan='78'])[2]"), 1)) {
			if (!(getTextOfElement(findElement(By.xpath("(/*//tr//td[@colspan='78'])[2]"))).trim().equals(""))) {

				log.info("Hata!! " + getTextOfElement(findElement(By.xpath("(/*//tr//td[@colspan='78'])[2]"))));
				throw new As400Exceptions(paraGelisTarihi, paraGelissaati, dekont, policeNumarasi, "" + gelenPara + "",
						banka, dekontNo, odeyenBilgi, "Başarısız",
						getTextOfElement(By.xpath("/*[@id='screenarea']/tbody/tr//td[@colspan='77']")),
						BESTahsilatList);

			}

		}

	}

	public void kontrolHataMesajıPoliceGirisi(String policeNumarasi, String paraGelisTarihi, String paraGelissaati,
			String dekont, Double gelenPara, String banka, String dekontNo, String odeyenBilgi,
			List<String[]> BESTahsilatList) throws As400Exceptions, InterruptedException, IOException {

		if (isElementExist(By.xpath("//*[@id='screenarea']/tbody/tr[27]/td[3]"), 1)) {
			if (!(getTextOfElement(findElement(By.xpath("//*[@id='screenarea']/tbody/tr[27]/td[3]"))).trim()
					.equals(""))) {

				log.info("Hata!! "
						+ getTextOfElement(findElement(By.xpath("//*[@id='screenarea']/tbody/tr[27]/td[3]"))));

				throw new As400Exceptions(paraGelisTarihi, paraGelissaati, dekont, policeNumarasi, "" + gelenPara + "",
						banka, dekontNo, odeyenBilgi, "Başarısız",
						getTextOfElement(By.xpath("//*[@id='screenarea']/tbody/tr[27]/td[3]")), BESTahsilatList);

			}
		}

	}

}
