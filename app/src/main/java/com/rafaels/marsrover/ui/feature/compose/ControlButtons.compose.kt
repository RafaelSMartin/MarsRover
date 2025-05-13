package com.rafaels.marsrover.ui.feature.compose


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ControlButtons(
    modifier: Modifier = Modifier,
    onMovementClick: () -> Unit = {},
    onMoveLeftClick: () -> Unit = {},
    onMoveRightClick: () -> Unit = {},
    isMovementEnabled: Boolean = false,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = onMovementClick,
            enabled = !isMovementEnabled
        ) {
            Text(text = "Movement")
        }

        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            Button(
                onClick = onMoveLeftClick,
                enabled = !isMovementEnabled
            ) {
                Text(text = "Rotate Left")
            }
            Button(
                onClick = onMoveRightClick,
                enabled = !isMovementEnabled
            ) {
                Text(text = "Rotate Right")
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun ControlButtonsPreview() {
    ControlButtons()
}