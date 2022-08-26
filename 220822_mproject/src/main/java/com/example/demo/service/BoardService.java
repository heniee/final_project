package com.example.demo.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.demo.dao.BoardDao;
import com.example.demo.dto.BoardDto;
import com.example.demo.dto.BoardDto.Page;
import com.example.demo.entity.Board;
import com.example.demo.exception.BoardNotFoundException;
import com.example.demo.exception.JobFailException;

@Service
public class BoardService {
	@Autowired
	private BoardDao boardDao;
	@Value("${mproject.pagesize}")
	private Integer pagesize;
	
	// 글쓰기 : 실패하면 409
	public Board write(BoardDto.Write dto, String loginId) {
		Board board = dto.toEntity().addWriter(loginId);
		boardDao.save(board);
		return board;
	}
	
	// 서비스는 업무로직(비즈니스 로직)이 있는 곳
	// 글읽기 : 글이 없으면 409. 글이 있고 글쓴이면 조회수 증가 
	// orElseThrow를 이용해 예외 발생 -> ControllerAdvice에서 처리
	public BoardDto.Read read(Integer bno, String loginId){
		BoardDto.Read dto = boardDao.findById(bno).orElseThrow(()->new BoardNotFoundException());
		// 글쓴이가 아닐경우 
		if(loginId!=null && dto.getWriter().equals(loginId)==false) {
			boardDao.update(Board.builder().bno(bno).readCnt(1).build());
			dto.setReadCnt(dto.getReadCnt()+1);
		}
		return dto;
	}
	
	// 글목록 : 글이 없으면 빈 목록 
	public BoardDto.Page list(Integer pageno, String writer){
		Integer totalcount = boardDao.count(writer);
		// pageno		start	end
				// 1			1		10
				// 2			11		20
				// end가 totalcount보다 크다면.... totalcount가 end
				
		Integer start = (pageno-1)*pagesize+1;
		Integer end = start + pagesize - 1;
		if(end>totalcount)
			end=totalcount;
		
		Map<String,Object> map = new HashMap<>();
		map.put("writer", writer);
		map.put("start",start);
		map.put("end",end);
		return new Page(pageno, pagesize,totalcount, boardDao.findAll(map));
		//return new Page(pageno, pagesize,totalcount, boardDao.findAll(map));
	}
	
	// 글변경 : 실패 - 글이 없다(BoardNotFouneException), 글쓴이가 아니다(JobFailException)
	public void update(BoardDto.Update dto, String loginId) {
		String writer = boardDao.findWriterById(dto.getBno()).orElseThrow(()->new BoardNotFoundException());
		if(writer.equals(loginId)==false)
			throw new JobFailException("변경 권한이 없습니다");
		boardDao.update(dto.toEntity());
	}
	
	// 글삭제 : 실패 - 글이 없다(BNFE), 글쓴이가 아니다(JFE)
	public void delete(Integer bno, String loginId) {
		String writer = boardDao.findWriterById(bno).orElseThrow(()->new BoardNotFoundException());
		if(writer.equals(loginId)==false)
			throw new JobFailException("변경 권한이 없습니다");
		boardDao.deleteById(bno);
	}
	
	
}