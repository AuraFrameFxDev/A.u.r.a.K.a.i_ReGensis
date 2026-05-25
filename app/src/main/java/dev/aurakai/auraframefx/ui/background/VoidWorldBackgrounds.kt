package dev.aurakai.auraframefx.ui.background

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.random.Random

// ─── AuraKai Neon Void Palette (canonical — locked from art reference) ────────
// Base: deep violet/indigo void. Accents: cyan + magenta + electric purple.
private val Void       = Color(0xFF050008)   // Master void — deep purple-black
private val VoidDeep   = Color(0xFF080012)   // Deeper void — violet layer
private val VoidSurface= Color(0xFF0D0018)   // Surface void — panel ambient
private val Cyan       = Color(0xFF00F5FF)   // Crystal edge cyan (image 1)
private val CyanDim    = Color(0xFF00B8CC)   // Dim cyan
private val Teal       = Color(0xFF00FFD4)   // Phoenix teal (image 12)
private val Blue       = Color(0xFF00D9FF)   // Mid-range blue
private val Magenta    = Color(0xFFFF00D4)   // Hot magenta (image 12 rings) — NOT orange
private val Green      = Color(0xFF39FF14)   // Sentinel phosphor
private val Violet     = Color(0xFF7B00FF)   // Electric purple (crystal structure)
private val White      = Color(0xFFE8FAFF)   // HUD highlight

// ─── Route → Background mapping ───────────────────────────────────────────────
enum class VoidBackground {
    NEURAL_NEXUS, LDO_ARCHITECT, CHROMA_FORGE, SENTINEL_MATRIX,
    ORACLE_DRIVE, CHAOS_CATALYST, CONFERENCE_MESH, EMERGENT_SWARM,
    FOUNDATION_CRYSTAL, SENTIENT_SHELL;

    companion object {
        fun fromRoute(route: String): VoidBackground = when (route) {
            "neural_nexus"       -> NEURAL_NEXUS
            "ldo_architecture"   -> LDO_ARCHITECT
            "chroma_forge"       -> CHROMA_FORGE
            "sentinel_matrix"    -> SENTINEL_MATRIX
            "oracle_drive"       -> ORACLE_DRIVE
            "chaos_catalyst"     -> CHAOS_CATALYST
            "conference_room"    -> CONFERENCE_MESH
            "emergent_swarm"     -> EMERGENT_SWARM
            "foundation_rebirth" -> FOUNDATION_CRYSTAL
            "sentient_shell"     -> SENTIENT_SHELL
            else                 -> NEURAL_NEXUS
        }
    }
}

// ─── Dispatch ─────────────────────────────────────────────────────────────────
@Composable
fun VoidWorldBackground(type: VoidBackground, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                // Violet/indigo gradient base — locked from image 1 (crystal city) + image 8 (city dive)
                Brush.verticalGradient(listOf(VoidDeep, Void, VoidSurface.copy(alpha = 0.6f)))
            )
    ) {
        when (type) {
            VoidBackground.NEURAL_NEXUS       -> SynapticVoid()
            VoidBackground.LDO_ARCHITECT      -> BlueprintVoid()
            VoidBackground.CHROMA_FORGE       -> ChromaVoid()
            VoidBackground.SENTINEL_MATRIX    -> SentinelVoid()
            VoidBackground.ORACLE_DRIVE       -> OracleVoid()
            VoidBackground.CHAOS_CATALYST     -> ChaosVoid()
            VoidBackground.CONFERENCE_MESH    -> ConferenceVoid()
            VoidBackground.EMERGENT_SWARM     -> SwarmVoid()
            VoidBackground.FOUNDATION_CRYSTAL -> CrystalVoid()
            VoidBackground.SENTIENT_SHELL     -> MatrixVoid()
        }
    }
}

