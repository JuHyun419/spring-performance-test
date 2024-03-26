package com.juhyun.springlock.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
class CouponServiceTest {

    @Autowired
    private CouponService service;

    @Test
    void admitTest() throws InterruptedException {
        int size = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(size);

        for (int i = 0; i < size; i++) {
            executorService.execute(() -> {
                try {
                    service.admit("021355961325", 100L);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                latch.countDown();
            });
        }

        latch.await();
    }

    @Test
    void optimisticLockAdmitTest() throws InterruptedException {
        int size = 2;
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(size);

        for (int i = 0; i < size; i++) {
            executorService.execute(() -> {
                try {
                    service.optimisticLockAdmit("000316508337", 1L);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                latch.countDown();
            });
        }

        latch.await();
    }

    @Test
    void pessimisticLockAdmitTest() throws InterruptedException {
        int size = 1_000;
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(size);

        for (int i = 0; i < size; i++) {
            executorService.execute(() -> {
                try {
                    service.pessimisticLockAdmit("000005331429", 10L);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                latch.countDown();
            });
        }

        latch.await();
    }


}
