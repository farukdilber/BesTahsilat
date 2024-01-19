package com.avivasa.rpa.base;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.mail.MessagingException;

import org.testng.ITestContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.avivasa.rpa.util.Configuration;
import com.avivasa.rpa.util.ThreadController;
import com.avivasa.rpa.utiliy.UtilityMethods;
import com.avivasa.rpa.utiliy.log;

public class AbstractTest {

	// protected RemoteWebDriver driver;
	protected static Configuration config = Configuration.getInstance();
	public static String reportFilePath;
	public static String sysdate = getSysDateCustom();
	public URL REMOTE_URL;
	public static ThreadController threadController = new ThreadController();
	UtilityMethods um = new UtilityMethods();

	@SuppressWarnings("static-access")
	public static class AutomationVariables {
		public static final String ENVIRONMENT = config.getEnv();// AbstractTest.ENVIRONMENT;
		public static final String MACHINE = config.getMachine();// AbstractTest.MACHINE;
		public static final String APP = config.getApp();
		public static final String TEXECKEY = config.getTexeckey();
		public static final String TPLANKEY = config.getTplankey();
		public static final String[] EMAILS = config.getEmails();
		public static final boolean ScreenShot = false;
		public static final boolean SEND_MAIL = true;
	}


	@BeforeSuite
	public void StartSuite(ITestContext ctx) throws MalformedURLException, RemoteException {
		log.startTestCase(ctx.getCurrentXmlTest().getSuite().getName());
			
		// REMOTE_URL = new URL(GetData.REMOTE_MACHINE_IP);
	}

	@AfterSuite(enabled = false)
	public void SendResult(ITestContext ctx)
			throws IOException, InterruptedException, MessagingException, SQLException {
	}

	public static String getSysDateCustom() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd-HH;mm;ss");
		LocalDateTime now = LocalDateTime.now();
		String sysDate = dtf.format(now);

		return sysDate;
	}

	public static Date getSysDateFormat(String tarih) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyyy");
		
		Date date = (Date) dtf.parse(tarih);
		return date;
	}



}
