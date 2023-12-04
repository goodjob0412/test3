package com.bitstudy.app.domain;

import org.springframework.web.util.UriComponentsBuilder;

/* 검색시 필요한 값 : #{keyword}, #{offset}, #{pageSize}, ${option}, ${page}
*  */
public class Ex12_SearchCondition {
    private Integer page = 1;
    private Integer pageSize = 10;
    private Integer offset = 0;
    private String keyword = "";
    private String option = ""; // TC(제목+내용), T(제목), W(작성자),

    public Ex12_SearchCondition() {}
    public Ex12_SearchCondition(Integer page, Integer pageSize, String keyword, String option) {
        this.page = page;
        this.pageSize = pageSize;
        this.keyword = keyword;
        this.option = option;
    }

    /* 페이지 데이터 뽑는거
    * 검색하고 페이징부분 숫자 누르거나 글수정,삭제 눌렀을때 기존 검색 옵션들을 유지 하게 하기 위함 */
    public String getQueryString() {
        return getQueryString(page);
    }
    public String getQueryString(Integer page) {
        return UriComponentsBuilder.newInstance() // ?page=2&pageSize=10&option=W&keyword=2
                .queryParam("page",page) // 2
                .queryParam("pageSize",pageSize) // 10
                .queryParam("option",option) // W
                .queryParam("keyword",keyword) // 2
                .build().toString();
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        if(pageSize != null)
            this.pageSize = pageSize;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    @Override
    public String toString() {
        return "Ex12_SearchCondition{" +
                "page=" + page +
                ", pageSize=" + pageSize +
                ", offset=" + offset +
                ", keyword='" + keyword + '\'' +
                ", option='" + option + '\'' +
                '}';
    }
}
