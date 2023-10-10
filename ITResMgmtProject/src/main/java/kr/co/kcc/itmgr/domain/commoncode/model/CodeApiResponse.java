package kr.co.kcc.itmgr.domain.commoncode.model;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CodeApiResponse {
	private String status;
    private int code;
    private String message;
    private Map<String,Object> data;
}
