package dev.aurakai.auraframefx.ui.gates

import androidx.compose.foundation.BorderStroke
import androidx.compose.material.icons.filled.ThreeDRotation
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * 🌀 3D GYROSCOPE CUSTOMIZATION SCREEN
 *
 * Control how the interface reacts to physical device motion.
 * Features real-time 3D parallax preview and sensitivity tuning.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GyroscopeCustomizationScreen(navController: NavController) {
    var sensitivity by remember { mutableFloatStateOf(0.5f) }
    var parallaxTilt by remember { mutableFloatStateOf(15f) }
    var isEnabled by remember { mutableStateOf(true) }

    // Animation for the 3D cube preview
    val infiniteTransition = rememberInfiniteTransition(label = "gyro_preview")
    val rotationX by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(10000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotationX"
    )
    val rotationY by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(15000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotationY"
    )

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black)) {
        // High-tech constellation background
        ConstellationBackground()

        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                "KINETIC ENGINE",
                                fontFamily = LEDFontFamily,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                letterSpacing = 2.sp
                            )
                            Text(
                                "3D GYROSCOPE CALIBRATION",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color(0xFF00E5FF)
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = Color.White)
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent)
                )
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 3D Preview Area
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .background(Color.White.copy(alpha = 0.05f))
                        .border(1.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(24.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    // Animated 3D Wireframe Preview
                    Canvas(modifier = Modifier
                        .size(200.dp)
                        .graphicsLayer {
                            rotationX = if (isEnabled) rotationX else 0f
                            rotationY = if (isEnabled) rotationY else 0f
                        }) {
                        val centerX = size.width / 2
                        val centerY = size.height / 2
                        val size = 150f

                        // Draw a complex geometric shape that "rotates"
                        drawCircle(
                            color = Color(0xFF00E5FF).copy(alpha = 0.3f),
                            radius = size,
                            center = Offset(centerX, centerY),
                            style = Stroke(width = 2f)
                        )

                        // Internal facets
                        for (i in 0 until 12) {
                            val angle = Math.toRadians(i * 30.0 + rotationY).toFloat()
                            val x = centerX + cos(angle) * size
                            val y = centerY + sin(angle) * size / 2

                            drawLine(
                                color = Color(0xFF00E5FF).copy(alpha = 0.5f),
                                start = Offset(centerX, centerY),
                                end = Offset(x, y),
                                strokeWidth = 1f
                            )
                        }
                    }

                    Text(
                        "LIVE PREVIEW",
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 16.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White.copy(alpha = 0.5f)
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Control Panel
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.05f)),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.MotionPhotosAuto, null, tint = Color(0xFF00E5FF))
                            Spacer(Modifier.width(16.dp))
                            Text("Enable Kinetic Motion", modifier = Modifier.weight(1f), color = Color.White)
                            Switch(
                                checked = isEnabled,
                                onCheckedChange = { isEnabled = it },
                                colors = SwitchDefaults.colors(checkedThumbColor = Color(0xFF00E5FF))
                            )
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        Text("Sensitivity", style = MaterialTheme.typography.labelMedium, color = Color.Gray)
                        Slider(
                            value = sensitivity,
                            onValueChange = { sensitivity = it },
                            colors = SliderDefaults.colors(
                                thumbColor = Color(0xFF00E5FF),
                                activeTrackColor = Color(0xFF00E5FF)
                            )
                        )
                        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                            Text("Subtle", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                            Text("Aggressive", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        Text(
                            "Max Tilt Angle (${(parallaxTilt * sensitivity * 2).toInt()}°)",
                            style = MaterialTheme.typography.labelMedium,
                            color = Color.Gray
                        )
                        Slider(
                            value = parallaxTilt,
                            onValueChange = { parallaxTilt = it },
                            valueRange = 5f..45f,
                            colors = SliderDefaults.colors(
                                thumbColor = Color(0xFF00E5FF),
                                activeTrackColor = Color(0xFF00E5FF)
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Feature Cards
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    FeatureMiniCard(
                        icon = Icons.Default.ThreeDRotation,
                        title = "3D Shadows",
                        isEnabled = true,
                        modifier = Modifier.weight(1f)
                    )
                    FeatureMiniCard(
                        icon = Icons.Default.CompassCalibration,
                        title = "Auto-Calibrate",
                        isEnabled = false,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
fun ConstellationBackground() {
    val infiniteTransition = rememberInfiniteTransition(label = "stars")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.2f,
        targetValue = 0.8f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alpha"
    )

    Canvas(modifier = Modifier.fillMaxSize()) {
        val points = listOf(
            Offset(size.width * 0.2f, size.height * 0.1f),
            Offset(size.width * 0.8f, size.height * 0.2f),
            Offset(size.width * 0.5f, size.height * 0.5f),
            Offset(size.width * 0.1f, size.height * 0.8f),
            Offset(size.width * 0.9f, size.height * 0.9f)
        )

        points.forEach { pos ->
            drawCircle(Color(0xFF00E5FF).copy(alpha = alpha), radius = 4f, center = pos)
        }

        // Connect some
        drawLine(Color(0xFF00E5FF).copy(alpha = 0.1f), points[0], points[2], strokeWidth = 1f)
        drawLine(Color(0xFF00E5FF).copy(alpha = 0.1f), points[1], points[2], strokeWidth = 1f)
        drawLine(Color(0xFF00E5FF).copy(alpha = 0.1f), points[3], points[2], strokeWidth = 1f)
    }
}

@Composable
fun FeatureMiniCard(
    icon: ImageVector,
    title: String,
    isEnabled: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = if (isEnabled) Color(0xFF00E5FF).copy(alpha = 0.1f) else Color.White.copy(
                alpha = 0.05f
            )
        ),
        border = BorderStroke(
            1.dp,
            if (isEnabled) Color(0xFF00E5FF).copy(alpha = 0.3f) else Color.White.copy(alpha = 0.1f)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(icon, null, tint = if (isEnabled) Color(0xFF00E5FF) else Color.Gray)
            Spacer(Modifier.height(8.dp))
            Text(
                title,
                style = MaterialTheme.typography.labelMedium,
                color = if (isEnabled) Color.White else Color.Gray
            )
        }
    }
}
