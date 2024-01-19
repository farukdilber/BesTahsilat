package com.avivasa.rpa.actions;

import java.net.MalformedURLException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;

import com.avivasa.rpa.base.AbstractPage;
import com.avivasa.rpa.data.GetData;
import com.avivasa.rpa.library.AS400Library;
import com.avivasa.rpa.library.ServicesLibrary;
import com.avivasa.rpa.pageobject.ObjectRepository.AS400ObjectRepostiory;
import com.avivasa.rpa.utiliy.EmailSend;
import com.avivasa.rpa.utiliy.UtilityMethods;
import com.avivasa.rpa.utiliy.log;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import org.testng.Assert;
import org.testng.SkipException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import org.testng.Assert;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public abstract class ServicesActions extends ServicesLibrary {
	
	
	public ServicesActions() throws MalformedURLException {

		super();
	}
	
	
	
	public ServicesActions OdemeDegisiklikServices(String policeno) {

		getServiceWithSessionIDOdemeDegisikligi("","","");
			
		return null;

		
		
	}



	
	
	
		
	}






		


	



