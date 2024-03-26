package com.juhyun.springlock.api;

import com.juhyun.springlock.service.CouponService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class CouponController {

    private final CouponService service;

    public CouponController(CouponService service) {
        this.service = service;
    }

    @PostMapping("/bulk-issue")
    public void bulkIssue(@RequestBody CouponIssueRequest request) {
        service.bulkIssue(request.getCount());
    }

    @PostMapping("/jpa-issue-save")
    public void bulkIssueJpaSave(@RequestBody CouponIssueRequest request) {
        service.bulkIssueJpaSave(request.getCount());
    }

    @PostMapping("/jpa-issue-saveAll")
    public void bulkIssueJpaSaveAll(@RequestBody CouponIssueRequest request) {
        service.bulkIssueJpaSaveAll(request.getCount());
    }

    @PostMapping("/admit")
    public void admit(@RequestBody CouponAdmitRequest request) {
        service.admit(request.couponNumber, request.amountPrice);
    }

    @Retryable(
            retryFor = OptimisticLockingFailureException.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 500),
            recover = "recoverOptimisticLockingFailureException"
    )
    @PostMapping("/optimisticLock-admit")
    public void optimisticLockAdmit(@RequestBody CouponAdmitRequest request) {
        service.optimisticLockAdmit(request.couponNumber, request.amountPrice);
    }

    @Recover
    public void recoverOptimisticLockingFailureException(CouponAdmitRequest request) {
        log.info("recoverOptimisticLockingFailureException");
        log.info("CouponNumber: {}, admit: {}", request.couponNumber, request.amountPrice);
    }

    @PostMapping("/pessimisticLock-admit")
    public void pessimisticLockAdmit(@RequestBody CouponAdmitRequest request) {
        service.pessimisticLockAdmit(request.couponNumber, request.amountPrice);
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CouponIssueRequest {
        private int count;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class CouponAdmitRequest {
        private String couponNumber;
        private Long amountPrice;
    }

}
