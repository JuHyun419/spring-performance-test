package com.juhyun.springlock.repository;

import com.juhyun.springlock.domain.CouponEntity;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import java.util.Optional;

public interface CouponRepository extends JpaRepository<CouponEntity, String>, CustomCouponRepository {

    @Lock(LockModeType.OPTIMISTIC)
    @Query("select c from CouponEntity c where c.couponNumber = :couponNumber")
    Optional<CouponEntity> findByCouponNumberByVersion(String couponNumber);


    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select c from CouponEntity c where c.couponNumber = :couponNumber")
    @QueryHints({@QueryHint(name = "jakarta.persistence.lock.timeout", value = "3000")})
    Optional<CouponEntity> findByCouponNumberByForUpdate(String couponNumber);
}
