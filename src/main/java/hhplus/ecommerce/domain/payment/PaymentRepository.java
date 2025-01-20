package hhplus.ecommerce.domain.payment;

public interface PaymentRepository {
    Payment save(Payment pay);

    void deleteAll();
}
