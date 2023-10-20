package kr.co.kcc.itmgr.domain.ipinfo.model;

import java.sql.Timestamp;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class IpInfo {
	// ipInfo
	@Schema(description = "IP 시리얼 번호" , example = "1")
	private int ipSn;
	
	@Schema(description = "IP 주소" , example = "192.168.0.1")
	@Pattern(regexp = "^\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}$", message = "유효하지 않은 IP 주소 형식")
	private String ip;
	
	//@NotBlank(message = "IP 설명을 입력하세요.")
	@Schema(description = "IP 설명" , example = "사용중인 IP입니다.")
	private String ipDesc;
	
	private Timestamp createDate;
	private String createrId;
	private Timestamp updateDate;
	private String updaterId;
	
	// commoncode
	@Schema(description = "공통 코드 그룹 ID" , example = "공통 코드의 그룹 ID")
    private String codeGroupId;
	@Schema(description = "상세 코드 ID" , example = "상세 코드의 ID")
	private String detailCode;
	@Schema(description = "상세 코드 이름" , example = "상세 코드의 이름")
	private String detailCodeName;
	
	private int rn;
}
