package com.wuyiccc.tianxuan.search.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author wuyiccc
 * @date 2023/9/3 06:53
 */
public interface FileService {


    String uploadFile(MultipartFile file) throws IOException;
}
