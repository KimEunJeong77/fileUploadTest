package com.example.demo.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.common.FileUtils;
import com.example.demo.dto.BoardDTO;
import com.example.demo.dto.BoardFileDTO;
import com.example.demo.service.BoardService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class BoardController {
	
	@Autowired
	private BoardService boardService; 
	@Autowired
	private FileUtils fileUtils;
	
	@RequestMapping("/board-form")
	public ModelAndView main() {
		log.info("========================== BoardController(/board-form) ==================================");
		ModelAndView mv=new ModelAndView("/boardForm");
		return mv;
	}
	
	@RequestMapping("/board")
	public ModelAndView insertBoard(
			BoardDTO board, 
			MultipartHttpServletRequest multipartHttpServletRequest	
	) throws Exception {
		log.info("========================== BoardController(/board) ==================================");
		boardService.insertBoard(board);
		List<BoardFileDTO> list=fileUtils.parseFileInfo(
						board.getBoardIdx(), multipartHttpServletRequest
		);
		if(!CollectionUtils.isEmpty(list)) boardService.insertBoardFileList(list);
		return new ModelAndView("redirect:/board-list");
	}
	
	@RequestMapping("/board-list")
	public ModelAndView findBoardList() {
		log.info("========================== BoardController(/board-list) ==================================");
		ModelAndView mv=new ModelAndView("/boardList");
		List<BoardDTO> list=boardService.selectBoardList();
		mv.addObject("list", list);
		return mv;
	}	
}
