package com.rafaels.marsrover.ui.feature.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rafaels.marsrover.R
import com.rafaels.marsrover.ui.feature.HomeViewModel
import com.rafaels.marsrover.ui.feature.model.rotateRover
import org.koin.androidx.compose.koinViewModel


@Composable
fun GrillDynamic(
    modifier: Modifier = Modifier,
    marsRoverViewModel: HomeViewModel = koinViewModel()
) {
    val uiState by marsRoverViewModel.uiState.collectAsStateWithLifecycle()
    val isMovementEnabled by marsRoverViewModel.isMovementEnabled.collectAsStateWithLifecycle()

    val columns = uiState.topRightCorner.first
    val gridHeight = uiState.topRightCorner.second // Obtén la altura del grid
    val items = columns * gridHeight // Número total de ítems
    val roverInitialDirection: Float = rotateRover(uiState.roverInitialDirection)

    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        modifier = Modifier.fillMaxWidth(),
    ) {
        items(items) { item ->
            Box(
                modifier = Modifier
                    .padding(1.dp)
                    .size(64.dp)
                    .background(colorResource(R.color.sand)),
                contentAlignment = Alignment.Center

            ) {
                val normalRow: Int = item.div(columns)
                val column: Int = item % (columns)
                // Calcula la fila ajustada para el origen en la esquina inferior izquierda
                val adjustedRow: Int = gridHeight - 1 - normalRow

                if (adjustedRow == uiState.roverPosition.second && column == uiState.roverPosition.first) {
                    Image(
                        painter = painterResource(id = R.drawable.icon_rover),
                        //painter = painterResource(id = android.R.drawable.arrow_up_float),
                        contentDescription = "ImageRover",
                        modifier = Modifier
                            .width(36.dp)
                            .height(36.dp)
                            .rotate(roverInitialDirection)
                    )
                }
                Text(
                    text = "(${(column)}-${adjustedRow})",
                    fontSize = 8.sp,
                    modifier = Modifier.align(Alignment.BottomEnd)
                )
            }
        }
    }

    Spacer(modifier = Modifier.padding(8.dp))

    ControlButtons(
        modifier = modifier,
        onMovementClick = { marsRoverViewModel.onMovement() },
        onMoveLeftClick = { marsRoverViewModel.onRotateLeft() },
        onMoveRightClick = { marsRoverViewModel.onRotateRight() },
        isMovementEnabled = isMovementEnabled
    )
}
