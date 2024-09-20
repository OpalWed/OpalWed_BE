FROM maven:3.9.9-ibm-semeru-21-jammy AS build
WORKDIR /opal-build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:21 AS deploy
WORKDIR /opal
COPY --from=build /opal-build/target/OpalWed-0.0.1-SNAPSHOT.jar opalweb.jar
RUN rm -rf /opal-build
ENTRYPOINT [ "java", "-jar", "opalweb.jar" ]