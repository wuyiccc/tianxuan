package com.wuyiccc.tianxuan.file.service;

import com.wuyiccc.tianxuan.pojo.bo.Base64FileBO;
import io.minio.errors.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author wuyiccc
 * @date 2023/9/3 06:53
 */
public interface FileService {


    String uploadFile(MultipartFile file, String userId) throws IOException;

    String uploadAdminFace(Base64FileBO base64FileBO) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException;
}