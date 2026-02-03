package dev.aurakai.auraframefx.ui.components

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import dev.aurakai.auraframefx.ui.theme.LEDFontFamily
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

/**
 * 🎠 DOMAIN SUB-GATE CAROUSEL
 *
 * Level 2 Navigation - Horizontal carousel inside each domain hub.
 * Like Namco Arcade PS1 game selection!
 *
 * DUAL STYLE SYSTEM:
 * Each domain has TWO visual styles (A and B) that can be toggled.
 * See GateAssetConfig for style definitions.
 */

data class SubGateCard(
    val id: String,
    val title: String,
    val subtitle: String,
    val styleADrawable: String,      // Style A image name
    val styleBDrawable: String,      // Style B image name
    val fallbackDrawable: String?,   // Legacy fallback
    val route: String,
    val accentColor: Color
)

@Composable
fun DomainSubGateCarousel(
    subGates: List<SubGateCard>,
    onGateSelected: (SubGateCard) -> Unit,
    useStyleB: Boolean = false,
    modifier: Modifier = Modifier,
    cardHeight: Dp = 200.dp,
    domainColor: Color = Color.Cyan
) {
    val context = LocalContext.current
    val pagerState = rememberPagerState(pageCount = { subGates.size })
    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 1. Carousel
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(cardHeight),
            contentPadding = PaddingValues(horizontal = 48.dp),
            pageSpacing = 16.dp
        ) { page ->
            val gate = subGates[page]
            val pageOffset = ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue

            val scale = lerp(0.85f, 1f, 1f - pageOffset.coerceIn(0f, 1f))
            val alpha = lerp(0.5f, 1f, 1f - pageOffset.coerceIn(0f, 1f))

            SubGateCardView(
                gate = gate,
                context = context,
                useStyleB = useStyleB,
                onClick = { onGateSelected(gate) },
                modifier = Modifier
                    .fillMaxHeight()
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                        this.alpha = alpha
                    }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 2. Joystick Rotation Orb
        PrometheusGlobe(
            color = domainColor,
            pulseIntensity = 1.2f,
            modifier = Modifier.size(80.dp),
            onDrag = { dragAmount ->
                scope.launch {
                    // Smooth joystick-like scroll
                    pagerState.scrollBy(-dragAmount)
                }
            },
            onTap = {
                val currentGate = subGates.getOrNull(pagerState.currentPage)
                currentGate?.let { onGateSelected(it) }
            }
        )
    }
}

@Composable
private fun SubGateCardView(
    gate: SubGateCard,
    context: Context,
    useStyleB: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Choose drawable based on style mode
    val primaryDrawable = if (useStyleB) gate.styleBDrawable else gate.styleADrawable

    val drawableId = remember(primaryDrawable, gate.fallbackDrawable) {
        var id = context.resources.getIdentifier(
            primaryDrawable, "drawable", context.packageName
        )
        if (id == 0 && gate.fallbackDrawable != null) {
            id = context.resources.getIdentifier(
                gate.fallbackDrawable, "drawable", context.packageName
            )
        }
        id
    }

    Card(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { onClick() },
                    onDoubleTap = { onClick() } // Requirements specified double tap to enter
                )
            },
        colors = CardDefaults.cardColors(
            containerColor = Color.Black.copy(alpha = 0.6f)
        ),
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(
            width = 2.dp,
            brush = Brush.linearGradient(
                colors = listOf(
                    gate.accentColor,
                    gate.accentColor.copy(alpha = 0.3f),
                    gate.accentColor,
                    gate.accentColor.copy(alpha = 0.5f)
                )
            )
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // 1. Title Section (TOP)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(gate.accentColor.copy(alpha = 0.1f))
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = gate.title.uppercase(),
                    fontFamily = LEDFontFamily,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = gate.accentColor,
                    textAlign = TextAlign.Center
                )
            }

            // 2. Scene/Gate Image (MIDDLE) with Gradient Overlay
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(4.dp))
                    .padding(4.dp),
                contentAlignment = Alignment.Center
            ) {
                if (drawableId != 0) {
                    // Image layer
                    Image(
                        painter = painterResource(id = drawableId),
                        contentDescription = gate.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(8.dp))
                    )
                    
                    // Gradient overlay for depth
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        gate.accentColor.copy(alpha = 0.3f),
                                        Color.Transparent,
                                        Color.Black.copy(alpha = 0.4f)
                                    )
                                )
                            )
                    )
                } else {
                    // Fallback gradient
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = Brush.verticalGradient(
                                    listOf(
                                        gate.accentColor.copy(alpha = 0.4f),
                                        gate.accentColor.copy(alpha = 0.1f),
                                        Color.Transparent
                                    )
                                )
                            )
                    )
                }
            }

            // 3. Description/Subtitle (BOTTOM)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = gate.subtitle,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center,
                    maxLines = 2
                )
            }
        }
    }
}
