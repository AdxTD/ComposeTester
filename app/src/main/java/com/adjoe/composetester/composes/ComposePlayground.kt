package com.adjoe.composetester.composes

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adjoe.composetester.R
import com.adjoe.composetester.ui.theme.ComposeTesterTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun RememberCoroutineScopeDemo(){ // Not that often needed, probably you will use ViewModel scope instead
    val scope = rememberCoroutineScope()
    /*scope.launch { // will have a side effect which we are trying to avoid
    //.. it should only be used inside callbacks like the onclick below

    }*/
    Button(onClick = {
        scope.launch {
            delay(1000L)
            println("Hello World !")
        }
    }) {

    }
}


@Composable
fun GreetingTest() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Row(
            modifier = Modifier
                .requiredWidth(800.dp)
                .height(300.dp)
                .background(Color.Green)
                .padding(10.dp)
                .border(5.dp, Color.Magenta)
                .padding(10.dp)
                .border(5.dp, Color.Blue),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Greeting("Android")
        }
        Row(
            modifier = Modifier
                .requiredWidth(450.dp)
                .fillMaxHeight(0.7f)
                .background(Color.LightGray),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Greeting("Android")
        }
    }
    // A surface container using the 'background' color from the theme
    /*Surface(
        //modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {

    }*/
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = Modifier.offset(50.dp, 30.dp),
        color = Color.White
    )
    Spacer(modifier = Modifier.width(100.dp))
    Text(
        text = "Hello Interviews!",
        modifier = Modifier,
        color = Color.White
    )
    Spacer(modifier = Modifier.width(100.dp))
    Text(
        text = "Hello Ramadan!",
        modifier = Modifier.clickable {

        },
        color = Color.White
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeTesterTheme {
        Greeting("Android")
    }
}


@Composable
fun ImageCardTest() {
    val painter = painterResource(id = R.drawable.ic_launcher_background)
    val description = "launcher foreground"
    val title = "jUST AN ImageCard"
    Box(
        modifier = Modifier
            .fillMaxWidth(0.5f)
            .padding(16.dp)
    )
    {
        ImageCard(painter = painter, contentDescription = description, title = title)
    }
}