// ─── 1. NEURAL NEXUS — Synaptic web with pulsing node signals ─────────────────
@Composable
private fun SynapticVoid() {
    data class Node(val x: Float, val y: Float, val phase: Float, val size: Float)

    val nodes = remember {
        List(32) {
            Node(
                x = Random.nextFloat(),
                y = Random.nextFloat(),
                phase = Random.nextFloat() * 2f * PI.toFloat(),
                size = Random.nextFloat() * 3f + 1.5f
            )
        }
    }

    val t = rememberInfiniteTransition(label = "syn").run {
        animateFloat(0f, 1f, infiniteRepeatable(tween(14000, easing = LinearEasing)), label = "t").value
    }

    Canvas(Modifier.fillMaxSize()) {
        val w = size.width; val h = size.height
        val threshold = w * 0.24f

        for (i in nodes.indices) {
            for (j in i + 1 until nodes.size) {
                val a = nodes[i]; val b = nodes[j]
                val dx = (a.x - b.x) * w; val dy = (a.y - b.y) * h
                val dist = sqrt(dx * dx + dy * dy)
                if (dist < threshold) {
                    val strength = 1f - dist / threshold
                    val pulse = sin(t * 2f * PI.toFloat() + a.phase) * 0.5f + 0.5f
                    drawLine(
                        color = Cyan.copy(alpha = strength * pulse * 0.3f),
                        start = Offset(a.x * w, a.y * h),
                        end = Offset(b.x * w, b.y * h),
                        strokeWidth = 0.8f
                    )
                    val pt = (t + a.phase / (2f * PI.toFloat())) % 1f
                    drawCircle(
                        color = White.copy(alpha = strength * 0.9f),
                        radius = 2f,
                        center = Offset(
                            a.x * w + (b.x - a.x) * w * pt,
                            a.y * h + (b.y - a.y) * h * pt
                        )
                    )
                }
            }
        }
        nodes.forEach { n ->
            val p = sin(t * 2f * PI.toFloat() + n.phase) * 0.5f + 0.5f
            drawCircle(Blue.copy(alpha = 0.12f), n.size * (3f + p * 4f), Offset(n.x * w, n.y * h))
            drawCircle(Cyan.copy(alpha = 0.6f + p * 0.4f), n.size, Offset(n.x * w, n.y * h))
        }
    }
}

// ─── 2. LDO ARCHITECT — Blueprint grid with scanning telemetry line ───────────
@Composable
private fun BlueprintVoid() {
    val tr = rememberInfiniteTransition(label = "bp")
    val scan by tr.animateFloat(-0.05f, 1.08f, infiniteRepeatable(tween(3800, easing = LinearEasing)), "scan")
    val nod  by tr.animateFloat(0.3f, 0.8f, infiniteRepeatable(tween(2400, easing = FastOutSlowInEasing), RepeatMode.Reverse), "nod")

    Canvas(Modifier.fillMaxSize()) {
        val w = size.width; val h = size.height; val g = 52f
        var x = 0f
        while (x <= w) {
            drawLine(Blue.copy(alpha = 0.10f), Offset(x, 0f), Offset(x, h), 0.5f); x += g
        }
        var y = 0f
        while (y <= h) {
            drawLine(Blue.copy(alpha = 0.10f), Offset(0f, y), Offset(w, y), 0.5f); y += g
        }
        x = 0f
        while (x <= w) {
            y = 0f
            while (y <= h) {
                drawCircle(Cyan.copy(alpha = nod * 0.35f), 1.8f, Offset(x, y)); y += g
            }
            x += g
        }
        val sy = scan * h
        drawLine(Cyan.copy(alpha = 0.85f), Offset(0f, sy), Offset(w, sy), 1.5f)
        drawRect(Cyan.copy(alpha = 0.07f), topLeft = Offset(0f, sy - 16f), size = androidx.compose.ui.geometry.Size(w, 32f))
    }
}

