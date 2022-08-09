package com.cos.blog.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HttpControllerTest {

	private static final String TAG = "HttpControllerTest : ";
	
	@GetMapping("/http/lombok")
	public String lombokTest() {
		// Member m = new Member(1, "jongmin", "1234", "jongminzzang97@naver.com");
		Member m = Member.builder().password("1234").email("jongminzzang97@naver.com").id(1).username("jongmin").build();
		System.out.println(TAG + "getter : "+ m.getId());
		m.setId(2);
		System.out.println(TAG +"getter : "+ m.getId());
		return "lombok test 완료";
	}
	
	// get 방식으로 서버에 요청할 때는 query string으로 값을 전달하는 방법 외에는 없음
	@GetMapping("/http/get")
	public String getTest(Member m) { //MessageConverter 가 알아서 읽어서 파싱
		return "get 요청" + m.getId() + m.getPassword() + m.getUsername() + m.getEmail();
	}

	@PostMapping("/http/post")
	public String postTest(@RequestBody Member m) { //MessageConverter 가 알아서 읽어서 파싱
		return "post 요청" + m.getId() + m.getPassword() + m.getUsername() + m.getEmail();
	}

	@PutMapping("/http/put")
	public String putTest() {
		return "put 요청";
	}

	@DeleteMapping("/http/delete")
	public String deleteTest() {
		return "delete 요청";
	}

}
