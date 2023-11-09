# Utiliza una imagen base con Gradle 8.4 y Java 17
FROM eclipse-temurin:17-jdk-alpine as compiler

COPY . .
RUN ./gradlew build -x test --no-daemon

FROM eclipse-temurin:17-jre-alpine

ENV APP_JAR_FILE tenpo
ENV APP_HOME /user/app

COPY --from=compiler /build/libs/. $APP_HOME/
WORKDIR $APP_HOME
RUN apk add --no-cache redis

# Configuraciones de la JVM (Opcional)
ENV JAVA_OPTS="-Xmx768m -Xms256m"
RUN redis-server &

# Comando para ejecutar la aplicación al iniciar el contenedor
ENTRYPOINT ["sh", "-c"]
CMD ["java $JAVA_OPTS -jar ${APP_JAR_FILE}.jar"]