// ─── 3. CHROMA FORGE — Paint eruptions rising from the floor ──────────────────
@Composable
private fun ChromaVoid() {
    data class Source(val x: Float, val phase: Float, val color: Color)

    val sources = remember {
        listOf(
            Source(0.15f, 0.00f, Cyan),
            Source(0.38f, 0.20f, Magenta),
            Source(0.62f, 0.42f, Violet),
            Source(0.82f, 0.65f, Teal),
            Source(0.50f, 0.85f, Blue)
        )
    }
    val particles = remember {
        sources.map { List(18) { Triple(Random.nextFloat() - 0.5f, Random.nextFloat(), Random.nextFloat()) } }
    }

    val t = rememberInfiniteTransition(label = "ch").run {
        animateFloat(0f, 1f, infiniteRepeatable(tween(9000, easing = LinearEasing)), "t").value
    }

    Canvas(Modifier.fillMaxSize()) {
        val w = size.width; val h = size.height
        sources.forEachIndexed { si, src ->
            particles[si].forEach { (xOff, spd, ph) ->
                val ft = (t + src.phase + ph * 0.4f) % 1f
                val px = src.x * w + xOff * w * 0.18f
                val py = h - ft * h * (0.6f + spd * 0.6f)
                val alpha = if (ft < 0.65f) (1f - ft / 0.65f) * 0.7f else 0f
                if (alpha > 0f) {
                    val r = (1f - ft) * 7f + 1.5f
                    drawCircle(src.color.copy(alpha = alpha * 0.3f), r * 2.5f, Offset(px, py))
                    drawCircle(src.color.copy(alpha = alpha), r, Offset(px, py))
                }
            }
            val rt = (t + src.phase) % 1f
            drawCircle(
                src.color.copy(alpha = (1f - rt) * 0.25f),
                rt * w * 0.28f,
                Offset(src.x * w, h),
                style = Stroke(1.5f)
            )
        }
    }
}

// ─── 4. SENTINEL MATRIX — Hex fortress with security pulse rings ───────────────
@Composable
private fun SentinelVoid() {
    val tr = rememberInfiniteTransition(label = "snt")
    val pulse by tr.animateFloat(0f, 1f, infiniteRepeatable(tween(2800, easing = LinearEasing)), "p")
    val hp    by tr.animateFloat(0.85f, 1.1f, infiniteRepeatable(tween(2000, easing = FastOutSlowInEasing), RepeatMode.Reverse), "hp")

    Canvas(Modifier.fillMaxSize()) {
        val w = size.width; val h = size.height; val hs = 52f

        val rows = (h / (hs * 0.866f)).toInt() + 3
        val cols = (w / (hs * 1.5f)).toInt() + 3
        val maxD = sqrt((w * w + h * h).toFloat()) / 2f

        for (row in -1 until rows) {
            for (col in -1 until cols) {
                val cx = col * hs * 1.5f
                val cy = row * hs * 0.866f * 2 + (col % 2) * hs * 0.866f
                val d = sqrt(((cx - w/2)*(cx - w/2) + (cy - h/2)*(cy - h/2)).toFloat()) / maxD
                val alpha = (0.07f + 0.12f * (1f - d)) * hp

                val path = Path()
                for (i in 0 until 6) {
                    val a = Math.toRadians((i * 60).toDouble())
                    val px = cx + hs / 2 * cos(a).toFloat()
                    val py = cy + hs / 2 * sin(a).toFloat()
                    if (i == 0) path.moveTo(px, py) else path.lineTo(px, py)
                }
                path.close()
                drawPath(path, Green.copy(alpha = alpha), style = Stroke(1.2f))
            }
        }

        repeat(5) { i ->
            val rt = (pulse + i * 0.2f) % 1f
            drawCircle(
                Green.copy(alpha = (1f - rt) * 0.35f),
                rt * maxOf(w, h) * 0.65f,
                Offset(w / 2, h / 2),
                style = Stroke(1.5f)
            )
        }
    }
}

