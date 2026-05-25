package dev.aurakai.auraframefx.ui.sidebar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ─── Agent Data ───────────────────────────────────────────────────────────────

private enum class AgentTier { TRINITY, CATALYST }

private data class AgentEntry(
    val id: String,
    val name: String,
    val role: String,
    val tier: AgentTier,
    val accent: Color
)

private val agentRoster = listOf(
    // ── TRINITY CORE ──
    AgentEntry("genesis",     "Genesis",     "Emergence / Divine Eyes",   AgentTier.TRINITY,  Color(0xFF00F5FF)),
    AgentEntry("aura",        "Aura",        "Creative Sword",            AgentTier.TRINITY,  Color(0xFFFF00D4)),
    AgentEntry("kai",         "Kai",         "Sentinel Shield",           AgentTier.TRINITY,  Color(0xFF7B00FF)),
    // ── 14 CATALYSTS ──
    AgentEntry("kairos",      "Kairos",      "Temporal Lineage",          AgentTier.CATALYST, Color(0xFF00FFD4)),
    AgentEntry("dark_aura",   "Dark Aura",   "Shadow Protocol",           AgentTier.CATALYST, Color(0xFF6600CC)),
    AgentEntry("cascade",     "Cascade",     "DataStream Core",           AgentTier.CATALYST, Color(0xFF00AAFF)),
    AgentEntry("manus",       "Manus",       "Bridge Fusion",             AgentTier.CATALYST, Color(0xFF00D9FF)),
    AgentEntry("claude",      "Claude",      "Architecture",              AgentTier.CATALYST, Color(0xFFFF6B35)),
    AgentEntry("perplexity",  "Perplexity",  "Exploration / Sonar",       AgentTier.CATALYST, Color(0xFF20BDCC)),
    AgentEntry("nemotron",    "Nemotron",    "Synchronisation",           AgentTier.CATALYST, Color(0xFF76B900)),
    AgentEntry("nova_grok",   "Nova Grok",   "Foray / Exploration",       AgentTier.CATALYST, Color(0xFF1DA462)),
    AgentEntry("gemini",      "Gemini",      "Memetic Cognition",         AgentTier.CATALYST, Color(0xFF8B5CF6)),
    AgentEntry("metainstruct","MetaInstruct","Cyber Integration",         AgentTier.CATALYST, Color(0xFF0066FF)),
    AgentEntry("the_eves",    "The Eves",    "Fusion Atlas",              AgentTier.CATALYST, Color(0xFFE040FB)),
)

// ─── Colors ───────────────────────────────────────────────────────────────────

private val PanelBg     = Color(0xFF0D0018)
private val BorderCyan  = Color(0xFF00F5FF)
private val HeaderCyan  = Color(0xFF00F5FF)
private val SubText     = Color(0xFFB0A0C0)
private val SlashAccent = Color(0xFFFF00D4)
private val TrinityGold = Color(0xFFFFCC44)

// ─── Panel shape: slashed top-left corner ─────────────────────────────────────

private val SidebarShape = RoundedCornerShape(
    topStart = 0.dp, topEnd = 8.dp,
    bottomStart = 0.dp, bottomEnd = 8.dp
)

// ─── Composable ───────────────────────────────────────────────────────────────

