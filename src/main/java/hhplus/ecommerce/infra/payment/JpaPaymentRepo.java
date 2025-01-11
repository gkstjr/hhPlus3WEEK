package hhplus.ecommerce.infra.payment;

import hhplus.ecommerce.domain.payment.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPaymentRepo extends JpaRepository<Payment,Long> {
}
