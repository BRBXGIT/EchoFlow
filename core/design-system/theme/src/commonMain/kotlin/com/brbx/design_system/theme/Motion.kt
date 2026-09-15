package com.brbx.design_system.theme

import androidx.compose.animation.core.DurationBasedAnimationSpec
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.material3.MotionScheme
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf

internal val LocalMotion = staticCompositionLocalOf<EchoFlowMotion> { DefaultMotion() }

@Immutable
interface EchoFlowMotion : MotionScheme {

    // ==========================================
    // SPATIAL (Geometry: Size, Offset, Scale)
    // ==========================================

    /**
     * Used for large spatial transitions (e.g., bottom sheets).
     * Features a smooth start and a confident, almost bounce-free completion.
     */
    fun <T> slowSpatialSpec(visibilityThreshold: T? = null): SpringSpec<T>

    /**
     * The default spatial animation for standard UI element resizing (e.g., expanding cards, dropdowns).
     * Features a very subtle, organic micro-bounce at the end.
     */
    fun <T> mediumSpatialSpec(visibilityThreshold: T? = null): SpringSpec<T>

    /**
     * Fast and crisp spatial animation for small elements (e.g., switches, small icons).
     */
    fun <T> fastSpatialSpec(visibilityThreshold: T? = null): SpringSpec<T>

    // ==========================================
    // EFFECTS (Accents, Notifications, Feedback)
    // ==========================================

    /**
     * Playful animation with a noticeable bounce.
     * Use for positive reinforcement (e.g., likes, badges, success states).
     */
    fun <T> bouncyEffectSpec(visibilityThreshold: T? = null): SpringSpec<T>

    /**
     * Crisp and rigid animation.
     * Best suited for sharp, immediate feedback (e.g., tooltips, error states).
     */
    fun <T> snappyEffectSpec(visibilityThreshold: T? = null): SpringSpec<T>

    /**
     * Smooth, completely bounce-free animation.
     * Used for gentle background shifts or skeleton loading appearances.
     */
    fun <T> softEffectSpec(visibilityThreshold: T? = null): SpringSpec<T>

    // ==========================================
    // STRUCTURAL (Lists, Enter/Exit)
    // ==========================================

    /**
     * Animation for incoming content. Designed to attract attention without being disruptive.
     */
    fun <T> enterStructuralSpec(visibilityThreshold: T? = null): SpringSpec<T>

    /**
     * Animation for outgoing content.
     * Fast and with little bounce to free up the UI space immediately.
     */
    fun <T> exitStructuralSpec(visibilityThreshold: T? = null): SpringSpec<T>

    /**
     * Specifically tuned for staggered list item appearances and cascading layouts.
     */
    fun <T> listChoreographySpec(visibilityThreshold: T? = null): SpringSpec<T>


    // ==========================================
    // GESTURES (Touch interactions)
    // ==========================================

    /**
     * Immediate, sticky reaction to a touch-down event (e.g., pressing a button or card).
     */
    fun <T> pressGestureSpec(visibilityThreshold: T? = null): SpringSpec<T>

    /**
     * Inertial settling animation after a gesture ends (e.g., swipe-to-dismiss release).
     */
    fun <T> settleGestureSpec(visibilityThreshold: T? = null): SpringSpec<T>


    // ==========================================
    // NON-SPATIAL (Alpha, Color)
    // ==========================================

    /**
     * An exceptionally slow, duration-based animation specification for non-spatial properties.
     * Ideal for almost imperceptible ambient changes, long-running atmospheric background shifts,
     * or dramatic, thematic transitions where the change should feel extremely gradual.
     **/
    fun <T> nonSpatialExtraSlowSpec(delay: Long = 0L): DurationBasedAnimationSpec<T>

    /**
     * A slow, duration-based animation specification for non-spatial properties like alpha or color.
     * Ideal for broad, ambient transitions such as full-screen crossfades, large background color shifts,
     * or gradual state changes that shouldn't feel jarring.
     **/
    fun <T> nonSpatialSlowSpec(delay: Long = 0L): DurationBasedAnimationSpec<T>

