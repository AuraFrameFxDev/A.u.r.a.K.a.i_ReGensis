package dev.aurakai.auraframefx.ui.gates

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dev.aurakai.auraframefx.R
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

data class GateDomain(
    val moduleId: String,
    val title: String,
    val accentColor: Color,
    val variants: List<GateVariant>
)

data class GateVariant(
    val key: String,
    val label: String,
    val resId: Int
)

private val GATE_DOMAINS = listOf(
    GateDomain(
        "sentinels-fortress", "SENTINEL'S FORTRESS", Color(0xFF9B30FF),
        listOf(
            GateVariant("final", "FINAL V2", R.drawable.gatescenes_kai_sentinelsfortress_v2),
            GateVariant("orig", "ORIGINAL", R.drawable.gatescene_1),
        )
    ),
    GateDomain(
        "collab-canvas", "COLLAB CANVAS", Color(0xFFFF2D78),
        listOf(
            GateVariant("v2", "MESH V2", R.drawable.gatescenes_aura_collabcanvas_v2),
            GateVariant("orig", "ORIGINAL", R.drawable.gatescene_9),
        )
    ),
    GateDomain(
        "uiux-design-studio", "UXUI DESIGN STUDIO", Color(0xFF00FFCC),
        listOf(
            GateVariant("v2", "AURA V2", R.drawable.gatescenes_aura_designstudio_v2),
            GateVariant("orig", "ORIGINAL", R.drawable.gatescene_2),
        )
    ),
    GateDomain(
        "lsposed-gate", "LSPOSED MODULES", Color(0xFF4A9EFF),
        listOf(
            GateVariant("main", "GATE", R.drawable.gatescenes_lsposed),
            GateVariant("orig", "ORIGINAL", R.drawable.gatescene_3),
        )
    ),
)

@Composable
fun GateDomainImagePicker(
    navController: NavController,
    onNavigateBack: () -> Unit = {}
) {
    val scope = rememberCoroutineScope()
    var expandedDomain by remember { mutableStateOf<String?>(null) }
    var pendingSelection by remember { mutableStateOf<Pair<String, GateVariant>?>(null) }
    var showConfirmDialog by remember { mutableStateOf(false) }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFF030D0F))) {
        InfinityRibbonBackground(modifier = Modifier
            .fillMaxSize()
            .alpha(0.12f))

        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                IconButton(onClick = onNavigateBack) {
                    Icon(Icons.Default.ArrowBack, null, tint = Color(0xFF00FFFF))
                }
                Column {
                    Text(
                        "GATE IMAGE SWITCHER", fontFamily = FontFamily.Monospace,
                        fontSize = 16.sp, fontWeight = FontWeight.Bold,
                        letterSpacing = 3.sp, color = Color(0xFF00FFFF)
                    )
                    Text(
                        "TAP DOMAIN → SELECT ART → CONFIRM",
                        fontSize = 9.sp, letterSpacing = 1.sp, color = Color(0xFF00FFFF).copy(0.4f)
                    )
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(bottom = 24.dp)
            ) {
                items(GATE_DOMAINS) { domain ->
                    DomainCard(
                        domain = domain,
                        isExpanded = expandedDomain == domain.moduleId,
                        onToggle = {
                            expandedDomain =
                                if (expandedDomain == domain.moduleId) null else domain.moduleId
                        },
                        onVariantSelect = { variant ->
                            pendingSelection = domain.moduleId to variant
                            showConfirmDialog = true
                        }
                    )
                }
            }
        }

        if (showConfirmDialog && pendingSelection != null) {
            val (moduleId, variant) = pendingSelection!!
            val domain = GATE_DOMAINS.find { it.moduleId == moduleId }
            AlertDialog(
                onDismissRequest = { showConfirmDialog = false },
                containerColor = Color(0xFF001520),
                titleContentColor = Color(0xFF00FFFF),
                textContentColor = Color.White.copy(0.7f),
                title = {
                    Text(
                        "APPLY GATE IMAGE?",
                        fontFamily = FontFamily.Monospace,
                        letterSpacing = 2.sp
                    )
                },
                text = {
                    Text("${domain?.title}\n→ ${variant.label}\n\nThis will update the gate card display.")
                },
                confirmButton = {
                    TextButton(onClick = {
                        scope.launch {
                            // Persistence logic
                        }
                        showConfirmDialog = false
                        pendingSelection = null
                    }) {
                        Text(
                            "APPLY",
                            color = Color(0xFF00FFFF),
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 2.sp
                        )
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showConfirmDialog = false }) {
                        Text("CANCEL", color = Color.White.copy(0.4f))
                    }
                }
            )
        }
    }
}

