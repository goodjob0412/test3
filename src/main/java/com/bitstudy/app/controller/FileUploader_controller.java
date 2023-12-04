package com.bitstudy.app.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;

@Controller
public class FileUploader_controller {

    @GetMapping("/upload")
    public String f_upload() {
        return "fileUpload";
    }

    private static final String F_PATH = "C:/Users/user1/Desktop/homepage/src/main/webapp/resources/img/";

    @PostMapping("/upload")
    public ModelAndView upload(@RequestParam(value="f_file", required = false) MultipartFile mf) {
        ModelAndView mv = new ModelAndView("fileUpload");
        String originalFileName = mf.getOriginalFilename();
        String safeFile = F_PATH + System.currentTimeMillis() + originalFileName;
        System.out.println("원래 파일명: " + originalFileName);
        try {
            mf.transferTo(new File(safeFile));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mv;
    }
}
