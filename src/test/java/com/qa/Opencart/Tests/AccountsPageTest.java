package com.qa.Opencart.Tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.Opencart.BaseTest.BaseTest;
import com.qa.Opencart.Constants.AppConstants;

public class AccountsPageTest extends BaseTest {
	
	@BeforeClass
	public void accPageSetup() {
		accountsPage=loginPage.doLogin(prop.getProperty("username"),prop.getProperty("password"));
	}
	
	@Test
	public void accountsPageTitleTest() {
		String actualTitle=accountsPage.getAccPageTitle();
		Assert.assertEquals(actualTitle, AppConstants.ACCOUNT_PAGE_TITLE_VALUE);
	}
	
	@Test
	public void accountsPageUrlTest() {
		String actUrl=accountsPage.getAccPageUrl();
		Assert.assertTrue(actUrl.contains(AppConstants.ACCOUNT_PAGE_URL_FRACTION_VALUE));
	}
	
	@Test
	public void isLogoutLinkExistTest() {
		Assert.assertTrue(accountsPage.isLogoutLinkExist());
	}
	
	@Test
	public void isSearchBoxExistTest() {
		Assert.assertTrue(accountsPage.isSearchBoxExist());
	}
	
	@Test
	public void accPageHeadersCountTest() {
		List<String> actualAccPageHeadersList=accountsPage.getAccountsPageHeaderList();
		System.out.println("Account Page Headers List: "+actualAccPageHeadersList);
		Assert.assertEquals(actualAccPageHeadersList.size(), AppConstants.ACCOUNTS_PAGE_HEADERS_COUNT);
	}
	
	@Test
	public void accPageHeadersValueTest() {
		List<String> actualAccPageHeadersList=accountsPage.getAccountsPageHeaderList();
		System.out.println("Actual Account Page Headers List: "+actualAccPageHeadersList);
		System.out.println("Expected Account Page Headers List: "+AppConstants.EXPECTED_ACCOUNTS_PAGE_HEADERS_LIST);
		Assert.assertEquals(actualAccPageHeadersList,AppConstants.EXPECTED_ACCOUNTS_PAGE_HEADERS_LIST);
	}
	
	@DataProvider
	public Object[][] getProductData() {
		return new Object[][] {
			{"MacBook"},
			{"iMac"},
			{"Apple"},
			{"Samsung"}
		};
	}
	
	@Test(dataProvider="getProductData")
	public void searchTest(String searchKey) {
		searchPage=accountsPage.performSearch(searchKey);
		Assert.assertTrue(searchPage.getSearchProductCount()>0);
	}
	
	@DataProvider
	public Object[][] getProductTestData() {
		return new Object[][] {
			{"MacBook","MacBook Air"},
			{"MacBook","MacBook Pro"},
			{"iMac","iMac"},
			{"Apple","Apple Cinema 30\""},
			{"Samsung","Samsung SyncMaster 941BW"},
			{"Samsung","Samsung Galaxy Tab 10.1"},
			{"Vishwa","Samsung Galaxy Tab 10.1"}
		};
	}
	
	@Test(dataProvider="getProductTestData")
	public void selectProductTest(String searchKey, String productName) {
		searchPage=accountsPage.performSearch(searchKey);
		if(searchPage.getSearchProductCount()>0) {
			productInfoPage=searchPage.selectProduct(productName);
			Assert.assertEquals(productInfoPage.getProductHeaderValue(),productName);
		}
	}
}
