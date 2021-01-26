package com.blocklynukkit.loader.other.card;

import com.blocklynukkit.loader.Loader;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class CardMaker {
    //乱搞的，没用，不要在意
    public void makeDefaultCard(String title) {
        try {
            String datapath = Loader.plugin.getDataFolder() + "/";
            ResizeImage.changeSize(900, 450, datapath + "background.jpg", datapath + "processcard.jpg");
            BufferedImage img = ImageIO.read(new File(datapath + "processcard.jpg"));
            BufferedImage gusedimg = Gaussian.makeGaussian(img);
            gusedimg = Gaussian.makeGaussian(gusedimg);
            gusedimg = WaterMark.addWaterMark(
                    gusedimg, 0, 70, title,
                    new Color(255, 255, 255, 200),
                    new Font("Arial", Font.PLAIN, 70)
            );
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(datapath + "servercard.jpg"));
            ImageIO.write(gusedimg, "jpeg", out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
