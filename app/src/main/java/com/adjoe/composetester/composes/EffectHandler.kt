package com.adjoe.composetester.composes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import kotlinx.coroutines.delay

private var i = 0
@Composable
fun EffectHandlerTester(){
    // it is important to watch this video to understand the below:
    //https://www.youtube.com/watch?v=gxWcfz3V2QE&list=PLQkwcJG4YTCSpJ2NLhDTHhi6XBNfk9WiC&index=10&ab_channel=PhilippLackner
    val text by remember {
        mutableStateOf("")
    }
    // This is a bad practice, it is called a side effect, because each time the composable is recomposed,
    // then i++ will be triggered
    /*Button(onClick = { text += "#"}) {
        i++ // this called: escape scope, we might have to do it like in collecting flows
        Text(text = text)
    }*/
    LaunchedEffect(key1 = text, block = {
        delay(1000L)
        println("Text is $text")
    } )
}