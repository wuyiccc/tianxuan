package com.wuyiccc.chat.demo.serializer;

import com.wuyiccc.chat.demo.serializer.impl.JSONSerializer;

/**
 * @author wuyiccc
 * @date 2024/6/29 17:21
 */
public interface Serializer {

    byte JSON_SERIALIZER = 1;

    Serializer DEFAULT = new JSONSerializer();



    /**
     * 序列化算法
     */
    byte getSerializerAlgorithm();


    /**
     * java对象转二进制
     */
    byte[] serialize(Object object);


    /**
     * 二进制转java对象
     */
    <T> T deserialize(Class<T> clazz, byte[] bytes);
}
