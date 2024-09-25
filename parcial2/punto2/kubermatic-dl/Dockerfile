FROM python:3.8-slim

# Instalar dependencias del sistema, incluyendo libgomp1 y libquadmath0
RUN apt-get update && apt-get install -y \
    libgomp1 \
    libquadmath0 \
    && apt-get clean

# Crear el directorio de trabajo
WORKDIR /app

# Copiar el archivo de dependencias
COPY requirements.txt .

# Instalar dependencias de Python
RUN pip install --no-cache-dir -r requirements.txt

# Copiar el código de la aplicación
COPY app.py .

# Exponer el puerto 5000
EXPOSE 5000

# Ejecutar la aplicación
CMD ["python", "app.py"]

