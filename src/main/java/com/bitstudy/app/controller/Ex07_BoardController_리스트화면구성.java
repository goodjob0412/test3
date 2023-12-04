package com.bitstudy.app.controller;

/* 할거: 게시판 화면 만들기 (전체 리스트 구성)
* 
*   views 폴더에 boardList.jsp 파일 필요
*
*   - 게시판 화면에서 보여줘야 할거
*   1. 게시글
*   2. pageHandler
*   3. 현재 보고있는 페이지 번호
* 
* 위 세개중에 1번은 TDD > Ex05에서 selectAll 에서 한것처럼 싹 다 가져와서 찍어주면 됨
* 2,3 번은 페이지 정보가 필요하기 때문에 domain > Ex04 필요
*
*  */

import com.bitstudy.app.domain.Ex02_BoardDto;
import com.bitstudy.app.domain.Ex04_PageHandler;
import com.bitstudy.app.service.Ex06_BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/board7")
public class Ex07_BoardController_리스트화면구성 {
    @Autowired
    Ex06_BoardService boardService;


    @GetMapping("/list")
    /*
        1. 일단 사용자가 게시판 페이지로 들어올때 "현재페이지" 와 "한 페이지당 보여줄 페이지 개수" 를 컨트롤러에서 받아야 하기 때문에 GetMapping 부분에서 매개변수로 page와 pageSize 를 받는다
        2. page, pageSize 를 받아서 Model에 담아서 뷰로 넘겨준다
     */
    public String list(Integer page, Integer pageSize, Model m, HttpServletRequest request) {
        // 로그인 했는지 파악
        if(!loginChk(request)) {
            return "redirect:/login/login?prevPage="+request.getRequestURL();
        }

        if(page == null) page = 1;
        if(pageSize == null) pageSize = 10;
        System.out.println("page: " + page + " , pageSize: " +pageSize);

        // 총 게시글 개수 구해서 페이징 계산
        int totalCount = boardService.getCount(); // 총 게시글 개수 구하기
        Ex04_PageHandler ph = new Ex04_PageHandler(totalCount, pageSize, page);
        System.out.println("ph: " +ph);

        // boardMapper.xml  >  id="selectPage" 에 map을 보내서 몇번쨰부터 몇개 글을 가져올건지 정하기
        Map map = new HashMap();
        map.put("offset", (page-1)*pageSize);
        map.put("pageSize", pageSize);
        List<Ex02_BoardDto> list = boardService.getPage(map);

        System.out.println(list);

        m.addAttribute("ph",ph);
        m.addAttribute("list",list);

        return "boardList";
    }

    private boolean loginChk(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return session.getAttribute("id")!=null;
    }


}
















