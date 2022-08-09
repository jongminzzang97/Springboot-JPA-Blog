package com.cos.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TempControllerTest {

	// http://localhost:8000/blog/temp/home
	@GetMapping("/temp/home")
	public String tempHome() {
		System.out.println("tempHome()");
		return "/home.html";
	}
	
	@GetMapping("/temp/img")
	public String tempImg() {
			return "/a.png";
	}
	
	//	 오류가 난다. 
	// static folder 안에는 정적인 파일만 있어야함
	//	@GetMapping("/temp/jsp")
	//	public String tempJsp() {
	//			return "/test.jsp";
	//	}
	//	
	
	@GetMapping("/temp/jsp")
		public String tempJsp() {
				return "/test";
		}
		
	
}
