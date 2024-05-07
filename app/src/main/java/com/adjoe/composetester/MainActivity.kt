package com.adjoe.composetester

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.adjoe.composetester.composes.EffectHandlerTester
import com.adjoe.composetester.ui.theme.ComposeTesterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTesterTheme {
                //GreetingTest()
                //ImageCardTest()
                //ExternalStateTester()
                //GreetMe()
                //ListTest()
                //ConstraintsLayoutTester()
                EffectHandlerTester()
            }
        }
    }
}



