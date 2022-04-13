package com.as.kasirapi.repository;

import com.as.kasirapi.model.Mail;

public interface MailRepository {
	public void sendEmail(Mail mail);
}
