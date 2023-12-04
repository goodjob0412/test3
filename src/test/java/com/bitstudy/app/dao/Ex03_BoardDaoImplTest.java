package com.bitstudy.app.dao;

import com.bitstudy.app.domain.Ex02_BoardDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class Ex03_BoardDaoImplTest {

    @Autowired
    Ex01_BoardDao boardDao;

    @Test // bno 라는 게시글 고유번호 보내서 글 정보(목록) 하나 가져오기
    public void select() {
        assertTrue(boardDao != null);
        System.out.println("boardDao: " + boardDao);

        Ex02_BoardDto boardDto = boardDao.select(9);
        System.out.println("boardDto: " + boardDto);
        assertTrue(boardDto.getBno() == 9);
    }
}