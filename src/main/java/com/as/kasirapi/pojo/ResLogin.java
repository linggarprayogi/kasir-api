package com.as.kasirapi.pojo;

import com.as.kasirapi.model.member.Member;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class ResLogin {

	private Member member;
	private String accessToken;
	private String refreshToken;

	public ResLogin(Member member, String accessToken, String refreshToken) {
		this.member       = member;
		this.accessToken  = accessToken;
		this.refreshToken = refreshToken;
	}

}