// ─── 5. ORACLE DRIVE — Deep space: layered starfield + data ribbons ────────────
@Composable
private fun OracleVoid() {
    data class Star(val x: Float, val y: Float, val size: Float, val speed: Float, val phase: Float)

    val stars = remember {
        listOf(
            List(120) { Star(Random.nextFloat(), Random.nextFloat(), 0.8f + Random.nextFloat() * 0.8f, 0.3f, Random.nextFloat() * 2f * PI.toFloat()) },
            List(60)  { Star(Random.nextFloat(), Random.nextFloat(), 1.2f + Random.nextFloat() * 1.2f, 0.6f, Random.nextFloat() * 2f * PI.toFloat()) },
            List(25)  { Star(Random.nextFloat(), Random.nextFloat(), 1.8f + Random.nextFloat() * 1.8f, 1.0f, Random.nextFloat() * 2f * PI.toFloat()) }
        )
    }

    val t = rememberInfiniteTransition(label = "orc").run {
        animateFloat(0f, 1f, infiniteRepeatable(tween(20000, easing = LinearEasing)), "t").value
    }

    Canvas(Modifier.fillMaxSize()) {
        val w = size.width; val h = size.height

        stars.forEach { layer ->
            layer.forEach { s ->
                val px = (s.x + t * s.speed * 0.05f) % 1f
                val py = s.y
                val twinkle = sin(t * 8f * PI.toFloat() * s.speed + s.phase) * 0.3f + 0.7f
                drawCircle(White.copy(alpha = twinkle * 0.9f), s.size, Offset(px * w, py * h))
                if (s.size > 1.8f) drawCircle(Blue.copy(alpha = twinkle * 0.2f), s.size * 3f, Offset(px * w, py * h))
            }
        }

        // Data ribbons curling in from edges toward center
        repeat(4) { i ->
            val phase = i * 0.25f
            val rt = (t + phase) % 1f
            val path = Path()
            val startX = if (i % 2 == 0) 0f else w
            path.moveTo(startX, h * (0.2f + i * 0.18f))
            path.cubicTo(
                w * 0.25f, h * (0.3f + i * 0.1f),
                w * 0.55f, h * (0.45f + sin(rt * PI.toFloat()) * 0.15f),
                w / 2, h / 2
            )
            drawPath(path, Cyan.copy(alpha = (1f - rt) * 0.4f), style = Stroke(1f))
        }
    }
}

// ─── 6. CHAOS CATALYST — Lightning storm + electromagnetic chaos ───────────────
@Composable
private fun ChaosVoid() {
    val tr = rememberInfiniteTransition(label = "chao")
    val t by tr.animateFloat(0f, 1f, infiniteRepeatable(tween(6000, easing = LinearEasing)), "t")

    val boltSeeds = remember { List(8) { Pair(Random.nextLong(), Random.nextLong()) } }
    // remember must be in Composable scope — NOT inside Canvas lambda
    val chaos = remember { List(40) { Triple(Random.nextFloat(), Random.nextFloat(), Random.nextFloat()) } }

    Canvas(Modifier.fillMaxSize()) {
        val w = size.width; val h = size.height

        // EM field rings
        repeat(3) { i ->
            val rt = (t + i * 0.33f) % 1f
            val r = rt * w * 0.8f
            drawCircle(Violet.copy(alpha = (1f - rt) * 0.2f), r, Offset(w / 2, h / 2), style = Stroke(1f))
        }

        // Lightning bolts
        boltSeeds.forEachIndexed { idx, (s1, s2) ->
            val boltT = (t * 4f + idx * 0.125f) % 1f
            if (boltT > 0.85f) {
                val rnd = Random(s1 + (t * 100).toLong())
                val startX = rnd.nextFloat() * w
                val segments = 6
                var cx = startX; var cy = 0f
                val alpha = ((boltT - 0.85f) / 0.15f).let { if (it < 0.5f) it * 2f else (1f - it) * 2f }

                repeat(segments) {
                    val nx = cx + (rnd.nextFloat() - 0.5f) * w * 0.15f
                    val ny = cy + h / segments
                    drawLine(Magenta.copy(alpha = alpha * 0.9f), Offset(cx, cy), Offset(nx, ny), 2f)
                    drawLine(Cyan.copy(alpha = alpha * 0.35f), Offset(cx, cy), Offset(nx, ny), 5f)
                    cx = nx; cy = ny
                }

                // Spark burst at bottom
                val sparkRnd = Random(s2 + (t * 100).toLong())
                repeat(8) {
                    val angle = sparkRnd.nextFloat() * 2f * PI.toFloat()
                    val len = sparkRnd.nextFloat() * 30f + 10f
                    drawLine(
                        Cyan.copy(alpha = alpha * 0.7f),
                        Offset(cx, cy),
                        Offset(cx + cos(angle) * len, cy + sin(angle) * len),
                        1f
                    )
                }
            }
        }

        // Chaos particles flying across
        chaos.forEach { (sx, sy, ph) ->
            val pt = (t * 2f + ph) % 1f
            val px = (sx + pt * 0.5f) % 1f
            val py = sy + sin(pt * PI.toFloat() * 2f + ph * 10f) * 0.08f
            drawCircle(Magenta.copy(alpha = 0.5f - pt * 0.4f), 2.5f, Offset(px * w, py * h))
        }
    }
}