    /**
     * The standard, medium-speed duration-based animation specification for non-spatial properties.
     * Best suited for everyday color transitions (e.g., button hover, press, or focus states) and standard
     * component opacity crossfades.
     **/
    fun <T> nonSpatialMediumSpec(delay: Long = 0L): DurationBasedAnimationSpec<T>

    /**
     * A fast, duration-based animation specification for non-spatial properties.
     * Used for immediate, highly responsive visual feedback such as quick alpha toggles, rapid
     * highlight pulses, or instantaneous color snaps.
     **/
    fun <T> nonSpatialFastSpec(delay: Long = 0L): DurationBasedAnimationSpec<T>

    /**
     * An exceptionally fast, almost instantaneous duration-based animation specification.
     * Best suited for micro-interactions or immediate state toggles where a hard cut is slightly
     * too jarring, but a standard fast transition feels sluggish.
     **/
    fun <T> nonSpatialExtraFastSpec(delay: Long = 0L): DurationBasedAnimationSpec<T>
}

@Immutable
internal class DefaultMotion : EchoFlowMotion {

    // =========================================================================
    // MATERIAL 3 OVERRIDES
    // =========================================================================

    override fun <T> defaultSpatialSpec(): SpringSpec<T> = mediumSpatialSpec()
    override fun <T> fastSpatialSpec(): SpringSpec<T> = fastSpatialSpec(null)
    override fun <T> slowSpatialSpec(): SpringSpec<T> = slowSpatialSpec(null)

    override fun <T> defaultEffectsSpec(): SpringSpec<T> = softEffectSpec()
    override fun <T> fastEffectsSpec(): SpringSpec<T> = snappyEffectSpec()
    override fun <T> slowEffectsSpec(): SpringSpec<T> = bouncyEffectSpec()

    // =========================================================================
    // CUSTOM SPECIFICATIONS
    // =========================================================================

    // --- Spatial ---
    override fun <T> slowSpatialSpec(visibilityThreshold: T?): SpringSpec<T> = spring(
        dampingRatio = MotionTokens.Damping.Subtle,
        stiffness = MotionTokens.Stiffness.VeryLow,
        visibilityThreshold = visibilityThreshold,
    )

    override fun <T> mediumSpatialSpec(visibilityThreshold: T?): SpringSpec<T> = spring(
        dampingRatio = MotionTokens.Damping.Organic,
        stiffness = MotionTokens.Stiffness.MediumLow,
        visibilityThreshold = visibilityThreshold,
    )

    override fun <T> fastSpatialSpec(visibilityThreshold: T?): SpringSpec<T> = spring(
        dampingRatio = MotionTokens.Damping.SlightBouncy,
        stiffness = MotionTokens.Stiffness.MediumHigh,
        visibilityThreshold = visibilityThreshold,
    )

    // --- Effects ---
    override fun <T> bouncyEffectSpec(visibilityThreshold: T?): SpringSpec<T> = spring(
        dampingRatio = MotionTokens.Damping.HighBouncy,
        stiffness = MotionTokens.Stiffness.MediumHigh,
        visibilityThreshold = visibilityThreshold,
    )

    override fun <T> snappyEffectSpec(visibilityThreshold: T?): SpringSpec<T> = spring(
        dampingRatio = MotionTokens.Damping.Snappy,
        stiffness = MotionTokens.Stiffness.High,
        visibilityThreshold = visibilityThreshold,
    )

    override fun <T> softEffectSpec(visibilityThreshold: T?): SpringSpec<T> = spring(
        dampingRatio = MotionTokens.Damping.LowBouncy,
        stiffness = MotionTokens.Stiffness.MediumLow,
        visibilityThreshold = visibilityThreshold,
    )

    // --- Structural ---
    override fun <T> enterStructuralSpec(visibilityThreshold: T?): SpringSpec<T> = spring(
        dampingRatio = MotionTokens.Damping.MediumBouncy,
        stiffness = MotionTokens.Stiffness.Medium,
        visibilityThreshold = visibilityThreshold,
    )

