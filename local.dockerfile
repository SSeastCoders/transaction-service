FROM maven
WORKDIR /var/www

COPY . .
RUN mvn clean -DskipTests install
#EXPOSE 8224

CMD [ "mvn", "-pl", "transaction-api", "-P", "dev", "spring-boot:run"]