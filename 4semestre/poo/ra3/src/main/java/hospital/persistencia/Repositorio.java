package hospital.persistencia;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Repositorio<T extends Serializable> {
    private final String caminhoArquivo;

    public Repositorio(String nomeArquivo) {
        String diretorio = System.getProperty("user.home") + File.separator + "hospital-data";
        new File(diretorio).mkdirs();
        this.caminhoArquivo = diretorio + File.separator + nomeArquivo;
    }

    @SuppressWarnings("unchecked")
    public List<T> carregar() {
        File arquivo = new File(caminhoArquivo);
        if (!arquivo.exists()) {
            return new ArrayList<>();
        }
        try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (List<T>) entrada.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }

    public void salvar(List<T> lista) {
        try (ObjectOutputStream saida = new ObjectOutputStream(new FileOutputStream(caminhoArquivo))) {
            saida.writeObject(new ArrayList<>(lista));
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar dados: " + e.getMessage(), e);
        }
    }
}
