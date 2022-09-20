package com.dimka228.web2.beans;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class RawBean {
    private Deque<Raw> raws;

    public RawBean() {
        raws = new LinkedList<>();
    }

    public Deque<Raw> getRaws() {
        return raws;
    }
}
