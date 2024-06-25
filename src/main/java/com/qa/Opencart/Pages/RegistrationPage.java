package com.qa.Opencart.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.Opencart.Constants.AppConstants;
import com.qa.Opencart.Utils.ElementUtil;

public class RegistrationPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	private By firstName = By.id("input-firstname");
	private By lastName = By.id("input-lastname");
	private By email = By.id("input-email");
	private By telephone = By.id("input-telephone");
	private By password = By.id("input-password");
	private By confirmPassword = By.id("input-confirm");
	
	private By subscribeYes = By.xpath("//label[normalize-space()='Yes']//input[@type='radio']");
	private By subscribeNo = By.xpath("//label[normalize-space()='No']//input[@type='radio']");
	
	private By agreeCheckbox = By.name("agree");
	private By continueBtn = By.xpath("//input[@type='submit' and @value='Continue']");
	
	private By logoutLink = By.linkText("Logout");
	private By registerLink = By.linkText("Register");
	private By regSuccessMsg = By.cssSelector("div#content h1");
	
	public RegistrationPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	public boolean registerUser(String firstName, String lastName, String email, String telephone,
								String password, String subscribe) {
		
		eleUtil.waitForElementVisible(this.firstName, AppConstants.DEFAULT_MEDIUM_TIMEOUT).sendKeys(firstName);
		eleUtil.doSendKeys(this.lastName, lastName);
		eleUtil.doSendKeys(this.email, email);
		eleUtil.doSendKeys(this.telephone, telephone);
		eleUtil.doSendKeys(this.password, password);
		eleUtil.doSendKeys(this.confirmPassword, password);
		
		if(subscribe.equalsIgnoreCase("yes")) {
			eleUtil.doClick(subscribeYes);
		}else {
			eleUtil.doClick(subscribeNo);
		}
		
		eleUtil.doClick(agreeCheckbox);
		eleUtil.doClick(continueBtn);
		
		String regSuccMsg=eleUtil.waitForElementVisible(regSuccessMsg, AppConstants.DEFAULT_MEDIUM_TIMEOUT).getText();
		System.out.println("User success Msg: "+regSuccMsg);
		if(regSuccMsg.contains(AppConstants.REGISTRATION_SUCCESS_MSG)) {
			eleUtil.doClick(logoutLink);
			eleUtil.doClick(registerLink);
			return true;
		}
		return false;
	}
}
