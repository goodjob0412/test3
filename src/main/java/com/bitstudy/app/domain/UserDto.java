package com.bitstudy.app.domain;

import java.util.Date;

public class UserDto {
    private String id;
    private String pw;
    private String name;
    private String email;
    private Date birth;
    private String sns;
    private Date joinDate;


    public UserDto() {}

    public UserDto(String id, String pw, String name, String email, Date birth, String sns, Date joinDate) {
        this.id = id;
        this.pw = pw;
        this.name = name;
        this.email = email;
        this.birth = birth;
        this.sns = sns;
        this.joinDate = joinDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getSns() {
        return sns;
    }

    public void setSns(String sns) {
        this.sns = sns;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", pw='" + pw + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", birth=" + birth +
                ", sns='" + sns + '\'' +
                ", joinDate=" + joinDate +
                '}';
    }
}
