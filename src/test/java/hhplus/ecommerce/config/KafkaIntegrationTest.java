package hhplus.ecommerce.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EnableKafka
public class KafkaIntegrationTest {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private final BlockingQueue<String> messages = new LinkedBlockingQueue<>(); //카프카의 메시지수신은 비동기적으로 실행되기 때문에 BlockingQueue 적절하다고 판단

    @KafkaListener(topics = "name", groupId = "test-group")
    public void consume(String message) {
        messages.add(message);
    }

    @Test
    public void 카프카연동테스트_name토픽에서_기만석_반환() throws InterruptedException {
        // given
        String topic = "name";
        String message = "기만석";

        // when
        kafkaTemplate.send(topic, message);

        // then
        String receivedMessage = messages.poll(5, TimeUnit.SECONDS);
        assertThat(receivedMessage).isEqualTo(message);
    }
}