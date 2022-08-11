package com.cos.blog.controller;

import java.util.UUID;

import org.hibernate.internal.build.AllowSysOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.cos.blog.model.KakaoProfile;
import com.cos.blog.model.OAuthToken;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

// 인증이 안된 사용자들이 출입할 수 있는 경로를 /auth/** 허용
// 그냥 주소가 /이면 index.jsp 허용
// static이하에 있는 /js/**, /css/**, /image/** 허용

@Controller
public class UserController {
	
	@Value("${cos.key}")
	private String cosKey;

	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@GetMapping("/auth/joinForm")
	public String joinFrom() {
		return "user/joinForm";
	}
	
	@GetMapping("/auth/loginForm")
	public String loginFrom() {
		return "user/loginForm";
	}
	
	@GetMapping("/user/updateForm")
	public String updateFrom() {
		return "user/updateForm";
	}
	
	@GetMapping("auth/kakao/callback")
	public String kakaoCallback(String code) {
		
		// POST 방식으로 key = value 데이터를 요청 (카카오쪽으로)
		
		RestTemplate rt = new RestTemplate();
		// HttpHeader 오브젝트 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		// HttpBody 오브젝트 생성
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", "71edfed970c00b7544c0253fb4875e65");
		params.add("redirect_uri", "http://localhost:8000/auth/kakao/callback");
		params.add("code", code);
		
		// HttpHeader와 HttpBody를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);
		
		// Http 요청하기 -> post 방식으로
		ResponseEntity<String> response = rt.exchange(
				"https://kauth.kakao.com/oauth/token",
				HttpMethod.POST,
				kakaoTokenRequest,
				String.class
				);
		
		ObjectMapper objectMapper = new ObjectMapper();
		OAuthToken oAuthToken = null;
		try {
			oAuthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		System.out.println("카카오 엑세스 토큰 : " + oAuthToken.getAccess_token());
		
		RestTemplate rt2 = new RestTemplate();
		// HttpHeader 오브젝트 생성
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization", "Bearer "+oAuthToken.getAccess_token());
		headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest2 = new HttpEntity<>(headers2);
		
		// Http 요청하기 -> post 방식으로
		ResponseEntity<String> response2 = rt2.exchange(
				"https://kapi.kakao.com/v2/user/me",
				HttpMethod.POST,
				kakaoProfileRequest2,
				String.class
				);
		
		
		ObjectMapper objectMapper2 = new ObjectMapper();
		KakaoProfile kakaoProfile = null;
		try {
			kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		System.out.println("카카오 아이디: " + kakaoProfile.getId());
		System.out.println("카카오 이메일: " + kakaoProfile.getKakao_account().getEmail());
		
		
		//User 오브젝트 : username, password, email

		System.out.println("블로그 유저네임: " + kakaoProfile.getKakao_account().getEmail() + "_" +kakaoProfile.getId());
		System.out.println("블로그 이메일: " + kakaoProfile.getKakao_account().getEmail());
		UUID garbagePassword = UUID.randomUUID();
		System.out.println("블로그 패스워드" + garbagePassword);
		
		User kakaoUser = User.builder()
				.username(kakaoProfile.getKakao_account().getEmail() + "_" +kakaoProfile.getId())
				.email(kakaoProfile.getKakao_account().getEmail())
				.password(cosKey)
				.oauth("kakao")
				.build();
		
		User originUser = userService.회원찾기(kakaoUser.getUsername());
		if (originUser.getUsername() == null) {
			System.out.println("신규 회원입니다");
			userService.회원가입(kakaoUser);
		}
		
		// 로그인 처리
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(), cosKey));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		
		return "redirect:/";
	}
}
