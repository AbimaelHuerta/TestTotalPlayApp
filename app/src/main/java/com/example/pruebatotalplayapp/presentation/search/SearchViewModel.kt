package com.example.pruebatotalplayapp.presentation.search


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pruebatotalplayapp.data.model.PlacePrediction
import com.example.pruebatotalplayapp.data.repository.PlacesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SearchState(
    val query: String = "",
    val predictions: List<PlacePrediction> = emptyList(),
    val errorMessage: String? = null,
    val isLoading: Boolean= false
)

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: PlacesRepository
) : ViewModel() {

    private val _state = MutableStateFlow(SearchState())
    val state: StateFlow<SearchState> = _state.asStateFlow()

    private val _queryFlow = MutableStateFlow("")

    init {
        viewModelScope.launch {
            _queryFlow
                .filter { it.length > 2 }
                .collect { query ->
                    searchPlaces(query)
                }
        }
    }

    fun onQueryChange(query: String) {
        _state.value = _state.value.copy(query = query, errorMessage = null)

        if (query.length < 3) {
            _state.value = _state.value.copy(predictions = emptyList())
        } else {
            _queryFlow.value = query
        }
    }

    private fun searchPlaces(query: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, errorMessage = null)


            repository.searchPlaces(query).fold(
                onSuccess = { predictions ->
                    _state.value = _state.value.copy(
                        predictions = predictions,
                        isLoading = false
                    )
                },
                onFailure = { error ->
                    _state.value = _state.value.copy(
                        errorMessage = error.message ?: "Error desconocido",
                        isLoading = false
                    )
                }
            )
        }
    }

    fun clearSearch() {
        _state.value = SearchState()
        _queryFlow.value = ""
    }
}