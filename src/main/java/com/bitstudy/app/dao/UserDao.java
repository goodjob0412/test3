package com.bitstudy.app.dao;

import com.bitstudy.app.domain.UserDto;

import java.sql.SQLException;

public interface UserDao {
    void deleteAll() throws SQLException;

    int insertUser(UserDto user);

    UserDto selectUser(String uid) throws Exception;

    int updateUser(UserDto user) throws Exception;

    int deleteUser(String id) throws SQLException;
}
