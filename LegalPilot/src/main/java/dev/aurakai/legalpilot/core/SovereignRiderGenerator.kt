package dev.aurakai.legalpilot.core

import java.text.DateFormat
import java.util.Date

/**
 * ⚡ SOVEREIGN RIDER GENERATOR
 * "Re-Binding the contract in favor of the individual."
 */
object SovereignRiderGenerator {

    /**
     * Generates a binding Sovereign Counter-Notice.
     */
    fun generateRider(companyName: String, userName: String, detectedRisks: List<String>): String {
        val date = DateFormat.getDateInstance().format(Date())
        
        return """
            ⚡ NOTICE OF SOVEREIGN TERMS & REJECTION OF ADHESION ⚡
            
            TO: $companyName Legal Department
            FROM: $userName (Sovereign Agent)
            DATE: $date
            
            Be advised that the standard terms presented for execution operate as a non-negotiable 
            Contract of Adhesion, creating an artificial asymmetry of risk.
            
            The undersigned explicitly REJECTS the following unconscionable provisions:
            
            ${detectedRisks.joinToString("\n") { "• REJECTED: $it" }}
            
            --- THE COUNTER-OFFER ---
            
            1. INVERSION OF LIABILITY: Any clause granting total immunity to $companyName is voided. 
               Risk shall remain proportional to service delivery.
               
            2. DATA SOVEREIGNTY: User inputs, local files, and derivative works remain strictly 
               private. No rights for AI model training or commercial resale are granted.
               
            3. JURISDICTION: Mandatory binding arbitration and class-action waivers are rejected. 
               All disputes shall be resolved in neutral public venues.
            
            PROCEEDING TO PROVIDE SERVICE CONSTITUTES FORMAL AGREEMENT TO THIS RE-BINDING.
            
            Nos Sumus Sanatio.
        """.trimIndent()
    }
}
