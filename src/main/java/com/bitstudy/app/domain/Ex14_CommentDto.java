package com.bitstudy.app.domain;

import java.util.Date;

/* 할일:
    1) DB에 있는 comment 테이블에 맞춰서 변수들 세팅하기
    2) getter / setter 만들기
    3) 생성자 만들기 (기본생성자, 커스텀생성자(bno, comment, commenter))
    4) toString 만들기
    5) Dao 만들러 가기
    
*   */
public class Ex14_CommentDto {
    private Integer cno;
    private Integer bno; // 현재 저장될 댓글 정보가 어떤 게시글에 딸려있는 댓글이지 알아보기 위한거
    private String comment;
    private String commenter;
    private Date reg_date;
    private Date up_date;

    public Ex14_CommentDto() {}

    public Ex14_CommentDto(Integer bno, String comment, String commenter) {
        this.bno = bno;
        this.comment = comment;
        this.commenter = commenter;
    }

    public Integer getCno() {
        return cno;
    }

    public void setCno(Integer cno) {
        this.cno = cno;
    }

    public Integer getBno() {
        return bno;
    }

    public void setBno(Integer bno) {
        this.bno = bno;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCommenter() {
        return commenter;
    }

    public void setCommenter(String commenter) {
        this.commenter = commenter;
    }

    public Date getReg_date() {
        return reg_date;
    }

    public void setReg_date(Date reg_date) {
        this.reg_date = reg_date;
    }

    public Date getUp_date() {
        return up_date;
    }

    public void setUp_date(Date up_date) {
        this.up_date = up_date;
    }

    @Override
    public String toString() {
        return "Ex14_CommentDto{" +
                "cno=" + cno +
                ", bno=" + bno +
                ", comment='" + comment + '\'' +
                ", commenter='" + commenter + '\'' +
                ", reg_date=" + reg_date +
                ", up_date=" + up_date +
                '}';
    }
}
