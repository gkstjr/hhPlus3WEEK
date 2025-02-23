package hhplus.ecommerce;

import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Configuration;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;
@Configuration
public class TestContainersConfiguration {


    static {
        // MySQL 설정 (docker-compose의 mysql 컨테이너와 연동)
        System.setProperty("spring.datasource.url", "jdbc:mysql://localhost:3307/hhplus?characterEncoding=UTF-8&serverTimezone=UTC&allowPublicKeyRetrieval=true&useSSL=false");
        System.setProperty("spring.datasource.username", "test");
        System.setProperty("spring.datasource.password", "test");

        // Redis 설정 (docker-compose의 redis 컨테이너와 연동)
        System.setProperty("spring.redis.host", "localhost");
        System.setProperty("spring.redis.port", "6379");

        // Kafka 설정 (docker-compose의 kafka 컨테이너와 연동)
        System.setProperty("spring.kafka.bootstrap-servers", "localhost:9092");

        // JPA 설정
        System.setProperty("spring.jpa.properties.hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        System.setProperty("spring.jpa.hibernate.ddl-auto", "create");
        System.setProperty("spring.jpa.show-sql", "true");
    }
}