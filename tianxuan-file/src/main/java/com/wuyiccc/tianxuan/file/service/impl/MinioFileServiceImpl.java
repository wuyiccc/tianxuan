package com.wuyiccc.tianxuan.file.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.IdUtil;
import com.wuyiccc.tianxuan.common.exception.CustomException;
import com.wuyiccc.tianxuan.common.util.Base64ToFileUtils;
import com.wuyiccc.tianxuan.file.config.TianxuanMinioConfig;
import com.wuyiccc.tianxuan.file.service.FileService;
import com.wuyiccc.tianxuan.pojo.bo.Base64FileBO;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.UploadObjectArgs;
import io.minio.errors.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
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

    @Override
    public String uploadAdminFace(Base64FileBO base64FileBO) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {

        String base64File = base64FileBO.getBase64File();

        String suffixName = ".png";

        String uuid = IdUtil.simpleUUID();
        String objectName= uuid + suffixName;

        String rootPath = "./tmp" + File.separator;
        String filePath = rootPath + File.separator + objectName;

        Base64ToFileUtils.base64ToFile(base64File, filePath);

        File file = new File(filePath);

        UploadObjectArgs build = UploadObjectArgs.builder()
                .bucket(tianxuanMinioConfig.getBucket())
                .object(objectName)
                .filename(file.getAbsolutePath())
                .build();
        minioClient.uploadObject(build);

        boolean delete = file.delete();
        log.info("delete: {}", delete);


        return tianxuanMinioConfig.getEndpoint() + "/" + tianxuanMinioConfig.getBucket() + "/" + objectName;
    }

    @Override
    public String uploadLogo(MultipartFile file) throws IOException {

        log.info("contentType: {}", file.getContentType());

        String fileName = "company" + StrPool.SLASH + UUID.randomUUID().toString().replace("-", "") + file.getOriginalFilename();
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

    @Override
    public String uploadBizLicense(MultipartFile file) throws IOException {
        log.info("contentType: {}", file.getContentType());

        String fileName = "bizLicense" + StrPool.SLASH + UUID.randomUUID().toString().replace("-", "") + file.getOriginalFilename();
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

    @Override
    public String uploadAuthLetter(MultipartFile file) throws IOException {
        log.info("contentType: {}", file.getContentType());

        String fileName = "company/authLetter" + StrPool.SLASH + UUID.randomUUID().toString().replace("-", "") + file.getOriginalFilename();
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

    @Override
    public String uploadPhoto(MultipartFile file, String companyId) throws IOException {
        String fileName = "company/photo/" + companyId + StrPool.SLASH + UUID.randomUUID().toString().replace("-", "") + file.getOriginalFilename();
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

    @Override
    public String uploadArticleCover(MultipartFile file) throws IOException {
        String fileName = "article/cover" + StrPool.SLASH + UUID.randomUUID().toString().replace("-", "") + file.getOriginalFilename();
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

    @Override
    public String updateArticleImage(MultipartFile file) throws IOException {
        String fileName = "article/content" + StrPool.SLASH + UUID.randomUUID().toString().replace("-", "") + file.getOriginalFilename();
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
