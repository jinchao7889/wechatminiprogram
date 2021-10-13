package com.orange.tavels.service.impl;


import com.orange.share.wxconfig.ServiceProperty;
import com.orange.tavels.dao.FileManageDao;
import com.orange.tavels.domain.FileManage;
import com.orange.tavels.service.CosFileService;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.transfer.TransferManager;
import com.qcloud.cos.transfer.Upload;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
public class CosFileServiceImpl implements CosFileService {
    @Autowired
    FileManageDao fileManageDao;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public FileManage upload(MultipartFile file) {
        String userId= (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String id = UUID.randomUUID().toString().replaceAll("-", "");
        String originalFileName = file.getOriginalFilename();
        String fileExport=originalFileName.substring(originalFileName.lastIndexOf(".")).toLowerCase();
        log.info("文件上传："+fileExport);
        String cosKey=id+fileExport;
        FileManage fileManage = fileRecord(id,userId,cosKey);
        ObjectMetadata objectMetadata = getMetaData(fileExport,file.getSize());

        try {
            upCos(cosKey,file.getInputStream(),objectMetadata);
        } catch (Exception e) {
            log.error("文件上传失败",e);
            throw new RuntimeException("图片文件上传失败");
        }
        return fileManage;
    }

    private FileManage fileRecord(String id,String userId,String cosKey){
        FileManage fileManage = new FileManage();
        fileManage.setId(id);
        fileManage.setUserId(userId);
        fileManage.setFileUrl(ServiceProperty.COS_URL+cosKey);
        fileManage.setCosKey(cosKey);
        fileManage.setCreateTime(System.currentTimeMillis()/1000);
        fileManageDao.save(fileManage);
        return fileManage;
    }

    private ObjectMetadata getMetaData(String fileExport,Long size){
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(size);
        switch (fileExport){
            case ".png":objectMetadata.setContentType("image/png");break;
            case ".jpg":objectMetadata.setContentType("image/jpeg");break;
            case ".jpeg":objectMetadata.setContentType("image/jpeg");break;
            case ".gif":objectMetadata.setContentType("image/gif");break;
            case ".mp4":objectMetadata.setContentType("video/mpeg4");break;
            case ".asf":objectMetadata.setContentType("video/x-ms-asf");break;
            case ".avi":objectMetadata.setContentType("video/avi");break;
            default:throw new RuntimeException("不支持的文件内类型");
        }
        return objectMetadata;
    }

    @Override
    public FileManage uploadFixWH(MultipartFile file, Integer width, Integer height) throws IOException, InterruptedException {
        String userId= (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String id = UUID.randomUUID().toString().replaceAll("-", "");
        String originalFileName = file.getOriginalFilename();
        String fileExport=originalFileName.substring(originalFileName.lastIndexOf(".")).toLowerCase();
        File tempFile =  File.createTempFile("temp", fileExport);

        Thumbnails.of(file.getInputStream()).size(width, height).keepAspectRatio(false).toFile(tempFile);
        String cosKey=id+fileExport;
        String cosKey2=id+"_"+width+"X"+height+fileExport;
        FileManage fileManage = fileRecord(id,userId,cosKey2);
        try {
            ObjectMetadata objectMetadata = getMetaData(fileExport,file.getSize());
            upCos(cosKey2,file.getInputStream(),objectMetadata);
            FileInputStream fis = new FileInputStream(tempFile);

            ObjectMetadata objectMetadata2 = getMetaData(fileExport,tempFile.length());
            /**
             * 上传压缩过的图片
             */
            upCos(cosKey2,fis,objectMetadata2);
        }finally {
            tempFile.delete();
        }
        return fileManage;
    }

    @Override
    public FileManage uploadFixW(MultipartFile file, Integer width) throws IOException, InterruptedException {
        String userId= (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String id = UUID.randomUUID().toString().replaceAll("-", "");
        String originalFileName = file.getOriginalFilename();
        String fileExport=originalFileName.substring(originalFileName.lastIndexOf(".")).toLowerCase();
        File tempFile =  File.createTempFile("temp", fileExport);

        Thumbnails.of(file.getInputStream()).width(width).keepAspectRatio(true).toFile(tempFile);
        String cosKey=id+fileExport;
        String cosKey2=id+"_"+width+fileExport;
        FileManage fileManage = fileRecord(id,userId,cosKey2);
        try {
            ObjectMetadata objectMetadata = getMetaData(fileExport,file.getSize());
            upCos(cosKey2,file.getInputStream(),objectMetadata);
            FileInputStream fis = new FileInputStream(tempFile);
            ObjectMetadata objectMetadata2 = getMetaData(fileExport,tempFile.length());
            /**
             * 上传压缩过的图片
             */
            upCos(cosKey2,fis,objectMetadata2);
        }finally {
            tempFile.delete();
        }
        return fileManage;
    }


    private void upCos(String key, InputStream inputStream,ObjectMetadata objectMetadata) throws InterruptedException {
        COSCredentials cred = new BasicCOSCredentials(ServiceProperty.TX_FILE_APPID, ServiceProperty.TX_FILE_KEY);
        ClientConfig clientConfig = new ClientConfig(new Region("ap-chengdu"));
// 3 生成 cos 客户端。
        COSClient cosClient = new COSClient(cred, clientConfig);
// 指定要上传到的存储桶
        String bucketName = ServiceProperty.BUCKET_NAME;
// 指定要上传到 COS 上对象键
        ExecutorService threadPool = Executors.newFixedThreadPool(32);
// 传入一个 threadpool, 若不传入线程池, 默认 TransferManager 中会生成一个单线程的线程池。
        TransferManager transferManager = new TransferManager(cosClient, threadPool);

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, inputStream,objectMetadata);
        Upload upload = transferManager.upload(putObjectRequest);
        upload.waitForUploadResult();
    }
}
