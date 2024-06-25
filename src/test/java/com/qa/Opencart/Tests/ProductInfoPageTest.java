package com.qa.Opencart.Tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.Opencart.BaseTest.BaseTest;

public class ProductInfoPageTest extends BaseTest{
	
	@BeforeClass
	public void accPageSetup() {
		accountsPage=loginPage.doLogin(prop.getProperty("username"),prop.getProperty("password"));
	}
	
	@DataProvider
	public Object[][] getProductImagesTestData() {
		return new Object[][] {
			{"MacBook","MacBook Pro",4},
			{"iMac","iMac",3},
			{"Apple","Apple Cinema 30\"",6},
			{"Samsung","Samsung SyncMaster 941BW",1},
		};
	}
	
	@Test(dataProvider="getProductImagesTestData")
	public void productImageCountTest(String searchKey, String productName, int imagesCount) {
		searchPage=accountsPage.performSearch(searchKey);
		productInfoPage=searchPage.selectProduct(productName);
		int actImagesCount=productInfoPage.getProductImagecount();
		Assert.assertEquals(actImagesCount, imagesCount);
	}
	
	@Test
	public void productInfoTest() {
		searchPage=accountsPage.performSearch("MacBook");
		productInfoPage=searchPage.selectProduct("MacBook Pro");
		Map<String, String> actProductInfoMap = productInfoPage.getProductInfo();
		softAssert.assertEquals(actProductInfoMap.get("Brand"), "Apple");
		softAssert.assertEquals(actProductInfoMap.get("Product Code"), "Product 18");
		softAssert.assertEquals(actProductInfoMap.get("productname"), "MacBook Pro");
		softAssert.assertEquals(actProductInfoMap.get("productPrice"), "$2,000.00");
		
		softAssert.assertAll();
	}
	
	@Test
	public void addToCartTest() {
		searchPage=accountsPage.performSearch("MacBook");
		productInfoPage=searchPage.selectProduct("MacBook Pro");
		productInfoPage.enterQuantity(2);
		String actSuccessMsg=productInfoPage.addProductToCart();
		softAssert.assertTrue(actSuccessMsg.contains("Success"));
		softAssert.assertTrue(actSuccessMsg.contains("MacBook Pro"));
		
		softAssert.assertEquals(actSuccessMsg, "Success: You have added MacBook Pro to your shopping cart!");
		softAssert.assertAll();
	}
}
