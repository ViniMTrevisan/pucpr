from flask import Flask, request, jsonify, send_from_directory
from ollama import chat
from pydantic import BaseModel

app = Flask(__name__)

class Country(BaseModel):
    name: str
    capital: str
    languages: list[str]

# NOVO MODELO E FUNÇÃO
class CountryAlt(BaseModel):
    country_name: str
    main_city: str
    spoken_languages: list[str]

@app.route('/ask_alt', methods=['POST'])
def ask_country_alt():
    data = request.get_json()

    if not data or 'message' not in data:
        return jsonify({'error': 'Missing "message" field'}), 400

    user_message = data['message']

    # Chamar o modelo com o novo formato
    response = chat(
        messages=[{'role': 'user', 'content': user_message}],
        model='gemma3',
        format=CountryAlt.model_json_schema(),
    )

    # Validar e converter para objeto CountryAlt
    country = CountryAlt.model_validate_json(response.message.content)

    # Retornar como JSON
    return jsonify(country.dict())

@app.route('/')
def index():
    return send_from_directory(app.static_folder, 'index.html')

if __name__ == '__main__':
    app.run(debug=True)
