package com.zyg.exam.controller;

import com.zyg.exam.common.JsonBean;
import com.zyg.exam.service.MonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MonitorController {

    @Autowired
    private MonitorService monitorService;
    @RequestMapping("/getMonitorPaper")
    public JsonBean getMonitorPaper() {
        return monitorService.getMonitorPager();
    }
}
