package com.wuyiccc.tianxuan.file.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author wuyiccc
 * @date 2023/9/3 06:53
 */
public interface FileService {


    String uploadFile(MultipartFile file, String userId) throws IOException;
}
