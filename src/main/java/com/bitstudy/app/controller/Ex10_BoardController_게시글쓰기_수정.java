package com.bitstudy.app.controller;

/* 할거: 게시글 쓰기, 수정 기능 만들기
    주의: 게시글 쓰기 화면은 '제목', '내용' 부분으로 구성될거임
        이미 글하나 읽기 할때 board.jsp 의 form 안에 다 readonly 써놨는데
        쓰기일때는 mode=new 라는 값을 만들어서 new일때는 readonly 들이 없어지게 할거임

    글쓰기 할때는 메서드 두개 필요함.
        1) 글 쓸 수 있는 빈 폼 불러주는 메서드 (get)
        2) 글 다 쓴 다음에 '글쓰기' 버튼 눌러서 (post) 형태로 넘기는 메서드
*  */
    

import com.bitstudy.app.domain.Ex02_BoardDto;
import com.bitstudy.app.domain.Ex04_PageHandler;
import com.bitstudy.app.service.Ex06_BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/board10")
public class Ex10_BoardController_게시글쓰기_수정 {
    @Autowired
    Ex06_BoardService boardService;

    /* 글 수정 */
    @PostMapping("/modify")
    public String modify(Ex02_BoardDto boardDto, HttpSession session, Integer page, Integer pageSize, Model model ) {
        String currUserId = (String) session.getAttribute("id");

        System.out.println(boardDto);

        // 방법1. Map 으로 한 경우
//        Map map = new HashMap();
//        map.put("title",boardDto.getTitle());
//        map.put("content",boardDto.getContent());
//        map.put("bno",boardDto.getBno());
//        map.put("writer",currUserId);
//        boardService.modify(map);
        
        // 방법2. 기존 저장소(DTO) 사용
        boardDto.setWriter(currUserId);
        boardService.modify2(boardDto);

        model.addAttribute("page", page);
        model.addAttribute("pageSize", pageSize);


        return "redirect:/board/list";
    }

    
    /* 글쓰기 폼 불러오기 */
    @GetMapping("/write")
    public String write(Model model) {
        model.addAttribute("mode","new");
        return "board";
    }

    /* 글쓰기 DB에 저장 */
    @PostMapping("/write")
    /* borad.jsp의 '글쓰기' 누르면 DB에 제목, 본문내용, 작성자, 현재시간 등을 등록하는 메서드 */
    public String write(Ex02_BoardDto boardDto, HttpSession session, RedirectAttributes rattr) {

        // 세션에 있는 현재 로그인 되어있는 아이디 불러오기
        String currUserId = (String) session.getAttribute("id");
        boardDto.setWriter(currUserId); // DTO에 현재 작성자 정보 넣기

        boardService.write(boardDto); // DB에 저장

        rattr.addFlashAttribute("msg", "WRT_OK");
        return "redirect:/board/list";
    }

    /* 삭제 */
    @PostMapping("/remove")
    public String remove(Integer bno, Integer page, Integer pageSize,/* Model model,*/ HttpSession session
    , RedirectAttributes rattr) {
        /** RedirectAttributes 
         * 메세징 띄워줄때 사용 가능. model이랑 비슷함. 
         * Model은 계속 남아있는 정보, RedirectAttributes 는 일회성 정보
         * */

        try {
//            model.addAttribute("page", page);
//            model.addAttribute("pageSize", pageSize);

            // 세션에 있는 현재 로그인 되어있는 아이디 불러와서 DB에서 삭제하려는 글의 아이디랑 비교하기
            String currUserId = (String) session.getAttribute("id");

            int rowCnt = boardService.remove(bno, currUserId);
//            if(rowCnt != 1) {
//                throw new Exception("삭제 에러");
//            }

//            model.addAttribute("msg","del_ok");
            rattr.addFlashAttribute("msg","del_ok");
            
            System.out.println("삭제 결과: " + rowCnt);
        } catch (Exception e) {
//            throw new RuntimeException(e);

//            model.addAttribute("msg","del_err");
            rattr.addFlashAttribute("msg","del_err");
        }


//        return "redirect:/board/list";
        return "redirect:/board/list?page="+page+"&pageSize="+pageSize;
    }


    /* 글 1개 조회 */
    @GetMapping("/read")
    public String read(Integer bno, Integer page, Integer pageSize, Model model) {
        try {
            Ex02_BoardDto boardDto = boardService.read(bno);
            // bno번글 불러와서 board.jsp(뷰) 화면에 보여줄거니깐 일단 BoardDto 형으로 저장

            model.addAttribute("boardDto", boardDto);
            model.addAttribute("page", page);
            model.addAttribute("pageSize", pageSize);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return "board";
    }


    /* 글쓰기 전체 불러오기 */
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

        try {
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

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "boardList";
    }

    private boolean loginChk(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return session.getAttribute("id")!=null;
    }


}
















