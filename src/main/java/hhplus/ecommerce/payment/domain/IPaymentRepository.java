package hhplus.ecommerce.payment.domain;

import hhplus.ecommerce.payment.domain.model.Payment;

public interface IPaymentRepository {
    Payment save(Payment pay);

    void deleteAll();
}
