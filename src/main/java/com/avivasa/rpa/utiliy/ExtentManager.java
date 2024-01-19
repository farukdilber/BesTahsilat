package com.avivasa.rpa.utiliy;

import java.io.File;

import com.avivasa.rpa.base.AbstractTest;
import com.avivasa.rpa.base.AbstractTest.AutomationVariables;
import com.avivasa.rpa.data.GetData;
import com.relevantcodes.extentreports.ExtentReports;

public class ExtentManager {

	static ExtentReports extent;
	static String sysdate = AbstractTest.sysdate;
	public static final String REPORT_FILE_PATH = GetData.RESULT_FILE_PATH + "Test Results" + File.separator
			+ System.getProperty("user.name") + File.separator + sysdate + "_" + "robotdemo" + "_"
			+ AutomationVariables.ENVIRONMENT + "_" + "Report" + ".html";

	private ExtentManager() {
		throw new IllegalStateException("Utility class");
	}

	public static synchronized ExtentReports getReporter() {
		if (extent == null) {
			extent = new ExtentReports(REPORT_FILE_PATH, true);

			extent.addSystemInfo("Application", "Extent");
			extent.addSystemInfo("User Name", System.getProperty("user.name"));
			extent.addSystemInfo("Environment", AutomationVariables.ENVIRONMENT);
			extent.addSystemInfo("Machine", AutomationVariables.MACHINE.toUpperCase());
			extent.loadConfig(ClassLoader.getSystemResource("extent-config.xml"));
		}

		return extent;
	}
}