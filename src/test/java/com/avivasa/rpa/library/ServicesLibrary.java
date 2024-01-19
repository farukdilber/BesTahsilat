package com.avivasa.rpa.library;

import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.jfree.util.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;

import com.avivasa.rpa.actions.AS400Actions;
import com.avivasa.rpa.base.AbstractPage;
import com.avivasa.rpa.pageobject.AS400ObjectRepository.AS400CommonPage;
import com.avivasa.rpa.pageobject.ObjectRepository.AS400ObjectRepostiory;
import com.avivasa.rpa.utiliy.ExtentTestManager;
import com.avivasa.rpa.utiliy.log;
import com.relevantcodes.extentreports.LogStatus;

import java.net.MalformedURLException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;

import com.avivasa.rpa.base.AbstractPage;
import com.avivasa.rpa.data.GetData;
import com.avivasa.rpa.library.AS400Library;
import com.avivasa.rpa.utiliy.UtilityMethods;
import com.avivasa.rpa.utiliy.log;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import org.testng.Assert;
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


public abstract class ServicesLibrary {
			
	public <T> ServicesLibrary() throws MalformedURLException {

		super();
	}
			public OkHttpClient.Builder client = client();
			public Response response = null;
			public String sessionId = null;
			public String token = null;
			public String requestBody = null;
			public String url = null;
			public String refererUrl = null;
			public String xApiKey = null;

			private OkHttpClient.Builder client() {

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

			abstract public String getSessionId(Response res);

			protected Request getServiceWithSessionId(String url) {

				return new Request.Builder().url(url)
						.addHeader("content-type", "application/json")
						.addHeader("cookie", getSessionId(response))
						.build();
			}

			protected Request getServiceOdemeDegisikligi(String url) {

				return new Request.Builder().url(url)
						.addHeader("x-auth-config", "internal")
						.addHeader("Authorization", "Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJfNDZBcGhPTVc5LW1GWnhOaVpFSEZZTUlvZEVaMXVxb0JnS0hRQVZzNVhjIn0.eyJqdGkiOiIwMjliMGFiYy0wMjU3LTRjZmEtOTkxYS02ZDNkMTk4ZTU3MjUiLCJleHAiOjE4NzY0NTk4NDcsIm5iZiI6MCwiaWF0IjoxNTYxMDk5ODQ3LCJpc3MiOiJodHRwczovL2F1dGhtYWZ0c3QuYXZpdmFzYS5jb20udHIvYXV0aC9yZWFsbXMvaW50ZXJuYWwiLCJhdWQiOiJiYXNpYyIsInN1YiI6IjcwMzk1ZDk2LWUyOGQtNDM5OS04NmQzLTRlMWIxZDNhYmE4NiIsInR5cCI6IkJlYXJlciIsImF6cCI6ImJhc2ljIiwiYXV0aF90aW1lIjowLCJzZXNzaW9uX3N0YXRlIjoiYzRlNDYxNDYtYmIyOS00NWQwLWI3YjAtYTc4MjAxYWEzZTcyIiwiYWNyIjoiMSIsImNsaWVudF9zZXNzaW9uIjoiMWZjYjgwZjQtNmNhOC00ZGE1LWE0OTctZmZkYzI1NzQyYTQyIiwiYWxsb3dlZC1vcmlnaW5zIjpbXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIk1vZHVsZVNhbGVzIiwiTW9kdWxlQ29udGVudCIsIk1vZHVsZVpla2FLdXB1IiwiTW9kdWxlQWRtaW4iLCJNb2R1bGVDb3JwIiwidW1hX2F1dGhvcml6YXRpb24iLCJNb2R1bGVPcGVyYXRpb25hbCJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJ2aWV3LXByb2ZpbGUiXX19LCJuYW1lIjoiIiwicHJlZmVycmVkX3VzZXJuYW1lIjoic3dhZ2dlciJ9.mthwklCE79OwIwKWPp16uGG8BUWMZiBY3nsCWG37P_mKzb75AMCrRXiGTUIAnmMKkwtbbCFaQdMhxyfCU0_Kyalgc7CYXGgidfPKPigvRL84sFYSq9Y7QA8MQTuqBc68gudxIN4mbRlprFaSzRhLJH0PJe8TrsWt3-6k7XdGhrPUQMlBW_wD_U0jz6FsdUJUvUVMr0yIZGXex9Mr2rw-nRfvhXfbiTv1XsZdmlpt5Yt4CqO2fCRfjv4bmMMkrfayjpqzzWle_eRk7dNRmqBbPFHlBNmhBDbAtYJPK0PMWB08Yj5l6uKPB7k1PeeecySvLDCaGJT8L6r2GwBsRSvjOg")
						.build();
			}
			

			public String getServiceWithSessionIDOdemeDegisikligi(String url, String service, String serviceName) {

				String body = "";
				try {
					Request request = getServiceOdemeDegisikligi(url + service);
					response = client.build()
							.newCall(request)
							.execute();
					body = response.body()
							.string();
				
				} catch (Exception e) {
				
					Assert.assertTrue(false, e.toString());
				}
				return body;
			}		

			public String getServiceWithSessionId(String url, String service, String serviceName) {

				String body = "";
				try {
					Request request = getServiceWithSessionId(url + service);
					response = client.build()
							.newCall(request)
							.execute();
					body = response.body()
							.string();
					

				} catch (Exception e) {
					
					Assert.assertTrue(false, e.toString());
				}
				return body;
			}

			protected Request getService(String url) {

				return new Request.Builder().url(url)
						.get()
						.addHeader("content-type", "application/json")
						.addHeader("cookie", getSessionId(response))
						.build();
			}

			protected String getService(String url, String service, String serviceName) {

				String body = "";
				try {
					Request request = getService(url + service);
					response = client.build()
							.newCall(request)
							.execute();
					body = response.body()
							.string();
					
				} catch (Exception e) {
					Assert.assertTrue(false, e.toString());
				}
				return body;
			}
		
		}

	


