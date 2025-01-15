package hhplus.ecommerce.infra.payment;

import hhplus.ecommerce.domain.payment.PaymentRepository;
import hhplus.ecommerce.domain.payment.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PaymentRepositoryImpl implements PaymentRepository {
    private final JpaPaymentRepo jpaPaymentRepo;

    @Override
    public Payment save(Payment pay) {
        return jpaPaymentRepo.save(pay);
    }

    @Override
    public void deleteAll() {
        jpaPaymentRepo.deleteAll();
    }
}
