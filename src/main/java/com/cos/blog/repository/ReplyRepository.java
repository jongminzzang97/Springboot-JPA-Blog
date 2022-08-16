package com.cos.blog.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.cos.blog.dto.ReplySaveRequesetDto;
import com.cos.blog.model.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Integer>{

	// interface 에서는 public 생략해도 public
	
	@Modifying
	@Query(value="INSERT INTO reply(userId, boardId, content, createDate) VALUES(?1, ?2, ?3, now())", nativeQuery = true)
	int mSave(int userId, int boardId, String content);
	
}
