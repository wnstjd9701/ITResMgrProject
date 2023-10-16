package kr.co.kcc.itmgr.domain.ipinfo.model;

import java.util.List;
import java.util.Map;

import kr.co.kcc.itmgr.domain.installplace.model.InstallPlace;
import lombok.Data;

@Data
public class IpApiResponse{
	private String status;
    private int code;
    private String message;
    private Map<String,Object> data;
}
