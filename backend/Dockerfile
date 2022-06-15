FROM openjdk:11
VOLUME /tmp
EXPOSE 8080
ADD ./target/crudemployees-0.0.1-SNAPSHOT.jar crudemployees.jar
ENTRYPOINT ["java","-jar","/crudemployees.jar"]