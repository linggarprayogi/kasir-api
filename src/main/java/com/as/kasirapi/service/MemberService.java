package com.as.kasirapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.as.kasirapi.model.Mail;
import com.as.kasirapi.model.member.Member;
import com.as.kasirapi.model.member.MemberOTP;
import com.as.kasirapi.pojo.PayloadAccess;
import com.as.kasirapi.pojo.ReqBodyCheckOTP;
import com.as.kasirapi.pojo.ResLogin;
import com.as.kasirapi.pojo.ResRequestOtp;
import com.as.kasirapi.repository.MemberOTPRepository;
import com.as.kasirapi.repository.MemberRepository;
import com.as.kasirapi.util.JwtTokenUtil;
import com.as.kasirapi.util.OTP;
import com.google.gson.Gson;

@Service
@Transactional
public class MemberService {

	@Autowired
	private MemberRepository    memberRepository;
	@Autowired
	private MemberOTPRepository memberOTPRepository;
	@Autowired
	private MailService         mailService;
	@Autowired
	private JwtTokenUtil        jwtTokenUtil;

	Gson                        gson = new Gson();

	public List<Member> listAll() {
		return memberRepository.findAll();
	}

	public Member checkMember(String username) {
		return memberRepository.findByName(username);
	}

	public ResRequestOtp requestOTP(String email) throws Exception {

		Member member = memberRepository.findByEmail(email);

		if (member == null)
			throw new Exception("Member not valid");

		OTP           otp        = new OTP();
		char[]        otpCode    = otp.generateOTP(4);
		StringBuilder otpCodeStr = new StringBuilder();
		otpCodeStr.append(otpCode);

		MemberOTP memberOTP = MemberOTP.builder().id(null).member_id(member.getId()).otp_code(otpCodeStr.toString())
				.status(0).build();
		memberOTPRepository.save(memberOTP);

		Mail mail = new Mail();
		mail.setMailFrom("Alterseed <no-reply@alterseed.id>");
		mail.setMailTo(member.getEmail().toLowerCase());
		mail.setMailSubject("Otp Code - Kasir Kita");
		mail.setMailContent("\r\n" + "        <!doctype html>\r\n" + "        <html>\r\n" + "        <head>\r\n"
				+ "            <meta name=\"viewport\" content=\"width=device-width\" />\r\n"
				+ "            <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\r\n"
				+ "            <title>Cloud Ticketing System</title>\r\n" + "            <style>\r\n"
				+ "            /* -------------------------------------\r\n" + "                GLOBAL RESETS\r\n"
				+ "            ------------------------------------- */\r\n" + "            img {\r\n"
				+ "                border: none;\r\n" + "                -ms-interpolation-mode: bicubic;\r\n"
				+ "                max-width: 100%; }\r\n" + "        \r\n" + "            body {\r\n"
				+ "                background-color: #f6f6f6;\r\n" + "                font-family: sans-serif;\r\n"
				+ "                -webkit-font-smoothing: antialiased;\r\n" + "                font-size: 14px;\r\n"
				+ "                line-height: 1.4;\r\n" + "                margin: 0;\r\n"
				+ "                padding: 0;\r\n" + "                -ms-text-size-adjust: 100%;\r\n"
				+ "                -webkit-text-size-adjust: 100%; }\r\n" + "        \r\n" + "            table {\r\n"
				+ "                border-collapse: separate;\r\n" + "                mso-table-lspace: 0pt;\r\n"
				+ "                mso-table-rspace: 0pt;\r\n" + "                width: 100%; }\r\n"
				+ "                table td {\r\n" + "                font-family: sans-serif;\r\n"
				+ "                font-size: 14px;\r\n" + "                vertical-align: top; }\r\n" + "        \r\n"
				+ "            /* -------------------------------------\r\n" + "                BODY & CONTAINER\r\n"
				+ "            ------------------------------------- */\r\n" + "        \r\n"
				+ "            .body {\r\n" + "                background-color: #f6f6f6;\r\n"
				+ "                width: 100%; }\r\n" + "        \r\n"
				+ "            /* Set a max-width, and make it display as block so it will automatically stretch to that width, but will also shrink down on a phone or something */\r\n"
				+ "            .container {\r\n" + "                display: block;\r\n"
				+ "                Margin: 0 auto !important;\r\n" + "                /* makes it centered */\r\n"
				+ "                max-width: 680px;\r\n" + "                padding: 10px;\r\n"
				+ "                width: 680px; }\r\n" + "        \r\n"
				+ "            /* This should also be a block element, so that it will fill 100% of the .container */\r\n"
				+ "            .content {\r\n" + "                box-sizing: border-box;\r\n"
				+ "                display: block;\r\n" + "                Margin: 0 auto;\r\n"
				+ "                max-width: 680px;\r\n" + "                padding: 10px; }\r\n" + "        \r\n"
				+ "            /* -------------------------------------\r\n"
				+ "                HEADER, FOOTER, MAIN\r\n"
				+ "            ------------------------------------- */\r\n" + "            .main {\r\n"
				+ "                background: #ffffff;\r\n" + "                border-radius: 3px;\r\n"
				+ "                width: 100%; }\r\n" + "        \r\n" + "            .wrapper {\r\n"
				+ "                box-sizing: border-box;\r\n" + "                padding: 20px; }\r\n"
				+ "        \r\n" + "            .content-block {\r\n" + "                padding-bottom: 10px;\r\n"
				+ "                padding-top: 10px;\r\n" + "            }\r\n" + "        \r\n"
				+ "            .footer {\r\n" + "                clear: both;\r\n"
				+ "                Margin-top: 10px;\r\n" + "                text-align: center;\r\n"
				+ "                width: 100%; }\r\n" + "                .footer td,\r\n"
				+ "                .footer p,\r\n" + "                .footer span,\r\n"
				+ "                .footer a {\r\n" + "                color: #999999;\r\n"
				+ "                font-size: 12px;\r\n" + "                text-align: center; }\r\n" + "        \r\n"
				+ "            /* -------------------------------------\r\n" + "                TYPOGRAPHY\r\n"
				+ "            ------------------------------------- */\r\n" + "            h1,\r\n"
				+ "            h2,\r\n" + "            h3,\r\n" + "            h4 {\r\n"
				+ "                color: #000000;\r\n" + "                font-family: sans-serif;\r\n"
				+ "                font-weight: 400;\r\n" + "                line-height: 1.4;\r\n"
				+ "                margin: 0;\r\n" + "                Margin-bottom: 30px; }\r\n" + "        \r\n"
				+ "            h1 {\r\n" + "                font-size: 35px;\r\n"
				+ "                font-weight: 300;\r\n" + "                text-align: center;\r\n"
				+ "                text-transform: capitalize; }\r\n" + "        \r\n" + "            p,\r\n"
				+ "            ul,\r\n" + "            ol {\r\n" + "                font-family: sans-serif;\r\n"
				+ "                font-size: 14px;\r\n" + "                font-weight: normal;\r\n"
				+ "                margin: 0;\r\n" + "                Margin-bottom: 15px; }\r\n"
				+ "                p li,\r\n" + "                ul li,\r\n" + "                ol li {\r\n"
				+ "                list-style-position: inside;\r\n" + "                margin-left: 5px; }\r\n"
				+ "        \r\n" + "            a {\r\n" + "                color: #3498postgres;\r\n"
				+ "                text-decoration: underline; }\r\n" + "        \r\n"
				+ "            /* -------------------------------------\r\n" + "                BUTTONS\r\n"
				+ "            ------------------------------------- */\r\n" + "            .btn {\r\n"
				+ "                box-sizing: border-box;\r\n" + "                width: 100%; }\r\n"
				+ "                .btn > tbody > tr > td {\r\n" + "                padding-bottom: 15px; }\r\n"
				+ "                .btn table {\r\n" + "                width: auto; }\r\n"
				+ "                .btn table td {\r\n" + "                background-color: #3e8dc6;\r\n"
				+ "                border-radius: 5px;\r\n" + "                text-align: center; }\r\n"
				+ "                .btn a {\r\n" + "                background-color: #3e8dc6;\r\n"
				+ "                border: solid 1px #3e8dc6;\r\n" + "                border-radius: 5px;\r\n"
				+ "                box-sizing: border-box;\r\n" + "                color: #e3d76e;\r\n"
				+ "                cursor: pointer;\r\n" + "                display: inline-block;\r\n"
				+ "                font-size: 14px;\r\n" + "                font-weight: bold;\r\n"
				+ "                margin: 0;\r\n" + "                padding: 12px 25px;\r\n"
				+ "                text-decoration: none;\r\n" + "                text-transform: capitalize; }\r\n"
				+ "        \r\n" + "            .btn-primary table td {\r\n"
				+ "                background-color: #3e8dc6; }\r\n" + "        \r\n"
				+ "            .btn-primary a {\r\n" + "                background-color: #3e8dc6;\r\n"
				+ "                border-color: #3e8dc6;\r\n" + "                color: #e3d76e; }\r\n"
				+ "        \r\n" + "            /* -------------------------------------\r\n"
				+ "                OTHER STYLES THAT MIGHT BE USEFUL\r\n"
				+ "            ------------------------------------- */\r\n" + "            .last {\r\n"
				+ "                margin-bottom: 0; }\r\n" + "        \r\n" + "            .first {\r\n"
				+ "                margin-top: 0; }\r\n" + "        \r\n" + "            .align-center {\r\n"
				+ "                text-align: center; }\r\n" + "        \r\n" + "            .align-right {\r\n"
				+ "                text-align: right; }\r\n" + "        \r\n" + "            .align-left {\r\n"
				+ "                text-align: left; }\r\n" + "        \r\n" + "            .clear {\r\n"
				+ "                clear: both; }\r\n" + "        \r\n" + "            .mt0 {\r\n"
				+ "                margin-top: 0; }\r\n" + "        \r\n" + "            .mb0 {\r\n"
				+ "                margin-bottom: 0; }\r\n" + "        \r\n" + "            .preheader {\r\n"
				+ "                color: transparent;\r\n" + "                display: none;\r\n"
				+ "                height: 0;\r\n" + "                max-height: 0;\r\n"
				+ "                max-width: 0;\r\n" + "                opacity: 0;\r\n"
				+ "                overflow: hidden;\r\n" + "                mso-hide: all;\r\n"
				+ "                visibility: hidden;\r\n" + "                width: 0; }\r\n" + "        \r\n"
				+ "            .powered-by a {\r\n" + "                text-decoration: none; }\r\n" + "        \r\n"
				+ "            hr {\r\n" + "                border: 0;\r\n"
				+ "                border-bottom: 1px solid #f6f6f6;\r\n" + "                Margin: 20px 0; }\r\n"
				+ "        \r\n" + "            /* -------------------------------------\r\n"
				+ "                RESPONSIVE AND MOBILE FRIENDLY STYLES\r\n"
				+ "            ------------------------------------- */\r\n"
				+ "            @media only screen and (max-width: 620px) {\r\n"
				+ "                table[class=body] h1 {\r\n" + "                font-size: 28px !important;\r\n"
				+ "                margin-bottom: 10px !important; }\r\n" + "                table[class=body] p,\r\n"
				+ "                table[class=body] ul,\r\n" + "                table[class=body] ol,\r\n"
				+ "                table[class=body] td,\r\n" + "                table[class=body] span,\r\n"
				+ "                table[class=body] a {\r\n" + "                font-size: 16px !important; }\r\n"
				+ "                table[class=body] .wrapper,\r\n" + "                table[class=body] .article {\r\n"
				+ "                padding: 10px !important; }\r\n" + "                table[class=body] .content {\r\n"
				+ "                padding: 0 !important; }\r\n" + "                table[class=body] .container {\r\n"
				+ "                padding: 0 !important;\r\n" + "                width: 100% !important; }\r\n"
				+ "                table[class=body] .main {\r\n"
				+ "                border-left-width: 0 !important;\r\n"
				+ "                border-radius: 0 !important;\r\n"
				+ "                border-right-width: 0 !important; }\r\n"
				+ "                table[class=body] .btn table {\r\n" + "                width: 100% !important; }\r\n"
				+ "                table[class=body] .btn a {\r\n" + "                width: 100% !important; }\r\n"
				+ "                table[class=body] .img-responsive {\r\n"
				+ "                height: auto !important;\r\n" + "                max-width: 100% !important;\r\n"
				+ "                width: auto !important; }}\r\n" + "        \r\n"
				+ "            /* -------------------------------------\r\n"
				+ "                PRESERVE THESE STYLES IN THE HEAD\r\n"
				+ "            ------------------------------------- */\r\n" + "            @media all {\r\n"
				+ "                .ExternalClass {\r\n" + "                width: 100%; }\r\n"
				+ "                .ExternalClass,\r\n" + "                .ExternalClass p,\r\n"
				+ "                .ExternalClass span,\r\n" + "                .ExternalClass font,\r\n"
				+ "                .ExternalClass td,\r\n" + "                .ExternalClass div {\r\n"
				+ "                line-height: 100%; }\r\n" + "                .apple-link a {\r\n"
				+ "                color: inherit !important;\r\n"
				+ "                font-family: inherit !important;\r\n"
				+ "                font-size: inherit !important;\r\n"
				+ "                font-weight: inherit !important;\r\n"
				+ "                line-height: inherit !important;\r\n"
				+ "                text-decoration: none !important; }\r\n"
				+ "                .btn-primary table td:hover {\r\n"
				+ "                background-color: #34495e !important; }\r\n"
				+ "                .btn-primary a:hover {\r\n"
				+ "                background-color: #34495e !important;\r\n"
				+ "                border-color: #34495e !important; } }\r\n" + "            \r\n"
				+ "            .btn {\r\n" + "                -webkit-border-radius: 5;\r\n"
				+ "                -moz-border-radius: 5;\r\n" + "                border-radius: 5px;\r\n"
				+ "                font-family: Arial;\r\n" + "                color: #e3d76e;\r\n"
				+ "                font-size: 16px;\r\n" + "                font-weight: 'bold' !important;\r\n"
				+ "                background: #3e8dc6;\r\n" + "                padding: 13px 50px 13px 50px;\r\n"
				+ "                text-decoration: none;\r\n" + "            }\r\n" + "        \r\n"
				+ "            .btn:hover {\r\n" + "                background: #29618a;\r\n"
				+ "                color: #f9f1a8;\r\n" + "                text-decoration: none;\r\n"
				+ "            }\r\n" + "            </style>\r\n" + "        </head>\r\n"
				+ "        <body class=\"\">\r\n"
				+ "            <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"body\">\r\n"
				+ "            <tr>\r\n" + "                <td>&nbsp;</td>\r\n"
				+ "                <td class=\"container\">\r\n" + "                <div class=\"content\">\r\n"
				+ "        \r\n" + "                    <table class=\"main\">\r\n" + "        \r\n"
				+ "                    <!-- START MAIN CONTENT AREA -->\r\n" + "                    <tr>\r\n"
				+ "                        <td class=\"wrapper\">\r\n"
				+ "                        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n"
				+ "                            <tr>\r\n" + "                            <td>\r\n"
				+ "                                <p>Dear<strong> " + member.getUsername() + "</strong>,</p>\r\n"
				+ "                                <p>Berikut kode OTP anda</p>\r\n"
				+ "                                <br>\r\n" + "                                <p><b><h1>" + otpCodeStr
				+ "</h1></b></p>\r\n" + "                                <br>\r\n"
				+ "                            </td>\r\n" + "                            </tr>\r\n"
				+ "                        </table>\r\n" + "                        </td>\r\n"
				+ "                    </tr>\r\n" + "        \r\n"
				+ "                    <!-- END MAIN CONTENT AREA -->\r\n" + "                    </table>\r\n"
				+ "        \r\n" + "                <!-- END CENTERED WHITE CONTAINER -->\r\n"
				+ "                </div>\r\n" + "                </td>\r\n" + "                <td>&nbsp;</td>\r\n"
				+ "            </tr>\r\n" + "            </table>\r\n" + "        </body>\r\n" + "        </html>");

		mailService.sendEmail(mail);

		return new ResRequestOtp(member.getEmail(), otpCodeStr.toString());
	}

