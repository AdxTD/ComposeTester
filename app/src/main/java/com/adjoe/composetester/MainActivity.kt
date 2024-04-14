package com.adjoe.composetester

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.adjoe.composetester.ui.theme.ComposeTesterTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTesterTheme {
                //GreetingTest()
                //ImageCardTest()
                //ExternalStateTester()
                //GreetMe()
                //listTest()
                //ConstraintsLayoutTester()
                EffectHandlerTester()
            }
        }
    }
}

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

@Composable
fun LaunchedEffectAnimation(counter: Int){
    val animatable = remember {
        Animatable(0f)
    }
    LaunchedEffect(key1 = counter){
        animatable.animateTo(counter.toFloat())
    }
}

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

@Composable
fun listTest(){
    LazyColumn{
        itemsIndexed(
            listOf("This", "is", "Jetpack", "Compose" )
        ){index, string ->
            Text(
                text = "$string + $index",
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp)
            )

        }
    }
    /*LazyColumn{
        items(5000){
            Text(
                text = "Item $it",
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp)
            )

        }
    }*/
    /*val scrollState = rememberScrollState()
    Column(
        modifier = Modifier.verticalScroll(scrollState)
    ){
        for ( i in 1..100){
            Text(
                text = "Item $i",
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp)
            )
        }
    }    */
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GreetMe(){
    val snackbarHostState = remember { SnackbarHostState() }
    var textFieldState by remember {
        mutableStateOf("")
    }
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp),

            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            TextField(
                value = textFieldState,
                label = {
                    Text(text = "Enter your name")
                },
                onValueChange = {
                    textFieldState = it
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                scope.launch {
                    snackbarHostState.showSnackbar("Hello $textFieldState")
                }
            }) {
                Text(text = "Pls greet me")
            }
        }
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
fun ImageCard(
    painter: Painter,
    contentDescription: String,
    title: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(5.dp, 5.dp, 5.dp, 5.dp, 5.dp, 5.dp)
    ) {
        Box(
            modifier = Modifier.height(600.dp)
        ) {
            Image(
                painter = painter,
                contentDescription = contentDescription,
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black
                            ),
                            startY = 500f
                        )
                    )
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                Text(text = title, style = TextStyle(color = Color.White, fontSize = 16.sp))
            }
        }
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

@Composable
fun TextFontStylingTest() {
    val fontFamily = FontFamily(
        Font(R.font.lexend_black, weight = FontWeight.Black),
        Font(R.font.lexend_thin, weight = FontWeight.Thin),
        Font(R.font.lexend_bold, weight = FontWeight.Bold)
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0XFF101010))
    ) {
        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = Color.Green,
                        fontSize = 50.sp
                    )
                ) {
                    append("J")
                }
                append("etpack")
                withStyle(
                    style = SpanStyle(
                        color = Color.Green,
                        fontSize = 50.sp
                    )
                ) {
                    append("C")
                }
                append("ompose")
            },
            color = Color.White,
            fontSize = 30.sp,
            fontFamily = fontFamily,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            textAlign = TextAlign.Center,
            textDecoration = TextDecoration.Underline
        )
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

@Composable
fun ExternalStateTester(modifier: Modifier = Modifier){
    val color = remember {
        mutableStateOf(Color.Green)
    }
    Column(Modifier
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