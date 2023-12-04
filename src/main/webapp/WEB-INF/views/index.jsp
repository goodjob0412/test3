<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%-- <c:set var="logInOutLink" value="${ sessionScope.SID==null?'/login/login':'/login/logout'}" />
<c:set var="logInOutTxt" value="${ sessionScope.SID==null?'login':'logout'}" /> --%>

<%@ page session="false" %> <%-- 이 페이지에서는 세션을 새로 생성 안하겠다 라는 뜻 --%>
<c:set var="logInOutLink" value="${ pageContext.request.getSession(false).getAttribute('id')==null?'/login/login':'/login/logout'}" />
<c:set var="logInOutTxt" value="${ pageContext.request.getSession(false).getAttribute('id')==null?'Login':'Logout'}" />
<c:set var="userId" value="${ pageContext.request.getSession(false).getAttribute('id')==null?'':pageContext.request.getSession(false).getAttribute('id')}" />


<!DOCTYPE html>
<html lang="en">
<head>
   <meta charset="UTF-8">
   <meta http-equiv="X-UA-Compatible" content="IE=edge">
   <meta name="viewport" content="width=device-width, initial-scale=1.0">
   <title>게시판 페이지</title>
   <link rel="stylesheet" href="<c:url value='/css/common.css'/>">
   <link rel="stylesheet" href="<c:url value='/css/h_f.css'/>">
   <link rel="stylesheet" href="<c:url value='/css/index.css'/>">
 
</head>
<body> 
   <div class="header">
      <div class="content_area">
         <div id="logo"><a href="<c:url value='/'/>">BITSTUDY</a></div>

         <ul class="menu">
            <li class="item">
               <a href="<c:url value='/'/>">Home</a>
            </li>
            <li class="item">
               <a href="<c:url value='/board/list'/>">Board</a>
            </li>
            <li class="item">
               <%-- <a href="<c:url value='/login/login'/>">login</a> --%>
               <a href="<c:url value='${ logInOutLink }' />">${ logInOutTxt }</a>
            </li> 
            <li class="item">
               <c:if test="${empty userId }">
                  <a href="<c:url value='/register/add'/>">Sign in</a>
               </c:if>
               <c:if test="${not empty userId }">
                  <a href="<c:url value=''/>">${userId}</a>
               </c:if>

            </li> 
         </ul> 
      </div>
   </div>
   <div class="main">
      <div class="content_area">
         <h1>메인 페이지 </h1>
      </div>
   </div>
</body>
</html>