	public Member registration(Member member) throws Exception {
		Member cekMember = memberRepository.findByEmail(member.getEmail());

		if (cekMember != null) {
			throw new Exception("Email already exist");
		}

		memberRepository.save(member);

		return cekMember;
	}

	public ResLogin login(Member member) throws Exception {
		Member dataMember = memberRepository.findByEmail(member.getEmail());

		if (dataMember == null)
			throw new Exception("Member not valid");

		if (!BCrypt.checkpw(member.getPassword(), dataMember.getPassword()))
			throw new Exception("Password does not match");

		PayloadAccess payloadAccess   = PayloadAccess.builder().username(dataMember.getUsername())
				.email(member.getEmail()).build();
		String        payloadAccesStr = gson.toJson(payloadAccess);

		System.out.println(payloadAccesStr);
		String accessToken  = jwtTokenUtil.generateAccessToken(payloadAccesStr);
		String refreshToken = jwtTokenUtil.generateRefreshToken(dataMember.getEmail());

		return new ResLogin(dataMember, accessToken, refreshToken);
	}

	public String checkOTP(ReqBodyCheckOTP reqBodyCheckOTP) {
		String    respons   = null;

		Member    member    = memberRepository.findByEmail(reqBodyCheckOTP.getEmail());
		MemberOTP memberOTP = memberOTPRepository.findMemberOTPIdMember(member.getId(), reqBodyCheckOTP.getOtp_code());

		if (memberOTP != null) {
			switch (memberOTP.getStatus()) {
			case 0:
				// Update Status OTP
				memberOTP.setStatus(1);
				memberOTPRepository.save(memberOTP);
				respons = "Kode OTP sesuai";
				break;
			case 1:
				respons = "OTP code sudah pernah digunakan";
				break;
			default:
				respons = "Status OTP tidak sesuai 0/1";
				break;
			}
		} else {
			respons = "Kode OTP tidak sesuai, coba lagi !";
		}
		return respons;
	}

}
