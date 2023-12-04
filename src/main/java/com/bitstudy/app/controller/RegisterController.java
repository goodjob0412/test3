package com.bitstudy.app.controller;

import com.bitstudy.app.dao.UserDao;
import com.bitstudy.app.domain.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.net.URLEncoder;

/* 할거: 회원가입 폼이랑 연결
        회원가입 컨트롤러 생성(dao를 이용해서 실제 로그인 구현)
        select, insert

 */
@Controller
public class RegisterController {

    @Autowired
    UserDao userDao;
//    Ex03_UserDaoImpl userDaoImpl;

    @GetMapping("/register/add")
    public String add() {
        return "registerForm";
    }

    @PostMapping("/register/save")
    public String save(UserDto user, Model m) throws Exception {

        System.out.println("user: " + user);

        // 1. 유효성 검사 - 해당 아이디가 DB에 있는지 확인
        if (!isVaild(user)) {
            
            String msg = "사용중인 아이디 입니다";
            System.out.println(msg);

            /* 인코딩 - import 시켜야함*/
            msg = URLEncoder.encode(msg, "utf-8");
            m.addAttribute("msg", msg);

//			return "error";
            return "redirect:/register/add";
//			return "forward:/register/add";
        }
        else {
            /* 회원가입 시키기 */
            userDao.insertUser(user);
        }



        return "redirect:/";
    }

    private boolean isVaild(UserDto user) throws Exception {
        UserDto user2 = userDao.selectUser(user.getId());
        if(user2!=null) return false; // null 이 아니면 이미 있는 id 라는 뜻이니까 false 리턴되야함

        return true;
    }
}