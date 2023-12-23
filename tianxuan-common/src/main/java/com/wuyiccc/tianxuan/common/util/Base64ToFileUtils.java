package com.wuyiccc.tianxuan.common.util;

import com.wuyiccc.tianxuan.common.exception.CustomException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sun.misc.BASE64Decoder;

import java.io.*;

/**
 * @author wuyiccc
 * @date 2023/6/26 21:54
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class Base64ToFileUtils {
    // 传入base64编码字符以及保存路径
    public static void base64ToFile(String base64, String filePath) throws IOException {
        // base64编码字符必须不能包含base64的前缀，否则会报错
        // filePath需要为具体到文件的名称和格式，如111.txt
        // 文件路径需要双斜杠转义，如:  D:\\files\\111.txt
        if (base64 == null || filePath == null) {
            throw new CustomException("生成文件失败, 参数为null");
        }
        // 判断是否base64是否包含data:image/png;base64等前缀，如果有则去除
        if (base64.contains("data:image/png;base64")) {
            base64 = base64.substring(22);
            log.info("包含png" + base64);
        }
        if (base64.contains("data:image/jpeg;base64")) {
            base64 = base64.substring(23);
            log.info("包含jpeg" + base64);
        }
        if (base64.contains("data:application/pdf;base64")) {
            base64 = base64.substring(28);
            log.info("包含pdf" + base64);
        }
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] bytes = decoder.decodeBuffer(base64);
        for (int i = 0; i < bytes.length; ++i) {
            // 调整异常数据
            if (bytes[i] < 0) {
                bytes[i] += 256;
            }
        }
        OutputStream outputStream = null;
        InputStream inputStream = new ByteArrayInputStream(bytes);
        // 此处判断文件夹是否存在，不存在则创建除文件外的父级文件夹
        File file = new File(filePath);
        if (!file.exists()) {
            log.info("上级目录" + file.getParentFile());
            file.getParentFile().mkdirs();
        }
        try {
            // 生成指定格式文件
            outputStream = new FileOutputStream(filePath);
            byte[] buff = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(buff)) != -1) {
                outputStream.write(buff, 0, len);
            }
        } catch (IOException e) {
            log.error("base生成文件异常", e);
            throw new CustomException(e.getMessage());
        } finally {
            outputStream.flush();
            outputStream.close();
        }
    }
}

