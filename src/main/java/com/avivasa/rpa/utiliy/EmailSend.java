package com.avivasa.rpa.utiliy;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.avivasa.rpa.base.AbstractTest;
import com.avivasa.rpa.base.AbstractTest.AutomationVariables;
import com.avivasa.rpa.data.GetBESTahsilatData;

public class EmailSend {

	static String sysdate = AbstractTest.sysdate;
	// from Mail Address
	static String from = "Techbot_Destek@avivasa.com.tr";

	// to Mail Address for Report
	static String to = "gulizar.ceylan@avivasa.com.tr";
	static String to1 = "Avivasa-TR_OK_Tahsilat_Destek@avivasa.com.tr";
	static String to2 = "Techbot_Destek@avivasa.com.tr";

	// to Mail Address for File Errors
	static String fileTo = "";
	static String fileCC = "";

	private EmailSend() {
		throw new IllegalStateException("Utility class");
	}

	public static void dosyaBulunamadi(String path, String fileName) {
		mailGonder("TechBot - BES Havale ile Gelen Odemeler Tahsilat Sureci - Hata Bildirimi",
				"Sayin Yetkili," + "<br><br>" + fileName + " isimli dosya bulunamamistir." + "<br><br>" + "Dosya Ismi: "
						+ fileName + "<br><br>" + "Dosya Yolu: " + path + fileName + "<br><br><br>"
						+ "Bu e-posta TechBot tarafindan otomatize edilip tarafiniza gönderilmistir.");
	}

	public static void dosyaAcik(String path, String fileName) {
		mailGonder("TechBot - BES Havale ile Gelen Odemeler Tahsilat Sureci - Hata Bildirimi",
				"Sayin Yetkili," + "<br><br>" + fileName
						+ " isimli dosyanin acik olmamasi gerekiyor. Lutfen dosyayi kapatiniz." + "<br><br>"
						+ "Dosya Ismi: " + fileName + "<br><br>" + "Dosya Yolu: " + path + fileName + "<br><br><br>"
						+ "Bu e-posta TechBot tarafindan otomatize edilip tarafiniza gönderilmistir.");
	}

	public static void dosyaOkunamadi(String path, String fileName) {
		mailGonder("TechBot - BES Havale ile Gelen Odemeler Tahsilat Sureci - Hata Bildirimi",
				"Sayin Yetkili," + "<br><br>" + fileName
						+ " isimli dosya okunurken hata meydana geldi. Dosya formati dogru olmayabilir." + "<br><br>"
						+ "Dosya Ismi: " + fileName + "<br><br>" + "Dosya Yolu: " + path + fileName + "<br><br><br>"
						+ "Bu e-posta TechBot tarafindan otomatize edilip tarafiniza gönderilmistir.");

		log.info("Hata bildirimi maili gönderildi. Ek : " + fileName);

	}

	public static void menuAcilmadi(String menu) {
		String version = "";
		mailGonder("Otomasyon Hata Bildirimi - RPA - BES Havale ile Gelen Odemeler Tahsilat Sureci",
				"Menü : " + menu + "-" + version + "<br><br> Menü açılmadı. ");
	}

	public static void as400LoginHatasi() {
		mailGonder("TechBot - BES Havale ile Gelen Odemeler Tahsilat Sureci - Hata Bildirimi", "AS400 baglanti hatasi."
				+ "<br><br><br>" + "Bu e-posta TechBot tarafindan otomatize edilip tarafiniza gönderilmistir.");
	}

	public static void as400CloseHatasi() {
		mailGonder("TechBot - BES Havale ile Gelen Odemeler Tahsilat Sureci - Hata Bildirimi",
				"AS400 baglanti kapatma hatasi." + "<br><br><br>"
						+ "Bu e-posta TechBot tarafindan otomatize edilip tarafiniza gönderilmistir.");
	}

	public static void as400SelectHatasi() {
		mailGonder("TechBot - BES Havale ile Gelen Odemeler Tahsilat Sureci - Hata Bildirimi", "AS400 sorgu hatasi."
				+ "<br><br><br>" + "Bu e-posta TechBot tarafindan otomatize edilip tarafiniza gönderilmistir.");
	}

