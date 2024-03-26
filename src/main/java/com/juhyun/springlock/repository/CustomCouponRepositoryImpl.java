package com.juhyun.springlock.repository;

import com.juhyun.springlock.domain.CouponEntity;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CustomCouponRepositoryImpl implements CustomCouponRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final JdbcTemplate jdbcTemplate;

    public CustomCouponRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate, JdbcTemplate jdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void bulkInsert(List<CouponEntity> coupons) {
        final String sql =
                "INSERT INTO factory.coupons (coupon_number, price, admit_yn, admit_date) VALUES (?, ?, 'N', null)";
        
        jdbcTemplate.batchUpdate(
                sql,
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        CouponEntity entity = coupons.get(i);

                        ps.setString(1, entity.getCouponNumber());
                        ps.setLong(2, entity.getPrice());
                    }

                    @Override
                    public int getBatchSize() {
                        return coupons.size();
                    }
                }
        );

    }
}
