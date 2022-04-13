package com.as.kasirapi.pojo;

import lombok.Data;

@Data
public class ReqBodyCheckOTP {

	private String email;
	private String otp_code;

	public ReqBodyCheckOTP(String email, String otp_code) {
		this.email    = email;
		this.otp_code = otp_code;
	}
}
