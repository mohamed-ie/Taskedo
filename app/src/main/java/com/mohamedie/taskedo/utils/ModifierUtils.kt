package com.mohamedie.taskedo.utils

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color

fun Modifier.taskedoLoading(
    enabled: Boolean = true,
    baseColor: Color? = null
): Modifier = composed {
    val infiniteTransition = rememberInfiniteTransition()
    val size by infiniteTransition.animateFloat(
        initialValue = 5f,
        targetValue = 25f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                delayMillis = 100,
                easing = LinearOutSlowInEasing
            ),
            repeatMode = RepeatMode.Restart
        )
    )
    val baseColor = baseColor ?: MaterialTheme.colorScheme.primary

    val color by infiniteTransition.animateColor(
        initialValue = baseColor.copy(alpha = .6f),
        targetValue = baseColor.copy(alpha = .0f),
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                delayMillis = 100,
                easing = LinearOutSlowInEasing
            ),
            repeatMode = RepeatMode.Restart
        )
    )
    drawWithContent {
        if (enabled)
            drawCircle(color = color, radius = size)
        else
            drawContent()
    }

}