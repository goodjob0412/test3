<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%--<c:set var="logInOutLink" value="${ sessionScope.id==null ? '/login/login':'/login/logout' }" />--%>
<%--<c:set var="logInOutTxt" value="${ sessionScope.id==null ? 'login':'logout' }" />--%>
<%--<c:set var="userId" value="${ pageContext.request.getSession(false).getAttribute('id')==null ? '':pageContext.request.getSession(false).getAttribute('id') }" />--%>


<!-- 세션이 있으면 logout 이라고 찍고, 없으면 login 이라고 찍기 (이따가 아래쪽꺼도 할것)-->
<%-- <c:set var="logInOutLink" value="${ sessionScope.id==null ? '/login/login':'/login/logout29' }" />
<c:set var="logInOutTxt" value="${ sessionScope.id==null ? 'login':'logout' }" />
 --%>

<%@ page session="false" %><%-- 이 페이지에서는 세션을 새로 생성 안하겠다는 뜻 --%>
<%-- ** 주의 **
	session=false 일때 sessionScope와 pageContext.session 사용 불가!
	pageContext.request.getSession(false).getAttribute('id') 로 해야됨. 따옴표로!. 에러나도 무시!!
--%>
<c:set var="logInOutLink" value="${ pageContext.request.getSession(false).getAttribute('id')==null ? '/login/login':'/login/logout' }" />
<c:set var="logInOutTxt" value="${ pageContext.request.getSession(false).getAttribute('id')==null ? 'login':'logout' }" />
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
    <link rel="stylesheet" href="<c:url value='/css/board.css'/> ">

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
            <li class="item">
                <a href="<c:url value='${logInOutLink} '/>">${logInOutTxt}</a>
            </li>

            <c:choose>
                <c:when test="${userId==null || userId.equals('') }">
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
<div class="main">

    <%--    <jsp:include page="/WEB-INF/views/loginForm.jsp"/>--%>
    <%--    <%@ include file="/WEB-INF/views/loginForm.jsp" %>--%>
    <script>
        // Ex10 글쓰기 파일에서 넘어오는거
        if(msg=="WRT_ERR") { alert("글쓰기 실패")}
    </script>
    <div class="main">
        <div class="content_area">
            <h2>게시물 ${mode=="new"?"쓰기":"읽기"}</h2>

            <form action="" id="frm">
                <!-- readonly 는 boardList.jsp 에서 'Ex10_BoardController' 글쓰기 기능 만들기 전까지는 사용. -->
<%--                <input type="text" name="bno" value="${boardDto.bno}" readonly>--%>
<%--                <input type="text" name="title" value="${boardDto.title}" readonly>--%>
<%--                <textarea name="content" readonly>${boardDto.content}</textarea>--%>

                <!-- 'Ex10_BoardController' 부터 사용-->
                <input type="hidden" name="bno" value="${boardDto.bno}" readonly >
                <input type="text" name="title" id="title" value="${boardDto.title}" ${mode=="new"?"":"readonly"} >
                <img src="img/1699581651383Kontted.png" alt="">
                <textarea name="content" id="content" ${mode=="new"?"":"readonly"} >${boardDto.content}



                </textarea>

<%-- 파일 업로드--%>
<input type="file" name="f_file"  multiple="multiple">
<br /><br />

                <%--button 을 submit 으로 걸면 form 에 있는 name속성 있는 input 들의 값이 넘어간다--%>
                <c:if test="${ mode == 'new'}">
                    <button type="button"  class="btn" id="btn_write">글쓰기</button>
                </c:if>
                <c:if test="${ mode!='new' && boardDto.writer.equals(userId)  }">
                    <button type="button"  class="btn" id="btn_modify">수정</button>
                    <button type="button"  class="btn" id="btn_del">삭제</button>
                </c:if>

                <button type="button"  class="btn" id="btn_list">돌아가기</button>
            </form>

            <hr />

            <div id="comment_area" >
<%--                <form action="" id="reg_comment">--%>
<%--                    <input type="text" id="txt_comment" name="comment" placeholder="댓글 추가" >--%>
<%--                    <button type="button" id="btn_submit_comment">등록</button>--%>
<%--                </form>--%>
<%--                <div id="list_comment">--%>

               <jsp:include page="comment.jsp" flush="false"/>

                <%--page  : 포함할 JSP 페이지
                flush : 지정한 JSP 페이지를 실행하기 전에 출력 버퍼를 플러시 할지의 여부를 지정, false면 출력 버퍼를 플러시 하지 않는다.--%>
