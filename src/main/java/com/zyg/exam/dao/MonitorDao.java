package com.zyg.exam.dao;


import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MonitorDao {
    List<Integer> getMonitorPaperIds();
}
