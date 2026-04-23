/*
 * Decompiled with CFR 0.152.
 */
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Util {
    public static int defaultWidth = 50;
    public static int defaultHeight = 50;

    public static BufferedImage openFullImage(String path) throws IOException {
        BufferedImage img = ImageIO.read(new File(path));
        return img;
    }

    public static BufferedImage openEmbbededImage(String path) throws IOException {
        BufferedImage img = ImageIO.read(new File(path));
        int width = defaultWidth;
        int height = defaultHeight;
        Image tmp = img.getScaledInstance(width, height, 4);
        BufferedImage resized = new BufferedImage(width, height, 2);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }

    public static BufferedImage resize(BufferedImage img) throws IOException {
        int width = 300;
        int height = 300;
        Image tmp = img.getScaledInstance(width, height, 4);
        BufferedImage resized = new BufferedImage(width, height, 2);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }
}

