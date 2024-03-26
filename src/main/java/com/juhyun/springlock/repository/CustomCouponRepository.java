package com.juhyun.springlock.repository;

import com.juhyun.springlock.domain.CouponEntity;

import java.util.List;

public interface CustomCouponRepository {

    void bulkInsert(List<CouponEntity> coupons);

}
