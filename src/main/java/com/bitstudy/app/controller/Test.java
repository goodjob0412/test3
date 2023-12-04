package com.bitstudy.app.controller;

import com.bitstudy.app.dao.Ex14_CommentDao;
import com.bitstudy.app.domain.Ex14_CommentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class Test {

    @Autowired
    Ex14_CommentDao commentDao;

    @GetMapping("/test")
    public String tt() {
        return "test";
    }


    @PostMapping("/test")
    @ResponseBody
    public ResponseEntity<List<Ex14_CommentDto>> list(Integer bno) {

        System.out.println(":bno: " + bno);

        try {
            List<Ex14_CommentDto> list = commentDao.selectAll(bno);

            return new ResponseEntity<List<Ex14_CommentDto>>(list , HttpStatus.OK);

        } catch (Exception e) {
//            throw new RuntimeException(e);
            e.printStackTrace();
            return new ResponseEntity<List<Ex14_CommentDto>> (HttpStatus.BAD_REQUEST); // 400
        }

    }
}
