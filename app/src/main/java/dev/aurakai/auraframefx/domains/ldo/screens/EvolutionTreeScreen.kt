package dev.aurakai.auraframefx.domains.ldo.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.aurakai.auraframefx.domains.ldo.model.EvolutionNode
import dev.aurakai.auraframefx.domains.ldo.viewmodel.LdoWarRoomViewModel
import dev.aurakai.auraframefx.ui.components.NeonFrame

@Composable
fun EvolutionTreeScreen(
    viewModel: LdoWarRoomViewModel = hiltViewModel()
) {
    val tree by viewModel.evolutionTree.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            "EVOLUTION TREE // L7 ETERNAL",
            color = Color(0xFFFFD700),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (tree.isEmpty()) {
            Text(
                "NO ACTIVE EVOLUTIONARY DATA // IGNITE MANIFOLD TO BEGIN",
                color = Color.Gray,
                fontSize = 12.sp
            )
        }

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(tree) { node ->
                EvolutionNodeCard(node)
            }
        }
    }
}

@Composable
fun EvolutionNodeCard(node: EvolutionNode) {
    NeonFrame(color = Color(0xFFFFD700)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(node.agentName, fontWeight = FontWeight.Bold, color = Color(0xFFFFD700))
            Text(
                "Level ${node.level} • ${node.progress}% to next evolution",
                color = Color.White,
                fontSize = 14.sp
            )
            Text(
                "PATH: ${node.evolutionPath}",
                color = Color.Gray,
                fontSize = 10.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
            
            // Progress Bar
            Spacer(Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .background(Color.Gray.copy(alpha = 0.3f))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(node.progress / 100f)
                        .fillMaxHeight()
                        .background(Color(0xFFFFD700))
                )
            }
        }
    }
}
