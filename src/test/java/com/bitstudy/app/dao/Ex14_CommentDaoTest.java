package com.bitstudy.app.dao;

import com.bitstudy.app.domain.Ex14_CommentDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class Ex14_CommentDaoTest {

    @Autowired
    Ex14_CommentDao commentDao;

    @Test
    public void count() {
        commentDao.deleteAll(1);
        assertTrue( commentDao.count(1) == 0  );

        Ex14_CommentDto commentDto = new Ex14_CommentDto(1, "댓글1","asdf");
        assertTrue(commentDao.insert(commentDto) == 1);

        assertTrue(commentDao.count(1) == 1);
    }

    @Test
    public void delete() {
        // DB에 1번 글에 달린 댓글들 싹 다 지우고
        commentDao.deleteAll(1);
        // 새로 1번글의 댓글 하나만 넣기
//        Ex14_CommentDto commentDto = new Ex14_CommentDto(1, "댓글1","asdf");
//        commentDao.insert(commentDto);
//
//        int cno = commentDao.selectAll(1).get(0).getCno();
//
//        // 해당 글 하나 삭제하기
//        int rowCount = commentDao.delete(cno,"asdf");
//        assertTrue(rowCount == 1);
    }

    @Test
    public void insert() {

//        assertTrue(commentDao.deleteAll(1) > 0);
        commentDao.deleteAll(1);


        Ex14_CommentDto commentDto = new Ex14_CommentDto(1, "댓글1","asdf");
        assertTrue(commentDao.insert(commentDto) == 1);
    }

    @Test
    public void selectAll() {
        // 다 삭제
        commentDao.deleteAll(1);

        // 두개만 넣고
        Ex14_CommentDto commentDto = new Ex14_CommentDto(1, "댓글1","asdf");
        commentDao.insert(commentDto);
        commentDto = new Ex14_CommentDto(1, "댓글2","asdf11");
        commentDao.insert(commentDto);

        List<Ex14_CommentDto> list = commentDao.selectAll(1);
        assertTrue(list.size() ==2);
    }

    @Test
    public void select() {
//        commentDao.deleteAll(1);
//        Ex14_CommentDto commentDto = new Ex14_CommentDto(1, "댓글1","asdf");
//        commentDao.insert(commentDto);

        Ex14_CommentDto commentDto = commentDao.select(68);
        System.out.println(commentDto);
        assertTrue(commentDto.getCno() == 68);
    }

    @Test
    public void update() {
        commentDao.deleteAll(1);
        Ex14_CommentDto commentDto = new Ex14_CommentDto(1, "댓글1","asdf");
        commentDao.insert(commentDto);

        List<Ex14_CommentDto> list = commentDao.selectAll(1);
        commentDto.setCno(list.get(0).getCno());
        commentDto.setComment("11111");
System.out.println("commentDto: " +commentDto);
//        assertTrue(commentDao.update(commentDto) == 1);

        commentDao.update(commentDto);
        assertTrue(list.get(0).getCommenter().equals("asdf") );
    }

//--------------------------------------------
    @Test
    public void insert100() {
//        commentDao.deleteAll(1);

        for(int i=1; i<22; i++) {
            Ex14_CommentDto commentDto = new Ex14_CommentDto(684, "댓글"+i, "asdf");
            commentDao.insert(commentDto);
        }
    }


}