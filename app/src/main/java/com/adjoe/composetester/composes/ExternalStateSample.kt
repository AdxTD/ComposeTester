package com.adjoe.composetester.composes

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlin.random.Random

@Composable
fun ExternalStateTester(modifier: Modifier = Modifier){
    val color = remember {
        mutableStateOf(Color.Green)
    }
    Column(
        Modifier
        .fillMaxSize()) {
        StateColorBoxTester(
            Modifier
                .weight(1f)
                .fillMaxSize()
        ) {
            color.value = it
        }
        Box(modifier = Modifier
            .background(color.value)
            .weight(1f)
            .fillMaxSize())
    }
}


@Composable
fun StateColorBoxTester(modifier: Modifier = Modifier,
                        updateColor : (Color) -> Unit
) {
    Box(modifier = modifier
        .background(Color.Yellow)
        .clickable {
            updateColor(
                Color(
                    Random.nextFloat(),
                    Random.nextFloat(),
                    Random.nextFloat(),
                    1f
                )
            )
        }
    )
}