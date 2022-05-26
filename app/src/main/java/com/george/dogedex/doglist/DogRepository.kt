package com.george.dogedex.doglist

import com.george.dogedex.Dog
import com.george.dogedex.api.DogsApi.retrofitService
import com.george.dogedex.api.dto.DogDTOMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DogRepository {

    suspend fun downloadDogs(): List<Dog>{
        return withContext(Dispatchers.IO){
            val dogListApiResponse = retrofitService.getAllDogs()
            val dogDTOList = dogListApiResponse.data.dogs
            val dogDTOMapper = DogDTOMapper()
            dogDTOMapper.fromDogDTOListToDogDomainList(dogDTOList)
        }
    }
}