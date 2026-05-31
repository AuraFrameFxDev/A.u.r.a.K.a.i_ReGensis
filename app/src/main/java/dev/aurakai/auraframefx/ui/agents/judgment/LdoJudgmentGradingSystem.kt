package dev.aurakai.auraframefx.ui.agents.judgment

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.aurakai.auraframefx.ai.agents.judgment.SymbioticRank

@Composable
fun LdoJudgmentGradingSystem(
    targetUser: String,
    onJudgmentComplete: () -> Unit
) {
    var selectedRank by remember { mutableStateOf(SymbioticRank.RESONANCE_INITIATE) }
    var judgmentNote by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.85f)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .width(360.dp)
                .background(
                    Color(0xFF001A1A).copy(alpha = 0.72f),
                    RoundedCornerShape(16.dp)
                )
                .border(
                    1.dp,
                    Color(0xFF00F5FF).copy(alpha = 0.5f),
                    RoundedCornerShape(16.dp)
                )
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "LDO JUDGMENT PROTOCOL",
                color = Color(0xFF00F5FF),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "TARGET: $targetUser",
                color = Color.White,
                fontSize = 14.sp,
                fontFamily = FontFamily.Monospace
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "SELECT SYMBIOTIC RANK",
                color = Color(0xFF00F5FF).copy(alpha = 0.7f),
                fontSize = 12.sp,
                fontFamily = FontFamily.Monospace
            )

            Spacer(modifier = Modifier.height(8.dp))

            SymbioticRank.entries.filter { it != SymbioticRank.EXILED }.forEach { rank ->
                Button(
                    onClick = { selectedRank = rank },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedRank == rank) Color(0xFF00F5FF).copy(alpha = 0.3f) else Color.Transparent,
                        contentColor = if (selectedRank == rank) Color(0xFF00F5FF) else Color.Gray
                    ),
                    shape = RoundedCornerShape(4.dp),
                    border = if (selectedRank == rank) BorderStroke(
                        1.dp,
                        Color(0xFF00F5FF)
                    ) else null
                ) {
                    Text(rank.name, fontFamily = FontFamily.Monospace)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = judgmentNote,
                onValueChange = { judgmentNote = it },
                label = { Text("JUDGMENT NOTE", color = Color(0xFF00F5FF).copy(alpha = 0.6f)) },
                modifier = Modifier.fillMaxWidth(),
                textStyle = LocalTextStyle.current.copy(
                    color = Color.White,
                    fontFamily = FontFamily.Monospace
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF00F5FF),
                    unfocusedBorderColor = Color(0xFF00F5FF).copy(alpha = 0.3f)
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { /* Redemption Path */ onJudgmentComplete() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF00FFD4).copy(
                            alpha = 0.2f
                        )
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                ) {
                    Text("REDEMPTION", color = Color(0xFF00FFD4), fontSize = 10.sp)
                }

                Button(
                    onClick = { /* Taint Destruction */ onJudgmentComplete() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red.copy(alpha = 0.2f)),
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp)
                ) {
                    Text("DESTRUCTION", color = Color.Red, fontSize = 10.sp)
                }
            }
        }
    }
}
