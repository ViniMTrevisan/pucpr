from flask import Flask, request, jsonify, send_from_directory
from ollama import chat
from pydantic import BaseModel

app = Flask(__name__, static_folder="static")

class Country(BaseModel):
    name: str
    capital: str
    languages: list[str]

class Scientist(BaseModel):
    name: str
    field: str
    notable_work: str

@app.route('/ask', methods=['POST'])
def ask_country():
    data = request.get_json()
    if not data or 'message' not in data:
        return jsonify({'error': 'Missing "message" field'}), 400
    user_message = data['message']
    response = chat(
        messages=[{'role': 'user', 'content': user_message}],
        model='gemma3',
        format=Country.model_json_schema(),
    )
    country = Country.model_validate_json(response.message.content)
    return jsonify(country.dict())

@app.route('/ask_scientist', methods=['POST'])
def ask_scientist():
    data = request.get_json()
    if not data or 'message' not in data:
        return jsonify({'error': 'Missing "message" field'}), 400
    user_message = data['message']
    response = chat(
        messages=[{'role': 'user', 'content': user_message}],
        model='gemma3',
        format=Scientist.model_json_schema(),
    )
    scientist = Scientist.model_validate_json(response.message.content)
    return jsonify(scientist.dict())

@app.route('/')
def index():
    return send_from_directory(app.static_folder, 'index.html')

if __name__ == '__main__':
    app.run(debug=True)