	public static void excelDosyasiOlusturmaHatasi() {
		mailGonder("TechBot - BES Havale ile Gelen Odemeler Tahsilat Sureci - Hata Bildirimi",
				"Excel dosyasi olusturma hatasi." + "<br><br><br>"
						+ "Bu e-posta TechBot tarafindan otomatize edilip tarafiniza gönderilmistir.");
	}

	public static void excelDosyasiDoldurmaHatasi() {
		mailGonder("TechBot - BES Havale ile Gelen Odemeler Tahsilat Sureci - Hata Bildirimi",
				"Excel dosyasi data doldurma hatasi." + "<br><br><br>"
						+ "Bu e-posta TechBot tarafindan otomatize edilip tarafiniza gönderilmistir.");
	}

	public static void excelDosyasiKumulDoldurmaHatasi() {
		mailGonder("TechBot - BES Havale ile Gelen Odemeler Tahsilat Sureci - Hata Bildirimi",
				"Excel dosyasi kumul data doldurma hatasi." + "<br><br><br>"
						+ "Bu e-posta TechBot tarafindan otomatize edilip tarafiniza gönderilmistir.");
	}

	public static void raporListesiDoldurmaHatasi() {
		mailGonder("TechBot - BES Havale ile Gelen Odemeler Tahsilat Sureci - Hata Bildirimi",
				"Rapor listesi doldurma hatasi." + "<br><br><br>"
						+ "Bu e-posta TechBot tarafindan otomatize edilip tarafiniza gönderilmistir.");
	}

	public static void BESTahsilatBulunamadiHatasi() {
		mailGonder("TechBot - BES Havale ile Gelen Odemeler Tahsilat Sureci - Hata Bildirimi",
				" BES Tahsilat işlemleri icin data bulunamadi." + "<br><br><br>"
						+ "Bu e-posta TechBot tarafindan otomatize edilip tarafiniza gönderilmistir.");
	}

	public static void BESTahsilatSonucRaporu(String excelFileName) {

		mailGonder("TechBot - BES Havale ile Gelen Odemeler Tahsilat Sureci Sonuc Raporu - " + excelFileName,
				"Sayin Yetkili," + "<br><br>"
						+ "TechBot BES Havale ile Gelen Odemeler Tahsilat Sureci raporu dosyasina ait sonuclar ekte bilgilerinize sunulmustur."
						+ "<br><br>" + excelFileName + " <br><br><br>"
						+ "Bu e-posta TechBot tarafindan otomatize edilip tarafiniza gönderilmistir.",
				"Sonuc", excelFileName);
		log.info("############## Onay maili gönderildi. Ek : " + excelFileName + "  ##############");
	}

	private static void mailGonder(String subject, String body) {
		mailGonder(subject, body, "", "");
	}

	private static void mailGonder(String subject, String body, String flag, String fname) {
		if (!AutomationVariables.SEND_MAIL)
			return;
		// mail host bilgisi
		Properties props = new Properties();
		props.put("mail.smtp.host", "192.168.239.251");
		Session session = Session.getDefaultInstance(props, null);

		// mail içeriği
		Message msg = new MimeMessage(session);
		try {
			msg.setFrom(new InternetAddress(from));
			msg.setSubject(subject);

//			msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to2));

//			msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
//			msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to1));

			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to1));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to2));
//			msg.addRecipient(Message.RecipientType.CC, new InternetAddress(fileCC));

			if (AutomationVariables.EMAILS != null) {
				msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
				for (String mails : AutomationVariables.EMAILS)
					msg.addRecipient(Message.RecipientType.TO, new InternetAddress(mails));
			}
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(body, "text/html");

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);

			if (flag.equals("Sonuc")) {

				messageBodyPart = new MimeBodyPart();
				String fileName = fname;
				DataSource source = new FileDataSource(GetBESTahsilatData.filePath + fileName);
				messageBodyPart.setDataHandler(new DataHandler(source));
				messageBodyPart.setFileName(fileName);
				multipart.addBodyPart(messageBodyPart);
			}

			msg.setContent(multipart);
			Transport.send(msg);
			log.info("Mail gönderildi");
		} catch (Exception e) {
			log.error("Mail gönderilemedi. : " + e.toString());
		}
	}

}