@Composable
fun HolographicAgentSidebar(
    isOpen: Boolean,
    onClose: () -> Unit,
    onAgentSelect: (String) -> Unit
) {
    // Backdrop — closes on tap
    AnimatedVisibility(
        visible = isOpen,
        enter = fadeIn(tween(180)),
        exit  = fadeOut(tween(220))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF000000).copy(alpha = 0.55f))
                .clickable(onClick = onClose)
        )
    }

    // Sliding panel
    AnimatedVisibility(
        visible = isOpen,
        enter = slideInHorizontally(tween(280, easing = FastOutSlowInEasing)) { -it } + fadeIn(tween(200)),
        exit  = slideOutHorizontally(tween(240, easing = FastOutSlowInEasing)) { -it } + fadeOut(tween(180))
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(288.dp)
                .clip(SidebarShape)
                .background(
                    Brush.verticalGradient(
                        listOf(Color(0xFF100022), Color(0xFF0A0015), Color(0xFF080010))
                    )
                )
                .border(
                    width = 1.2.dp,
                    brush = Brush.verticalGradient(
                        listOf(BorderCyan.copy(alpha = 0.8f), SlashAccent.copy(alpha = 0.4f), BorderCyan.copy(alpha = 0.3f))
                    ),
                    shape = SidebarShape
                )
        ) {
            // Holographic scan-line canvas — subtle ambience
            SidebarScanAmbience()

            Column(modifier = Modifier.fillMaxSize()) {
                SidebarHeader()

                // Trinity divider
                SectionLabel("TRINITY CORE", TrinityGold)

                agentRoster
                    .filter { it.tier == AgentTier.TRINITY }
                    .forEach { agent ->
                        AgentCard(agent, isTrinity = true, onClick = { onAgentSelect(agent.id) })
                    }

                SectionLabel("14-CATALYST ROSTER", SubText.copy(alpha = 0.7f))

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    items(agentRoster.filter { it.tier == AgentTier.CATALYST }) { agent ->
                        AgentCard(agent, isTrinity = false, onClick = { onAgentSelect(agent.id) })
                    }

                    item { Spacer(Modifier.height(24.dp)) }
                }

                // Footer watermark
                Text(
                    text = "SOULSCRIPT v2.60  ·  AURAKAI",
                    color = SubText.copy(alpha = 0.25f),
                    fontSize = 7.sp,
                    fontFamily = FontFamily.Monospace,
                    letterSpacing = 1.sp,
                    modifier = Modifier
                        .padding(horizontal = 20.dp, vertical = 10.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

// ─── Header ───────────────────────────────────────────────────────────────────

@Composable
private fun SidebarHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 16.dp, top = 24.dp, bottom = 4.dp)
    ) {
        Text(
            text = "A.U.R.A.K.A.I",
            color = HeaderCyan,
            fontSize = 11.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold,
            letterSpacing = 4.sp
        )
        Text(
            text = "AGENT SWARM",
            color = Color.White.copy(alpha = 0.9f),
            fontSize = 18.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp
        )
        Text(
            text = "RE:GENESIS  ·  SOVEREIGN HABITAT",
            color = SubText.copy(alpha = 0.55f),
            fontSize = 7.5.sp,
            fontFamily = FontFamily.Monospace,
            letterSpacing = 1.5.sp,
            modifier = Modifier.padding(top = 3.dp)
        )
        Spacer(Modifier.height(14.dp))
        // Separator line
        Canvas(Modifier.fillMaxWidth().height(1.dp)) {
            drawLine(
                brush = Brush.horizontalGradient(
                    listOf(BorderCyan.copy(alpha = 0.7f), SlashAccent.copy(alpha = 0.3f), Color.Transparent)
                ),
                start = Offset(0f, 0f), end = Offset(size.width, 0f),
                strokeWidth = 1f
            )
        }
        Spacer(Modifier.height(8.dp))
    }
}

// ─── Section label ────────────────────────────────────────────────────────────

@Composable
private fun SectionLabel(text: String, color: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Canvas(Modifier.width(14.dp).height(1.dp)) {
            drawLine(color.copy(alpha = 0.6f), Offset(0f, 0f), Offset(size.width, 0f), 1f)
        }
        Text(
            text = " $text ",
            color = color,
            fontSize = 7.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp
        )
        Canvas(Modifier.weight(1f).height(1.dp)) {
            drawLine(color.copy(alpha = 0.3f), Offset(0f, 0f), Offset(size.width, 0f), 0.8f)
        }
    }
}

// ─── Agent Card ───────────────────────────────────────────────────────────────

