package com.jeric.ricafrente.naruto.ui.helper

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun NarutoLoadingBar(
    modifier: Modifier = Modifier,
    size: Dp = 60.dp
) {
    val animation = rememberInfiniteTransition(label = "Loading Bar")
    val progress by animation.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000),
            repeatMode = RepeatMode.Restart
        ),
        label = ""
    )
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()){
        Box(
            modifier = modifier
                .size(size)
                .scale(progress)
                .alpha(1f - progress)
                .border(
                    width = 5.dp,
                    color = MaterialTheme.colorScheme.onBackground,
                    shape = CircleShape
                )
        )
    }
}