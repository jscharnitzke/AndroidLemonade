package com.example.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lemonade.ui.theme.LemonadeTheme

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
    var stepNumber by remember { mutableIntStateOf(0) }

    val step = when(stepNumber) {
        0 -> Step(
            currentStep = stepNumber,
            description = stringResource(id = R.string.tree_description),
            image = painterResource(id = R.drawable.lemon_tree),
            instructions = stringResource(id = R.string.tree_instructions),
        )
        1 -> QuicktimeStep(
            currentStep = stepNumber,
            description = stringResource(id = R.string.lemon_description),
            image = painterResource(id = R.drawable.lemon_squeeze),
            instructions = stringResource(id = R.string.lemon_instructions),
        )
        2 -> Step(
            currentStep = stepNumber,
            description = stringResource(id = R.string.lemonade_description),
            image = painterResource(id = R.drawable.lemon_drink),
            instructions = stringResource(id = R.string.lemonade_instructions),
        )
        else -> Step(
            currentStep = stepNumber,
            description = stringResource(id = R.string.empty_glass_description),
            image = painterResource(id = R.drawable.lemon_restart),
            instructions = stringResource(id = R.string.empty_glass_instructions),
        )
    }

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
            Instructions(modifier = modifier, onChange = { stepNumber = it }, step = step)
         }
    }
}

open class Step(
    protected val currentStep: Int,
    val description: String,
    val instructions: String,
    val image: Painter,
) {
    open fun onClick(): Int {
        return (currentStep + 1) % 4
    }
}

class QuicktimeStep(
    currentStep: Int,
    description: String,
    instructions: String,
    image: Painter,
): Step(currentStep, description, instructions, image) {
    private var numTaps = (2..4).random()
    private var tapCount = 0

    override fun onClick(): Int {
        if(++tapCount < numTaps) {
            return currentStep
        }

        return super.onClick()
    }
}

@Composable
fun Instructions(modifier: Modifier = Modifier, onChange: (Int) -> Unit, step: Step) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize().padding(bottom = 64.dp),
    ) {
        Button(
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.button_background)),
            modifier = modifier,
            onClick = { onChange(step.onClick()) },
            shape = RoundedCornerShape(percent = 15)
        ) {
            Image(
                contentDescription = step.description,
                painter = step.image,
            )
        }

        Text(
            modifier = modifier.padding(16.dp),
            text = step.instructions
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