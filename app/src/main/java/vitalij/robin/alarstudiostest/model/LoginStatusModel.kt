package vitalij.robin.alarstudiostest.model

import com.google.gson.annotations.SerializedName

class LoginStatusModel(
    @SerializedName("status") var status: String,
    @SerializedName("code") var code: String?
)