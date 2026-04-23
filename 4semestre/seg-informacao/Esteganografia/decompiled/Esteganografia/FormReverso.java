/*
 * Decompiled with CFR 0.152.
 */
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.border.SoftBevelBorder;

public class FormReverso
extends JFrame {
    public static BufferedImage imagemOriginal;
    public static BufferedImage imagemOriginalcomImagem;
    public static BufferedImage imagemOriginalcomTexto;
    private static String path;
    private JButton btnCalcular;
    private JButton btnCalcular1;
    private JButton jButton2;
    private JLabel jLabel1;
    private JLabel jLabel6;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JLabel lblImagem;
    private JTextField txtTamanho;

    public FormReverso() {
        this.initComponents();
    }

    private void initComponents() {
        this.jPanel1 = new JPanel();
        this.jPanel2 = new JPanel();
        this.jLabel1 = new JLabel();
        this.lblImagem = new JLabel();
        this.btnCalcular = new JButton();
        this.jButton2 = new JButton();
        this.jLabel6 = new JLabel();
        this.txtTamanho = new JTextField();
        this.btnCalcular1 = new JButton();
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
        this.jLabel1.setText("Esteganografia Reverso");
        this.lblImagem.setFont(new Font("Lucida Grande", 0, 14));
        this.lblImagem.setForeground(new Color(255, 255, 255));
        this.lblImagem.setText("Imagem modificada:");
        this.btnCalcular.setBackground(new Color(102, 255, 102));
        this.btnCalcular.setText("Extrair texto");
        this.btnCalcular.setCursor(new Cursor(12));
        this.btnCalcular.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                FormReverso.this.btnCalcularActionPerformed(evt);
            }
        });
        this.jButton2.setFont(new Font("Lucida Grande", 0, 12));
        this.jButton2.setText("Procurar Imagem modificada");
        this.jButton2.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                FormReverso.this.jButton2ActionPerformed(evt);
            }
        });
        this.jLabel6.setFont(new Font("Lucida Grande", 0, 14));
        this.jLabel6.setForeground(new Color(255, 255, 255));
        this.jLabel6.setText("Tamanho do texto:");
        this.txtTamanho.setText("0");
        this.txtTamanho.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                FormReverso.this.txtTamanhoActionPerformed(evt);
            }
        });
        this.btnCalcular1.setBackground(new Color(102, 255, 102));
        this.btnCalcular1.setText("Extrair imagem (imagem ser\u00e1 redimensionada)");
        this.btnCalcular1.setCursor(new Cursor(12));
        this.btnCalcular1.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                FormReverso.this.btnCalcular1ActionPerformed(evt);
            }
        });
        GroupLayout jPanel2Layout = new GroupLayout(this.jPanel2);
        this.jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel1, -1, 443, Short.MAX_VALUE).addGroup(jPanel2Layout.createSequentialGroup().addContainerGap().addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.btnCalcular1, -1, -1, Short.MAX_VALUE).addComponent(this.lblImagem, -1, -1, Short.MAX_VALUE).addComponent(this.jButton2, -1, -1, Short.MAX_VALUE).addComponent(this.jLabel6, -1, -1, Short.MAX_VALUE).addGroup(jPanel2Layout.createSequentialGroup().addComponent(this.txtTamanho, -2, 208, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.btnCalcular, -1, -1, Short.MAX_VALUE))).addContainerGap()));
        jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addComponent(this.jLabel1, -2, 67, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.lblImagem).addGap(10, 10, 10).addComponent(this.jButton2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jLabel6).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.txtTamanho, -2, 33, -2).addComponent(this.btnCalcular, -2, 37, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.btnCalcular1, -2, 37, -2).addGap(194, 194, 194)));
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jPanel2, -1, -1, Short.MAX_VALUE));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jPanel2, -2, 273, -2));
        this.pack();
    }

    private void txtTamanhoActionPerformed(ActionEvent evt) {
    }

    private void jButton2ActionPerformed(ActionEvent evt) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        int result = fileChooser.showOpenDialog(this);
        if (result == 0) {
            File selectedFile = fileChooser.getSelectedFile();
            path = selectedFile.getAbsolutePath();
        }
    }

    private void btnCalcularActionPerformed(ActionEvent evt) {
        int tamanhoTexto = 0;
        try {
            if (path == null || path.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Selecione o arquivo!");
                return;
            }
            tamanhoTexto = Integer.parseInt(this.txtTamanho.getText());
            try {
                Algoritmo.extractText(ImageIO.read(new File(path)), tamanhoTexto, null);
            }
            catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro:" + e.getMessage());
            }
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro de convers\u00c3\u00a3o:" + e.getMessage());
        }
    }

    private void btnCalcular1ActionPerformed(ActionEvent evt) {
        int alturaSecreta = 0;
        int larguraSecreta = 0;
        try {
            if (path == null || path.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Selecione o arquivo!");
                return;
            }
            alturaSecreta = Util.defaultHeight;
            larguraSecreta = Util.defaultWidth;
            try {
                Algoritmo.extractImage(ImageIO.read(new File(path)), larguraSecreta, alturaSecreta, null);
            }
            catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro:" + e.getMessage());
            }
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro de convers\u00c3\u00a3o:" + e.getMessage());
        }
    }

    public static void main(String[] args) {
        FormReverso f = new FormReverso();
        f.setVisible(true);
        f.setDefaultCloseOperation(3);
    }
}

