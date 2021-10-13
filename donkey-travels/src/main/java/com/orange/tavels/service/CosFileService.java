package com.orange.tavels.service;

import com.orange.tavels.domain.FileManage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CosFileService {

    FileManage upload(MultipartFile file) throws IOException, InterruptedException;
    FileManage uploadFixWH(MultipartFile file,Integer width, Integer height) throws IOException, InterruptedException;
    FileManage uploadFixW(MultipartFile file,Integer width ) throws IOException, InterruptedException;

}
