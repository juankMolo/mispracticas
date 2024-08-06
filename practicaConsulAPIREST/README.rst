mysql> CREATE TABLE products (
    -> id INT NOT NULL AUTO_INCREMENT,
    -> name VARCHAR(255) NOT NULL,
    -> price DECIMAL(10, 2) NOT NULL,
    -> quantity INT NOT NULL,
    -> PRIMARY KEY (id)
    -> );

mysql> INSERT INTO products (name, price, quantity) VALUES ('rice', 1.50, 100);
mysql> INSERT INTO products (name, price, quantity) VALUES ('almonds', 3.75, 50);
