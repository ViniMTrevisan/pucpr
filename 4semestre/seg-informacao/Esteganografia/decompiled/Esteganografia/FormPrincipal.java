/*
 * Decompiled with CFR 0.152.
 */
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle;
import javax.swing.border.SoftBevelBorder;

public class FormPrincipal
extends JFrame {
    public static BufferedImage imagemOriginal;
    public static BufferedImage imagemOriginalcomImagem;
    public static BufferedImage imagemOriginalcomTexto;
    private static String path;
    private static String imagemEntrada;
    private static String figuraEmbutir;
    private static String textoEmbutir;
    private static String imagemEmbutidaTexto;
    private static String imagemEmbutidaFigura;
    private static String figuraExtraida;
    private static String textoExtraido;
    private JButton btnCalcular;
    private JButton jButton1;
    private JButton jButton2;
    private JButton jButton3;
    private JLabel jLabel1;
    private JLabel jLabel5;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JScrollPane jScrollPane1;
    private JLabel lblCaminho;
    private JLabel lblEmbutir;
    private JLabel lblImagem;
    private JTextArea lblTexto;

    static {
        imagemEmbutidaTexto = String.valueOf(path) + "resultado_Texto.png";
        imagemEmbutidaFigura = String.valueOf(path) + "resultado_Figura.png";
        figuraExtraida = String.valueOf(path) + "resultado_FiguraExtraida.png";
        textoExtraido = String.valueOf(path) + "resultado_TextoExtraido.txt";
    }

    public FormPrincipal() {
        this.initComponents();
    }

    public static void atualizaPatth() {
        imagemEmbutidaTexto = String.valueOf(path) + "resultado_Texto.png";
        imagemEmbutidaFigura = String.valueOf(path) + "resultado_Figura.png";
        figuraExtraida = String.valueOf(path) + "resultado_FiguraExtraida.png";
        textoExtraido = String.valueOf(path) + "resultado_TextoExtraido.txt";
    }

    public static BufferedImage embedText(BufferedImage image, String text) {
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
            File outputfile = new File(imagemEmbutidaTexto);
            ImageIO.write((RenderedImage)image, "png", outputfile);
        }
        catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar imagem.");
        }
        return image;
    }

    public static void extractText(BufferedImage image, int length) {
        System.out.print("Extracting: ");
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
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(textoExtraido)));
            writer.write(c);
            writer.close();
        }
        catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao extrair imagem");
        }
    }

    public static BufferedImage embedImage(BufferedImage imageC, BufferedImage imageS) {
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
            File outputfile = new File(imagemEmbutidaFigura);
            ImageIO.write((RenderedImage)imageC, "png", outputfile);
        }
        catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar a imagem");
        }
        return imageC;
    }

    public static void extractImage(BufferedImage image, int width, int height) {
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
            File outputfile = new File(figuraExtraida);
            ImageIO.write((RenderedImage)imageStore, "png", outputfile);
        }
        catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar a imagem.");
        }
    }

    private void initComponents() {
        this.jPanel1 = new JPanel();
        this.jPanel2 = new JPanel();
        this.jLabel1 = new JLabel();
        this.lblCaminho = new JLabel();
        this.lblImagem = new JLabel();
        this.lblEmbutir = new JLabel();
        this.jLabel5 = new JLabel();
        this.btnCalcular = new JButton();
        this.jButton1 = new JButton();
        this.jButton2 = new JButton();
        this.jButton3 = new JButton();
        this.jScrollPane1 = new JScrollPane();
        this.lblTexto = new JTextArea();
        GroupLayout jPanel1Layout = new GroupLayout(this.jPanel1);
        this.jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 100, Short.MAX_VALUE));
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 100, Short.MAX_VALUE));
        this.setDefaultCloseOperation(3);
        this.jPanel2.setBackground(new Color(85, 85, 85));
        this.jPanel2.setBorder(new SoftBevelBorder(0));
        this.jPanel2.setCursor(new Cursor(0));
        this.jLabel1.setFont(new Font("Lucida Grande", 1, 36));
        this.jLabel1.setForeground(new Color(255, 255, 255));
        this.jLabel1.setHorizontalAlignment(0);
        this.jLabel1.setText("Esteganografia - Prof. Vilmar Abreu Junior");
        this.lblCaminho.setFont(new Font("Lucida Grande", 0, 14));
        this.lblCaminho.setForeground(new Color(255, 255, 255));
        this.lblCaminho.setText("Diretorio de saida (os resultados ser\u00e3o armazenados nessa pasta)");
        this.lblImagem.setFont(new Font("Lucida Grande", 0, 14));
        this.lblImagem.setForeground(new Color(255, 255, 255));
        this.lblImagem.setText("Imagem original (utilizar imagens com resolu\u00e7\u00e3o acima de 800x600):");
        this.lblEmbutir.setFont(new Font("Lucida Grande", 0, 14));
        this.lblEmbutir.setForeground(new Color(255, 255, 255));
        this.lblEmbutir.setText("Imagem para embutir (ser\u00e1 convertida para resolu\u00e7\u00e3o 50x50):");
        this.jLabel5.setFont(new Font("Lucida Grande", 0, 14));
        this.jLabel5.setForeground(new Color(255, 255, 255));
        this.jLabel5.setText("Texto para esconder:");
        this.btnCalcular.setBackground(new Color(102, 255, 102));
        this.btnCalcular.setText("Executar o algoritmo");
        this.btnCalcular.setCursor(new Cursor(12));
        this.btnCalcular.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                FormPrincipal.this.btnCalcularActionPerformed(evt);
            }
        });
        this.jButton1.setFont(new Font("Lucida Grande", 0, 12));
        this.jButton1.setText("Procurar Caminho");
        this.jButton1.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                FormPrincipal.this.jButton1ActionPerformed(evt);
            }
        });
        this.jButton2.setFont(new Font("Lucida Grande", 0, 12));
        this.jButton2.setText("Procurar Imagem 1");
        this.jButton2.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                FormPrincipal.this.jButton2ActionPerformed(evt);
            }
        });
        this.jButton3.setFont(new Font("Lucida Grande", 0, 12));
        this.jButton3.setText("Procurar Imagem 2");
        this.jButton3.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                FormPrincipal.this.jButton3ActionPerformed(evt);
            }
        });
        this.lblTexto.setColumns(20);
        this.lblTexto.setRows(5);
        this.jScrollPane1.setViewportView(this.lblTexto);
        GroupLayout jPanel2Layout = new GroupLayout(this.jPanel2);
        this.jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel1, -1, 1018, Short.MAX_VALUE).addGroup(GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup().addContainerGap().addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.jButton1, -1, -1, Short.MAX_VALUE).addComponent(this.btnCalcular, GroupLayout.Alignment.LEADING, -1, -1, Short.MAX_VALUE).addComponent(this.lblCaminho, GroupLayout.Alignment.LEADING, -1, -1, Short.MAX_VALUE).addComponent(this.lblImagem, GroupLayout.Alignment.LEADING, -1, -1, Short.MAX_VALUE).addComponent(this.lblEmbutir, GroupLayout.Alignment.LEADING, -1, -1, Short.MAX_VALUE).addComponent(this.jLabel5, GroupLayout.Alignment.LEADING, -1, -1, Short.MAX_VALUE).addComponent(this.jScrollPane1).addComponent(this.jButton2, GroupLayout.Alignment.LEADING, -1, -1, Short.MAX_VALUE).addComponent(this.jButton3, GroupLayout.Alignment.LEADING, -1, -1, Short.MAX_VALUE)).addContainerGap()));
        jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addComponent(this.jLabel1, -2, 67, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.lblImagem).addGap(10, 10, 10).addComponent(this.jButton2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.lblEmbutir).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jButton3).addGap(18, 18, 18).addComponent(this.jLabel5).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jScrollPane1, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.lblCaminho).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jButton1).addGap(18, 18, 18).addComponent(this.btnCalcular, -2, 37, -2).addContainerGap(-1, Short.MAX_VALUE)));
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jPanel2, -1, -1, Short.MAX_VALUE));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jPanel2, -2, -1, -2));
        this.pack();
    }

    private void btnCalcularActionPerformed(ActionEvent evt) {
        path = String.valueOf(this.lblCaminho.getText()) + "/";
        FormPrincipal.atualizaPatth();
        imagemEntrada = this.lblImagem.getText();
        figuraEmbutir = this.lblEmbutir.getText();
        textoEmbutir = this.lblTexto.getText();
        BufferedImage originalImageText = null;
        BufferedImage coverImageText = null;
        BufferedImage coverImage = null;
        BufferedImage secretImage = null;
        try {
            originalImageText = Util.openFullImage(imagemEntrada);
            coverImageText = Util.openFullImage(imagemEntrada);
            coverImage = Util.openFullImage(imagemEntrada);
            secretImage = Util.openEmbbededImage(figuraEmbutir);
            coverImageText = FormPrincipal.embedText(coverImageText, textoEmbutir);
            FormPrincipal.extractText(ImageIO.read(new File(imagemEmbutidaTexto)), textoEmbutir.length());
            coverImage = FormPrincipal.embedImage(coverImage, secretImage);
            FormPrincipal.extractImage(ImageIO.read(new File(imagemEmbutidaFigura)), secretImage.getWidth(), secretImage.getHeight());
            JFrame frame = new JFrame("Imagem Original");
            JPanel panel = new JPanel();
            JLabel label = new JLabel(new ImageIcon(originalImageText));
            panel.add(label);
            frame.add(panel);
            frame.pack();
            frame.setVisible(true);
            JFrame frame1 = new JFrame("Imagem com Imagem escondida");
            JPanel panel1 = new JPanel();
            JLabel label1 = new JLabel(new ImageIcon(coverImage));
            panel1.add(label1);
            frame1.add(panel1);
            frame1.pack();
            frame1.setVisible(true);
            JFrame frame2 = new JFrame("Imagem com Texto escondido");
            JPanel panel2 = new JPanel();
            JLabel label2 = new JLabel(new ImageIcon(coverImageText));
            panel2.add(label2);
            frame2.add(panel2);
            frame2.pack();
            frame2.setVisible(true);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Tamanho Desproporcional.");
        }
    }

    private void jButton1ActionPerformed(ActionEvent evt) {
        JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
        fileChooser.setFileSelectionMode(1);
        int result = fileChooser.showOpenDialog(this);
        if (result == 0) {
            File selectedFile = fileChooser.getSelectedFile();
            this.lblCaminho.setText(selectedFile.getAbsolutePath());
        }
    }

    private void jButton2ActionPerformed(ActionEvent evt) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        int result = fileChooser.showOpenDialog(this);
        if (result == 0) {
            File selectedFile = fileChooser.getSelectedFile();
            this.lblImagem.setText(selectedFile.getAbsolutePath());
        }
    }

    private void jButton3ActionPerformed(ActionEvent evt) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        int result = fileChooser.showOpenDialog(this);
        if (result == 0) {
            File selectedFile = fileChooser.getSelectedFile();
            this.lblEmbutir.setText(selectedFile.getAbsolutePath());
        }
    }

    public static void main(String[] args) {
        FormPrincipal f = new FormPrincipal();
        f.setVisible(true);
        f.setDefaultCloseOperation(3);
    }
}

