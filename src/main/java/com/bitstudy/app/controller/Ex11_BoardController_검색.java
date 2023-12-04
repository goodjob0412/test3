package com.bitstudy.app.controller;

/* 할거: 검색 기능 만들기 (3~4 개 파일을 같이 만들어야함)

    1) boardMapper.xml 에 검색 관련 쿼리 만들기
    2) domain > Ex12_SearchCondition 만들기
    3) dao > Ex12_BoardDao (interface 만들기)
    4) TDD 하기
    5) service > Ex12_BoardService
*  */
    

import com.bitstudy.app.domain.Ex02_BoardDto;
import com.bitstudy.app.domain.Ex04_PageHandler;
import com.bitstudy.app.domain.Ex12_SearchCondition;
import com.bitstudy.app.service.Ex06_BoardService;
import com.bitstudy.app.service.Ex12_BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/board")
public class Ex11_BoardController_검색 {
    @Autowired
    Ex12_BoardService boardService;



    /* 글 수정 */
    @PostMapping("/modify")
    public String modify(Ex02_BoardDto boardDto, HttpSession session, /*Integer page, Integer pageSize*/ Ex12_SearchCondition sc , Model model ) {
        String currUserId = (String) session.getAttribute("id");

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

//        model.addAttribute("page", sc.getPage());
//        model.addAttribute("pageSize", sc.getPageSize());


        return "redirect:/board/list"+sc.getQueryString();
    }

    
    /* 글쓰기 폼 불러오기 */
    @GetMapping("/write")
    public String write(Model model) {
        model.addAttribute("mode","new");
        return "board";
    }

    /* 글쓰기 DB에 저장 */
    private static final String F_PATH = "C:/Users/user1/Desktop/homepage/src/main/webapp/resources/img/";
    @PostMapping("/write")
    /* borad.jsp의 '글쓰기' 누르면 DB에 제목, 본문내용, 작성자, 현재시간 등을 등록하는 메서드 */
    public String write(@RequestParam(value="f_file", required = false) MultipartFile mf, Ex02_BoardDto boardDto, HttpSession session, RedirectAttributes rattr) {

        // 세션에 있는 현재 로그인 되어있는 아이디 불러오기
        String currUserId = (String) session.getAttribute("id");
        boardDto.setWriter(currUserId); // DTO에 현재 작성자 정보 넣기

        boardService.write(boardDto); // DB에 저장

        rattr.addFlashAttribute("msg", "WRT_OK");

        /* 파일 업로드 */
        String originalFileName = mf.getOriginalFilename();
        String safeFile = F_PATH + System.currentTimeMillis() + originalFileName;
System.out.println("originalFileName: " + originalFileName);
System.out.println("safeFile: " + safeFile);
        try {
            mf.transferTo(new File(safeFile));
        } catch (Exception e) {
            e.printStackTrace();
        }


        return "redirect:/board/list";
    }

    /* 삭제 */
    @PostMapping("/remove")
    public String remove(Integer bno, /*Integer page, Integer pageSize*/ Ex12_SearchCondition sc,/* Model model,*/ HttpSession session
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
        return "redirect:/board/list"+sc.getQueryString();
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


    /* 글쓰기 전체 불러오기 & 검색 */
    @GetMapping("/list")
    /*
        1. 일단 사용자가 게시판 페이지로 들어올때 "현재페이지" 와 "한 페이지당 보여줄 페이지 개수" 를 컨트롤러에서 받아야 하기 때문에 GetMapping 부분에서 매개변수로 page와 pageSize 를 받는다
        2. page, pageSize 를 받아서 Model에 담아서 뷰로 넘겨준다
     */
//    public String list(Integer page, Integer pageSize, Model m, HttpServletRequest request) {
    public String list(Ex12_SearchCondition sc, Model m, HttpServletRequest request) {
System.out.println("sc: " +sc);
        // 로그인 했는지 파악
        if(!loginChk(request)) {
            return "redirect:/login/login?prevPage="+request.getRequestURL();
        }

        try {
    /* 변경 - 아예 안씀. 왜냐면 이미 sc에 기본값으로 담겨 있어서 여기서 따로 할 필요 없음 */
//            if(page == null) page = 1;
//            if(pageSize == null) pageSize = 10;
//            System.out.println("page: " + page + " , pageSize: " +pageSize);

            // 총 게시글 개수 구해서 페이징 계산
   /* 변경 *///int totalCount = boardService.getCount(); // 총 게시글 개수 구하기

            int totalCount = boardService.getSearchResultCount(sc);
System.out.println("totalCount: " +totalCount);
System.out.println("sc: " +sc);

    /* 변경 *///Ex04_PageHandler ph = new Ex04_PageHandler(totalCount, pageSize, page);
            Ex04_PageHandler ph = new Ex04_PageHandler(totalCount, sc.getPageSize(), sc.getPage());
System.out.println("ph: " +ph);

            // boardMapper.xml  >  id="selectPage" 에 map을 보내서 몇번쨰부터 몇개 글을 가져올건지 정하기
    /* 변경 */// Map map = new HashMap();
//            map.put("offset", (page-1)*pageSize);
//            map.put("pageSize", pageSize);
//            List<Ex02_BoardDto> list = boardService.getPage(map);

            int offset = (sc.getPage()-1)*sc.getPageSize();
System.out.println("sc1: " + sc);

            sc.setOffset(offset);
System.out.println("sc2: " + sc);
            List<Ex02_BoardDto> list = boardService.getSearchResultPage(sc);
System.out.println("list: "+ list);

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
















