package com.wuyiccc.tianxuan.file.controller;

import com.wuyiccc.tianxuan.common.result.CommonResult;
import com.wuyiccc.tianxuan.file.service.FileService;
import com.wuyiccc.tianxuan.pojo.bo.Base64FileBO;
import io.minio.errors.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

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

    // app
    @PostMapping("/uploadFace")
    public CommonResult<String> uploadFace(@RequestParam("file") MultipartFile file, @RequestParam("userId") String userId) throws IOException {

        String url = fileService.uploadFile(file, userId);
        return CommonResult.ok(url);
    }


    // admin
    @PostMapping("/uploadAdminFace")
    public CommonResult<String> uploadAdminFace(@RequestBody @Valid Base64FileBO base64FileBO) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {

        String url = fileService.uploadAdminFace(base64FileBO);
        return CommonResult.ok(url);
    }

    // app
    @PostMapping("/uploadLogo")
    public CommonResult<String> uploadLogo(@RequestParam("file") MultipartFile file) throws IOException {

        String url = fileService.uploadLogo(file);
        return CommonResult.ok(url);
    }


    // app
    @PostMapping("/uploadBizLicense")
    public CommonResult<String> uploadBizLicense(@RequestParam("file") MultipartFile file) throws IOException {

        String url = fileService.uploadBizLicense(file);
        return CommonResult.ok(url);
    }

    // app
    @PostMapping("/uploadAuthLetter")
    public CommonResult<String> uploadAuthLetter(@RequestParam("file") MultipartFile file) throws IOException {

        String url = fileService.uploadAuthLetter(file);
        return CommonResult.ok(url);
    }

}
