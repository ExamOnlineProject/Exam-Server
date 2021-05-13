package com.zyg.exam.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zyg.exam.common.VO.OptionVO;
import com.zyg.exam.dao.*;
import com.zyg.exam.model.*;
import com.zyg.exam.model.Class;
import com.zyg.exam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class OptionController {
    @Autowired
    private UserService userService;
    @Autowired
    private CourseDao courseDao;
    @Autowired
    private ClassDao classDao;
    @Autowired
    private ChapterDao chapterDao;
    @Autowired
    private TeachDao teachDao;
    @Autowired
    private QuestionDao questionDao;

    @RequestMapping("/teacherOption")
    public List<OptionVO> teacherOption(Integer id){
        List<OptionVO> list = new ArrayList<>();

        if (id != null) {
            // TODO 根据courseid 查找teacher 的 OptionVO
            list=teachDao.selectTeacher(id);
            return list;
        }

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role",2);
        for (User user: userService.list(queryWrapper)) {
            OptionVO optionVO = new OptionVO(user.getId(),user.getName());
            list.add(optionVO);
        }
        return list;
    }
    @RequestMapping("/courseOption")
    public List<OptionVO> courseOption(Integer id){
        List<OptionVO> list = new ArrayList<>();
        if (id != null){
            // TODO 根据 teacherid 查询 所教课程 的 OptionVO
            list=courseDao.selectCourse(id);
            return list;
        }

        for (Course course: courseDao.selectList(null)) {
            OptionVO optionVO = new OptionVO(course.getCourseid(),course.getCoursename());
            list.add(optionVO);
        }
        return list;
    }
    @RequestMapping("/chapterOption")
    public List<OptionVO> chapterOption(Integer id){
        List<OptionVO> list = new ArrayList<>();
        QueryWrapper<Chapter> queryWrapper = null;
        if (id != null){
            queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("courseid",id);
        }
        for (Chapter chapter: chapterDao.selectList(queryWrapper.select("chapterid","chaptername"))) {
            OptionVO optionVO = new OptionVO(chapter.getChapterid(),chapter.getChaptername());
            list.add(optionVO);
        }
        return list;
    }
    @RequestMapping("/classOption")
    public List<OptionVO> classOption(Integer id){
        List<OptionVO> list = new ArrayList<>();
        for (Class entity: classDao.selectList(null)) {
            OptionVO optionVO = new OptionVO(entity.getClassid(),entity.getClassname());
            list.add(optionVO);
        }
        return list;
    }
    @RequestMapping("/teachOption")
    public List<OptionVO> teachOption(){
        List<OptionVO> list = teachDao.optionTeachInfo();
        return list;
    }

    @RequestMapping("/questionOption")
    public List<OptionVO> questionOption(Integer id,String type){
        List<OptionVO> list = new ArrayList<>();
        QueryWrapper<Question> queryWrapper = null;
        if (id != null){
            queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("chapterid",id);
            queryWrapper.eq("type",type);
        }
        for (Question question: questionDao.selectList(queryWrapper.select("subjectid","content"))) {
            OptionVO optionVO = new OptionVO(question.getSubjectid(),question.getContent());
            list.add(optionVO);
        }
        return list;
    }
}
