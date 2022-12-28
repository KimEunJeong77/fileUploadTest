package com.example.demo.controller;

import java.io.File;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	@RequestMapping("/test")
	public String test() {
		log.info("========================== BoardController(/test) ==================================");
		return "test";
	}	
	
	@RequestMapping("/board-form")
	public ModelAndView main() {
		log.info("========================== BoardController(/board-form) ==================================");
		ModelAndView mv=new ModelAndView("/boardForm");
		return mv;
	}
	
	@RequestMapping("/board")
	public ModelAndView registerBoard(
			BoardDTO board, 
			MultipartHttpServletRequest multipartHttpServletRequest	
	) throws Exception {
		log.info("========================== BoardController(/board) ==================================");
		boardService.registerBoard(board);
		List<BoardFileDTO> list=fileUtils.parseFileInfo(
						board.getBoardIdx(), multipartHttpServletRequest
		);
		if(!CollectionUtils.isEmpty(list)) boardService.registerBoardFileList(list);
		return new ModelAndView("redirect:/board-list");
	}
	
	@RequestMapping("/board-list")
	public ModelAndView findBoardList() {
		log.info("========================== BoardController(/board-list) ==================================");
		ModelAndView mv=new ModelAndView("/boardList");
		List<BoardDTO> list=boardService.findBoardList();
		mv.addObject("list", list);
		return mv;
	}	
	
	@RequestMapping("/board-detail")
	public ModelAndView findBoardDetail(
			@RequestParam int boardIdx) {
		log.info("========================== BoardController(/board) ==================================");
		ModelAndView mv=new ModelAndView("/boardDetail");
		BoardDTO board=boardService.findBoardDetail(boardIdx);
		mv.addObject("board", board);
		return mv;
	}	
	
	@RequestMapping("/board/downloadBoardFile")
	public void downloadBoardFile(
			@RequestParam int idx, HttpServletResponse response) throws Exception {
		log.info("========================== BoardController(/board/downloadBoardFile) ==============");
		BoardFileDTO boardFile=boardService.findBoardFileDetail(idx);
		log.info("파일정보:"+boardFile);
		if(!ObjectUtils.isEmpty(boardFile)) {
			String fileName=boardFile.getFileName();
			byte[] file=org.apache.commons.io.FileUtils.readFileToByteArray(
					new File(boardFile.getFilePath()));
			response.setContentType("application/octet-stream");
			response.setContentLength(file.length);
			response.setHeader("Content-Disposition", "attachment; fileName=\""+
							URLEncoder.encode(fileName,"UTF-8")+"\";");
			response.setHeader("Content-Transfer-Encoding", "binary");
			OutputStream out=response.getOutputStream();
			out.write(file);
			out.flush();
			out.close();	
		}
	}		
}
