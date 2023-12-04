package com.bitstudy.app.dao;

import com.bitstudy.app.domain.Ex02_BoardDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/* 할일: BoardDao에 있는 메서드들 테스트 하기
        1) Autowired로 BoardDao 끌어오기
        2) RunWith랑 ContextConfigration 넣기
        3) 테스트 끝나면 service 파일 작성 하러가기
*    */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class Ex05_BoardDaoTest {

    @Autowired
    Ex05_BoardDao boardDao;

    @Test // bno 라는 게시글 고유번호 보내서 글 정보(목록) 하나 가져오기
    public void select() {
        // boardDao 가 비어있는지 확인 (안비어 있으면 autowired로 제대로 끌어왔다는 뜻)
        assertTrue(boardDao != null);
        System.out.println("boardDao: " + boardDao);

        Ex02_BoardDto boardDto = boardDao.select(1);
        System.out.println("boardDto: " + boardDto);
        assertTrue(boardDto.getBno() == 1);
    }
    @Test
    public void count() {
        deleteAll();
        Ex02_BoardDto boardDto = new Ex02_BoardDto("수동 제목1", "수동 내용1", "asdf");
        assertTrue(boardDao.insert(boardDto) == 1);

        assertTrue(boardDao.count() == 1);

        boardDao.insert(boardDto);
        assertTrue(boardDao.count() == 2);
    }

    @Test
    public void deleteAll() {
        boardDao.deleteAll();

        Ex02_BoardDto boardDto = new Ex02_BoardDto("수동 제목1", "수동 내용1", "asdf");
        boardDao.insert(boardDto);
        boardDao.insert(boardDto);
        boardDao.insert(boardDto);

        assertTrue(boardDao.deleteAll() == 3);
    }

    @Test
    public void delete() {
        boardDao.deleteAll();

        Ex02_BoardDto boardDto = new Ex02_BoardDto("수동 제목1", "수동 내용1", "asdf");
        boardDao.insert(boardDto);
        boardDto = new Ex02_BoardDto("수동 제목2", "수동 내용2", "asdf2");
        boardDao.insert(boardDto);
    //--------------------------------------

        // 스스로 정보 불러와서
        // bno, writer 정보를 구해서 delete에 보내기
        int bno =boardDao.selectAll().get(0).getBno();
        System.out.println(":bno: " +bno);
        String writer =boardDao.selectAll().get(0).getWriter();
        assertTrue(boardDao.delete(bno, writer)==1);
    }

    @Test
    public void deleteForAdmin() {
        boardDao.deleteAll();

        Ex02_BoardDto boardDto = new Ex02_BoardDto("수동 제목1", "수동 내용1", "asdf");
        boardDao.insert(boardDto);
        boardDto = new Ex02_BoardDto("수동 제목2", "수동 내용2", "asdf2");
        boardDao.insert(boardDto);
        //--------------------------------------
        int bno =boardDao.selectAll().get(0).getBno();
        assertTrue(boardDao.deleteForAdmin(bno)==1);
    }

    @Test
    public void insert() {
        boardDao.deleteAll();

        Ex02_BoardDto boardDto = new Ex02_BoardDto("수동 제목10", "수동 내용10", "asdf");
        assertTrue(boardDao.insert(boardDto) == 1);

    }

    @Test
    public void selectAll() throws Exception {
        deleteAll();
        insert100();
//    ------------------------------------------
        List<Ex02_BoardDto> list = boardDao.selectAll();

        /* 혹시 한줄씩 찍어보고 싶으면 이거 사용...*/
        for(Ex02_BoardDto tmp : list) {
            System.out.println(tmp);
        }
        assertTrue(list.size() > 0);
    }

    @Test
    public void increaseViewCnt() throws Exception {
        deleteAll(); // 다 지우고

        // 하나만 생성
        Ex02_BoardDto boardDto = new Ex02_BoardDto("수동 제목10", "수동 내용10", "asdf");
        assertTrue(boardDao.insert(boardDto) == 1);

        // 최근 글 bno 찾아서 저장
        Integer bno = boardDao.selectAll().get(0).getBno();
        assertTrue(boardDao.increaseViewCnt(bno) == 1);


        boardDto = boardDao.select(bno);
        System.out.println(boardDto);
        assertTrue(boardDto != null);

    }

//////////////////////////////////////////////////
//    @Test
    // 100 줄 넣기
    public void insert100() throws Exception {
//        deleteAll();
        for(int i=0; i<50; i++) {
            Ex02_BoardDto boardDto = new Ex02_BoardDto("야매 제목"+i, "내용"+i, "asdf");
            boardDao.insert(boardDto);
        }
        for(int i=0; i<50; i++) {
            Ex02_BoardDto boardDto = new Ex02_BoardDto("title_"+i, "content"+i, "asdf2");
            boardDao.insert(boardDto);
        }
    }

}