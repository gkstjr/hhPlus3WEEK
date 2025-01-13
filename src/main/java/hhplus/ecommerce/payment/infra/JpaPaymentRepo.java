package hhplus.ecommerce.payment.infra;

import hhplus.ecommerce.payment.domain.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPaymentRepo extends JpaRepository<Payment,Long> {
}
