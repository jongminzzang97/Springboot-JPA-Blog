package com.cos.blog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.dto.ReplySaveRequesetDto;
import com.cos.blog.model.Board;
import com.cos.blog.model.Reply;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;
import com.cos.blog.repository.ReplyRepository;
import com.cos.blog.repository.UserRepository;

import ch.qos.logback.core.encoder.Encoder;

// 스프링이 컴포넌트 스캔을 통해서 Bean에 등록을 해줌. IoC를 해준다.
@Service
public class BoardService {

	@Autowired
	private BoardRepository boardRepository;

	@Autowired
	private ReplyRepository replyRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Transactional 
	public void 글쓰기(Board board, User user) {
		board.setCount(0);
		board.setUser(user);
		boardRepository.save(board); 
	}
	
	@Transactional(readOnly = true)
	public Page<Board> 글목록(Pageable pageable){
		return boardRepository.findAll(pageable);
	}
	
	@Transactional(readOnly = true)
	public Board 글상세보기(int id) {
		
		//board는 reply를 들고 있음 Eager 전략
		// findById -> reply를 들고 있음 
		return boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("글 상세보기 실패: 글의 아이디를 찾을 수 없습니다.");
				});
	}
	
	@Transactional
	public void 글삭제하기(int id) {
		boardRepository.deleteById(id);
	}
	
	@Transactional
	public void 글수정하기(int id, Board requestBoard) {
		Board board = boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("글 찾기 실패");
				});
		board.setTitle(requestBoard.getTitle());
		board.setContent(requestBoard.getContent());
		// 해당함수가 종료시(Service가 종료될때) 트랜잭션이 종료. -> 더티체킹 자동업데이트(db flush)
	}
	
	@Transactional
	public void 댓글쓰기(ReplySaveRequesetDto replySaveRequesetDto) {
		
		User user = userRepository.findById(replySaveRequesetDto.getUserId())
				.orElseThrow(()->{
					return new IllegalArgumentException("댓글 쓰기 실패 : 유저 id를 찾을 수 없습니다.");
				});
		Board board = boardRepository.findById(replySaveRequesetDto.getBoardId())
				.orElseThrow(()->{
					return new IllegalArgumentException("댓글 쓰기 실패 : 게시글 id를 찾을 수 없습니다.");
				});
		
		Reply reply = new Reply();
		reply.update(user, board, replySaveRequesetDto.getContent());
		
		replyRepository.save(reply);
	}
	
	@Transactional
	public void 댓글쓰기_Dto없이(User user, int boardId, Reply requestReply) {
		Board board = boardRepository.findById(boardId)
				.orElseThrow(()->{
					return new IllegalArgumentException("댓글 쓰기 실패 : 게시글 id를 찾을 수 없습니다.");
				});
		
		requestReply.setUser(user);
		requestReply.setBoard(board);
		
		replyRepository.save(requestReply);
	}
	
	@Transactional
	public void 댓글쓰기_네이티브쿼리(ReplySaveRequesetDto replySaveRequesetDto) {
		replyRepository.mSave(replySaveRequesetDto.getUserId(), replySaveRequesetDto.getBoardId(), replySaveRequesetDto.getContent());
	}
	
	@Transactional
	public void 댓글삭제(int replyId) {
		System.out.println("댓글삭제실행");
		replyRepository.deleteById(replyId);
	}
	
}
