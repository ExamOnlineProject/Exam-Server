package com.zyg.exam.controller;

import com.zyg.exam.common.DTO.PaperDTO;
import com.zyg.exam.common.JsonBean;
import com.zyg.exam.common.ResVO;
import com.zyg.exam.dao.PaperDao;
import com.zyg.exam.model.Paper;
import com.zyg.exam.service.PaperService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.sound.midi.Soundbank;

@Slf4j
@RestController
public class PaperController {
    @Autowired
    private PaperService paperService;
    @Autowired
    private PaperDao paperDao;

    @RequestMapping("/paperCount")
    public int count(){
        return paperDao.selectCount(null);
    }

    @RequestMapping("/getPaperList")
    public ResVO getPaperList(PaperDTO paperDTO){
        System.out.println(paperDTO);
        return paperService.selectPaper(paperDTO);
    }

    @RequestMapping("/deletePaper")
    public JsonBean deletePaper(int[] ids){
        return paperService.deletePaper(ids);
    }

    @RequestMapping("/listQuestion")
    public ResVO selectQuestion(Integer paperId, Integer pageIndex, Integer pageSize){
        return paperService.selectQuestion(paperId,pageIndex,pageSize);
    }

    @RequestMapping("/updatePaper")
    public JsonBean updatePaper(Paper paper){
        System.out.println(paper.toString()+"******************");
        return paperService.updatePaper(paper);
    }

    @RequestMapping("selectQuestionByPaper")
    public JsonBean listQuestions(Integer paperid){
        return paperService.selectQuestions(paperid);
    }
}
