package com.bitstudy.app.service;

/* 할일: 검색관련 (맨 아래 메서드) 삽입  */

import com.bitstudy.app.dao.Ex05_BoardDao;
import com.bitstudy.app.dao.Ex12_BoardDao;
import com.bitstudy.app.domain.Ex02_BoardDto;
import com.bitstudy.app.domain.Ex12_SearchCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class Ex12_BoardService {
    @Autowired
    Ex12_BoardDao boardDao;
        public int getCount() {
        return boardDao.count();
    }
    /* 삭제 관련 */
    public int remove(Integer bno, String writer) {
        return boardDao.delete(bno, writer);
    }
    /* 쓰기 관련 */
    public int write(Ex02_BoardDto dto) {
        return boardDao.insert(dto);
    }

    // 게시글 하나 가져올때 조회수 1도 올리기   /* (글 불러오기)조회 1개 관련 */
    public Ex02_BoardDto read(Integer bno) {

//        boardDao.increaseViewCnt(bno);
//        return boardDao.select(bno);

        Ex02_BoardDto boardDto = boardDao.select(bno); // 게시글 1개 가져오고
        boardDao.increaseViewCnt(bno);

        boardDto.setView_cnt(boardDto.getView_cnt()+1);

        return boardDto;
    }
    /* (글 불러오기)특정 범위 내로 불러오기 */
    public List<Ex02_BoardDto> getPage(Map map) {
        return boardDao.selectPage(map);
    }
    /* 게시글 전체 불러오기 */
    public List<Ex02_BoardDto> getList() {
        return boardDao.selectAll();
    }

    /* 게시글 수정 */
    public int modify(Map map) {
        return boardDao.update(map);
    }
    public int modify2(Ex02_BoardDto boardDto) {
        return boardDao.update2(boardDto);
    }

    /**********************************************************/
    /* 검색 */
    public List<Ex02_BoardDto> getSearchResultPage(Ex12_SearchCondition sc) {
        return boardDao.searchSelectPage(sc);
    }
    public int getSearchResultCount(Ex12_SearchCondition sc) {
        return boardDao.searchSelectCount(sc);
    }


}














