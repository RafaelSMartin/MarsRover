package com.rafaels.marsrover.ui.mapper

import com.rafaels.domain.model.RoverDirectionModel
import com.rafaels.marsrover.ui.feature.model.RoverAction
import com.rafaels.marsrover.ui.feature.model.RoverDirection


fun RoverDirectionModel.mapUIDirection(): RoverDirection {
    return when (this) {
        RoverDirectionModel.NORTH -> RoverDirection.NORTH
        RoverDirectionModel.SOUTH -> RoverDirection.SOUTH
        RoverDirectionModel.EAST -> RoverDirection.EAST
        RoverDirectionModel.WEST -> RoverDirection.WEST
        else -> RoverDirection.NORTH
    }
}

fun Char.mapUIRoverAction(): RoverAction {
    return when (this) {
        'M' -> RoverAction.MOVE
        'L' -> RoverAction.TURN_LEFT
        'R' -> RoverAction.TURN_RIGHT
        else -> {
            println("Error: Unknown movement character found: '$this'") // O log.e
            throw IllegalArgumentException("Unknown movement character: $this. Cannot map to UiRoverAction.")
        }
    }
}