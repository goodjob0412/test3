package com.bitstudy.app.controller;

import com.bitstudy.app.domain.Ex14_CommentDto;
import com.bitstudy.app.service.Ex14_CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class Ex14_CommentController {

    @Autowired
    Ex14_CommentService service;


    // 해당 게시글의 댓글 전체 불러오기
    @GetMapping("/comments")
    @ResponseBody
    public ResponseEntity<List<Ex14_CommentDto>> list(Integer bno) {
        try {
            List<Ex14_CommentDto> list = service.getList(bno);
            return new ResponseEntity<List<Ex14_CommentDto>> (list, HttpStatus.OK); // 200
        } catch (Exception e) {
//            throw new RuntimeException(e);
            e.printStackTrace();
            return new ResponseEntity<List<Ex14_CommentDto>> (HttpStatus.BAD_REQUEST); // 400
        }
        /* ResponseEntity - 응답이나 요청시 전송할 정보를 보낼 대상에게 상태코드를 설정 */

    }

    /* 댓글 등록 */
    @PostMapping("/comments")
    public ResponseEntity<String> write(@RequestBody Ex14_CommentDto commentDto, Integer bno, HttpSession session) {

        try {
            // 현재 로그인 한 유저 아이디 저장(DTO에 담을 용도)
            String commenter = (String)session.getAttribute("id");

            commentDto.setCommenter(commenter);
            commentDto.setBno(bno);

            if(service.write(bno, commentDto) != 1) {
                throw new Exception("실패");
            }

            return new ResponseEntity<String> ("댓글 등록 성공", HttpStatus.OK); // 200
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String> ("댓글 등록 실패", HttpStatus.BAD_REQUEST); // 400
        }
    }

    /* 수정 - comment.jsp 에서 댓글 '등록' 누르고 왔을때 */
    @PatchMapping("/comments/{cno}")
    public ResponseEntity<String> modify(@PathVariable Integer cno, @RequestBody Ex14_CommentDto dto, HttpSession session ) {
        try {
            String commenter = (String)session.getAttribute("id");

            dto.setCommenter(commenter);
            dto.setCno(cno);

            if(service.modify(dto) != 1) {
                throw new Exception("실패");
            }

            return new ResponseEntity<String> ("댓글 수정 성공", HttpStatus.OK); // 200
            
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String> ("댓글 수정 실패", HttpStatus.BAD_REQUEST); // 400
        }


    }
    
    
    /* 삭제 */
    @DeleteMapping("/comments/{cno}")
    /* (Restful API) {cno} 처럼 맵핑된 URL의 일부 값을 가져올 때는 @PathVariable를 붙인다.   */
//    @ResponseBody
    public ResponseEntity<String> remove(@PathVariable Integer cno, Integer bno, HttpSession session) {
        try {
            String commenter = (String)session.getAttribute("id");

            if(service.remove(bno, cno, commenter) != 1) {
                throw new Exception("실패");
            }
            return new ResponseEntity<String> ("댓글 삭제 성공", HttpStatus.OK); // 200
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String> ("댓글 삭제 실패", HttpStatus.BAD_REQUEST); // 400
        }
 
    }






}
