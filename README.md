# microservice-samsung

topic 9 samsung stp 2025

[//]: # chay kafka : docker-compose up -d

-   build .jar bỏ qua test : mvn clean package -DskipTests
-   chạy jar : java -jar target/yourproject-0.0.1-SNAPSHOT.jar
-   chạy port khác : java -jar target/yourproject.jar --server.port=9090
-   nếu muốn cấu hình file application.yml khác thì để ngang cấp với .jar
-   chỉ định cụ thể file cấu hình: java -jar app.jar --spring.config.location=./myconfig/application.yml

k6 run k6-test/order-test-single-token.js

docker compose build : build image khi co cac Dockerfile tung service
docker compose build --no-cache
docker compose up -d

docker compose up --scale customer-summary-service=2 --scale order-service=2 -d --scale product-service=2 -d

docker exec -it mongo mongosh
use topic9_customer_summary_service
db["customer-summary"].deleteMany({})
db["customer-summary"].find().pretty()

use topic9_notification_service
db["notifications"].deleteMany({})

docker exec -it mysql mysql -u root -proot
USE `topic9_order_service`;
delete from orders;

USE `topic9_product_service`;
delete from order_detail;

use admin
db.createUser({
user: "root",
pwd: "root",
roles: [ { role: "root", db: "admin" } ]
})
