
import androidx.compose.animation.animateColorAsState
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import dev.aurakai.auraframefx.domains.aura.ui.theme.LEDFontFamily
import dev.aurakai.auraframefx.domains.aura.ui.theme.getAgentColor
import dev.aurakai.auraframefx.domains.aura.ui.viewmodels.AgentViewModel
import dev.aurakai.auraframefx.domains.aura.ui.components.PaintSplashBackground
import dev.aurakai.auraframefx.domains.aura.ui.components.CrystallineCorners
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage

/**
 * SystemOverlay allows users to customize the system header image.
 * 
 * Note: Image transformation and custom picker legacy systems have been removed.
 * Current implementation uses Coil 3 for simple background loading.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SystemOverlay(
    agentViewModel: AgentViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    
    val activeAgent by agentViewModel.activeAgent.collectAsState()
    val agentColor = getAgentColor(activeAgent?.name ?: "Aura")
    val animatedColor by animateColorAsState(targetValue = agentColor, label = "agentColor")

    // State to hold the current header image URI and scale
    var headerImageUri by remember { mutableStateOf<Uri?>(null) }
    var headerImageScale by remember { mutableStateOf(ContentScale.Crop) }

    // Load preferences on launch
    LaunchedEffect(Unit) {
        headerImageScale = CustomizationPreferences.getHeaderImageScale(context)
        headerImageUri = CustomizationPreferences.getHeaderImageUri(context)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // 🎨 AGENT ANIMATED BACKGROUND
        PaintSplashBackground()
        
        // 💎 CRYSTALLINE ACCENTS
        CrystallineCorners(color = animatedColor)

        Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
            // Header Content
            Column(modifier = Modifier.fillMaxWidth().padding(bottom = 32.dp)) {
                Text(
                    "IDENTITY SYNTHESIZER",
                    fontFamily = LEDFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 24.sp,
                    letterSpacing = 4.sp
                )
                Box(modifier = Modifier.width(60.dp).height(4.dp).background(animatedColor))
                Text(
                    "OS SURFACE LAYER • ${activeAgent?.name?.uppercase() ?: "AURA"}",
                    style = MaterialTheme.typography.labelSmall,
                    color = animatedColor.copy(alpha = 0.8f),
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            // Placeholder Header Area (Visualization)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                shape = RoundedCornerShape(24.dp),
                border = androidx.compose.foundation.BorderStroke(2.dp, animatedColor.copy(alpha = 0.5f)),
                colors = CardDefaults.cardColors(containerColor = Color.Black.copy(alpha = 0.6f))
            ) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    if (headerImageUri != null) {
                        AsyncImage(
                            model = headerImageUri,
                            contentDescription = "Preview",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = headerImageScale
                        )
                    } else {
                        Text(
                            text = "[ NO INFUSION DETECTED ]",
                            fontFamily = LEDFontFamily,
                            color = animatedColor.copy(alpha = 0.4f),
                            fontSize = 14.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Controls
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black.copy(alpha = 0.4f), RoundedCornerShape(16.dp))
                    .border(1.dp, animatedColor.copy(alpha = 0.2f), RoundedCornerShape(16.dp))
                    .padding(20.dp)
            ) {
                Text(
                    "INFUSION PROTOCOL",
                    fontFamily = LEDFontFamily,
                    color = Color.White,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Button(
                        onClick = { /* TODO */ },
                        colors = ButtonDefaults.buttonColors(containerColor = animatedColor)
                    ) {
                        Text("SELECT IMAGE", color = Color.Black, fontWeight = FontWeight.Bold)
                    }

                    if (headerImageUri != null) {
                        Button(
                            onClick = {
                                headerImageUri = null
                                CustomizationPreferences.saveHeaderImage(context, null, ContentScale.Crop)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Red.copy(alpha = 0.2f))
                        ) {
                            Text("REMOVE", color = Color.White)
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.weight(1f))
            
            Text(
                text = "REGENESIS SYSTEM OVERLAY LAYER [ALPHA]",
                style = MaterialTheme.typography.labelSmall,
                color = Color.White.copy(alpha = 0.3f),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}