@Composable
private fun DomainCard(
    domain: GateDomain,
    isExpanded: Boolean,
    onToggle: () -> Unit,
    onVariantSelect: (GateVariant) -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "ribbon_${domain.moduleId}")
    val ribbonT by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = 2f * PI.toFloat(),
        animationSpec = infiniteRepeatable(tween(4000, easing = LinearEasing), RepeatMode.Restart),
        label = "ribbon_t"
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .border(
                1.dp,
                if (isExpanded) domain.accentColor else domain.accentColor.copy(0.3f),
                RoundedCornerShape(8.dp)
            )
            .background(domain.accentColor.copy(if (isExpanded) 0.1f else 0.05f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onToggle)
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Canvas(modifier = Modifier.size(48.dp)) {
                val path = Path()
                val cx = size.width / 2f
                val cy = size.height / 2f
                val rx = size.width * 0.35f
                val ry = size.height * 0.2f
                for (i in 0..40) {
                    val t = i.toFloat() / 40f * 2 * PI.toFloat() + ribbonT
                    val cos2t = cos(2 * t)
                    val sc = if (cos2t > 0) sqrt(cos2t) else 0f
                    val lx = cx + rx * sc * cos(t)
                    val ly = cy + ry * sc * sin(t) * cos(t) * 0.8f
                    if (i == 0) path.moveTo(lx, ly) else path.lineTo(lx, ly)
                }
                drawPath(
                    path,
                    domain.accentColor,
                    style = Stroke(width = 2f, cap = StrokeCap.Round)
                )
                drawPath(
                    path,
                    domain.accentColor.copy(0.2f),
                    style = Stroke(width = 6f, cap = StrokeCap.Round)
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    domain.title, fontSize = 11.sp, fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp, color = domain.accentColor
                )
                Text(
                    "${domain.variants.size} variants",
                    fontSize = 9.sp,
                    color = Color.White.copy(0.4f)
                )
            }

            Icon(
                if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                null, tint = domain.accentColor.copy(0.7f), modifier = Modifier.size(20.dp)
            )
        }

        AnimatedVisibility(
            visible = isExpanded,
            enter = expandVertically() + fadeIn(),
            exit = shrinkVertically() + fadeOut()
        ) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp)
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(domain.variants) { variant ->
                    GateVariantTile(
                        variant = variant,
                        accentColor = domain.accentColor,
                        onClick = { onVariantSelect(variant) }
                    )
                }
            }
        }
    }
}

@Composable
private fun GateVariantTile(
    variant: GateVariant,
    accentColor: Color,
    onClick: () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "tile_${variant.key}")
    val glow by infiniteTransition.animateFloat(
        initialValue = 0.4f, targetValue = 1f,
        animationSpec = infiniteRepeatable(
            tween(1800, easing = FastOutSlowInEasing),
            RepeatMode.Reverse
        ),
        label = "glow"
    )

    Box(
        modifier = Modifier
            .width(110.dp)
            .fillMaxHeight()
            .clip(RoundedCornerShape(8.dp))
            .border(
                width = 1.5.dp,
                brush = Brush.linearGradient(
                    listOf(
                        accentColor.copy(glow),
                        accentColor.copy(glow * 0.4f)
                    )
                ),
                shape = RoundedCornerShape(8.dp)
            )
            .clickable(onClick = onClick)
    ) {
        Image(
            painter = painterResource(variant.resId),
            contentDescription = variant.label,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(listOf(Color.Transparent, Color(0xCC000000)))
                )
        )
        Text(
            variant.label,
            fontSize = 9.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp,
            color = Color.White, textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 6.dp)
        )
    }
}