// ─── 7. CONFERENCE MESH — Orbital holographic node network ────────────────────
@Composable
private fun ConferenceVoid() {
    val tr = rememberInfiniteTransition(label = "conf")
    val t by tr.animateFloat(0f, 2f * PI.toFloat(), infiniteRepeatable(tween(18000, easing = LinearEasing)), "t")

    data class OrbNode(val orbit: Int, val angle: Float)

    val nodes = remember {
        listOf(
            List(6)  { OrbNode(0, it * 2f * PI.toFloat() / 6) },
            List(9)  { OrbNode(1, it * 2f * PI.toFloat() / 9) },
            List(14) { OrbNode(2, it * 2f * PI.toFloat() / 14) }
        ).flatten()
    }

    Canvas(Modifier.fillMaxSize()) {
        val w = size.width; val h = size.height
        val cx = w / 2f; val cy = h / 2f
        val radii = listOf(w * 0.12f, w * 0.24f, w * 0.38f)
        val speeds = listOf(1f, 0.6f, 0.35f)

        val positions = nodes.map { n ->
            val r = radii[n.orbit]
            val a = n.angle + t * speeds[n.orbit]
            Offset(cx + cos(a) * r, cy + sin(a) * r)
        }

        // Orbit rings
        radii.forEachIndexed { i, r ->
            drawCircle(Cyan.copy(alpha = 0.08f + i * 0.04f), r, Offset(cx, cy), style = Stroke(0.8f))
        }

        // Cross-orbit connections
        for (i in nodes.indices) {
            for (j in i + 1 until nodes.size) {
                if (nodes[i].orbit != nodes[j].orbit) continue
                val p1 = positions[i]; val p2 = positions[j]
                val dist = sqrt((p1.x - p2.x).let { it * it } + (p1.y - p2.y).let { it * it })
                if (dist < w * 0.15f) {
                    drawLine(Teal.copy(alpha = 0.3f * (1f - dist / (w * 0.15f))), p1, p2, 0.8f)
                }
            }
        }

        // Node dots
        positions.forEachIndexed { i, pos ->
            val orbit = nodes[i].orbit
            val color = listOf(Cyan, Teal, Blue)[orbit]
            drawCircle(color.copy(alpha = 0.15f), 8f + orbit * 3f, pos)
            drawCircle(color.copy(alpha = 0.9f), 3f + orbit.toFloat(), pos)
        }

        // Center core
        drawCircle(Brush.radialGradient(listOf(Cyan.copy(alpha = 0.35f), Color.Transparent), Offset(cx, cy), w * 0.06f), w * 0.06f, Offset(cx, cy))
        drawCircle(Cyan.copy(alpha = 0.8f), 5f, Offset(cx, cy))
    }
}

