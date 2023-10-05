package kr.co.kcc.itmgr.domain.home.model;

import lombok.Data;

@Data
public class ServerStatusResponse {
    private int code;
    private String status;
    private String message;
    private boolean serverStatus;
}
