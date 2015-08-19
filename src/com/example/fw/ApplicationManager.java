package com.example.fw;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;

public class ApplicationManager {
	
	private WebDriver driver;
	public String baseUrl;
	private NavigationHelper navigationHelper;
 	public WebDriverBaseHelper baseHelper;
	private JDBCHelper jdbcHelper;
	private Properties properties;
	private AccountHelper accountHelper;
	private MailHelper mailHelper;
	private JamesHelper jamesHelper;
	private ErrorMessagesHelper errorMessagesHelper;
	
	public ApplicationManager(Properties properties) {
		this.properties = properties;
	}
	
	public String getProperty(String key) {
		return properties.getProperty(key);
	}

	public WebDriver getDriver() {
		if (driver == null) {
			String browser = properties.getProperty("browser");
			switch (browser.toLowerCase()) {
				case "firefox" :
					driver = new FirefoxDriver();
					break;
				case "ie" :
					driver = new InternetExplorerDriver();
					break;
				case "chrome" :
					driver = new ChromeDriver();
					break;
				case "opera" :
					driver = new OperaDriver();
					break;
				default :
					throw new Error("Incorrect browser. Got: " + browser + ". Expected: firefox, ie, chrome, opera");
			}
			baseUrl = properties.getProperty("baseUrl");
		    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);  
		    driver.get(baseUrl);
		}
		return driver;
	}

	public NavigationHelper getNavigationHelper () {
		if (navigationHelper == null) {
			navigationHelper = new NavigationHelper(this);
		}
		return navigationHelper;
	}


	public JDBCHelper getJDBCHelper () {
		if (jdbcHelper == null) {
			jdbcHelper = 
				new JDBCHelper(this, getProperty("dbConnection"), getProperty("dbLogin"), getProperty("dbPass"));
		}
		return jdbcHelper;
	}

	public AccountHelper getAccountHelper() {
		if (accountHelper == null) {
			accountHelper = new AccountHelper(this);
		}
		return accountHelper;
	}
	
	public MailHelper getMailHelper() {
		if (mailHelper == null) {
			mailHelper = new MailHelper(this);
		}
		return mailHelper;
	}
	
	public JamesHelper getJamesHelper() {
		if (jamesHelper == null) {
			jamesHelper = new JamesHelper(this);
		}
		return jamesHelper;
	}
	
	public ErrorMessagesHelper getErrorMessagesHelper() {
		if (errorMessagesHelper == null) {
			errorMessagesHelper = new ErrorMessagesHelper(this);
		}
		return errorMessagesHelper;
	}
	
	public void stop() {
		driver.close();
		driver.quit();
	}

}
