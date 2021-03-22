package vitalij.robin.alarstudiostest.interfaces

import vitalij.robin.alarstudiostest.model.ErrorModel

interface ErrorController {

    fun setError(errorModel: ErrorModel)

    fun hideError()

}