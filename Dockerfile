FROM openjdk:11
VOLUME /tmp
EXPOSE 9041
ADD ./target/ms-accountcustomer-0.0.1-SNAPSHOT.jar ms-accountcustomer.jar
ENTRYPOINT ["java","-jar","/ms-accountcustomer.jar"]