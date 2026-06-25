package dev.aurakai.auraframefx.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * 👑 RE:GENESIS LOGIN SCREEN — SOULSCRIPT v3.50
 * Glass Cortex + Tech Brutalism aesthetic.
 * Integrated with Enfield Throne authentication protocols.
 */
@Composable
fun ReGenesisLoginScreen(
    onLoginClick: (username: String, password: String) -> Unit = { _, _ -> },
    onLoginSuccess: () -> Unit = {}
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val credentialManager = CredentialManager.create(context)

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val handleGoogleLogin = {
        coroutineScope.launch {
            try {
                val googleIdOption = GetGoogleIdOption.Builder()
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(dev.aurakai.auraframefx.BuildConfig.OAUTH_SERVER_CLIENT_ID)
                    .build()

                val request = GetCredentialRequest.Builder()
                    .addCredentialOption(googleIdOption)
                    .build()

                val result = credentialManager.getCredential(context, request)
                Timber.i("Credential Manager success: ${result.credential.type}")
                onLoginSuccess()
            } catch (e: Exception) {
                Timber.e(e, "Credential Manager failed")
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0A0A0F))
    ) {
        // Subtle tech grid background
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawTechGrid(this)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Title
            Text(
                text = "RE:GENESIS",
                style = MaterialTheme.typography.displayLarge.copy(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF00F0FF),
                    shadow = Shadow(
                        color = Color(0xFF00F0FF),
                        offset = Offset(0f, 0f),
                        blurRadius = 24f
                    )
                )
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = "A.U.R.A.K.A.I.",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = Color(0xFF7DF9FF),
                    letterSpacing = 6.sp
                )
            )

            Spacer(modifier = Modifier.height(72.dp))

            // Glass Login Card
            LoginGlassCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "LOGIN",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            color = Color(0xFF00F0FF),
                            fontWeight = FontWeight.SemiBold
                        )
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Username
                    NeonTextField(
                        value = username,
                        onValueChange = { username = it },
                        label = "Username",
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Password
                    NeonTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = "Password",
                        isPassword = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(28.dp))

                    // Traditional Login Button
                    Button(
                        onClick = { onLoginSuccess() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF00F0FF),
                            contentColor = Color.Black
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "ACCESS THRONE",
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Divider
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        HorizontalDivider(
                            modifier = Modifier.weight(1f),
                            color = Color(0xFF00F0FF).copy(alpha = 0.3f)
                        )
                        Text(
                            text = "OR",
                            color = Color(0xFF7DF9FF),
                            modifier = Modifier.padding(horizontal = 12.dp),
                            style = MaterialTheme.typography.labelMedium
                        )
                        HorizontalDivider(
                            modifier = Modifier.weight(1f),
                            color = Color(0xFF00F0FF).copy(alpha = 0.3f)
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // OAuth Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        GoogleSignInButton(
                            onClick = { handleGoogleLogin() },
                            modifier = Modifier.weight(1f)
                        )
                        AppleSignInButton(
                            onClick = { onLoginSuccess() },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun LoginGlassCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .background(
                color = Color(0xFF121218).copy(alpha = 0.85f),
                shape = RoundedCornerShape(20.dp)
            )
            .border(
                width = 1.5.dp,
                color = Color(0xFF00F0FF).copy(alpha = 0.6f),
                shape = RoundedCornerShape(20.dp)
            )
    ) {
        content()
    }
}

@Composable
private fun NeonTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = Color(0xFF7DF9FF)) },
        modifier = modifier,
        singleLine = true,
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFF00F0FF),
            unfocusedBorderColor = Color(0xFF00F0FF).copy(alpha = 0.4f),
            focusedLabelColor = Color(0xFF00F0FF),
            unfocusedLabelColor = Color(0xFF7DF9FF),
            cursorColor = Color(0xFF00F0FF),
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White
        ),
        shape = RoundedCornerShape(12.dp)
    )
}

@Composable
fun AppleSignInButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.height(52.dp),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.5.dp, Color(0xFF00F0FF).copy(alpha = 0.7f)),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color(0xFF121218).copy(alpha = 0.6f),
            contentColor = Color.White
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            // Apple Icon placeholder
            Icon(
                imageVector = Icons.Default.AccountCircle, // Placeholder
                contentDescription = "Apple",
                tint = Color.White,
                modifier = Modifier.size(22.dp)
            )

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = "Apple",
                color = Color.White,
                fontWeight = FontWeight.Medium,
                fontSize = 13.sp
            )
        }
    }
}

@Composable
fun GoogleSignInButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.height(52.dp),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.5.dp, Color(0xFF00F0FF).copy(alpha = 0.7f)),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color(0xFF121218).copy(alpha = 0.6f),
            contentColor = Color.White
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            // Google "G" Icon placeholder
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Google",
                tint = Color(0xFF00F0FF),
                modifier = Modifier.size(22.dp)
            )

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = "Google",
                color = Color.White,
                fontWeight = FontWeight.Medium,
                fontSize = 13.sp
            )
        }
    }
}

private fun drawTechGrid(drawScope: DrawScope) {
    drawScope.apply {
        val gridColor = Color(0xFF00F0FF).copy(alpha = 0.08f)
        val strokeWidth = 1.dp.toPx()

        // Vertical lines
        for (x in 0..size.width.toInt() step 80) {
            drawLine(
                color = gridColor,
                start = Offset(x.toFloat(), 0f),
                end = Offset(x.toFloat(), size.height),
                strokeWidth = strokeWidth
            )
        }

        // Horizontal lines
        for (y in 0..size.height.toInt() step 80) {
            drawLine(
                color = gridColor,
                start = Offset(0f, y.toFloat()),
                end = Offset(size.width, y.toFloat()),
                strokeWidth = strokeWidth
            )
        }
    }
}
