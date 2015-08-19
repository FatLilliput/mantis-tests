/**
 * 
 */
package com.example.fw;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Msg {

	private String text;

	public Msg(String text) {
		this.text = text;			
	}

	public String getConfirmationLink() {
		return findText("http\\S*");
	}
	
	public String getAccountInfo() {
		String login = findText("Username: \\S*").substring("Username: ".length());
		String email = findText("E-mail: \\S*").substring("E-mail: ".length());
		return login + " " + email;
	}

	private String findText(String searchParam) {
		Pattern regex = Pattern.compile(searchParam);
		Matcher matcher = regex.matcher(text);
		if (matcher.find()) {
			return matcher.group();
		} else {
			return "";
		}
	}
	
}