package com.bitstudy.app.service;


import com.bitstudy.app.dao.Ex12_BoardDao;
import com.bitstudy.app.dao.Ex14_CommentDao;
import com.bitstudy.app.domain.Ex14_CommentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class Ex14_CommentService {
    @Autowired
    Ex12_BoardDao boardDao;

    @Autowired
    Ex14_CommentDao commentDao;

//    public Ex14_CommentService(Ex12_BoardDao boardDao, Ex14_CommentDao commentDao) {
//        this.boardDao = boardDao;
//        this.commentDao = commentDao;
//    }
    
    // 해당 글의 댓글 수
    public int getCount(Integer bno) {
        return commentDao.count(bno);
    }


    /** @Transactional - 해당 메서드 동작중에 하나라도 예외가 생기면 다 롤백해버렷!! */
    @Transactional(rollbackFor = Exception.class)
    public int remove(Integer bno, Integer cno, String commenter) {
        // board 테이블에 있는 댓글수 -1 하기
        boardDao.updateCommentCount(bno, -1);
        return commentDao.delete(cno, commenter);
    }
    @Transactional(rollbackFor = Exception.class)
    public int write(Integer bno, Ex14_CommentDto commentDto) {
        boardDao.updateCommentCount(bno, 1);
        return commentDao.insert(commentDto);
    }

    //  댓글 리스트
    public List<Ex14_CommentDto> getList(Integer bno) throws Exception {

//        throw new Exception("test");

        return commentDao.selectAll(bno);

    }

    // 댓글 하나
    public Ex14_CommentDto read(Integer cno) {
        return commentDao.select(cno);
    }

    public int modify(Ex14_CommentDto commentDto) {
        return commentDao.update(commentDto);
    }
}

















