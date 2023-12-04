package com.bitstudy.app.service;

/* 할일: 서비스 계층 만들기   
        다 하고 interface 만들기

*   서비스계층이란: 컨트롤러에서 받은 값들을 이용해서 연산을 하거나 DAO로 DTO를 이용해서 정보의 전달을 해주거다 함. */

import com.bitstudy.app.dao.Ex05_BoardDao;
import com.bitstudy.app.domain.Ex02_BoardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class Ex06_BoardServiceImpl implements Ex06_BoardService {
    @Autowired
    Ex05_BoardDao boardDao;

    @Override
    public int getCount() {
        return boardDao.count();
    }

    @Override /* 삭제 관련 */
    public int remove(Integer bno, String writer) {
        return boardDao.delete(bno, writer);
    }

    @Override /* 쓰기 관련 */
    public int write(Ex02_BoardDto dto) {
        return boardDao.insert(dto);
    }

    // 게시글 하나 가져올때 조회수 1도 올리기
    @Override /* (글 불러오기)조회 1개 관련 */
    public Ex02_BoardDto read(Integer bno) {

//        boardDao.increaseViewCnt(bno);
//        return boardDao.select(bno);

        Ex02_BoardDto boardDto = boardDao.select(bno); // 게시글 1개 가져오고
        boardDao.increaseViewCnt(bno);

        boardDto.setView_cnt(boardDto.getView_cnt()+1);

        return boardDto;
    }

    @Override /* (글 불러오기)특정 범위 내로 불러오기 */
    public List<Ex02_BoardDto> getPage(Map map) {
        return boardDao.selectPage(map);
    }

    @Override /* 게시글 전체 불러오기 */
    public List<Ex02_BoardDto> getList() {
        return boardDao.selectAll();
    }


    @Override /* 게시글 수정 */
    public int modify(Map map) {
        return boardDao.update(map);
    }
    public int modify2(Ex02_BoardDto boardDto) {
        return boardDao.update2(boardDto);
    }
}
