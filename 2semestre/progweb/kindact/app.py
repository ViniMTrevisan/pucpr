from flask import Flask, render_template, request

app = Flask(__name__)

@app.route('/')
def login():
    return render_template("login.html")


@app.route('/cadastro')
def cadastro():
    return render_template('cadastro.html')

@app.route('/principal')
def principal():
    return render_template('principal.html')

@app.route('/ongs')
def ongs():
    return render_template('ongs.html')

if __name__ == '__main__':
    app.run(debug=True, port=8081)
