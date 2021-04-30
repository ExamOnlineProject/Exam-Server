package com.zyg.exam.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.zyg.exam.common.DTO.PaperDTO;
import com.zyg.exam.model.Paper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface PaperDao extends BaseMapper<Paper> {
    int deleteByPrimaryKey(int[] paperids);

    Paper selectByPrimaryKey(Integer paperid);

    int updateByPrimaryKeySelective(Paper record);

    int updateByPrimaryKey(Paper record);

    List<List<Object>> selectPaper(PaperDTO paperDTO);

    List<List<Object>> selectByClass(PaperDTO paperDTO);

    List<List<Object>> selectQuestion(Map<String,Object> params);

    int  insertPaperQuestion(Integer questionid,Integer paperid);

}