package com.qa.Opencart.Tests;

import java.util.Random;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.Opencart.BaseTest.BaseTest;
import com.qa.Opencart.Constants.AppConstants;
import com.qa.Opencart.Utils.ExcelUtil;

public class RegistrationPageTest extends BaseTest{
	
	@BeforeClass
	public void regPageSetup() {
		registrationPage=loginPage.navigateToRegisterPage();
	}
	
	public String getRandomEmail() {
//		Random random = new Random();
//		String email="automation"+random.nextInt(1000)+"@gmail.com";
		
		String email = "automation"+System.currentTimeMillis()+"@gmail.com";
		return email;
	}
	
	@DataProvider
	public Object[][] getRegTestData() {
		Object [][]regdata=ExcelUtil.getTestData(AppConstants.REGISTER_SHEET_NAME);
		return regdata;
	}

	@Test(dataProvider="getRegTestData")
	public void userRegTest(String firstname, String lastname, String telephone,
			String password,String subscribe) {
		registrationPage.registerUser(firstname, lastname, getRandomEmail(), telephone, password, subscribe);
	}
}
