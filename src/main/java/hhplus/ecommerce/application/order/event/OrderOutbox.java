package hhplus.ecommerce.application.order.event;

import hhplus.ecommerce.support.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class OrderOutbox extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Column(unique = true)
    private String eventId;
    @Column(nullable = false)
    private String payload;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderOutboxStatus status;  //(PENDING, PUBLISHED)

    public static OrderOutbox create(String payload , String eventId) {
        OrderOutbox outbox = new OrderOutbox();
        outbox.eventId = eventId;
        outbox.payload = payload;
        outbox.status = OrderOutboxStatus.PENDING;

        return outbox;
    }

    public void checkPublished() {
        status = OrderOutboxStatus.PUBLISHED;
    }

    public static enum OrderOutboxStatus {
        PENDING, //발행 검증 전
        PUBLISHED //발행 검증완료
    }
}


