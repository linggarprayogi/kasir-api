package com.as.kasirapi.model.auth;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.as.kasirapi.model.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "auth_user_api", schema = "auth")
public class UserApi extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long   id;
	@Column(name = "user_api_code")
	private String userApiCode;
	private String username;
	private String password;
	private String status;

	@Override
	public String toString() {
		return "UserApi [userApiCode=" + userApiCode + ", username=" + username + ", password=" + password + ", status="
				+ status + "]";
	}

}
