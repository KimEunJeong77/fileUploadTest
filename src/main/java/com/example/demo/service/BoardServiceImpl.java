package com.example.demo.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.dto.BoardDTO;
import com.example.demo.dto.BoardFileDTO;
import com.example.demo.mapper.BoardMapper;


@Service
public class BoardServiceImpl implements BoardService{

	@Autowired
	private BoardMapper boardMapper;
	@Override
	public void insertBoard(BoardDTO board) throws Exception {
		// TODO Auto-generated method stub
		boardMapper.insertBoard(board);// 쿼리실행뒤 boardIdx설정
	}
	@Override
	public List<BoardDTO> selectBoardList() {
		// TODO Auto-generated method stub
		return boardMapper.selectBoardList();
	}
	@Override
	public void insertBoardFileList(List<BoardFileDTO> list) throws Exception {
		// TODO Auto-generated method stub
		for(BoardFileDTO boardFile:list)
			boardMapper.insertBoardFile(boardFile);
	}
}
