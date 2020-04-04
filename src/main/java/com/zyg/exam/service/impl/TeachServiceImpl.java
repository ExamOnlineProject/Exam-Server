package com.zyg.exam.service.impl;

import com.zyg.exam.common.JsonBean;
import com.zyg.exam.dao.TeachDao;
import com.zyg.exam.model.Teach;
import com.zyg.exam.model.TeachInfo;
import com.zyg.exam.service.TeachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TeachServiceImpl implements TeachService {
    @Autowired
    private TeachDao teachDao;

    @Override
    public JsonBean listTeachInfo() {
        List<TeachInfo> teachInfos = teachDao.listTeachInfo();
        if (teachInfos.size()>0){
            return new JsonBean(HttpStatus.OK.value(),"",teachInfos);
        }else {
            return new JsonBean(500,"所查结果为空",null);
        }
    }

    @Override
    public JsonBean deleteTeach(int teachId) {
        int num = teachDao.deleteTeach(teachId);
        if (num>0){
            return new JsonBean(HttpStatus.OK.value(),"删除成功",null);
        }else {
            return new JsonBean(500,"删除失败",null);
        }
    }

    @Override
    public JsonBean insertTeach(Teach teach) {
        int num = teachDao.insertSelective(teach);
        if (num>0){
            return new JsonBean(HttpStatus.OK.value(),"添加成功",null);
        }else {
            return new JsonBean(500,"删除失败",null);
        }
    }

    @Override
    public JsonBean updateTeach(Teach teach) {
        int num = teachDao.updateByPrimaryKeySelective(teach);
        if (num>0){
            return new JsonBean(HttpStatus.OK.value(),"修改成功",null);
        }else {
            return new JsonBean(500,"修改失败",null);
        }
    }
}
