package com.mera.islam.duaazkar.core.presentation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.Spring.StiffnessLow
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.unit.IntOffset
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

fun Modifier.bounceClickable(
    minScale: Float = 0.5f,
    onAnimationFinished: (() -> Unit)? = null,
    onClick: (() -> Unit)? = null,
) = composed {
    var isPressed by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (isPressed) minScale else 1f,
        label = ""
    ) {
        if (isPressed) {
            isPressed = false
            onAnimationFinished?.invoke()
        }
    }

    this
        .graphicsLayer {
            scaleX = scale
            scaleY = scale
        }
        .clickable {
            isPressed = true
            onClick?.invoke()
        }
}

fun Modifier.springEffect() = composed {
    val offsetY = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    offset { IntOffset(0, offsetY.value.roundToInt()) }

    pointerInput(Unit) {
        awaitEachGesture {
            //Detect a touch down event
            awaitFirstDown()
            do {
                val event: PointerEvent = awaitPointerEvent()
                event.changes.forEach { pointerInputChange: PointerInputChange ->
                    //Consume the change
                    scope.launch {
                        offsetY.snapTo(
                            offsetY.value + pointerInputChange.positionChange().y
                        )
                    }
                }
            } while (event.changes.any { it.pressed })

            // Touch released - Action_UP
            scope.launch {
                offsetY.animateTo(
                    targetValue = 0f, spring(
                        dampingRatio = Spring.DampingRatioLowBouncy,
                        stiffness = StiffnessLow

                    )
                )
            }
        }
    }
}

