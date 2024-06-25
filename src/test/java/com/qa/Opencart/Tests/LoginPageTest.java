package com.qa.Opencart.Tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.Opencart.BaseTest.BaseTest;
import com.qa.Opencart.Constants.AppConstants;

public class LoginPageTest extends BaseTest{

	@Test(priority=1)
	public void loginPageTitleTest() {
		String actualTitle=loginPage.getLoginPageTitle();
		Assert.assertEquals(actualTitle, AppConstants.LOGIN_PAGE_TITLE_VALUE);
	}
	
	@Test(priority=2)
	public void loginPageUrlTest() {
		String actualUrl=loginPage.getLoginPageUrl();
		Assert.assertTrue(actualUrl.contains(AppConstants.LOGIN_PAGE_URL_FRACTION_VALUE));
	}
	
	@Test(priority=3)
	public void forgotPwdLinkExistTest() {
		Assert.assertTrue(loginPage.isForgotPwdLinkExist());
	}
	
	@Test(priority=4)
	public void loginTest() {
		accountsPage=loginPage.doLogin(prop.getProperty("username"),prop.getProperty("password") );
		Assert.assertTrue(accountsPage.isLogoutLinkExist());
	}
}
