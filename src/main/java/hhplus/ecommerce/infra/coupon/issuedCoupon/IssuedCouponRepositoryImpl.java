package hhplus.ecommerce.infra.coupon.issuedCoupon;

import hhplus.ecommerce.domain.coupon.issuedcoupon.IIssuedCouponRepository;
import hhplus.ecommerce.domain.coupon.issuedcoupon.IssuedCoupon;
import hhplus.ecommerce.domain.coupon.model.Coupon;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class IssuedCouponRepositoryImpl implements IIssuedCouponRepository {
    private final JpaIssuedCouponRepo jpaIssuedCouponRepo;

    public IssuedCouponRepositoryImpl(JpaIssuedCouponRepo jpaIssuedCouponRepo) {
        this.jpaIssuedCouponRepo = jpaIssuedCouponRepo;
    }

    @Override
    public IssuedCoupon save(IssuedCoupon issuedCoupon) {
        return jpaIssuedCouponRepo.save(issuedCoupon);
    }

    @Override
    public void deleteAll() {
        jpaIssuedCouponRepo.deleteAll();
    }

    @Override
    public Optional<IssuedCoupon> findByIdWithCoupon(Long issuedCouponId) {
        return jpaIssuedCouponRepo.findByIdWithCoupon(issuedCouponId);
    }

    @Override
    public List<IssuedCoupon> findAllByUserId(long userId) {
        return jpaIssuedCouponRepo.findAllByUserId(userId);
    }
}
