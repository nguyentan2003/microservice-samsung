@echo off
echo ==== Starting all microservices ====

start java -jar api-gateway-service/api-gateway-0.0.1-SNAPSHOT.jar --spring.config.location=api-gateway-service/application.yaml
start java -jar customer-summary-service/customer-summary-service-0.0.1-SNAPSHOT.jar --spring.config.location=customer-summary-service/application.yaml
start java -jar identity-service/identity-service-0.0.1-SNAPSHOT.jar --spring.config.location=identity-service/application.yaml
start java -jar notification-service/notification-service-0.0.1-SNAPSHOT.jar --spring.config.location=notification-service/application.yaml
start java -jar order-service/order-service-0.0.1-SNAPSHOT.jar --spring.config.location=order-service/application.yaml
start java -jar payment-service/payment-service-0.0.1-SNAPSHOT.jar --spring.config.location=payment-service/application.yaml
start java -jar product-service/product-service-0.0.1-SNAPSHOT.jar --spring.config.location=product-service/application.yaml
start java -jar profile-service/profile-service-0.0.1-SNAPSHOT.jar --spring.config.location=profile-service/application.yaml

echo ==== All services started ====
pause
