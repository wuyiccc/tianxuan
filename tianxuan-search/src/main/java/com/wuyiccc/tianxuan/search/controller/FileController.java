package com.wuyiccc.tianxuan.search.controller;

import com.wuyiccc.tianxuan.common.result.CommonResult;
import com.wuyiccc.tianxuan.search.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author wuyiccc
 * @date 2023/9/3 00:24
 */
@RestController
@RequestMapping("/file")
@Slf4j
public class FileController {


    @Autowired
    private FileService fileService;

    @PostMapping("/uploadFile")
    public CommonResult<String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {

        String url = fileService.uploadFile(file);
        return CommonResult.ok(url);
    }

}
