# actual container
FROM eclipse-temurin:21.0.3_9-jre-jammy
ENV APP_HOME=/com/ltc/TelegramBotLinkedin

WORKDIR $APP_HOME
COPY build/libs/TelegramBotLinkedin-0.0.1-SNAPSHOT.jar TelegramBotLinkedin-0.0.1-SNAPSHOT.jar

EXPOSE 8082:8082
CMD ["java", "-jar", "TelegramBotLinkedin-0.0.1-SNAPSHOT.jar"]