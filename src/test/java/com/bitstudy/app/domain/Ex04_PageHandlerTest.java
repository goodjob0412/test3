package com.bitstudy.app.domain;

import org.junit.Test;

import static org.junit.Assert.*;

public class Ex04_PageHandlerTest {
    @Test
    public void test1() {
        Ex04_PageHandler ph = new Ex04_PageHandler(221, 1);
        ph.print();

        assertTrue(ph.getBeginPage() == 1);
        assertTrue(ph.getEndPage() == 10);
    }
    @Test
    public void test2() {
        Ex04_PageHandler ph = new Ex04_PageHandler(250, 16);
        ph.print();

        assertTrue(ph.getBeginPage() == 11);
        assertTrue(ph.getEndPage() == 20);
    }
    @Test
    public void test3() {
        Ex04_PageHandler ph = new Ex04_PageHandler(253, 26);
        ph.print();

        assertTrue(ph.getBeginPage() == 21);
        assertTrue(ph.getEndPage() == 26);
    }
}