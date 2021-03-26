package vitalij.robin.alarstudiostest.model.network

import com.google.gson.annotations.SerializedName

class MainResponse(
    @SerializedName("status") var status: String,
    @SerializedName("page") var page: Int,
    @SerializedName("data") var data: List<MainModel>
)