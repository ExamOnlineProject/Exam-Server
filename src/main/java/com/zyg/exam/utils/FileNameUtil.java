package com.zyg.exam.utils;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public class FileNameUtil {

    public static String[] chars = new String[]{"a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z"};

    public static String getName(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String extName = getExtName(originalFilename);
        String uniqueName = generateUuid() + extName;
        return uniqueName;
    }


    private static String generateUuid() {
//        StringBuffer shortBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
//        for (int i = 0; i < 8; i++) {
//            String str = uuid.substring(i * 4, i * 4 + 4);
//            int x = Integer.parseInt(str, 16);
//            shortBuffer.append(chars[x % 0x3E]);
//        }
        return uuid;
    }


    /**
     * 获取文件扩展名
     *
     * @return
     */
    public static String getExtName(String filename) {
        int index = filename.lastIndexOf(".");

        if (index == -1) {
            return null;
        }
        String result = filename.substring(index + 1);
        return result;
    }


//    public static void main(String[] args) {
//        for (int i = 0; i < 20; i++) {
//            System.out.println(generateUuid());
//        }
//    }
}