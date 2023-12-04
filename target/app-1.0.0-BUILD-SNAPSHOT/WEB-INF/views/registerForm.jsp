<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page import="java.net.URLDecoder" %>

<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css" />
	<style>
		* {
			box-sizing: border-box;
		}
		form {
			width: 400px;
			height: 600px;
			display: flex;
			flex-direction: column;
			align-items: center;
			position: absolute;
			top: 50%;
			left: 50%;
			transform: translate(-50%, -50%);
			border: 1px solid rgb(89, 117, 196);
			border-radius: 10px;
		}
		.txt {
			width: 300px;
			height: 40px;
			border: 1px solid rgb(89, 117, 196);
			border-radius: 5px;
			padding: 0 10px;
			margin-bottom: 10px;
		}
		label {
			width: 300px;
			height: 30px;
			margin-top: 4px;
		}
		button {
			background-color: #eee8aa;
			color: #333;
			width: 300px;
			height: 50px;
			font-size: 17px;
			border: none;
			border-radius: 5px;
			margin: 20px 0 30px 0;
		}
		.title {
			font-size: 50px;
			margin: 40px 0 30px 0;
		}
		.msg {
			text-align: center;
			font-size: 16px;
			color: red;
			margin-bottom: 20px;
		}
		.sns-chk {
			margin-top: 5px;
		}
	</style>
	<title>Register</title>
</head>
<body>
	<%-- 경로도 언제든 바뀔수 있기 때문에 일반 파일명 쓰지 말고, jstl을 써서 바꾸기
		c:url이 context path를 자동으로 포함시키기 때문에 서버 소스를 수정하면 알아서 그 수정된 경로가 추가된다
	 --%>
	<!-- <form action="/app/Ex09_2_회원가입_뷰2.jsp" method="GET" onsubmit="return formCheck(this)"> -->
	<form action="<c:url value="/register/save" />" method="POST" onsubmit="return formCheck(this)">
		<div class="title">회원가입</div>
		<div id="msg" class="msg">모델 쓸때: ${ URLDecoder.decode(msg,"utf-8")  }</div>
		<div id="msg" class="msg">모델 안쓸때: ${ URLDecoder.decode(param.msg,"utf-8")  }</div>
		
		<label for="">아이디</label>
		<input class="txt" type="text" name="id" placeholder="8~12자리의 영대소문자와 숫자 조합" value="asdf" autofocus>
		<label for="">비밀번호</label>
		<input class="txt" type="text" id="pp" name="pw" placeholder="8~12자리의 영대소문자와 숫자 조합" value="1234">
		<label for="">이름</label>
		<input class="txt" type="text" name="name" value="홍길동1" placeholder="홍길동">
		<label for="">이메일</label>
		<input class="txt" type="text" name="email" value="example@bitstudy.co.kr" placeholder="example@bitstudy.co.kr">
		<label for="">생일</label>
		<input class="txt" type="text" name="birth" value="2020/12/31" placeholder="2020/12/31">
		<div class="sns-chk">
			<label><input type="checkbox" name="sns" value="facebook" checked />페이스북</label>
			<label><input type="checkbox" name="sns" value="kakaotalk" checked />카카오톡</label>
			<label><input type="checkbox" name="sns" value="instagram" checked />인스타그램</label>
		</div>
		<button>회원 가입</button>
	</form>
</body>
<script>
	function formCheck(frm) {
		var msg = ''; 
		if (frm.id.value.length < 3) {
				setMessage('id의 길이는 3이상이어야 합니다.', frm.id);
				return false;
		}  
		//if (frm.pp.value.length < 3) {
		if (frm.pw.value.length < 3) { 
			setMessage('pw의 길이는 3이상이어야 합니다.', frm.pw);
				return false;
		}
		return true;
	} 
		 
	function setMessage(msg, element) {
	<%--
		msg 인자를 받아서 빽틱 안에서 출력을 시도하면 동작 안함
		이유는 빽틱 내부에서 변수를 쓰기위해 ${} 를 썼지만(es6 방식) 
		EL 방식은 서버에서 돌아가는거고, es6 방식은 브라우저에서 돌아가는데
		서버가 먼저 동작하기 때문에 ${ msg } 라고 쓰면 이걸 서버측 EL 방식으로 인식한다. (${} 벗겨짐)
		그래서 ${} 내부에 있는 msg만 남아있게 되어 빽틱 안에서 msg가 일반 텍스트 취급을 받게 된다.
		한마디로 msg를 변수취급 하려면 ${} 한세트를 더 넣어주면 된다. => ${ ${msg} } 
		(이렇게 하면 안쪽 ${} 도 EL로 보니까 따옴표로 감싸자) =>  ${ '${msg}' }
	 --%> 
		document.getElementById("msg").innerHTML = `<i class="fa fa-exclamation-circle"> ${ '${msg}' } </i>`;
		if (element) {
				// 해당 요소로 focus
			element.select();
		}
	} 
</script>
</html>







