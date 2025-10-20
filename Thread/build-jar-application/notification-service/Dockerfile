# ---------- Dockerfile ----------
FROM eclipse-temurin:21-jdk-jammy

# Thiết lập timezone cho container
ENV TZ=Asia/Ho_Chi_Minh
RUN apt-get update && apt-get install -y tzdata && \
    ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

ARG JAR_FILE=*.jar
WORKDIR /app

# Copy file jar
COPY ${JAR_FILE} app.jar

# Copy file cấu hình
COPY application.yaml application.yaml

# Cho phép ghi đè options nếu cần
# Thêm timezone cho JVM luôn ở đây
ENV JAVA_OPTS="-Duser.timezone=Asia/Ho_Chi_Minh"

# Expose port (tuỳ service)
EXPOSE 8700

# Chạy app với cấu hình bên ngoài jar
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar --spring.config.location=file:/app/application.yaml"]
