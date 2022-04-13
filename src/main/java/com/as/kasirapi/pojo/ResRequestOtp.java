package com.as.kasirapi.pojo;

import lombok.Data;

@Data
public class ResRequestOtp {
	private String email;
	private String otp_code;

	public ResRequestOtp(String email, String otp_code) {
		this.email    = email;
		this.otp_code = otp_code;
	}

}
