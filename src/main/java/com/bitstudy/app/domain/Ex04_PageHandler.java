package com.bitstudy.app.domain;

public class Ex04_PageHandler {
    private int totalCount; // 총 게시글 수 (만약 221 개 이고)
    private int pageSize; // 한 페이지당 보여줄 게시글 개수 (한페이지당 게시글 10개)
    private int navSize = 10; // 한번에 몇 페이지 보여줄지 결정하는 개수 (한번에 총 10개 페이지. 아래쪽에 숫자로 붙는거)

    private int totalPage; // totalCount / navSize 하면 전체 페이지 개수 나옴
    private int page; // 현재 페이지
    private int beginPage; // 네비게이션의 첫번째 페이지
    private int endPage; // 네비게이션의 마지막번째 페이지

    private boolean showPrev; // [이전] 버튼 보여줄지 말지 결정
    private boolean showNext; // [다음] 버튼 보여줄지 말지 결정


    // 전체 필요한 페이지 개수 구하기
    public Ex04_PageHandler(int totalCount, int pageSize, int page) {
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.page = page;

        totalPage = (int) Math.ceil(totalCount / (double)pageSize) ;
        System.out.println("전체페이지 수: " + totalPage);
        
    /* 네비게이션 첫번째 페이지 구하기
       한번에 10개 페이지씩 보여준다고 했을때 내가 1번페이지를 선택했던 10번페이지를 선택했던
       지금 화면엔 나오는 beginPage 는 1 이어야 함(11부터 20까지일 beginPage는 11)

            (선택한 페이지-1) / 한번에 보여줄 페이지 개수 * 한번에 보여줄 페이지 개수 + 1
            (13-1) / 10 하면 1나온다
            1 * 10 하면 10 나온다
            근데 13페이지의 begin 은 11 이어야 하므로 1 더하면 된다.

            현재페이지  |  beginPage
                 5 -1   |    1    =>   0  +1 => 1
                15 -1   |    11   =>   10 +1 => 11
                12 -1   |    11   =>   10 +1 => 11
                25 -1   |    21   =>   20 +1 => 21

     */
        beginPage = (page - 1) / navSize * navSize + 1;
        endPage = Math.min((beginPage + navSize - 1), totalPage);

        showPrev = beginPage != 1;
        showNext = endPage != totalPage;
    }


    // TDD 할때 임의로 전체 게시글 개수(totalCount) 몇개고, 몇페이지 현재 보고 있는지 가짜로 보내서 구하기


    public Ex04_PageHandler(int totalCount, int page) {
        this(totalCount, 10, page );
    }

    public void print() {
        System.out.println("page: " + page);
        System.out.print(showPrev?"[이전]":"");
        for(int i= beginPage; i<=endPage; i++) {
            System.out.print("  " + i + "  ");
        }
        System.out.print(showNext?"[다음]":"");
    }

    @Override
    public String toString() {
        return "Ex04_PageHandler{" +
                "totalCount=" + totalCount +
                ", pageSize=" + pageSize +
                ", navSize=" + navSize +
                ", totalPage=" + totalPage +
                ", page=" + page +
                ", beginPage=" + beginPage +
                ", endPage=" + endPage +
                ", showPrev=" + showPrev +
                ", showNext=" + showNext +
                '}';
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getNavSize() {
        return navSize;
    }

    public void setNavSize(int navSize) {
        this.navSize = navSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getBeginPage() {
        return beginPage;
    }

    public void setBeginPage(int beginPage) {
        this.beginPage = beginPage;
    }

    public int getEndPage() {
        return endPage;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }

    public boolean isShowPrev() {
        return showPrev;
    }

    public void setShowPrev(boolean showPrev) {
        this.showPrev = showPrev;
    }

    public boolean isShowNext() {
        return showNext;
    }

    public void setShowNext(boolean showNext) {
        this.showNext = showNext;
    }
}















