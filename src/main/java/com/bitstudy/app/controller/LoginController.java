package com.bitstudy.app.controller;

import com.bitstudy.app.dao.UserDao;
import com.bitstudy.app.domain.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;

@Controller
public class LoginController {


    @GetMapping("/login/login")
    public String loginForm(HttpSession session) {
        return "loginForm";
    }

    @GetMapping("/login/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 세션 즉시 삭제
        return "redirect:/"; // 삭제 후 메인페이지로 다시 이동
    }


    @Autowired
    UserDao userDao;

    @PostMapping("login/login")
    public String login(String id, String pw, boolean rememberId, Model m,
                        HttpServletResponse response,
                        HttpServletRequest request,
                        String prevPage) throws Exception {

        if(!loginChk(id, pw)) {
            // DB에 해당 회원 정보가 없는경우
            String msg = URLEncoder.encode("일치하는 회원정보가 없습니다.", "utf-8");
            m.addAttribute("msg",msg);
            m.addAttribute("prevPage",prevPage);
            return "redirect:/login/login?msg="+msg;
        }

        Cookie cookie = new Cookie("id", id); // 쿠키 생성
        if(rememberId) {
            cookie.setMaxAge(60 * 60 * 24 * 365 * 10); // 10년. 아예 안쓰면 브라우저 끄면 사라짐
            cookie.setPath("/");
        }
        else if(!rememberId) {
            cookie.setMaxAge(0);
            cookie.setPath("/");
        }
        response.addCookie(cookie); // 사용자에게 돌려보낼 '응답'에 쿠키 넣어서 보내기

        /* 세션 */
        HttpSession session = request.getSession();
        session.setAttribute("id", id);

        prevPage=prevPage.trim().replace(",", "");
        prevPage=(prevPage==null || ("").equals(prevPage))?"/":prevPage;
        return "redirect:"+prevPage;
    }

    private boolean loginChk(String id, String pw) throws Exception {
        UserDto user = userDao.selectUser(id);
        if(user==null) return false;
        return user.getPw().equals(pw);
    }

}