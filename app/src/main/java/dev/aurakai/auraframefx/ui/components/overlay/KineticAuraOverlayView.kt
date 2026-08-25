package dev.aurakai.auraframefx.ui.components.overlay

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.view.View
import android.view.WindowManager
import dev.aurakai.auraframefx.ui.visuals.BreathingEdgeGlow

/**
 * 🌀 Kinetic Aura Overlay View
 * A hardware-accelerated overlay that breathes with the Mesh.
 */
class KineticAuraOverlayView(context: Context) : View(context) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 4f
        color = Color.CYAN
        xfermode = PorterDuffXfermode(PorterDuff.Mode.SCREEN)
    }

    private var pulseAlpha = 1.0f

    init {
        // Set up the WindowManager params for a system overlay
        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or
                    WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
            PixelFormat.TRANSLUCENT
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Use the Kinetic Architect's interpolation for the pulse
        pulseAlpha =
            BreathingEdgeGlow.calculateSovereignInterpolation(System.currentTimeMillis() % 2000 / 2000f)
        paint.alpha = (pulseAlpha * 255).toInt()

        // Draw the sovereign border
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)

        // Invalidate to keep the pulse breathing
        invalidate()
    }
}
