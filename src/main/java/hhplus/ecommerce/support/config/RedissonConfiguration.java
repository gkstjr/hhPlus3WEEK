package hhplus.ecommerce.support.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfiguration {

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        String redisHost = System.getProperty("spring.redis.host");
        String redisPort = System.getProperty("spring.redis.port");
        config.useSingleServer().setAddress("redis://" + redisHost + ":" + redisPort);
        return Redisson.create(config);
    }
}
