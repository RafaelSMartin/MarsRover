package com.rafaels.marsrover.ui.feature.commom

fun incrementalMovementControl(pos: Int, totalPos: Int): Int {
    return if (pos < 0) pos + 1
    else if (pos >= totalPos) pos - 1
    else pos
}
