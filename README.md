## 성능 비교
- Using [nGrinder](https://github.com/naver/ngrinder)

### 사양

- Apple M1 Pro, 32GB, Sonoma 14.1

1. JdbcTemplate vs JPA bulk insert
2. JPA Optimistic Lock vs Pessimistic Lock

### JdbcTemplate vs JPA (save, saveAll) Bulk Insert Performance

|                             | 100건    | 1,000건   | 10,000건   | 100,000건  | 1,000,000건 |
|-----------------------------|---------|----------|-----------|-----------|------------|
| bulk insert(JdbcTemplate)   | 68ms    | 383ms    | 616ms     | 3,500ms   | 36,915ms   | 
| save(JPA)                   | 1,453ms | 11,334ms | 162,297ms | x         | x          |
| saveAll(JPA, batch_size=30) | 300ms   | 1,600ms  | 16,473ms  | 179,298ms | x          |

### Pessimistic Lock Coupon Admit Performance (Hikari default configuration)
- https://github.com/brettwooldridge/HikariCP (maximum, minimum pool = 10)

|                | performance | -     | -       | -       | -       |
|----------------|-------------|-------|---------|---------|---------|
| Vuser          | 100         | 200   | 500     | 1000    | 2000    |
| TPS (peek TPS) | 50          | 50    | 80      | 122     | 108     |
| MTT(lagency)   | 1,070ms     | 960ms | 2,500ms | 3,604ms | 6,800ms |
| Success        | 100         | 200   | 488     | 980     | 1,743   |
| Errors         | 0           | 0     | 12      | 20      | 257     |


### Pessimistic Lock Coupon Admit Performance (Hikari custom configuration)
- maximum, minimum pool = 30 ~ 50

|                | performance | -      | -       | -       | -       |
|----------------|-------------|--------|---------|---------|---------|
| Vuser          | 100         | 200    | 500     | 1000    | 2000    |
| TPS (peek TPS) | 50          | 50     | 80      | 110     | 133     |
| MTT(lagency)   | 124ms       | 1120ms | 1,920ms | 3,400ms | 5,650ms |
| Success        | 100         | 200    | 500     | 893     | 1,602   |
| Errors         | 0           | 0      | 0       | 107     | 398     |

- Optimistic Lock admit, Vuser:2, Run Count: 5000 (Total Run Count: 10,000)
    - TPS: 65, MTT: 28.44
    - success: 9,988, error: 12

- Pessimistic Lock admit, Vuser:2, Run Count: 5000 (Total Run Count: 10,000)
    - TPS: 131, MTT: 14.65
    - success: 10,000, error: 0
