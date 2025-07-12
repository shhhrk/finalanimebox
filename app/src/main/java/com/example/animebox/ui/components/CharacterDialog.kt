package com.example.animebox.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.animebox.domain.model.Character
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.animation.core.Animatable
import kotlinx.coroutines.launch

@Composable
fun CharacterDialog(
    character: Character,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val startAnim = remember { mutableStateOf(false) }
                val scale by animateFloatAsState(
                    targetValue = if (startAnim.value) 1f else 0.6f,
                    animationSpec = tween(durationMillis = 500), label = "scale"
                )
                val alpha by animateFloatAsState(
                    targetValue = if (startAnim.value) 1f else 0f,
                    animationSpec = tween(durationMillis = 500), label = "alpha"
                )
                val flashAlpha = remember { Animatable(0f) }
                LaunchedEffect(Unit) {
                    startAnim.value = true
                    flashAlpha.snapTo(0f)
                    flashAlpha.animateTo(1f, animationSpec = tween(80))
                    flashAlpha.animateTo(0f, animationSpec = tween(320))
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .padding(bottom = 16.dp)
                        .scale(scale)
                        .graphicsLayer { this.alpha = alpha }
                ) {
                    Image(
                        painter = painterResource(id = character.imageRes),
                        contentDescription = character.name,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White.copy(alpha = flashAlpha.value))
                    )
                }
                Text(
                    text = character.name,
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "Редкость: ${character.rarity}",
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Button(
                    onClick = {
                        onConfirm()
                        onDismiss()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("OK")
                }
            }
        }
    }
} 