package com.george.dogedex.doglist

import com.george.dogedex.Dog
import com.george.dogedex.R
import com.george.dogedex.api.ApiResponseStatus
import com.george.dogedex.api.DogsApi.retrofitService
import com.george.dogedex.api.dto.DogDTOMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.net.UnknownHostException

class DogRepository {

    suspend fun downloadDogs(): ApiResponseStatus {
        return withContext(Dispatchers.IO) {
            try {
                val dogListApiResponse = retrofitService.getAllDogs()
                val dogDTOList = dogListApiResponse.data.dogs
                val dogDTOMapper = DogDTOMapper()
                ApiResponseStatus.Success(dogDTOMapper.fromDogDTOListToDogDomainList(dogDTOList))
            } catch (e: UnknownHostException) {
                ApiResponseStatus.Error(R.string.unknown_host_exception_error)
            } catch (e: Exception) {
                ApiResponseStatus.Error(R.string.unknown_error)
            }
        }
    }
}