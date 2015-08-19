/*
 * This class contains methods needed for correctly auto-tests work 
 * and common methods for all application's modules
 */
package com.example.tests;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.example.fw.AccountHelper;
import com.example.fw.ApplicationManager;
import com.example.fw.ErrorMessagesHelper;
import com.example.fw.JDBCHelper;
import com.example.fw.JamesHelper;
import com.example.fw.MailHelper;
import com.example.fw.NavigationHelper;

public class TestBase {
	public static ApplicationManager app;
	public static JDBCHelper inDataBase;
	public static ErrorMessagesHelper message;
	public static NavigationHelper navigateTo;
	public static AccountHelper account;
	public static JamesHelper mail_account;
	public static MailHelper email;
	
	public static User administrator;
	public static User adminMail;
	
	@BeforeClass
	public void setUp() throws Exception {
		String configFile = System.getProperty("configFile", "application.properties");
		Properties properties = new Properties();
		properties.load(new FileReader(new File (configFile)));
		app = new ApplicationManager(properties);
		
		inDataBase = app.getJDBCHelper();
		message = app.getErrorMessagesHelper();
		account = app.getAccountHelper();
		navigateTo = app.getNavigationHelper();
		mail_account = app.getJamesHelper();
		email = app.getMailHelper();

		adminMail = new User()
			.setLogin(properties.getProperty("mailserver.adminlogin"))
			.setPassword(properties.getProperty("mailserver.adminpassword"))
			.setEmail(properties.getProperty("admin.email"));
	  }
	
	@AfterClass
	public void tearDown() throws Exception {
		inDataBase.dbClose();
		app.stop();	    
	  }

}
