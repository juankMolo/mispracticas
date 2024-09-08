from db.db import db

class Orders(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    userName = db.Column(db.String(255), nullable=False)
    userEmail = db.Column(db.String(255), nullable=False)
    saleTotal = db.Column(db.Numeric(10, 2), nullable=False)
    date = db.Column(db.DateTime, default=db.func.current_timestamp())
    products = db.Column(db.JSON, nullable=False)  # Campo JSON para almacenar productos y cantidades

    def __init__(self, userName, userEmail, saleTotal, products):
        self.userName = userName
        self.userEmail = userEmail
        self.saleTotal = saleTotal
        self.products = products  # Asegúrate de almacenar los productos cuando se inicializa la orden

