package com.cos.blog.test;

import java.util.List;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

@RestController
public class DummyControllerTest {
	
	@Autowired // 의존성 주입
	private UserRepository userRepository;
	
	
	@DeleteMapping("dummy/user/{id}")
	public String delete(@PathVariable int id) {
		try {
			userRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			return "삭제에 실패하였습니다. 해당 id는 DB에 없습니다.";
		}
		return "삭제되었습니다.";
	}
	
	
	@GetMapping("/dummy/userlist")
	public List<User> list(){
		return userRepository.findAll();
	}
	
	// email, password 수정 기능 구현
	
	@Transactional // 함수 종료시에 자동커밋
	@PutMapping("/dummy/user/{id}")
	// json 데이터를 보냈는데, java object(User)로 변환해서 받아줌 (MessageConverter의 Jackson)
	public User updateUser(@PathVariable int id, @RequestBody User requestUser) {
		System.out.println("id : " + id);
		System.out.println("password : " + requestUser.getPassword());
		System.out.println("email : " + requestUser.getEmail());
		
		User user = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("수정에 실패하였습니다.");
		});
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
		
		// userRepository.save(user);
		
		// 더티 체킹
		
		return user;
	}
	
	
	// 한 페이지 당 2건의 데이터를 리턴
	// http://localhost:8000/blog/dummy/user
	// http://localhost:8000/blog/dummy/user?page=0
	@GetMapping("/dummy/user")
	public Page<User> pageList(@PageableDefault(size=2, sort="id", direction = Sort.Direction.DESC) Pageable pageable){
		// Page<User> users = userRepository.findAll(pageable); -> page 에 대한 정보도 포함
		Page<User> pagingusers = userRepository.findAll(pageable);
		// List<User> users = pagingusers.getContent();
		return pagingusers;
	}
	
	// id주소로 파라미터를 전달 받을 수 있음
	// http://localhost:8000/blog/dummy/user/3
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
		
		// findById의 return type은 Optional
		// Optional -> null인지 판단해야함
		// 1. .get() -> null 객체에 접근하면 문제가 발생
		// 2. .orElseGet() -> null 이 아닌 객체라면 정상 접근 // null이 들어온다고하면 빈 객체를 생성하여 return
		// 3. 
		
		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
			@Override
			public IllegalArgumentException get() {
				// TODO Auto-generated method stub
				return new IllegalArgumentException("해당 유저는 없습니다. id : " + id);
			}
		});
		// return -> user : JAVA object
		// MessageConverter : JAVA object -> JSON (웹 브라우저가 이해할 수 있는 데이터) // Jackson이라는 라이브러리를 이용
		return user;
	}
	
	
	// http://localhost:8000/blog/dummy/join (요청)
	//	@PostMapping("/dummy/join")
	//	public String join(String username, String password, String email) {
	//		System.out.println("username: " + username);
	//		System.out.println("password: " + password);
	//		System.out.println("email: " + email);
	//		return "회원가입이 완료되었습니다.";
	//	}
	
	@PostMapping("/dummy/join")
	public String join(User user) {
		
		System.out.println("userid: " + user.getId());
		System.out.println("username: " + user.getUsername());
		System.out.println("password: " + user.getPassword());
		System.out.println("email: " + user.getEmail());
		System.out.println("role: " + user.getRole());
		System.out.println("ceateDate: " + user.getCreateDate());
		
		user.setRole(RoleType.USER);
		userRepository.save(user);
		return "회원가입이 완료되었습니다.";
	}
}
