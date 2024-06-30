package com.wuyiccc.chat.demo.serializer.impl;

import cn.hutool.json.JSONUtil;
import com.wuyiccc.chat.demo.serializer.Serializer;
import com.wuyiccc.chat.demo.serializer.SerializerAlgorithm;

import java.nio.charset.StandardCharsets;

/**
 * @author wuyiccc
 * @date 2024/6/29 17:32
 */
public class JSONSerializer implements Serializer {
    @Override
    public byte getSerializerAlgorithm() {

        return SerializerAlgorithm.JSON;
    }

    @Override
    public byte[] serialize(Object object) {

        return JSONUtil.toJsonStr(object).getBytes(StandardCharsets.UTF_8);
    }



    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {

        return JSONUtil.toBean(new String(bytes), clazz);
    }
}
