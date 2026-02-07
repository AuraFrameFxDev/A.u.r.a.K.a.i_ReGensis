import androidx.compose.animation.animateColorAsState
import dev.aurakai.auraframefx.domains.aura.ui.theme.LEDFontFamily
import dev.aurakai.auraframefx.domains.aura.ui.theme.getAgentColor
import dev.aurakai.auraframefx.domains.aura.ui.viewmodels.AgentViewModel
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import dev.aurakai.auraframefx.domains.aura.ui.components.PaintSplashBackground
import dev.aurakai.auraframefx.domains.aura.ui.components.CrystallineCorners
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

/**
 * GateCustomizationScreen allows users to customize navigation drawer and splash screen images.
 *
 * Note: Image transformation, cropping, and dynamic spacing systems have been discontinued.
 * Current implementation supports basic persistence of URI and scale for select backgrounds.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GateCustomizationScreen(
    navController: NavController,
    agentViewModel: AgentViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    
    val activeAgent by agentViewModel.activeAgent.collectAsState()
    val agentColor = getAgentColor(activeAgent?.name ?: "Aura")
    val animatedColor by animateColorAsState(targetValue = agentColor, label = "agentColor")

    // Navigation Drawer Background Image States
    var navDrawerBgUri by remember { mutableStateOf<Uri?>(null) }
    var navDrawerBgScale by remember { mutableStateOf(ContentScale.Crop) }

    // Splash Screen Image States
    var splashScreenUri by remember { mutableStateOf<Uri?>(null) }
    var splashScreenScale by remember { mutableStateOf(ContentScale.Crop) }

    // Load preferences on launch
    LaunchedEffect(Unit) {
        navDrawerBgUri = CustomizationPreferences.getNavDrawerBackgroundUri(context)
        navDrawerBgScale = CustomizationPreferences.getNavDrawerBackgroundScale(context)
        
        splashScreenUri = CustomizationPreferences.getSplashScreenImageUri(context)
        splashScreenScale = CustomizationPreferences.getSplashScreenImageScale(context)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // 🎨 AGENT ANIMATED BACKGROUND
        PaintSplashBackground()
        
        // 💎 CRYSTALLINE ACCENTS
        CrystallineCorners(color = animatedColor)

        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                "CHROMACORE HUB",
                                fontFamily = LEDFontFamily,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                letterSpacing = 2.sp
                            )
                            Text(
                                "SURFACE CUSTOMIZATION",
                                style = MaterialTheme.typography.labelSmall,
                                color = animatedColor
                            )
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color.Transparent
                    )
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(24.dp)
                    .verticalScroll(scrollState)
            ) {
                // Intro Card
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.Black.copy(alpha = 0.5f)),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, animatedColor.copy(alpha = 0.3f))
                ) {
                    Text(
                        text = "Synthesize your workspace identity. Aura can infuse these gates with your unique presence.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.8f),
                        modifier = Modifier.padding(16.dp)
                    )
                }

                Spacer(Modifier.height(32.dp))

                // Navigation Drawer Background Section
                CustomizationHeader("NAVIGATION DRAWER", animatedColor)
                
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = { /* TODO */ },
                        colors = ButtonDefaults.buttonColors(containerColor = animatedColor)
                    ) {
                        Text("SELECT IMAGE", color = Color.Black, fontWeight = FontWeight.Bold)
                    }
                    
                    if (navDrawerBgUri != null) {
                        Button(onClick = {
                            navDrawerBgUri = null
                            CustomizationPreferences.saveNavDrawerBackground(context, null, ContentScale.Crop)
                        }, colors = ButtonDefaults.buttonColors(containerColor = Color.Red.copy(alpha = 0.2f))) {
                            Text("REMOVE", color = Color.White)
                        }
                    }
                }
                
                navDrawerBgUri?.let {
                    AsyncImage(
                        model = it,
                        contentDescription = "Preview",
                        modifier = Modifier.fillMaxWidth().height(120.dp).clip(RoundedCornerShape(12.dp)).border(1.dp, animatedColor, RoundedCornerShape(12.dp)),
                        contentScale = navDrawerBgScale
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Splash Screen Section
                CustomizationHeader("SPLASH SCREEN", animatedColor)
                
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = { /* TODO */ },
                        colors = ButtonDefaults.buttonColors(containerColor = animatedColor)
                    ) {
                        Text("SELECT IMAGE", color = Color.Black, fontWeight = FontWeight.Bold)
                    }
                    
                    if (splashScreenUri != null) {
                        Button(onClick = {
                            splashScreenUri = null
                            CustomizationPreferences.saveSplashScreenImage(context, null, ContentScale.Crop)
                        }, colors = ButtonDefaults.buttonColors(containerColor = Color.Red.copy(alpha = 0.2f))) {
                            Text("REMOVE", color = Color.White)
                        }
                    }
                }
                
                splashScreenUri?.let {
                    AsyncImage(
                        model = it,
                        contentDescription = "Preview",
                        modifier = Modifier.fillMaxWidth().height(120.dp).clip(RoundedCornerShape(12.dp)).border(1.dp, animatedColor, RoundedCornerShape(12.dp)),
                        contentScale = splashScreenScale
                    )
                }

                Spacer(Modifier.height(48.dp))
                
                Text(
                    text = "IDENTITY IS IMMUTABLE • STYLE IS FLUID",
                    style = MaterialTheme.typography.labelSmall,
                    color = animatedColor.copy(alpha = 0.5f),
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    letterSpacing = 4.sp
                )
            }
        }
    }
}

@Composable
private fun CustomizationHeader(text: String, color: Color) {
    Column {
        Text(
            text = text,
            fontFamily = LEDFontFamily,
            fontSize = 18.sp,
            color = Color.White,
            letterSpacing = 2.sp
        )
        Box(modifier = Modifier.width(40.dp).height(2.dp).background(color))
    }
}
