package com.as.kasirapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.as.kasirapi.model.member.MemberOTP;

@Repository
public interface MemberOTPRepository extends JpaRepository<MemberOTP, Long> {

	@Query(value = "SELECT * FROM membership.mbr_member_otp WHERE member_id=:memberId AND otp_code=:otpCode", nativeQuery = true)
	MemberOTP findMemberOTPIdMember(@Param("memberId") Long memberId, @Param("otpCode") String otpCode);

}
