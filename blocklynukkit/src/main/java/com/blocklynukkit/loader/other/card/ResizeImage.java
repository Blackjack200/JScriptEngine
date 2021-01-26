package com.blocklynukkit.loader.other.card;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class ResizeImage {
    public static boolean changeSize(int newWidth, int newHeight, String pathin, String pathout) {
        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(pathin));

            //字节流转图片对象
            Image bi = ImageIO.read(in);
            //构建图片流
            BufferedImage tag = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
            //绘制改变尺寸后的图
            tag.getGraphics().drawImage(bi, 0, 0, newWidth, newHeight, null);
            //输出流
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(pathout));
            //JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            //encoder.encode(tag);
            ImageIO.write(tag, "jpeg", out);
            in.close();
            out.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

}
