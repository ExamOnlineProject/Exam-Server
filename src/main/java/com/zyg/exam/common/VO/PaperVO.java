package com.zyg.exam.common.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaperVO {
    /**
     * 试卷id
     */
    private Integer paperId;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 1:限通信模式,2:霸屏模式
     */
    private Integer pattern;

    /**
     * 是否开启监控
     */
    private String isMonitor;

    /**
     * 课程名称
     */
    private String courseName;
    /**
     * 结束时间
     */
    private Date finishTime;

    private Integer courseid;

    private Integer classid;
    private String papername;
}
