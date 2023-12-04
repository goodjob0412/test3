package com.bitstudy.app.dao;

import com.bitstudy.app.domain.Ex02_BoardDto;
import com.bitstudy.app.domain.Ex12_SearchCondition;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class Ex12_BoardDao {
    /*
        할일: 검색기능 (맨 아래)
            
     */
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


    /*************************************************/
    /*************       검색 관련         ************/
    /*************************************************/
    public List<Ex02_BoardDto> searchSelectPage(Ex12_SearchCondition sc) {
        return session.selectList(namespace+"searchSelectPage",sc);
    }

    public int searchSelectCount(Ex12_SearchCondition sc) {
        return session.selectOne(namespace+"searchSelectCount",sc);
    }
    /*************************************************/
    /*************       댓글 관련         ************/
    /*************************************************/
    // 댓글 써지면 comment 테이블에 댓글 정보 삽입 후
    // board 테이블의 조회수를 +1 / -1 해줘야함
    public int updateCommentCount(Integer bno, int count) {
        Map map = new HashMap();
        map.put("bno",bno);
        map.put("count",count);

        return session.update(namespace+"updateCommentCount", map);

    }
}



















