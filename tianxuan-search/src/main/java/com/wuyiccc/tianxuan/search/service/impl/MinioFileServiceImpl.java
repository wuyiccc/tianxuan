package com.wuyiccc.tianxuan.search.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
//import com.jvm123.minio.service.MinioFileService;
import com.wuyiccc.tianxuan.common.exception.CustomException;
import com.wuyiccc.tianxuan.search.config.MinioConfig;
import com.wuyiccc.tianxuan.search.service.FileService;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    private MinioConfig minioConfig;


    @Autowired
    private ObjectMapper objectMapper;


    @Override
    public String uploadFile(MultipartFile file) throws IOException {

        String fileName = UUID.randomUUID().toString().replace("-", "") + file.getOriginalFilename();
        MinioClient minioClient = getMinioClient();
        PutObjectArgs build = PutObjectArgs.builder()
                .bucket(minioConfig.getBucket())
                .object(fileName)
                .stream(file.getInputStream(), file.getSize(), -1)
                .contentType("image/jpeg")
                .build();
        try {

            minioClient.putObject(build);
            return minioConfig.getEndpoint() + "/" + minioConfig.getBucket() + "/" + fileName;
        } catch (Exception e) {
            log.error("文件上传失败", e);
            throw new CustomException("文件上传失败");
        }
    }

    private MinioClient getMinioClient() {
        return MinioClient.builder()
                .endpoint(minioConfig.getEndpoint())
                .credentials(minioConfig.getAccessKey(), minioConfig.getSecretKey())
                .build();
    }
}
