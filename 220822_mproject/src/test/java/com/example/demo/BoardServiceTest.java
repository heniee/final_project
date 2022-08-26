package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.dto.BoardDto;
import com.example.demo.entity.Board;
import com.example.demo.exception.BoardNotFoundException;
import com.example.demo.service.BoardService;

@SpringBootTest
public class BoardServiceTest {
	@Autowired
	private BoardService service;
	
	//@Test
	public void writeTest() {
		BoardDto.Write dto = BoardDto.Write.builder().title("dd").content("dd").build();
		Board board = service.write(dto, "spring");
		assertNotNull(board.getBno());
	}
	@Test
	public void readTeat() {
		Assertions.assertThrows(BoardNotFoundException.class, ()-> service.read(1000,"spring"));
		//BoardDto.Read dto = service.read(3, "spring");
		assertEquals(0, service.read(2, null).getReadCnt());
		assertEquals(0, service.read(2, "spring").getReadCnt());
		assertEquals(1, service.read(2, "summer").getReadCnt());
	}
}
