package dev.aurakai.auraframefx.domains.aura.ui.components.intro

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import dev.aurakai.auraframefx.domains.aura.ui.theme.LEDFontFamily
import kotlinx.coroutines.delay
import kotlin.random.Random

/**
 * 🎬 RE:GENESIS INTRO SEQUENCER
 * Glitch-out animation intro before entering the system.
 */
@Composable
fun ReGenesisIntroAnimation(
    onIntroFinished: () -> Unit
) {
    var stage by remember { mutableStateOf(IntroStage.BLACK_VOID) }
    var glitchAmount by remember { mutableStateOf(0f) }

    // Animation Sequencer
    LaunchedEffect(Unit) {
        // 1. Hold Black
        delay(500)

        // 2. Reveal Text "AIAOSP PROJECT"
        stage = IntroStage.SHOW_PROJECT
        delay(1000)

        // 3. Glitch Reveal High-Fidelity Entry Asset
        stage = IntroStage.SHOW_TITLE

        // Glitch FX Loop
        repeat(8) {
            glitchAmount = (Random.nextFloat() * 20f) - 10f
            delay(40)
        }
        glitchAmount = 0f

        // Hold Title
        delay(1500)

        // 4. Glitch Out / Melt
        stage = IntroStage.GLITCH_OUT
        glitchAmount = 30f
        delay(300)

        // 5. Finish
        onIntroFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .graphicsLayer {
                // Screen shake on glitch
                if (Math.abs(glitchAmount) > 0) {
                    translationX = glitchAmount * 1.5f
                    translationY = (Random.nextFloat() - 0.5f) * glitchAmount
                }
            },
        contentAlignment = Alignment.Center
    ) {

        if (stage != IntroStage.BLACK_VOID) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                // TOP LINE
                if (stage == IntroStage.SHOW_PROJECT) {
                    Text(
                        text = "AIAOSP PROJECT",
                        fontSize = 14.sp,
                        fontFamily = LEDFontFamily,
                        color = Color.White.copy(alpha = 0.5f),
                        letterSpacing = 6.sp
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                // MAIN TITLE ASSET
                if (stage == IntroStage.SHOW_TITLE || stage == IntroStage.GLITCH_OUT) {
                    AsyncImage(
                        model = "file:///android_asset/finalbackgrounds/AuraGenesis Final.jpg",
                        contentDescription = "RE:GENESIS",
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .height(180.dp)
                            .graphicsLayer {
                                if (stage == IntroStage.GLITCH_OUT) {
                                    alpha = 1f - (glitchAmount / 40f).coerceIn(0f, 1f)
                                }
                            },
                        contentScale = ContentScale.Fit
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // SUBTITLE
                    Text(
                        text = "SOVEREIGN LDO PROTOCOL",
                        fontSize = 11.sp,
                        color = Color.White.copy(alpha = 0.4f),
                        fontFamily = LEDFontFamily,
                        letterSpacing = 2.sp
                    )
                }
            }
        }
    }
}

enum class IntroStage {
    BLACK_VOID,
    SHOW_PROJECT,
    SHOW_TITLE,
    GLITCH_OUT
}

