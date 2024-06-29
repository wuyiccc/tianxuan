package com.wuyiccc.chat.demo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

/**
 * @author wuyiccc
 * @date 2024/6/29 15:17
 */
public class ByteBufTest {

    public static void main(String[] args) {


        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(9, 100);
        print("allocate ByteBuf(9, 100)", buffer);

        // write方法改变写指针, 写完之后写指针未到capacity的时候, buffer仍然可写
        buffer.writeBytes(new byte[]{1, 2, 3, 4});
        print("writeBytes(1, 2, 3, 4)", buffer);

        // write方法改变写指针, 写完之后未到capacity的时候, buffer仍然可写, 写完int类型之后，写指针增加4
        buffer.writeInt(12);
        print("writeInt(12)", buffer);

        // write方法改变写指针, 写完之后等于capacity的时候, buffer不可写
        buffer.writeBytes(new byte[]{5});
        print("writeBytes(5)", buffer);

        // write方法改变写指针，写的时候发现buffer不可写则开始扩容, 扩容之后capacity随即改变
        buffer.writeBytes(new byte[]{6});
        print("writeBytes(6)", buffer);

        // get方法不改变读写指针
        System.out.println("getBytes(3) return: " + buffer.getByte(3));
        System.out.println("getShort(3) return: " + buffer.getShort(3));
        System.out.println("getInt(3) return: " + buffer.getInt(3));
        print("getByte()", buffer);

        // set方法不改变读写指针
        buffer.setByte(buffer.readableBytes() + 1, 0);
        print("setByte()", buffer);


        // read方法改变读写指针
        byte[] dst = new byte[buffer.readableBytes()];
        buffer.readBytes(dst);
        print("readBytes(" + dst.length + ")", buffer);

        // 默认对外内存释放引用
        buffer.release();
    }


    private static void print(String action, ByteBuf buffer) {
        System.out.println("after ======" + action + "=============");
        System.out.println("capacity: " + buffer.capacity());
        System.out.println("macCapacity: " + buffer.maxCapacity());
        System.out.println("readerIndex: " + buffer.readerIndex());
        System.out.println("readerBytes: " + buffer.readableBytes());
        System.out.println("isReadable: " + buffer.isReadable());
        System.out.println("writerIndex: " + buffer.writerIndex());
        System.out.println("writableBytes: " + buffer.writableBytes());
        System.out.println("isWritable: " + buffer.isWritable());
        System.out.println("maxWritableBytes: " + buffer.maxWritableBytes());
        System.out.println("=======================================================> end");

    }
}