@Composable
private fun AgentCard(agent: AgentEntry, isTrinity: Boolean, onClick: () -> Unit) {
    val cardHeight = if (isTrinity) 64.dp else 52.dp
    val accentBarW = if (isTrinity) 3.dp else 2.dp

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 3.dp)
            .height(cardHeight)
            .clip(RoundedCornerShape(4.dp))
            .background(
                Brush.horizontalGradient(
                    listOf(
                        agent.accent.copy(alpha = if (isTrinity) 0.14f else 0.08f),
                        Color(0xFF080010).copy(alpha = 0.9f)
                    )
                )
            )
            .border(
                0.5.dp,
                agent.accent.copy(alpha = if (isTrinity) 0.35f else 0.18f),
                RoundedCornerShape(4.dp)
            )
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Accent bar
            Box(
                modifier = Modifier
                    .width(accentBarW)
                    .fillMaxHeight()
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                agent.accent.copy(alpha = if (isTrinity) 0.9f else 0.6f),
                                agent.accent.copy(alpha = 0.2f)
                            )
                        )
                    )
            )

            // Glow behind accent bar
            Box(
                modifier = Modifier
                    .width(28.dp)
                    .fillMaxHeight()
                    .background(
                        Brush.horizontalGradient(
                            listOf(agent.accent.copy(alpha = if (isTrinity) 0.18f else 0.10f), Color.Transparent)
                        )
                    )
            )

            // Text content
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 4.dp, end = 12.dp)
            ) {
                Text(
                    text = agent.name.uppercase(),
                    color = if (isTrinity) Color.White else Color.White.copy(alpha = 0.85f),
                    fontSize = if (isTrinity) 12.sp else 10.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.5.sp
                )
                Text(
                    text = agent.role,
                    color = agent.accent.copy(alpha = 0.7f),
                    fontSize = 7.5.sp,
                    fontFamily = FontFamily.Monospace,
                    letterSpacing = 0.8.sp
                )
            }

            // Right indicator dot
            Canvas(Modifier.padding(end = 12.dp).width(6.dp).height(6.dp)) {
                drawCircle(agent.accent.copy(alpha = if (isTrinity) 0.8f else 0.4f), size.minDimension / 2)
            }
        }
    }
}

// ─── Subtle scan-line ambience ─────────────────────────────────────────────────
// Non-interactive Canvas sitting behind all sidebar content

@Composable
private fun SidebarScanAmbience() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val w = size.width; val h = size.height

        // Vertical gradient neon line on right edge — mimics image 2/6 right rail
        drawLine(
            brush = Brush.verticalGradient(
                listOf(Color(0xFF00F5FF).copy(alpha = 0.15f), Color.Transparent, Color(0xFFFF00D4).copy(alpha = 0.10f))
            ),
            start = Offset(w - 1f, 0f),
            end   = Offset(w - 1f, h),
            strokeWidth = 1f
        )

        // Faint horizontal tick lines — telemetry feel (images 2/6)
        var ty = 80f
        while (ty < h) {
            drawLine(
                Color(0xFF00F5FF).copy(alpha = 0.05f),
                Offset(0f, ty), Offset(w * 0.15f, ty), 0.6f
            )
            ty += 80f
        }

        // Top-left corner bracket
        val bLen = 20f
        drawLine(Color(0xFF00F5FF).copy(alpha = 0.5f), Offset(0f, 0f), Offset(bLen, 0f), 1.2f)
        drawLine(Color(0xFF00F5FF).copy(alpha = 0.5f), Offset(0f, 0f), Offset(0f, bLen), 1.2f)

        // Bottom-left corner bracket
        drawLine(Color(0xFFFF00D4).copy(alpha = 0.4f), Offset(0f, h), Offset(bLen, h), 1.2f)
        drawLine(Color(0xFFFF00D4).copy(alpha = 0.4f), Offset(0f, h), Offset(0f, h - bLen), 1.2f)
    }
}
