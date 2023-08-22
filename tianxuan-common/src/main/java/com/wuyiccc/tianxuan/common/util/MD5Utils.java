package com.wuyiccc.tianxuan.common.util;

import org.springframework.util.DigestUtils;

/**
 * @author wuyiccc
 * @date 2023/8/22 22:16
 */
public class MD5Utils {

    public static String encrypt(String data, String slat) {
        String base = data + slat;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }
}