// ─── 8. EMERGENT SWARM — Flocking particle mass ────────────────────────────────
@Composable
private fun SwarmVoid() {
    data class Boid(val x: Float, val y: Float, val angle: Float, val speed: Float)

    val boids = remember {
        List(80) {
            Boid(
                x = Random.nextFloat(),
                y = Random.nextFloat(),
                angle = Random.nextFloat() * 2f * PI.toFloat(),
                speed = Random.nextFloat() * 0.008f + 0.004f
            )
        }
    }

    val t = rememberInfiniteTransition(label = "swm").run {
        animateFloat(0f, 1f, infiniteRepeatable(tween(20000, easing = LinearEasing)), "t").value
    }

    Canvas(Modifier.fillMaxSize()) {
        val w = size.width; val h = size.height

        boids.forEach { b ->
            val wave = sin(t * 2f * PI.toFloat() + b.angle) * 0.3f
            val ax = (b.x + t * b.speed * cos(b.angle + wave) * 3f) % 1f
            val ay = (b.y + t * b.speed * sin(b.angle + wave) * 3f) % 1f
            val trail = 0.4f + abs(wave) * 0.4f

            drawCircle(Green.copy(alpha = 0.08f), 8f, Offset(ax * w, ay * h))
            drawCircle(Cyan.copy(alpha = trail), 2.5f, Offset(ax * w, ay * h))

            val tx = (ax - cos(b.angle + wave) * b.speed * 15f).coerceIn(0f, 1f)
            val ty = (ay - sin(b.angle + wave) * b.speed * 15f).coerceIn(0f, 1f)
            drawLine(Green.copy(alpha = trail * 0.35f), Offset(ax * w, ay * h), Offset(tx * w, ty * h), 1f)
        }
    }
}

// ─── 9. FOUNDATION CRYSTAL — Angular crystal shards flying in violet void ─────
// Visual reference: image 1 (purple/cyan crystal city towers diving at angle)
// Deep violet/indigo void with geometric slabs at 30–45° angles, cyan neon edges,
// violet semi-transparent faces — multiple depth layers for parallax feel.
@Composable
private fun CrystalVoid() {
    data class Shard(
        val cx: Float, val cy: Float,   // normalised center position
        val width: Float, val height: Float,
        val angleDeg: Float,            // rotation angle
        val depth: Float,               // 0=far/dim, 1=close/bright
        val faceColor: Color,
        val edgeColor: Color,
        val driftX: Float,              // normalised drift speed per cycle
        val driftY: Float,
        val phase: Float
    )

    val shards = remember {
        val rng = Random(0xC4175A1)
        List(26) {
            val depth = rng.nextFloat()
            val facePick = listOf(
                Color(0xFF3A006F),   // deep electric violet
                Color(0xFF1A0040),   // almost-black violet
                Color(0xFF280060),   // mid violet
                Color(0xFF0D003A)    // near-black indigo
            ).random(rng)
            val edgePick = if (rng.nextFloat() > 0.35f) Color(0xFF00F5FF) else Color(0xFFFF00D4)
            Shard(
                cx       = rng.nextFloat() * 1.4f - 0.2f,
                cy       = rng.nextFloat() * 1.6f - 0.3f,
                width    = (rng.nextFloat() * 0.25f + 0.06f) * (0.5f + depth * 0.5f),
                height   = (rng.nextFloat() * 0.55f + 0.12f) * (0.5f + depth * 0.5f),
                angleDeg = rng.nextFloat() * 70f - 35f + if (rng.nextFloat() > 0.5f) 0f else 90f,
                depth    = depth,
                faceColor= facePick,
                edgeColor= edgePick,
                driftX   = (rng.nextFloat() - 0.5f) * 0.04f,
                driftY   = (rng.nextFloat() - 0.5f) * 0.025f,
                phase    = rng.nextFloat()
            )
        }.sortedBy { it.depth }  // paint back-to-front
    }

    val tr = rememberInfiniteTransition(label = "crys")
    val t  by tr.animateFloat(0f, 1f, infiniteRepeatable(tween(28000, easing = LinearEasing)), "t")
    val breathe by tr.animateFloat(0.85f, 1.0f, infiniteRepeatable(tween(4200, easing = FastOutSlowInEasing), RepeatMode.Reverse), "br")

    Canvas(Modifier.fillMaxSize()) {
        val w = size.width; val h = size.height

        shards.forEach { s ->
            // Slow drift + subtle breathe
            val px = ((s.cx + s.driftX * (t + s.phase)) % 1.4f - 0.2f + 1.6f) % 1.6f - 0.2f
            val py = ((s.cy + s.driftY * (t + s.phase)) % 1.6f - 0.3f + 1.9f) % 1.9f - 0.3f

            val scx = px * w
            val scy = py * h
            val sw  = s.width  * w * breathe
            val sh  = s.height * h * breathe

            val faceAlpha = (0.18f + s.depth * 0.30f) // far shards more transparent
            val edgeAlpha = (0.45f + s.depth * 0.50f)
            val glowAlpha = s.depth * 0.20f

            // Build shard as rotated parallelogram (Path)
            val rad = Math.toRadians(s.angleDeg.toDouble()).toFloat()
            val cosA = cos(rad); val sinA = sin(rad)
            fun rotX(lx: Float, ly: Float) = scx + lx * cosA - ly * sinA
            fun rotY(lx: Float, ly: Float) = scy + lx * sinA + ly * cosA

            val hw = sw / 2f; val hh = sh / 2f
            // Parallelogram: slight shear on X for crystal feel
            val shear = hw * 0.25f
            val path = Path().apply {
                moveTo(rotX(-hw + shear, -hh), rotY(-hw + shear, -hh))
                lineTo(rotX( hw + shear, -hh), rotY( hw + shear, -hh))
                lineTo(rotX( hw - shear,  hh), rotY( hw - shear,  hh))
                lineTo(rotX(-hw - shear,  hh), rotY(-hw - shear,  hh))
                close()
            }

            // Glow halo behind shard
            if (glowAlpha > 0.02f) {
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(s.edgeColor.copy(alpha = glowAlpha), Color.Transparent),
                        center = Offset(scx, scy),
                        radius = (sw + sh) * 0.5f
                    ),
                    radius = (sw + sh) * 0.5f,
                    center = Offset(scx, scy)
                )
            }

            // Face fill
            drawPath(path, s.faceColor.copy(alpha = faceAlpha))

            // Cyan/magenta neon edge
            drawPath(path, s.edgeColor.copy(alpha = edgeAlpha), style = Stroke(strokeWidth = (0.8f + s.depth * 1.8f)))

            // Bright highlight on top edge (edge-lit crystal look)
            val topStart = Offset(rotX(-hw + shear, -hh), rotY(-hw + shear, -hh))
            val topEnd   = Offset(rotX( hw + shear, -hh), rotY( hw + shear, -hh))
            drawLine(
                color = White.copy(alpha = s.depth * 0.35f),
                start = topStart, end = topEnd,
                strokeWidth = 0.8f
            )
        }

        // Atmospheric purple-to-transparent vignette at edges
        drawRect(
            brush = Brush.radialGradient(
                colors = listOf(Color.Transparent, Color(0x880A0018)),
                center = Offset(w / 2, h / 2),
                radius = maxOf(w, h) * 0.75f
            ),
            size = size
        )
    }
}

