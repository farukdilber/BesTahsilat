package com.avivasa.rpa.library;

import java.net.MalformedURLException;
import java.time.LocalDate;
import java.util.List;

import org.jfree.util.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.avivasa.rpa.base.AbstractPage;
import com.avivasa.rpa.pageobject.AS400ObjectRepository.AS400CommonPage;
import com.avivasa.rpa.pageobject.ObjectRepository.AS400ObjectRepostiory;
import com.avivasa.rpa.utiliy.log;

public class AS400Library extends AbstractPage {

	public String fileName = null;

	
	public <T> AS400Library() throws MalformedURLException {

		super();
	}
	
	public <T> AS400Library(String browserName) throws MalformedURLException {

		super(browserName);
	}
	
	public void control(boolean statement, String onTrue, String onFalse) {

		if (statement == true) {
//			LogPASS(onTrue);
			log.info(onTrue);
		} else {
//			LogFAIL(onFalse);
			log.info(onFalse);
			Assert.assertTrue(false);
		}
	}
	
	public void control(By by, String onTrue, String onFalse) {

		try {
			if (getTextOfElement(by).contains(onTrue)) {
				LogPASS(onTrue);
			} else {
				LogFAIL(onFalse);
				Assert.assertTrue(false);
			}

		} catch (Exception e) {
			LogFAIL(onFalse);
			Assert.assertTrue(false);
		}
	}

	
	protected String findMenuSayisi(String text) throws InterruptedException {

		text = text.replace(" ", " ");// &nbsp çevirici - alt+0160
		text = "//a[starts-with(text(),'" + text + "')]/parent::td/parent::tr/td[@colspan='3']/a";
		while (!isElementExist(By.xpath(text), 3)) {
			if (isElementExist(AS400CommonPage.btnMore.getBy(), 3))
				click(AS400CommonPage.btnMore.getBy());
			else {
				LogFAIL("İlgili menü bulunamadı...");
				Log.error("İlgili menü bulunamadı...");
				Assert.assertTrue(false);
			}
		}
		String str = findElement(By.xpath(text)).getText();
		// str = str.replace(" ", "");
		Log.info("Menüye giriş yapıldı...");
		LogPASS("menüye giriş yapıldı...");
		return str;
	}
	
	protected void clickMenu(String text) throws InterruptedException {
		String menuNo = findMenuSayisi(text);
		sendKeys(AS400ObjectRepostiory.txtSecim, menuNo);
		as400PressEnter();
	}
	
	protected void clickMenu(int menuNo) throws InterruptedException {
		sendKeys(AS400ObjectRepostiory.txtSecim, Integer.toString(menuNo));
		as400PressEnter();

	}

	/**
	 * AS400 için Enter a basar.
	 * 
	 * @throws InterruptedException
	 */
	protected void as400PressEnter() throws InterruptedException {
		click(AS400CommonPage.btnEnter.getBy());
//		LogPASS("Enter");
	}
	
	protected void as400SendKeysEnter(By by, String text) throws InterruptedException {
		sendKeys(by, text);
		as400PressEnter();
	}

	protected void as400SendKeys(By by, String text) throws InterruptedException {
		sendKeys(by, text);
	}

	/**
	 * AS400 de form doldurmak için kullanılır.
	 * 
	 * @param label
	 *            Doldurulacak alanın solunda bulununa text
	 * @param text
	 *            Doldurulacak alana yazılacak text
	 * @param index
	 *            Eğer doldurulacak alan 1den fazla ise(Örn: Tarih) index
	 *            kullanılır.
	 * @throws InterruptedException
	 */
	protected WebElement as400FindElement(String label, int... index) throws InterruptedException {
		return as400FindElement(label, "input", index);
	}

	/**
	 * AS400 de form doldurmak için kullanılır.
	 * 
	 * @param label
	 *            Doldurulacak alanın solunda bulununa text
	 * @param text
	 *            Doldurulacak alana yazılacak text
	 * @param index
	 *            Eğer doldurulacak alan 1den fazla ise(Örn: Tarih) index
	 *            kullanılır.
	 * @throws InterruptedException
	 */
	protected WebElement as400FindElement(String label, String tagName, int... index) throws InterruptedException {
		label = label.replace(" ", " ");// &nbsp çevirici - alt+0160
		String i = "1";
		if (index.length != 0)
			i = Integer.toString(index[0]);
		return findElement(By.xpath("(//a[starts-with(text(),'" + label + "')]/ancestor::td//following-sibling::td/"
				+ tagName + ")[" + i + "]"));

	}

