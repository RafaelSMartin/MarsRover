package com.rafaels.marsrover.ui.feature

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafaels.domain.Resource
import com.rafaels.domain.usecase.LoadMarsRoverDataUseCase
import com.rafaels.marsrover.ui.feature.commom.incrementalMovementControl
import com.rafaels.marsrover.ui.feature.model.MarsRoverUiState
import com.rafaels.marsrover.ui.feature.model.RoverAction
import com.rafaels.marsrover.ui.feature.model.RoverDirection
import com.rafaels.marsrover.ui.feature.model.Turn
import com.rafaels.marsrover.ui.mapper.mapUIDirection
import com.rafaels.marsrover.ui.mapper.mapUIRoverAction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val loadMarsRoverDataUseCase: LoadMarsRoverDataUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MarsRoverUiState())
    val uiState: StateFlow<MarsRoverUiState> = _uiState.asStateFlow()

    private val _isMovementEnabled = MutableStateFlow(true)
    val isMovementEnabled: StateFlow<Boolean> = _isMovementEnabled.asStateFlow()

    init {
        loadMarsRoverData()
    }

    private fun loadMarsRoverData() {
        viewModelScope.launch {
            when (val result = loadMarsRoverDataUseCase.getMarsRoverData()) {
                is Resource.Success -> {
                    _uiState.update {
                        it.copy(
                            topRightCorner = result.data.topRightCorner,
                            roverPosition = result.data.roverPosition,
                            roverInitialDirection = result.data.roverInitialDirectionModel.mapUIDirection(),
                            movements = result.data.movements.map { movement ->
                                movement.mapUIRoverAction()
                            },
                        )
                    }
                    onInitActions()
                }

                is Resource.Error -> {
                    //Handle error
                }
            }
        }
    }

    private suspend fun onInitActions() {
        uiState.value.movements.forEach {
            when (it) {
                RoverAction.MOVE -> onMovement()
                RoverAction.TURN_LEFT -> onRotateLeft()
                RoverAction.TURN_RIGHT -> onRotateRight()
            }
            kotlinx.coroutines.delay(1000)
        }
        Log.d("PUTOTA", "isMOvement: ${isMovementEnabled.value}")
        _isMovementEnabled.update { false }
        Log.d("PUTOTA", "isMOvementDespues: ${isMovementEnabled.value}")
    }


    fun onMovement() {
        _uiState.update {
            it.copy(
                roverPosition = onMove(
                    uiState.value.roverPosition.first,
                    uiState.value.roverPosition.second,
                    uiState.value.roverInitialDirection
                )
            )
        }
    }

    fun onRotateLeft() {
        _uiState.update {
            it.copy(roverInitialDirection = uiState.value.roverInitialDirection.turned(Turn.L))
        }
    }

    fun onRotateRight() {
        _uiState.update {
            it.copy(roverInitialDirection = uiState.value.roverInitialDirection.turned(Turn.R))
        }
    }

    private fun onMove(
        xPos: Int,
        yPos: Int,
        roverDirection: RoverDirection,
    ): Pair<Int, Int> {
        val totalRows = uiState.value.topRightCorner.second
        val totalColumns = uiState.value.topRightCorner.first

        return when (roverDirection) {
            RoverDirection.NORTH -> Pair(xPos, incrementalMovementControl(yPos + 1, totalColumns))
            RoverDirection.SOUTH -> Pair(xPos, incrementalMovementControl(yPos - 1, totalColumns))
            RoverDirection.EAST -> Pair(incrementalMovementControl(xPos + 1, totalRows), yPos)
            RoverDirection.WEST -> Pair(incrementalMovementControl(xPos - 1, totalRows), yPos)
        }
    }

}