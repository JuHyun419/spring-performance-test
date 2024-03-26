package com.juhyun.springlock.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "COUPONS")
public class CouponEntity {

    @Id
    @Column(name = "COUPON_NUMBER", nullable = false)
    private String couponNumber;

    @Column(name = "PRICE")
    private Long price;

    @Column(name = "ADMIT_YN", length = 1)
    private String admitYn;

    @Column(name = "ADMIT_DATE")
    private LocalDateTime admitDate;

//    @Version
//    private Integer version;

    public static CouponEntity of(String couponNumber) {
        return CouponEntity.builder()
                .couponNumber(couponNumber)
                .price(100_000L)
                .admitYn("N")
                .admitDate(null)
                .build();
    }

    public void admit(Long admitPrice) {
        if (this.price < admitPrice) {
            final String message = String.format("해당 쿠폰은 잔액 부족으로 승인이 불가능합니다. 쿠폰번호: %s, 잔액: %d, 승인금액: %d", couponNumber, price, admitPrice);
            throw new RuntimeException(message);
        }

        this.price -= admitPrice;
        this.admitYn = "Y";
        this.admitDate = LocalDateTime.now();
    }
}
