package com.example.yelp_network_kt

import com.google.gson.annotations.SerializedName

data class YelpSearchResult(
    @SerializedName("total") val total: Int,
    @SerializedName("businesses") val allRestaurants: List<YelpRestaurant>
)

data class YelpRestaurant(
    val name: String,
    val rating: Double,
    val price: String,
    @SerializedName("review_count") val numReview: Int,
    @SerializedName("distance") val distanceInMeter: Double,
    @SerializedName("image_url") val imageUrl: String,
    val categories: List<YelpCategories>,
    val location: YelpLocation
) {
    fun displayDistance(): String {
        val milesPerMeter = 0.000621371
        val distanceInMiles = "%.2f".format(distanceInMeter * milesPerMeter)
        return "$distanceInMiles mi"
    }
}

data class YelpCategories(
    val title: String
)

data class YelpLocation(
    @SerializedName("address1") val address: String
)