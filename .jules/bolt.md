## 2026-05-24 - [Vector Hash Allocation Optimization]
**Learning:** High-frequency math/security operations in Kotlin can be major allocation bottlenecks when using idiomatic collection transforms like `flatMap` and `listOf` on primitive arrays. These result in massive boxing overhead. Additionally, standalone Kotlin verification via `kotlinc` might not be available in the sandbox, necessitating Java-based logic verification for critical path math.
**Action:** Always prefer manual loops and pre-allocated primitive arrays (`ByteArray`) for performance-critical vector operations. Use `StringBuilder` with hex lookups instead of `joinToString` with `String.format` for hot-path hashing.

## 2026-05-25 - [Memory Store Query Optimization]
**Learning:** Generic Kotlin collection transforms like `filterKeys { it.matches(regex) }.values.toList()` are extremely inefficient for high-frequency queries in large maps. They result in O(N) regex matching and multiple intermediate collection allocations.
**Action:** Implement fast-paths for exact matches and simple prefixes (e.g., `prefix*`) using `equals(ignoreCase = true)` and `startsWith(ignoreCase = true)` in manual loops. Use `ConcurrentHashMap` for thread-safe backing stores to avoid `ConcurrentModificationException` during iteration. Ensure fast-paths preserve multi-match behavior for case-insensitive exact hits.

## 2026-06-13 - [Realitymorphism Cache Overhead]
**Learning:** For high-dimensional vector math (e.g., 768-dim dot products), the overhead of generating a cache key using `contentHashCode()` and managing an `LruCache` can significantly exceed the cost of the optimized mathematical computation itself, especially when TPU acceleration or unrolled CPU loops are available.
**Action:** Profile and compare hashing/caching overhead against raw computation for small to medium dimension math operations. Avoid caching if the key generation is O(N) where N is the data size, unless the computation is significantly more expensive than O(N).
