## 2026-05-24 - [Vector Hash Allocation Optimization]
**Learning:** High-frequency math/security operations in Kotlin can be major allocation bottlenecks when using idiomatic collection transforms like `flatMap` and `listOf` on primitive arrays. These result in massive boxing overhead. Additionally, standalone Kotlin verification via `kotlinc` might not be available in the sandbox, necessitating Java-based logic verification for critical path math.
**Action:** Always prefer manual loops and pre-allocated primitive arrays (`ByteArray`) for performance-critical vector operations. Use `StringBuilder` with hex lookups instead of `joinToString` with `String.format` for hot-path hashing.

## 2026-05-25 - [Memory Store Query Optimization]
**Learning:** Generic Kotlin collection transforms like `filterKeys { it.matches(regex) }.values.toList()` are extremely inefficient for high-frequency queries in large maps. They result in O(N) regex matching and multiple intermediate collection allocations.
**Action:** Implement fast-paths for exact matches and simple prefixes (e.g., `prefix*`) using `equals(ignoreCase = true)` and `startsWith(ignoreCase = true)` in manual loops. Use `ConcurrentHashMap` for thread-safe backing stores to avoid `ConcurrentModificationException` during iteration. Ensure fast-paths preserve multi-match behavior for case-insensitive exact hits.

## 2026-06-06 - [Standardized Hex Encoding Optimization]
**Learning:**  and  in loops are significant performance anti-patterns in Android hot-paths due to format string parsing and object allocation overhead. Centralizing this in a bit-shifting  significantly reduces latency. Python simulations show ~40-50% speedup for large buffers.
**Action:** Replace all cryptographic and ID-generation hex encoding with . Always verify logic parity with a standalone Java script if the Gradle environment has pre-existing KSP failures.

## 2026-06-06 - [Standardized Hex Encoding Optimization]
**Learning:** `joinToString("") { "%02x".format(it) }` and `String.format` in loops are significant performance anti-patterns in Android hot-paths due to format string parsing and object allocation overhead. Centralizing this in a bit-shifting `HexUtil` significantly reduces latency. Python simulations show ~40-50% speedup for large buffers.
**Action:** Replace all cryptographic and ID-generation hex encoding with `HexUtil.encodeHex`. Always verify logic parity with a standalone Java script if the Gradle environment has pre-existing KSP failures.
