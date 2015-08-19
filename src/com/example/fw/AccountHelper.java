package com.example.fw;

import org.openqa.selenium.By;

import com.example.tests.User;

public class AccountHelper extends WebDriverBaseHelper {
	
	public AccountHelper(ApplicationManager manager) {
		super(manager);
	}

	public void signUp(User user) {
		manager.getNavigationHelper().openLogOutPage();
		manager.getNavigationHelper().clickSignUpPage();
		fillElement("username", user.getLogin());
		fillElement("email", user.getEmail());
		clickButton("Signup");
	}

	public void registrate(User user) {
		fillElement("password", user.getPassword());
		fillElement("password_confirm", user.getPassword());
		fillElement("email", user.getEmail());
		fillElement("realname", user.getName());
		clickButton("Update User");
		manager.getNavigationHelper().clickProceed();
	}

	public void logIn(User user) {
		if (!checkPage("login_page.php")) {
			manager.getNavigationHelper().openLogOutPage();
		}
		fillElement("username", user.getLogin());
		fillElement("password", user.getPassword());
		clickButton("Login");
	}
	
	public boolean isLogggedIn(String login) {
		if (!(checkUser().equals(login))) {
			return false;
		}
		return true;
	}

	private String checkUser() {
		if (!isElementPresent(By.xpath("//td[@class='login-info-left']/span[1]")))
			return "";
		return driver.findElement(By.xpath("//td[@class='login-info-left']/span[1]")).getText();
	}

	public String activate(User user) {
		Msg message = manager.getMailHelper().getNewMail(user.getLogin(), user.getPassword());
		String link = message.getConfirmationLink();
		manager.getNavigationHelper().activateUser(link);
		return link;
	}

	public boolean isMessagePresent(String[] blok) {
		for (String message : blok) {
			if (!isElementPresent(By.xpath("//*[text()='" + message + "']"))) {
				return false;
			}
		}
		return true;
	}

}
