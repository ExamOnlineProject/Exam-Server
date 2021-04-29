package com.zyg.exam.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mysql.cj.util.Base64Decoder;
import com.qcloud.cos.model.ObjectMetadata;
import com.zyg.exam.common.DTO.QuestionDTO;
import com.zyg.exam.common.JsonBean;
import com.zyg.exam.common.ResVO;
import com.zyg.exam.model.Question;
import com.zyg.exam.service.QuestionService;
import com.zyg.exam.utils.COSUtil;
import com.zyg.exam.utils.FileNameUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Slf4j
@RestController
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @RequestMapping("/insertQuestion")
    public JsonBean insertQuestion(Question question){
        question.setContent(cutString(question.getContent()));
        question.setOption1(cutString(question.getOption1()));
        question.setOption2(cutString(question.getOption2()));
        question.setOption3(cutString(question.getOption3()));
        question.setOption4(cutString(question.getOption4()));
        return questionService.insertQuestion(question);
    }

    @RequestMapping("/deleteQuestion")
    public JsonBean deleteQuestion(int[] ids){
        return questionService.deleteQuestion(ids);
    }

    @RequestMapping("/selectQuestion")
    public ResVO selectByDifficulty(QuestionDTO questionDTO){
        //System.out.println(questionDTO);
        System.out.println(questionService.selectQuestion(questionDTO).getList());
        return questionService.selectQuestion(questionDTO);
    }

    @RequestMapping("/updateQuestion")
    public JsonBean updateQuestion(Question question){
        return questionService.updateQuestion(question);
    }

    @RequestMapping("/importQuestion")
    public JsonBean addQuestion(@RequestParam("file") MultipartFile file){
        JsonBean jsonBean=new JsonBean();
        String fileName = file.getOriginalFilename();
        log.info("{}",fileName);
        try {
            jsonBean=questionService.batchImport(fileName,file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  jsonBean;
    }
    public String cutString(String str){
        int e=0;
        e=str.indexOf("src");
        System.out.println(e);
        if(e==-1)
            return str;
        String string=str.substring(str.indexOf('"')+1);
        string=string.substring(0,string.indexOf('"'));
        return  string;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public JsonBean uploadImg(MultipartFile imageUrl) {

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(imageUrl.getSize());
        metadata.setContentType(imageUrl.getContentType());
        String name = FileNameUtil.getName(imageUrl);
        String realPath = null;
        try {
            realPath = COSUtil.upload("img/" + name, imageUrl.getInputStream(), metadata);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(imageUrl);
        JSONObject result = new JSONObject();
        result.put("imgUrl", realPath);

        return new JsonBean(200, "上传成功", result);
    }
}
