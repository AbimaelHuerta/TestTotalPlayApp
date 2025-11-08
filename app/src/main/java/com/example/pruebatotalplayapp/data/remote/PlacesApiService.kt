package com.example.pruebatotalplayapp.data.remote

import com.example.pruebatotalplayapp.data.model.PlaceDetailsResponse
import com.example.pruebatotalplayapp.data.model.PlacesAutocompleteResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PlacesApiService {

    @GET("place/autocomplete/json")
    suspend fun getPlaceAutocomplete(
        @Query("input") input: String,
        @Query("location") location: String = "19.434553517198584,-99.13307950891304",
        @Query("radius") radius: Int = 1000,
        @Query("language") language: String = "es",
        @Query("components") components: String = "country:mx",
        @Query("key") apiKey: String
    ): PlacesAutocompleteResponse

    @GET("place/details/json")
    suspend fun getPlaceDetails(
        @Query("place_id") placeId: String,
        @Query("key") apiKey: String
    ): PlaceDetailsResponse
}