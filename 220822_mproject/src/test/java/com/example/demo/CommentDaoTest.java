package com.example.demo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.transaction.annotation.*;

import com.example.demo.dao.*;
import com.example.demo.entity.*;

@SpringBootTest
public class CommentDaoTest {
	@Autowired
	private CommentDao dao;
	
	//@Test
	public void saveTest() {
		 assertEquals(1 , dao.save(Comment.builder().bno(1).content("zzz")
			.writer("winter").build()));
	}
	
	//@Test
	public void findByBnoTest() {
		assertEquals(1, dao.findByBno(1).size());
		assertEquals(1, dao.findByBno(10).size());
	}
	
	//@Test
	public void findWriterTEst() {
		assertEquals(true, dao.findWriterById(2).isPresent());
		assertEquals(true, dao.findWriterById(1500).isEmpty());
	}
	
	@Transactional
	//@Test
	public void deleteByCnoTest() {
		assertEquals(1, dao.deleteByCno(61));
		assertEquals(0, dao.deleteByCno(1500));
	}
	
	@Transactional
	@Test
	public void deleteByBnoTest() {
		assertNotEquals(0, dao.deleteByBno(2));
		assertEquals(0,dao.deleteByBno(1500));
	}
}
















