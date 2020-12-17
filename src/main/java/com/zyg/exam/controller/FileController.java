package com.zyg.exam.controller;

import com.zyg.exam.dao.AnswerDao;
import com.zyg.exam.dao.RecordDao;
import com.zyg.exam.model.Record;
import com.zyg.exam.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

@RestController
public class FileController {
    @Autowired
    private RecordDao recordDao;
    @Autowired
    private AnswerDao answerDao;

    @Autowired
    private FileService fileService;



    String oPath = System.getProperty("user.dir");
//    String serverPath = "http://localhost:8088/";
    String localPath = "D:\\uploadImg\\";
    //
    //String localPath ="D://java工程//在线考试修改//javaweb//Exam-Server//src//main//resources//static//";
    @RequestMapping("/uploadVideo")
    public int VideoFile(MultipartFile file,Integer recordid){
        try {
            String type = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            String imgName = UUID.randomUUID().toString() + type;
            String filePath = localPath + imgName;
            InputStream is = file.getInputStream();
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            FileOutputStream fileOutputStream = new FileOutputStream(new File(filePath));
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            fileOutputStream.write(output.toByteArray());
            is.close();
            fileOutputStream.close();
            output.close();

            Record record = Record.builder()
                    .recordid(recordid)
                    .grade(filePath)
                    .build();
            recordDao.updateById(record);

        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
        return 0;
    }

    @RequestMapping("/uploadFiles")
    public int AnswerFile(MultipartFile file,Integer recordid,Integer questionid) {


        return 0;
    }

    // wangEditor
    @PostMapping("uploadImg")
    public Map<String,Object> uploadImg(List<MultipartFile> files){
        File file = new File(localPath);
        if (!file.exists()){
            file.mkdirs();
        }
        String imgServerURL = null;
        List<String> urls = new ArrayList<>();
        for (MultipartFile imgFile: files) {

//            String type = imgFile.getOriginalFilename().substring(imgFile.getOriginalFilename().lastIndexOf("."));
//            String imgName = UUID.randomUUID().toString() + type;
//            String filePath = localPath + imgName;

            try {
                imgServerURL = fileService.uploadImg(imgFile);

//                InputStream is = imgFile.getInputStream();
//                FileOutputStream fileOutputStream = new FileOutputStream(filePath);
//                ByteArrayOutputStream output = new ByteArrayOutputStream();
//                byte[] buffer = new byte[1024];
//                int length;
//                while ((length = is.read(buffer)) > 0) {
//                    output.write(buffer, 0, length);
//                }
//                fileOutputStream.write(output.toByteArray());
//                is.close();
//                fileOutputStream.close();

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("[图片上传失败--]");
            }
//            imgServerURL =
            urls.add(imgServerURL);
            System.out.println("[图片上传成功--：]" + imgServerURL);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("errno",0);
        map.put("data",urls);
        return map;
    }
}
