# ---------- Dockerfile ----------
FROM eclipse-temurin:21-jdk-jammy

ARG JAR_FILE=*.jar
WORKDIR /app

# Copy file jar
COPY ${JAR_FILE} app.jar

# Copy file cấu hình
COPY application.yaml application.yaml

# Cho phép ghi đè options nếu cần
ENV JAVA_OPTS=""

# Expose port (tuỳ service)
EXPOSE 8888

# Chạy app với cấu hình bên ngoài jar
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar --spring.config.location=file:/app/application.yaml"]
