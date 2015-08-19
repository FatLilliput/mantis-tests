package com.example.tests;

import static org.testng.Assert.*;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.example.tests.User;

public class RegistrationTests extends TestBase {

	private User testUser = new User()
		.setLogin("testUser1").setPassword("testPassword1").setEmail("testUser1@localhost");
	private User dublicatedUser = new User()
		.setLogin("DublicatedLogin").setEmail("Dublicated@localhost");
	private User dublicatedMailUser = new User()
		.setLogin("AnotherLogin").setEmail("Dublicated@localhost");
	private User nonActivatedUser = new User()
		.setLogin("nonActivatedUser").setEmail("nonActivatedUser@localhost");
	
	@BeforeClass
	public void createMailAccount () {
		if (!mail_account.doesUserExist(testUser.getLogin())) 
			mail_account.createUser(testUser.getLogin(), testUser.getPassword());
		if (!mail_account.doesUserExist(adminMail.getLogin())) 
			mail_account.createUser(adminMail.getLogin(), adminMail.getPassword());
	} 
	
	@BeforeClass
	public void createTestUsersInDataBase() {
		inDataBase.insertUser(dublicatedUser);
	}
	
	@AfterClass
	public void deleteTestUsersFromDataBase() {
		inDataBase.deleteUser(testUser.getLogin());
		inDataBase.deleteUser(dublicatedUser.getLogin());
		inDataBase.deleteUser(dublicatedMailUser.getLogin());
		inDataBase.deleteUser(nonActivatedUser.getLogin());
	}
	
	@AfterClass
	public void deleteMailAccount () {
		if (mail_account.doesUserExist(testUser.getLogin()))
			mail_account.deleteUser(testUser.getLogin());
		if (mail_account.doesUserExist(adminMail.getLogin())) 
			mail_account.deleteUser(adminMail.getLogin());
	}
	
	@AfterMethod
	public void clearAdminMail() {
		email.deleteMessage(adminMail.getLogin(), adminMail.getPassword());
	}
	
	@Test
	public void testValidRegistration() {
		inDataBase.deleteUser(testUser.getLogin());
		
		//signUp user
		account.signUp(testUser);
		assertTrue(message.isSuccsessSignUp());
		
		//assert admin mails
		String regData = email.getNewMail(adminMail.getLogin(), adminMail.getPassword()).getAccountInfo();
		assertTrue(regData.equals(testUser.getLogin() + " " + testUser.getEmail()));
//		assertTrue(regData[0].equals(testUser.getLogin()));
//		assertTrue(regData[1].equals(testUser.getEmail()));
		
		//activate user
		account.activate(testUser);
		assertTrue(account.isLogggedIn(testUser.getLogin()));
		account.registrate(testUser);
		
		//assert login
		account.logIn(testUser);
		assertTrue(account.isLogggedIn(testUser.getLogin()));
	}

	@Test
	public void testInvalidRegistration() {
		//signUp user with invalid parameters
		account.signUp(new User().setLogin("!123").setEmail("!123@454~!@"));
		assertTrue(message.isError805());
	}
	
	@Test
	public void testEmptyRegistration() {
		//signUp user with empty parameters
		account.signUp(new User().setLogin("").setEmail("") );
		assertTrue(message.isError805());
	}
	
	@Test
	public void testDublicatedLoginRegistration() {
		//signUp user with login existing in system
		account.signUp(dublicatedUser);		
		
		assertTrue(message.isError800());
	}
	
	@Test
	public void testDublicatedEmailRegistration() {		
		//signUp user with email existing in system
		account.signUp(dublicatedMailUser);
		
		assertFalse(app.getErrorMessagesHelper().isError800());
		assertTrue(message.isSuccsessSignUp());
	}
	
	@Test
	public void testNoActivationRegistration() {
		//signUp user
		account.signUp(nonActivatedUser);
				
		//try to login
		account.logIn(nonActivatedUser);
		assertFalse(account.isLogggedIn(nonActivatedUser.getLogin()));
		assertTrue(message.isUnknownUserError());
	}
	
	@Test
	public void testNoSignUpLogin() {
		//try to login without registration
		account.logIn(new User().setLogin("neverUser").setPassword("pass"));
		assertFalse(account.isLogggedIn(testUser.getLogin()));
		assertTrue(message.isUnknownUserError());
	}
	
	@Test
	public void testActivateNotExistingUser() {
		inDataBase.deleteUser(testUser.getLogin());
		
		//signUp user new user
		account.signUp(testUser);
		
		//delete it from database (imitating of removing user by administrator)
		int id = inDataBase.deleteUser(testUser.getLogin());
				
		//try to activate user
		account.activate(testUser);
		assertFalse(account.isLogggedIn(testUser.getLogin()));
		assertTrue(message.isError811(id));
	}
	
	@Test
	public void testOutOfSessionRegistration() {
		inDataBase.deleteUser(testUser.getLogin());
		
		//signUp user
		account.signUp(testUser);
		
		//expiring activation
		inDataBase.updateTimeStamps(testUser, "1438387200");
		
		//try to activate user
		account.activate(testUser);
		assertFalse(account.isLogggedIn(testUser.getLogin()));
		assertTrue(message.isError1901());
	}
	
	@Test
	public void testDoubleActivationRegistration() {
		inDataBase.deleteUser(testUser.getLogin());
		
		//signUp and activate user
		account.signUp(testUser);
		String activationLink = account.activate(testUser);
		account.registrate(testUser);
		
		//try to activate user once more
		navigateTo.openAddressLink(activationLink);;
		assertFalse(account.isLogggedIn(testUser.getLogin()));
		assertTrue(message.isError1901());
	}	

}
