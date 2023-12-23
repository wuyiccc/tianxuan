package com.wuyiccc.tianxuan.file.service.impl;

import cn.hutool.core.text.StrPool;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wuyiccc.tianxuan.common.exception.CustomException;
import com.wuyiccc.tianxuan.file.config.TianxuanMinioConfig;
import com.wuyiccc.tianxuan.file.service.FileService;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.UUID;

/**
 * @author wuyiccc
 * @date 2023/9/3 06:53
 */
@Slf4j
@Service
public class MinioFileServiceImpl implements FileService {


    @Autowired
    private TianxuanMinioConfig tianxuanMinioConfig;


    @Resource
    private MinioClient minioClient;


    @Override
    public String uploadFile(MultipartFile file, String userId) throws IOException {

        log.info("contentType: {}", file.getContentType());

        String fileName = userId + StrPool.SLASH + UUID.randomUUID().toString().replace("-", "") + file.getOriginalFilename();
        PutObjectArgs build = PutObjectArgs.builder()
                .bucket(tianxuanMinioConfig.getBucket())
                .object(fileName)
                .stream(file.getInputStream(), file.getSize(), -1)
                .contentType(file.getContentType())
                .build();
        try {

            minioClient.putObject(build);
            return tianxuanMinioConfig.getEndpoint() + "/" + tianxuanMinioConfig.getBucket() + "/" + fileName;
        } catch (Exception e) {
            log.error("文件上传失败", e);
            throw new CustomException("文件上传失败");
        }
    }

}
