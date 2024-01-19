package com.avivasa.rpa.actions;

import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;

import com.avivasa.rpa.base.AbstractPage;
import com.avivasa.rpa.data.GetData;
import com.avivasa.rpa.utiliy.log;

public class CreamActions extends AbstractPage {

	public <T> CreamActions() throws MalformedURLException {

		super();
	}
	
	public CreamActions openUrl(String url) {

		navigateToCream(url);
		log.info("openUrl transaction passed");

		return this;
	}
	
	

	public CreamActions login(String username, String password) throws InterruptedException, SQLException {

		driver.switchTo().frame("WA0");
		driver.switchTo().frame("CONTENT");
		driver.switchTo().frame("WA1");
		sendKeys(By.id("F_12"), username, false);
		sendKeys(By.id("F_15"), password, false);
		click(By.id("B_21"));
		driver.manage().timeouts().implicitlyWait(GetData.DEFAULT_WAIT, TimeUnit.SECONDS);
		LogPASS("Login Passed");

		return this;
	}

		public CreamActions menuSorumluTakip() throws InterruptedException {
			driver.switchTo().parentFrame();
			driver.switchTo().parentFrame();
			driver.switchTo().parentFrame();
			driver.switchTo().frame("WA0");
			driver.switchTo().frame("AVAILABLEACTIVITIES");
			driver.switchTo().frame("WA0");
			// waitForElement(By.id("CasaBUTTONLIST27BUTTON3"));
			click(By.id("CasaBUTTONLIST27BUTTON3"));
			driver.manage().timeouts().implicitlyWait(GetData.DEFAULT_WAIT, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(GetData.DEFAULT_WAIT, TimeUnit.SECONDS);
			click(By.id("CasaCLIENTTREE26Link1"));// CTREE_TR26_1
			return this;
		}

		public CreamActions SorumluTakip() throws InterruptedException {
			driver.switchTo().parentFrame();
			driver.switchTo().parentFrame();
			driver.switchTo().parentFrame();
			driver.switchTo().frame("WA0");
			driver.switchTo().frame("CONTENT");
			driver.switchTo().frame("WA0");
			click(By.id("RADIO11"));
			return this;
		}
		
		public CreamActions BasvuruOnayListesi() throws InterruptedException {
			
			driver.switchTo().parentFrame();
			driver.switchTo().parentFrame();
			driver.switchTo().parentFrame();
			driver.switchTo().frame("WA0");
			driver.switchTo().frame("AVAILABLEACTIVITIES");
			driver.switchTo().frame("WA0");
			// waitForElement(By.id("CasaBUTTONLIST27BUTTON3"));
			click(By.id("CasaBUTTONLIST27BUTTON2"));
			driver.manage().timeouts().implicitlyWait(GetData.DEFAULT_WAIT, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(GetData.DEFAULT_WAIT, TimeUnit.SECONDS);
			click(By.id("CasaCLIENTTREE26Link2"));// CTREE_TR26_1
			return this;
			
		}
		
		
		public CreamActions BasvuruOnayListesiSorgula(String paketkodu, String policeNo) throws InterruptedException {
			
			driver.switchTo().parentFrame();
			driver.switchTo().parentFrame();
			driver.switchTo().parentFrame();
			driver.switchTo().frame("WA0");
			driver.switchTo().frame("CONTENT");
			driver.switchTo().frame("WA0");
			sendKeys(By.id("F_45"),paketkodu);
			sendKeys(By.id("F_47"),policeNo);
			click(By.id("TDB101"));
			driver.manage().timeouts().implicitlyWait(GetData.DEFAULT_WAIT, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(GetData.DEFAULT_WAIT, TimeUnit.SECONDS);
			return this;
		}

		
		public CreamActions BasvuruOnayListesiGoruntuleme() throws InterruptedException {
			
			driver.switchTo().parentFrame();
			driver.switchTo().parentFrame();
			driver.switchTo().parentFrame();
			driver.switchTo().frame("WA0");
			driver.switchTo().frame("CONTENT");
			driver.switchTo().frame("WA0");
			doubleClick(By.id("TGROW119_0"));
		//	click(By.id("TGROW119_0"));	
			return this;
		}

	public CreamActions BasvuruOzetEkrani() throws InterruptedException {
		

		for (String winHandle : driver.getWindowHandles())
			driver.switchTo().window(winHandle);	
			driver.switchTo().parentFrame();
			driver.switchTo().parentFrame();
			driver.switchTo().parentFrame();
			driver.switchTo().frame("WA0");
			click(By.id("ICONIMG5"));
			driver.manage().timeouts().implicitlyWait(GetData.DEFAULT_WAIT, TimeUnit.SECONDS);
			getTextOfElement(By.id("CDYN_50"));
			return this;
		  
		}

	public CreamActions UrunOdemeBilgileri() throws InterruptedException {
		


			selectCombobox(By.id("CDYN_346"),"2");
			return this;
			
		  
		}
			
		public CreamActions SozlesmeSorgula(String sozlesmeNo) throws InterruptedException {
			sendKeys(By.id("F_88"), sozlesmeNo);
			click(By.id("B_148"));
			click(By.xpath("//table[@testtoolid='pagingLines.items_table']//tr[2]/td"));
			return this;
		}

		public CreamActions AtamaYap(String atamaYapılacakSicilNo) throws InterruptedException {
			click(By.id("RADIO190"));
			click(By.id("FIC_222"));

			String winHandleBefore = driver.getWindowHandle();
			for (String winHandle : driver.getWindowHandles())
				driver.switchTo().window(winHandle);

			driver.switchTo().parentFrame();
			driver.switchTo().parentFrame();
			driver.switchTo().parentFrame();
			driver.switchTo().frame("WA0");
			sendKeys(By.id("F_15"), atamaYapılacakSicilNo);
			click(By.id("B_12"));
			doubleClick(By.xpath("//table[@testtoolid='lines.items_table']//tr[2]/td"));

			driver.switchTo().window(winHandleBefore);
			driver.switchTo().parentFrame();
			driver.switchTo().parentFrame();
			driver.switchTo().parentFrame();
			driver.switchTo().frame("WA0");
			driver.switchTo().frame("CONTENT");
			driver.switchTo().frame("WA0");
			selectCombobox(By.id("CDYN_239"), "6");
			sendKeys(By.id("FIELD243"), "Robot Otomatik  Atama");
			click(By.id("B_255"));

			for (String winHandle : driver.getWindowHandles())
				driver.switchTo().window(winHandle);
			driver.switchTo().parentFrame();
			driver.switchTo().parentFrame();
			driver.switchTo().parentFrame();
			driver.switchTo().frame("WA0");
			click(By.id("B_9"));

			driver.switchTo().window(winHandleBefore);
			driver.switchTo().parentFrame();
			driver.switchTo().parentFrame();
			driver.switchTo().parentFrame();
			driver.switchTo().frame("WA0");
			driver.switchTo().frame("CONTENT");
			driver.switchTo().frame("WA0");
			return this;

		}

	}



