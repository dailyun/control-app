package com.example.controlapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.min

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HeartPulseScreen()
        }
    }
}

@Composable
private fun HeartPulseScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE91E63)),
        contentAlignment = Alignment.Center
    ) {
        HeartPulseAnimation()
    }
}

@Composable
private fun HeartPulseAnimation() {
    val infiniteTransition = rememberInfiniteTransition(label = "heartPulse")
    val beatScale by infiniteTransition.animateFloat(
        initialValue = 0.9f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 900, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "beatScale"
    )

    val ringProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2200, easing = LinearEasing)
        ),
        label = "ringProgress"
    )

    Canvas(modifier = Modifier.fillMaxSize()) {
        val center = Offset(size.width / 2f, size.height / 2f)
        val baseRadius = min(size.width, size.height) * 0.08f
        val maxRadius = min(size.width, size.height) * 0.48f
        val ringCount = 6
        val ringGap = 1f / ringCount

        repeat(ringCount) { index ->
            val progress = (ringProgress + ringGap * index) % 1f
            val radius = baseRadius + (maxRadius - baseRadius) * progress
            val alpha = (1f - progress).coerceIn(0f, 1f)
            drawCircle(
                color = Color(0xFFFFF1F7).copy(alpha = alpha),
                radius = radius,
                center = center,
                style = Stroke(width = 8.dp.toPx())
            )
        }

        scale(beatScale, beatScale, pivot = center) {
            val heartSize = min(size.width, size.height) * 0.22f
            val heartPath = buildHeartPath(center, heartSize)
            drawPath(
                path = heartPath,
                color = Color(0xFFE53935)
            )
            drawPath(
                path = heartPath,
                color = Color(0xFFB71C1C),
                style = Stroke(width = 6.dp.toPx())
            )
        }
    }
}

private fun buildHeartPath(center: Offset, size: Float): Path {
    val half = size / 2f
    val top = center.y - half * 0.4f
    val left = center.x - half
    val right = center.x + half
    val bottom = center.y + half

    return Path().apply {
        moveTo(center.x, bottom)
        cubicTo(
            center.x - half * 1.1f,
            center.y + half * 0.3f,
            left,
            center.y - half * 0.2f,
            center.x,
            top
        )
        cubicTo(
            right,
            center.y - half * 0.2f,
            center.x + half * 1.1f,
            center.y + half * 0.3f,
            center.x,
            bottom
        )
        close()
    }
}

@Preview(showBackground = true)
@Composable
private fun HeartPulsePreview() {
    HeartPulseScreen()
}
