from flask import Flask
from users.controllers.user_controller import user_controller
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
    name=app.config['USER_SERVICE_ID'],
    interval='5s',
    tags=['user-service'],
    port=5002,
    httpcheck='http://localhost:5002/healthcheck'
)

# Registrando el blueprint del controlador de usuarios
app.register_blueprint(user_controller)

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5002)

