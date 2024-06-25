package com.qa.Opencart.Listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry implements IRetryAnalyzer{
	private int count =0;
	private static int maxTry = 3;
	
	@Override
	public boolean retry(ITestResult result) {
		if(!result.isSuccess()) { // Check if test not succeed
			if(count<maxTry) { // Check if maxtry count is reached
				count++; // Increase the maxTry count by 1
				result.setStatus(result.FAILURE); // Mark test as failed
				return true; // Tells TestNG to re-run the test
			}else {
				result.setStatus(result.FAILURE); // If maxCount reached,test marked as failed
			}
		}else {
			result.setStatus(result.SUCCESS); // If test passes, TestNG marks it as passed
		}
		return false;
	}

}
