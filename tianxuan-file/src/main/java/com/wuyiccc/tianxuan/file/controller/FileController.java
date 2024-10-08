package com.wuyiccc.tianxuan.file.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.IdUtil;
import com.wuyiccc.tianxuan.common.result.R;
import com.wuyiccc.tianxuan.file.service.FileService;
import com.wuyiccc.tianxuan.file.utils.VideoUtils;
import com.wuyiccc.tianxuan.pojo.bo.Base64FileBO;
import com.wuyiccc.tianxuan.pojo.vo.VideoMsgVO;
import io.minio.errors.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

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
    public R<String> uploadFace(@RequestParam("file") MultipartFile file, @RequestParam("userId") String userId) throws IOException {

        String url = fileService.uploadFile(file, userId);
        return R.ok(url);
    }


    // admin
    @PostMapping("/uploadAdminFace")
    public R<String> uploadAdminFace(@RequestBody @Valid Base64FileBO base64FileBO) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {

        String url = fileService.uploadAdminFace(base64FileBO);
        return R.ok(url);
    }

    // app
    @PostMapping("/uploadLogo")
    public R<String> uploadLogo(@RequestParam("file") MultipartFile file) throws IOException {

        String url = fileService.uploadLogo(file);
        return R.ok(url);
    }


    // app
    @PostMapping("/uploadBizLicense")
    public R<String> uploadBizLicense(@RequestParam("file") MultipartFile file) throws IOException {

        String url = fileService.uploadBizLicense(file);
        return R.ok(url);
    }

    // app
    @PostMapping("/uploadAuthLetter")
    public R<String> uploadAuthLetter(@RequestParam("file") MultipartFile file) throws IOException {

        String url = fileService.uploadAuthLetter(file);
        return R.ok(url);
    }

    // app
    @PostMapping("/uploadPhoto")
    public R<List<String>> uploadPhoto(@RequestParam("files") List<MultipartFile> files, @RequestParam("companyId") String companyId) throws IOException {

        if (CharSequenceUtil.isBlank(companyId)) {
            companyId = CharSequenceUtil.EMPTY;
        }

        List<String> imgUrlList = new ArrayList<>();
        for (MultipartFile file : files) {

            String url = fileService.uploadPhoto(file, companyId);
            imgUrlList.add(url);
        }

        return R.ok(imgUrlList);
    }

    @PostMapping("/uploadArticleCover")
    public R<String> uploadArticleCover(@RequestParam("file") MultipartFile file) throws IOException {

        String url = fileService.uploadArticleCover(file);
        return R.ok(url);
    }

    @PostMapping("/uploadArticleImages")
    public R<List<String>> uploadArticleImages(@RequestParam("files") List<MultipartFile> files) throws IOException {

        List<String> imgUrlList = new ArrayList<>();
        for (MultipartFile file : files) {

            String url = fileService.updateArticleImage(file);
            imgUrlList.add(url);
        }

        return R.ok(imgUrlList);
    }

    @PostMapping("/uploadChatPhoto")
    public R<String> uploadChatPhoto(@RequestParam("file") MultipartFile file, @RequestParam String userId) throws IOException {

        String url = fileService.uploadChatPhoto(file, userId);
        return R.ok(url);
    }

    @PostMapping("/uploadChatVideo")
    public R<VideoMsgVO> uploadChatVideo(@RequestParam("file") MultipartFile file, @RequestParam String userId) throws IOException {

        String url = fileService.uploadChatVideo(file, userId);


        File tempDir = FileUtil.getTmpDir();

        String fileName = IdUtil.simpleUUID() + ".jpg";
        String tmpFilePath = tempDir.getAbsolutePath() + StrPool.SLASH + fileName;
        log.info("tmpFile path: {}", tmpFilePath);

        byte[] firstFrame = VideoUtils.getVideoFirstFrame(url);

        log.info("视频截图成功");

        File newFile = FileUtil.writeBytes(firstFrame, tmpFilePath);

        // 上传封面
        String imgUrl = fileService.uploadFile(newFile, fileName);

        // 删除文件
        FileUtil.del(newFile);

        log.info("上传封面成功: {}", imgUrl);

        VideoMsgVO vo = new VideoMsgVO();
        vo.setCover(imgUrl);
        vo.setVideoPath(url);


        return R.ok(vo);
    }


    @PostMapping("/uploadChatVoice")
    public R<String> uploadChatVoice(@RequestParam("file") MultipartFile file, @RequestParam String userId) throws IOException {

        String url = fileService.uploadChatVoice(file, userId);
        return R.ok(url);
    }

    /**
     * 语音转文字, 这里需要调用百度云服务
     */
    @PostMapping("/uploadVoice")
    public R<String> uploadVoice(@RequestParam("file") MultipartFile file) {


        return R.ok("语音转文字测试");
    }

}
