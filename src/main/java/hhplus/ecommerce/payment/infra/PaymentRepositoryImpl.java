package hhplus.ecommerce.payment.infra;

import hhplus.ecommerce.payment.domain.IPaymentRepository;
import hhplus.ecommerce.payment.domain.model.Payment;
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
