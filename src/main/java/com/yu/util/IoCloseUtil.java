package com.yu.util;

import org.apache.commons.lang3.ObjectUtils;

import java.io.Closeable;
import java.io.Flushable;

/**
 * 流关闭工具类
 *
 * @author yu_chen
 * @date 2019年11月14日17:51:19
 */
public class IoCloseUtil {
    /**
     * 关闭
     *
     * @param ios 各种流
     */
    public static void closeAll(Closeable... ios) {
        for (Closeable io : ios) {
            if (ObjectUtils.isNotEmpty(io)) {
                try {
                    io.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}