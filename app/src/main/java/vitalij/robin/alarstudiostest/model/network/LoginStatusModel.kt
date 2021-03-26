package vitalij.robin.alarstudiostest.model.network

import com.google.gson.annotations.SerializedName

class LoginStatusModel(
    @SerializedName("status") var status: String,
    @SerializedName("code") var code: String?
)