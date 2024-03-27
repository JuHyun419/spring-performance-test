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
| MTT(latency)   | 1,070ms     | 960ms | 2,500ms | 3,604ms | 6,800ms |
| Success        | 100         | 200   | 488     | 980     | 1,743   |
| Errors         | 0           | 0     | 12      | 20      | 257     |

### Pessimistic Lock Coupon Admit Performance (Hikari custom configuration)

- maximum, minimum pool = 30 ~ 50

|                | performance | -      | -       | -       | -       |
|----------------|-------------|--------|---------|---------|---------|
| Vuser          | 100         | 200    | 500     | 1000    | 2000    |
| TPS (peek TPS) | 50          | 50     | 80      | 110     | 133     |
| MTT(latency)   | 124ms       | 1120ms | 1,920ms | 3,400ms | 5,650ms |
| Success        | 100         | 200    | 500     | 893     | 1,602   |
| Errors         | 0           | 0      | 0       | 107     | 398     |

### Pessimistic Lock vs Optimistic Lock (change Vusers)

|                | Optimistic-Lock | Pessimistic-Lock | 
|----------------|-----------------|------------------|
| Vusers         | 2               | 2                | 
| Run Count      | 5,000           | 5,000            |
| Total Count    | 10,000          | 10,000           |
| TPS (peek TPS) | 64              | 131              | 
| MTT(latency)   | 28.44ms         | 14.65ms          | 
| Success        | 9,988           | 10,000           | 
| Errors         | 12              | 0                | 


|                | Optimistic-Lock | Pessimistic-Lock | 
|----------------|-----------------|------------------|
| Vusers         | 1               | 1                | 
| Run Count      | 10,000          | 10,000           |
| Total Count    | 10,000          | 10,000           |
| TPS (peek TPS) | 53              | 59               | 
| MTT(latency)   | 17.91ms         | 15.91ms          | 
| Success        | 10,000          | 10,000           | 
| Errors         | 0               | 0                | 


|                | Optimistic-Lock | Pessimistic-Lock | 
|----------------|-----------------|------------------|
| Vusers         | 2               | 2                | 
| Run Count      | 5,000           | 5,000            |
| Total Count    | 10,000          | 10,000           |
| TPS (peek TPS) | 64              | 131              | 
| MTT(latency)   | 28.44ms         | 14.65ms          | 
| Success        | 9,988           | 10,000           | 
| Errors         | 12              | 0                | 


|                | Optimistic-Lock | Pessimistic-Lock | 
|----------------|-----------------|------------------|
| Vusers         | 20              | 20               | 
| Run Count      | 100             | 100              |
| Total Count    | 2,000           | 2,000            |
| TPS (peek TPS) | 62              | 99               | 
| MTT(latency)   | 259ms           | 187ms            | 
| Success        | 1,734           | 2,000            | 
| Errors         | 266             | 0                | 

