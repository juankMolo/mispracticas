from flask import Flask
from products.controllers.product_controller import product_controller
from db.db import db
from flask_cors import CORS
from flask_consulate import Consul

app = Flask(__name__)
CORS(app)
app.config.from_object('config.Config')
db.init_app(app)

@app.route('/healthcheck')
def health_check():
    return '', 200

# Consul
consul = Consul(app=app)
# Fetch the configuration:
consul.apply_remote_config(namespace='mynamespace/')
# Register Consul service:
consul.register_service(
    name=app.config['PRODUCT_SERVICE_ID'],
    interval='5s',
    tags=['product-service'],
    port=5003,
    httpcheck='http://localhost:5003/healthcheck'
)

# Registrando el blueprint del controlador de productos
app.register_blueprint(product_controller)

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5003)

