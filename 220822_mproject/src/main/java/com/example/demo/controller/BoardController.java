package com.example.demo.controller;

import java.security.*;

import javax.validation.*;
import javax.validation.constraints.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.security.access.prepost.*;
import org.springframework.validation.*;
import org.springframework.validation.annotation.*;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.*;
import com.example.demo.entity.*;
import com.example.demo.service.BoardService;

import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.*;
import springfox.documentation.annotations.*;

// 스프링 검증 활성화(자바 표준 검증보다 기능이 더 많다)
@Validated
// REST만 처리하는 @Controller의 부분집합
@RestController
public class BoardController {
	@Autowired
	private BoardService service;
	
	//@PreAuthorize("isAuthenticated()")
	@PostMapping(value="/board/new", produces=MediaType.TEXT_PLAIN_VALUE)
	@Operation(summary="5.글 쓰기", description="제목과 내용을 입력해 글 작성")
	@ApiImplicitParams({
		@ApiImplicitParam(name="title", value="제목", required=true, dataTypeClass=String.class),
		@ApiImplicitParam(name="content", value="내용", required=true, dataTypeClass=String.class),
	})
	@ApiResponses({
		@ApiResponse(code=200, response=String.class, message="글을 읽을 주소"), 
		@ApiResponse(code=409, response=String.class, message="오류 메시지") 
	})
	public ResponseEntity<String> write(@Valid BoardDto.Write dto, BindingResult bidBindingResult,  @ApiIgnore Principal principal){
		Board board = service.write(dto, "spring"); //principal.getName()
		return ResponseEntity.ok("/board/read?bno=" + board.getBno());
}}