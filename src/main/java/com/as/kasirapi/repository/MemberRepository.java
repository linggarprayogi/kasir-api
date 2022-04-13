package com.as.kasirapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.as.kasirapi.model.member.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

	@Query(value = "SELECT * FROM membership.mbr_member WHERE username=:username", nativeQuery = true)
	public Member findByName(@Param("username") String username);

	@Query(value = "SELECT * FROM membership.mbr_member WHERE email=:email", nativeQuery = true)
	public Member findByEmail(@Param("email") String email);

}