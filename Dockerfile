FROM maven:latest as build
COPY . /app
WORKDIR /app
RUN mvn install -DskipTests
FROM maven:latest as base
WORKDIR /app
COPY --from=build /app/target/*.jar /usr/local/lib/statosu.jar
ENTRYPOINT ["java","-jar","/usr/local/lib/statosu.jar"]