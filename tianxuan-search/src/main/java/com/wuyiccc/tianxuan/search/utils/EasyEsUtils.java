package com.wuyiccc.tianxuan.search.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.dromara.easyes.annotation.IndexName;

import java.util.Objects;

/**
 * @author wuyiccc
 * @date 2023/7/26 15:46
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EasyEsUtils {

    public static String getAliasName(Class<?> klass) {
        // 获取IndexName注解
        IndexName indexNameAnnotation = klass.getAnnotation(IndexName.class);

        if (Objects.isNull(indexNameAnnotation)) {
            return "";
        }
        // 获取aliasName的值
        return indexNameAnnotation.aliasName();
    }
}
