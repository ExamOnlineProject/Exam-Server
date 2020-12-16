package com.zyg.exam.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyg.exam.common.DTO.ClassDTO;
import com.zyg.exam.common.JsonBean;
import com.zyg.exam.common.ResVO;
import com.zyg.exam.dao.ClassDao;
import com.zyg.exam.model.Class;
import com.zyg.exam.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ClassController {
    @Autowired
    private ClassService classService;
    @Autowired
    private ClassDao classDao;

    @RequestMapping("/selectStudentInClass")
    public JsonBean selectStudent(int classId){
        return classService.selectStudentInClass(classId);
    }

    //@GetMapping("/selectTeachInfo")
    //public JsonBean selectTeachInfo(int classId){
    //    return classService.selectTeachInfo(classId);
    //}

    @RequestMapping("/getClassList")
    public List<Class> listClass(){
        return classService.listClass();
    }

    @RequestMapping("/selectClass")
    public ResVO selectClass(ClassDTO classDTO){
        Page<Class> page = new Page<>(classDTO.getPageIndex(),classDTO.getPageSize());
        QueryWrapper<Class> queryWrapper = new QueryWrapper<>();
        Map<String, String> map = new HashMap<>();
        map.put("classname",classDTO.getClassname());
        map.remove("classname","null");
        if (classDTO.getClassname() != null && !classDTO.getClassname().isEmpty()) {
            queryWrapper.allEq(map);
        }
        IPage<Class> classList = classDao.selectPage(page,queryWrapper);
        return new ResVO(classList.getRecords(),classList.getTotal());
    }
    @RequestMapping("/deleteClass")
    public JsonBean deleteClass(int[] ids){
        return classService.deleteClass(ids);
    }
    @RequestMapping("/updateClass")
    public JsonBean updateClass(Class entity){
        return new JsonBean(200,Integer.toString(classDao.updateById(entity)),null);
    }
    @RequestMapping("/insertClass")
    public JsonBean insertClass(Class entity){
        return new JsonBean(200,Integer.toString(classDao.insert(entity)),null);
    }
}
