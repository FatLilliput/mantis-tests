package com.example.fw;

import org.openqa.selenium.By;

public class ErrorMessagesHelper extends WebDriverBaseHelper{
		
	private static String back = "Please use the \"Back\" button in your web browser to return to the previous page. There you can correct whatever problems were identified in this error or select another action. You can also click an option from the menu bar to go directly to a new section.";

	public ErrorMessagesHelper(ApplicationManager manager) {
		super(manager);
	}
	
	public boolean isError800 () {
		if (!isElementPresent(By.xpath("//*[text()='APPLICATION ERROR #800']")))
			return false;
		if (!isElementPresent(By.xpath("//*[text()='That username is already being used. Please go back and select another one.']")))
			return false;
		if (!isElementPresent(By.xpath("//*[text()='" + back + "']")))
			return false;
		return true;
	}
	
	public boolean isError805 () {
		if (!isElementPresent(By.xpath("//*[text()='APPLICATION ERROR #805']")))
			return false;
		if (!isElementPresent(By.xpath("//*[text()='The username is invalid. Usernames may only contain Latin letters, numbers, spaces, hyphens, dots, plus signs and underscores.']")))
			return false;
		if (!isElementPresent(By.xpath("//*[text()='" + back + "']")))
			return false;
		return true;
	}
	
	public boolean isError811 (int id) {
		if (!isElementPresent(By.xpath("//*[text()='APPLICATION ERROR #811']")))
			return false;
		if (!isElementPresent(By.xpath("//*[text()='User with id \"" + id + "\" not found.']"))){
			return false;}
		if (!isElementPresent(By.xpath("//*[text()='" + back + "']")))
			return false;
		return true;
	}
	
	public boolean isError1901 () {
		if (!isElementPresent(By.xpath("//*[text()='APPLICATION ERROR #1901']")))
			return false;
		if (!isElementPresent(By.xpath("//*[text()='The confirmation URL is invalid or has already been used. Please signup again.']")))
			return false;
		if (!isElementPresent(By.xpath("//*[text()='" + back + "']")))
			return false;
		return true;
	}
	
	public boolean isUnknownUserError () {
		if (!isElementPresent(By.xpath("//*[text()='Your account may be disabled or blocked or the username/password you entered is incorrect.']")))
			return false;
		return true;
	}
	
	public boolean isSuccsessSignUp () {
		if (!isElementPresent(By.xpath("//*[text()='Account registration processed.']")))
			return false;
		return true;
	}
	
}
