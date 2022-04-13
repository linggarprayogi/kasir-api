package com.as.kasirapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.as.kasirapi.model.auth.UserApi;

@Repository
public interface AuthRepository extends JpaRepository<UserApi, Long> {

	@Query(value = "SELECT * FROM auth.auth_user_api WHERE username=:username", nativeQuery = true)
	UserApi findByName(@Param("username") String username);
}