	/**
	 * AS400 de form doldurmak için kullanılır.
	 * 
	 * @param label
	 *            Doldurulacak alanın solunda bulununa text
	 * @param text
	 *            Doldurulacak alana yazılacak text
	 * @param index
	 *            Eğer doldurulacak alan 1den fazla ise(Örn: Tarih) index
	 *            kullanılır.
	 * @throws InterruptedException
	 */
	protected void clickWithLabel(String label, int... index) throws InterruptedException {
		as400FindElement(label, index).click();
	}

	protected boolean ErrorMessageControl(String message) throws InterruptedException {

		if (isElementExist(AS400CommonPage.errorMessage.getBy(), 1)) {

			if (findElement(AS400CommonPage.errorMessage.getBy()).getText().contains(message)) {
				return true;
			} else {
				Log.info("Hata mesajı: " + message);
				Assert.assertTrue(false);
				return false;
			}
		} else
			return false;
	}

	/**
	 * AS400 de form doldurmak için kullanılır.
	 * 
	 * @param label
	 *            Doldurulacak alanın solunda bulununa text
	 * @param text
	 *            Doldurulacak alana yazılacak text
	 * @param index
	 *            Eğer doldurulacak alan 1den fazla ise(Örn: Tarih) index
	 *            kullanılır.
	 * @throws InterruptedException
	 */
	protected void sendKeysWithLabel(String label, String text, int... index) throws InterruptedException {
		as400FindElement(label, index).clear();
		as400FindElement(label, index).sendKeys(text);
		LogPASS(label + ": " + text);
	}

	protected void sendKeys(Keys key) throws InterruptedException {
		sendKeys(By.xpath("//table//input"), key);
	}

	protected void fKeys(Keys key) throws InterruptedException {
		fKeys(key, 1);
	}

	protected void fKeys(Keys key, int count) throws InterruptedException {
		for (int i = 0; i < count; i++)
			sendKeys(key);
	}

	public void SelectMenuInForm(String label, int menuIndex, int... index) throws InterruptedException {
		as400FindElement(label, index).clear();
		as400PressEnter();
		SelectInMenu(menuIndex);
		LogPASS(label + ": " + menuIndex);
	}

	public void SelectTable(int... index) throws InterruptedException {
		String i = "1";
		if (index.length != 0)
			i = Integer.toString(index[0]);
		as400SendKeysEnter(By.xpath("//input[@class='iwa-editField' and @tabindex=" + i + "]"), "X");
//		LogPASS("Munu Secildi \"" + index + "\" Passed");
	}

	public void SelectInMenu(int... index) throws InterruptedException {
		Wait(500);

		String i = "1";
		if (index.length != 0)
			i = Integer.toString(index[0]);
		as400SendKeysEnter(By.xpath("//input[@class='iwa-editField' and @tabindex=" + i + "]"), "X");
//		LogPASS("Munu Secildi \"" + index + "\" Passed");
	}

	public void selectMenuItem(String menu, int menuID) throws InterruptedException {
		clickMenu(menuID);
		logger.info(menu);
	}

	/**
	 * AS400 de form doldurmak için kullanılır.
	 * 
	 * @param label
	 *            Doldurulacak alanın solunda bulununa text
	 * @param text
	 *            Doldurulacak alana yazılacak text
	 * @param index
	 *            Eğer doldurulacak alan 1den fazla ise(Örn: Tarih) index
	 *            kullanılır.
	 * @throws InterruptedException
	 */
	protected void sendKeysWithLabel(String label, Keys text, int... index) throws InterruptedException {
		as400FindElement(label, index).sendKeys(text);
		LogPASS(label + ": " + text.toString());
	}

	protected WebElement AS400findElementOnTable(int columnIndex, String columnInput, int getcolumnIndex,
			By getColumnByTagName) throws InterruptedException {
		WebElement table = findElement(By.xpath("//table")).findElement(By.tagName("tbody"));

		List<WebElement> allRows = table.findElements(By.tagName("tr"));
		WebElement elem = null, elem2 = null;
		for (WebElement row : allRows) {
			elem2 = row.findElements(By.tagName("td")).get(columnIndex - 1);
			elem = row.findElements(By.tagName("td")).get(getcolumnIndex - 1);

			if (!elem2.getText().equals(columnInput)) {
				return elem;
			}
		}
		if (elem != null)
			return elem;
		return null;
	}

	protected static LocalDate SysdateMinus30Days() {
		LocalDate localDate = null;
		localDate = LocalDate.now();
		localDate = localDate.minusDays(30);
		return localDate;
	}
	

}
