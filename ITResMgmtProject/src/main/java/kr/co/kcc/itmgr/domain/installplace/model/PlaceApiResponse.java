package kr.co.kcc.itmgr.domain.installplace.model;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class PlaceApiResponse {
    private String status;
    private int code;
    private String message;
    private List<InstallPlace> data;
    private Map<String,Object> paging;
}