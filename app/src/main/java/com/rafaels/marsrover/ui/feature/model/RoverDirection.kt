package com.rafaels.marsrover.ui.feature.model

enum class RoverDirection {
    NORTH,
    EAST,
    SOUTH,
    WEST;

    fun turned(turn: Turn): RoverDirection {
        val modulo: (Int, Int) -> Int =
            { number, divisor -> ((number % divisor) + divisor) % divisor }
        return entries[modulo(entries.indexOf(this) + turn.step, entries.size)]
    }
}

enum class Turn(val step: Int) { L(-1), R(1) }

fun rotateRover(roverDirection: RoverDirection): Float {
    return when (roverDirection) {
        RoverDirection.NORTH -> 0f
        RoverDirection.SOUTH -> 180f
        RoverDirection.EAST -> 90f
        RoverDirection.WEST -> 270f
    }
}