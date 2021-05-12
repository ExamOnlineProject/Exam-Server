package com.zyg.exam.service.impl;

import com.zyg.exam.common.JsonBean;
import com.zyg.exam.dao.MonitorDao;
import com.zyg.exam.dao.PaperDao;
import com.zyg.exam.model.Paper;
import com.zyg.exam.service.MonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MonitorServiceImpl implements MonitorService {

    @Autowired
    private PaperDao paperDao;

    @Autowired
    private MonitorDao monitorDao;

    @Override
    public JsonBean getMonitorPager() {
        List<Integer> paperIds = monitorDao.getMonitorPaperIds();
        List<Paper> papers = new ArrayList<>();
        for (int id : paperIds) {
            Paper paper = paperDao.selectByPrimaryKey(id);
            papers.add(paper);
        }
        return new JsonBean(200, "查询成功", papers);
    }
}
