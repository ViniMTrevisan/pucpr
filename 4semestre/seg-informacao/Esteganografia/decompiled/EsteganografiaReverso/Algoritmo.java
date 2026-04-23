/*
 * Decompiled with CFR 0.152.
 */
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Algoritmo {
    public static BufferedImage embedText(BufferedImage image, String text, String outputFile) {
        char bitMask = '\u0001';
        int x = 0;
        int y = 0;
        int i = 0;
        while (i < text.length()) {
            int bit = text.charAt(i);
            int j = 0;
            while (j < 8) {
                int flag = bit & bitMask;
                if (flag == 1) {
                    if (x < image.getWidth()) {
                        image.setRGB(x, y, image.getRGB(x, y) | 1);
                        ++x;
                    } else {
                        x = 0;
                        image.setRGB(x, ++y, image.getRGB(x, y) | 1);
                    }
                } else if (x < image.getWidth()) {
                    image.setRGB(x, y, image.getRGB(x, y) & 0xFFFFFFFE);
                    ++x;
                } else {
                    x = 0;
                    image.setRGB(x, ++y, image.getRGB(x, y) & 0xFFFFFFFE);
                }
                bit >>= 1;
                ++j;
            }
            ++i;
        }
        try {
            File outputfile = new File(outputFile);
            ImageIO.write((RenderedImage)image, "png", outputfile);
        }
        catch (IOException iOException) {
            // empty catch block
        }
        return image;
    }

    public static void extractText(BufferedImage image, int length, String outputFile) {
        int bitMask = 1;
        int x = 0;
        int y = 0;
        char[] c = new char[length];
        int i = 0;
        while (i < length) {
            int bit = 0;
            int j = 0;
            while (j < 8) {
                int flag;
                if (x < image.getWidth()) {
                    flag = image.getRGB(x, y) & bitMask;
                    ++x;
                } else {
                    x = 0;
                    flag = image.getRGB(x, ++y) & bitMask;
                }
                if (flag == 1) {
                    bit >>= 1;
                    bit |= 0x80;
                } else {
                    bit >>= 1;
                }
                ++j;
            }
            c[i] = (char)bit;
            ++i;
        }
        try {
            JOptionPane.showMessageDialog(null, "Texto extraido: " + new String(c));
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao extrair texto");
        }
    }

    public static BufferedImage embedImage(BufferedImage imageC, BufferedImage imageS, String outputFile) {
        int bitMask = 1;
        int x = 0;
        int y = 0;
        int l = imageS.getWidth() * imageS.getHeight();
        int[] array = new int[l];
        int i = 0;
        while (i < array.length) {
            if (x < imageS.getWidth()) {
                array[i] = imageS.getRGB(x, y);
                ++x;
            } else {
                x = 0;
                array[i] = imageS.getRGB(x, ++y);
            }
            ++i;
        }
        x = 0;
        y = 0;
        i = 0;
        while (i < l) {
            int j = 0;
            while (j < 32) {
                int flag = array[i] & bitMask;
                if (flag == 1) {
                    if (x < imageC.getWidth()) {
                        imageC.setRGB(x, y, imageC.getRGB(x, y) | 1);
                        ++x;
                    } else {
                        x = 0;
                        imageC.setRGB(x, ++y, imageC.getRGB(x, y) | 1);
                    }
                } else if (x < imageC.getWidth()) {
                    imageC.setRGB(x, y, imageC.getRGB(x, y) & 0xFFFFFFFE);
                    ++x;
                } else {
                    x = 0;
                    imageC.setRGB(x, ++y, imageC.getRGB(x, y) & 0xFFFFFFFE);
                }
                array[i] = array[i] >> 1;
                ++j;
            }
            ++i;
        }
        try {
            File outputfile = new File(outputFile);
            ImageIO.write((RenderedImage)imageC, "png", outputfile);
        }
        catch (IOException iOException) {
            // empty catch block
        }
        return imageC;
    }

    public static void extractImage(BufferedImage image, int width, int height, String outputFile) {
        int bitMask = 1;
        int x = 0;
        int y = 0;
        BufferedImage imageStore = new BufferedImage(width, height, 1);
        int pixelNumber = width * height;
        int[] array = new int[pixelNumber];
        int i = 0;
        while (i < pixelNumber) {
            int bit = 0;
            int j = 0;
            while (j < 32) {
                int flag;
                if (x < image.getWidth()) {
                    flag = image.getRGB(x, y) & bitMask;
                    ++x;
                } else {
                    x = 0;
                    flag = image.getRGB(x, ++y) & bitMask;
                }
                if (flag == 1) {
                    bit >>= 1;
                    bit |= Integer.MIN_VALUE;
                } else {
                    bit >>= 1;
                    bit &= Integer.MAX_VALUE;
                }
                ++j;
            }
            array[i] = bit;
            ++i;
        }
        x = 0;
        y = 0;
        i = 0;
        while (i < array.length) {
            if (x < width) {
                imageStore.setRGB(x, y, array[i]);
                ++x;
            } else {
                x = 0;
                imageStore.setRGB(x, ++y, array[i]);
            }
            ++i;
        }
        try {
            if (outputFile == null) {
                imageStore = Util.resize(imageStore);
                JFrame frame = new JFrame("Esteganografia - Imagem ampliada");
                JPanel panel = new JPanel();
                JLabel label1 = new JLabel(new ImageIcon(imageStore));
                panel.add(label1);
                frame.add(panel);
                frame.pack();
                frame.setExtendedState(6);
                frame.setVisible(true);
            } else {
                File outputfile = new File(outputFile);
                ImageIO.write((RenderedImage)imageStore, "png", outputfile);
            }
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }
}

