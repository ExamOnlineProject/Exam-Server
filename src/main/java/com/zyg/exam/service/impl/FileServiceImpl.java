package com.zyg.exam.service.impl;

import com.qcloud.cos.model.ObjectMetadata;
import com.zyg.exam.service.FileService;
import com.zyg.exam.utils.COSUtil;
import com.zyg.exam.utils.FileNameUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String uploadImg(MultipartFile file) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());
        String name = FileNameUtil.getName(file);
        String realPath = null;
        try {
            realPath = COSUtil.upload("img/" + name, file.getInputStream(), metadata);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return realPath;
    }
}
