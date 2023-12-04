package com.bitstudy.app.dao;

import com.bitstudy.app.domain.Ex02_BoardDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class Ex05_BoardDao {
    /*
        할일: 1) select 외의 다른 메서드들 만들기
             2) interface 만들기
             3) mapper > boardMapper.xml 완성하기
             4) TDD 하기
            
     */
    // CRUD를 하기 위해서 boardMapper.xml 에 있는 sql문들을 동작시켜야 하기 기때문 SqlSession 객체를 끌어와야한다.
    // SqlSession이 맵퍼 정보들과 DataSource 정보도 가지고 있어서 별도의 DataSource 를 @Autowired로 가져올 필요가 없다.
    @Autowired
    SqlSession session;

    String namespace="com.bitstudy.app.dao.boardMapper."; /* 주의: 마지막에 . 있음 */
    
    // 글 하나 불러오기
    public Ex02_BoardDto select(int bno) {
        return session.selectOne(namespace+"select", bno);
    }
    // 불러오면서 조회수 1씩 증가
    public int increaseViewCnt(Integer bno) {
        return session.update(namespace+"increaseViewCnt", bno);
    }

    
    public int count()  {
        return session.selectOne(namespace+"count");
    }


    /*************************************************/
    /*************     글 삭제 관련         ************/
    /*************************************************/
    public int deleteAll() {
        return session.delete(namespace+"deleteAll");
    }
    
    /* 글 하나 삭제 - 삭제시 글쓴이가 현재 로그인 한 사람인지 확인하는 용도로 writer 정보 필요. 글 하나 지워야 하니까 글 고유번호 필요 */
    public int delete(Integer bno, String writer) {
        Map map = new HashMap();
        map.put("bno", bno);
        map.put("writer", writer);

        return session.delete(namespace+"delete", map);
    }

    /* 관리자용 삭제 - 글번호만 필요*/
    public int deleteForAdmin(Integer bno) {
        return session.delete(namespace+"deleteForAdmin", bno);
    }
 
    /*************************************************/
    /*************     글 쓰기 관련         ************/
    /*************************************************/
    public int insert(Ex02_BoardDto dto) {
        return session.insert(namespace+"insert", dto);
    }

    /*************************************************/
    /*************     글 불러오기 관련      ************/
    /*************************************************/
    // 전체 불러오기
    public List<Ex02_BoardDto> selectAll() {
        return session.selectList(namespace+"selectAll");
    }

    public List<Ex02_BoardDto> selectPage(Map map) {
        return session.selectList(namespace+"selectPage", map);
    }

    public int update(Map map) {
        return session.update(namespace+"update", map);
    }
    public int update2(Ex02_BoardDto boardDto) {
        return session.update(namespace+"update2", boardDto);
    }

}



















