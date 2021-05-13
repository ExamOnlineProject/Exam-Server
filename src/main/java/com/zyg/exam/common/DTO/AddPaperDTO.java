package com.zyg.exam.common.DTO;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
public class AddPaperDTO {
    private Integer courseid;
    private Integer pattern;
    private String ismonitor;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date starttime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date finishtime;
    private List<StrategyDTO> strategyDTOS; // 存储条件策略
    private String papername;
}