    override fun <T> exitStructuralSpec(visibilityThreshold: T?): SpringSpec<T> = spring(
        dampingRatio = MotionTokens.Damping.LowBouncy,
        stiffness = MotionTokens.Stiffness.VeryHigh,
        visibilityThreshold = visibilityThreshold,
    )

    override fun <T> listChoreographySpec(visibilityThreshold: T?): SpringSpec<T> = spring(
        dampingRatio = MotionTokens.Damping.Organic,
        stiffness = MotionTokens.Stiffness.Low,
        visibilityThreshold = visibilityThreshold,
    )

    // --- Gestures ---
    override fun <T> pressGestureSpec(visibilityThreshold: T?): SpringSpec<T> = spring(
        dampingRatio = MotionTokens.Damping.Bouncy,
        stiffness = MotionTokens.Stiffness.VeryHigh,
        visibilityThreshold = visibilityThreshold,
    )

    override fun <T> settleGestureSpec(visibilityThreshold: T?): SpringSpec<T> = spring(
        dampingRatio = MotionTokens.Damping.Bouncy,
        stiffness = MotionTokens.Stiffness.VeryHigh,
        visibilityThreshold = visibilityThreshold,
    )

    // --- Non-Spatial ---
    override fun <T> nonSpatialExtraFastSpec(delay: Long): DurationBasedAnimationSpec<T> =
        tween(
            durationMillis = MotionTokens.NonSpatial.DurationExtraFast,
            delayMillis = delay.toInt(),
            easing = MotionTokens.NonSpatial.EasingExtraFast,
        )

    override fun <T> nonSpatialFastSpec(delay: Long): DurationBasedAnimationSpec<T> = tween(
        durationMillis = MotionTokens.NonSpatial.DurationFastMillis,
        delayMillis = delay.toInt(),
        easing = MotionTokens.NonSpatial.EasingFast,
    )

    override fun <T> nonSpatialMediumSpec(delay: Long): DurationBasedAnimationSpec<T> = tween(
        durationMillis = MotionTokens.NonSpatial.DurationMediumMillis,
        delayMillis = delay.toInt(),
        easing = MotionTokens.NonSpatial.EasingMedium,
    )

    override fun <T> nonSpatialSlowSpec(delay: Long): DurationBasedAnimationSpec<T> = tween(
        durationMillis = MotionTokens.NonSpatial.DurationSlowMillis,
        delayMillis = delay.toInt(),
        easing = MotionTokens.NonSpatial.EasingSlow,
    )

    override fun <T> nonSpatialExtraSlowSpec(delay: Long): DurationBasedAnimationSpec<T> =
        tween(
            durationMillis = MotionTokens.NonSpatial.DurationExtraSlow,
            delayMillis = delay.toInt(),
            easing = MotionTokens.NonSpatial.EasingExtraSlow,
        )
}

private object MotionTokens {

    object Stiffness {
        const val VeryLow = 400f
        const val Low = 600f
        const val MediumLow = 700f
        const val Medium = 900f
        const val MediumHigh = 1000f
        const val High = 1200f
        const val VeryHigh = 1500f
    }

    object Damping {
        const val HighBouncy = 0.35f
        const val Bouncy = 0.55f
        const val MediumBouncy = 0.65f
        const val SlightBouncy = 0.6f
        const val Snappy = 0.65f
        const val Organic = 0.75f
        const val Subtle = 0.8f
        const val LowBouncy = 0.95f
    }

    object NonSpatial {
        const val DurationExtraFast = 150
        const val DurationFastMillis = 300
        const val DurationMediumMillis = 500
        const val DurationSlowMillis = 700
        const val DurationExtraSlow = 850

        val EasingExtraFast = FastOutSlowInEasing
        val EasingFast = LinearOutSlowInEasing
        val EasingMedium = FastOutSlowInEasing
        val EasingSlow = LinearEasing
        val EasingExtraSlow = LinearEasing
    }
}