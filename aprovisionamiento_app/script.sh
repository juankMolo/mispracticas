#!/bin/bash

# Install MySQL
echo "Installing MySQL."

debconf-set-selections <<< 'mysql-server mysql-server/root_password password root'
debconf-set-selections <<< 'mysql-server mysql-server/root_password_again password root'

sudo apt update
sudo apt install mysql-server -y
sudo systemctl start mysql.service

# Create and fill Database
echo "Creating and filling database."
sudo mysql -h localhost -u root -proot < /home/vagrant/db/init.sql

# Adding permissions to remote access
echo "Adding permissions to remote access."
sudo sed -i 's/127.0.0.1/0.0.0.0/g' /etc/mysql/mysql.conf.d/mysqld.cnf
sudo systemctl restart mysql.service

# Install Python Flask and Flask-MySQLdb
sudo apt install python3-dev default-libmysqlclient-dev build-essential pkg-config mysql-client python3-pip -y
pip3 install Flask==2.3.3
pip3 install flask-cors
pip3 install Flask-MySQLdb
pip install Flask-SQLAlchemy

# Install Docker
echo "Installing Docker."
sudo apt update
sudo apt install -y \
    ca-certificates \
    curl \
    gnupg \
    lsb-release

sudo mkdir -p /etc/apt/keyrings
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /etc/apt/keyrings/docker.gpg

echo \
  "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/ubuntu \
  $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null

sudo apt update
sudo apt install docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin -y

# Install Docker Compose
echo "Installing Docker Compose."
sudo curl -L "https://github.com/docker/compose/releases/download/v2.21.0/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose

echo "Permissions for wait-for-it.sh file."
cd /home/vagrant/microusers/
chmod +x wait-for-it.sh
cd /home/vagrant/microproducts/
chmod +x wait-for-it.sh
cd /home/vagrant/microorders/
chmod +x wait-for-it.sh

echo "Build app."
sudo docker-compose build

echo "Installation complete."