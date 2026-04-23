#!/usr/bin/env bash
set -euo pipefail

BASE_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$BASE_DIR"

mkdir -p out

echo "Executando 5 rodadas com pares predefinidos..."
echo "Em cada rodada:"
echo "1) o script prepara entrada.jpg e secreta.png"
echo "2) abre o Esteganografia.jar"
echo "3) ao fechar a janela, guarda resultados em out/rodada_N"
echo

for i in 1 2 3 4 5; do
  grande="imagens_forum/grandes/forum_grande_${i}.jpg"
  secreta="imagens_forum/secretas/forum_secreta_${i}.png"
  texto="imagens_forum/textos/forum_texto_${i}.txt"
  rodada_dir="out/rodada_${i}"

  if [[ ! -f "$grande" || ! -f "$secreta" || ! -f "$texto" ]]; then
    echo "Arquivos da rodada $i não encontrados. Abortando."
    exit 1
  fi

  cp "$grande" entrada.jpg
  cp "$secreta" secreta.png
  cp "$texto" mensagem_rodada_atual.txt

  mkdir -p "$rodada_dir"

  echo "=============================="
  echo "RODADA $i"
  echo "Imagem base:    $grande"
  echo "Imagem secreta: $secreta"
  echo "Texto:          $texto"
  echo "Conteudo do texto:"
  cat "$texto"
  echo
  echo "Abrindo Esteganografia.jar..."
  echo "Use o texto acima no campo de mensagem e execute."
  echo "Feche a janela do programa para continuar para a próxima rodada."

  java -jar Esteganografia.jar || true

  cp -f entrada.jpg "$rodada_dir/entrada.jpg"
  cp -f secreta.png "$rodada_dir/secreta.png"
  cp -f "$texto" "$rodada_dir/texto.txt"

  # Salva resultados típicos caso existam
  [[ -f out/resultado_Texto.png ]] && cp -f out/resultado_Texto.png "$rodada_dir/resultado_Texto.png"
  [[ -f out/resultado_Figura.png ]] && cp -f out/resultado_Figura.png "$rodada_dir/resultado_Figura.png"
  [[ -f out/resultado_TextoExtraido.txt ]] && cp -f out/resultado_TextoExtraido.txt "$rodada_dir/resultado_TextoExtraido.txt"
  [[ -f out/resultado_FiguraExtraida.png ]] && cp -f out/resultado_FiguraExtraida.png "$rodada_dir/resultado_FiguraExtraida.png"

  echo "Rodada $i finalizada. Resultados em $rodada_dir"
  echo

done

echo "Concluído. Todas as 5 rodadas foram processadas."