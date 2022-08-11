package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
// @DynamicInsert  // insert시에 null인 필드를 제외시켜준다.
public class User {

	@Id //Primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) //프로젝트에서 연결된 DB의 넘버링 전략을 따라간다.
	private int id; // 시퀀스, auto_increment
	
	@Column(nullable = false, length=100, unique=true)
	private String username;

	@Column(nullable = false, length=100) //추후에 해쉬로 암호화 할수있기 때문에..
	private String password;
	
	@Column(nullable = false, length=50)
	private String email;
	
	// @ColumnDefault("USER")
	@Enumerated(EnumType.STRING) //DB는 RoleType이 없으므로 RoleType이 String임을 알려줌
	private RoleType role; // 스트링이면 userrrr 가 가능
	
	private String oauth; // kakao, google ...
	
	@CreationTimestamp // 시간이 자동으로 기록됨
	private Timestamp createDate;
	
	
}
