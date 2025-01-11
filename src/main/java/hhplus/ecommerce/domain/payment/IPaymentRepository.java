package hhplus.ecommerce.domain.payment;

import hhplus.ecommerce.domain.payment.model.Payment;

public interface IPaymentRepository {
    Payment save(Payment pay);

    void deleteAll();
}
