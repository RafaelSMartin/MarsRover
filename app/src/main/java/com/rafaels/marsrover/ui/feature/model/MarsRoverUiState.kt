package com.rafaels.marsrover.ui.feature.model

data class MarsRoverUiState(
    val topRightCorner: Pair<Int, Int> = Pair(1, 1),
    val roverPosition: Pair<Int, Int> = Pair(0, 0),
    val roverInitialDirection: RoverDirection = RoverDirection.NORTH,
    val movements: List<RoverAction> = emptyList(),
)



