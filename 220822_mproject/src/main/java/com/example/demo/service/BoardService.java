package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.BoardDao;
import com.example.demo.dto.BoardDto;
import com.example.demo.entity.Board;

@Service
public class BoardService {
	@Autowired
	private BoardDao boardDao;

	
	// 글쓰기 : 실패하면 409
	public Board write(BoardDto.Write dto, String loginId) {
		Board board = dto.toEntity().addWriter(loginId);
		boardDao.save(board);
		return board;
	}
	
	// 글읽기 : 글이 없으면 409. 글이 있고 글쓴이면 조회수 증가 
	
	// 글목록 : 글이 없으면 빈 목록 
	
	// 글변경 : 실패 - 글이 없다(BoardNotFouneException), 글쓴이가 아니다(JobFailException)
	
	// 글삭제 : 실패 - 글이 없다(BNFE), 글쓴이가 아니다(JFE)
	
}