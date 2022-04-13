package com.as.kasirapi.pojo;

import lombok.Data;

@Data
public class MemberPojo {
	private Integer no;
	private Long    id;
	private String  username;
	private String  password;
	private String  email;
	private String  phone;
}
