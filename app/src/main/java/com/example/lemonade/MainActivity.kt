package com.example.lemonade

import android.graphics.drawable.shapes.Shape
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lemonade.ui.theme.LemonadeTheme
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LemonadeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainContent()
                }
            }
        }
    }
}

@Composable
fun MainContent(modifier: Modifier = Modifier) {
    var step by remember { mutableIntStateOf(0) }

    Column {
         Row(modifier = modifier
             .background(color = colorResource(id = R.color.lemonade))
             .fillMaxWidth()) {
            Text(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                text = stringResource(id = R.string.lemonade_title),
                textAlign = TextAlign.Center,
            )
         }

         Row(modifier = modifier.weight(weight = 1.0f)) {
            Instructions(modifier = modifier, onClick = { step = (step + 1) % 4}, step = step)
         }
    }
}

@Composable
fun Instructions(modifier: Modifier = Modifier, onClick: () -> Unit, step: Int) {
    val description = when(step) {
        0 -> stringResource(id = R.string.tree_description)
        1 -> stringResource(id = R.string.lemon_description)
        2 -> stringResource(id = R.string.lemonade_description)
        else -> stringResource(id = R.string.empty_glass_description)
    }

    val image = when(step) {
        0 -> painterResource(id = R.drawable.lemon_tree)
        1 -> painterResource(id = R.drawable.lemon_squeeze)
        2 -> painterResource(id = R.drawable.lemon_drink)
        else -> painterResource(id = R.drawable.lemon_restart)
    }

    val instructions = when (step) {
        0 -> stringResource(id = R.string.tree_instructions)
        1 -> stringResource(id = R.string.lemon_instructions)
        2 -> stringResource(id = R.string.lemonade_instructions)
        else -> stringResource(id = R.string.empty_glass_instructions)
    }
    
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize().padding(bottom = 64.dp),
    ) {
        Button(
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.button_background)),
            modifier = modifier,
            onClick = onClick,
            shape = RoundedCornerShape(percent = 15)
        ) {
            Image(
                contentDescription = description,
                painter = image,
            )
        }

        Text(
            modifier = modifier.padding(16.dp),
            text = instructions
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LemonadeTheme {
        MainContent()
    }
}