package com.bitstudy.app.service;

import com.bitstudy.app.domain.Ex02_BoardDto;

import java.util.List;
import java.util.Map;

public interface Ex06_BoardService {
    int getCount();

    int remove(Integer bno, String writer);

    int write(Ex02_BoardDto dto);

    // 게시글 하나 가져올때 조회수 1도 올리기
    Ex02_BoardDto read(Integer bno);


    List<Ex02_BoardDto> getList();
    List<Ex02_BoardDto> getPage(Map map);

    int modify(Map map);
    int modify2(Ex02_BoardDto boardDto);
}
