package com.zyg.exam.utils;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;

import java.io.InputStream;


public class COSUtil {
    private static String secretId = "AKIDuc6OTJI397sb4II1DAJbfBPPDrFOmVSG";
    private static String secretKey = "iP3TcW9ZjhRwOeAQ1MYVBEyVpuoUsDNQ";
    private static String COS_REGION = "ap-nanjing";
    private static String appId = "1304545458";
    private static String bucketName = "xun-1304545458";
    private static String baseUrl = "https://" + bucketName + ".cos." + COS_REGION + ".myqcloud.com";
    private static COSCredentials cred;
    private static Region region;
    private static ClientConfig clientConfig;

    static {
        // 1 初始化用户身份信息（secretId, secretKey）。
        cred = new BasicCOSCredentials(secretId, secretKey);
        // 2 设置 bucket 的区域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
        region = new Region(COS_REGION);
        clientConfig = new ClientConfig(region);
    }


    public static String upload(String key, InputStream input, ObjectMetadata metadata) {
        // 3 生成 cos 客户端。
        COSClient cosClient = new COSClient(cred, clientConfig);
        String rtValue = null;
        try {
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, input, metadata);
            PutObjectResult result = cosClient.putObject(putObjectRequest);
            rtValue = baseUrl + "/" + key;
        } catch (CosClientException e) {
            e.printStackTrace();
        } finally {
            cosClient.shutdown();
            return rtValue;
        }
    }

//    public static void main(String[] args) {
//        File file = new File("C:\\Users\\huaxi\\Pictures\\v2-46d4c5f14cc51bb9adfe40d4be5544b2_r.jpg");
//        String key = "article/638eaa0505.jpg";
//        String upload = upload(file, key);
//        System.out.println(upload);
//    }
}
