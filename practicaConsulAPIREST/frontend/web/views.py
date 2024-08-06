from flask import Flask, render_template
from flask_cors import CORS
from flask_consulate import Consul

app = Flask(__name__)
CORS(app)
app.config.from_object('config.Config')

@app.route('/healthcheck')
def health_check():
    return '', 200

# Consul
consul = Consul(app=app)
# Fetch the configuration:
consul.apply_remote_config(namespace='mynamespace/')
# Register Consul service:
consul.register_service(
    name=app.config['FRONTEND_SERVICE_ID'],
    interval='5s',
    tags=['frontend'],
    port=5001,
    httpcheck='http://localhost:5001/healthcheck'
)

@app.route('/')
def index():
    return render_template('index.html')

@app.route('/users')
def users():
    return render_template('users.html')

@app.route('/editUser/<string:id>')
def edit_user(id):
    print("id recibido", id)
    return render_template('editUser.html', id=id)

@app.route('/products')
def products():
    return render_template('products.html')

@app.route('/editProduct/<string:id>')
def edit_product(id):
    print("id recibido", id)
    return render_template('editProduct.html', id=id)

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5001)

