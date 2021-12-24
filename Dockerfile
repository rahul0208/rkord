FROM azul/zulu-openjdk:11
COPY target/rkord.jar rkord.jar
EXPOSE 8080
CMD ["java", "-jar", "-Dspring.profiles.active=prod","/rkord.jar"]