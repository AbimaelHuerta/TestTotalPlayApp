package com.example.pruebatotalplayapp

import com.example.pruebatotalplayapp.data.model.PlacePrediction
import com.example.pruebatotalplayapp.data.model.StructuredFormatting
import com.example.pruebatotalplayapp.data.repository.PlacesRepository
import com.example.pruebatotalplayapp.presentation.search.SearchViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchUnitTest {
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repository: PlacesRepository
    private lateinit var viewModel: SearchViewModel

    private val mockPredictions = listOf(
        PlacePrediction(
            placeId = "1",
            description = "CDMX Centro",
            structuredFormatting = StructuredFormatting("CDMX", "Ciudad de México"),
            types = listOf("locality")
        ),
        PlacePrediction(
            placeId = "2",
            description = "CDMX Aeropuerto",
            structuredFormatting = StructuredFormatting("Aeropuerto", "CDMX"),
            types = listOf("airport")
        )
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk(relaxed = true)
        viewModel = SearchViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    //query con menos de 3 caracteres no debe de tener predicciones
    @Test
    fun queryMenorTresCaracteres()= runTest{
        viewModel.onQueryChange("cd")
        advanceTimeBy(600)
        testDispatcher.scheduler.advanceUntilIdle()

        assertTrue(viewModel.state.value.predictions.isEmpty())

    }

    @Test
    fun busquedaExitosa()= runTest {
        coEvery { repository.searchPlaces("cdmx") } returns Result.success(mockPredictions)

        viewModel.onQueryChange("cdmx")
        advanceTimeBy(600)
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.state.value
        assertFalse(state.isLoading)
        assertEquals(2, state.predictions.size)
        assertEquals("1", state.predictions[0].placeId)
        assertNull(state.errorMessage)
    }

    @Test
    fun busquedaFallida()=runTest {
        coEvery { repository.searchPlaces("cdmx") } returns Result.failure(
            Exception("Error de red")
        )

        viewModel.onQueryChange("cdmx")
        advanceTimeBy(600)
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.state.value
        assertFalse(state.isLoading)
        assertTrue(state.predictions.isEmpty())
        assertEquals("Error de red", state.errorMessage)
    }

}