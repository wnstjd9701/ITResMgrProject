package kr.co.kcc.itmgr.domain.ipinfo.model;

import java.sql.Timestamp;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class IpInfo {
	// ipInfo
	private int ipSn;
	
	@Pattern(regexp = "^\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}$", message = "유효하지 않은 IP 주소 형식")
	private String ip;
	
	//@NotBlank(message = "IP 설명을 입력하세요.")
	private String ipDesc;
	
	private Timestamp createDate;
	private String createrId;
	private Timestamp updateDate;
	private String updaterId;
	
	// commoncode
    private String codeGroupId;
	private String detailCode;
	private String detailCodeName;
	
	private int rn;
}
