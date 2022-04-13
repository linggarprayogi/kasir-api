package com.as.kasirapi.controller.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.as.kasirapi.model.ResponseModel;
import com.as.kasirapi.model.member.Member;
import com.as.kasirapi.pojo.ReqBodyCheckOTP;
import com.as.kasirapi.pojo.ReqBodyRequestOTP;
import com.as.kasirapi.pojo.ResLogin;
import com.as.kasirapi.pojo.ResRequestOtp;
import com.as.kasirapi.repository.MemberRepository;
import com.as.kasirapi.service.MailService;
import com.as.kasirapi.service.MemberService;

@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/member") // initial endpoint ex: ("example/")
public class MemberController {

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	MemberService    memberService;

	@Autowired
	MailService      mailService;

	/**
	 * controller registration
	 * 
	 * @param member
	 * @return
	 */
	@PostMapping("/registration")
	public ResponseEntity<?> registration(@RequestBody Member member) {
		try {

			Member respons = memberService.registration(member);

			return ResponseEntity.status(HttpStatus.OK).body(new ResponseModel(true, "Success", respons));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseModel(false, e.getMessage()));
		}
	}

	/**
	 * controller login
	 * 
	 * @param member
	 * @return
	 */
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Member member) {
		try {

			ResLogin respons = memberService.login(member);

			return ResponseEntity.status(HttpStatus.OK).body(new ResponseModel(true, "Success", respons));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseModel(false, e.getMessage()));
		}
	}

	/**
	 * controller cek member
	 * 
	 * @param username
	 * @return
	 */
	@GetMapping("/cek-member")
	public ResponseEntity<?> getMember(@RequestParam(value = "username", defaultValue = "ALL") String username) {
		try {

			Member members = memberService.checkMember(username);

			return ResponseEntity.status(HttpStatus.OK).body(new ResponseModel(true, "Success", members));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseModel(false, e.getMessage()));
		}
	}

	/**
	 * controller request OTP
	 * 
	 * @param email
	 * @return
	 */
	@GetMapping("/request-otp")
	public ResponseEntity<?> requestOTP(@RequestBody ReqBodyRequestOTP requestBodyOTP) {
		try {

			ResRequestOtp respons = memberService.requestOTP(requestBodyOTP.getEmail());

			return ResponseEntity.status(HttpStatus.OK).body(new ResponseModel(true, "Success", respons));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseModel(false, e.getMessage()));
		}
	}

	/**
	 * controller check OTP
	 * 
	 * @param reqBodyCheckOTP
	 * @return
	 */
	@PostMapping("/check-otp")
	public ResponseEntity<?> checkOTP(@RequestBody ReqBodyCheckOTP reqBodyCheckOTP) {
		try {

			String respons = memberService.checkOTP(reqBodyCheckOTP);

			return ResponseEntity.status(HttpStatus.OK).body(new ResponseModel(true, "Success", respons));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseModel(false, e.getMessage()));
		}
	}

}
