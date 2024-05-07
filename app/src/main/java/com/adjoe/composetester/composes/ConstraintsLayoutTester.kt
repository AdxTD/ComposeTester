package com.adjoe.composetester.composes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension

@Composable
fun ConstraintsLayoutTester(){
    val constraintSet = ConstraintSet {
        val greenBoxRef = createRefFor("green_box") // just like an id in xml
        val redBoxRef = createRefFor("red_box")
        val guideLine = createGuidelineFromTop(0.5f)
        constrain(greenBoxRef){
            //top.linkTo(parent.top)
            top.linkTo(guideLine)
            start.linkTo(parent.start)
            width = Dimension.value(100.dp)
            height = Dimension.value(100.dp)
        }
        constrain(redBoxRef){
            top.linkTo(parent.top)
            start.linkTo(greenBoxRef.end)
            end.linkTo(parent.end)
            //width = Dimension.fillToConstraints
            width = Dimension.value(100.dp)
            height = Dimension.value(100.dp)
        }
        createHorizontalChain(greenBoxRef, redBoxRef, chainStyle = ChainStyle.Packed)
    }
    ConstraintLayout(constraintSet, modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier
            .background(Color.Green)
            .layoutId("green_box")
        )
        Box(modifier = Modifier
            .background(Color.Red)
            .layoutId("red_box")
        )
    }
}
