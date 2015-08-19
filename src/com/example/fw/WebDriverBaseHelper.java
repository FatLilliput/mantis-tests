package com.example.fw;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

public abstract class WebDriverBaseHelper extends BaseHelper {
	
	public boolean acceptNextAlert = true;
	public WebDriver driver;	
	
	public WebDriverBaseHelper (ApplicationManager manager) {
		super(manager);
		this.driver = manager.getDriver();
	}
	
	public void fillElement(String name, String value) {
		if (value != null) {
			driver.findElement(By.name(name)).clear();
			driver.findElement(By.name(name)).sendKeys(value);
		}
	}
	
	public void clickButton(String text) {
		driver.findElement(By.xpath("//input[@value='" + text + "']")).click();
	}
	
	public void clickLinkText(String text) {
		driver.findElement(By.linkText(text)).click();
	}
	
	public void openPage(String link) {
		openAddressLink(manager.baseUrl + link);
	}
	
	public void openAddressLink(String link) {
		driver.get(link);
	}

	public boolean isElementPresent(By by) {
	    try {
	    	driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	    	driver.findElement(by);
	    	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	    	return true;
	    } catch (NoSuchElementException e) {
	    	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	    	return false;
	    }
	}

	public boolean isAlertPresent() {
	    try {
	    	driver.switchTo().alert();
	    	return true;
	    } catch (NoAlertPresentException e) {
	    	return false;
	    }
	}

	public String closeAlertAndGetItsText() {
		try {
	    	Alert alert = driver.switchTo().alert();
	    	String alertText = alert.getText();
	    	if (acceptNextAlert) {
	    		alert.accept();
	    	} else {
	    		alert.dismiss();
	    	}
	    	return alertText;
	    } finally {
	    	acceptNextAlert = true;
	    }
	}

	public void waitMe(Long time) {
		driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
	}
	
	public boolean checkPage(String string) {
		return driver.getCurrentUrl().equals(manager.baseUrl + string);
	}

}
