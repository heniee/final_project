package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.BoardDao;
import com.example.demo.entity.Board;

// Junit은 단위(unit) 테스트 도구 - 메소드의 동작을 확인 
@SpringBootTest
public class BoardDaoTest {
	@Autowired
	private BoardDao boardDao;
	
	// Test 케이스 1. dao 생성 -> null이 아님
	//@Test
	public void initTest() {
		assertNotNull(boardDao);
	}
	
	// insert,delete,update의 실행 결과는 변경된 행의 개수 
	// Test 케이스 2. save : Board -> 결과값이 1이다
	// Transaction : 일련의 sql를 모아서 하나의 작업으로 지정
	//				 함께 commit 되거나 rollback되어야한다(일부분만 동작할 수 없다)
	@Transactional
	//@Test
	public void saveTest() {
		Board board = Board.builder().title("제목이에요").content("내용이에요").writer("작성자에요").build();
		assertEquals(1, boardDao.save(board));
	}
	
	// Test 케이스 3. count: count -> 개수를 수동으로 확인해서 assert한다
	//@Test
	public void countTest() {
		assertEquals(26, boardDao.count(null));
	}
	
	// Test 케이스 4. findAll : 글이 14개 있다 11~14까지 4개를 읽어오자
	//@Test
	 public void findAllTest() {
		Map<String,Object> map = new HashMap<>();
		map.put("writer", null);
		map.put("start",11);
		map.put("end",14);
		
		assertEquals(4, boardDao.findAll(map).size());
	}

	// Test 케이스 5 :(내용,제목), 조회수,좋아요,싫어요에 대한 값을 주면 update
	@Transactional
	//@Test
	public void updateTest() {
		assertNotEquals(0,boardDao.update(Board.builder().bno(1).readCnt(1).build()));
		assertNotEquals(0,boardDao.update(Board.builder().bno(1).goodCnt(1).build()));
		assertNotEquals(0,boardDao.update(Board.builder().bno(1).commentCnt(1).build()));
		assertNotEquals(0,boardDao.update(Board.builder().bno(1).title("변경").build())); 
	}
	
	// 바람직하지 않은 결과도 테스트해야한다
	// Test 케이스 6 : 1번글을 읽으면 비어있다, 2번글을 읽으면 존재한다
	//@Test
	public void findByIdTest() {
		assertEquals(true, boardDao.findById(50).isEmpty());
		assertEquals(true, boardDao.findById(1).isPresent());
	}
	
	// Test 케이스 7 : 글의 작성자 확인
	//@Test
	public void findWriterTest() {
		assertEquals("spring", boardDao.findWriterById(2));
	}	
	
	// Test 케이스 8 : 글의 삭제 결과 성공시 1, 실패시 0 
 	@Transactional
	@Test
	public void deleteByIdTest() {
		assertEquals(1, boardDao.deleteById(1));
	}
}
