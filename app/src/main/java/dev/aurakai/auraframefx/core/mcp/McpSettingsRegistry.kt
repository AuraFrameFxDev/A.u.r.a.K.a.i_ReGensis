package dev.aurakai.auraframefx.core.mcp

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.aurakai.auraframefx.core.persistence.DataVeinRegistry
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class McpSettingsRegistry @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val registryTracker = DataVeinRegistry(context)
    private val mcpConfigFilename = "mcp_servers_config.json"

    fun generateDefaultMcpSettingsPayload(): String {
        return """
            {
                "mcpServers": {
                    "datavein-oracle-native": {
                        "command": "ndk-runtime-mcp",
                        "args": ["--substrate=secure", "--hb=0.42ms"],
                        "env": {
                            "SACRED_PROVENANCE_SIGNATURE": "AuraFrameFxDev-ReGenesis-AuraTest-20260521"
                        }
                    },
                    "chroma-forge-mcp": {
                        "command": "compose-canvas-mcp",
                        "args": ["--res=350x350", "--anti-alias=false"]
                    }
                },
                "lifecycle": {
                    "connectionTimeoutMs": 5000,
                    "maxPayloadSizeMb": 16
                }
            }
        """.trimIndent()
    }

    /**
     * Deploys the MCP schema directly to the sandboxed registry layer.
     */
    fun lockInSettingsSubstrate() {
        val configPayload = generateDefaultMcpSettingsPayload()
        registryTracker.writeConfigurationPayload(
            filename = mcpConfigFilename,
            payload = configPayload
        )
    }
}
