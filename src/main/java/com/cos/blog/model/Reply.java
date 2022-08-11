package com.cos.blog.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Reply {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false, length = 200)
	private String content;
	
	@ManyToOne
	@JoinColumn(name="boardId")
	private Board board;
	
	@ManyToOne(fetch = FetchType.EAGER)  // 그냥 가져오자. 왜? -> 하나밖에 없으니까 간단함
	@JoinColumn(name="userId")
	private User user;
	

	//	@@OneToMany(fetch = FetchType.LAZY) // 필요하면 가져오고 필요하지 않으면 가져오지 않는다.
	@OneToMany(mappedBy = "board", fetch = FetchType.EAGER) //mappedBy 연관관계의 주인이 아니다. 난 FK가 아니다. DB에 칼럼을 만들지 말라. board는 Reply 클레스의 필드 이름
	private List<Reply> reply;
	
	@CreationTimestamp
	private Timestamp createDate;
}
