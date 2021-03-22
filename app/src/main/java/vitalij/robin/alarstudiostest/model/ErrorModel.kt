package vitalij.robin.alarstudiostest.model

data class ErrorModel(
        val textResourceId: Int,
        val imageResourceId: Int,
        val descriptionResourceId: Int? = null,
        val isButtonVisible: Boolean = false
)