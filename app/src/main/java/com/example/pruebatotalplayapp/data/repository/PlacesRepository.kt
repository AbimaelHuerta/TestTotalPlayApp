package com.example.pruebatotalplayapp.data.repository


import com.example.pruebatotalplayapp.data.model.PlaceDetail
import com.example.pruebatotalplayapp.data.model.PlacePrediction
import com.example.pruebatotalplayapp.data.remote.PlacesApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PlacesRepository @Inject constructor(
    private val apiService: PlacesApiService
) {
    private val apiKey = "AIzaSyByspR_e5IGCdkyJZ1HAjLvWvvtX6kPzuU"

    suspend fun searchPlaces(query: String): Result<List<PlacePrediction>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getPlaceAutocomplete(
                    input = query,
                    apiKey = apiKey
                )

                if (response.status == "OK" || response.status == "ZERO_RESULTS") {
                    Result.success(response.predictions)
                } else {
                    Result.failure(Exception("Error en la búsqueda: ${response.status}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    suspend fun getPlaceDetails(placeId: String): Result<PlaceDetail> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getPlaceDetails(
                    placeId = placeId,
                    apiKey = apiKey
                )

                if (response.status == "OK") {
                    Result.success(response.result)
                } else {
                    Result.failure(Exception("Error al obtener detalles: ${response.status}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    fun getPhotoUrl(photoReference: String, maxWidth: Int = 400): String {
        return "https://maps.googleapis.com/maps/api/place/photo?maxwidth=$maxWidth&photo_reference=$photoReference&key=$apiKey"
    }
}