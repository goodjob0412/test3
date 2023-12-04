package com.bitstudy.app.dao;

import com.bitstudy.app.domain.Ex14_CommentDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* 할일: commentMapper.xml 에 있는 sql 문들 호출할 수 있게 메서드들 만들기 */
@Repository
public class Ex14_CommentDao {

    @Autowired
    SqlSession session;

    private String namespace="com.bitstudy.app.dao.commentMapper.";

    public int deleteAll(Integer bno) {
        return session.delete(namespace+"deleteAll", bno);
    }
    public int count(Integer bno) {
        return session.selectOne(namespace+"count", bno);
    }

    public int delete(Integer cno, String commenter) {
        Map map = new HashMap();
        map.put("cno",cno);
        map.put("commenter",commenter);

        return session.delete(namespace+"delete", map);
    }

    public int insert(Ex14_CommentDto commentDto) {
        return session.insert(namespace+"insert", commentDto);
    }

    public List<Ex14_CommentDto> selectAll(Integer bno) {
        return session.selectList(namespace+"selectAll", bno);
    }

    public Ex14_CommentDto select(Integer cno) {
        return session.selectOne(namespace+"select", cno);
    }

    public int update(Ex14_CommentDto commentDto) {
        return session.update(namespace+"update", commentDto);
    }





}
