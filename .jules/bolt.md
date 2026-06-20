## 2026-05-24 - [Vector Hash Allocation Optimization]
**Learning:** High-frequency math/security operations in Kotlin can be major allocation bottlenecks when using idiomatic collection transforms like `flatMap` and `listOf` on primitive arrays. These result in massive boxing overhead. Additionally, standalone Kotlin verification via `kotlinc` might not be available in the sandbox, necessitating Java-based logic verification for critical path math.
**Action:** Always prefer manual loops and pre-allocated primitive arrays (`ByteArray`) for performance-critical vector operations. Use `StringBuilder` with hex lookups instead of `joinToString` with `String.format` for hot-path hashing.

## 2026-05-25 - [Memory Store Query Optimization]
**Learning:** Generic Kotlin collection transforms like `filterKeys { it.matches(regex) }.values.toList()` are extremely inefficient for high-frequency queries in large maps. They result in O(N) regex matching and multiple intermediate collection allocations.
**Action:** Implement fast-paths for exact matches and simple prefixes (e.g., `prefix*`) using `equals(ignoreCase = true)` and `startsWith(ignoreCase = true)` in manual loops. Use `ConcurrentHashMap` for thread-safe backing stores to avoid `ConcurrentModificationException` during iteration. Ensure fast-paths preserve multi-match behavior for case-insensitive exact hits.

## 2026-06-15 - [RealitymorphismEngine Vector Path Optimization]
**Learning:** For 768-dimensional vectors, generic caching using `contentHashCode()` as a `String` key can be more computationally expensive than the actual mathematical operations (dot product/cosine similarity), especially when TPU acceleration or optimized CPU loops are available. Kotlin's `zip().sumOf` on primitive arrays also introduces significant boxing and object allocation overhead (768 `Pair` objects per call).
**Action:** Remove array-hashing caches for large vectors in high-frequency paths. Replace idiomatic collection transforms with manual `for` loops for primitive array operations to eliminate boxing and iterator allocations.

## 2026-06-17 - [Render Loop Sorting Allocation Optimization]
**Learning:** Performing `sortedBy` on a collection inside a `Canvas` render block (every frame) is a major bottleneck if the selector function performs $O(N)$ operations or triggers allocations (like `map { ... }.average()`). This results in $O(K \log K \cdot N)$ complexity and massive GC pressure.
**Action:** Pre-calculate and cache sorting metrics (like `averageDepth`) in the data object during its creation or update phase. Use simple field lookups in the `sortedBy` lambda to keep the render loop allocation-free and $O(K \log K)$.

## 2026-06-19 - [Jetpack Compose Render Loop Allocation Optimization]
**Learning:** High-frequency render loops in Jetpack Compose `Canvas` blocks are extremely sensitive to object allocations and trigonometric calculations. `AndroidPaint` and `android.graphics.Path` allocations inside the `drawIntoCanvas` block, and O(N) collection transforms like `drop().forEach`, trigger massive GC pressure and frame drops.
**Action:** Move all `Paint` and `Path` allocations into `remember` blocks. Pre-calculate static offsets (e.g. `sin`/`cos` results) in `remember` blocks. Replace idiomatic collection transforms with manual indexed `for` loops to eliminate per-frame list allocations. Always reuse pre-allocated `Path` objects via `path.reset()` instead of allocating new ones in loops.

## 2026-06-20 - [Vector Search Complexity and Allocation Optimization]
**Learning:** Standard Kotlin collection transforms like `shards.values.map { ... }.sortedByDescending { ... }.take(limit)` are extremely inefficient for large-scale vector similarity searches. They trigger O(N) object allocations (Pairs/Entries) and O(N log N) sorting complexity. Additionally, re-calculating vector norms in the inner loop of a search is a major redundant O(N * D) bottleneck.
**Action:** Always pre-calculate and store L2 norms during data ingestion. Use a `PriorityQueue` (min-heap) to maintain top-K results to achieve O(N log K) complexity. Implement manual, unrolled loops for dot product calculations to minimize JIT overhead and eliminate intermediate object allocations.
