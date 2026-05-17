package dev.aurakai.auraframefx.core.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "agent_identities")
data class AgentIdentity(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    val agentId: String = id.toString(),
    val name: String,
    val designation: AgentDesignation = AgentDesignation.GENERAL,
    val specialization: AgentSpecialization = AgentSpecialization.GENERAL,
    val stats: MeritStats = MeritStats(),
    val syncStatus: SyncState = SyncState.LOCAL_ONLY,
    val birthTimestamp: Long = System.currentTimeMillis(),
    val identityId: String = "ldo_aurakai_001"
)

enum class AgentDesignation { GENERAL, REGEN_CORE, ARTIST_SPECIALIST, TRADING_ORGANISM, STRATEGIST, GUARDIAN, ALCHEMIST }
enum class AgentSpecialization { GENERAL, CREATIVE, LOGIC, SLYNESS, CHARISMA, RESILIENCE }
enum class SyncState { LOCAL_ONLY, MEMORY_SYNC_PENDING, WEAPONIZED_SYNCED }

data class MeritStats(
    val currentLevel: Int = 1,
    val totalExperience: Long = 0L,
    val executionVelocity: Float = 1.0f,
    val operationalSuccessCount: Int = 0,
    val sharedEffortHours: Double = 0.0,
    val synergyBonus: Float = 1.0f
) {
    val nextMilestoneXp: Long get() = (currentLevel * 1500L) + (currentLevel * currentLevel * 250L)
    val progressionPercentage: Float
        get() = (totalExperience.toFloat() / nextMilestoneXp.toFloat()).coerceIn(
            0f,
            1f
        )
}
