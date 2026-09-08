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

## 2026-06-25 - [Render Loop Collection Allocation Optimization]
**Learning:** Using `.map` or other collection transforms on a range inside a `Canvas` block (e.g., to generate points for a hexagon) creates a `List` and multiple `Offset` objects every frame, causing significant GC pressure even for small N.
**Action:** Replace range-based collection transforms with manual `for` loops and direct drawing calls to keep the render path allocation-free. Pre-calculate alpha-modified colors and trigonometric constants outside these loops.

## 2026-07-05 - [Render Loop Hoisting & Context Safety]
**Learning:** In high-frequency Jetpack Compose render loops, hoisting not just allocations (, ) but also mathematical factors (, ) and state lookups () outside the loop can significantly reduce per-frame overhead. Crucially,  functions like `remember` MUST be called in a Composable context; calling them inside a `Canvas` `DrawScope` lambda will cause a compilation failure.
**Action:** Always hoist `remember` blocks and loop-invariant math to the top level of the Composable. Use manual indexed `for` loops in `Canvas` to avoid `Iterator` churn.

## 2026-07-05 - [Render Loop Hoisting & Context Safety]
**Learning:** In high-frequency Jetpack Compose render loops, hoisting not just allocations (Paint, Path) but also mathematical factors (speedFactor, radiusFactor) and state lookups (runesList) outside the loop can significantly reduce per-frame overhead. Crucially, @Composable functions like remember MUST be called in a Composable context; calling them inside a Canvas DrawScope lambda will cause a compilation failure.
**Action:** Always hoist remember blocks and loop-invariant math to the top level of the Composable. Use manual indexed for loops in Canvas to avoid Iterator churn.

## 2026-07-10 - [Positional Gradient Hoisting Caution]
**Learning:** Hoisting a `Brush.verticalGradient` completely into a `remember` block in Jetpack Compose can cause visual regressions if the gradient coordinates (`startY`, `endY`) depend on dynamic layout values like a horizon line or container size. While it eliminates `Brush` allocation, it loses spatial accuracy.
**Action:** Hoist only the gradient colors (`listOf<Color>`) and alpha modifications into `remember`, but continue to instantiate the `Brush` inside the `Canvas` if it requires layout-dependent coordinates. This balances allocation reduction with visual fidelity.

## 2026-07-21 - [SQLite Storage Queue Thread Optimization]
**Learning:** In high-frequency backend monitoring modules (such as the `ConsciousnessMatrix`), spawning standard OS threads on every write operation creates a massive thread-spawning bottleneck and leads to database connection leakage / locking contention. During high-throughput testing, this can trigger file descriptor exhaustion errors (`OSError: [Errno 24] Too many open files`).
**Action:** Always prefer a single thread-safe queue (`queue.Queue`) and a dedicated background daemon worker thread to handle database transaction serialization sequentially. This eliminates thread-spawning overhead, protects database locking models, and bounds file descriptor consumption.
