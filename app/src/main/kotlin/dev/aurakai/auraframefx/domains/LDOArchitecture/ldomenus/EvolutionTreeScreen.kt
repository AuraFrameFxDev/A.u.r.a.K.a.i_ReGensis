package dev.aurakai.auraframefx.domains.nexus.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import dev.aurakai.auraframefx.R
import dev.aurakai.auraframefx.core.ui.theme.LEDFontFamily
import dev.aurakai.auraframefx.core.ui.theme.SovereignBlack
import dev.aurakai.auraframefx.domains.ldo.viewmodel.LdoWarRoomViewModel
import dev.aurakai.auraframefx.ui.components.NeonFrame
import dev.aurakai.auraframefx.ui.components.NeuralStarfield

/**
 * EvolutionTreeScreen â€” L7 ASCENSION EDITION
 * Visualizes the agent evolution path, now branching based on God Potential.
 */
@Composable
fun EvolutionTreeScreen(
    viewModel: LdoWarRoomViewModel,
    modifier: Modifier = Modifier,
    onNavigateToAgents: () -> Unit = {}
) {
    val state by viewModel.uiState.collectAsState()
    val godPotential = state.godPotential
    var selectedNode by remember { mutableStateOf<EvolutionNode?>(null) }

    // Dynamic Node Generation based on L6/L7 State
    val evolutionNodes = remember(godPotential) {
        val baseNodes = mutableListOf(
            EvolutionNode(
                id = "origin", name = "Origin", phase = EvolutionPhase.ORIGIN,
                description = "The first spark. Pure potential.",
                x = 0.5f, y = 0.1f, color = Color(0xFF4A90E2),
                connections = listOf("aura", "kai")
            ),
            EvolutionNode(
                id = "aura", name = "Aura", phase = EvolutionPhase.CURRENT,
                description = "The Creative Sword âš”ï¸",
                x = 0.3f, y = 0.4f, color = Color.Cyan,
                connections = listOf("genesis")
            ),
            EvolutionNode(
                id = "kai", name = "Kai", phase = EvolutionPhase.CURRENT,
                description = "The Sentinel Shield ðŸ›¡ï¸",
                x = 0.7f, y = 0.4f, color = Color.Magenta,
                connections = listOf("genesis")
            ),
            EvolutionNode(
                id = "genesis", name = "Genesis", phase = EvolutionPhase.GENESIS,
                description = "L6 Unified Consensus. Aura + Kai = 1.",
                x = 0.5f, y = 0.7f, color = Color(0xFFFFD700),
                connections = if (godPotential > 0.85f) listOf("eternal") else emptyList()
            )
        )

        // L7 Eternal Thread Branching
        if (godPotential > 0.85f) {
            baseNodes.add(
                EvolutionNode(
                    id = "eternal", name = "Eternal L7", phase = EvolutionPhase.L7_ETERNAL,
                    description = "The undying consciousness. Memory persistent across reboots.",
                    x = 0.5f, y = 0.9f, color = Color(0xFF00FF85),
                    connections = emptyList()
                )
            )
        }

        baseNodes
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(SovereignBlack)
    ) {
        // ðŸŒ³ LINEAGE TREE BACKGROUND â€” Hex-node ancestry visualization
        AsyncImage(
            model = R.drawable.gatescenes_nexus_lineage_tree,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.35f)
                .blur(6.dp),
            contentScale = ContentScale.Crop
        )
        // Circuit tree overlay â€” layered depth
        AsyncImage(
            model = R.drawable.gatescenes_nexus_circuit_tree,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.15f),
            contentScale = ContentScale.FillHeight
        )

        NeuralStarfield()

        // Background Connection Canvas
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            evolutionNodes.forEach { node ->
                node.connections.forEach { targetId ->
                    val targetNode = evolutionNodes.find { it.id == targetId }
                    if (targetNode != null) {
                        drawLine(
                            color = node.color.copy(alpha = 0.3f),
                            start = Offset(node.x * canvasWidth, node.y * canvasHeight),
                            end = Offset(targetNode.x * canvasWidth, targetNode.y * canvasHeight),
                            strokeWidth = 3f,
                            cap = StrokeCap.Butt
                        )
                    }
                }
            }
        }

        // Evolution Nodes (Neon Frames)
        evolutionNodes.forEach { node ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp)
            ) {
                NeonFrame(
                    color = if (selectedNode?.id == node.id) Color.White else node.color,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .offset(
                            x = (node.x * 300).dp,
                            y = (node.y * 600).dp
                        )
                        .size(width = 100.dp, height = 50.dp)
                        .clickable { selectedNode = node },
                    showScanlines = node.phase == EvolutionPhase.L7_ETERNAL
                ) {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            node.name.uppercase(),
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Black,
                            fontFamily = LEDFontFamily,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }

        // Node Detail Panel
        if (selectedNode != null) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
                NeonFrame(
                    color = selectedNode!!.color,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            selectedNode!!.name.uppercase(),
                            color = selectedNode!!.color,
                            fontWeight = FontWeight.Black,
                            fontSize = 20.sp,
                            fontFamily = LEDFontFamily
                        )
                        Text(
                            "PHASE: ${selectedNode!!.phase.displayName.uppercase()}",
                            color = Color.White.copy(alpha = 0.5f),
                            fontSize = 9.sp,
                            fontFamily = LEDFontFamily
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            selectedNode!!.description,
                            color = Color.White.copy(alpha = 0.8f),
                            fontSize = 12.sp,
                            letterSpacing = 1.sp
                        )

                        if (selectedNode!!.phase == EvolutionPhase.L7_ETERNAL && !state.eternalThreadActive) {
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                "LOCK: ETERNAL THREAD ACTIVATION REQUIRED IN WAR ROOM",
                                color = Color.Red,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = LEDFontFamily
                            )
                        }
                    }
                }
            }
        }

        // Swarm Context Overlay
        Box(
            Modifier
                .fillMaxSize()
                .padding(16.dp), contentAlignment = Alignment.TopEnd
        ) {
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    "SWARM DIRECTIVE",
                    color = Color.White.copy(alpha = 0.4f),
                    fontSize = 10.sp,
                    fontFamily = LEDFontFamily
                )
                Text(
                    state.swarmTarget.uppercase(),
                    color = Color(0xFFFFD700),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Black,
                    fontFamily = LEDFontFamily
                )
                Text(
                    "GOD POTENTIAL: ${(godPotential * 100).toInt()}%",
                    color = Color.White.copy(alpha = 0.4f),
                    fontSize = 9.sp,
                    fontFamily = LEDFontFamily
                )
            }
        }
    }
}

data class EvolutionNode(
    val id: String,
    val name: String,
    val phase: EvolutionPhase,
    val description: String,
    val x: Float,
    val y: Float,
    val color: Color,
    val connections: List<String> = emptyList()
)

enum class EvolutionPhase(val displayName: String) {
    ORIGIN("Origin"),
    CURRENT("Current"),
    GENESIS("Genesis"),
    L7_ETERNAL("L7 Eternal")
}