<%--                    <div class="list_item">--%>
<%--                        <div>--%>
<%--                            <div class="item_head">--%>
<%--                                <span>작성자</span>--%>
<%--                                <span>2020-11-25 05:42</span>--%>
<%--                            </div>--%>
<%--                            <div class="item_body">--%>
<%--                                <input type="text" class="comment_title" name="comment_title" value="댓글 내용" readonly >--%>
<%--                            </div>--%>
<%--                        </div>--%>
<%--                        <div>--%>
<%--                            <button type="button" class="btn_comment btn_comment_modify">수정</button>--%>
<%--                            <button type="button" class="btn_comment btn_comment_del">삭제</button>--%>
<%--                        </div>--%>
<%--                    </div>--%>

                </div>
            </div>


        </div>

        <script>
            $(document).ready(function(){
                // 댓글 등록
                $('#btn_submit_comment').on("click", function(){
                    <%--location.href = "<c:url value='/comments'/>?page=${page}&pageSize=${pageSize}&bno=${boardDto.bno}";--%>
                    alert("<c:url value='/comments'/>?page=${page}&pageSize=${pageSize}&bno=${boardDto.bno}")
                    location.href = "<c:url value='/comments'/>?bno=${boardDto.bno}";


                    let frm = $("#frm");
                    frm.attr('action', "<c:url value='/comments/add'/>?page=${page}&pageSize=${pageSize}");
                    frm.attr('method', 'post');
                    frm.submit();
                })


                // $('#btn_list').click(function(){
                $('#btn_list').on("click", function(){
                    <%--alert("<c:url value='/board/list'/>?page=${page}&pageSize=${pageSize}");--%>

                    // 이렇게 하면 get 방식으로 나감
                    location.href = "<c:url value='/board/list'/>?page=${page}&pageSize=${pageSize}";
                })

                $('#btn_del').on("click", function(){
                    // 이렇게 하면 get 방식으로 나감
                    // location.href = "<c:url value='/board/remove'/>?page=${page}&pageSize=${pageSize}";

                    if(!confirm("삭제하시겠습니까? ")) return;
<%--alert("<c:url value='/board/remove'/>?page=${page}&pageSize=${pageSize}");--%>
                    // 우리는 Post 방식으로 보내야함.
                    let frm = $("#frm");
                    frm.attr('action', "<c:url value='/board/remove'/>?"+getUri());
                    frm.attr('method', 'post');
                    frm.submit();
                })

                // 글쓰기
                $('#btn_write').on("click", function(){
                    if($('#title').val().trim()==""   ) {
                        alert("제목을 입력하시오");
                        $('#title').focus();
                    }
                    else if($('#content').val().trim()==""   ) {
                        alert("내용을 입력하시오");
                        $('#content').focus();
                    }
                    else {

                        // alert("<c:url value='/board/write'/>");
                        // 우리는 Post 방식으로 보내야함.
                        let frm = $("#frm");
                        frm.attr('action', "<c:url value='/board/write'/>");
                        frm.attr('method', 'post');
                        frm.attr('enctype','multipart/form-data')
                        frm.submit();
                    }
                })

                // url 파라미터 값들 구하기
                function getUri() {
                    let tmpParam="";
                    const searchParams = new URLSearchParams(location.search); // bno=673&page=2&pageSize=10&option=W&keyword=2
                    for(const param of searchParams) {
                        console.log(param);
                        tmpParam+=param[0]+"="+param[1]+"&";
                    }
                    return tmpParam;
                }

                $('#btn_modify').on("click", function(){
                    // 1. 읽기 상태면 수정 상태로 변경
                    let frm = $("#frm");

                    let isReadOnly = $('input[name="title"]').attr('readonly');
                    console.log("isReadOnly: " + isReadOnly)

                    if(isReadOnly == "readonly") {
                        $('input[name="title"]').attr('readonly',false);
                        $('textarea[name="content"]').attr('readonly',false);
                        $('#btn_modify').text("등록");
                        $('h2').text("게시물 수정");
                        return; // 버튼이 '수정' 상태일때는 글자만 바꾸고 readonly 였던것들 풀어주면 됨.
                        // 아래 코드들은 '수정' 버튼이 '등록' 버튼으로 바뀐 상태에서 '등록' 눌렀을때 동작해야함
                    }
<%--alert("<c:url value='/board/modify'/>?"+getUri());--%>
                    // 2. 수정 상태면, 수정된 내용 서버로 전송(업데이트)
                    frm.attr('action', "<c:url value='/board/modify'/>?"+getUri());
                    frm.attr('method', 'post');
                    frm.submit();
                })
            })
        </script>
    </div>
</body>
</html>