FROM maven:3.8.6-openjdk-17 as build
COPY . /app
WORKDIR /app
RUN mvn install -DskipTests
FROM maven:3.8.6-openjdk-17 as base
WORKDIR /app
COPY --from=build /app/target/*.jar /usr/local/lib/statosu.jar
ENTRYPOINT ["java","-jar","/usr/local/lib/statosu.jar"]