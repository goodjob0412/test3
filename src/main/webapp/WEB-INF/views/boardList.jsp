<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="logInOutLink" value="${ sessionScope.id==null ? '/login/login':'/login/logout' }" />
<c:set var="logInOutTxt" value="${ sessionScope.id==null ? 'login':'logout' }" />
<c:set var="userId" value="${ pageContext.request.getSession(false).getAttribute('id')==null ? '':pageContext.request.getSession(false).getAttribute('id') }" />

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>게시판 페이지</title>
    <link rel="stylesheet" href="<c:url value='/css/common.css'/> ">
    <link rel="stylesheet" href="<c:url value='/css/h_f.css'/> ">
    <link rel="stylesheet" href="<c:url value='/css/boardList.css'/> ">

    <!-- https://code.jquery.com/jquery-1.12.4.min.js 부분에 마우스 가져다 대면 라이브러리 다운로드 할건지 물어봄.
     하게되면 jquery 다운받아서 좀 더 빠르게 사용할수 있음. 하면 External Libraries 폴더에 jquery 라이브러리 생김 -->
    <script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
</head>
<body>
<div class="header">
    <div class="content_area">
        <div id="logo">
            <a href="<c:url value='/'/>">BITSTUDY</a>
        </div>
        <ul class="menu">
            <li class="item">
                <a href="<c:url value='/'/>">Home</a>
            </li>
            <li class="item">
                <a href="<c:url value='/board/list'/>">Board</a>
            </li>
            <%-- <li class="item">
               <a href="<c:url value='/login/login'/>">login</a>
            </li>  --%>
            <li class="item">
                <a href="<c:url value='${logInOutLink} '/>">${logInOutTxt}</a>
            </li>

            <c:choose>
                <c:when test="${userId==null}">
                    <li class="item">
                        <a href="<c:url value='/register/add'/>">Sign in</a>
                    </li>
                </c:when>

                <c:otherwise>
                    <li class="item">
                            ${userId} 님
                    </li>
                </c:otherwise>
            </c:choose>
        </ul>
    </div>
</div>
<script>

    //let msg = "${param.msg}" // 모델 사용할때. // get 방식일떈 param. 을 붙여야함
    let msg = "${msg}" // RedirectAttributes 사용할떄 // get 방식일떈 param. 을 붙여야함
    // alert(1);
    // alert(msg);
    // alert(2);
    if(msg=="del_ok") { alert("삭제되었습니다.") }
    if(msg=="del_err") { alert("요청이 실패하였습니다.") }

    // Ex10 글쓰기 파일에서 넘어오는거 (여기서 실패 메세지는 필요 없음. 어짜피 board.jsp 로 가버릴테니까)
    if(msg=="WRT_OK") { alert("글쓰기 성공")}
    // Ex10 에서 넘어오는거
    if(msg=="NO_LIST") { alert("해당 게시물이 존재하지 않습니다..")}
</script>


<div class="main">
    <div class="content_area">
        <h2>게시판 리스트</h2>

        <div class="search_area" name="search_area">
            <select id="sel_filter">
                <option value="TC" ${ param.option=="TC"?"selected":"" }>제목+내용</option>
                <option value="T" ${ param.option=="T"?"selected":"" }>제목만</option>
                <option value="W" ${ param.option=="W"?"selected":"" }>작성자</option>
            </select>

            <%--            <form id="search_box">--%>
            <div id="search_box">
                <input type="text" id="txt_search" name="txt_search" placeholder="검색어를 입력하세요" value="${param.keyword}">
                <input type="button" value="검색" id="btn_search" name="btn_search">
            </div>
            <%--            </form>--%>
            <div id="btn_write" onclick="location.href='<c:url value="/board/write"/>'">글쓰기</div>

        </div>

        <%--        검색 결과: ${list.size()}개--%>
        <%--<c:if test="${list.size() == 0}">
        <c:if test="${empty list}">
            <h1>일치하는 결과가 없습니다.</h1>
        </c:if>--%>
        <c:choose>
            <c:when test="${empty list}">
                <h1>일치하는 결과가 없습니다.</h1>
            </c:when>
            <c:otherwise>
                <table>
                    <thead>
                    <tr>
                        <th class="no">번호</th>
                        <th class="title">제목</th>
                        <th class="writer">이름</th>
                        <th class="regdate">등록일</th>
                        <th class="viewcnt">조회수</th>
                    </tr>
                    </thead>
                    <tbody>
        <c:forEach var="boardDto" items="${list}">
            <tr class="boardBody">
                <td class="no">${boardDto.bno}</td>
                <td class="title">
                    <a href="<c:url value="/board/read?bno=${boardDto.bno}&page=${ph.page}&pageSize=${ph.pageSize}&option=${param.option}&keyword=${param.keyword}"/>">${boardDto.title}</a>
                </td>
                <td class="writer">${boardDto.writer}</td>
                <td class="regdate"><fmt:formatDate value="${boardDto.reg_date}" pattern="yyyy-MM-dd" type="date"/></td>
                <td class="viewcnt">${boardDto.view_cnt}</td>
            </tr>
        </c:forEach>

                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
        <div class="pagination">
            <c:if test="${ph.showPrev}">
                <a href="<c:url value='/board/list?page=${ph.beginPage-1}&pageSize=${ph.pageSize}' />" class="beginPage">[이전]</a>
            </c:if>
            <c:forEach var="i" begin="${ph.beginPage}" end="${ph.endPage}">
                <a href="<c:url value='/board/list?page=${i}&pageSize=${ph.pageSize}&option=${param.option}&keyword=${param.keyword}' /> " class="page ${i==ph.page?"pageActive":""}"> ${i}</a>
            </c:forEach>
            <c:if test="${ph.showNext}">
                <a href="<c:url value='/board/list?page=${ph.endPage+1}&pageSize=${ph.pageSize}' />" class="endPage">[다음]</a>
            </c:if>
        </div>
    </div>

    <script>
        $(document).ready(function(){

            // url 파라미터 값들 구하기
            function getUri() {
                let tmpParam="";
                const searchParams = new URLSearchParams(location.search);
                for(const param of searchParams) {
                    console.log(param);
                    tmpParam+=param[0]+"="+param[1]+"&";
                }
                return tmpParam;
            }

            $('#btn_search').on("click", function(){
                <%--alert("<c:url value='/board/list'/>?page=${page}&pageSize=${pageSize}");--%>

                location.href = "<c:url value='/board/list'/>?page=1&pageSize=${pageSize}&option="+$('#sel_filter').val()+"&keyword="+$('#txt_search').val().trim();

                /* keyword 와 option 은 SearchCondition 의 이름과 맞춰줘야 한다. */
            })
            $('#txt_search').on('keydown', function(e){
                if(e.keyCode==13 && $('#btn_search').val().trim().length > 0) {
                    $('#btn_search').trigger('click');
                }
            });
        })

    </script>
</div>
</body>
</html>