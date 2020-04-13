package com.zyg.exam.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * paper
 * @author 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Paper implements Serializable {
    /**
     * 试卷id
     */
    private Integer paperid;

    /**
     * 开始时间
     */
    private Date starttime;

    /**
     * 结束时间
     */
    private Date finishtime;

    /**
     * 1:限通信模式,2:霸屏模式
     */
    private Integer pattern;

    /**
     * 是否开启监控
     */
    private String ismonitor;

    /**
     * 课程id
     */
    private Integer courseid;

    private static final long serialVersionUID = 1L;


}