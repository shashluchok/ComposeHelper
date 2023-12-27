package ru.shashluchokdesignhelper.widgets.popup

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties

private const val transitionLabel = "popUpContentTransition"
private const val scaleAnimationLabel = "scale"
private const val alphaAnimationLabel = "alpha"
private const val scaleAnimationDuration = 200
private const val alphaAnimationDuration = 200

private val popUpTonalElevation = 1.dp
private val popUpShadowElevation = 3.dp
private val popUpCornerRadius = 4.dp

@Composable
fun SdhPopup(
    modifier: Modifier = Modifier,
    popUpVisible: Boolean,
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit
) {
    val transitionState = remember { MutableTransitionState(false) }

    LaunchedEffect(popUpVisible) {
        transitionState.targetState = popUpVisible
    }

    val transition = updateTransition(transitionState, transitionLabel)

    val scale by transition.animateFloat(
        transitionSpec = {
            tween(durationMillis = scaleAnimationDuration)
        },
        label = scaleAnimationLabel
    ) {
        if (it) 1f else 0f
    }

    val alpha by transition.animateFloat(
        transitionSpec = {
            tween(durationMillis = alphaAnimationDuration)
        },
        label = alphaAnimationLabel
    ) {
        if (it) 1f else 0f
    }

    if (transitionState.currentState || transitionState.targetState) {
        Popup(
            properties = PopupProperties(focusable = true),
            onDismissRequest = onDismissRequest,
            alignment = Alignment.BottomEnd
        ) {
            Surface(
                modifier = modifier
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                        this.alpha = alpha
                        this.transformOrigin = TransformOrigin(
                            pivotFractionX = 1f,
                            pivotFractionY = 1f
                        )
                    },
                shape = RoundedCornerShape(popUpCornerRadius),
                tonalElevation = popUpTonalElevation,
                shadowElevation = popUpShadowElevation,
            ) {
                content()
            }
        }
    }
}
