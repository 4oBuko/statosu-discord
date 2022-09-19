FROM maven:3.8.6-openjdk-18
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM openjdk:18-jdk-alpine
COPY --from=build /home/app/target/*.jar /usr/local/lib/statosu.jar
ENTRYPOINT ["java","-jar","/usr/local/lib/statosu.jar"]
ONBUILD