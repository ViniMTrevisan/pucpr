import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class FloodFillerMelhorado {
    
    public FloodFillerMelhorado() {
    }
    
    public void preencher(String arquivoEntrada, String arquivoSaida, Ponto pontoInicial, 
                         Color novaCor, EstruturaDeDados estrutura) {
        try {
            BufferedImage imagem = ImageIO.read(new File(arquivoEntrada));
            int largura = imagem.getWidth();
            int altura = imagem.getHeight();
            
            int corOriginal = imagem.getRGB(pontoInicial.x, pontoInicial.y);
            int corNova = novaCor.getRGB();
            
            if (corOriginal == corNova) {
                return;
            }
            
            boolean[][] visitados = new boolean[largura][altura];
            
            int[][] direcoes = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
            
            estrutura.adicionar(pontoInicial);
            visitados[pontoInicial.x][pontoInicial.y] = true;
            while (!estrutura.estaVazia()) {
                Ponto pontoAtual = estrutura.remover();
                int x = pontoAtual.x;
                int y = pontoAtual.y;
                
                if (x < 0 || x >= largura || y < 0 || y >= altura) {
                    continue;
                }
                
                int corAtual = imagem.getRGB(x, y);
                
                if (corAtual == corOriginal) {
                    imagem.setRGB(x, y, corNova);
                    
                    for (int[] direcao : direcoes) {
                        int novoX = x + direcao[0];
                        int novoY = y + direcao[1];
                        
                        if (novoX >= 0 && novoX < largura && novoY >= 0 && novoY < altura) {
                            if (!visitados[novoX][novoY]) {
                                visitados[novoX][novoY] = true;
                                estrutura.adicionar(new Ponto(novoX, novoY));
                            }
                        }
                    }
                }
            }
            
            ImageIO.write(imagem, "png", new File(arquivoSaida));
            System.out.println("Imagem salva em: " + arquivoSaida);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
