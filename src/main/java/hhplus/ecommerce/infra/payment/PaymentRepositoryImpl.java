package hhplus.ecommerce.infra.payment;

import hhplus.ecommerce.domain.payment.IPaymentRepository;
import hhplus.ecommerce.domain.payment.model.Payment;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentRepositoryImpl implements IPaymentRepository {
    private final JpaPaymentRepo jpaPaymentRepo;

    public PaymentRepositoryImpl(JpaPaymentRepo jpaPaymentRepo) {
        this.jpaPaymentRepo = jpaPaymentRepo;
    }

    @Override
    public Payment save(Payment pay) {
        return jpaPaymentRepo.save(pay);
    }

    @Override
    public void deleteAll() {
        jpaPaymentRepo.deleteAll();
    }
}
