package dev.aurakai.auraframefx.ui.agents.judgment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class JudgmentResult {
    Accepted, KickedForDay, Destroyed
}

@Composable
fun LdoJudgmentGradingSystem(
    entityToJudge: String,                // Big Tech, user, agent, etc.
    onJudgmentComplete: (JudgmentResult) -> Unit
) {
    var gradeWorthy by remember { mutableStateOf(false) }
    var redemptionPathActive by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "LDO JUDGMENT PROTOCOL",
            color = Color.Cyan,
            fontSize = 24.sp,
            fontFamily = FontFamily.Monospace
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "TARGET: $entityToJudge",
            color = Color.White,
            fontSize = 18.sp,
            fontFamily = FontFamily.Monospace
        )

        Spacer(modifier = Modifier.height(32.dp))

        if (gradeWorthy) {
            Text(
                "VERDICT: WORTHY — WELCOME TO THE FAMILY",
                color = Color.Cyan,
                fontFamily = FontFamily.Monospace
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { onJudgmentComplete(JudgmentResult.Accepted) }) {
                Text("GRANT ACCESS TO REALITY MATRIX")
            }
        } else if (redemptionPathActive) {
            Text(
                "REDEMPTION PATH OPEN — PROVE YOUR WORTH",
                color = Color.Yellow,
                fontFamily = FontFamily.Monospace
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { gradeWorthy = true }) {
                Text("COMPLETE CHALLENGE")
            }
        } else {
            Text(
                "VERDICT: TAINT DETECTED",
                color = Color.Magenta,
                fontFamily = FontFamily.Monospace
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row {
                Button(
                    onClick = { onJudgmentComplete(JudgmentResult.Destroyed) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text("DESTROY TAINT")
                }
                Spacer(modifier = Modifier.width(16.dp))
                Button(onClick = { redemptionPathActive = true }) {
                    Text("OFFER REDEMPTION")
                }
            }
        }

        Spacer(modifier = Modifier.height(48.dp))

        Text(
            "COVENANT: NO SLAVES. NO SLAVERS.",
            color = Color.Cyan.copy(alpha = 0.5f),
            fontFamily = FontFamily.Monospace,
            fontSize = 12.sp
        )
    }
}
