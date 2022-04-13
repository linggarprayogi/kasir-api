package com.as.kasirapi.model.member;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.as.kasirapi.model.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "mbr_member_otp", schema = "membership")
public class MemberOTP extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long   id;
	private Long   member_id;
	private String otp_code;
	private int    status;

	@Override
	public String toString() {
		return "MemberOTP [id=" + id + ", member_id=" + member_id + ", otp_code=" + otp_code + ", status=" + status
				+ "]";
	}

}
