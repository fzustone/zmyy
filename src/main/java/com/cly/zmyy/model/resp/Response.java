package com.cly.zmyy.model.resp;

import lombok.Data;

import java.util.List;

@Data
public class Response<T> {
    private List<T> list;

    private int status;

    private String notice;

    private String dragon;

    private String tiger;

    private String guid;
}
