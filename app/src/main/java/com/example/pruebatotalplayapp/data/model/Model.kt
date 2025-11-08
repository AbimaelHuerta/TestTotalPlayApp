package com.example.pruebatotalplayapp.data.model


import com.google.gson.annotations.SerializedName

data class PlacesAutocompleteResponse(
    val predictions: List<PlacePrediction>,
    val status: String
)

data class PlacePrediction(
    @SerializedName("place_id")
    val placeId: String,
    val description: String,
    @SerializedName("structured_formatting")
    val structuredFormatting: StructuredFormatting,
    val types: List<String>
)

data class StructuredFormatting(
    @SerializedName("main_text")
    val mainText: String,
    @SerializedName("secondary_text")
    val secondaryText: String?
)

data class PlaceDetailsResponse(
    val result: PlaceDetail,
    val status: String
)

data class PlaceDetail(
    @SerializedName("place_id")
    val placeId: String,
    val name: String,
    @SerializedName("formatted_address")
    val formattedAddress: String,
    @SerializedName("formatted_phone_number")
    val formattedPhoneNumber: String?,
    val rating: Double?,
    @SerializedName("user_ratings_total")
    val userRatingsTotal: Int?,
    val photos: List<Photo>?,
    val reviews: List<Review>?,
    @SerializedName("opening_hours")
    val openingHours: OpeningHours?,
)

data class Photo(
    @SerializedName("photo_reference")
    val photoReference: String,
    val height: Int,
    val width: Int
)

data class Review(
    @SerializedName("author_name")
    val authorName: String,
    val rating: Int,
    val text: String,
    @SerializedName("relative_time_description")
    val relativeTimeDescription: String,
    @SerializedName("profile_photo_url")
    val profilePhotoUrl: String?
)

data class OpeningHours(
    @SerializedName("open_now")
    val openNow: Boolean?,
    @SerializedName("weekday_text")
    val weekdayText: List<String>?
)
