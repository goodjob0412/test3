package com.bitstudy.app.dao;

import com.bitstudy.app.domain.Ex02_BoardDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class Ex01_BoardDaoImpl implements Ex01_BoardDao {
    /*
        할일: boardMapper.xml 파일에 있는 select문을 동작시킬거임
            1) BoardDao 만들고 interface 만들기
            2) 일단 selectOne 만 동작시켜서 테스트 돌려보기
            
     */
    // CRUD를 하기 위해서 boardMapper.xml 에 있는 sql문들을 동작시켜야 하기 기때문 SqlSession 객체를 끌어와야한다.
    // SqlSession이 맵퍼 정보들과 DataSource 정보도 가지고 있어서 별도의 DataSource 를 @Autowired로 가져올 필요가 없다.
    @Autowired
    SqlSession session;

    String namespace="com.bitstudy.app.dao.boardMapper."; /* 주의: 마지막에 . 있음 */
    @Override
    public Ex02_BoardDto select(int bno) {
        return session.selectOne(namespace+"select", bno);
    }
}
