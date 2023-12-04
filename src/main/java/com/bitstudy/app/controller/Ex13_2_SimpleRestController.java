package com.bitstudy.app.controller;

import com.bitstudy.app.domain.Person;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
/*
    - ajax 사용하려면 라이브러리 필요함
        1) MVN repo 사이트 가기
        2) jackson databind 검색
        3) 최신버전 긁어오기
        4) pom.xml 에 넣고 업뎃

    - @ResponseBody , @RequestBody
        스크링에서 비동기 처리 하는경우  @ResponseBody , @RequestBody 사용
        
        @RequestBody를 이용해서 HTTP방식(텍스트방식)을 자바 객체로 변환해서 서버로 전송
        @ResponseBody를 이용해서 자바 객체를 HTTP방식(텍스트방식) 으로 매핑해서 클라이언트한테 전송
 */
@Controller
public class Ex13_2_SimpleRestController {
    @GetMapping("/ajax13")
    public String ajax() {
        return "ajax";
    }


    @PostMapping("/send13")
    @ResponseBody /* 서버에서 p(자바객체)를 사용자한테 보낼건데 텍스트방식 으로 전환해서 보내라는 뜻  */
    public Person test(@RequestBody Person p) {
        System.out.println("p= "+p);
        p.setName("ABC");
        p.setAge(p.getAge()+10);
        return p;
    }
}












