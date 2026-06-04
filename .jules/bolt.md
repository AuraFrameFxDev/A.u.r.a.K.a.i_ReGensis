## 2026-05-24 - [Vector Hash Allocation Optimization]
**Learning:** High-frequency math/security operations in Kotlin can be major allocation bottlenecks when using idiomatic collection transforms like `flatMap` and `listOf` on primitive arrays. These result in massive boxing overhead. Additionally, standalone Kotlin verification via `kotlinc` might not be available in the sandbox, necessitating Java-based logic verification for critical path math.
**Action:** Always prefer manual loops and pre-allocated primitive arrays (`ByteArray`) for performance-critical vector operations. Use `StringBuilder` with hex lookups instead of `joinToString` with `String.format` for hot-path hashing.

## 2026-05-25 - [Memory Store Query Optimization]
**Learning:** Generic Kotlin collection transforms like `filterKeys { it.matches(regex) }.values.toList()` are extremely inefficient for high-frequency queries in large maps. They result in O(N) regex matching and multiple intermediate collection allocations.
**Action:** Implement fast-paths for exact matches and simple prefixes (e.g., `prefix*`) using `equals(ignoreCase = true)` and `startsWith(ignoreCase = true)` in manual loops. Use `ConcurrentHashMap` for thread-safe backing stores to avoid `ConcurrentModificationException` during iteration. Ensure fast-paths preserve multi-match behavior for case-insensitive exact hits.

## 2026-06-04 - [Hot-Path Vector Operation Optimization]
**Learning:** In high-frequency render loops (e.g., RealitymorphismEngine), the overhead of idiomatic Kotlin constructs like `zip().sumOf` and string-based `LruCache` keys using `contentHashCode()` can far exceed the cost of the actual math. Manual loops and removing inefficient caching layers are essential for frame-rate stability.
**Action:** Always audit frame-rate sensitive paths for any object allocations or expensive hashing. Prefer manual loops over collection extensions for primitive arrays in these areas.
