package com.rafaels.marsrover

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.rafaels.domain.Resource
import com.rafaels.domain.model.MarsRoverModel
import com.rafaels.domain.model.RoverDirectionModel
import com.rafaels.domain.usecase.LoadMarsRoverDataUseCase
import com.rafaels.marsrover.ui.feature.HomeViewModel
import com.rafaels.marsrover.ui.feature.model.RoverAction
import com.rafaels.marsrover.ui.mapper.mapUIDirection
import com.rafaels.marsrover.ui.mapper.mapUIRoverAction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var mockLoadMarsRoverDataUseCase: LoadMarsRoverDataUseCase

    // Define el TestDispatcher
    private val testDispatcher = StandardTestDispatcher()

    // --- Datos de Mock Comunes ---
    private val dummyMarsRoverModel = MarsRoverModel(
        topRightCorner = Pair(0, 0),
        roverPosition = Pair(0, 0),
        roverInitialDirectionModel = RoverDirectionModel.NORTH,
        movements = "" // Deja esto vacío para evitar el error de mapeo en este test
    )

    private val dummySuccessResult = Resource.Success(dummyMarsRoverModel)
    // --- Fin de Datos de Mock Comunes ---


    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        // Resetear el dispatcher Main después de cada test
        Dispatchers.resetMain()
    }

    @Test
    fun `getInputUseCase is called on ViewModel initialization`() = runTest(testDispatcher) {
        `when`(mockLoadMarsRoverDataUseCase.getMarsRoverData()).thenReturn(dummySuccessResult)

        val viewModel = HomeViewModel(mockLoadMarsRoverDataUseCase)

        advanceUntilIdle()

        verify(mockLoadMarsRoverDataUseCase).getMarsRoverData()
    }

    @Test
    fun `getInputUseCase loads initial data into uiState on success without movements`() =
        runTest(testDispatcher) {
            val mockTopRightCorner = Pair(5, 5)
            val mockRoverPosition = Pair(1, 2)
            val mockInitialDirection = RoverDirectionModel.NORTH


            val mockDomainDataWithoutMovements = MarsRoverModel(
                topRightCorner = mockTopRightCorner,
                roverPosition = mockRoverPosition,
                roverInitialDirectionModel = mockInitialDirection,
                movements = ""
            )
            val successResource = Resource.Success(mockDomainDataWithoutMovements)

            `when`(mockLoadMarsRoverDataUseCase.getMarsRoverData()).thenReturn(successResource)
            val viewModel = HomeViewModel(mockLoadMarsRoverDataUseCase)
            advanceUntilIdle()

            verify(mockLoadMarsRoverDataUseCase).getMarsRoverData()
            val currentUiState = viewModel.uiState.value
            val expectedUiInitialDirection = mockInitialDirection.mapUIDirection()

            assert(currentUiState.topRightCorner == mockTopRightCorner) {
                "Expected topRightCorner ${mockTopRightCorner} but was ${currentUiState.topRightCorner}"
            }
            assert(currentUiState.roverPosition == mockRoverPosition) {
                "Expected roverPosition ${mockRoverPosition} but was ${currentUiState.roverPosition}" // <-- Ahora debería coincidir con (1, 2)
            }
            assert(currentUiState.roverInitialDirection == expectedUiInitialDirection) {
                "Expected roverInitialDirection ${expectedUiInitialDirection} but was ${currentUiState.roverInitialDirection}"
            }

            println("Final UI State for initial load test: $currentUiState")
        }

    @Test
    fun `getInputUseCase loads initial data into uiState on success with movements`() =
        runTest(testDispatcher) {
            val mockTopRightCorner = Pair(5, 5)
            val mockRoverPosition = Pair(1, 2)
            val mockInitialDirection = RoverDirectionModel.NORTH
            val mockMovementsString = "LMLMLMLMM"
            val mockFinalRoverPosition = Pair(1, 3)


            val mockDomainDataWithoutMovements = MarsRoverModel(
                topRightCorner = mockTopRightCorner,
                roverPosition = mockRoverPosition,
                roverInitialDirectionModel = mockInitialDirection,
                movements = mockMovementsString
            )
            val successResource = Resource.Success(mockDomainDataWithoutMovements)

            `when`(mockLoadMarsRoverDataUseCase.getMarsRoverData()).thenReturn(successResource)
            val viewModel = HomeViewModel(mockLoadMarsRoverDataUseCase)
            advanceUntilIdle()


            val currentUiState = viewModel.uiState.value
            val expectedUiInitialDirection = mockInitialDirection.mapUIDirection()
            val expectedUiMovements: List<RoverAction> =
                mockMovementsString.map { it.mapUIRoverAction() }

            verify(mockLoadMarsRoverDataUseCase).getMarsRoverData()
            assert(currentUiState.topRightCorner == mockTopRightCorner) {
                "Expected topRightCorner ${mockTopRightCorner} but was ${currentUiState.topRightCorner}"
            }
            assert(currentUiState.roverPosition == mockFinalRoverPosition) {
                "Expected roverPosition ${mockFinalRoverPosition} but was ${currentUiState.roverPosition}" // <-- Ahora debería coincidir con (1, 2)
            }
            assert(currentUiState.roverInitialDirection == expectedUiInitialDirection) {
                "Expected roverInitialDirection ${expectedUiInitialDirection} but was ${currentUiState.roverInitialDirection}"
            }
            assert(currentUiState.movements == expectedUiMovements) { // Aserción para movements
                "Expected movements ${expectedUiMovements} but was ${currentUiState.movements}"
            }

            println("Final UI State for initial load test: $currentUiState")
        }
}