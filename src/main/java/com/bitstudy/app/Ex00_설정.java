package com.bitstudy.app;

/*
    1) pom.xml 설정
        - mvn repo 에서 MyBatis, My Batis Spring(2버전으로 받기) 받아서 pom에 넣기
    
    2) web.xml 파일에 한글필터 넣기 (이거 없으면 한글 깨짐)
    3) root-context.xml 파일 설정(아래 두개 설정하기)
        <bean id="sqlSessionFactory"
        <bean id="sqlSession"
    4) "mybatis-config.xml" 생성 (별칭파일 만들기)
        파일위치: main > resources 에 만들기
    5) sql mapper 파일 만들기 (여기에 모든 sql 문들 다 넣을거임)
        파일위치: main > resources > mapper 폴더 생성 > boardMapper.xml 만들기

    6) HomeController 대신 기본 화면 index로 나오게 설정(servlet-context.xml 설정)
        - servlet-context.xml 파일에서 <view-controller path="/" view-name="index" /> 삽입
        - HomeController.java 삭제 , home.jsp 삭제
        
    7) 각 view 파일을 복사해오기 (index. loginForm, registerForm, board)
    8) css도 복사해오기
    9) com.bitstudy.app 안에 controller, dao, domain(dto), service 폴더(패키지)들 만들기
    10) LoginController, UserDao, UserDto ch2 에서 복사 하고 각각 연결
    11) 톰캣 재설정 하기
 */
public class Ex00_설정 {
}
