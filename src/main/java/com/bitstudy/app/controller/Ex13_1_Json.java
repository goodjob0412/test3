package com.bitstudy.app.controller;

/*
1. Json 이란
    Rest API 와 Ajax 를 사용하기 위해서 json(Java String Object Notation) 이란 자바스크립트 객체 표기법이 필요하다.
    XML 대신에 간단한 자바스크립트 객체 표기법을 쓰자 라고 해서 만들어짐.

    json 방식: {속성명1:속성값1, 속성명2:속성명2} 이런식

    위 방식을 이용하면서 객체를 여러개 넣으려면 배열처럼 넣으면 된다.
        1) 객체 배열의 경우: [{속성명1:속성값1, 속성명2:속성명2}] (이미 theComma때 해봤다.)
        2) map 의 경우: { 키:{ 속성명1:속성값1, 속성명2:속성명2 }, 키2:{속성명1:속성값1, 속성명2:속성명2} }

2. json으로 서버와 데이터 주고 받는 방법
    직렬화:  json 방식으로 JS 객체를 서버로 전송할때 사용
            사용법: JSON.stringify()
            문자열로 변환.
            객체가 저장될때 메모리에 한칸한칸씩 (변수처럼) 별도로 나뉘어서 저장되는데
            서버로 전송을 위해서 문자열 한줄로 변환한다.(HTTP가 텍스트 기반이기 때문임). 이걸 직렬화 라고 한다.
            {id:"asdf",pw:"1234",name:"홍길동" .... } 이걸 '{"id":"asdf","pw":"1234","name":"홍길동" .... }' 이렇게 바꿔줌

    역직렬화: 서버가 보낸 데이터를 json 방식으로 변환할때 사용
            사용법: JSON.parse()
            직렬화의 반대로 작업함. (문자열을 나눠서 각 객체로 변환)
             '{"id":"asdf","pw":"1234","name":"홍길동" .... }' 이걸 {id:"asdf",pw:"1234",name:"홍길동" .... }  이렇게 바꿔줌

     ** JSON 예시 보여주기
        1) 크롬 개발자 도구 > 콘솔창에서 다음 입력
            - let obj={id:"asdf",pw:"1234",name:"홍길동"}
            - obj  (이거 입력하면 {id: 'asdf', pw: '1234', name: '홍길동'} 이렇게 나올거임) 현재 JS 객체 상태
            - let obj2=JSON.stringify(obj)
            - obj2 (이거 입력하면 '{"id":"asdf","pw":"1234","name":"홍길동"}' 이렇게 나옴) 문자열 방식으로 바뀐 상태임

3. Ajax (비동기 방식으로 데이터를 주고 받을수 있는 기술)
            Asynchronous javascript and XML (옛날에 만들어져서 끝에 XML 붙지만 요즘은 XML 대신 JSON으로 대체됨)
         웹페이지 전체(Date + UI) 가 아닌 일부만(Data) 업데이트 가능

4. REST란
    리소스 중심으로 API 디자인 - HTTP 메서드로 수행할 작업을 정의함
                             ex) GET, POST
                                 PUT - upload
                                 DELETE - 파일삭제
                                 PATCH - 수정
    일반 방식
        작업 |         URI           | HTTP메서드  |   설명
        읽기 | /comment/read?cno=번호 | GET       | 지정된 번호의 댓글을 보여줌
        쓰기 | /comment/write        | POST      | 작성한 게시물을 저장함
        삭제 | /comment/remove       | POST      | 댓글 삭제
        수정 | /comment/modify       | POST      | 게시물 수정


    Restful 방식 - URI 랑 HTTP메서드 부분이 다름
        작업 |         URI          | HTTP메서드   |   설명
        읽기 | /comments            | GET         | 모든 댓글을 보여줌
        읽기 | /comment/{번호}       | GET         | 지정된 번호의 댓글을 보여줌
        쓰기 | /comments            | POST        | 작성한 게시물을 저장함
        삭제 | /comment/{번호}       | DELETE      | 지정된 번호의 댓글 삭제
        수정 | /comment/{번호}       | PUT / PATCH | 게시물 수정
-------------------------------------------------------------------------------

 ** ajax 사용하려면 라이브러리 필요함
    1) MVN repo 사이트 가기
    2) jackson databind 검색
    3) 최신버전 긁어오기
    4) pom.xml 에 넣고 업뎃

*/
public class Ex13_1_Json {
}
