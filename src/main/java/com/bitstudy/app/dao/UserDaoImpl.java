package com.bitstudy.app.dao;

import com.bitstudy.app.domain.UserDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    DataSource ds;

    @Autowired
    UserDao userDao;

    @Autowired
    SqlSession session;
    String namespace="com.bitstudy.app.dao.userMapper.";

    // 1. 데이터베이스 연결
    // 2. sql문 생성
    // 3. sql문 실행
    // 4. 리소스 반납( close )
    @Override
    public void deleteAll() throws SQLException {
        // 1. 데이터베이스 연결
        Connection conn = ds.getConnection();
        // 2. sql문 생성
        String sql = "delete from user_info";
        // 3. sql문 실행
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.executeUpdate();
        // 4. 리소스 반납( close )
        pstmt.close();
        conn.close();

    }

    /* ************ insert 시작 ************ */
    @Override
    public int insertUser(UserDto userDto) {
        return session.insert(namespace+"insert" , userDto);

//        Connection conn = null;
//        PreparedStatement pstmt = null;
//
//        try {
//            // user -> DB에 넣기
//            // 1. 데이터베이스 연결
//            conn = ds.getConnection();
//
//            // 2. sql문 생성
//            String sql = "insert into user_info values (?,?,?,?,?,?, now())";
//
//            // 3. sql문 실행
//            pstmt = conn.prepareStatement(sql);
//            pstmt.setString(1, user.getId());
//            pstmt.setString(2, user.getPw());
//            pstmt.setString(3, user.getName());
//            pstmt.setString(4, user.getEmail());
//            pstmt.setDate(5, new java.sql.Date(user.getBirth().getTime()));
//            // date의 타입이 util.date 가 아니라 sql.date 라서 변환 필요
//            pstmt.setString(6, user.getSns());
//
//            int rowCount = pstmt.executeUpdate();
//            // select 할때는 executeQuery
//            // 나머지 할때는 executeUpdate
//
//
//            return rowCount;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } finally {
//            // 4. 리소스 반환
////            pstmt.close();
////            conn.close();
//
//            close(pstmt, conn);
//        }
    }
    /* ************ select 시작 ************ */

    @Override
    public UserDto selectUser(String uid) throws Exception {
        return session.selectOne(namespace+"select" , uid);

//        Connection conn = null;
//        PreparedStatement pstmt=null;
//        ResultSet rs=null;
//        try {
//            // 주어진 정보(id만 받는다고 가정)를 이용해서 DB에 해당 사용자 정보 싹 다 가져오기
//
//            // 1. 데이터베이스 연결
//            conn = ds.getConnection();
//
//            // 2. sql문 생성
//            String sql="select * from user_info where id=?";
//
//            // 3. sql문 실행
//            pstmt = conn.prepareStatement(sql);
//            pstmt.setString(1, uid);
//            rs = pstmt.executeQuery(); // select 일때는 executeQuery() 써야하고, 결과는 ResultSet 으로 넘어온다
//
//            // asdf2  , 1234  , 김개똥  , 김개똥@aaa.com  , 2023-11-02  , facebook  , 2023-11-02
//
//            if(rs.next()) {
//                UserDto user = new UserDto();
//                user.setId(rs.getString(1));
//                user.setPw(rs.getString(2));
//                user.setName(rs.getString(3));
//                user.setEmail(rs.getString(4));
//                user.setBirth(new Date(rs.getDate(5).getTime()));
//                user.setSns(rs.getString(6));
//                user.setJoinDate(new Date(rs.getDate(7).getTime()));
//
//                return user;
//            }
//
//            return null;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } finally {
//            // 4. 리소스 반납( close )
////            rs.close();
////            pstmt.close();
////            conn.close();
//
//            close(rs, pstmt, conn);
//
//        }
    }

    /* ************ update 시작 ************ */
    @Override
    public int updateUser(UserDto user) throws Exception {
        String sql="update user_info set pw=?, email=?, birth=?, sns=? where id=?";
        try(
                Connection conn = ds.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
        ){

            pstmt.setString(1, user.getPw());
            pstmt.setString(2, user.getEmail());
            pstmt.setDate(3, new java.sql.Date(user.getBirth().getTime()));
            pstmt.setString(4, user.getSns());
            pstmt.setString(5, user.getId());

            int rowCount = pstmt.executeUpdate();
            return rowCount;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /* ************ delete 시작 ************ */
    @Override
    public int deleteUser(String id) throws SQLException {
        Connection conn = ds.getConnection();
        String sql = "delete from user_info where id=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, id);
        int rowCount = pstmt.executeUpdate();
        pstmt.close();

        return rowCount;
    }

    // 가변인자. 매개변수로 몇개가 올진 모르겠지만 그 속성들을 배열처럼 받아서 저장
    private void close(AutoCloseable... acs) {
        for(AutoCloseable ac : acs) { // pstmt, conn
            try {
                if(ac != null) ac.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

}
