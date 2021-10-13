package com.orange.tavels.controller;

import com.orange.share.response.ResponseWrapper;
import com.orange.tavels.service.CosFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/cosFile")
public class CosFileController {
    @Autowired
    CosFileService cosFileService;

    /**
     * 原图上传,不进行压缩
     * @param file
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    @PostMapping("/upload")
    public ResponseWrapper upload(@RequestParam("file") MultipartFile file) throws IOException, InterruptedException {
        return ResponseWrapper.markSuccess(cosFileService.upload(file));
    }

    /**
     * 宽高指定压缩
     * @param file
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    @PostMapping("/upload/{width}/{height}")
    public ResponseWrapper uploadFixedWH(@RequestParam("file") MultipartFile file, @PathVariable Integer width,@PathVariable Integer height) throws IOException, InterruptedException {
        return ResponseWrapper.markSuccess(cosFileService.uploadFixWH(file,width,height));
    }

    /**
     * 等宽不等高
     * @param file
     * @param width
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    @PostMapping("/upload/{width}")
    public ResponseWrapper uploadFixedW(@RequestParam("file") MultipartFile file, @PathVariable Integer width) throws IOException, InterruptedException {
        return ResponseWrapper.markSuccess(cosFileService.uploadFixW(file,width));
    }
}
