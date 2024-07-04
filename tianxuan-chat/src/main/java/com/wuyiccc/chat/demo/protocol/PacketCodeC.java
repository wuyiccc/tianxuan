package com.wuyiccc.chat.demo.protocol;

import com.wuyiccc.chat.demo.protocol.request.LoginRequestPacket;
import com.wuyiccc.chat.demo.protocol.request.MessageRequestPacket;
import com.wuyiccc.chat.demo.protocol.response.LoginResponsePacket;
import com.wuyiccc.chat.demo.protocol.response.MessageResponsePacket;
import com.wuyiccc.chat.demo.serializer.Serializer;
import com.wuyiccc.chat.demo.serializer.impl.JSONSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import org.bouncycastle.util.Pack;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.wuyiccc.chat.demo.protocol.Command.*;

/**
 * @author wuyiccc
 * @date 2024/6/29 17:37
 */
public class PacketCodeC {

    public static final int MAGIC_NUMBER = 0x12345678;

    // command : Packet
    private static final Map<Byte, Class<? extends Packet>> packetTypeMap;


    // algType: AlgClass
    private static final Map<Byte, Serializer> serializerMap;



    public static final PacketCodeC INSTANCE = new PacketCodeC();

    static {

        packetTypeMap = new HashMap<>();
        packetTypeMap.put(LOGIN_REQUEST, LoginRequestPacket.class);
        packetTypeMap.put(LOGIN_RESPONSE, LoginResponsePacket.class);
        packetTypeMap.put(MESSAGE_REQUEST, MessageRequestPacket.class);
        packetTypeMap.put(MESSAGE_RESPONSE, MessageResponsePacket.class);

        serializerMap = new HashMap<>();
        Serializer serializer = new JSONSerializer();
        serializerMap.put(serializer.getSerializerAlgorithm(), serializer);

    }

    public void encode(ByteBuf byteBuf, Packet packet) {

        // 1. 序列化java对象
        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        // 编码写入ByteBuf
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
    }

    /**
     * 该方法ByteBuf有内存泄露的问题
     */
    public ByteBuf encode(ByteBufAllocator byteBufAllocator, Packet packet) {

        // 1. 创建ByteBuf对象
        ByteBuf byteBuf = byteBufAllocator.ioBuffer();
        // 序列化java对象
        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);

        return byteBuf;
    }

    public Packet decode(ByteBuf byteBuf) {

        // 跳过magic number
        byteBuf.skipBytes(4);

        // 跳过版本号
        byteBuf.skipBytes(1);

        // 拿到序列化算法
        byte serializeAlgorithm = byteBuf.readByte();

        // 指令
        byte command = byteBuf.readByte();

        // 数据包长度
        int length = byteBuf.readInt();

        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Class<? extends Packet> requestType = getRequestType(command);
        Serializer serializer = getSerializer(serializeAlgorithm);

        if (Objects.nonNull(requestType) && Objects.nonNull(serializer)) {
            return serializer.deserialize(requestType, bytes);
        }


        return null;
    }

    private Serializer getSerializer(Byte serializeAlgorithm) {

        return serializerMap.get(serializeAlgorithm);
    }

    private Class<? extends Packet> getRequestType(byte command) {

        return packetTypeMap.get(command);
    }
}
