# 빌드 스테이지
FROM openjdk:17-jdk-slim AS builder

WORKDIR /app

# Gradle Wrapper와 빌드 스크립트 복사
COPY gradlew .
COPY gradle/ gradle/
COPY build.gradle .
COPY settings.gradle .

# gradlew 실행 권한 부여
RUN chmod +x ./gradlew

# 소스 코드 복사
COPY src/ src/

# 애플리케이션 빌드
RUN ./gradlew clean bootJar --no-daemon

# 런타임 스테이지
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# 빌드된 JAR 파일 복사
COPY --from=builder /app/build/libs/*.jar app.jar

# 포트 노출
EXPOSE 11001

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=${SPRING_PROFILES_ACTIVE:prod}"]