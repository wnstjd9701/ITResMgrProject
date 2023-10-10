package kr.co.kcc.itmgr.global.common;

import java.util.List;

import lombok.Data;

@Data
public class Response<T> {
    private int code;
    private String message;
    private List<T> data;
}