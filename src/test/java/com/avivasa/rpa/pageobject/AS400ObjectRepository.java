package com.avivasa.rpa.pageobject;

import org.openqa.selenium.By;

import com.avivasa.rpa.util.DataFinder;

public class AS400ObjectRepository {

	public enum AS400CommonPage {

		btnEnter,txtSecim,btnMore,txtPlanSecim,errorMessage;

		public By getBy() {
			return DataFinder.getAs400By(this.getDeclaringClass().getSimpleName() + "." + this.name());
		}
	}
	
	public enum AS400LoginPage {

		txtUser,txtPassword,btnExitSign;

		public By getBy() {
			return DataFinder.getAs400By(this.getDeclaringClass().getSimpleName() + "." + this.name());
		}
		
		public enum As400BireyselSozlesmeGirisi {

			txtBankayaYolla;
			
			public By getBy() {
				return DataFinder.getAs400By(this.getDeclaringClass().getSimpleName() + "." + this.name());
			}
	}
}
}