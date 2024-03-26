package com.juhyun.springlock.service;

import com.juhyun.springlock.domain.CouponEntity;
import com.juhyun.springlock.repository.CouponRepository;
import com.juhyun.springlock.support.CodeConfig;
import com.juhyun.springlock.support.VoucherCodes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CouponService {

    private final CouponRepository repository;

    public CouponService(CouponRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void bulkIssue(int count) {
        List<CouponEntity> coupons = new ArrayList<>();
        CodeConfig codeConfig = new CodeConfig("");

        for (int i = 0; i < count; i++) {
            String couponNumber = VoucherCodes.generate(codeConfig);
            coupons.add(CouponEntity.of(couponNumber));
        }

        repository.bulkInsert(coupons);

        log.info(count + " bulkIssue success!");
    }

    public void bulkIssueJpaSave(int count) {
        CodeConfig codeConfig = new CodeConfig("");

        for (int i = 0; i < count; i++) {
            String couponNumber = VoucherCodes.generate(codeConfig);
            repository.save(CouponEntity.of(couponNumber));
        }

        log.info(count + " bulkIssueSave success!");
    }

    public void bulkIssueJpaSaveAll(int count) {
        List<CouponEntity> coupons = new ArrayList<>();
        CodeConfig codeConfig = new CodeConfig("");

        for (int i = 0; i < count; i++) {
            String couponNumber = VoucherCodes.generate(codeConfig);
            coupons.add(CouponEntity.of(couponNumber));
        }

        repository.saveAll(coupons);

        log.info(count + " bulkIssueSaveAll success!");
    }

    @Transactional
    public void admit(String couponNumber, Long price) {
        final CouponEntity coupon = repository.findById(couponNumber).orElseThrow();

        coupon.admit(price);
    }

    @Transactional
    public void optimisticLockAdmit(String couponNumber, Long price) {
        final CouponEntity coupon = repository.findByCouponNumberByVersion(couponNumber).orElseThrow();

        coupon.admit(price);
    }

    @Transactional
    public void pessimisticLockAdmit(String couponNumber, Long price) {
        final CouponEntity coupon = repository.findByCouponNumberByForUpdate(couponNumber).orElseThrow();

        coupon.admit(price);
    }

}
