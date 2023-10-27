package kr.co.kcc.itmgr.domain.ipinfo.model;

import java.util.Map;
import lombok.Data;

@Data
public class IpApiResponse{
	private String status;
    private int code;
    private String message;
    private Map<String,Object> data;
}
