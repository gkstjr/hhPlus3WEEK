package hhplus.ecommerce.config;

import org.junit.jupiter.api.Test;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class RedisConfigTest {

    @Autowired
    private RedissonClient redissonClient;

    @Test
    public void testRedisConnection() {
        // Redis Key-Value 테스트
        RBucket<String> bucket = redissonClient.getBucket("testKey");
        bucket.set("testValue");

        String value = bucket.get();
        assertThat(value).isEqualTo("testValue");
    }
}
