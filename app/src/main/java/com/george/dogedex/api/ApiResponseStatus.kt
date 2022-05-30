package com.george.dogedex.api

import com.george.dogedex.Dog

sealed class ApiResponseStatus {
    class Success(val dogList: List<Dog>) : ApiResponseStatus()
    object Loading : ApiResponseStatus()
    class Error(val messageId: Int) : ApiResponseStatus()
}