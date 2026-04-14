# app.py
from flask import Flask, request, jsonify

app = Flask(__name__)

@app.route('/', methods=['GET'])
def index():
    return jsonify({
        'message': 'Flask API is running!',
        'endpoints': {
            '/': 'GET - This info page',
            '/test': 'POST - Test connection with JSON body'
        },
        'status': 'success'
    })

@app.route('/test', methods=['POST'])
def test_connection():
    try:
        data = request.get_json()
        message = data.get('message', 'No message')
        return jsonify({'response': f'Backend received: {message}', 'status': 'success'})
    except Exception as e:
        return jsonify({'error': str(e), 'status': 'error'})

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000, debug=True)