FROM python:3.9

WORKDIR /app

COPY requirements.txt requirements.txt
COPY . .

RUN pip install -r requirements.txt

COPY wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh

ENV FLASK_APP=./run.py

EXPOSE 5002

CMD ["./wait-for-it.sh", "db:3306", "-t", "60", "--", "flask", "run", "--host=0.0.0.0", "--port=5002"]

#CMD ["flask", "run", "--host=0.0.0.0", "--port=5002"]