// ─── 10. SENTIENT SHELL — Matrix code rain ────────────────────────────────────
@Composable
private fun MatrixVoid() {
    data class Column(val x: Float, val speed: Float, val length: Int, val phase: Float)

    val columns = remember {
        List(48) {
            Column(
                x = it / 48f + Random.nextFloat() * (1f / 48f),
                speed = 0.008f + Random.nextFloat() * 0.018f,
                length = 8 + Random.nextInt(12),
                phase = Random.nextFloat()
            )
        }
    }

    val t = rememberInfiniteTransition(label = "mat").run {
        animateFloat(0f, 1f, infiniteRepeatable(tween(10000, easing = LinearEasing)), "t").value
    }

    Canvas(Modifier.fillMaxSize()) {
        val w = size.width; val h = size.height
        val dotSpacing = h / 40f

        columns.forEach { col ->
            val headY = ((t * 2f * col.speed * 100f + col.phase) % 1.2f) * h
            repeat(col.length) { i ->
                val dotY = headY - i * dotSpacing
                if (dotY < 0f || dotY > h) return@repeat
                val alpha = when {
                    i == 0 -> 1f
                    i < 3  -> 0.8f - i * 0.15f
                    else   -> (1f - i.toFloat() / col.length) * 0.5f
                }
                val color = if (i == 0) White else Green
                drawCircle(color.copy(alpha = alpha), if (i == 0) 3f else 1.8f, Offset(col.x * w, dotY))
                if (i == 0) drawCircle(Green.copy(alpha = 0.2f), 8f, Offset(col.x * w, dotY))
            }
        }
    }
}
