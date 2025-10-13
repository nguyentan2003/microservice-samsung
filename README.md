# microservice-samsung
topic 9 samsung stp 2025

[//]: # chay kafka : docker-compose up -d

- build .jar bỏ qua test : mvn clean package -DskipTests
- chạy jar : java -jar target/yourproject-0.0.1-SNAPSHOT.jar
- chạy port khác : java -jar target/yourproject.jar --server.port=9090
- nếu muốn cấu hình file application.yml khác thì để ngang cấp với .jar
- chỉ định cụ thể file cấu hình:  java -jar app.jar --spring.config.location=./myconfig/application.yml
