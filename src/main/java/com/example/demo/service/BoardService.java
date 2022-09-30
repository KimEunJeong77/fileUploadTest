package com.example.demo.service;

import java.util.List;
import com.example.demo.dto.BoardDTO;
import com.example.demo.dto.BoardFileDTO;

public interface BoardService {
	List<BoardDTO> selectBoardList();
	void insertBoard( BoardDTO dto ) throws Exception ; 
	void insertBoardFileList(List<BoardFileDTO> list) throws Exception ; 
}
