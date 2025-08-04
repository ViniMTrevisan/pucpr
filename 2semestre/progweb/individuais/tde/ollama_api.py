from flask import Flask, request, jsonify
from flask_cors import CORS
import requests

app = Flask(__name__)
CORS(app)

OLLAMA_URL = "http://localhost:11434/api/generate"
OLLAMA_MODEL = "mistral"  

@app.route('/api/perguntar', methods=['POST'])
def perguntar():
    dados = request.json
    pergunta = dados.get('pergunta')

    if not pergunta:
        return jsonify({'erro': 'Pergunta não fornecida'}), 400

    resposta = requests.post(OLLAMA_URL, json={
        "model": OLLAMA_MODEL,
        "prompt": pergunta,
        "stream": False
    })

    if resposta.status_code != 200:
        return jsonify({'erro': 'Erro ao consultar o Ollama'}), 500

    conteudo = resposta.json()
    return jsonify({'resposta': conteudo.get('response')})

if __name__ == '__main__':
    app.run(debug=True)