package com.qa.Opencart.Pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.Opencart.Constants.AppConstants;
import com.qa.Opencart.Utils.ElementUtil;

public class AccountsPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	private By logoutLink = By.linkText("Logout");
	private By searchBox = By.name("search");
	private By accHeaders = By.cssSelector("div#content h2");
	private By searchIcon = By.cssSelector("#search button");

	public AccountsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	public String getAccPageTitle() {
		String title=eleUtil.waitForTitleIsAndFetch(AppConstants.DEFAULT_SHORT_TIMEOUT, AppConstants.ACCOUNT_PAGE_TITLE_VALUE);
		System.out.println("Accounts Page Title: "+title);
		return title;
	}
	
	public String getAccPageUrl() {
		String url=eleUtil.waitForURLContainsAndFetch(AppConstants.DEFAULT_SHORT_TIMEOUT, AppConstants.ACCOUNT_PAGE_URL_FRACTION_VALUE);
		System.out.println("Accounts Page Url: "+url);
		return url;
	}
	
	public boolean isLogoutLinkExist() {
		return eleUtil.waitForElementVisible(logoutLink, AppConstants.DEFAULT_SHORT_TIMEOUT).isDisplayed();
	}
	
	public boolean isSearchBoxExist() {
		return eleUtil.waitForElementVisible(searchBox, AppConstants.DEFAULT_SHORT_TIMEOUT).isDisplayed();
	}
	
	public List<String> getAccountsPageHeaderList() {
		List<WebElement> accHeadersList = eleUtil.waitForElementsVisible(accHeaders, AppConstants.DEFAULT_SHORT_TIMEOUT);
		List<String> accHeadersValueList = new ArrayList<String>();
		
		for(WebElement e:accHeadersList) {
			String text=e.getText();
			accHeadersValueList.add(text);
		}
		return accHeadersValueList;
	}
	
	public SearchPage performSearch(String searchKey) {
		if(isSearchBoxExist()) {
			eleUtil.doSendKeys(searchBox, searchKey);
			eleUtil.doClick(searchIcon);
			return new SearchPage(driver);
		}else {
			System.out.println("Search field is not present on the page...");
			return null;
		}
	}

}
