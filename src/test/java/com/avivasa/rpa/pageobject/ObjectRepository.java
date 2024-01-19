package com.avivasa.rpa.pageobject;

import org.openqa.selenium.By;

import com.avivasa.rpa.utiliy.log;

public class ObjectRepository {

	public static class AS400ObjectRepostiory {

		// AS400LoginPage
		public static final By txtUser = By.id("inpR6C53L10.id");
		public static final By txtPassword = By.id("inpR7C53L10.id");
		public static final By  btnExitSign=By.name("keyPF3");
		// AS400LoginPage.btnExitSign=name:keyPF3

		// AS400CommonPage
		public static final By btnEnter = By.name("keyEnter");
		public static final By txtSecim = By.id("inpR23C43L3.id");
		public static final By txtTCNo = By.id("inpR5C23L12.id");
		public static final By txtMusteriNo = By.id("inpR3C23L10.id");

		// AS400 Müsteriye Ait Policeler Page
		public static final By txtX = By.id("inpR6C3L1.id");

		// AS400 Secilen Police Page
		public static final By txtSecilenMusteriPoliceX = By.id("inpR6C2L1.id");

		// AS400 Police Bilgileri Görüntüleme Page
		public static final By txtPolBilgGor = By.id("inpR7C41L1.id");
		
		public static final By txtAraVerme = By.xpath("//*[@id='screenarea']/tbody/tr[2]/td[7]/a");
		public static final By yururlukGun = By.xpath("//*[@id='screenarea']/tbody/tr[6]/td[5]/a");
		public static final By yururlukAy = By.xpath("//*[@id='screenarea']/tbody/tr[6]/td[7]/a");
		public static final By yururlukYil = By.xpath("//*[@id='screenarea']/tbody/tr[6]/td[9]/a");

		public static final By txtTCNO = By.xpath("//*[@id='screenarea']/tbody/tr[16]/td[21]/a");
		public static final By txtMSISDN = By.xpath("//*[@id='screenarea']/tbody/tr[15]/td[14]/a");
		public static final By anneAdi = By.xpath("//*[@id=\"screenarea\"]/tbody/tr[10]/td[11]/a");
		public static final By policeBulunamadi= By.xpath("//*[@id='screenarea']/tbody/tr[27]/td[3]/a");
	

	}

}
