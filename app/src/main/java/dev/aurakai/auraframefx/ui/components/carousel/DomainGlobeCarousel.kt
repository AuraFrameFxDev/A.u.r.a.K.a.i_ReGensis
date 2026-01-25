package dev.aurakai.auraframefx.ui.components.carousel

import androidx.compose.animation.core.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.GraphicsLayerScope
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import dev.aurakai.auraframefx.ui.components.hologram.CardStyle
import dev.aurakai.auraframefx.ui.components.hologram.HolographicCard
import dev.aurakai.auraframefx.ui.theme.LEDFontFamily
import kotlin.math.absoluteValue

data class GlobeItem(
    val title: String,
    val description: String,
    val route: String,
    val runeRes: Int,
    val glowColor: Color,
    val style: CardStyle = CardStyle.MYTHICAL
)

/**
 * 🌍 DOMAIN GLOBE CAROUSEL
 * 3D rotating "globe" system for Level 2 navigation.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DomainGlobeCarousel(
    items: List<GlobeItem>,
    onNavigate: (String) -> Unit,
    onPageSelection: (GlobeItem) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(pageCount = { items.size })
    
    // Notify parent of current selection
    LaunchedEffect(pagerState.currentPage) {
        onPageSelection(items[pagerState.currentPage])
    }
    
    // Floating animation for the ACTIVE card
    val infiniteTransition = rememberInfiniteTransition(label = "GlobeFloat")
    val floatOffset by infiniteTransition.animateFloat(
        initialValue = -15f,
        targetValue = 15f,
        animationSpec = infiniteRepeatable(
            animation = tween(2500, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "FloatY"
    )

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 60.dp),
            pageSpacing = (-40).dp // Overlap for globe feel
        ) { page ->
            val item = items[page]
            val pageOffset = ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction)
            val absOffset = pageOffset.absoluteValue
            
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        // 3D Globe Rotation Logic
                        val scale = lerp(0.6f, 1f, 1f - absOffset.coerceAtMost(1f))
                        scaleX = scale
                        scaleY = scale
                        
                        rotationY = pageOffset * -45f
                        alpha = lerp(0.3f, 1f, 1f - absOffset.coerceAtMost(1f))
                    }
                    .clickable { 
                        if (pagerState.currentPage == page) {
                            onNavigate(item.route)
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                // THE CARD (Transparent, same size 280x400)
                HolographicCard(
                    runeRes = item.runeRes,
                    glowColor = item.glowColor,
                    style = item.style,
                    modifier = Modifier
                        .size(280.dp, 400.dp)
                        .offset(y = if (absOffset < 0.2f) floatOffset.dp else 0.dp),
                    dangerLevel = 0f
                )
            }
        }
    }
}
