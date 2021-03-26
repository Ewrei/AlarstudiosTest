package vitalij.robin.alarstudiostest.model.network

import com.google.gson.annotations.SerializedName

class MainModel(
    @SerializedName("id") var id: String,
    @SerializedName("name") var name: String,
    @SerializedName("country") var country: String,
    @SerializedName("lat") var lat: Double,
    @SerializedName("lon") var lon: Double
) {
    var position: Int = 0

    var imageUrl: String = ""
}