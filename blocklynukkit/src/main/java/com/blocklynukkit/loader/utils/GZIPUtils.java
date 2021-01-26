package com.blocklynukkit.loader.utils;


import cn.nukkit.plugin.PluginLogger;
import com.blocklynukkit.loader.Loader;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GZIPUtils
{
    private static PluginLogger log = Loader.getPluginLogger();
    /**
     * 将字符串压缩后Base64
     * @param primStr 待加压加密函数
     * @return
     */
    public static String gzipString(String primStr) {
        if (primStr == null || primStr.length() == 0) {
            return primStr;
        }
        ByteArrayOutputStream out = null;
        GZIPOutputStream gout = null;
        try{
            out = new ByteArrayOutputStream();
            gout = new GZIPOutputStream(out);
            gout.write(primStr.getBytes(StandardCharsets.ISO_8859_1));
            gout.flush();
        } catch (IOException e) {
            log.error("gzip压缩操作失败：",  e);
            return null;
        } finally {
            if (gout != null) {
                try {
                    gout.close();
                } catch (IOException e) {
                    log.error("gzip压缩操作，关闭gzip操作流失败：", e);
                }
            }
        }
        return Base64.getEncoder().encodeToString(out.toByteArray());
    }

    /**
     * 将压缩并Base64后的字符串进行解密解压
     * @param compressedStr 待解密解压字符串
     * @return
     */
    public static final String ungzipString(String compressedStr) {
        if (compressedStr == null) {
            return null;
        }
        ByteArrayOutputStream out = null;
        ByteArrayInputStream in = null;
        GZIPInputStream gin = null;
        String decompressed = null;
        try {
            byte[] compressed = Base64.getDecoder().decode(compressedStr);
            out = new ByteArrayOutputStream();
            in = new ByteArrayInputStream(compressed);
            gin = new GZIPInputStream(in);
            byte[] buffer = new byte[1024];
            int offset = -1;
            while((offset = gin.read(buffer)) != -1) {
                out.write(buffer, 0, offset);
            }
            decompressed = out.toString("ISO-8859-1");
        } catch (IOException e) {
            log.error("gzip解压操作失败：", e);
            decompressed = null;
        } finally {
            if (gin != null) {
                try {
                    gin.close();
                } catch (IOException e) {
                    log.error("gzip解压操作，关闭压缩流失败：", e);
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    log.error("gzip解压操作，关闭输入流失败：", e);
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    log.error("gzip解压操作，关闭输出流失败：", e);
                }
            }
        }
        return decompressed;
    }

}
