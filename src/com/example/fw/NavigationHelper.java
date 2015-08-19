package com.example.fw;

public class NavigationHelper extends WebDriverBaseHelper {

	public NavigationHelper(ApplicationManager manager) {
		super(manager);
	}

	public void openMainPage() {
		openPage("");
	}

	public void openSignUpPage() {
		openPage("signup_page.php");
	}
	
	public void clickSignUpPage() {
		clickLinkText("Signup for a new account");
	}

	public void clickProceed() {
		clickLinkText("Proceed");
	}
	
	public void activateUser(String activateLink) {
		openAddressLink(activateLink);
	}

	public void openLogOutPage() {
		openPage("logout_page.php");
		
	}

}