package com.example.pruebatotalplayapp.presentation.details


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pruebatotalplayapp.data.model.PlaceDetail
import com.example.pruebatotalplayapp.data.repository.PlacesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PlaceDetailsState(
    val placeDetail: PlaceDetail? = null,
    val photoUrls: List<String> = emptyList(),
    val errorMessage: String? = null
)

@HiltViewModel
class PlaceDetailsViewModel @Inject constructor(
    private val repository: PlacesRepository
) : ViewModel() {

    private val _state = MutableStateFlow(PlaceDetailsState())
    val state: StateFlow<PlaceDetailsState> = _state.asStateFlow()

    fun loadPlaceDetails(placeId: String) {
        viewModelScope.launch {

            repository.getPlaceDetails(placeId).fold(
                onSuccess = { placeDetail ->
                    val photoUrls = placeDetail.photos?.take(5)?.map { photo ->
                        repository.getPhotoUrl(photo.photoReference)
                    } ?: emptyList()

                    _state.value = _state.value.copy(
                        placeDetail = placeDetail,
                        photoUrls = photoUrls,
                    )
                },
                onFailure = { error ->
                    _state.value = _state.value.copy(
                        errorMessage = error.message ?: "Error al cargar los detalles"
                    )
                }
            )
        }
    }
}