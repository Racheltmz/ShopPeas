from flask import Flask, jsonify
from flask_cors import CORS
from talisman import Talisman

app = Flask(__name__)
Talisman(app)
CORS(app)

@app.route("/")
def hello_world():
    return "<p>Hello, World!</p>"

# Run app
if __name__ == '__main__':
    app.run(port=5000, debug=True)