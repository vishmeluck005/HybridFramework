package com.qa.Opencart.Pages;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.Opencart.Constants.AppConstants;
import com.qa.Opencart.Utils.ElementUtil;

public class ProductInfoPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	private By productHeader = By.tagName("h1");
	private By productImages = By.cssSelector("ul.thumbnails  img");
	private By productMetaData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[position()=1]//li");
	private By productPriceDate = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[position()=2]//li");
	private By quantity = By.id("input-quantity");
	private By addToCartBtn = By.id("button-cart");
	private By cartSuccMsg = By.xpath("//div[@class='alert alert-success alert-dismissible']");

	private Map<String,String> productInfoMap;
	
	public ProductInfoPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	public String getProductHeaderValue() {
		String productHeaderValue=eleUtil.doElementGetText(productHeader);
		System.out.println("The product Header value: "+productHeaderValue);
		return productHeaderValue;
	}
	
	public int getProductImagecount() {
		int imagescount=eleUtil.waitForElementsVisible(productImages, AppConstants.DEFAULT_MEDIUM_TIMEOUT).size();
		System.out.println("Product Images count: "+imagescount);
		return imagescount;
	}
	
	public void enterQuantity(int qty) {
		System.out.println("Product Quantity: "+qty);
		eleUtil.doSendKeys(quantity, String.valueOf(qty));
	}
	
	public String addProductToCart() {
		eleUtil.doClick(addToCartBtn);
		String successMsg=eleUtil.waitForElementVisible(cartSuccMsg, AppConstants.DEFAULT_MEDIUM_TIMEOUT).getText();
		System.out.println("cart Success Msg: "+successMsg);
		String msg=successMsg.substring(0, successMsg.length()-1).replace("\n", "").toString();	
		System.out.println(msg);
		return msg;
	}
	
	public Map<String, String> getProductInfo() {
		//productInfoMap = new HashMap<String,String>(); -- Does not maintain insertion order
		productInfoMap = new LinkedHashMap<String,String>(); //Maintains order of insertion
		//productInfoMap = new TreeMap<String,String>(); -- Stores in sorted order (A-Z)->(a-z)->(0-9)
		
		//header
		productInfoMap.put("productname", getProductHeaderValue());	
		getProductMetaData();
		getProductPricingData();
		System.out.println(productInfoMap);
		return productInfoMap;
	}
	
	//metadata
	private void getProductMetaData() {
		List<WebElement> metaList= eleUtil.getElements(productMetaData);
		for(WebElement e:metaList) {
			String meta=e.getText();
			String[]metaInfo=meta.split(":");
			String Key=metaInfo[0].trim();
			String value=metaInfo[1].trim();
			productInfoMap.put(Key, value);
		}
	}
	
	//priceData
	private void getProductPricingData() {
		List<WebElement> priceList= eleUtil.getElements(productPriceDate);
		String price=priceList.get(0).getText();
		String extax=priceList.get(1).getText();
		String extaxVal =extax.split(":")[1].trim();
		
		productInfoMap.put("productPrice", price);
		productInfoMap.put("exTax", extaxVal);
	}
}
