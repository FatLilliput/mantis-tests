package com.example.fw;

import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;

public class MailHelper extends BaseHelper {

	private String mailserver;

	public MailHelper(ApplicationManager manager) {
		super(manager);
		this.mailserver = manager.getProperty("mailserver.host");
	}

	public Msg getNewMail(String user, String password) {
		Properties props = System.getProperties();
		Session session = Session.getDefaultInstance(props);
		
		Store store;
		try {
			store = session.getStore("pop3");
			store.connect(mailserver, user, password);
			Folder folder = store.getDefaultFolder().getFolder("INBOX");
			if (!waitForMessage(folder, 30000, 500))
				return null;
			Message message = folder.getMessage(1);

			message.setFlag(Flags.Flag.DELETED, true);
			Msg msg = new Msg((String) message.getContent());
			folder.close(true);
			store.close();

			return msg;
		
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void deleteMessage(String user, String password)  {
		try {
			Store store = Session.getDefaultInstance(System.getProperties()).getStore("pop3");
			store.connect(mailserver, user, password);
			Folder folder = store.getDefaultFolder().getFolder("INBOX");
			waitForMessage(folder, 3000, 200);
			int i = 0;
			while (i < folder.getMessageCount()) {
				i++;
				folder.getMessage(i).setFlag(Flags.Flag.DELETED, true);
			}
			if (folder.isOpen())
				folder.close(true);
			store.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean waitForMessage(Folder folder, int duration, int step) throws MessagingException {
		int time = 0;
		while (time < duration) {
			time = time + step;
			try {
				Thread.sleep(step);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			folder.open(Folder.READ_WRITE);
			if (folder.getMessageCount() != 0) {
				return true;				
			} else {
				folder.close(true);
			}
		}
		return false;
	}
	
}
