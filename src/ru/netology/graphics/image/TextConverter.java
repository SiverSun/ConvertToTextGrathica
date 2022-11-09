package ru.netology.graphics.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.net.URL;

public class TextConverter implements TextGraphicsConverter {
    protected double maxRatio;
    protected int maxWidth;
    protected int maxHeight;
    protected TextColorSchema schema = new TextColor();

    @Override
    public String convert(String url) throws IOException, BadImageSizeException {
        BufferedImage img = ImageIO.read(new URL(url));

        if (this.maxRatio != 0) {
            double imgRatio = img.getWidth() / img.getHeight();
            if (imgRatio > this.maxRatio) {
                throw new BadImageSizeException(imgRatio, this.maxRatio);
            }
        }

        int newWidth = img.getWidth();
        int newHeight = img.getHeight();
        if (newHeight > this.maxHeight) {
            double ratio = newHeight / maxHeight;
            newWidth = (int) (newWidth / ratio);
            newHeight = (int) (newHeight / ratio);

        }

        Image scaledImage = img.getScaledInstance(newWidth, newHeight, BufferedImage.SCALE_SMOOTH);
        BufferedImage bwImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D graphics = bwImg.createGraphics();
        graphics.drawImage(scaledImage, 0, 0, null);
        //ImageIO.write(bwImg, "png", new File("out.png"));

        WritableRaster bwRaster = bwImg.getRaster();

        StringBuilder sb = new StringBuilder();
        for (int h = 0; h < newHeight; h++) {
            for (int w = 0; w < newWidth; w++) {
                int color = bwRaster.getPixel(w, h, new int[3])[0];
                char c = schema.convert(color);
                sb.append(c).append(c);
            }
            sb.append("\n");
        }
        return sb.toString();

    }

    @Override
    public void setMaxWidth(int width) {
        this.maxWidth = width;
    }

    @Override
    public void setMaxHeight(int height) {
        this.maxHeight = height;
    }

    @Override
    public void setMaxRatio(double maxRatio) {
        this.maxRatio = maxRatio;
    }

    @Override
    public void setTextColorSchema(TextColorSchema schema) {
        this.schema = schema;
    }

}
