package com.avivasa.rpa.util;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.jfree.util.Log;
import org.openqa.selenium.By;

import com.avivasa.rpa.base.AbstractTest.AutomationVariables;
import com.avivasa.rpa.data.GetData.*;

public class DataFinder {

	private DataFinder() {
		throw new IllegalStateException("Utility class");
	}

	private static Map<String, Properties> fileList = new HashMap<>();

	public static final String getUrl(Url u) {
		return getValue("GetData", u.name());
	}

	public static final String getLoginInfo(LoginInfo l) {
		return getValue("GetData", l.name());
	}

	public static final String getDBInfo(String s) {
		return getDBValue(s);
	}

	public static final String getData(Data u) {
		return getValue("GetData", u.name());
	}

	public static final By getRopBy(String key) {
		return getBy("RopObjectRepository", key);
	}

	public static By getAs400By(String key) {
		return getBy("AS400ObjectRepository", key);
	}

	public static final By getKisBy(String key) {
		return getBy("KisObjectRepository", key);
	}

	public static final By getMiniBisBy(String key) {
		return getBy("MiniBisObjectRepository", key);
	}

	private static String getDBValue(String key) {
		return getValue("DB", key);
	}

	public static String getValue(String key) {
		return getValue("GetData", key);
	}

	private static String getValue(String filePath, String key) {
		return getProp(filePath).getProperty(key);
	}

	private static By getBy(String filePath, String key) {
		String value = getValue(filePath, key);

		String[] values = value.split(":");

		StringBuilder bld = new StringBuilder();
		bld.append(values[1]);
		for (int i = 2; i < values.length; i++)
			bld.append(":"+values[i]);

		String locate = bld.toString();

		By by = null;
		switch (values[0]) {
		case "xpath":
			by = By.xpath(locate);
			break;
		case "cssSelector":
			by = By.cssSelector(locate);
			break;
		case "css":
			by = By.cssSelector(locate);
			break;
		case "id":
			by = By.id(locate);
			break;
		case "name":
			by = By.name(locate);
			break;
		default:
			Log.error(values[0] + " değeri bulunamadı! \nProperties dosyasını kontrol ediniz.\n");
			assertTrue(false);
			break;
		}
		return by;
	}

	private static Properties getProp(String filePath) {
		if (!fileList.containsKey(filePath)) {
			fileList.put(filePath, createProp(filePath));
		}
		return fileList.get(filePath);
	}

	private static Properties createProp(String filePath) {
		InputStream is = null;
		Properties prop = new Properties();
		try {
			is = ClassLoader.getSystemResourceAsStream(filePath + AutomationVariables.ENVIRONMENT + ".properties");
			prop.load(is);
		} catch (Exception e) {
			Log.error(filePath + AutomationVariables.ENVIRONMENT + ".properties" + " dosyası bulunamadı! \n\n" + e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					Log.error("Dosya kapatılamadı! \n\n" + e);
				}
			}
		}
		return prop;
	}
}
