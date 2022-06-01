package com.george.dogedex.doglist

import com.george.dogedex.Dog
import com.george.dogedex.R
import com.george.dogedex.api.ApiResponseStatus
import com.george.dogedex.api.DogsApi.retrofitService
import com.george.dogedex.api.dto.DogDTOMapper
import com.george.dogedex.api.makeNetworkCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.net.UnknownHostException

class DogRepository {

    suspend fun downloadDogs(): ApiResponseStatus<List<Dog>> = makeNetworkCall {
        val dogListApiResponse = retrofitService.getAllDogs()
        val dogDTOList = dogListApiResponse.data.dogs
        val dogDTOMapper = DogDTOMapper()
        dogDTOMapper.fromDogDTOListToDogDomainList(dogDTOList)
    }

}